package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.CoMainActivity;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.CoAttentionBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HangYeList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.HanyeBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.PhotoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.UserBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
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

public class HrCoAttentionActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_typr)
    TextView edtTypr;
    @BindView(R.id.edt_address)
    TextView edtAddress;
    @BindView(R.id.iv_change_icon)
    ImageView ivChangeIcon;
    @BindView(R.id.rl_edit)
    RelativeLayout rlEdit;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.edt_co_boss)
    EditText edtCoBoss;
    @BindView(R.id.edt_co_boss_num)
    EditText edtCoBossNum;

    private OptionsPickerView pvCustomTime;
    private int co_property,hanye ;
    private String photo_url,state_now="";
    ImageCaptureManager captureManager;

    private String type ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_co_attention_layout);
        ButterKnife.bind(this);
        initView();
        type = getIntent().getStringExtra("type");
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("hr_co_attention_layout");
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("hr_co_attention_layout");
        MobclickAgent.onPause(mContext);
    }

    private void initData() {
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
                    edtName.setText( attentionBean.getName());
                    edtTypr.setText(attentionBean.getCtypeName());
                    edtAddress.setText( attentionBean.getMainTrade());
                    edtCoBoss.setText(attentionBean.getCorporation());
                    if (String.valueOf(attentionBean.getCorporationId()).equals("0")){
                        edtCoBossNum.setText("");
                    }else {
                        edtCoBossNum.setText(""+attentionBean.getCorporationId());
                    }
                    Glide.with(mContext).load(attentionBean.getLicense_photo()).placeholder(R.mipmap.zhaopian21).into(ivChangeIcon);
                    photo_url=attentionBean.getLicense_photo();
                    co_property=attentionBean.getCtype();
                    hanye = attentionBean.getMainTradeId();
                    state_now=attentionBean.getState();

                    if (state_now.equals("1")){
                        tvAttention.setText("认证成功");
                        tvAttention.setBackgroundResource(R.color.text_gray);
                        tvAttention.setEnabled(false);
                    }else if (state_now.equals("2")){
                        tvAttention.setText("认证中");
                    }else if (state_now.equals("3")){
                        tvAttention.setText("认证失败,请重新认证");
                    }
                }
            }
        });
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadTitle.setText("企业认证");
    }

    @OnClick({R.id.tv_attention,R.id.edt_typr,R.id.edt_address,R.id.rl_edit})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_attention:
                if (!state_now.equals("1")&&!state_now.equals("2")) {
                    doUpdate();
                }
                break;
            case R.id.edt_typr:
                initProperty();
                break;
            case R.id.edt_address:
                initHangYe();
                break;
            case R.id.rl_edit:
                selectPhotoCo();
                break;
        }
    }

    private void selectPhotoCo() {
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
    }

    private void doUpdate() {

        if (edtName.getText().toString().equals("")) {
            showToast("请填写企业名称");
            return;
        }
        if (edtTypr.getText().toString().equals("")||co_property==0) {
            showToast("请选择企业性质");
            return;
        }

        if (edtAddress.getText().toString().equals("")) {
            showToast("请选择公司主行业");
            return;
        }

        if (edtCoBoss.getText().toString().equals("")) {
            showToast("请填写企业法人");
            return;
        }

        if (edtCoBossNum.getText().toString().equals("")) {
            showToast("请填写身份证号");
            return;
        }
        if (photo_url==null) {
            showToast("请上传营业执照");
            return;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("license_photo",photo_url);
        map.put("corporation",edtCoBoss.getText().toString());
        map.put("corporationId",edtCoBossNum.getText().toString());
        map.put("ctype",co_property);
        Log.e("ssssssssssmm",""+co_property);
        map.put("name",edtName.getText().toString());
        map.put("mianTrade",hanye);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_CO_ATTENTION, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                    BaseEntity entity = gson.fromJson(response,BaseEntity.class);

                    if (entity.getCode().equals(PublicUtils.SUCCESS)){
                        MobclickAgent.onEvent(mContext,"updateAuthentication");
                        if (type==null) {
                            finish();
                        }else if (type.equals("2")){
                            doMYLogin();
                        }
                    }else {
                        showToast(entity.getMsg());
                    }
            }
        });
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
                    ActiivtyStack.getScreenManager().clearAllActivity();
                    mContext.startActivity(new Intent(mContext, CoMainActivity.class));
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
                                        edtTypr.setText(date.get(options1));
                                        co_property = comclass.getComclass().get(i).getId();
                                        Log.e("ssssss",""+co_property);
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
                            String tx = hang_list_two.get(options1).get(options2).getTradeName();
                            edtAddress.setText(tx);
                            hanye = hang_list_two.get(options1).get(options2).getId();
                        }
                    }).setTitleText("选择行业").build();
                    pvCustomTime.setPicker(one_list,two_list);
                    pvCustomTime.show();
                }
            }
        });
    }

    private void takePhoto() {
        RxPermissions permissions = new RxPermissions(HrCoAttentionActivity.this);
        permissions.request(Manifest.permission.CAMERA,Manifest.permission.VIBRATE,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean){
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
        RxPermissions permissions = new RxPermissions(HrCoAttentionActivity.this);
        permissions.request(Manifest.permission.CAMERA,Manifest.permission.VIBRATE,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean){
                    //选择相册
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(HrCoAttentionActivity.this ,PhotoPicker.REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                // 拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if(captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        // 照片地址
                        String imagePaht = captureManager.getCurrentPhotoPath();
                        updatePhoto(imagePaht);
                    }
                    break;
            }
        }
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE){
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                String imagePaht = photos.get(0) ;
                updatePhoto(imagePaht);
            }
        }
    }

    private void updatePhoto(final String imagePaht) {
        if (imagePaht!=null) {
            File file = new File(imagePaht);
            try {
                final ProgressDialog dialog2 = ProgressDialog.show(mContext, "提示", "正在上传....");
                dialog2.show();
                OkHttpClientManager.postAsyn(UrlUtis.UPLOAD_FILE, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        dialog2.dismiss();
                    }

                    @Override
                    public void onResponse(String response) {
                        Glide.with(mContext).load(imagePaht).into(ivChangeIcon);
                        dialog2.dismiss();
                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                            PhotoBean photoBean = gson.fromJson(gson.toJson(entity.getData()),PhotoBean.class);
                            showToast(photoBean.getInfo());
                            photo_url=photoBean.getFilepath();
//                            initData();
                        }
                    }
                },file,"file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
