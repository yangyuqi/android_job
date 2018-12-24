package com.youzheng.tongxiang.huntingjob.Prestener.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.MessageDataBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.MessageDataList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.InviteActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.WhoSeeMeActivity;
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
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;
    Unbinder unbinder;

    private int page = 1 ;
    private int rows = 30 ;

    private List<MessageDataBean> data = new ArrayList<>();
    private CommonAdapter<MessageDataBean> adapter ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        if (accessToken.equals("")){
            startActivity(new Intent(mContext,LoginActivity.class));
        }else {
            initData();
        }
        return view;
    }

    public void initData(){
        if (!accessToken.equals("")){
            Map<String,Object> map = new HashMap<>();
            map.put("accessToken",accessToken);
            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.MESSAGE_NEW_LIST, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    sv.onFinishFreshAndLoad();
                    BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)){
                        MessageDataList list = gson.fromJson(gson.toJson(entity.getData()),MessageDataList.class);
                        if (list.getMessage().size()>0){
                            adapter.setData(list.getMessage());
                            adapter.notifyDataSetChanged();
                        }else {
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accessToken.equals("")){
        }else {
            initData();
        }
    }

    private void initView() {
        textHeadTitle.setText("消息");
        adapter = new CommonAdapter<MessageDataBean>(mContext,data,R.layout.ls_message_item) {
            @Override
            public void convert(final ViewHolder helper, final MessageDataBean item) {
                helper.setText(R.id.tv_content,item.getTitle());
                helper.setText(R.id.tv_time,item.getCreateTime());
                helper.setText(R.id.tv_time_content,item.getMessage());
                if (item.getCount()>0){
                    helper.getView(R.id.tv_count).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_count,""+item.getCount());
                }else {
                    helper.getView(R.id.tv_count).setVisibility(View.GONE);
                }

                if (item.getType().equals("seeresume")){
                    helper.setImageResource(R.id.iv_icon,R.mipmap.hh_message);
                }else if (item.getType().equals("deliveryjob")){
                    helper.setImageResource(R.id.iv_icon,R.mipmap.hh_email);
                }

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getType().equals("seeresume")){
                            startActivity(new Intent(mContext, WhoSeeMeActivity.class));
                        }else if (item.getType().equals("deliveryjob")){
                            startActivity(new Intent(mContext, InviteActivity.class));
                        }
                    }
                });

            }
        };
        ls.setAdapter(adapter);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadmore() {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (accessToken.equals("")){
                startActivity(new Intent(mContext,LoginActivity.class));
            }
        }
    }
}
