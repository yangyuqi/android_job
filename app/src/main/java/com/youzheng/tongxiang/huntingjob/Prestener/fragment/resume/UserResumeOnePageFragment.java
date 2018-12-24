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
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Event.SelectJianLiBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.OccupationList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.OccupationListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
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

public class UserResumeOnePageFragment extends BaseFragment {


    @BindView(R.id.edt_name)
    EditText edtName;
    Unbinder unbinder;
    @BindView(R.id.tv_brith)
    TextView tvBrith;
    @BindView(R.id.cb)
    RadioButton cb;
    @BindView(R.id.cb_woman)
    RadioButton cbWoman;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.edt_zhiwei)
    EditText edtZhiwei;
    @BindView(R.id.edt_money)
    TextView edtMoney;
    @BindView(R.id.iv_money)
    ImageView ivMoney;
    @BindView(R.id.edt_address)
    TextView edtAddress;
    @BindView(R.id.iv_address)
    ImageView ivAddress;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.tv_hangye)
    TextView tvHangye;
    @BindView(R.id.iv_hangye)
    ImageView ivHangye;
    @BindView(R.id.tv_riqi)
    ImageView tvRiqi;
    @BindView(R.id.iv_city_address)
    ImageView ivCityAddress;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.tv_work_nature)
    TextView tvWorkNature;
    @BindView(R.id.iv_nature)
    ImageView ivNature;
    @BindView(R.id.tv_start_work)
    TextView tvStartWork;
    @BindView(R.id.iv_start_work)
    ImageView ivStartWork;
    @BindView(R.id.tv_xueli)
    TextView tvXueli;
    @BindView(R.id.iv_city_xueli)
    ImageView ivCityXueli;
    private SelectJianLiBean jianLiBean;
    private OptionsPickerView pvCustomTime;

    private String citys, hope_citys, hangye_id;
    private int state_id, money_id, jobs_nature ,xueli_class_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static UserResumeOnePageFragment getInstance(SelectJianLiBean bean) {
        UserResumeOnePageFragment fragment = new UserResumeOnePageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_resume_one_page_layout, null);
        unbinder = ButterKnife.bind(this, view);

        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DescribeDetailsActivity) mContext).finish();
            }
        });
        textHeadTitle.setVisibility(View.GONE);
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("确定");
        final int rid = (int) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.rid, 0);
        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("rid", rid);
                map.put("telephone", edtPhone.getText().toString());
                map.put("email", edtEmail.getText().toString());
                int sex;
                if (cb.isChecked()) {
                    sex = 1;
                } else {
                    sex = 0;
                }
                map.put("gender", sex);
                if (tvBrith.getText().toString().equals("")) {
                    showToast("选择出生日期");
                    return;
                }
                if (edtPhone.getText().toString().equals("")) {
                    showToast("选择联系电话");
                    return;
                }
                if (tvCity.getText().toString().equals("")) {
                    showToast("选择所在城市");
                    return;
                }
                if (tvStatus.getText().toString().equals("")) {
                    showToast("选择状态");
                    return;
                }
                if (edtZhiwei.getText().toString().equals("")) {
                    showToast("选择期望职位");
                    return;
                }
                if (edtMoney.getText().toString().equals("")) {
                    showToast("选择期望月薪");
                    return;
                }
                if (edtAddress.getText().toString().equals("")) {
                    showToast("选择期望工作地点");
                    return;
                }

                if (tvStartWork.getText().toString().equals("")){
                    showToast("填写开始工作时间");
                    return;
                }

                if (tvXueli.getText().toString().equals("")){
                    showToast("选择学历");
                    return;
                }

                map.put("birthdate", tvBrith.getText().toString());
                map.put("city", citys);
                map.put("hope_city", hope_citys);
                map.put("state", state_id);
                map.put("trade", hangye_id);
                map.put("position", edtZhiwei.getText().toString());
                map.put("wage", money_id);
                map.put("jobs_nature", jobs_nature);
                map.put("education",xueli_class_id);
                map.put("work_time",tvStartWork.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_BASE_INFO, new OkHttpClientManager.StringCallback() {
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

        if (getArguments() != null) {
            jianLiBean = (SelectJianLiBean) getArguments().getSerializable("data");
            edtName.setText(jianLiBean.getJi().getResume().getTruename());
            edtName.setEnabled(false);
            tvBrith.setText(jianLiBean.getJi().getResume().getBirthdate());
            if (jianLiBean.getJi().getResume().getGender() == 1) {
                cb.setChecked(true);
            } else {
                cbWoman.setChecked(true);
            }
            edtPhone.setText(jianLiBean.getJi().getResume().getTelephone());
            edtZhiwei.setText(jianLiBean.getJi().getResume().getPosition());
            edtEmail.setText(jianLiBean.getJi().getResume().getEmail());
            tvCity.setText(jianLiBean.getJi().getResume().getCitysName());
            edtMoney.setText(jianLiBean.getJi().getResume().getSalary());
            edtAddress.setText(jianLiBean.getJi().getResume().getHopeCityName());
            citys = jianLiBean.getJi().getResume().getCitys();
            hope_citys = jianLiBean.getJi().getResume().getHope_city();
            tvHangye.setText(jianLiBean.getJi().getResume().getTradeName());
            tvWorkNature.setText(jianLiBean.getJi().getResume().getJobs_natureName());
            hangye_id = jianLiBean.getJi().getResume().getTrade_id();
            money_id = jianLiBean.getJi().getResume().getWage();
            jobs_nature = jianLiBean.getJi().getResume().getJobs_nature();
            xueli_class_id = jianLiBean.getJi().getResume().getEducationid();
            tvXueli.setText(jianLiBean.getJi().getResume().getEducation());
            tvStartWork.setText(jianLiBean.getJi().getResume().getWork_time());
            if (jianLiBean.getJi().getResume().getStateName() != null) {
                tvStatus.setText(jianLiBean.getJi().getResume().getStateName());
                state_id = Integer.parseInt(jianLiBean.getJi().getResume().getState());
            }
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_riqi, R.id.iv_city_address, R.id.iv_address, R.id.iv_status, R.id.iv_money, R.id.iv_hangye, R.id.iv_nature,R.id.iv_start_work ,R.id.iv_city_xueli})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_nature:
                initNature();
                break;
            case R.id.iv_hangye:
                initHangYe();
                break;
            case R.id.tv_riqi:
                initTime(1);
                break;
            case R.id.iv_city_address:
                newInitPlace("1");
                break;
            case R.id.iv_address:
                newInitPlace("0");
                break;
            case R.id.iv_money:
                Map<String, Object> money_map = new HashMap<>();
                money_map.put("ctype", "wage");
                OkHttpClientManager.postAsynJson(gson.toJson(money_map), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
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
                                                edtMoney.setText(date.get(options1));
                                                money_id = comclass.getComclass().get(i).getId();
                                            }
                                        }
                                    }
                                }).setTitleText("选择月薪").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
                break;

            case R.id.iv_status:
                Map<String, Object> map = new HashMap<>();
                map.put("ctype", "current");
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
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
                                                tvStatus.setText(date.get(options1));
                                                state_id = comclass.getComclass().get(i).getId();
                                            }
                                        }
                                    }
                                }).setTitleText("当前状态").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
                break;

            case R.id.iv_city_xueli :
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
                                                tvXueli.setText(date.get(options1));
                                                xueli_class_id = comclass.getComclass().get(i).getId();
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

            case R.id.iv_start_work:
                initTime(2);
                break;

        }
    }

    private void initNature() {
        Map<String, Object> map = new HashMap<>();
        map.put("ctype", "jobs_nature");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
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
                                        tvWorkNature.setText(date.get(options1));
                                        jobs_nature = comclass.getComclass().get(i).getId();
                                    }
                                }
                            }
                        }).setTitleText("工作性质").build();
                        pvCustomTime.setPicker(date);
                        pvCustomTime.show();
                    }
                }
            }
        });
    }

    private void initTime(final int ntype) {

        TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (ntype==1) {
                    tvBrith.setText(sdf.format(date));
                }else {
                    tvStartWork.setText(sdf.format(date));
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

    public void newInitPlace(final String type) {
        final List<AreaListBean> options1Items = new ArrayList<>();
        final List<List<AreaListBean>> options2Items = new ArrayList<>();
        final List<String> list_data = new ArrayList<>();
        final List<List<String>> list_data_two = new ArrayList<>();
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtis.TONGXIANG_ADDRESS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    AreaList list = gson.fromJson(gson.toJson(entity.getData()), AreaList.class);
                    if (list.getAreaList().size() > 0) {
                        options1Items.addAll(list.getAreaList());
                        for (AreaListBean bean : list.getAreaList()) {
                            List<AreaListBean> m = new ArrayList<AreaListBean>();
                            List<String> n = new ArrayList<String>();
                            list_data.add(bean.getAreaName());
                            if (bean.getChilds().size() > 0) {
                                m.addAll(bean.getChilds());
                                for (AreaListBean bean1 : bean.getChilds()) {
                                    n.add(bean1.getAreaName());
                                }
                            }
                            options2Items.add(m);
                            list_data_two.add(n);
                        }
                    }
                    initDialog(type, options1Items, options2Items, list_data, list_data_two);
                }
            }

            private void initDialog(final String type, final List<AreaListBean> options1Items, final List<List<AreaListBean>> options2Items, final List<String> list_data, final List<List<String>> list_data_two) {
                OptionsPickerView pickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String tx = options1Items.get(options1).getAreaName();
                        String m = options2Items.get(options1).get(options2).getAreaName() == null ? "" : options2Items.get(options1).get(options2).getAreaName();

                        if (type.equals("1")) {
                            if (!m.equals("不限")) {
                                citys = options1Items.get(options1).getId() + "," + options2Items.get(options1).get(options2).getId();
                            } else {
                                citys = String.valueOf(options1Items.get(options1).getId());
                            }
                            if (tx != null && !tx.equals("")) {
                                tvCity.setText(tx + m);
                            }
                        } else if (type.equals("0")) {
                            if (!m.equals("不限")) {
                                hope_citys = options1Items.get(options1).getId() + "," + options2Items.get(options1).get(options2).getId();
                            } else {
                                hope_citys = String.valueOf(options1Items.get(options1).getId());
                            }
                            if (tx != null && !tx.equals("")) {
                                edtAddress.setText(tx + m);
                            }
                        }
                    }
                }).setTitleText("城市选择")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentTextSize(20)
                        .setOutSideCancelable(false)// default is true
                        .build();

                pickerView.setPicker(list_data, list_data_two);//三级选择器
                pickerView.show();
            }
        });
    }

    private void initHangYe() {

        final List<String> one_list = new ArrayList<>();
        final List<List<String>> two_list = new ArrayList<>();
        final List<OccupationListBean> hang_list_one = new ArrayList<>();
        final List<List<OccupationListBean>> hang_list_two = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.GET_ALL_CATEGORY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    final OccupationList list = gson.fromJson(gson.toJson(entity.getData()), OccupationList.class);
                    hang_list_one.addAll(list.getOccupationList());
                    for (int i = 0; i < list.getOccupationList().size(); i++) {
                        one_list.add(list.getOccupationList().get(i).getOccupationName());
                    }

                    for (int i = 0; i < hang_list_one.size(); i++) {
                        List<OccupationListBean> CityList = new ArrayList<>();
                        List<List<OccupationListBean>> Province_AreaList = new ArrayList<>();

                        List<String> c_city = new ArrayList<>();
                        List<List<String>> p_city = new ArrayList<>();


                        for (int c = 0; c < hang_list_one.get(i).getChilds().size(); c++) {
                            OccupationListBean childBean = hang_list_one.get(i).getChilds().get(c);
                            CityList.add(childBean);

                            c_city.add(hang_list_one.get(i).getChilds().get(c).getOccupationName());


                            List<OccupationListBean> City_AreaList = new ArrayList<>();

                            List<String> c_list = new ArrayList<>();

                            if (hang_list_one.get(i).getChilds().get(c).getOccupationName() == null
                                    || hang_list_one.get(i).getChilds().get(c).getChilds().size() == 0) {
                                City_AreaList.add(new OccupationListBean());
                                c_list.add("");
                            } else {
                                for (int d = 0; d < hang_list_one.get(i).getChilds().get(c).getChilds().size(); d++) {
                                    OccupationListBean AreaName = hang_list_one.get(i).getChilds().get(c).getChilds().get(d);

                                    City_AreaList.add(AreaName);
                                    c_list.add(AreaName.getOccupationName());
                                }
                                Province_AreaList.add(City_AreaList);
                                p_city.add(c_list);
                            }
                        }

                        hang_list_two.add(CityList);
                        two_list.add(c_city);
                    }
                    if (hang_list_two.size() > 0 && hang_list_one.size() > 0) {
                        initDialog2(one_list, two_list, hang_list_one, hang_list_two);
                    }
                }
            }
        });
    }

    private void initDialog2(List<String> one_list, List<List<String>> two_list, final List<OccupationListBean> hang_list_one, final List<List<OccupationListBean>> hang_list_two) {
        OptionsPickerView pickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = hang_list_two.get(options1).get(options2).getOccupationName();

                hangye_id = hang_list_one.get(options1).getId() + "," + hang_list_two.get(options1).get(options2).getId();
//                hangye_id_last = String.valueOf(hang_list_two.get(options1).get(options2).getId());
                if (tx != null && !tx.equals("")) {
                    tvHangye.setText(tx);
                }
            }
        }).setTitleText("选择行业")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        pickerView.setPicker(one_list, two_list, null);//三级选择器
        pickerView.show();
    }
}
