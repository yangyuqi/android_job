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
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HomeBeanList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ReplayFastBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ReplayFastBeanDetails;
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

public class CoListActivity extends BaseActivity {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;
    private List<ReplayFastBeanDetails> new_data = new ArrayList<>();
    private CommonAdapter<ReplayFastBeanDetails> adapter2;

    private List<CoBeanDetails> coBeanDetailses = new ArrayList<>();
    private CommonAdapter<CoBeanDetails> co_adapter ;

    private String title, type;
    private int page = 1, rows = 30,layoutId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.co_list_layout);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        initView();

        initData();
    }

    private void initData() {
        if (!title.equals("公司推荐")) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("flag", type);
            map1.put("page", page);
            map1.put("rows", rows);
            OkHttpClientManager.postAsynJson(gson.toJson(map1), UrlUtis.HOME_INFO, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    sv.onFinishFreshAndLoad();
                }

                @Override
                public void onResponse(String response) {
                    sv.onFinishFreshAndLoad();
                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        ReplayFastBean fastBean = gson.fromJson(gson.toJson(entity.getData()), ReplayFastBean.class);
                        if (fastBean.getJobList().size() > 0) {
                            adapter2.setData(fastBean.getJobList());
                            adapter2.notifyDataSetChanged();
                        }
                    }
                }
            });
        }else {
            Map<String, Object> map3 = new HashMap<>();
            map3.put("accessToken", accessToken);
            map3.put("page",page);
            map3.put("rows",rows);
            OkHttpClientManager.postAsynJson(gson.toJson(map3), UrlUtis.HOME_INTRODUCE, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    sv.onFinishFreshAndLoad();
                }

                @Override
                public void onResponse(String response) {
                    sv.onFinishFreshAndLoad();
                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        final HomeBeanList list = gson.fromJson(gson.toJson(entity.getData()), HomeBeanList.class);
                        if (list.getComList().size()>0){
                            co_adapter.setData(list.getComList());
                            co_adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
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
        if (type.equals("name_brand_company")){
            layoutId=R.layout.home_page_ls_item;
        }else if (type.equals("replay_fast")){
            layoutId = R.layout.home_page_delay_ls_item;
        }
        adapter2 = new CommonAdapter<ReplayFastBeanDetails>(mContext, new_data,layoutId) {
            @Override
            public void convert(ViewHolder helper, final ReplayFastBeanDetails item) {
                if (!type.equals("name_brand_company")) {
                    helper.setText(R.id.tv_co, item.getName());
                    helper.setText(R.id.tv_intro, item.getSummary());
                    Glide.with(mContext).load(item.getLogo()).placeholder(R.mipmap.youyuzhanweicopy2).into(((ImageView) helper.getView(R.id.iv_icon)));
                    helper.setText(R.id.tv_product, item.getTradeName());
                    helper.setText(R.id.tv_num, item.getScaleName());
                    helper.setText(R.id.tv_pace, item.getCitysName());

                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, IntroduceCoActivity.class);
                            intent.putExtra("id", item.getId());
                            mContext.startActivity(intent);
                        }
                    });
                }else {
                    helper.setText(R.id.tv_name, item.getTitle());
                    helper.setText(R.id.tv_level_l, item.getEducation()+"/"+item.getCity());
                    Glide.with(mContext).load(item.getCom_logo()).placeholder(R.mipmap.youyuzhanweicopy2).into(((ImageView) helper.getView(R.id.iv_logo)));
                    helper.setText(R.id.tv_name_co, item.getName());
                    helper.setText(R.id.tv_below, item.getJobtag());
                    helper.setText(R.id.tv_update_time,item.getCreate_time());
                    if (item.getWage_face() == 0) {
                        helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                    } else {
                        helper.setText(R.id.tv_money, "面议");
                    }
                    String[] strings = item.getJobtag().split(",");
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0 ;i <strings.length;i++){
                        arrayList.add(strings[i]);
                    }
                    LabelsView labelsView = helper.getView(R.id.labelView);
                    labelsView.setLabels(arrayList);
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, IntroduceCoActivity.class);
                            intent.putExtra("id", item.getCom_id());
                            mContext.startActivity(intent);
                        }
                    });
                }
            }
        };

        co_adapter = new CommonAdapter<CoBeanDetails>(mContext,coBeanDetailses,R.layout.home_page_delay_ls_item) {
            @Override
            public void convert(ViewHolder helper, final CoBeanDetails item) {
                helper.setText(R.id.tv_co, item.getName());
                helper.setText(R.id.tv_intro, item.getSummary());
                Glide.with(mContext).load(item.getCom_logo()).placeholder(R.mipmap.youyuzhanweicopy2).into(((ImageView) helper.getView(R.id.iv_icon)));
                helper.setText(R.id.tv_product, item.getTradeName());
                helper.setText(R.id.tv_num, item.getScale());
                helper.setText(R.id.tv_pace, item.getCity());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, IntroduceCoActivity.class);
                        intent.putExtra("id", item.getCom_id());
                        mContext.startActivity(intent);
                    }
                });
            }
        };

        if (!title.equals("公司推荐")){
            ls.setAdapter(adapter2);
        }else {
            ls.setAdapter(co_adapter);
        }

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                initData();
            }

            @Override
            public void onLoadmore() {
                page++;
                initData();
            }
        });
    }
}
