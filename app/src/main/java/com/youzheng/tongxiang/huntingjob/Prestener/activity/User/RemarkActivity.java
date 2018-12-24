package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.BinaryUtil;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.VideoCommentBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.MediaUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.dialog.BottomPhotoDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

public class RemarkActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.ll_add_video)
    LinearLayout llAddVideo;
    @BindView(R.id.iv_image)
    ImageView ivImage;

    private String type, path, video_image, comType;
    ImageCaptureManager captureManager;
    float  heigh ,width ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remark_activity_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        path = getIntent().getStringExtra("path");
        comType = getIntent().getStringExtra("comType");
        btnBack.setVisibility(View.VISIBLE);
        textHeadTitle.setText("填写视频信息");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("确定");
        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvPhone.getText().toString().equals("")) {
                    showToast("请填写备注信息");
                    return;
                }

//                if (video_image == null) {
//                    showToast("请选择视频封面");
//                    return;
//                }

                if (!tvPhone.getText().toString().equals("")) {
                    initUpLoad();
                } else {
                    showToast("请填写想说的话");
                }
            }
        });

        llAddVideo.setOnClickListener(new View.OnClickListener() {
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

        MediaUtils.getImageForVideo(path, new MediaUtils.OnLoadVideoImageListener() {
            @Override
            public void onLoadImage(File file) {
                updatePhoto(file.getPath());
            }
        });
    }

    private void initUpLoad() {
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(false);
        dialog.setMessage("请等待");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        //推荐使用OSSAuthCredentialsProvider。token过期可以及时更新
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIR2B4nJ3aX25H", "JFCuuhKHTDq43vupOEml6sYf62YPXQ");
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        String objectKey = UUID.randomUUID().toString() + ".mp4";
        PutObjectRequest put = new PutObjectRequest("upinhr-channel", objectKey, path);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        try {
            // 设置Md5以便校验
            metadata.setContentMD5(BinaryUtil.calculateBase64Md5(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        put.setMetadata(metadata);

        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.e("http", gson.toJson(request));
                try {
                    JSONObject object = new JSONObject(new Gson().toJson(request));
                    String objectKey = object.getString("objectKey");
                    if (type != null) {
                        if (!type.equals("2")) {
                            Map<String, Object> map = new HashMap<>();

                            map.put("width", width);
                            map.put("height", heigh);
                            map.put("type", type);
                            map.put("accessToken", accessToken);
                            map.put("url", objectKey);
                            map.put("comment", tvPhone.getText().toString());
                            map.put("indeximg", video_image);
                            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPLOAD_VIDEO, new OkHttpClientManager.StringCallback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void onResponse(String response) {
                                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                                        dialog.dismiss();
                                        finish();
                                        EventBus.getDefault().post("finish");
                                    }
                                }
                            });
                        } else {

                            if (comType.equals("comTypeVideo")) {
                                EventBus.getDefault().post(new VideoCommentBean(tvPhone.getText().toString(), objectKey, video_image, width, heigh));
                                dialog.dismiss();
                                EventBus.getDefault().post("finish");
                                finish();
                            } else {
                                EventBus.getDefault().post(new VideoCommentBean(tvPhone.getText().toString(), objectKey, video_image,width,heigh));
                                dialog.dismiss();
                                EventBus.getDefault().post("finish");
                                finish();
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    dialog.dismiss();
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    dialog.dismiss();
                    finish();
                }
            }
        });
    }


    private void takePhoto() {
        RxPermissions permissions = new RxPermissions((RemarkActivity) mContext);
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
        RxPermissions permissions = new RxPermissions((RemarkActivity) mContext);
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
                            .start(RemarkActivity.this, PhotoPicker.REQUEST_CODE);
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
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imagePaht, options);

         heigh = options.outHeight;
         width = options.outWidth;

        Glide.with(mContext).load(imagePaht).error(R.mipmap.gggggroup).into(ivImage);
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(false);
        dialog.setMessage("请等待");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        //推荐使用OSSAuthCredentialsProvider。token过期可以及时更新
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIR2B4nJ3aX25H", "JFCuuhKHTDq43vupOEml6sYf62YPXQ");
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        String objectKey = UUID.randomUUID().toString() + ".png";
        PutObjectRequest put = new PutObjectRequest("upinhr-channel", objectKey, imagePaht);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        try {
            // 设置Md5以便校验
            metadata.setContentMD5(BinaryUtil.calculateBase64Md5(imagePaht));
        } catch (IOException e) {
            e.printStackTrace();
        }
        put.setMetadata(metadata);

        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });


        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.e("http", gson.toJson(request));
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(new Gson().toJson(request));
                    video_image = object.getString("objectKey");
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    Log.e("http", "oooooooooo");
                    dialog.dismiss();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("http", "uuuuuuuuuuu");
                    dialog.dismiss();
                }
            }
        });
    }
}
