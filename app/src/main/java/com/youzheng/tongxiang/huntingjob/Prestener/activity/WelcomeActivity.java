package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.CoMainActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrCoInfoActivity;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.DownLoadAllData;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.DownLoadData;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.UserBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Utils.apkupdate.DownloadManager;
import com.youzheng.tongxiang.huntingjob.UI.dialog.DeleteDialog;
import com.youzheng.tongxiang.huntingjob.UI.dialog.DeleteDialogInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by qiuweiyu on 2018/2/22.
 */

public class WelcomeActivity extends BaseActivity {

    private DownloadManager downloadManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        downloadManager = new DownloadManager(mContext);

        Map<String, Object> map = new HashMap<>();
        map.put("version", PublicUtils.getAppVersionName(mContext));
        map.put("type", "1");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_VERSION, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    DownLoadAllData entity = gson.fromJson(response, DownLoadAllData.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        final DownLoadData downLoadData = entity.getData();
                        String versionCode = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.versionCode,"");
                        if (!versionCode.equals(downLoadData.getDate().getVersion())) {
                            if (!downLoadData.getDate().getVersion().equals(PublicUtils.getAppVersionName(mContext))) {
                                DeleteDialog deleteDialog = new DeleteDialog(mContext, "版本更新", "当前版本" + PublicUtils.getAppVersionName(mContext) + "线上版本" + downLoadData.getDate().getVersion() + ",是否需要更新?", "更新");
                                deleteDialog.OnDeleteBtn(new DeleteDialogInterface() {
                                    @Override
                                    public void isDelete(boolean isdelete) {
                                        if (isdelete) {
                                            downloadManager.download(downLoadData.getDate().getDownloadUrl());
                                        }
                                    }
                                });
                                deleteDialog.show();
                                deleteDialog.getTv_no().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.versionCode, downLoadData.getDate().getVersion());
                                        doPost();
                                    }
                                });
                            }
                        }else {
                            doPost();
                        }

                    }else {
                        doPost();
                    }
                }catch (Exception e){
                    doPost();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void doPost(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int first = (int) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.fiet,0);
                if (first==0){
                    startActivity(new Intent(mContext,AdActivity.class));
                    finish();
                }else {
                    if (accessToken.equals("")) {
                        startActivity(new Intent(mContext,MainActivity.class));//游客模式
                        finish();
                    }else {
                        Map<String,Object> map = new HashMap<>();
                        map.put("access_token",accessToken);
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.TOKEN_LOGIN, new OkHttpClientManager.StringCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                try {
                                    BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                    if (entity.getCode()==null){
                                        startActivity(new Intent(mContext,LoginActivity.class));
                                        finish();
                                    }else if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                        UserBean bean = gson.fromJson(gson.toJson(entity.getData()),UserBean.class);
                                        if (bean.getUserType()==0) {
                                            if (bean.isBooleanBaseInfo()) {
                                                startActivity(new Intent(mContext, MainActivity.class));
                                            }else {
                                                showToast("您的信息不完整，请去完整");
                                                startActivity(new Intent(mContext,FillInfoActivity.class));
                                            }
                                        }else {
                                            if (bean.isBooleanBaseInfo()){
                                                startActivity(new Intent(mContext, CoMainActivity.class));
                                            }else {
                                                showToast("您的信息不完整，请去完整");
                                                Intent intent = new Intent(mContext,HrCoInfoActivity.class);
                                                intent.putExtra("type","2");
                                                startActivity(intent);
                                            }
                                        }
                                        finish();
                                    }else if (!entity.getCode().equals(PublicUtils.SUCCESS)){
                                        startActivity(new Intent(mContext,LoginActivity.class));
                                        finish();
                                    }
                                }catch (Exception e){
                                    startActivity(new Intent(mContext,LoginActivity.class));
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        },1500);
    }
}
