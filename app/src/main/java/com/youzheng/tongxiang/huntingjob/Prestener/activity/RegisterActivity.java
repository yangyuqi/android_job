package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.tongxiang.huntingjob.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.layout_header)
    RelativeLayout layoutHeader;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_co)
    TextView tvCo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        textHeadTitle.setText("注册");
        layoutHeader.setBackgroundResource(R.drawable.main_color);
    }

    @OnClick({R.id.btnBack,R.id.tv_user,R.id.tv_co})
    public void OnClick(View view){
        Intent i = null ;
        switch (view.getId()){
            case R.id.btnBack :
                finish();
                break;
            case R.id.tv_user:
                i = new Intent(mContext,RegisterPhoneActivity.class);
                i.putExtra("type","1");
                startActivity(i);
                break;
            case R.id.tv_co:
                i = new Intent(mContext,RegisterPhoneActivity.class);
                i.putExtra("type","2");
                startActivity(i);
                break;

        }
    }
}
