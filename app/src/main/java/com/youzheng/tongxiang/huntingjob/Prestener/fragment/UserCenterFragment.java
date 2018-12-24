package com.youzheng.tongxiang.huntingjob.Prestener.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


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
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.HR.UI.GoVideoActivity;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.BaseJianli;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.UserInfoSeeBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.BandPhoneActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.CollectActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.DeliverMessageActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.GuideVideoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.HelpAndRebackActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.InviteActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.SecretActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.SetAppActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.SpeakActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.UserCenterActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.WhoSeeMeActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.FileManager;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;
import com.youzheng.tongxiang.huntingjob.UI.dialog.BottomPhotoDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public class UserCenterFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    @BindView(R.id.rl_speak)
    RelativeLayout rlSpeak;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.rl_set)
    RelativeLayout rlSet;
    @BindView(R.id.rl_secret)
    RelativeLayout rlSecret;
    @BindView(R.id.rl_bang_phone)
    RelativeLayout rlBangPhone;
    @BindView(R.id.rl_deliver)
    LinearLayout rlDeliver;
    @BindView(R.id.rl_invite)
    LinearLayout rlInvite;
    @BindView(R.id.rl_help)
    RelativeLayout rlHelp;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_show1)
    LinearLayout llShow1;
    @BindView(R.id.ll_show2)
    LinearLayout llShow2;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.iv_icon)
    CircleImageView ivIcon;
    @BindView(R.id.rl_see)
    RelativeLayout rlSee;
    @BindView(R.id.ll_video)
    LinearLayout llVideo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup conuser_center_layouttainer, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_center_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }



    private void initView() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        MobclickAgent.onResume(mContext);
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onResume(mContext);
    }

    private void initData() {
        if (!accessToken.equals("")) {
            llShow1.setVisibility(View.VISIBLE);
            llShow2.setVisibility(View.GONE);
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", accessToken);
            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.USER_INFO_SEE, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        UserInfoSeeBean userInfoSeeBean = gson.fromJson(gson.toJson(entity.getData()), UserInfoSeeBean.class);
                        if (Integer.parseInt(userInfoSeeBean.getUser().getGender()) == 1) {
                            Glide.with(mContext).load(userInfoSeeBean.getUser().getPhoto()).error(R.mipmap.morentouxiangnansheng).into(ivIcon);
                        } else if (Integer.parseInt(userInfoSeeBean.getUser().getGender()) == 0) {
                            Glide.with(mContext).load(userInfoSeeBean.getUser().getPhoto()).error(R.mipmap.morentouxiangnvsheng).into(ivIcon);
                        }
                        tvName.setText(userInfoSeeBean.getUser().getNickname());
//                        tvPersonal.setText(userInfoSeeBean.getUser().getPersonal());
                    }
                }
            });

            Map<String, Object> map1 = new HashMap<>();
            map1.put("accessToken", accessToken);
            OkHttpClientManager.postAsynJson(gson.toJson(map1), UrlUtis.MY_RESUME, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        final BaseJianli baseJianli = gson.fromJson(gson.toJson(entity.getData()), BaseJianli.class);
                        tvPersonal.setText(baseJianli.getResume().getWork_year()+"年"+" | "+baseJianli.getResume().getEducation()+" | "+baseJianli.getResume().getCitysName());
                    }
                }
            });
        } else {
            llShow1.setVisibility(View.GONE);
            llShow2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_user, R.id.rl_speak, R.id.rl_collect, R.id.rl_user_info, R.id.rl_set, R.id.rl_secret, R.id.rl_bang_phone, R.id.rl_deliver, R.id.rl_invite, R.id.rl_help, R.id.ll_show2, R.id.rl_see,R.id.ll_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_show2:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.rl_speak:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, SpeakActivity.class));
                break;
            case R.id.rl_collect:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, CollectActivity.class));
                break;
            case R.id.rl_user_info:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, UserCenterActivity.class));
                break;
            case R.id.rl_set:
                startActivity(new Intent(mContext, SetAppActivity.class));
                break;
            case R.id.rl_secret:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, SecretActivity.class));
                break;
            case R.id.rl_bang_phone:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, BandPhoneActivity.class));
                break;
            case R.id.rl_deliver:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, DeliverMessageActivity.class));
                break;
            case R.id.rl_invite:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, InviteActivity.class));
                break;
            case R.id.rl_help:
                Intent intent = new Intent(mContext, HelpAndRebackActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.rl_see:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                startActivity(new Intent(mContext, WhoSeeMeActivity.class));
                break;

            case R.id.ll_video :
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
//                Intent intd = new Intent(mContext, GoVideoActivity.class);
//                intd.putExtra("type","0");
//                startActivity(intd);
                Intent intent1 = new Intent(mContext, GuideVideoActivity.class);
                intent1.putExtra("type","0");
                startActivity(intent1);

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}
