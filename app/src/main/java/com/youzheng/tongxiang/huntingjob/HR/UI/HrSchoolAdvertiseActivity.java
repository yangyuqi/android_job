package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBeanData;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HotNewsList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobNewsBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewCareerBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewCareerList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.SchoolBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.BannerList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.NewsDetailsActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.SchoolSpeakActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.SchoolSpeakDetailsActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.SearchJobActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.WorkDiscussActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.BannerNormalAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.NoScrollListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2018/5/24.
 */

public class HrSchoolAdvertiseActivity extends BaseActivity {

    @BindView(R.id.roll_view)
    RollPagerView rollView;
    @BindView(R.id.ls)
    NoScrollListView ls;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.top_header_ll_right_iv_caidan)
    ImageView topHeaderLlRightIvCaidan;
    @BindView(R.id.layout_header)
    RelativeLayout layoutHeader;
    @BindView(R.id.ls_two)
    NoScrollListView lsTwo;
    @BindView(R.id.tv_yinjie)
    TextView tvYinjie;
    @BindView(R.id.tv_shixi)
    TextView tvShixi;
    @BindView(R.id.tv_jianzhi)
    TextView tvJianzhi;
    @BindView(R.id.tv_xuanjianghui)
    TextView tvXuanjianghui;
    @BindView(R.id.tv_console)
    TextView tvConsole;
    @BindView(R.id.ls_three)
    NoScrollListView lsThree;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.tv_more2)
    TextView tvMore2;
    @BindView(R.id.tv_more4)
    TextView tvMore4;
    @BindView(R.id.ls_four)
    NoScrollListView lsFour;
    @BindView(R.id.tv_more5)
    TextView tvMore5;
    @BindView(R.id.ls_five)
    NoScrollListView lsFive;
    @BindView(R.id.tv_more3)
    TextView tvMore3;

    List<UserJlBean> data = new ArrayList<>();
    CommonAdapter<UserJlBean> adapter ,adapter1,adapter2;

    private CommonAdapter<NewCareerBean> adapter_three;
    private List<NewCareerBean> data_three = new ArrayList<>();

    private CommonAdapter<JobNewsBean> adapter_five;
    private List<JobNewsBean> data_five = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_school_advertise_layout);
        ButterKnife.bind(this);
        layoutHeader.setBackgroundResource(R.drawable.main_color);
        textHeadTitle.setText("校园招聘会");
        btnBack.setVisibility(View.VISIBLE);
        topHeaderLlRightIvCaidan.setImageResource(R.mipmap.sousuo2);
        topHeaderLlRightIvCaidan.setVisibility(View.VISIBLE);
        initView();
        initData();
    }

    private void initView() {
        adapter_three = new CommonAdapter<NewCareerBean>(mContext, data_three, R.layout.speak_ls_item) {
            @Override
            public void convert(ViewHolder helper, final NewCareerBean item) {
                helper.setText(R.id.tv_school_speak, item.getTitle());
                helper.setText(R.id.tv_school_name, item.getSchool());
                helper.setText(R.id.tv_school_address, "地点 :" + item.getPlace());
                helper.setText(R.id.tv_school_time, "时间 :" + item.getOpen_date());
                helper.setText(R.id.tv_data, item.getCreate_date());
                Glide.with(mContext).load(item.getLogo()).error(R.mipmap.gongsixinxi).into((ImageView) helper.getView(R.id.iv_speak));
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, SchoolSpeakDetailsActivity.class);
                        intent.putExtra("id", item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        lsThree.setAdapter(adapter_three);

        adapter_five = new CommonAdapter<JobNewsBean>(mContext, data_five, R.layout.work_discuss_ls_item) {
            @Override
            public void convert(ViewHolder helper, final JobNewsBean item) {
                Glide.with(mContext).load(item.getPhoto()).into((ImageView) helper.getView(R.id.iv_logo));
                helper.setText(R.id.tv_name, item.getTitle());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                        intent.putExtra("id", item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        lsFive.setAdapter(adapter_five);

        adapter = new CommonAdapter<UserJlBean>(mContext,data,R.layout.hr_home_ls_item) {
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
                if (item.getVideoUpdated()==1){
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                }else {
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
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
            }
        };

        ls.setAdapter(adapter);

        adapter2 = new CommonAdapter<UserJlBean>(mContext,data,R.layout.hr_home_ls_item) {
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
                if (item.getVideoUpdated()==1){
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                }else {
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
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
            }
        };

        lsFour.setAdapter(adapter2);

        adapter1 = new CommonAdapter<UserJlBean>(mContext,data,R.layout.hr_home_ls_item) {
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
                if (item.getVideoUpdated()==1){
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                }else {
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
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
            }
        };

        lsTwo.setAdapter(adapter1);

        ls.setFocusable(false);
        lsTwo.setFocusable(false);
        lsThree.setFocusable(false);
        lsFour.setFocusable(false);
        lsFive.setFocusable(false);
    }

    private void initData() {
        Map<String, Object> map5 = new HashMap<>();
        map5.put("showType", "app");
        map5.put("placement", "campus");
        OkHttpClientManager.postAsynJson(gson.toJson(map5), UrlUtis.QUERY_BANNER, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    BannerList list = gson.fromJson(gson.toJson(entity.getData()), BannerList.class);
                    if (list.getBanner().size() > 0) {
                        rollView.setAdapter(new BannerNormalAdapter(list.getBanner(),accessToken));
                    }
                }
            }
        });

        Map<String, Object> map6 = new HashMap<>();
        map6.put("number", 30);
        OkHttpClientManager.postAsynJson(gson.toJson(map6), UrlUtis.JOB_NEWS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    HotNewsList list = gson.fromJson(gson.toJson(entity.getData()), HotNewsList.class);
                    adapter_five.setData(list.getHotnews());
                    adapter_five.notifyDataSetChanged();
                }
            }
        });

        Map<String, Object> map2 = new HashMap<>();
        map2.put("number", 20);
        OkHttpClientManager.postAsynJson(gson.toJson(map2), UrlUtis.SCHOOL_SPEAK, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    NewCareerList list = gson.fromJson(gson.toJson(entity.getData()), NewCareerList.class);
                    if (list.getNewcareer().size() > 0) {
                        if (list.getNewcareer().size() >= 5) {
                            adapter_three.setData(list.getNewcareer().subList(0, 5));
                        } else {
                            adapter_three.setData(list.getNewcareer());
                        }
                        adapter_three.notifyDataSetChanged();
                    }
                }
            }
        });

        Map<String,Object> map = new HashMap<>();
        map.put("jobs_nature",162);
        map.put("type","resume");
        map.put("page",1);
        map.put("rows",5);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SCHOOL_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    UserJlBeanData userJlBeanData = gson.fromJson(gson.toJson(entity.getData()),UserJlBeanData.class);
                    if (userJlBeanData.getResumeList().size()>0) {
                        if (userJlBeanData.getResumeList().size()>5){
                            adapter.setData(userJlBeanData.getResumeList().subList(0,5));
                        }else {
                            adapter.setData(userJlBeanData.getResumeList());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        Map<String,Object> map1 = new HashMap<>();
        map1.put("jobs_nature",63);
        map1.put("type","resume");
        map1.put("page",1);
        map1.put("rows",5);
        OkHttpClientManager.postAsynJson(gson.toJson(map1), UrlUtis.SCHOOL_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    UserJlBeanData userJlBeanData = gson.fromJson(gson.toJson(entity.getData()),UserJlBeanData.class);
                    if (userJlBeanData.getResumeList().size()>0) {
                        if (userJlBeanData.getResumeList().size()>5){
                            adapter1.setData(userJlBeanData.getResumeList().subList(0,5));
                        }else {
                            adapter1.setData(userJlBeanData.getResumeList());
                        };
                        adapter1.notifyDataSetChanged();
                    }
                }
            }
        });

        Map<String,Object> map3 = new HashMap<>();
        map3.put("jobs_nature",62);
        map3.put("type","resume");
        map3.put("page",1);
        map3.put("rows",5);
        OkHttpClientManager.postAsynJson(gson.toJson(map3), UrlUtis.SCHOOL_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    UserJlBeanData userJlBeanData = gson.fromJson(gson.toJson(entity.getData()),UserJlBeanData.class);
                    if (userJlBeanData.getResumeList().size()>0) {
                        if (userJlBeanData.getResumeList().size()>5){
                            adapter2.setData(userJlBeanData.getResumeList().subList(0,5));
                        }else {
                            adapter2.setData(userJlBeanData.getResumeList());
                        }
                        adapter2.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    @OnClick({R.id.tv_xuanjianghui,R.id.tv_console,R.id.btnBack, R.id.top_header_ll_right_iv_caidan,R.id.tv_more,R.id.tv_more2,R.id.tv_more4,R.id.tv_more3,R.id.tv_more5,R.id.tv_yinjie,R.id.tv_shixi,R.id.tv_jianzhi})
    public void OnClick(View view){
        Intent intent = null ;
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.top_header_ll_right_iv_caidan:
                startActivity(new Intent(mContext, HrSearchResultActivity.class));
                break;
            case R.id.tv_xuanjianghui :
                startActivity(new Intent(mContext, SchoolSpeakActivity.class));
                break;
            case R.id.tv_console:
                startActivity(new Intent(mContext, WorkDiscussActivity.class));
                break;
            case R.id.tv_more:
            case R.id.tv_yinjie:
                intent = new Intent(mContext, MoreInterViewActivity.class);
                intent.putExtra("type","resume");
                intent.putExtra("title","应届生专区");
                intent.putExtra("jobs_nature",162);
                startActivity(intent);
                break;
            case R.id.tv_more2:
            case R.id.tv_shixi :
                intent = new Intent(mContext, MoreInterViewActivity.class);
                intent.putExtra("type","resume");
                intent.putExtra("title","实习生专区");
                intent.putExtra("jobs_nature",63);
                startActivity(intent);
                break;
            case R.id.tv_more4:
            case R.id.tv_jianzhi:
                intent = new Intent(mContext, MoreInterViewActivity.class);
                intent.putExtra("type","resume");
                intent.putExtra("title","兼职专区");
                intent.putExtra("jobs_nature",62);
                startActivity(intent);
                break;
            case R.id.tv_more3:
                startActivity(new Intent(mContext, SchoolSpeakActivity.class));
                break;
            case R.id.tv_more5:
                startActivity(new Intent(mContext, WorkDiscussActivity.class));
                break;
        }
    }
}
