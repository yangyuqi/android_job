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
import com.youzheng.tongxiang.huntingjob.Model.entity.user.BlackCoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.BlackCompanyList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
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
 * Created by qiuweiyu on 2018/5/8.
 */

public class ShieldCoActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.rl)
    RelativeLayout rl;
    List<BlackCoBean> data = new ArrayList<>();
    private CommonAdapter<BlackCoBean> adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shield_co_layout);
        ButterKnife.bind(this);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("rid",rid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ALL_BLACK, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    BlackCompanyList list = gson.fromJson(gson.toJson(entity.getData()),BlackCompanyList.class);
                    if (list.getBlackcompany().size()>0) {
                        tvNum.setText(""+list.getBlackcompany().size());
                        adapter.setData(list.getBlackcompany());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        textHeadTitle.setText("屏蔽公司");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<BlackCoBean>(mContext,data,R.layout.shield_ls_item) {
            @Override
            public void convert(ViewHolder helper, final BlackCoBean item) {
                helper.setText(R.id.tv_co,item.getName());
                helper.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("rid",rid);
                        map.put("cid",item.getCid());
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.DELETE_BLACK_CO, new OkHttpClientManager.StringCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                    initData();
                                }
                            }
                        });
                    }
                });
            }
        };

        ls.setAdapter(adapter);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,ShieldSearchCoActivity.class));
            }
        });
    }
}
