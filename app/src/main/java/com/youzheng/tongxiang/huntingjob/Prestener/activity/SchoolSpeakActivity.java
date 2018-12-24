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
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewCareerBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewCareerList;
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

public class SchoolSpeakActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;
    private List<NewCareerBean> data = new ArrayList<>();
    private CommonAdapter<NewCareerBean> adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_speak_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        textHeadTitle.setText("校园宣讲会");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new CommonAdapter<NewCareerBean>(mContext,data,R.layout.speak_ls_item) {
            @Override
            public void convert(ViewHolder helper, final NewCareerBean item) {
                helper.setText(R.id.tv_school_speak,item.getTitle());
                helper.setText(R.id.tv_school_name,item.getSchool());
                helper.setText(R.id.tv_school_address,"地点 :"+item.getPlace());
                helper.setText(R.id.tv_school_time,"时间 :"+item.getOpen_date());
                Glide.with(mContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.iv_speak));
                helper.setText(R.id.tv_data,item.getCreate_date());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,SchoolSpeakDetailsActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("number",20);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SCHOOL_SPEAK, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    NewCareerList list = gson.fromJson(gson.toJson(entity.getData()),NewCareerList.class);
                    if (list.getNewcareer().size()>0){
                            adapter.setData(list.getNewcareer());
                            adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
