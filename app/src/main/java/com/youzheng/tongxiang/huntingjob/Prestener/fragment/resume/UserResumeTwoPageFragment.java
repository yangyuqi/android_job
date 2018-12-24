package com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import com.bigkoo.pickerview.TimePickerView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Event.SelectJianLiBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HangYeList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HanyeBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.WorkExperenceBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.DescribeDetailsActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class UserResumeTwoPageFragment extends BaseFragment {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.tv_work_start)
    TextView tvWorkStart;
    @BindView(R.id.tv_brith)
    TextView tvBrith;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.cb)
    RadioButton cb;
    @BindView(R.id.cb_woman)
    RadioButton cbWoman;
    Unbinder unbinder;
    @BindView(R.id.iv_riqi1)
    ImageView ivRiqi1;
    @BindView(R.id.iv_riqi2)
    ImageView ivRiqi2;
    @BindView(R.id.edt_co)
    EditText edtCo;
    @BindView(R.id.iv_hangye)
    ImageView ivHangye;
    @BindView(R.id.tv_hangye)
    TextView tvHangye;
    @BindView(R.id.iv_co)
    ImageView ivCo;
    @BindView(R.id.edt_position)
    EditText edtPosition;
    @BindView(R.id.edt_desc)
    EditText edtDesc;

    private String hangye_id;
    private int co_id;

    private WorkExperenceBean bean ;

    private OptionsPickerView pvCustomTime;
    private ArrayList<String> one_list = new ArrayList<>();
    private ArrayList<ArrayList<String>> two_list = new ArrayList<>();
    private ArrayList<HanyeBean> hang_list_one = new ArrayList<>();
    private ArrayList<ArrayList<HanyeBean>> hang_list_two = new ArrayList<>();

    public static UserResumeTwoPageFragment getInstance(SelectJianLiBean bean) {
        UserResumeTwoPageFragment fragment = new UserResumeTwoPageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_resume_two_page_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        if (getArguments()!=null) {
            SelectJianLiBean jianLiBean = (SelectJianLiBean) getArguments().getSerializable("data");
            final int id = jianLiBean.getExpe_id();
            if (id != 0) {
                for (int j = 0; j < jianLiBean.getJi().getExperienceList().size(); j++) {
                    if (jianLiBean.getJi().getExperienceList().get(j).getId() == id) {
                        bean = jianLiBean.getJi().getExperienceList().get(j);
                    }
                }
                if (bean != null) {
                    tvWorkStart.setText(bean.getStart_time());
                    tvBrith.setText(bean.getEnd_time());
                    edtCo.setText(bean.getCompany());
                    edtPosition.setText(bean.getPosition());
                    tvHangye.setText(bean.getTrade());
                    edtDesc.setText(bean.getDescription());
                    tvCity.setText(bean.getScale());
                    hangye_id = bean.getTradeid();
                    co_id = bean.getScaleid();
                }

                textHeadNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("rid", rid);
                        map.put("company", edtCo.getText().toString());
                        map.put("start_time", tvWorkStart.getText().toString());
                        map.put("end_time", tvBrith.getText().toString());
                        map.put("trade", hangye_id);
                        map.put("scale", co_id);
                        map.put("reid", id);
                        map.put("position",edtPosition.getText().toString());
                        map.put("description", edtDesc.getText().toString());
                        map.put("flag", "1");
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_EXPERENCE, new OkHttpClientManager.StringCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                                    ((DescribeDetailsActivity) mContext).finish();
                                } else {
                                    showToast(entity.getMsg());
                                }
                            }
                        });
                    }
                });
            }
        }
        return view;
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DescribeDetailsActivity) mContext).finish();
            }
        });
        textHeadNext.setText("确定");
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadTitle.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_riqi1, R.id.iv_riqi2, R.id.iv_hangye, R.id.iv_co, R.id.textHeadNext})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_riqi1:
                initTime(0);
                break;
            case R.id.iv_riqi2:
                initTime(1);
                break;
            case R.id.iv_hangye:
                initHangYe();
                break;
            case R.id.iv_co:
                Map<String, Object> school_map = new HashMap<>();
                school_map.put("ctype", "scale");
                OkHttpClientManager.postAsynJson(gson.toJson(school_map), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            final EducationBean comclass = gson.fromJson(gson.toJson(entity.getData()), EducationBean.class);
                            if (comclass.getComclass().size() > 0) {
                                final List<String> date = new ArrayList<String>();
                                for (int i = 0; i < comclass.getComclass().size(); i++) {
                                    date.add(comclass.getComclass().get(i).getName());
                                }
                                pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        for (int i = 0; i < comclass.getComclass().size(); i++) {
                                            if (date.get(options1).equals(comclass.getComclass().get(i).getName())) {
                                                tvCity.setText(date.get(options1));
                                                co_id = comclass.getComclass().get(i).getId();
                                            }
                                        }
                                    }
                                }).setTitleText("选择公司规模").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
                break;
            case R.id.textHeadNext:
                Map<String, Object> map = new HashMap<>();
                int rid = (int) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.rid, 0);
                map.put("rid", rid);
                map.put("company", edtCo.getText().toString());
                map.put("start_time", tvWorkStart.getText().toString());
                map.put("end_time", tvBrith.getText().toString());
                map.put("trade", hangye_id);
                map.put("position", edtPosition.getText().toString());
                map.put("scale", co_id);
                map.put("description",edtDesc.getText().toString());
                map.put("flag","0");
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.WORK_EXPERENCE, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                            ((DescribeDetailsActivity)mContext).finish();
                        }else {
                            showToast(entity.getMsg());
                        }
                    }
                });
                break;
        }
    }

    private void initHangYe() {
        Map<String, Object> map = new HashMap<>();
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ALL_TRADE, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    HangYeList list = gson.fromJson(gson.toJson(entity.getData()), HangYeList.class);
                    hang_list_one.addAll(list.getTradeList());
                    for (int q = 0; q < hang_list_one.size(); q++) {
                        one_list.add(hang_list_one.get(q).getTradeName());
                        ArrayList<String> data = new ArrayList<String>();
                        ArrayList<HanyeBean> d = new ArrayList<HanyeBean>();
                        for (int j = 0; j < hang_list_one.get(q).getChilds().size(); j++) {
                            data.add(hang_list_one.get(q).getChilds().get(j).getTradeName());
                            d.add(hang_list_one.get(q).getChilds().get(j));
                        }
                        two_list.add(data);
                        hang_list_two.add(d);
                    }

                    pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            String tx = hang_list_one.get(options1).getTradeName() +
                                    hang_list_two.get(options1).get(options2).getTradeName();
                            tvHangye.setText(tx);
                            hangye_id = hang_list_one.get(options1).getId() + "," + hang_list_two.get(options1).get(options2).getId();
                        }
                    }).setTitleText("选择行业").build();
                    pvCustomTime.setPicker(one_list, two_list);
                    pvCustomTime.show();
                }
            }
        });
    }

    private void initTime(final int type) {

        TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (type == 0) {
                    tvWorkStart.setText(sdf.format(date));
                } else if (type == 1) {
                    tvBrith.setText(sdf.format(date));
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "") //设置空字符串以隐藏单位提示
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(Calendar.getInstance())
                .build();

        timePickerView.show();
    }
}
