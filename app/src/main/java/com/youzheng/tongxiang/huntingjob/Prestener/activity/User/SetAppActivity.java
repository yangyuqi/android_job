package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.DownLoadAllData;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.DownLoadData;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FindPwdActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.H5Activity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.ActiivtyStack;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Utils.apkupdate.DownloadManager;
import com.youzheng.tongxiang.huntingjob.UI.dialog.DeleteDialog;
import com.youzheng.tongxiang.huntingjob.UI.dialog.DeleteDialogInterface;
import com.youzheng.tongxiang.huntingjob.UI.dialog.ShareDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2018/2/11.
 */

public class SetAppActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.switvh)
    Switch switvh;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_update_version)
    RelativeLayout rlUpdateVersion;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @BindView(R.id.rl_out_login)
    RelativeLayout rlOutLogin;
    @BindView(R.id.rl_bang_phone)
    RelativeLayout rlBangPhone;
    @BindView(R.id.rl_secret)
    RelativeLayout rlSecret;

    private DownloadManager downloadManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_app_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        textHeadTitle.setText("设置");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        downloadManager = new DownloadManager(mContext);
        tvVersion.setText("(当前版本v" + PublicUtils.getAppVersionName(mContext) + ")");
        if (accessToken.equals("")) {
            rlOutLogin.setVisibility(View.GONE);
        } else {
            rlOutLogin.setVisibility(View.VISIBLE);
        }
        int userType = (int) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.userType,0);

        if (userType==1){
            findViewById(R.id.rl_secret).setVisibility(View.GONE);
            findViewById(R.id.viewwww).setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.rl_change_pwd, R.id.rl_update_version, R.id.rl_share, R.id.rl_about_us, R.id.rl_out_login,R.id.rl_bang_phone,R.id.rl_secret})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_change_pwd:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                Intent intent = new Intent(mContext, FindPwdActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.rl_update_version:
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
                                } else {
                                    showToast("版本一致无需更新");
                                }
                            }
                        }catch (Exception e){}
                    }
                });
                break;
            case R.id.rl_share:
                ShareDialog dialog = new ShareDialog(mContext, "2", 0 ,"专注智能精准化人才职业发展","专注智能精准化人才职业发展");
                dialog.show();
                break;

            case R.id.rl_out_login:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }

                DeleteDialog deleteDialog = new DeleteDialog(mContext, "提示", "是否退出登录", "确定");
                deleteDialog.show();
                deleteDialog.OnDeleteBtn(new DeleteDialogInterface() {
                    @Override
                    public void isDelete(boolean isdelete) {
                        if (!accessToken.equals("")) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("access_token", accessToken);
                            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.LOGIN_OUT, new OkHttpClientManager.StringCallback() {
                                @Override
                                public void onFailure(Request request, IOException e) {

                                }

                                @Override
                                public void onResponse(String response) {
                                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                                        finish();
                                        SharedPreferencesUtils.clear(mContext);
                                        SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.fiet, 1);
                                        startActivity(new Intent(mContext, LoginActivity.class));
                                        ActiivtyStack.getScreenManager().clearAllActivity();
                                    }
                                }
                            });
                        }
                    }
                });
                deleteDialog.show();
                break;
            case R.id.rl_about_us:
                Intent intent1 = new Intent(mContext, H5Activity.class);
                intent1.putExtra("title", "关于我们");
                intent1.putExtra("content", "面视官是专为职场人打造的短视频求职招聘网站，是一个集人才求职、企业招聘、视频招聘、政府动态、职场资讯、人才培训、就业指导等综合服务为一体的人力资源服务平台。面视官以短视频求职招聘为特色，通过视频更直观、真实、生动的呈现方式，帮助求职者快速找到\n" +
                        "心仪的企业工作，帮助企业更快筛选适合岗位的求职者，同时依托平台强大的大数据系统，智能推选，实现人才与岗位的智能匹配，确保专业、精准、高效就业，也让求职和招聘变得更快乐轻松。");
                startActivity(intent1);
                break;

            case R.id.rl_secret :
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, SecretActivity.class));
                break;

            case R.id.rl_bang_phone :
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, BandPhoneActivity.class));
                break;
        }
    }
}
