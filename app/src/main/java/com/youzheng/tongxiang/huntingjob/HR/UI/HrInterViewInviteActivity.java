package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.CollectListBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobCollectBean;
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

public class HrInterViewInviteActivity extends BaseActivity {

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
        setContentView(R.layout.hr_inter_view_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        textHeadTitle.setText("面试邀请");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<UserJlBean>(mContext,data,R.layout.interview_ls_item) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {

                helper.getView(R.id.rl_no).setVisibility(View.VISIBLE);

                if (item.getLastestCompany()==null){
                    helper.setText(R.id.tv_style, "暂无公司");
                }else {
                    helper.setText(R.id.tv_style, item.getLastestCompany());
                }
                helper.setText(R.id.tv_name, item.getTruename());
                if (item.getGender()!=null) {
                    if (item.getGender() != 1) {
                        helper.setText(R.id.tv_sex, "女");
                        ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_24_nv);
                    } else {
                        helper.setText(R.id.tv_sex, "男");
                        ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_25_nan);
                    }
                }
                if (item.getBirthdate()!=null) {
                    helper.setText(R.id.tv_age, item.getBirthdate());
                }
                helper.setText(R.id.tv_work_year, "" + item.getExperience() + "年" + " | " + item.getEducation() + " | " + item.getCitysName());
                helper.setText(R.id.tv_status, item.getStatus());
                helper.setText(R.id.tv_get_job, "应聘职位 : "+item.getPosition());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.civ));
                helper.setText(R.id.tv_see_time, item.getActTime()+"更新");
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,HrJianliDetailsActivity.class);
                        intent.putExtra("jid",item.getRid());
                        startActivity(intent);
                    }
                });



//                if (item.getLastestCompany()==null){
//                    helper.setText(R.id.tv_style, "暂无公司");
//                }else {
//                    helper.setText(R.id.tv_style, item.getLastestCompany());
//                }
//                helper.setText(R.id.tv_name, item.getTruename());
//                if (item.getGender()!=null) {
//                    if (item.getGender() != 1) {
//                        helper.setText(R.id.tv_sex, "女");
//                        ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_24_nv);
//                    } else {
//                        helper.setText(R.id.tv_sex, "男");
//                        ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_25_nan);
//                    }
//                }
//                helper.setText(R.id.tv_age, item.getBirthdate());
//                helper.setText(R.id.tv_work_year, "" + item.getWork_year() + "年" + " | " + item.getEducation() + " | " + item.getCitysName());
//                helper.setText(R.id.tv_status, item.getStatus());
//                helper.setText(R.id.tv_get_job, "应聘职位 :  "+item.getPosition());
//                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.civ));
//                helper.setText(R.id.tv_see_time, item.getActTime()+"更新");
//
//                TextView textView = helper.getView(R.id.tv_collect);
//                if (item.getCollectState().equals("0")){
//                    textView.setText("收藏");
//                }else {
//                    textView.setText("收藏");
//                }
//
                helper.getView(R.id.rl_shoucan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getJid()!=null) {
                            doCollect(item.getJid());
                        }
                    }
                });

                helper.getView(R.id.rl_redo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getId()!=null&&item.getJid()!=null&&item.getRid()!=null) {
                            doActionDelete(item.getId(), item.getJid(), item.getRid());
                        }
                    }
                });
//                helper.getView(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(mContext,HrJianliDetailsActivity.class);
//                        intent.putExtra("jid",item.getRid());
//                        startActivity(intent);
//                    }
//                });
            }
        };
        ls.setAdapter(adapter);

        initEvent();
    }

    private void doActionDelete(int id, int jid, int rid) {
        Map<String,Object> map = new HashMap<>();
        map.put("status","2");
        map.put("id",id);
        map.put("rid",rid);
        map.put("jid",jid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_DELIVERY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                showToast(entity.getMsg());
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    initData();
                }
            }
        });
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("page",page);
        map.put("rows",rows);
        map.put("time",1);
        map.put("status","3");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.HANDLER_JIANLI, new OkHttpClientManager.StringCallback() {
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
                        if (jobBean.getResumeList().size()>0&&jobBean.getResumeList().size()<=20){
                            if (page==1){
                                data.clear();
                            }
                            data.addAll(jobBean.getResumeList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }else {
                            data.addAll(jobBean.getResumeList());
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

    public void doCollect(final int jid){

        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CO_COLLECT_LIST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    final ListJobData listJobData = gson.fromJson(gson.toJson(entity.getData()), ListJobData.class);
                    if (listJobData.getData().size() > 0) {
                        final List<Integer> id_list = new ArrayList<Integer>();
                        final List<String> title_list = new ArrayList<String>();
                        for (int i = 0; i < listJobData.getData().size(); i++) {
                            id_list.add(listJobData.getData().get(i).getId());
                            title_list.add(listJobData.getData().get(i).getName());
                        }
                        OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                    addCollect(listJobData.getData().get(options1),jid);
                            }
                        }).setTitleText("收藏夹选择").build();
                        pvCustomTime.setPicker(title_list);
                        pvCustomTime.show();
                    }
                }
            }
        });

    }

    private void addCollect(ListJobCollectBean listJobCollectBean ,int jid) {
        Map<String, Object> t_map = new HashMap<>();
        t_map.put("jid", jid);
        t_map.put("uid", uid);
        t_map.put("ctype", "0");
        t_map.put("favoritesId", listJobCollectBean.getId());
        OkHttpClientManager.postAsynJson(gson.toJson(t_map), UrlUtis.JOB_COLLECT, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                showToast(entity.getMsg());
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    initData();
                }
            }
        });
    }
}
