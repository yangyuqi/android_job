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

public class NewHrCollectDetailActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.tv_zhiwei)
    TextView tvZhiwei;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.tv_edit_collect)
    TextView tvEditCollect;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_go_other_collect)
    TextView tvGoOtherCollect;
    @BindView(R.id.tv_go_delete)
    TextView tvGoDelete;

    private List<UserJlBean> data = new ArrayList<>();
    private CommonAdapter<UserJlBean> adapter;

    private String name, note;
    private int id;

    private int page = 1, rows = 20, allCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_hr_collect_detail_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
        initData();
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

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("page", page);
        map.put("rows", rows);
        map.put("state", "2");
        map.put("time", "1");
        map.put("favoritesId", id);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CO_COLLECT_JL, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    CollectListBean jobBean = gson.fromJson(gson.toJson(entity.getData()), CollectListBean.class);
                    if (jobBean.getCount() > 0) {
                        allCount = jobBean.getCount();
                        if (jobBean.getCollectList().size() > 0 && jobBean.getCollectList().size() <= 20) {
                            if (page == 1) {
                                data.clear();
                            }
                            data.addAll(jobBean.getCollectList());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        } else {
                            data.addAll(jobBean.getCollectList());
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name = getIntent().getStringExtra("name");
        note = getIntent().getStringExtra("note");
        id = getIntent().getIntExtra("id", 0);
        tvZhiwei.setText(name);
        tvBeizhu.setText(note);
        textHeadTitle.setText("收藏夹");
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("管理");
        adapter = new CommonAdapter<UserJlBean>(mContext, data, R.layout.hr_collect_ls_item) {
            @Override
            public void convert(final ViewHolder helper, final UserJlBean item) {

                if (item.getType() == 0) {
                    helper.getView(R.id.iv_select_icon).setVisibility(View.GONE);
                } else if (item.getType() == 1) {
                    helper.getView(R.id.iv_select_icon).setVisibility(View.VISIBLE);
                    if (item.isSelect()) {
                        ((ImageView) helper.getView(R.id.iv_select_icon)).setImageResource(R.mipmap.group_20_1);
                    } else {
                        ((ImageView) helper.getView(R.id.iv_select_icon)).setImageResource(R.mipmap.group_21_1);
                    }
                }

                helper.getView(R.id.iv_select_icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.isSelect()) {
                            ((ImageView) helper.getView(R.id.iv_select_icon)).setImageResource(R.mipmap.group_21_1);
                            item.setSelect(false);
                        } else {
                            ((ImageView) helper.getView(R.id.iv_select_icon)).setImageResource(R.mipmap.group_20_1);
                            item.setSelect(true);
                        }
                    }
                });

                if (item.getLastestCompany()==null){
                    helper.setText(R.id.tv_style, "暂无公司");
                }else {
                    helper.setText(R.id.tv_style, item.getLastestCompany());
                }
                helper.setText(R.id.tv_name, item.getTruename());
                try {
                    if (item.getGender() != 1) {
                        helper.setText(R.id.tv_sex, "女");
                        ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_24_nv);
                    } else {
                        helper.setText(R.id.tv_sex, "男");
                        ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_25_nan);
                    }
                }catch (Exception e){}
                helper.setText(R.id.tv_age, item.getBirthdate());
                helper.setText(R.id.tv_work_year, "" + item.getWork_year() + "年" + " | " + item.getEducation() + " | " + item.getCitysName());
                helper.setText(R.id.tv_status, item.getStatus());
                helper.setText(R.id.tv_get_job, "应聘职位 :  "+item.getPosition());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.civ));
                helper.setText(R.id.tv_see_time, item.getActTime()+"更新");
                if (item.getType()==0) {
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, HrJianliDetailsActivity.class);
                            intent.putExtra("jid", item.getRid());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        ls.setAdapter(adapter);

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textHeadNext.getText().toString().equals("管理")) {
                    rlBottom.setVisibility(View.VISIBLE);
                    textHeadNext.setText("完成");
                    tvEditCollect.setVisibility(View.VISIBLE);
                    for (UserJlBean bean : data) {
                        bean.setType(1);
                    }
                } else {
                    for (UserJlBean bean : data) {
                        bean.setType(0);
                    }
                    tvEditCollect.setVisibility(View.GONE);
                    textHeadNext.setText("管理");
                    rlBottom.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
        });

        tvEditCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddAndEditCollectActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("note", note);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        tvGoOtherCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("uid",uid);
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
                                            moveInter(listJobData.getData().get(options1).getId());
                                    }
                                }).setTitleText("收藏夹选择").build();
                                pvCustomTime.setPicker(title_list);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
            }
        });

        tvGoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> integerList = new ArrayList<>();
                for (UserJlBean bean : data){
                    if (bean.isSelect()){
                        integerList.add(bean.getId());
                    }
                }
                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put("ids",integerList);
                objectMap.put("accessToken",accessToken);
                OkHttpClientManager.postAsynJson(gson.toJson(objectMap), UrlUtis.DELETE_ALL_REVIEW, new OkHttpClientManager.StringCallback() {
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
        });
    }

    private void moveInter(int id) {
        if (data.size()>0){
            List<Integer> integerList = new ArrayList<>();
            for (UserJlBean bean : data){
                if (bean.isSelect()){
                    integerList.add(bean.getId());
                }
            }
            Map<String,Object> objectMap = new HashMap<>();
            objectMap.put("ids",integerList);
            objectMap.put("accessToken",accessToken);
            objectMap.put("favoritesId",id);
            OkHttpClientManager.postAsynJson(gson.toJson(objectMap), UrlUtis.MOVE_REVIEW_ALL, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        initData();
                    }
                }
            });
        }
    }


    private void getJob(final int rid) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("uid", uid);
        objectMap.put("rid", rid);
        OkHttpClientManager.postAsynJson(gson.toJson(objectMap), UrlUtis.GET_HR_CO_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    final ListJobData listJobData = gson.fromJson(gson.toJson(entity.getData()), ListJobData.class);
                    if (listJobData.getList().size() > 0) {
                        final List<Integer> id_list = new ArrayList<Integer>();
                        List<String> title_list = new ArrayList<String>();
                        for (int i = 0; i < listJobData.getList().size(); i++) {
                            id_list.add(listJobData.getList().get(i).getJid());
                            title_list.add(listJobData.getList().get(i).getTitle());
                        }
                        OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                int m_id = listJobData.getList().get(options1).getJid();
                                initInter(m_id, "3", rid);
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
        Map<String, Object> map = new HashMap<>();
        map.put("rid", String.valueOf(rid));
        map.put("status", s);
        map.put("id", "");
        map.put("jid", String.valueOf(m_id));
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_DELIVERY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    initData();
                }
            }
        });
    }
}
