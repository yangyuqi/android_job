package com.youzheng.tongxiang.huntingjob.HR;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrSearchResultActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.ReportJobActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.TestActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.CoAttentionBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.IntroduceCoJobBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.IntroduceJobActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
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
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/5/10.
 */

public class HrJobFragment extends BaseFragment {


    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.ls)
    ListView ls;
    Unbinder unbinder;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.iv_fabu)
    ImageView ivFabu;


    private List<JobBeanDetails> data = new ArrayList<>();
    private CommonAdapter<JobBeanDetails> adapter;

    private int mSatatus ;
    private int allCount ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hr_zhiwei_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        textHeadTitle.setText("职位");

        adapter = new CommonAdapter<JobBeanDetails>(mContext, data, R.layout.new_zhiwei_ls_item) {
            @Override
            public void convert(ViewHolder helper, final JobBeanDetails item) {
                helper.setText(R.id.tv_name, item.getTitle());
                String content = "";
                if (item.getWage_face()==0) {
                    content ="" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K";
                }else {
                    content = "面议";
                }
                helper.setText(R.id.tv_name_money,content);
                helper.setText(R.id.tv_level_l, item.getExperience()+" | "+ item.getEducation()+" | "+item.getCity());
                Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.gongsixinxi).into((ImageView) helper.getView(R.id.iv_logo));
                helper.setText(R.id.tv_num_num,""+item.getDeliverNum());
                if (item.getState()==0){
                    helper.setText(R.id.tv_status,"发布中");
                    helper.getView(R.id.rl_match).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_edit).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_redo).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_shoucan).setVisibility(View.VISIBLE);
                }else if (item.getState()==1){
                    helper.setText(R.id.tv_status,"已撤销");
                    helper.getView(R.id.rl_edit).setVisibility(View.GONE);
                    helper.getView(R.id.rl_redo).setVisibility(View.GONE);
                    helper.getView(R.id.rl_shoucan).setVisibility(View.GONE);
                }else if (item.getState()==2){
                    helper.setText(R.id.tv_status,"已入职");
                    helper.getView(R.id.rl_edit).setVisibility(View.GONE);
                    helper.getView(R.id.rl_redo).setVisibility(View.GONE);
                    helper.getView(R.id.rl_shoucan).setVisibility(View.GONE);
                }else {
                    helper.setText(R.id.tv_status,"");
                }

                helper.getView(R.id.rl_more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,TestActivity.class);
                        intent.putExtra("id",item.getId());
                        intent.putExtra("from","co");
                        startActivity(intent);
                    }
                });

                helper.getView(R.id.rl_redo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        redo(item.getId(),"1");
                    }
                });

                helper.getView(R.id.rl_shoucan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        redo(item.getId(),"2");
                    }
                });

                helper.getView(R.id.rl_match).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, HrSearchResultActivity.class);
                        intent.putExtra("keyWord",item.getTitle());
                        startActivity(intent);
                    }
                });

                helper.getView(R.id.rl_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,ReportJobActivity.class);
                        intent.putExtra("jid",String.valueOf(item.getId()));
                        startActivity(intent);
                    }
                });

            }
        };
        ls.setAdapter(adapter);

        initEvent();
    }

    private void redo(int id, String s) {
        Map<String,Object> map = new HashMap<>();
        map.put("jid",id);
        map.put("state",s);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_STATE, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    initData(mSatatus);
                }
            }
        });
    }

    private void initEvent() {
        ivFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAttention();
            }
        });
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                initData(mSatatus);
            }

            @Override
            public void onLoadmore() {
                if (allCount>20){
                    if (page*20<allCount){
                        page++;
                        initData(mSatatus);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(0);
    }

    private void getAttention() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ATTENTION_CO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    CoAttentionBean attentionBean = gson.fromJson(gson.toJson(entity.getData()), CoAttentionBean.class);
                    if (attentionBean.getState().equals("1")) {
                        startActivity(new Intent(mContext, ReportJobActivity.class));
                    }else {
                        showToast("请认证成功后,再来发布职位");
                    }
                }
            }
        });
    }

    private int page =1 ;
    private int rows = 20 ;

    private void initData(int status) {
        mSatatus=status;
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",rows);
        map.put("uid",uid);
        if (status!=0){
            if (status==2) {
                map.put("state", status);
            }
        }else {
            if (map.containsKey("state")){
                map.remove("state");
            }
        }
        if (status==1){
            map.put("state",status-1);
        }
        if (status==3){
            map.put("state",status-2);
        }

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.GET_CO_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    IntroduceCoJobBean jobBean = gson.fromJson(gson.toJson(entity.getData()),IntroduceCoJobBean.class);
                    if (jobBean.getCount()>0){
                        allCount = jobBean.getCount();
                        if (jobBean.getAllJobList().size()>0&&jobBean.getAllJobList().size()<=20){
                            if (page==1){
                                data.clear();
                            }
                            data.addAll(jobBean.getAllJobList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }else {
                            data.addAll(jobBean.getAllJobList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        adapter.setData(new ArrayList<JobBeanDetails>());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
