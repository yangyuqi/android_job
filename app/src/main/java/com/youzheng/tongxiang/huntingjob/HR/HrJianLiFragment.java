package com.youzheng.tongxiang.huntingjob.HR;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.HR.UI.CoReviceActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrDelieveyMessageActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrJianliDetailsActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.ResumeListBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBeanEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobCollectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobData;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
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

public class HrJianLiFragment extends BaseFragment {

    @BindView(R.id.textHeadTitle)
    CheckBox textHeadTitle;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;
    Unbinder unbinder;

    List<UserJlBean> data = new ArrayList<>();
    CommonAdapter<UserJlBean> adapter;
    @BindView(R.id.rl_show)
    RelativeLayout rlShow;
    @BindView(R.id.rl_source_one)
    RelativeLayout rlSourceOne;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.cb2)
    CheckBox cb2;
    @BindView(R.id.ls_one)
    ListView lsOne;
    @BindView(R.id.ls_job)
    ListView lsJob;
    @BindView(R.id.rl_top3)
    RelativeLayout rlTop3;
    @BindView(R.id.btnBack)
    ImageView btnBack;

    private int deliveryTime = 1, page = 1, rows = 20, allCount, jid = -1;
    private String status = "0", type;

    private CommonAdapter<ListJobBean> job_adapter;
    private List<ListJobBean> job_data = new ArrayList<>();

    public static HrJianLiFragment getInstance(String type) {
        HrJianLiFragment fragment = new HrJianLiFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hr_jianli_list_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData(status, jid);
            }

            @Override
            public void onLoadmore() {
                if (allCount > 20) {
                    if (page * 20 < allCount) {
                        page++;
                        initData(status, jid);
                    }
                }
            }
        });
    }

    private void initView() {
        textHeadTitle.setText("全部职位");
        rlTop3.setVisibility(View.GONE);

        if (getArguments() != null) {
            type = getArguments().getString("type");
            if (type != null) {
                btnBack.setVisibility(View.VISIBLE);
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!type.equals("CoReviceActivity")) {
                            ((HrDelieveyMessageActivity) mContext).finish();
                        }else {
                            ((CoReviceActivity)mContext).finish();
                        }
                    }
                });
            }
        }

        adapter = new CommonAdapter<UserJlBean>(mContext, data, R.layout.new_hr_jianli_list_item_layout) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {
                if (item.getLastestCompany() == null) {
                    helper.setText(R.id.tv_style, "暂无公司");
                } else {
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
                helper.setText(R.id.tv_get_job, "应聘职位 : " + item.getPosition());
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.civ));
                helper.setText(R.id.tv_see_time, item.getActTime() + "更新");
                TextView textView = helper.getView(R.id.tv_collect);
                final TextView tv_ssss = helper.getView(R.id.tv_ssss);
                if (item.getCollectState().equals("0")) {
                    textView.setText("收藏");
                } else {
                    textView.setText("收藏");
                }
                helper.getView(R.id.rl_match).setVisibility(View.VISIBLE);
                helper.getView(R.id.rl_redo).setVisibility(View.VISIBLE);
                if (status.equals("3")) {
                    helper.getView(R.id.rl_redo).setVisibility(View.GONE);
                } else if (status.equals("4")) {
                    helper.getView(R.id.rl_match).setVisibility(View.GONE);
                }

                if (item.getResumeStatus().equals("3")) {
                    helper.getView(R.id.rl_redo).setVisibility(View.GONE);
                } else if (item.getResumeStatus().equals("4")) {
                    helper.getView(R.id.rl_match).setVisibility(View.GONE);
                }

                if (textView.getText().toString().equals("收藏")) {
                    helper.getView(R.id.rl_edit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            doCollect(item.getJid());
                        }
                    });
                }

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, HrJianliDetailsActivity.class);
                        intent.putExtra("jid", item.getRid());
                        intent.putExtra("type", "show");
                        startActivity(intent);
                    }
                });

                helper.getView(R.id.rl_match).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getJid()!=null&&item.getId()!=null) {
                            doNoMatch(item.getJid(), item.getId(), item.getRid(), "4");
                        }
                    }
                });

                if (!tv_ssss.getText().toString().equals("已邀请面试")) {

                    helper.getView(R.id.rl_redo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Map<String, Object> objectMap = new HashMap<>();
                            objectMap.put("uid", uid);
                            objectMap.put("rid", jid);
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
                                                    String time = PublicUtils.testDay(7).get(options2) + "   " + PublicUtils.showtime().get(options3);
                                                    initInter(m_id, "3", time, tv_ssss);
                                                }
                                            }).setTitleText("职位选择").build();
                                            pvCustomTime.setNPicker(title_list, PublicUtils.testDay(7), PublicUtils.showtime());
                                            pvCustomTime.show();
                                        }else {
                                            showToast("您已邀请面试");
                                        }
                                    }
                                }
                            });


//                        doNoMatch(item.getJid(), item.getId(), item.getRid(), "3");
                        }
                    });
                }
            }
        };
        ls.setAdapter(adapter);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb.isChecked()) {
                    rlShow.setVisibility(View.VISIBLE);
                    lsOne.setAdapter(initSelectAdapter(1));
                }else {
                    rlShow.setVisibility(View.GONE);
                }

            }
        });

        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb2.isChecked()) {
                    rlShow.setVisibility(View.VISIBLE);
                    lsOne.setAdapter(initSelectAdapter(2));
                }else {
                    rlShow.setVisibility(View.GONE);
                }
            }
        });


        textHeadTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textHeadTitle.isChecked()) {
                    rlTop3.setVisibility(View.VISIBLE);
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
                                    ListJobBean jobBean = new ListJobBean();
                                    jobBean.setTitle("全部职位");
                                    List<ListJobBean> listJobBeans = new ArrayList<>();
                                    listJobBeans.add(jobBean);
                                    listJobBeans.addAll(listJobData.getList());
                                    job_adapter.setData(listJobBeans);
                                    job_adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }else {
                    rlTop3.setVisibility(View.GONE);
                }
            }
        });

        job_adapter = new CommonAdapter<ListJobBean>(mContext, job_data, R.layout.check_box_ls_item2) {
            @Override
            public void convert(final ViewHolder helper, final ListJobBean item) {
                helper.setText(R.id.iv_jiantou, item.getTitle());
//                if (item.isSelect()) {
//                    ((TextView) helper.getView(R.id.cb)).setTextColor(mContext.getResources().getColor(R.color.blue_light));
//                    ((ImageView) helper.getView(R.id.iv_jiantou)).setImageResource(R.mipmap.group_19_1);
//                } else {
//                    ((TextView) helper.getView(R.id.cb)).setTextColor(mContext.getResources().getColor(R.color.text_gray));
//                    ((ImageView) helper.getView(R.id.iv_jiantou)).setImageResource(R.mipmap.group_33_1);
//                }
                final CheckBox checkBox = helper.getView(R.id.iv_jiantou);
                helper.getView(R.id.iv_jiantou).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.setSelect(true);
                        rlTop3.setVisibility(View.GONE);
                        textHeadTitle.setText(item.getTitle());
                        if (item.getTitle().equals("全部职位")) {
                            jid = -1;
                        } else {
                            jid = item.getJid();
                        }
                        initData(status, jid);
                        checkBox.setChecked(false);
                    }
                });

            }
        };
        lsJob.setAdapter(job_adapter);
    }

    public CommonAdapter<String> initSelectAdapter(final int type) {
        List<String> data = new ArrayList<>();
        if (type == 1) {
            data.add("全部来源");
        } else {
            data.add("全部状态");
            data.add("未处理");
            data.add("通知面试");
            data.add("不合适");
        }
        CommonAdapter<String> get_adapter = new CommonAdapter<String>(mContext, data, R.layout.check_box_ls_item) {
            @Override
            public void convert(ViewHolder helper, final String item) {
                helper.setText(R.id.cb, item);
                ((CheckBox) helper.getView(R.id.cb)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            rlShow.setVisibility(View.GONE);
                            switch (item) {
                                case "全部状态":
                                    initData("0", jid);
                                    break;
                                case "未处理":
                                    initData("1", jid);
                                    break;
                                case "通知面试":
                                    initData("3", jid);
                                    break;
                                case "不合适":
                                    initData("4", jid);
                                    break;
                            }
                            if (type == 2) {
                                cb2.setText(item);
                            } else {
                                cb.setText(item);
                            }
                        }
                    }
                });
            }
        };
        return get_adapter;
    }

    private void doNoMatch(final int jid, int id, int rid, String s) {
        Map<String, Object> map = new HashMap<>();
        map.put("jid", jid);
        map.put("rid", rid);
        map.put("id", id);
        map.put("status", s);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_DELIVERY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    initData(status, jid);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData(status, jid);
    }

    private void initData(String mstatus, int mjid) {
        status = mstatus;
        jid = mjid;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("status", status);
        map.put("time", deliveryTime);
        map.put("page", page);
        map.put("rows", rows);
        if (jid != -1) {
            map.put("jid", jid);
        }
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.HANDLER_JIANLI, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    ResumeListBean jobBean = gson.fromJson(gson.toJson(entity.getData()), ResumeListBean.class);
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

    private void addCollect(ListJobCollectBean listJobCollectBean , int jid) {
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
//                    initData();
                }
            }
        });
    }


    private void initInter(int m_id, final String status, String time, final TextView tv_ssss) {
        Map<String, Object> map = new HashMap<>();
        map.put("rid", String.valueOf(rid));
        map.put("status", status);
        map.put("id", "");
        map.put("jid", String.valueOf(m_id));
        map.put("interview_time", time);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_DELIVERY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    if (status.equals("3")) {
                        tv_ssss.setText("已邀请面试");
                        tv_ssss.setEnabled(false);
                    } else if (status.equals("4")) {
                        tv_ssss.setText("邀请面试");
                        tv_ssss.setEnabled(true);
                    }
                }
            }
        });
    }

}
