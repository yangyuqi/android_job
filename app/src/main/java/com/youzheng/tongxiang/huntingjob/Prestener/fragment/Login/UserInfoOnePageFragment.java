package com.youzheng.tongxiang.huntingjob.Prestener.fragment.Login;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;

import com.bigkoo.pickerview.TimePickerView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Event.EventModel;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.AreaInfoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.AreaInfoChildBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.UserBaseBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import org.greenrobot.eventbus.EventBus;

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

public class UserInfoOnePageFragment extends BaseFragment {

    @BindView(R.id.tv_brith)
    TextView tvBrith;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;
    @BindView(R.id.iv_select_time)
    ImageView ivSelectTime;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_zhiwei)
    EditText edtZhiwei;
    @BindView(R.id.edt_address)
    TextView edtAddress;
    @BindView(R.id.iv_school)
    ImageView ivSchool;

    public int class_da, money_da, sex_da = 1;
    public String hope_city ;
    @BindView(R.id.edt_money)
    TextView edtMoney;
    @BindView(R.id.iv_money)
    ImageView ivMoney;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.rb)
    RadioButton rb;
    @BindView(R.id.rb_two)
    RadioButton rbTwo;
    @BindView(R.id.iv_address)
    ImageView ivAddress;

    private List<AreaInfoChildBean> options1Items = new ArrayList<>();
    private List<List<AreaInfoChildBean>> options2Items = new ArrayList<>();
    private List<List<List<AreaInfoChildBean>>> options3Items = new ArrayList<>();
    private List<String> list_data = new ArrayList<>();
    private List<List<String>> list_data_two = new ArrayList<>();
    private List<List<List<String>>> list_data_third = new ArrayList<>();

    private OptionsPickerView pvCustomTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_info_one_page_layout, null);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    private void initDate() {
        OkHttpClientManager.postAsynJson("", UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }


    private void initView() {
        Calendar mycalendar=Calendar.getInstance();
        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == rb.getId()) {
                    sex_da = 1;
                } else if (i == rbTwo.getId()) {
                    sex_da = 0;
                }
            }
        });
    }

    @OnClick({R.id.iv_select_time, R.id.btn_login, R.id.iv_school, R.id.iv_money,R.id.iv_address})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_select_time:
                initTime();
                break;
            case R.id.btn_login:
                if (edtName.getText().toString().equals("")) {
                    showToast("填写姓名");
                    return;
                }
                if (tvBrith.getText().toString().equals("")) {
                    showToast("填写出生日期");
                    return;
                }

                if (tvClass.getText().toString().equals("")) {
                    showToast("填写最高学历");
                    return;
                }
                if (edtZhiwei.getText().toString().equals("")) {
                    showToast("填写职位");
                    return;
                }
                if (edtMoney.getText().toString().equals("")) {
                    showToast("填写期望月薪");
                }

                if (edtAddress.getText().toString().equals("")) {
                    showToast("填写期望地点");
                    return;
                }
                UserBaseBean baseBean = new UserBaseBean();
                int uid = (int) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.uid, 0);
                baseBean.setUid(uid);
                baseBean.setTruename(edtName.getText().toString());
                baseBean.setGender(sex_da);
                baseBean.setBirthdate(tvBrith.getText().toString());
                baseBean.setPosition(edtZhiwei.getText().toString());
                baseBean.setEducation(class_da);
                baseBean.setHope_city(hope_city);
                baseBean.setWage(money_da);
                EventBus.getDefault().post(baseBean);
                EventBus.getDefault().post(EventModel.GO_UPPER_PAGE);
                break;

            case R.id.iv_school:
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
                                                tvClass.setText(date.get(options1));
                                                class_da = comclass.getComclass().get(i).getId();
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
                                                money_da = comclass.getComclass().get(i).getId();
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

            case R.id.iv_address :
                newInitPlace("0");
//                String add = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.AREAALL,"");
//                if (!add.equals("")){
//                    initAddress(add);
//                }else {
//                final ProgressDialog dialog2 = ProgressDialog.show(mContext, "提示", "正在加载中...");
//                dialog2.show();
//                    OkHttpClientManager.postAsynJson("", UrlUtis.ALL_ADDRESS, new OkHttpClientManager.StringCallback() {
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//                            dialog2.dismiss();
//                        }
//
//                        @Override
//                        public void onResponse(String response) {
//                            BaseEntity entity = gson.fromJson(response, BaseEntity.class);
//                            if (entity.getCode().equals(PublicUtils.SUCCESS)) {
//                                initAddress(response,dialog2);
//                            }
//                        }
//                    });
//                }
                break;
        }
    }

    private void initDialog() {
        OptionsPickerView pickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options1Items.get(options1).getAreaName()+
                        options2Items.get(options1).get(options2).getAreaName()+
                        options3Items.get(options1).get(options2).get(options3).getAreaName();

                hope_city = options1Items.get(options1).getId()+","+options2Items.get(options1).get(options2).getId()+","+options3Items.get(options1).get(options2).get(options3).getId();
                if (tx!=null&&!tx.equals("")) {
                    edtAddress.setText(tx);
                }

                Toast.makeText(mContext,tx,Toast.LENGTH_SHORT).show();
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        pickerView.setPicker(list_data, list_data_two,list_data_third);//三级选择器
        pickerView.show();
    }

    private int year;
    private int month;
    private int day;

    private void initTime() { ;

        TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                tvBrith.setText(sdf.format(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "") //设置空字符串以隐藏单位提示
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(Calendar.getInstance())
                .build();

        timePickerView.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initAddress(String areaall, ProgressDialog dialog2) {
        AreaInfoBean bean = gson.fromJson(areaall,AreaInfoBean.class);
        options1Items.addAll(bean.getData().get("areaList"));

            for (int q = 0 ; q<options1Items.size();q++){
                list_data.add(options1Items.get(q).getAreaName());
            }


            for (int i = 0 ;i<options1Items.size();i++){
                List<AreaInfoChildBean> CityList = new ArrayList<>();//该省的城市列表（第二级）
                List<List<AreaInfoChildBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                List<String> c_city = new ArrayList<>();
                List<List<String>> p_city = new ArrayList<>();


                for (int c = 0 ;c<options1Items.get(i).getChilds().size();c++) {
                    AreaInfoChildBean childBean = options1Items.get(i).getChilds().get(c);
                    CityList.add(childBean);//添加城市

                    c_city.add(options1Items.get(i).getChilds().get(c).getAreaName());


                    List<AreaInfoChildBean> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    List<String> c_list = new ArrayList<>();

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (options1Items.get(i).getChilds().get(c).getAreaName() == null
                            || options1Items.get(i).getChilds().get(c).getChilds().size() == 0) {
                        City_AreaList.add(new AreaInfoChildBean("", "", null));
                        c_list.add("");
                    } else {
                        for (int d = 0; d < options1Items.get(i).getChilds().get(c).getChilds().size(); d++) {//该城市对应地区所有数据
                            AreaInfoChildBean AreaName = options1Items.get(i).getChilds().get(c).getChilds().get(d);

                            City_AreaList.add(AreaName);//添加该城市所有地区数据
                            c_list.add(AreaName.getAreaName());
                        }
                        Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                        p_city.add(c_list);
                    }
                }

                /**
                 * 添加城市数据
                 */
                options2Items.add(CityList);

                /**
                 * 添加地区数据
                 */
                options3Items.add(Province_AreaList);

                list_data_two.add(c_city);
                list_data_third.add(p_city);
            }
        dialog2.dismiss();
            if (options2Items.size()>0&&options3Items.size()>0&&options1Items.size()>0){
                initDialog();
            }

    }

    public void newInitPlace(final String type){
        final List<AreaListBean> options1Items = new ArrayList<>();
        final List<List<AreaListBean>> options2Items = new ArrayList<>();
        final  List<String> list_data = new ArrayList<>();
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
                        for (AreaListBean bean : list.getAreaList()){
                            List<AreaListBean> m = new ArrayList<AreaListBean>();
                            List<String> n = new ArrayList<String>();
                            list_data.add(bean.getAreaName());
                            if (bean.getChilds().size()>0){
                                m.addAll(bean.getChilds());
                                for (AreaListBean bean1 : bean.getChilds()){
                                    n.add(bean1.getAreaName());
                                }
                            }
                            options2Items.add(m);
                            list_data_two.add(n);
                        }
                    }
                    initDialog(type,options1Items,options2Items,list_data,list_data_two);
                }
            }

            private void initDialog(final String type, final List<AreaListBean> options1Items,final List<List<AreaListBean>> options2Items, final List<String> list_data, final List<List<String>> list_data_two) {
                OptionsPickerView pickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String tx = options1Items.get(options1).getAreaName() ;
                        String  m =  options2Items.get(options1).get(options2).getAreaName()==null?"": options2Items.get(options1).get(options2).getAreaName();

                        if (type.equals("1")) {
                        }else if (type.equals("0")){
                            if (!m.equals("不限")) {
                                hope_city = options1Items.get(options1).getId() + "," + options2Items.get(options1).get(options2).getId();
                            }else {
                                hope_city = String.valueOf(options1Items.get(options1).getId());
                            }
                            if (tx != null && !tx.equals("")) {
                                edtAddress.setText(tx+m);
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

}
