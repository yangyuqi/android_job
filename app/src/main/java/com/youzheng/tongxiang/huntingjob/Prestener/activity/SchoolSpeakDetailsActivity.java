package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.SpeakBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/21.
 */

public class SchoolSpeakDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_co_name)
    TextView tvCoName;
    @BindView(R.id.tv_ctype)
    TextView tvCtype;
    @BindView(R.id.tv_businness)
    TextView tvBusinness;
    @BindView(R.id.tv_speak)
    TextView tvSpeak;
    @BindView(R.id.rl_bg)
    RelativeLayout rlBg;
    @BindView(R.id.web)
    WebView web;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_speak_details_layout);
        ButterKnife.bind(this);

        initData();

        initEvent();
    }

    private void initEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rlBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }

                Map<String, Object> map = new HashMap<>();
                map.put("token", accessToken);
                map.put("career_id", id);
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SPEAK_GO, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        showToast(entity.getMsg());
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            initData();
                        }
                    }
                });
            }
        });
    }

    private void initData() {
        id = getIntent().getIntExtra("id", 0);
        Map<String, Object> map = new HashMap<>();
        map.put("token", accessToken);
        map.put("career_id", id);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SCHOOL_APEAK_DETAIL, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    SpeakBean bean = gson.fromJson(gson.toJson(entity.getData()), SpeakBean.class);
                    tvCoName.setText(bean.getAllinfo().getTitle());
                    tvCtype.setText("时间 :" + bean.getAllinfo().getOpen_date());
                    tvBusinness.setText("地点 :" + bean.getAllinfo().getPlace());
                    Glide.with(mContext).load(bean.getAllinfo().getLogo()).error(R.mipmap.gongsixinxi).into(ivLogo);
                    web.loadDataWithBaseURL(null, PublicUtils.getNewContent(bean.getAllinfo().getCareer_info()),"text/html","utf-8",null);

                    if (bean.getAllinfo().getOuttime().equals("0")){
                        tvSpeak.setText("我要报名");
                        rlBg.setBackgroundResource(R.drawable.yellow_bg);
                    }else if (bean.getAllinfo().getOuttime().equals("1")){
                        rlBg.setBackgroundResource(R.drawable.bg_blue_deeper);
                        rlBg.setEnabled(false);
                        return;
                    }

                    if (bean.getAllinfo().getEnrollinfo().equals("1")) {
                        tvSpeak.setText("已报名");
                        rlBg.setBackgroundResource(R.drawable.bg_blue_deeper);
                    } else if (bean.getAllinfo().getEnrollinfo().equals("0")) {
                        tvSpeak.setText("我要报名");
                        rlBg.setBackgroundResource(R.drawable.yellow_bg);
                    }
                }
            }
        });
    }
}
