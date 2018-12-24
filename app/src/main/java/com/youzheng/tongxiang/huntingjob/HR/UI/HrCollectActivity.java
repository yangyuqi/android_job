package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.CollectListBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.ResumeListBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobData;
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

public class HrCollectActivity extends BaseActivity {

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
        setContentView(R.layout.hr_collect_layout);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
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

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("page",page);
        map.put("rows",rows);
        map.put("state","2");
        map.put("time","1");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CO_COLLECT_JL, new OkHttpClientManager.StringCallback() {
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
                        if (jobBean.getCollectList().size()>0&&jobBean.getCollectList().size()<=20){
                            if (page==1){
                                data.clear();
                            }
                            data.addAll(jobBean.getCollectList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }else {
                            data.addAll(jobBean.getCollectList());
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

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadTitle.setText("收藏夹");


        adapter = new CommonAdapter<UserJlBean>(mContext,data,R.layout.hr_collect_ls_item) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {
                helper.setText(R.id.tv_style,item.getPosition());
                helper.setText(R.id.tv_name,item.getTruename());
                if (item.getGender()!=1){
                    helper.setText(R.id.tv_sex,"女");
                }else {
                    helper.setText(R.id.tv_sex,"男");
                }
                RelativeLayout tv_collect = helper.getView(R.id.rl_shoucan);
                if (item.getResumeStatus().equals("3")){
                    tv_collect.setVisibility(View.GONE);
                }else {
                    tv_collect.setVisibility(View.VISIBLE);
                }
                helper.setText(R.id.tv_age,item.getBirthdate());
                helper.setText(R.id.tv_work_year,""+item.getExperience()+"年工作经验");
                helper.setText(R.id.tv_status,item.getStatus());
                helper.setText(R.id.tv_xueli,item.getEducation());
                helper.setText(R.id.tv_money,item.getWage());
                helper.setText(R.id.tv_address,item.getCitysName());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.civ));
                helper.setText(R.id.tv_see_time,item.getCreate_time());
                helper.getView(R.id.rl_redo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("jid", item.getRid());
                        map.put("uid", uid);
                        map.put("ctype", "0");
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UNSCRIBE_JOB, new OkHttpClientManager.StringCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                try {
                                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                                        initData();
                                    }
                                }catch (Exception e){}
                            }
                        });
                    }
                });

                helper.getView(R.id.rl_shoucan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getJob(item.getRid());
                    }
                });

                helper.getView(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,HrJianliDetailsActivity.class);
                        intent.putExtra("jid",item.getRid());
                        startActivity(intent);
                    }
                });

            }
        };
        ls.setAdapter(adapter);
    }

    private void getJob(final int rid) {
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("uid",uid);
        objectMap.put("rid",rid);
        OkHttpClientManager.postAsynJson(gson.toJson(objectMap), UrlUtis.GET_HR_CO_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    final ListJobData listJobData = gson.fromJson(gson.toJson(entity.getData()),ListJobData.class);
                    if (listJobData.getList().size()>0){
                        final List<Integer> id_list = new ArrayList<Integer>();
                        List<String> title_list = new ArrayList<String>();
                        for (int i = 0 ; i<listJobData.getList().size();i++){
                            id_list.add(listJobData.getList().get(i).getJid());
                            title_list.add(listJobData.getList().get(i).getTitle());
                        }
                        OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                int  m_id = listJobData.getList().get(options1).getJid();
                                initInter(m_id,"3",rid);
                            }
                        }).setTitleText("职位选择").build();
                        pvCustomTime.setPicker(title_list);
                        pvCustomTime.show();
                    }
                }
            }
        });
    }

    private void initInter(int m_id, String s, int rid) {
        Map<String,Object> map = new HashMap<>();
        map.put("rid",String.valueOf(rid));
        map.put("status",s);
        map.put("id","");
        map.put("jid",String.valueOf(m_id));
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_DELIVERY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    initData();
                }
            }
        });
    }
}
