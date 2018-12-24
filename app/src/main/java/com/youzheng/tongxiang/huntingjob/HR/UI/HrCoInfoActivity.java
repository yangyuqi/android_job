package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.CoMainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.AddressJw;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HangYeList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HanyeBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.YouHuoListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.PhotoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.UserBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.MapActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.GuideVideoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.UserCenterActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.ActiivtyStack;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.dialog.BottomPhotoDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2018/5/15.
 */

public class HrCoInfoActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_typr)
    EditText edtTypr;
    @BindView(R.id.edt_address)
    TextView edtAddress;
    @BindView(R.id.edt_short_num)
    TextView edtShortNum;
    @BindView(R.id.edt_common_address)
    TextView edtCommonAddress;
    @BindView(R.id.edt_play_type)
    TextView edtPlayType;
    @BindView(R.id.edt_net_address)
    EditText edtNetAddress;
    @BindView(R.id.edt_co_userful)
    TextView edtCoUserful;
    @BindView(R.id.edt_contact_name)
    EditText edtContactName;
    @BindView(R.id.edt_contact_phone)
    EditText edtContactPhone;
    @BindView(R.id.edt_contact_email)
    EditText edtContactEmail;
    @BindView(R.id.edt_contact_address)
    TextView edtContactAddress;
    @BindView(R.id.edt_co_jianjie)
    EditText edtCoJianjie;
    @BindView(R.id.edt_co_intro)
    EditText edtCoIntro;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.edt_regist_money)
    EditText edtRegistMoney;
    @BindView(R.id.ll_add_video)
    LinearLayout llAddVideo;
    @BindView(R.id.iv_name)
    ImageView ivName;


    private OptionsPickerView pvCustomTime;
    private int co_property, co_num;
    private String hanye, position, citys;

    private String type ,photo_url ;
    ImageCaptureManager captureManager;

    private ArrayList<YouHuoListBean> youHuoList;
    private String youHuo_id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_co_info_layout);
        ButterKnife.bind(this);
        initView();
        type = getIntent().getStringExtra("type");
        initData();
        edtCoUserful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(mContext, YouHuoActivity.class), 2);
            }
        });
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SEE_CO_INFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    CoBean coBean = gson.fromJson(gson.toJson(entity.getData()), CoBean.class);
                    edtName.setText(coBean.getCompany().getName());
                    edtTypr.setText(coBean.getCompany().getShortName());
                    edtAddress.setText(coBean.getCompany().getCtypeName());
                    edtShortNum.setText(coBean.getCompany().getScaleName());
                    edtCommonAddress.setText(coBean.getCompany().getCityName());
                    edtPlayType.setText(coBean.getCompany().getTradeName());
                    edtNetAddress.setText(coBean.getCompany().getNeturl());
                    edtCoUserful.setText(coBean.getCompany().getJobtag());
                    edtContactName.setText(coBean.getCompany().getContact());
                    Glide.with(mContext).load(coBean.getCompany().getLogo()).error(R.mipmap.gggggroup).into(ivName);
                    if (!String.valueOf(coBean.getCompany().getRegisterCapital()).equals("0.0")) {
                        int k = (int) coBean.getCompany().getRegisterCapital();
                        edtRegistMoney.setText("" + k );
                    }
                    if (coBean.getCompany().getMobilePhone() != null) {
                        edtContactPhone.setText(coBean.getCompany().getMobilePhone());
                    } else if (coBean.getCompany().getTelphone() != null) {
                        edtContactPhone.setText(coBean.getCompany().getTelphone());
                    }
                    edtContactEmail.setText(coBean.getCompany().getEmail());
                    edtContactAddress.setText(coBean.getCompany().getAddress());
                    edtCoJianjie.setText(coBean.getCompany().getSummary());
//                    edtCoIntro.setText(coBean.getCompany().getIntroduction());
                    position = coBean.getCompany().getPosition();
                    co_property = coBean.getCompany().getCtype();
                    co_num = coBean.getCompany().getScale();
                    citys = coBean.getCompany().getPro_city_dist();
                    hanye = coBean.getCompany().getTrade();
                    photo_url = coBean.getCompany().getPhoto();
                    youHuo_id = coBean.getCompany().getJobTagId();
                }
            }
        });
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == null) {
                    finish();
                } else if (type.equals("2")) {
                    showToast("请填写公司信息");
                }
            }
        });
        textHeadTitle.setText("企业信息");
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("确定");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("hr_co_info_layout");
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("hr_co_info_layout");
        MobclickAgent.onPause(mContext);
    }

    @OnClick({R.id.edt_address, R.id.edt_short_num, R.id.edt_common_address, R.id.edt_play_type, R.id.tv_select_address, R.id.textHeadNext, R.id.ll_add_video,R.id.iv_name})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.edt_address:
                initProperty();
                break;
            case R.id.edt_short_num:
                initNum();
                break;
            case R.id.edt_common_address:
                newInitPlace("0");
                break;
            case R.id.edt_play_type:
                initHangYe();
                break;
            case R.id.textHeadNext:
                doAction();
                break;
            case R.id.tv_select_address:
                Intent intent = new Intent(mContext, MapActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_add_video:
                Intent intd = new Intent(mContext, GuideVideoActivity.class);
                intd.putExtra("type", "1");
                startActivity(intd);
                break;
            case R.id.iv_name :
                final BottomPhotoDialog dialog = new BottomPhotoDialog(mContext, R.layout.view_popupwindow);
                dialog.show();
                dialog.getTv_pick_phone().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        takePhoto();
                    }
                });

                dialog.getTv_select_photo().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        selectPhoto();
                    }
                });
                break;
        }
    }



    private void doAction() {

        if (photo_url==null){
            showToast("请上传企业LOGO");
            return;
        }

        if (edtName.getText().toString().equals("")) {
            showToast("请填写企业名称");
            return;
        }
        if (edtTypr.getText().toString().equals("")) {
            showToast("请填写企业简称");
            return;
        }

        if (edtAddress.getText().toString().equals("")) {
            showToast("请填写公司性质");
            return;
        }

        if (edtShortNum.getText().toString().equals("")) {
            showToast("请填写公司规模");
            return;
        }

        if (edtCommonAddress.getText().toString().equals("")) {
            showToast("请选择公司地址");
            return;
        }
        if (edtPlayType.getText().toString().equals("")) {
            showToast("请选择公司行业");
            return;
        }
        if (edtCoUserful.getText().toString().equals("")) {
            showToast("请填写公司福利");
            return;
        }
        if (edtCoJianjie.getText().toString().equals("")) {
            showToast("请填写公司简介");
            return;
        }
//        if (edtCoIntro.getText().toString().equals("")) {
//            showToast("请填写公司介绍");
//            return;
//        }

        if (edtContactName.getText().toString().equals("")) {
            showToast("请填写联系人");
            return;
        }
        if (edtContactPhone.getText().toString().equals("")) {
            showToast("请填写联系人电话");
            return;
        }
        if (edtContactEmail.getText().toString().equals("")) {
            showToast("请填写联系人邮箱");
            return;
        }

        if (edtContactAddress.getText().toString().equals("")) {
            showToast("请填写地址");
            return;
        }

        final Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("name", edtName.getText().toString());
        map.put("short_name", edtTypr.getText().toString());
        map.put("ctype", co_property);
        map.put("scale", String.valueOf(co_num));
        map.put("pro_city_dist", citys);
        map.put("trade", hanye);
        if (edtRegistMoney.getText().toString().equals("")){
            map.put("register_capital",0.0);
        }else {
            map.put("register_capital", edtRegistMoney.getText().toString());
        }
        map.put("neturl", edtNetAddress.getText().toString());
        map.put("jobtag", edtCoUserful.getText().toString());
        map.put("jobTagId",youHuo_id);
        map.put("summary", edtCoJianjie.getText().toString());
        map.put("introduction", edtCoJianjie.getText().toString());
        map.put("contact", edtContactName.getText().toString());
        map.put("mobile_phone", edtContactPhone.getText().toString());
        map.put("email", edtContactEmail.getText().toString());
        map.put("address", edtContactAddress.getText().toString());
        map.put("position", String.valueOf(jing) + "," + String.valueOf(wei));
        map.put("logo",photo_url);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ADD_AND_UPDATE_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                MobclickAgent.onEvent(mContext,"addOrUpdateCompany");
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    if (type == null) {
                        finish();
                    } else if (type.equals("2")) {
//                        Intent intent = new Intent(mContext, HrCoAttentionActivity.class);
//                        intent.putExtra("type", "2");
//                        startActivity(intent);
//                        startActivity(new Intent(mContext, LoginActivity.class));
//                        finish();
                        doMYLogin();
                    }
                } else {
                    showToast(entity.getMsg());
                }
            }
        });
    }

    private void initNum() {
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
                                        edtShortNum.setText(date.get(options1));
                                        co_num = comclass.getComclass().get(i).getId();
                                    }
                                }
                            }
                        }).setTitleText("选择企业规模").build();
                        pvCustomTime.setPicker(date);
                        pvCustomTime.show();
                    }
                }
            }
        });
    }

    private void initProperty() {
        Map<String, Object> school_map = new HashMap<>();
        school_map.put("ctype", "company_type");
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
                                        edtAddress.setText(date.get(options1));
                                        co_property = comclass.getComclass().get(i).getId();
                                    }
                                }
                            }
                        }).setTitleText("选择企业性质").build();
                        pvCustomTime.setPicker(date);
                        pvCustomTime.show();
                    }
                }
            }
        });
    }


    private void initHangYe() {

        final ArrayList<String> one_list = new ArrayList<>();
        final ArrayList<ArrayList<String>> two_list = new ArrayList<>();
        final ArrayList<HanyeBean> hang_list_one = new ArrayList<>();
        final ArrayList<ArrayList<HanyeBean>> hang_list_two = new ArrayList<>();

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
                            edtPlayType.setText(tx);
                            hanye = hang_list_one.get(options1).getId() + "," + hang_list_two.get(options1).get(options2).getId();
                        }
                    }).setTitleText("选择行业").build();
                    pvCustomTime.setPicker(one_list, two_list);
                    pvCustomTime.show();
                }
            }
        });
    }


    private double jing, wei;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            AddressJw jw = (AddressJw) data.getSerializableExtra("data");
            jing = jw.getB();
            wei = jw.getA();
            edtContactAddress.setText(data.getStringExtra("title"));
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        // 照片地址
                        String imagePaht = captureManager.getCurrentPhotoPath();
                        updatePhoto(imagePaht);
                    }
                    break;
            }
        }
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                String imagePaht = photos.get(0);
                updatePhoto(imagePaht);
            }
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
            edtCoUserful.setText(string.toString());
        }
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
                        } else if (type.equals("0")) {
                            if (!m.equals("不限")) {
                                citys = options1Items.get(options1).getId() + "," + options2Items.get(options1).get(options2).getId();
                            } else {
                                citys = String.valueOf(options1Items.get(options1).getId());
                            }
                            if (tx != null && !tx.equals("")) {
                                edtCommonAddress.setText(tx + m);
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


    private void takePhoto() {
        RxPermissions permissions = new RxPermissions((HrCoInfoActivity) mContext);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    captureManager = new ImageCaptureManager(mContext);
                    Intent intent = null;
                    try {
                        intent = captureManager.dispatchTakePictureIntent();
                        startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void selectPhoto() {
        RxPermissions permissions = new RxPermissions((HrCoInfoActivity) mContext);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    //选择相册
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(HrCoInfoActivity.this, PhotoPicker.REQUEST_CODE);
                }
            }
        });
    }

    private void updatePhoto(String imagePaht) {
        Glide.with(mContext).load(imagePaht).into(ivName);
        if (imagePaht != null) {
            File file = new File(imagePaht);
            try {
                OkHttpClientManager.postAsyn(UrlUtis.UPLOAD_FILE, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            PhotoBean photoBean = gson.fromJson(gson.toJson(entity.getData()), PhotoBean.class);
                            showToast(photoBean.getInfo());
                            photo_url =  photoBean.getFilepath();

                        }
                    }
                }, file, "file");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
//                    SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.mobile, bean.getUsername());
                    SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.userType,bean.getUserType());
                    ActiivtyStack.getScreenManager().clearAllActivity();
                    mContext.startActivity(new Intent(mContext, CoMainActivity.class));
                }
            }
        });
    }
}
