package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/5/24.
 */

public class MyRebackActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_typr)
    EditText edtTypr;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.tv_attention)
    TextView tvAttention;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reback_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        textHeadTitle.setText("我要反馈");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("accessToken",accessToken);
                map.put("title",edtName.getText().toString());
                map.put("content",edtTypr.getText().toString());
                map.put("phone",edtAddress.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ADD_REBACK, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                        showToast(entity.getMsg());
                        finish();
                    }
                });
            }
        });

    }
}
