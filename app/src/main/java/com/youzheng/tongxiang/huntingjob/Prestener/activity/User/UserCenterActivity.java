package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.PhotoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.SeeUserBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;
import com.youzheng.tongxiang.huntingjob.UI.dialog.BottomPhotoDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2018/2/11.
 */

public class UserCenterActivity extends BaseActivity {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.rl_change_photo)
    RelativeLayout rlChangePhoto;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    ImageCaptureManager captureManager;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.edt_person)
    EditText edtPerson;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.rl_select_sex)
    RelativeLayout rlSelectSex;

    private int gender;
    private String photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        ButterKnife.bind(this);

        initView();

        initDate();
    }

    private void initDate() {
        if (!accessToken.equals("")) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", accessToken);
            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SEE_USER_INFO, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        SeeUserBean userBean = gson.fromJson(gson.toJson(entity.getData()), SeeUserBean.class);
                        if (userBean.getGender() == 1) {
                            Glide.with(mContext).load(userBean.getPhoto()).error(R.mipmap.morentouxiangnansheng).into(civ);
                            tvSex.setText("男");
                            gender = 1;
                        } else if (userBean.getGender() == 0) {
                            Glide.with(mContext).load(userBean.getPhoto()).error(R.mipmap.morentouxiangnvsheng).into(civ);
                            tvSex.setText("女");
                            gender = 0;
                        }
                        if (userBean.getNickname() == null) {
                            tvName.setHint("暂无昵称");
                        } else {
                            tvName.setText(userBean.getNickname());
                        }
                        tvPhone.setText("" + userBean.getUsername());
                        edtPerson.setText("" + userBean.getPersonal());
                        photo = userBean.getPhoto();
                    }
                }
            });
        }
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadTitle.setText("个人信息");
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("编辑");
        rlChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textHeadNext.getText().toString().equals("编辑")) {
                    btnLogin.setVisibility(View.VISIBLE);
                    textHeadNext.setText("完成");
                } else {
                    textHeadNext.setText("编辑");
                    btnLogin.setVisibility(View.GONE);
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", accessToken);
                map.put("gender", gender);
                map.put("photo", photo);
                map.put("personal", edtPerson.getText().toString());
                map.put("nickname", tvName.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_USER_INFO, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            initDate();
                            textHeadNext.setText("编辑");
                            btnLogin.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        rlSelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textHeadNext.getText().toString().equals("完成")) {
                    final List<String> m = new ArrayList<>();
                    m.add("男");
                    m.add("女");
                    OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            if (m.get(options1).equals("男")) {
                                gender = 1;
                                tvSex.setText("男");
                            } else {
                                gender = 0;
                                tvSex.setText("女");
                            }
                        }
                    }).setTitleText("职位性别").build();
                    pvCustomTime.setPicker(m);
                    pvCustomTime.show();
                }
            }
        });
    }

    private void takePhoto() {
        RxPermissions permissions = new RxPermissions((UserCenterActivity) mContext);
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
        RxPermissions permissions = new RxPermissions((UserCenterActivity) mContext);
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
                            .start(UserCenterActivity.this, PhotoPicker.REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    }

    private void updatePhoto(String imagePaht) {
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
                            Map<String, Object> h_map = new HashMap<>();
                            h_map.put("accessToken", accessToken);
                            h_map.put("photo", photoBean.getFilepath());
                            OkHttpClientManager.postAsynJson(gson.toJson(h_map), UrlUtis.EDIT_PERSIOM_INFO, new OkHttpClientManager.StringCallback() {
                                @Override
                                public void onFailure(Request request, IOException e) {

                                }

                                @Override
                                public void onResponse(String response) {
                                    BaseEntity entity1 = gson.fromJson(response, BaseEntity.class);
                                    if (entity1.getCode().equals(PublicUtils.SUCCESS)) {
                                        initDate();
                                    }
                                }
                            });
                        }
                    }
                }, file, "file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
