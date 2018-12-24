package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.MycareerData;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewCareerBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.SchoolSpeakDetailsActivity;
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
 * Created by qiuweiyu on 2018/2/11.
 */

public class SpeakActivity extends BaseActivity {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.ls)
    ListView ls;

    private List<NewCareerBean> data = new ArrayList<>();
    private CommonAdapter<NewCareerBean> adapter ;

    private int page = 1 ;
    private int pagesize = 20 ;
    Map<String,Object> map = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speak_activity_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        map.put("page",page);
        map.put("pagesize",pagesize);
        map.put("key","nostart");
        map.put("token",accessToken);
        cateRequest(map);
    }

    public void cateRequest(Object o){
        OkHttpClientManager.postAsynJson(gson.toJson(o), UrlUtis.GET_MY_SPEAK, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    MycareerData mycareerData = gson.fromJson(gson.toJson(entity.getData()),MycareerData.class);
                    adapter.setData(mycareerData.getMycareer());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initEvent() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    map.put("key","nostart");
                }else {
                    map.put("key","started");
                }
                cateRequest(map);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        textHeadTitle.setText("宣讲会");
        btnBack.setVisibility(View.VISIBLE);

        adapter = new CommonAdapter<NewCareerBean>(mContext,data,R.layout.speak_ls_item) {
            @Override
            public void convert(ViewHolder helper, final NewCareerBean item) {
                Glide.with(mContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.iv_speak));
                helper.setText(R.id.tv_school_speak,item.getTitle());
                helper.setText(R.id.tv_school_name,item.getSchool());
                helper.setText(R.id.tv_school_address,"地点 :"+item.getPlace());
                helper.setText(R.id.tv_school_time,"时间 :"+item.getOpen_date());
                helper.setText(R.id.tv_data,item.getCreate_date());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,SchoolSpeakDetailsActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    }
                });            }
        };
        ls.setAdapter(adapter);

    }


}
