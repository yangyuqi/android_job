package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/24.
 */

public class H5Activity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls_details)
    WebView lsDetails;
    @BindView(R.id.iv_show)
    ImageView ivShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_layout);
        ButterKnife.bind(this);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String url = getIntent().getStringExtra("url");
        if (title != null) {
            textHeadTitle.setText(title);
            if (title.equals("关于我们")){
                ivShow.setVisibility(View.VISIBLE);
            }
        }else {
            textHeadTitle.setText("");
        }
        if (content != null) {
            lsDetails.loadDataWithBaseURL(null, PublicUtils.getNewContent(content), "text/html", "utf-8", null);
        }
        if (url!=null){
            WebSettings webSettings = lsDetails.getSettings();
            webSettings.setJavaScriptEnabled(true);
            lsDetails.loadUrl(url);
        }

        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
