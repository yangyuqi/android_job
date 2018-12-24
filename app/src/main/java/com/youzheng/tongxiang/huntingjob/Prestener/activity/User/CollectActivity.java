package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CollectJobBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.IntroduceJobActivity;
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

public class CollectActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;

    private CommonAdapter<JobBeanDetails> adapter ;
    private List<JobBeanDetails> data = new ArrayList<>();

    private int page = 1;
    private int rows = 30 ;
    private int count ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_activity_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("page",page);
        map.put("rows",rows);
        map.put("accessToken",accessToken);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SHOUCAN_URL, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                    BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                   if (entity.getCode().equals(PublicUtils.SUCCESS)){
                       CollectJobBean jobBean = gson.fromJson(gson.toJson(entity.getData()),CollectJobBean.class);
                        count = jobBean.getCount();
                       adapter.setData(jobBean.getCollectList());
                       adapter.notifyDataSetChanged();
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
        textHeadTitle.setText("收藏夹");

        adapter = new CommonAdapter<JobBeanDetails>(mContext,data,R.layout.collect_ls_item) {
            @Override
            public void convert(ViewHolder helper, final JobBeanDetails item) {
                helper.setText(R.id.tv_name,item.getTitle());
                if (item.getWage_face()==0) {
                    helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                }else {
                    helper.setText(R.id.tv_money,"面议");
                }
                helper.setText(R.id.tv_class,item.getEducation()+"/"+item.getCity());
                helper.setText(R.id.tv_name_co,item.getName());
                helper.setText(R.id.tv_below,item.getJobtag());
                helper.setText(R.id.tv_time,item.getCreate_time());
                ImageView imageView = helper.getView(R.id.iv_logo);
                Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.youyuzhanweicopy2).into(imageView);

                if (item.getIs_delivery() == 0) {
                    ((TextView)helper.getView(R.id.tv_tou)).setText("投递简历");
                    helper.getView(R.id.tv_tou).setBackgroundResource(R.mipmap.toudi1);
                } else {
                    ((TextView)helper.getView(R.id.tv_tou)).setText("已投递");
                    helper.getView(R.id.tv_tou).setBackgroundResource(R.color.text_gray);
                }

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, IntroduceJobActivity.class);
                        intent.putExtra("id",item.getId());
                        mContext.startActivity(intent);
                    }
                });

                ArrayList<String> arrayList = new ArrayList<>();
                LabelsView labelsView = helper.getView(R.id.labelView);
                if (item.getJobtag()!=null) {
                    String[] strings = item.getJobtag().split(",");
                    for (int i = 0; i < strings.length; i++) {
                        arrayList.add(strings[i]);
                    }
                }
                labelsView.setLabels(arrayList);

                helper.getView(R.id.tv_tou).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getIs_delivery()==1){
                            showToast("您已投递该职位");
                        }else if (item.getIs_delivery()==0){
                            Map<String,Object> map = new HashMap<>();
                            map.put("jid",item.getId());
                            map.put("rid",rid);
                            map.put("uid",uid);
                            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.TOUJIANLI_JOB, new OkHttpClientManager.StringCallback() {
                                @Override
                                public void onFailure(Request request, IOException e) {

                                }

                                @Override
                                public void onResponse(String response) {
                                    BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                    showToast(entity.getMsg());
                                    initData();
                                }
                            });
                        }
                    }
                });

                helper.getView(R.id.tv_un_scribe).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("jid",item.getId());
                        map.put("uid",uid);
                        map.put("ctype","1");
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UNSCRIBE_JOB, new OkHttpClientManager.StringCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                    initData();
                                }else {
                                    showToast(entity.getMsg());
                                }
                            }
                        });
                    }
                });

            }
        };

        ls.setAdapter(adapter);
    }
}
