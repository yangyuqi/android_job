package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.HelpRebackBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.HelpRebackData;
import com.youzheng.tongxiang.huntingjob.Model.Hr.RebackDetailsBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.RebackDetailsBeanData;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.H5Activity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2018/5/15.
 */

public class HelpAndRebackActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.rl_reback)
    RelativeLayout rlReback;
    @BindView(R.id.rl_reback1)
    RelativeLayout rlReback1;

    private String type ;

    private List<HelpRebackBean> list_data = new ArrayList<>();
    private CommonAdapter<HelpRebackBean> adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_and_reback_layout);
        ButterKnife.bind(this);
        initView();
        type = getIntent().getStringExtra("type");
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.HELP_AND_REBACK, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    HelpRebackData data = gson.fromJson(gson.toJson(entity.getData()),HelpRebackData.class);
                    if (data.getData().size()>0){
                        adapter.setData(data.getData());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initView() {
        textHeadTitle.setText("帮助与反馈");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<HelpRebackBean>(mContext,list_data,R.layout.help_ls_item) {
            @Override
            public void convert(ViewHolder helper, final HelpRebackBean item) {
                    helper.setText(R.id.tv_title,item.getTitle());
                    helper.getView(R.id.tv_title).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String,Object> map = new HashMap<>();
                            map.put("id",""+item.getId());
                            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.REBACK_DETAILS, new OkHttpClientManager.StringCallback() {
                                @Override
                                public void onFailure(Request request, IOException e) {

                                }

                                @Override
                                public void onResponse(String response) {
                                    BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                    if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                        RebackDetailsBeanData detailsBeanData = gson.fromJson(gson.toJson(entity.getData()),RebackDetailsBeanData.class);
                                        RebackDetailsBean detailsBean = detailsBeanData.getDate();
                                        Intent intent = new Intent(mContext, H5Activity.class);
                                        intent.putExtra("title",detailsBean.getTitle());
                                        intent.putExtra("content",detailsBean.getContent());
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    });
            }
        };
        ls.setAdapter(adapter);

        rlReback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext,MyRebackActivity.class));
            }
        });

        rlReback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext,MyRebackListActivity.class));
            }
        });
    }

}
