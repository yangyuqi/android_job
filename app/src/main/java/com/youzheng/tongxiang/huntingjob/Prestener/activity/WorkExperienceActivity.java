package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Event.SelectJianLiBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducitionBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.ProjectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.SkillListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.WorkExperenceBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeOnePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeTwoPageFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/13.
 */

public class WorkExperienceActivity extends BaseActivity {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;

    private SelectJianLiBean bean ;

    ArrayList<WorkExperenceBean> workExperenceList = new ArrayList<>();
    ArrayList<ProjectBean> projectBeanList = new ArrayList<>();
    ArrayList<EducitionBean> educitionBeanArrayList = new ArrayList<>();
    ArrayList<SkillListBean> skillListLidt = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_experience_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        textHeadNext.setText("添加");
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadTitle.setVisibility(View.GONE);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,DescribeDetailsActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bean!=null){
            EventBus.getDefault().post(bean);
        }
    }

    @Subscribe
    public void onEvent(SelectJianLiBean liBean) {
        Log.e("sssssss",gson.toJson(liBean));
        if (liBean!=null){
            bean = liBean ;
            if (liBean.getType().equals("2")) {
                workExperenceList.clear();
                workExperenceList.addAll(liBean.getJi().getExperienceList());
                final CommonAdapter<WorkExperenceBean> adapter = new CommonAdapter<WorkExperenceBean>(mContext,workExperenceList,R.layout.work_experence_ls_item) {
                    @Override
                    public void convert(final ViewHolder helper, final WorkExperenceBean item) {
                        helper.setText(R.id.tv_co,item.getCompany());
                        helper.setText(R.id.tv_experice,item.getStart_time()+"至"+item.getEnd_time()+"/"+item.getPosition());
                        helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bean.setExpe_id(item.getId());
                                startActivity(new Intent(mContext,DescribeDetailsActivity.class));
                                finish();
                            }
                        });

                        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("reid",item.getId());
                                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.DELETE_WORK_YEAR, new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                            workExperenceList.remove(helper.getPosition());
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        });
                    }
                };
                ls.setAdapter(adapter);
            }else if (liBean.getType().equals("3")){
                projectBeanList.clear();
                projectBeanList.addAll(liBean.getJi().getProjectList());
                CommonAdapter<ProjectBean> adapter = new CommonAdapter<ProjectBean>(mContext,projectBeanList,R.layout.work_experence_ls_item) {
                    @Override
                    public void convert(final ViewHolder helper, final ProjectBean item) {
                        helper.setText(R.id.tv_co,item.getName());
                        helper.setText(R.id.tv_experice,item.getStart_time()+"至"+item.getEnd_time());
                        helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bean.setProject_id(item.getId());
                                startActivity(new Intent(mContext,DescribeDetailsActivity.class));
                                finish();
                            }
                        });
                        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("rpid",item.getId());
                                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.DELETE_PROJECT_YEAR, new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                            projectBeanList.remove(helper.getPosition());
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        });
                    }
                };
                ls.setAdapter(adapter);
            }else if (liBean.getType().equals("4")){
                educitionBeanArrayList.clear();
                educitionBeanArrayList.addAll(liBean.getJi().getEducationList());
                CommonAdapter<EducitionBean> adapter = new CommonAdapter<EducitionBean>(mContext,educitionBeanArrayList,R.layout.work_experence_ls_item) {
                    @Override
                    public void convert(final ViewHolder helper,final EducitionBean item) {
                        helper.setText(R.id.tv_co,item.getSchool());
                        helper.setText(R.id.tv_experice,item.getStarttime()+"至"+item.getEndtime()+"  "+item.getEducation()+"  "+item.getMajor());
                        helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bean.setEducition_id(item.getId());
                                startActivity(new Intent(mContext,DescribeDetailsActivity.class));
                                finish();
                            }
                        });
                        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("reid",item.getId());
                                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.DELETE_EDUCTION_YEAR, new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                            educitionBeanArrayList.remove(helper.getPosition());
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        });
                    }
                };
                ls.setAdapter(adapter);
            }else if (liBean.getType().equals("5")){
                skillListLidt.clear();
                skillListLidt.addAll(bean.getJi().getSkillList());
                CommonAdapter<SkillListBean> adapter = new CommonAdapter<SkillListBean>(mContext,skillListLidt,R.layout.work_experence_ls_item) {
                    @Override
                    public void convert(final ViewHolder helper,final SkillListBean item) {
                        helper.setText(R.id.tv_co,item.getDegree());
                        helper.setText(R.id.tv_experice,item.getSkill());
                        helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bean.setJineng_id(item.getId());
                                startActivity(new Intent(mContext,DescribeDetailsActivity.class));
                                finish();
                            }
                        });
                        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("rslid",item.getId());
                                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.DELETE_JIENG_, new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                            skillListLidt.remove(helper.getPosition());
                                            notifyDataSetChanged();
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
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
