package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.CoMainActivity;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.UserBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FillInfoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FindPwdActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.RegisterActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.ActiivtyStack;
import com.youzheng.tongxiang.huntingjob.UI.Utils.MyCountDownTimer;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginByCodeActivity extends BaseActivity {

    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_pwd)
    EditText tvPwd;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_find_pwd)
    TextView tvFindPwd;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.iv_close)
    TextView ivClose;

    private MyCountDownTimer timer ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_by_code_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        timer = new MyCountDownTimer(btnGetCode,60000,1000);
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PublicUtils.checkMobileNumber(tvPhone.getText().toString())){
                    showToast("请输入正确手机号");
                    return;
                }
                timer.start();
                Map<String,Object> map_map = new HashMap<>();
                map_map.put("mobile",tvPhone.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map_map), UrlUtis.SEND_CODE, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        showToast(entity.getMsg());
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvPwd.getText().toString().equals("")){
                    showToast("请输入验证码");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("username",tvPhone.getText().toString());
                map.put("smsCode",tvPwd.getText().toString());
                map.put("deviceType","android");
                map.put("deviceNo",PublicUtils.getPhotoImEi(mContext));
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CODE_LOGIN, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            UserBean bean = gson.fromJson(gson.toJson(entity.getData()), UserBean.class);
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.access_token, bean.getAccess_token());
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.uid, bean.getUser_id());
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.mobile, bean.getUsername());
                            if (bean.getUserType() == 0) {
                                if (bean.isBooleanBaseInfo()) {
                                    SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.rid, bean.getRid());
                                    ActiivtyStack.getScreenManager().clearAllActivity();
                                    startActivity(new Intent(mContext, MainActivity.class));
                                } else {
                                    showToast("您的信息不完整，请去完整");
                                    startActivity(new Intent(mContext, FillInfoActivity.class));
                                }
                            } else {
                                ActiivtyStack.getScreenManager().clearAllActivity();
                                startActivity(new Intent(mContext, CoMainActivity.class));
                            }
                            finish();
                        } else {
                            showToast(entity.getMsg());
                        }
                    }
                });
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, RegisterActivity.class));

            }
        });

        tvFindPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        });

        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, FindPwdActivity.class));
                finish();
            }
        });
    }
}
