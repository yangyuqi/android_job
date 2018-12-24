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
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.GovernmentBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.GovernmentList;
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

public class GovernmentActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls_)
    ListView ls;

    private CommonAdapter<GovernmentBean> adapter ;
    private List<GovernmentBean> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.government_layout);
        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("number",20);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.JOB_NEWS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    GovernmentList list = gson.fromJson(gson.toJson(entity.getData()),GovernmentList.class);
                    if (list.getHotnews().size()>0){
                        adapter.setData(list.getHotnews());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        textHeadTitle.setText("政府公告");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<GovernmentBean>(mContext,data,R.layout.government_ls_item) {
            @Override
            public void convert(ViewHolder helper, final GovernmentBean item) {
                helper.setText(R.id.tv_name,item.getTitle());
                helper.setText(R.id.tv_below,"时间 : "+item.getCreate_time()+"    查看 : "+item.getRead_count());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.iv_icon));
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,H5Activity.class);
                        intent.putExtra("title",item.getTitle());
                        intent.putExtra("url","http://101.132.125.42:8787/txyp_jobnews.html?id="+item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);
    }
}
