package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.recorder.AliyunRecorderCreator;
import com.aliyun.recorder.supply.AliyunIRecorder;
import com.aliyun.svideo.sdk.external.struct.common.CropKey;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.RemarkActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.Video.AliyunVideoRecorder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class GoVideoActivity extends BaseActivity {

    @BindView(R.id.tv_pick_phone)
    TextView tvPickPhone;
    @BindView(R.id.tv_pick_zone)
    TextView tvPickZone;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    private String mtype ;//0简历 1公司 2职位
    private String accessToken ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_popupwindow);
        ButterKnife.bind(this);

        mtype = getIntent().getStringExtra("type");

        tvPickPhone.setText("视频拍摄");
        tvPickZone.setText("从相册选取");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvPickPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxPermissions permissions = new RxPermissions(GoVideoActivity.this);
                permissions.request(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            PublicUtils.startRecordActivity(GoVideoActivity.this,mtype);
                        }
                    }
                });
            }
        });

        tvPickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxPermissions permissions = new RxPermissions(GoVideoActivity.this);
                permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            PublicUtils.startCropActivity(GoVideoActivity.this,mtype);
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==  1) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                int type = data.getIntExtra(AliyunVideoRecorder.RESULT_TYPE, 0);
                if (type == AliyunVideoRecorder.RESULT_TYPE_CROP) {
                    String path = data.getStringExtra(CropKey.RESULT_KEY_CROP_PATH);
                    int com ;
                    if (mtype.equals("0")){
                        com=90*1000;
                    }else {
                        com=90*1000;
                    }
                    if (data.getLongExtra(CropKey.RESULT_KEY_DURATION,0)<com&&data.getLongExtra(CropKey.RESULT_KEY_DURATION,0)>5*1000){

                        Intent intent = new Intent(mContext, RemarkActivity.class);
                        intent.putExtra("type",mtype);
                        intent.putExtra("path",path);
                        intent.putExtra("comType","comTypeRecode");
                        startActivity(intent);
                        finish();
                    }else {
                        if (mtype.equals("0")) {
                            showToast("视频录制时间为5到90秒");
                        }else {
                            showToast("视频录制时间为5到90秒");
                        }
                    }

                } else if (type == AliyunVideoRecorder.RESULT_TYPE_RECORD) {
                    Intent intent = new Intent(mContext, RemarkActivity.class);
                    intent.putExtra("type",mtype);
                    intent.putExtra("path",data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH));
                    intent.putExtra("comType","comTypeVideo");
                    startActivity(intent);
                    finish();

                }
            } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(mContext, "用户取消录制", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
