package com.youzheng.tongxiang.huntingjob.Prestener.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youzheng.tongxiang.huntingjob.Model.IBasePresenter;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.View.IBaseView;

import javax.inject.Inject;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {

    protected Context mContext;
    protected Gson gson ;

    protected T mPresenter ;

    protected String accessToken ;
    protected int rid ;
    protected int uid ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        gson = new Gson();
        accessToken = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.access_token,"");
        rid = (int) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.rid, 0);
        uid = (int) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.uid,0);
    }

    @Override
    public void onResume() {
        super.onResume();
        accessToken = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.access_token,"");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetError() {

    }

    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void doLogin(){
        startActivity(new Intent(mContext, LoginActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
