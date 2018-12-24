package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.CoMainActivity;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.UserBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.LoginByCodeActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.ActiivtyStack;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_pwd)
    EditText tvPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_find_pwd)
    TextView tvFindPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    final Map<String, Object> map = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        doLoginPer();
        String phone = (String) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.mobile, "");
        if (phone != null) {
            if (!phone.equals("")) {
                tvPhone.setText(phone);
            }
        }
    }

    @OnClick({R.id.tv_register, R.id.tv_pwd, R.id.btn_login, R.id.tv_find_pwd,R.id.tv_get_code})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
            case R.id.tv_find_pwd:
                startActivity(new Intent(mContext, FindPwdActivity.class));
                break;
            case R.id.btn_login:
                if (tvPhone.getText().toString().equals("")) {
                    showToast("请输入正确手机号");
                    return;
                }

                if (tvPwd.getText().toString().equals("")) {
                    showToast("密码不能为空");
                    return;
                }

                map.put("username", tvPhone.getText().toString());
                map.put("password", PublicUtils.md5Password(tvPwd.getText().toString()));
                map.put("userType", "0");
                map.put("deviceType", "android");

                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.LOGIN_URL, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            MobclickAgent.onEvent(mContext,"login");
                            UserBean bean = gson.fromJson(gson.toJson(entity.getData()), UserBean.class);
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.access_token, bean.getAccess_token());
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.uid, bean.getUser_id());
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.mobile, bean.getUsername());
                            SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.userType,bean.getUserType());
                            setTagAndAlias(bean.getAccess_token());
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
                break;

            case R.id.tv_get_code :
                startActivity(new Intent(mContext, LoginByCodeActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("login_layout");
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("login_layout");
        MobclickAgent.onPause(mContext);
    }

    private void setTagAndAlias(String token) {
        Set<String> tags = new HashSet<String>();
        if (!TextUtils.isEmpty(token)){
            tags.add(token);//设置tag
            JPushInterface.setAliasAndTags(mContext, token, tags,
                    null);
        }

    }

    public void doLoginPer(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions permissions = new RxPermissions(LoginActivity.this);
            permissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        map.put("deviceNo", PublicUtils.getPhotoImEi(mContext));
                        JPushInterface.setAlias(mContext, PublicUtils.getPhotoImEi(mContext), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                            }
                        });

                    }
                }
            });
        }else {

        }
    }
}
