package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.CollectListBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
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
 * Created by qiuweiyu on 2018/5/15.
 */

public class HrWhoSeeMeActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;

    private List<UserJlBean> data = new ArrayList<>();
    private CommonAdapter<UserJlBean> adapter;

    private int page = 1 , rows = 20 ,allCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_who_see_layout);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        textHeadTitle.setText("浏览过的简历");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<UserJlBean>(mContext,data,R.layout.interview_ls_item) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {
                helper.getView(R.id.rl_no).setVisibility(View.GONE);

                if (item.getLastestCompany()==null){
                    helper.setText(R.id.tv_style, "暂无公司");
                }else {
                    helper.setText(R.id.tv_style, item.getLastestCompany());
                }
                helper.setText(R.id.tv_name, item.getTruename());
                if (item.getGender() != 1) {
                    helper.setText(R.id.tv_sex, "女");
                    ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_24_nv);
                } else {
                    helper.setText(R.id.tv_sex, "男");
                    ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_25_nan);
                }
                helper.setText(R.id.tv_age, item.getBirthdate());
                helper.setText(R.id.tv_work_year, "" + item.getExperience() + "年" + " | " + item.getEducation() + " | " + item.getCitysName());
                helper.setText(R.id.tv_status, item.getStatus());
                helper.setText(R.id.tv_get_job, ""+item.getPosition());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.civ));
                helper.setText(R.id.tv_see_time, item.getActTime()+"更新");
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,HrJianliDetailsActivity.class);
                        intent.putExtra("jid",item.getId());
                        startActivity(intent);
                    }
                });

            }
        };
        ls.setAdapter(adapter);

    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("page",page);
        map.put("rows",rows);
        map.put("time","1");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SCAN_JIANLI, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    CollectListBean jobBean = gson.fromJson(gson.toJson(entity.getData()),CollectListBean.class);
                    if (jobBean.getCount()>0){
                        allCount = jobBean.getCount();
                        if (jobBean.getReadList().size()>0&&jobBean.getReadList().size()<=20){
                            if (page==1){
                                data.clear();
                            }
                            data.addAll(jobBean.getReadList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }else {
                            data.addAll(jobBean.getReadList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        adapter.setData(new ArrayList<UserJlBean>());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initEvent() {
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                initData();
            }

            @Override
            public void onLoadmore() {
                if (allCount>20){
                    if (page*20<allCount){
                        page++;
                        initData();
                    }
                }
            }
        });
    }
}
