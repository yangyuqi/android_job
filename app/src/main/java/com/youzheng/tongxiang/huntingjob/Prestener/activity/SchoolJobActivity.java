package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.jude.rollviewpager.RollPagerView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HotNewsList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobNewsBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewCareerBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewCareerList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.SchoolBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.SchoolBeanList;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.BannerList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.BannerNormalAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.MyIconHintView;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;
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
 * Created by qiuweiyu on 2018/2/7.
 */

public class SchoolJobActivity extends BaseActivity {

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
    private CommonAdapter<SchoolBeanDetails> adapter;
    private List<SchoolBeanDetails> data_data = new ArrayList<>();
    private CommonAdapter<SchoolBeanDetails> adapter_two;
    private List<SchoolBeanDetails> data_two = new ArrayList<>();
    private CommonAdapter<NewCareerBean> adapter_three;
    private List<NewCareerBean> data_three = new ArrayList<>();

    private CommonAdapter<SchoolBeanDetails> adapter_four;
    private List<SchoolBeanDetails> data_four = new ArrayList<>();

    private CommonAdapter<JobNewsBean> adapter_five;
    private List<JobNewsBean> data_five = new ArrayList<>();

    private List<Integer> data = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_job_layout);
        ButterKnife.bind(this);
        layoutHeader.setBackgroundResource(R.drawable.main_color);
        textHeadTitle.setText("校园招聘会");
        btnBack.setVisibility(View.VISIBLE);
        topHeaderLlRightIvCaidan.setImageResource(R.mipmap.sousuo2);
        topHeaderLlRightIvCaidan.setVisibility(View.VISIBLE);
        initData();
    }


    private void initData() {
        data.clear();
        data.add(R.mipmap.banner);
        data.add(R.mipmap.banner);
        data.add(R.mipmap.banner);
        rollView.setPlayDelay(6000);
        rollView.setHintView(new MyIconHintView(mContext, R.mipmap.select_rv_bg, R.mipmap.two_pg));
//        rollView.setAdapter(new BannerNormalAdapter(data));
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

        ls.setFocusable(false);

        adapter = new CommonAdapter<SchoolBeanDetails>(mContext, data_data, R.layout.home_page_ls_item) {
            @Override
            public void convert(ViewHolder helper, final SchoolBeanDetails item) {
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_level_l, item.getEducation() + "/" + item.getExperience() + "/" + item.getCity());
                helper.setText(R.id.tv_name_co, item.getName());
                helper.setText(R.id.tv_below, item.getJob_type());
                if (item.getWage_face()==0) {
                    helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                }else {
                    helper.setText(R.id.tv_money,"面议");
                }
                String[] strings = item.getJobtag().split(",");
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0 ;i <strings.length;i++){
                    arrayList.add(strings[i]);
                }
                LabelsView labelsView = helper.getView(R.id.labelView);
                labelsView.setLabels(arrayList);
                helper.setText(R.id.tv_update_time,item.getCreate_time());
                Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.youyuzhanweicopy2).into((CircleImageView) helper.getView(R.id.iv_logo));
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, IntroduceJobActivity.class);
                        intent.putExtra("id", item.getJid());
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);

        lsTwo.setFocusable(false);
        lsThree.setFocusable(false);
        lsFour.setFocusable(false);
        lsFive.setFocusable(false);
        adapter_two = new CommonAdapter<SchoolBeanDetails>(mContext, data_two, R.layout.home_page_ls_item) {
            @Override
            public void convert(ViewHolder helper, final SchoolBeanDetails item) {
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_level_l, item.getEducation() + "/" + item.getExperience() + "/" + item.getCity());
                helper.setText(R.id.tv_name_co, item.getName());
                helper.setText(R.id.tv_below, item.getJob_type());
                if (item.getWage_face()==0) {
                    helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                }else {
                    helper.setText(R.id.tv_money,"面议");
                }
                String[] strings = item.getJobtag().split(",");
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0 ;i <strings.length;i++){
                    arrayList.add(strings[i]);
                }
                LabelsView labelsView = helper.getView(R.id.labelView);
                labelsView.setLabels(arrayList);

                helper.setText(R.id.tv_update_time,item.getCreate_time());
                Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.youyuzhanweicopy2).into((CircleImageView) helper.getView(R.id.iv_logo));
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, IntroduceJobActivity.class);
                        intent.putExtra("id", item.getJid());
                        startActivity(intent);
                    }
                });
            }
        };

        lsTwo.setAdapter(adapter_two);


        adapter_three = new CommonAdapter<NewCareerBean>(mContext, data_three, R.layout.speak_ls_item) {
            @Override
            public void convert(ViewHolder helper, final NewCareerBean item) {
                Glide.with(mContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.iv_speak));
                helper.setText(R.id.tv_school_speak, item.getTitle());
                helper.setText(R.id.tv_school_name, item.getSchool());
                helper.setText(R.id.tv_school_address, "地点 :" + item.getPlace());
                helper.setText(R.id.tv_school_time, "时间 :" + item.getOpen_date());
                helper.setText(R.id.tv_data, item.getCreate_date());
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


        adapter_four = new CommonAdapter<SchoolBeanDetails>(mContext, data_four, R.layout.home_page_ls_item) {
            @Override
            public void convert(ViewHolder helper, final SchoolBeanDetails item) {
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_level_l, item.getEducation() + "/" + item.getExperience() + "/" + item.getCity());
                helper.setText(R.id.tv_name_co, item.getName());
                helper.setText(R.id.tv_below, item.getJob_type());
                if (item.getWage_face()==0) {
                    helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                }else {
                    helper.setText(R.id.tv_money,"面议");
                }
                String[] strings = item.getJobtag().split(",");
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0 ;i <strings.length;i++){
                    arrayList.add(strings[i]);
                }
                LabelsView labelsView = helper.getView(R.id.labelView);
                labelsView.setLabels(arrayList);
                helper.setText(R.id.tv_update_time,item.getCreate_time());
                Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.youyuzhanweicopy2).into((CircleImageView) helper.getView(R.id.iv_logo));
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, IntroduceJobActivity.class);
                        intent.putExtra("id", item.getJid());
                        startActivity(intent);
                    }
                });
            }
        };

        lsFour.setAdapter(adapter_four);


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


        Map<String, Object> map = new HashMap<>();
        map.put("jobs_nature", 162);
        map.put("type","job");
        map.put("page",1);
        map.put("rows",5);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SCHOOL_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    SchoolBeanList bean = gson.fromJson(gson.toJson(entity.getData()), SchoolBeanList.class);
                    if (bean.getJobList().size() > 0) {
                        if (bean.getJobList().size() >= 5) {
                            adapter.setData(bean.getJobList().subList(0, 5));
                        } else {
                            adapter.setData(bean.getJobList());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        Map<String, Object> map1 = new HashMap<>();
        map1.put("jobs_nature", 63);
        map1.put("type","job");
        map1.put("page",1);
        map1.put("rows",5);
        OkHttpClientManager.postAsynJson(gson.toJson(map1), UrlUtis.SCHOOL_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    SchoolBeanList bean = gson.fromJson(gson.toJson(entity.getData()), SchoolBeanList.class);
                    if (bean.getJobList().size() > 0) {
                        if (bean.getJobList().size() >= 5) {
                            adapter_two.setData(bean.getJobList().subList(0, 5));
                        } else {
                            adapter_two.setData(bean.getJobList());
                        }
                        adapter_two.notifyDataSetChanged();
                    }
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


        Map<String, Object> map4 = new HashMap<>();
        map4.put("jobs_nature", 62);
        map4.put("type","job");
        map4.put("page",1);
        map4.put("rows",5);
        OkHttpClientManager.postAsynJson(gson.toJson(map4), UrlUtis.SCHOOL_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    SchoolBeanList bean = gson.fromJson(gson.toJson(entity.getData()), SchoolBeanList.class);
                    if (bean.getJobList().size() > 0) {
                        if (bean.getJobList().size() >= 5) {
                            adapter_four.setData(bean.getJobList().subList(0, 5));
                        } else {
                            adapter_four.setData(bean.getJobList());
                        }
                        adapter_four.notifyDataSetChanged();
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

    }

    @OnClick({R.id.btnBack, R.id.top_header_ll_right_iv_caidan, R.id.tv_yinjie, R.id.tv_shixi, R.id.tv_jianzhi, R.id.tv_xuanjianghui, R.id.tv_console, R.id.tv_more,R.id.tv_more2,R.id.tv_more3,R.id.tv_more4,R.id.tv_more5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.top_header_ll_right_iv_caidan:
                startActivity(new Intent(mContext, SearchJobActivity.class));
                break;
            case R.id.tv_yinjie :
                Intent intent = new Intent(mContext, SearchJobActivity.class);
                intent.putExtra("type", "应届生");
                startActivity(intent);
                break;
            case R.id.tv_shixi:
                Intent intent1 = new Intent(mContext, SearchJobActivity.class);
                intent1.putExtra("keyWord", "实习生");
                startActivity(intent1);
                break;
            case R.id.tv_jianzhi:
                Intent intent2 = new Intent(mContext, SearchJobActivity.class);
                intent2.putExtra("keyWord", "兼职");
                startActivity(intent2);
                break;
            case R.id.tv_xuanjianghui:
                startActivity(new Intent(mContext, SchoolSpeakActivity.class));
                break;
            case R.id.tv_console:
                startActivity(new Intent(mContext, WorkDiscussActivity.class));
                break;
            case R.id.tv_more:
                Intent intent_more = new Intent(mContext, SearchJobActivity.class);
                intent_more.putExtra("type", "应届生");
                startActivity(intent_more);
                break;
            case R.id.tv_more2:
                Intent intent_more2 = new Intent(mContext, SearchJobActivity.class);
                intent_more2.putExtra("keyWord", "实习生");
                startActivity(intent_more2);
                break;
            case R.id.tv_more3:
                startActivity(new Intent(mContext, SchoolSpeakActivity.class));
                break;
            case R.id.tv_more4 :
                Intent intent_more4 = new Intent(mContext, SearchJobActivity.class);
                intent_more4.putExtra("keyWord", "兼职");
                startActivity(intent_more4);
                ;
                break;
            case R.id.tv_more5 :
                startActivity(new Intent(mContext, WorkDiscussActivity.class));
                break;
        }
    }
}
