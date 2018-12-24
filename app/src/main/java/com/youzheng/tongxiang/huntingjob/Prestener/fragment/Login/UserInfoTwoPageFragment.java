package com.youzheng.tongxiang.huntingjob.Prestener.fragment.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;

import com.bigkoo.pickerview.TimePickerView;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HangYeList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HanyeBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.AreaInfoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.AreaInfoChildBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.UserBaseBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.UserBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FillInfoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.ActiivtyStack;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.dialog.RegisterSuccessDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

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

public class UserInfoTwoPageFragment extends BaseFragment {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;
    @BindView(R.id.iv_select_time)
    ImageView ivSelectTime;
    @BindView(R.id.tv_time_time)
    TextView tvTimeTime;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.iv_address)
    ImageView ivAddress;

    public String hope_city;
    @BindView(R.id.tv_hangye)
    TextView tvHangye;
    @BindView(R.id.iv_hangye)
    ImageView ivHangye;

    private UserBaseBean baseBean;
    private OptionsPickerView pvCustomTime;
    private String hanye ;

    private ArrayList<String> one_list = new ArrayList<>();
    private ArrayList<ArrayList<String>> two_list = new ArrayList<>();
    private ArrayList<HanyeBean> hang_list_one = new ArrayList<>();
    private ArrayList<ArrayList<HanyeBean>> hang_list_two = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_info_two_page_layout, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.btn_login, R.id.iv_select_time, R.id.iv_address,R.id.iv_hangye})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (tvTimeTime.getText().toString().equals("")) {
                    showToast("填写开始工作时间");
                    return;
                }
                if (edtPhone.getText().toString().equals("")) {
                    showToast("填写手机号");
                    return;
                }
                if (edtEmail.getText().toString().equals("")) {
                    showToast("填写电子邮箱");
                    return;
                }
                if (tvCity.getText().toString().equals("")) {
                    showToast("填写所在城市");
                    return;
                }
                if (tvHangye.getText().toString().equals("")){
                    showToast("填写行业");
                    return;
                }

                if (baseBean != null) {
                    baseBean.setResidence(hope_city);
                    baseBean.setWork_time(tvTimeTime.getText().toString());
                    baseBean.setTelephone(edtPhone.getText().toString());
                    baseBean.setEmail(edtEmail.getText().toString());
                    baseBean.setTrade(hanye);
                }
                OkHttpClientManager.postAsynJson(gson.toJson(baseBean), UrlUtis.BASE_RESUME, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            MobclickAgent.onEvent(mContext,"insertResume");
                            try {
                                JSONObject object = new JSONObject(gson.toJson(entity.getData()));
                                int rid = object.getInt("rid");
                                SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.rid,rid);
                                doMYLogin();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
//                startActivity(new Intent(mContext, UserResumeActivity.class));
                break;
            case R.id.iv_select_time:
                initTime();
                break;
            case R.id.iv_address:
                newInitPlace("0");
                break;

            case R.id.iv_hangye:
                Map<String,Object> map = new HashMap<>();
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ALL_TRADE, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                            HangYeList list = gson.fromJson(gson.toJson(entity.getData()),HangYeList.class);
                            hang_list_one.addAll(list.getTradeList());
                            for (int q = 0 ; q<hang_list_one.size();q++){
                                one_list.add(hang_list_one.get(q).getTradeName());
                                ArrayList<String> data = new ArrayList<String>();
                                ArrayList<HanyeBean> d = new ArrayList<HanyeBean>();
                                for (int j = 0 ;j<hang_list_one.get(q).getChilds().size();j++){
                                    data.add(hang_list_one.get(q).getChilds().get(j).getTradeName());
                                    d.add(hang_list_one.get(q).getChilds().get(j));
                                }
                                two_list.add(data);
                                hang_list_two.add(d);
                            }

                            pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                                @Override
                                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                    String tx = hang_list_one.get(options1).getTradeName()+
                                            hang_list_two.get(options1).get(options2).getTradeName();
                                    tvHangye.setText(tx);
                                    hanye = hang_list_one.get(options1).getId()+","+hang_list_two.get(options1).get(options2).getId();
                                }
                            }).setTitleText("选择行业").build();
                            pvCustomTime.setPicker(one_list,two_list);
                            pvCustomTime.show();
                        }
                    }
                });

                break;
        }
    }

    private void doMYLogin() {
        Map<String, Object> map = new HashMap<>();
        String access_token = (String) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.access_token, "");
        map.put("access_token", access_token);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.TOKEN_LOGIN, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    UserBean bean = gson.fromJson(gson.toJson(entity.getData()),UserBean.class);
                    SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.access_token,bean.getAccess_token());
                    SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.uid,bean.getUser_id());
                    SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.rid, bean.getRid());
                    ActiivtyStack.getScreenManager().clearAllActivity();
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                }
            }
        });
    }


    @Subscribe
    public void onEvent(UserBaseBean s) {
        if (s != null) {
            baseBean = s;
        }
    }

    private void initTime() {

        TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                tvTimeTime.setText(sdf.format(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "") //设置空字符串以隐藏单位提示
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(Calendar.getInstance())
                .build();

        timePickerView.show();

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
                                tvCity.setText(tx+m);
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
