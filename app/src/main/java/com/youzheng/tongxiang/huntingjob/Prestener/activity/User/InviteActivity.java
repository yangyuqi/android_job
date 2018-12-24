package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

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
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.DeliverBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.DeliverBeanList;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.InvitationList;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.InvitationListBeam;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.IntroduceCoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.IntroduceJobActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;

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

public class InviteActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;

    private int page = 1 ,rows = 50 ;

    private CommonAdapter<DeliverBean> adapter ;
    private List<DeliverBean> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_layout);
        ButterKnife.bind(this);
        initView();

        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("page",page);
        map.put("rows",rows);
        map.put("status","3");
        map.put("accessToken",accessToken);
        map.put("type",2);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.DELIEVERY_MSG_LIST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    InvitationList list = gson.fromJson(gson.toJson(entity.getData()),InvitationList.class);
                    if (list!=null){
                        adapter.setData(list.getDeliveryList());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadTitle.setText("邀请函");

        adapter = new CommonAdapter<DeliverBean>(mContext,data,R.layout.invite_ls_item) {
            @Override
            public void convert(ViewHolder helper,  final DeliverBean item) {
                helper.setText(R.id.tv_name,item.getTitle());
                helper.setText(R.id.tv_level_l,item.getEducation()+" | "+item.getExperience()+" | "+item.getCity());
                helper.setText(R.id.tv_name_co,item.getName());
                helper.setText(R.id.tv_below,item.getTradeName());
                helper.setText(R.id.tv_time,item.getCreate_time());
                CircleImageView imageView = helper.getView(R.id.iv_logo);
                helper.setText(R.id.tv_belowss,"面试时间 : "+item.getInterview_time());
                Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.youyuzhanweicopy2).into(imageView);
                helper.getView(R.id.tv_money).setVisibility(View.GONE);
                if (item.getWage_face()==0) {
                    helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                }else {
                    helper.setText(R.id.tv_money,"面议");
                }
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, IntroduceCoActivity.class);
                        intent.putExtra("id",item.getCom_id());
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);
    }
}
