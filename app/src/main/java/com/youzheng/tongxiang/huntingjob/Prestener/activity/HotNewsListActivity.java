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
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewsBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewsBeanList;
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

public class HotNewsListActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;

    private int id ;
    private String title ;
    private int page = 1 ;
    private int pagesize = 20 ;

    private List<NewsBeanDetails> data = new ArrayList<>();
    private CommonAdapter<NewsBeanDetails> adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_news_list_layout);
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

        title = getIntent().getStringExtra("title");
        textHeadTitle.setText(title);

        adapter = new CommonAdapter<NewsBeanDetails>(mContext,data,R.layout.hot_news_ls_item) {
            @Override
            public void convert(ViewHolder helper, final NewsBeanDetails item) {
                helper.setText(R.id.tv_name,item.getTitle());
                helper.setText(R.id.tv_below,"时间 : "+item.getCreate_time()+"    查看 : "+item.getRead_count());
                Glide.with(mContext).load(item.getPhoto()).into((ImageView) helper.getView(R.id.iv_logo));
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,NewsDetailsActivity.class);
                        intent.putExtra("id",Integer.parseInt(item.getId()));
                        intent.putExtra("cid",item.getCid());
                        startActivity(intent);
                    }
                });
            }
        };

        ls.setAdapter(adapter);

        id = getIntent().getIntExtra("id",0);
        Map<String,Object> map = new HashMap<>();
        map.put("cid",id);
        map.put("page",page);
        map.put("pagesize",pagesize);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.GET_NEWS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    NewsBeanList list = gson.fromJson(gson.toJson(entity.getData()),NewsBeanList.class);
                    adapter.setData(list.getNews());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
