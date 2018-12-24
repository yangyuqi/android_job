package com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducitionBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.DescribeDetailsActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
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

public class UserResumeThreePageFragment extends BaseFragment {

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
    @BindView(R.id.tv_study_work)
    TextView tvStudyWork;
    Unbinder unbinder;
    @BindView(R.id.iv_rili1)
    ImageView ivRili1;
    @BindView(R.id.iv_rili2)
    ImageView ivRili2;
    @BindView(R.id.edt_school)
    EditText edtSchool;
    @BindView(R.id.iv_level)
    ImageView ivLevel;
    @BindView(R.id.cb)
    RadioButton cb;
    @BindView(R.id.cb_woman)
    RadioButton cbWoman;
    @BindView(R.id.edt_miaoshu)
    EditText edtMiaoshu;

    private OptionsPickerView pvCustomTime;

    private int class_id ;
    private int id ;
    private EducitionBean bean ;

    public static UserResumeThreePageFragment getInstance(SelectJianLiBean bean) {
        UserResumeThreePageFragment fragment = new UserResumeThreePageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_resume_three_page_layout, null);
        unbinder = ButterKnife.bind(this, view);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DescribeDetailsActivity)mContext).finish();
            }
        });

        textHeadTitle.setVisibility(View.GONE);
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("确定");
        if (getArguments() != null) {
            SelectJianLiBean jianLiBean = (SelectJianLiBean) getArguments().getSerializable("data");
            final int id = jianLiBean.getEducition_id();
            if (id!=0) {
                for (int j = 0; j < jianLiBean.getJi().getEducationList().size(); j++) {
                    if (jianLiBean.getJi().getEducationList().get(j).getId() == id) {
                        bean = jianLiBean.getJi().getEducationList().get(j);
                    }
                }
                if (bean!=null){
                    tvWorkStart.setText(bean.getStarttime());
                    tvBrith.setText(bean.getEndtime());
                    edtSchool.setText(bean.getSchool());
                    tvCity.setText(bean.getEducation());
                    class_id = bean.getEducationid();
                    tvStudyWork.setText(bean.getMajor());
                    edtMiaoshu.setText(bean.getDescription());
                }

                textHeadNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("rid",rid);
                        map.put("school",edtSchool.getText().toString());
                        map.put("education",class_id);
                        map.put("major",tvStudyWork.getText().toString());
                        map.put("description",edtMiaoshu.getText().toString());
                        map.put("flag","1");
                        map.put("reid",id);
                        map.put("starttime",tvWorkStart.getText().toString());
                        map.put("endtime",tvBrith.getText().toString());
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_EDU, new OkHttpClientManager.StringCallback() {
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
                    }
                });
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_rili1, R.id.iv_rili2, R.id.iv_level, R.id.textHeadNext})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_rili1:
                initTime(0);
                break;
            case R.id.iv_rili2:
                initTime(1);
                break;
            case R.id.textHeadNext:
                Map<String,Object> map = new HashMap<>();
                map.put("rid",rid);
                map.put("school",edtSchool.getText().toString());
                map.put("education",class_id);
                map.put("major",tvStudyWork.getText().toString());
                map.put("starttime",tvWorkStart.getText().toString());
                map.put("endtime",tvBrith.getText().toString());
                map.put("description",edtMiaoshu.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ADD_EDUCITON, new OkHttpClientManager.StringCallback() {
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
            case R.id.iv_level :
                Map<String, Object> school_map = new HashMap<>();
                school_map.put("ctype", "education");
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
                                                class_id = comclass.getComclass().get(i).getId();
                                            }
                                        }
                                    }
                                }).setTitleText("选择学历").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
                break;
        }
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
