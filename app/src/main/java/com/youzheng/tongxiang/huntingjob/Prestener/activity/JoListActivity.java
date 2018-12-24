package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.GoodsCoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.GoodsCoBeanDetails;
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
 * Created by qiuweiyu on 2018/2/22.
 */

public class JoListActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;

    private int page = 1;
    private int rows = 20;

    private CommonAdapter<GoodsCoBeanDetails> adapter;
    private List<GoodsCoBeanDetails> data = new ArrayList<>();

    private String title, type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_list_layout);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        initView();
        initData();

    }

    private void initView() {
        if (title != null) {
            textHeadTitle.setText(title);
        }
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<GoodsCoBeanDetails>(mContext, data, R.layout.home_page_ls_item) {
            @Override
            public void convert(ViewHolder helper, final GoodsCoBeanDetails item) {
                helper.setText(R.id.tv_name, item.getTitle());
                if (item.getEdition()==null) {
                    helper.setText(R.id.tv_level_l, item.getEducation() + "/" + item.getCitysName());
                }else {
                    helper.setText(R.id.tv_level_l, item.getEdition() + "/" + item.getCitysName());
                }
                helper.setText(R.id.tv_name_co, item.getName());
                helper.setText(R.id.tv_below, item.getTradeName());
                ImageView imageView = helper.getView(R.id.iv_logo);
                Glide.with(mContext).load(item.getCom_logo()).placeholder(R.mipmap.youyuzhanweicopy2).into(imageView);

                if (item.getWage_face() == 0) {
                    helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                } else {
                    helper.setText(R.id.tv_money, "面议");
                }

                ArrayList<String> arrayList = new ArrayList<>();
                LabelsView labelsView = helper.getView(R.id.labelView);
                if (item.getJobtag()!=null) {
                    String[] strings = item.getJobtag().split(",");
                    for (int i = 0; i < strings.length; i++) {
                        arrayList.add(strings[i]);
                    }
                }
                labelsView.setLabels(arrayList);

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, IntroduceJobActivity.class);
                        intent.putExtra("id", item.getId());
                        startActivity(intent);
                    }
                });
                if (item.getVideoUpdated()==1){
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                }
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
                page++;
                initData();
            }
        });
    }

    private void initData() {
        Map<String, Object> map2 = new HashMap<>();
        map2.put("flag", type);
        map2.put("page", page);
        map2.put("rows", rows);
        OkHttpClientManager.postAsynJson(gson.toJson(map2), UrlUtis.HOME_INFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    GoodsCoBean coBean = gson.fromJson(gson.toJson(entity.getData()), GoodsCoBean.class);
                    if (coBean.getJobList().size() > 0) {
                        adapter.setData(coBean.getJobList());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
