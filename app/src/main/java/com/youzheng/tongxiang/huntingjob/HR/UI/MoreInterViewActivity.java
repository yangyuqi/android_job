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
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBeanData;
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
 * Created by qiuweiyu on 2018/5/23.
 */

public class MoreInterViewActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;

    private List<UserJlBean> data = new ArrayList<>();
    private CommonAdapter<UserJlBean> adapter;

    private int page = 1, rows = 20, allCount;

    private String type, title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_interview_layout);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        textHeadTitle.setText(title);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<UserJlBean>(mContext, data, R.layout.hr_home_ls_item) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {
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
                helper.setText(R.id.tv_work_year, "" + item.getWork_year() + "年" + " | " + item.getEducation() + " | " + item.getCitysName());
                helper.setText(R.id.tv_status, item.getStatus());
                helper.setText(R.id.tv_get_job, ""+item.getPosition());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.civ));
                helper.setText(R.id.tv_see_time, item.getCreate_time()+"更新");
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,HrJianliDetailsActivity.class);
                        intent.putExtra("jid",item.getId());
                        startActivity(intent);
                    }
                });
//                if (item.getVideoUpdated()!=null) {
                    if (item.getVideoUpdated() == 1) {
                        helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                    }else {
                        helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
                    }
//                }
            }
        };
        ls.setAdapter(adapter);

    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        String url = null ;
        if (!type.equals("resume")) {
            map.put("uid", uid);
            map.put("page", page);
            map.put("rows", rows);
            map.put("flag", type);
            url = UrlUtis.CO_HOME;
        }else {
            int id = getIntent().getIntExtra("jobs_nature",0);
            map.put("type",type);
            map.put("jobs_nature",id);
            map.put("page", page);
            map.put("rows", rows);
            url = UrlUtis.SCHOOL_JOB;
        }
        OkHttpClientManager.postAsynJson(gson.toJson(map), url, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    UserJlBeanData jobBean = gson.fromJson(gson.toJson(entity.getData()), UserJlBeanData.class);
                    if (jobBean.getCount() > 0) {
                        allCount = jobBean.getCount();
                        if (jobBean.getResumeList().size() > 0 && jobBean.getResumeList().size() <= 20) {
                            if (page == 1) {
                                data.clear();
                            }
                            data.addAll(jobBean.getResumeList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        } else {
                            data.addAll(jobBean.getResumeList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
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
                page = 1;
                initData();
            }

            @Override
            public void onLoadmore() {
                if (allCount > 20) {
                    if (page * 20 < allCount) {
                        page++;
                        initData();
                    }
                }
            }
        });
    }
}
