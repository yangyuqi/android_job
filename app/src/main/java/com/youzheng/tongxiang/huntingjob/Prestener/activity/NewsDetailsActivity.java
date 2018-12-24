package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.Newinfo;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/21.
 */

public class NewsDetailsActivity extends BaseActivity {


    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.ls_details)
    WebView lsDetails;
    @BindView(R.id.ll_top)
    LinearLayout llTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadTitle.setText("资讯");

        Map<String, Object> map = new HashMap<>();
        int id = getIntent().getIntExtra("id", 0);
        String cid = getIntent().getStringExtra("cid");
        String url;
        if (cid != null) {
            map.put("cid", Integer.parseInt(cid));
            map.put("id", id);
            url = "http://101.132.125.42:8787/txyp_news.html?id=" + String.valueOf(id) + "&cid=" + String.valueOf(cid);
            llTop.setVisibility(View.GONE);
            WebSettings webSettings = lsDetails.getSettings();
            webSettings.setJavaScriptEnabled(true);
            lsDetails.loadUrl(url);
        } else {
            map.put("id", id);
            url = UrlUtis.NEWS_DETAILS;
            OkHttpClientManager.postAsynJson(gson.toJson(map), url, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        Newinfo newinfo = gson.fromJson(gson.toJson(entity.getData()), Newinfo.class);
                        tvTitle.setText(newinfo.getNewinfo().getTitle());
                        tvTime.setText("时间 : " + newinfo.getNewinfo().getDate());
                        tvNum.setText("查看 : " + newinfo.getNewinfo().getLooktime());
                        lsDetails.loadDataWithBaseURL(null, getNewContent(newinfo.getNewinfo().getContext()), "text/html", "utf-8", null);
                    }
                }
            });
        }
    }

    private String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
