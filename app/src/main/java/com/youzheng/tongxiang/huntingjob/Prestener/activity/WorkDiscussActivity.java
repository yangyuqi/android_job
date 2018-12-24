package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HotNewsList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobNewsBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobNewsList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/21.
 */

public class WorkDiscussActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;

    private CommonAdapter<JobNewsBean> adapter ;
    private List<JobNewsBean> data = new ArrayList<>();

    private int page =1 ;
    private int pagesize = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_discuss_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("number",30);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.JOB_NEWS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    HotNewsList list = gson.fromJson(gson.toJson(entity.getData()),HotNewsList.class);
                    adapter.setData(list.getHotnews());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        textHeadTitle.setText("职场干货");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new CommonAdapter<JobNewsBean>(mContext,data,R.layout.work_discuss_ls_item) {
            @Override
            public void convert(ViewHolder helper, final JobNewsBean item) {
                Glide.with(mContext).load(item.getPhoto()).into((ImageView) helper.getView(R.id.iv_logo));
                helper.setText(R.id.tv_name,item.getTitle());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,NewsDetailsActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);
    }
}
