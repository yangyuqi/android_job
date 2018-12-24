package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.MyCountDownTimer;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2018/2/9.
 */

public class FindPwdActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.tv_code)
    EditText tvCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_pwd)
    EditText tvPwd;
    @BindView(R.id.tv_again_pwd)
    EditText tvAgainPwd;

    private MyCountDownTimer timer ;

    private String type ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pwd_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");

        if (type==null) {
            textHeadTitle.setText("忘记密码");
        }else {
            textHeadTitle.setText("修改密码");
        }
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        timer = new MyCountDownTimer(btnGetCode,60000,1000);
    }

    @OnClick({R.id.btn_get_code,R.id.btn_login})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_get_code :
                if (tvPhone.getText().toString().length()!=11){
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
                        if (!entity.getCode().equals(PublicUtils.SUCCESS)) {
                            showToast(entity.getMsg());
                        }
                    }
                });

                break;
            case R.id.btn_login :
                if (tvPhone.getText().toString().length()!=11){
                    showToast("请输入正确手机号");
                    return;
                }
                if (tvCode.getText().toString().equals("")){
                    showToast("请输入验证码");
                    return;
                }
                if (tvPwd.getText().toString().equals("")){
                    if (type==null) {
                        showToast("请输入密码");
                    }else {
                        showToast("请输入原密码");
                    }
                    return;
                }
                if (type==null) {
                    if (!tvPwd.getText().toString().equals(tvAgainPwd.getText().toString())) {
                        showToast("两次密码不一致");
                        return;
                    }
                }else {
                    if (tvAgainPwd.getText().toString().equals("")){
                        showToast("请输入新密码");
                        return;
                    }
                }
                    Map<String, Object> map = new HashMap<>();
                    map.put("username", tvPhone.getText().toString());
                    map.put("smsCode", tvCode.getText().toString());
                    map.put("newPassword", PublicUtils.md5Password(tvPwd.getText().toString()));
                    OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.FIND_PWD, new OkHttpClientManager.StringCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                            showToast(entity.getMsg());
                            if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                                if (type==null) {
                                    finish();
                                }else {
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                    finish();
                                }
                            }
                        }
                    });

                break;
        }
    }
}
