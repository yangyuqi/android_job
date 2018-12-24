package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.UserBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.MyCountDownTimer;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.dialog.RegisterSuccessDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class RegisterPhoneActivity extends BaseActivity {

    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_pwd)
    EditText tvPwd;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.tv_code)
    EditText tvCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_find_pwd)
    TextView tvFindPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.layout_header)
    RelativeLayout layoutHeader;

    private MyCountDownTimer timer ;

    private String type ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phone_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        textHeadTitle.setText("注册");
        btnBack.setVisibility(View.VISIBLE);
        layoutHeader.setBackgroundResource(R.drawable.main_color);
        type=getIntent().getStringExtra("type");
        timer = new MyCountDownTimer(btnGetCode,60000,1000);

    }

    @OnClick({R.id.btnBack,R.id.btn_login,R.id.btn_get_code,R.id.tv_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack :
                finish();
                break;
            case R.id.btn_login :
                if (!PublicUtils.checkMobileNumber(tvPhone.getText().toString())){
                    showToast("请输入正确手机号");
                    return;
                }

                if (tvPwd.getText().toString().equals("")){
                    showToast("密码不能为空");
                    return;
                }
                if (tvCode.getText().toString().equals("")){
                    showToast("验证码不能为空");
                    return;
                }

                final Map<String,Object> map = new HashMap<>();
                map.put("username",tvPhone.getText().toString());
                map.put("password",PublicUtils.md5Password(tvPwd.getText().toString()));
                map.put("smsCode",tvCode.getText().toString());
                if (type.equals("1")) {
                    map.put("userType", "0");
                }else if (type.equals("2")){
                    map.put("userType","1");
                }
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.REGISTER_URL, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                            MobclickAgent.onEvent(mContext,"register");
                            final Map<String,Object> map = new HashMap<>();
                            RxPermissions permissions = new RxPermissions(RegisterPhoneActivity.this);
                            permissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (aBoolean){
                                        JPushInterface.setAlias(mContext, PublicUtils.getPhotoImEi(mContext), new TagAliasCallback() {
                                            @Override
                                            public void gotResult(int i, String s, Set<String> set) {


                                            }
                                        });
                                        map.put("deviceNo",PublicUtils.getPhotoImEi(mContext));
                                    }
                                }
                            });
                            map.put("username",tvPhone.getText().toString());
                            map.put("password",PublicUtils.md5Password(tvPwd.getText().toString()));
                            if (type.equals("1")) {
                                map.put("userType", "0");
                            }else if (type.equals("2")){
                                map.put("userType","1");
                            }
                            map.put("deviceType","android");

                            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.LOGIN_URL, new OkHttpClientManager.StringCallback() {
                                @Override
                                public void onFailure(Request request, IOException e) {

                                }

                                @Override
                                public void onResponse(String response) {
                                    BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                    if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                        UserBean bean = gson.fromJson(gson.toJson(entity.getData()),UserBean.class);
                                        SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.access_token,bean.getAccess_token());
                                        SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.uid,bean.getUser_id());
                                        SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.mobile,bean.getUsername());
                                        final RegisterSuccessDialog dialog = new RegisterSuccessDialog(mContext,type);
                                        dialog.show();
                                    }else {
                                        showToast(entity.getMsg());
                                    }
                                }
                            });
                        }else {
                            showToast(entity.getMsg());
                        }
                    }
                });


                break;

            case R.id.btn_get_code:
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
                break;
            case R.id.tv_register:
                startActivity(new Intent(mContext,LoginActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("register_phone_layout");
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("register_phone_layout");
        MobclickAgent.onPause(mContext);
    }

}
