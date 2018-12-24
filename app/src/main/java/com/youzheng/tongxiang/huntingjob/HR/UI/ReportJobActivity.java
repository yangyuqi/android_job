package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.AddressJw;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.OccupationList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.OccupationListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.VideoCommentBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.YouHuoListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.MapActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.GuideVideoActivity;
import com.youzheng.tongxiang.huntingjob.R;
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
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2018/5/14.
 */

public class ReportJobActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.edt_address)
    TextView edtAddress;
    @BindView(R.id.tv_property)
    TextView tvProperty;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.edt_lower_moeny)
    EditText edtLowerMoeny;
    @BindView(R.id.edt_high_money)
    EditText edtHighMoney;
    @BindView(R.id.cb_)
    CheckBox cb;
    @BindView(R.id.tv_educition)
    TextView tvEducition;
    @BindView(R.id.tv_work_year)
    TextView tvWorkYear;
    @BindView(R.id.edt_youhuo)
    TextView edtYouhuo;
    @BindView(R.id.edt_describe)
    EditText edtDescribe;
    @BindView(R.id.edt_contant_name)
    EditText edtContantName;
    @BindView(R.id.edt_contant_phone)
    EditText edtContantPhone;
    @BindView(R.id.edt_contant_email)
    EditText edtContantEmail;
    @BindView(R.id.edt_keyWord)
    EditText edtKeyWord;
    @BindView(R.id.edt_requrie)
    EditText edtRequrie;
    @BindView(R.id.edt_co_address)
    EditText edtCoAddress;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.edt_need_num)
    EditText edtNeedNum;
    @BindView(R.id.ll_add_video)
    LinearLayout llAddVideo;

    private int wageFace = 0, class_id, experience, jobsNature;
    ;
    private String hangye_id, hangye_id_last, citys, city_id;
    private OptionsPickerView pvCustomTime;

    private String jid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_job_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadTitle.setText("发布职位");
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("确定");
        jid = getIntent().getStringExtra("jid");
        if (jid != null) {
            initData();
        }

        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("report_job_layout");
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("report_job_layout");
        MobclickAgent.onPause(mContext);
    }

    private String videoComment ,videoUrl ,image_index ;
    private float width,height ;

    @Subscribe
    public void onEvent(VideoCommentBean videoCommentBean){
        if (videoCommentBean!=null){
            videoComment = videoCommentBean.getComment();
            videoUrl = videoCommentBean.getUrl();
            image_index = videoCommentBean.getVideo_img();
            width = videoCommentBean.getWidth() ;
            height =videoCommentBean.getHeight();
        }

    }

    private void initEvent() {
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edtLowerMoeny.setText("");
                    edtHighMoney.setText("");
                    edtLowerMoeny.setEnabled(false);
                    edtHighMoney.setEnabled(false);
                } else {
                    edtLowerMoeny.setEnabled(true);
                    edtHighMoney.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.tv_type, R.id.textHeadNext, R.id.edt_address, R.id.tv_educition, R.id.tv_work_year, R.id.tv_property, R.id.tv_select_address, R.id.edt_youhuo,R.id.ll_add_video})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                initHangYe();
                break;
            case R.id.textHeadNext:
                doAction();
                break;
            case R.id.edt_address:
                initplace("1");
                break;
            case R.id.tv_educition:
                initEducition();
                break;
            case R.id.tv_work_year:
                initWorkYear();
                break;
            case R.id.tv_property:
                initProperty();
                break;
            case R.id.tv_select_address:
                Intent intent = new Intent(mContext, MapActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.edt_youhuo:
                startActivityForResult(new Intent(mContext, YouHuoActivity.class), 2);
                break;

            case R.id.ll_add_video :
//                Intent intd = new Intent(mContext, GoVideoActivity.class);
//                intd.putExtra("type","2");
//                startActivity(intd);
                Intent intent1 = new Intent(mContext, GuideVideoActivity.class);
                intent1.putExtra("type","2");
                startActivity(intent1);
                break;
        }
    }

    private void initProperty() {
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
                                        tvProperty.setText(date.get(options1));
                                        jobsNature = comclass.getComclass().get(i).getId();
                                    }
                                }
                            }
                        }).setTitleText("选择工作性质").build();
                        pvCustomTime.setPicker(date);
                        pvCustomTime.show();
                    }
                }
            }
        });
    }

    private void initWorkYear() {
        Map<String, Object> map = new HashMap<>();
        map.put("ctype", "experience");
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
                                        tvWorkYear.setText(date.get(options1));
                                        experience = comclass.getComclass().get(i).getId();
                                    }
                                }
                            }
                        }).setTitleText("选择工作经验").build();
                        pvCustomTime.setPicker(date);
                        pvCustomTime.show();
                    }
                }
            }
        });
    }

    private void initEducition() {
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
                                        tvEducition.setText(date.get(options1));
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
                hangye_id_last = String.valueOf(hang_list_two.get(options1).get(options2).getId());
                if (tx != null && !tx.equals("")) {
                    tvType.setText(tx);
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

    private void doAction() {
        if (edtName.getText().toString().equals("")) {
            showToast("请填写职位名称");
            return;
        }
        if (tvType.getText().toString().equals("")) {
            showToast("请填写职位类别");
            return;
        }

        if (edtAddress.getText().toString().equals("")) {
            showToast("请填写地区");
            return;
        }
        if (cb.isChecked()) {
            wageFace = 1;
        } else {
            wageFace = 0;
            if (edtLowerMoeny.getText().toString().equals("")) {
                showToast("请填写最低薪资");
                return;
            }
            if (edtHighMoney.getText().toString().equals("")) {
                showToast("请填写最高薪资");
                return;
            }
        }

        if (tvEducition.getText().toString().equals("")) {
            showToast("请选择学历要求");
            return;
        }
        if (tvWorkYear.getText().toString().equals("")) {
            showToast("请选择工作年限");
            return;
        }
        if (edtYouhuo.getText().toString().equals("")) {
            showToast("请填写职位诱惑");
            return;
        }
        if (edtDescribe.getText().toString().equals("")) {
            showToast("请填写职位介绍");
            return;
        }

        if (edtNeedNum.getText().toString().equals("")) {
            showToast("请填招聘人数");
            return;
        }

        if (edtRequrie.getText().toString().equals("")) {
            showToast("请填写任职要求");
            return;
        }
        if (tvProperty.getText().toString().equals("")) {
            showToast("请填写职位性质");
            return;
        }

        if (edtContantName.getText().toString().equals("")) {
            showToast("请填写联系人");
            return;
        }
        if (edtContantPhone.getText().toString().equals("")) {
            showToast("请填写联系人电话");
            return;
        }
        if (edtContantEmail.getText().toString().equals("")) {
            showToast("请填写联系人邮箱");
            return;
        }

        if (edtCoAddress.getText().toString().equals("")) {
            showToast("请填写地址");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("title", edtName.getText().toString());
//        map.put("jobType", hangye_id_last);
        map.put("jobType", hangye_id);
//        map.put("cityid", Integer.parseInt(city_id));
        map.put("cityId", citys);
        if (!cb.isChecked()) {
            map.put("wageMin", Double.parseDouble(edtLowerMoeny.getText().toString()));
            map.put("wageMax", Double.parseDouble(edtHighMoney.getText().toString()));
        }
        map.put("wageFace", wageFace);
        map.put("education", class_id);
        map.put("experience", experience);
        map.put("jobTag", edtYouhuo.getText().toString());
        map.put("jobTagId", youHuo_id);
        map.put("keyword", edtKeyWord.getText().toString());
        map.put("description", edtDescribe.getText().toString());
        map.put("requirement", edtRequrie.getText().toString());
        map.put("jobsNature", jobsNature);
        map.put("contact", edtContantName.getText().toString());
        map.put("mobilePhone", edtContantPhone.getText().toString());
        map.put("email", edtContantEmail.getText().toString());
        map.put("address", edtCoAddress.getText().toString());
        map.put("position", String.valueOf(jing) + "," + String.valueOf(wei));
        map.put("count", Integer.parseInt(edtNeedNum.getText().toString()));
        if (videoComment!=null&&videoUrl!=null) {
            map.put("videoComment", videoComment);
            map.put("videoUrl", videoUrl);
            map.put("videoIndexImg",image_index);
            map.put("height",height);
            map.put("width",width);
        }
        if (jid == null) {
            map.put("flag", 0);
        } else {
            map.put("flag", "1");
            map.put("jid", jid);
        }
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.JOB_PUBLISH, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                MobclickAgent.onEvent(mContext,"publish");
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    finish();
                } else {
                    showToast(entity.getMsg());
                }
            }
        });
    }

    List<AreaListBean> options1Items = new ArrayList<>();
    List<List<AreaListBean>> options2Items = new ArrayList<>();
    List<String> list_data = new ArrayList<>();
    List<List<String>> list_data_two = new ArrayList<>();

    public void initplace(final String type) {
        options1Items.clear();
        options2Items.clear();
        list_data.clear();
        list_data_two.clear();
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
                    initDialog("1");
                }
            }
        });

    }


    private void initDialog(final String type) {
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

    private double jing, wei;

    private ArrayList<YouHuoListBean> youHuoList;
    private String youHuo_id = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            AddressJw jw = (AddressJw) data.getSerializableExtra("data");
            jing = jw.getB();
            wei = jw.getA();
            edtCoAddress.setText(data.getStringExtra("title"));
        }
        if (requestCode == 2 && resultCode == 2) {
            youHuoList = (ArrayList<YouHuoListBean>) data.getSerializableExtra("data");
            StringBuilder string = new StringBuilder();
            youHuo_id = "";
            for (YouHuoListBean bean : youHuoList) {
                string.append(bean.getName() + ",");
                if (youHuo_id.equals("")) {
                    youHuo_id = String.valueOf(bean.getId());
                } else {
                    youHuo_id = youHuo_id + "," + bean.getId();
                }
            }
            edtYouhuo.setText(string.toString());
        }
    }


    private void initData() {
        final Map<String, Object> map = new HashMap<>();
        map.put("jid", jid);
        map.put("uid", uid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.JOB_DETAILS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    JobBean jobBean = gson.fromJson(gson.toJson(entity.getData()), JobBean.class);
                    JobBeanDetails details = jobBean.getJob();
                    edtName.setText(details.getTitle());
                    tvType.setText(details.getTradeName());
                    edtAddress.setText(details.getCitysName());
                    if (details.getWage_face() == 1) {
                        cb.setChecked(true);
                    } else if (details.getWage_face() == 0) {
                        int low = (int) details.getWage_min();
                        int high = (int) details.getWage_max();
                        edtLowerMoeny.setText("" + low);
                        edtHighMoney.setText("" + high);
                    }
                    tvEducition.setText(details.getEducation());
                    tvWorkYear.setText(details.getExperience());
                    edtYouhuo.setText(details.getJobtag());
                    experience = details.getExperienceid();
                    class_id = details.getEducationid();
                    jobsNature = details.getJobs_natureid();
                    edtKeyWord.setText(details.getKeyword());
                    edtDescribe.setText(details.getDescription());
                    edtRequrie.setText(details.getRequirement());
                    tvProperty.setText(details.getJobs_nature());
                    edtContantName.setText(details.getContact());
                    edtContantPhone.setText(details.getMobile_phone() != null ? details.getMobile_phone() : details.getTelphone());
                    edtContantEmail.setText(details.getEmail());
                    edtCoAddress.setText(details.getAddress());
                    edtNeedNum.setText("" + details.getCount());
                    String[] d = details.getPosition().split(",");
                    jing = Double.parseDouble(d[0]);
                    wei = Double.parseDouble(d[1]);
                    hangye_id = details.getJobtypes();
                    youHuo_id = details.getJobTagId();
                    citys = details.getCitys();
                }
            }
        });
    }
}
