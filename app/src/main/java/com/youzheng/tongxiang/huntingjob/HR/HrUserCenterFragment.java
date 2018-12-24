package com.youzheng.tongxiang.huntingjob.HR;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.HR.UI.CoReviceActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.GoVideoActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrCoAttentionActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrCoInfoActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrInterViewInviteActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrScanActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrWhoSeeMeActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.NewHrCollectActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.IntroduceCoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.BandPhoneActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.GuideVideoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.HelpAndRebackActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.User.SetAppActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/5/10.
 */

public class HrUserCenterFragment extends BaseFragment {

    @BindView(R.id.iv_icon)
    CircleImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.rl_deliver)
    RelativeLayout rlDeliver;
    @BindView(R.id.rl_invite)
    RelativeLayout rlInvite;
    @BindView(R.id.rl_bang_phone)
    RelativeLayout rlBangPhone;
    @BindView(R.id.rl_secret)
    RelativeLayout rlSecret;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;
    @BindView(R.id.rl_see)
    RelativeLayout rlSee;
    @BindView(R.id.rl_help)
    RelativeLayout rlHelp;
    @BindView(R.id.rl_set)
    RelativeLayout rlSet;
    Unbinder unbinder;
    @BindView(R.id.ll_reciver)
    LinearLayout llReciver;
    @BindView(R.id.ll_co_home)
    LinearLayout llCoHome;
    @BindView(R.id.ll_make_video)
    LinearLayout llMakeVideo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hr_user_center_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    CoBean coBean;

    private void initData() {
        final Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SEE_CO_INFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    coBean = gson.fromJson(gson.toJson(entity.getData()), CoBean.class);
                    tvName.setText(coBean.getCompany().getName());
                    tvPersonal.setText(coBean.getCompany().getCtypeName()+" | "+coBean.getCompany().getScaleName()+" | "+coBean.getCompany().getTradeName());
                    Glide.with(mContext).load(coBean.getCompany().getPhoto()).error(R.mipmap.gggggroup).into(ivIcon);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_user_info, R.id.rl_deliver, R.id.rl_invite, R.id.rl_collect, R.id.rl_secret, R.id.rl_see, R.id.rl_help, R.id.rl_set, R.id.rl_bang_phone, R.id.ll_reciver, R.id.ll_co_home,R.id.ll_make_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_user_info:
                startActivity(new Intent(mContext, HrCoInfoActivity.class));
                break;
            case R.id.rl_deliver:
                startActivity(new Intent(mContext, HrCoAttentionActivity.class));
                break;
            case R.id.rl_invite:
                startActivity(new Intent(mContext, HrInterViewInviteActivity.class));
                break;
            case R.id.rl_collect:
//                startActivity(new Intent(mContext, HrCollectActivity.class));
                startActivity(new Intent(mContext, NewHrCollectActivity.class));
                break;
            case R.id.rl_secret:
                startActivity(new Intent(mContext, HrWhoSeeMeActivity.class));
                break;
            case R.id.rl_see:
                startActivity(new Intent(mContext, HrScanActivity.class));
                break;
            case R.id.rl_help:
                Intent intent = new Intent(mContext, HelpAndRebackActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.rl_set:
                startActivity(new Intent(mContext, SetAppActivity.class));
                break;
            case R.id.rl_bang_phone:
                startActivity(new Intent(mContext, BandPhoneActivity.class));
                break;
            case R.id.ll_reciver:
                startActivity(new Intent(mContext, CoReviceActivity.class));
                break;

            case R.id.ll_co_home:
                if (coBean != null) {
                    Intent co_in = new Intent(mContext, IntroduceCoActivity.class);
                    co_in.putExtra("from", "co");
                    co_in.putExtra("id", coBean.getCompany().getId());
                    startActivity(co_in);
                }
                break;

            case R.id.ll_make_video:
//                Intent intd = new Intent(mContext, GoVideoActivity.class);
//                intd.putExtra("type","1");
//                startActivity(intd);
                Intent intd = new Intent(mContext, GuideVideoActivity.class);
                intd.putExtra("type", "1");
                startActivity(intd);
                break;
        }
    }
}
