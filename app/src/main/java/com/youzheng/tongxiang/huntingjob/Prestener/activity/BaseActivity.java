package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youzheng.tongxiang.huntingjob.Model.IBasePresenter;
import com.youzheng.tongxiang.huntingjob.UI.Utils.ActiivtyStack;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.View.IBaseView;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public  class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {

    protected Context mContext ;
    protected FragmentManager fm;
    protected Gson gson ;
    protected String accessToken ;
    protected int uid ,rid ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        mContext = this ;
        gson = new Gson();
        accessToken = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.access_token,"");
        uid = (int) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.uid,0);
        rid = (int) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.rid,0);
        // 每次加入stack
        ActiivtyStack.getScreenManager().pushActivity(this);
    }


    @Override
    protected void onResume() {
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
    protected void onDestroy() {
        // 退出时弹出stack
        ActiivtyStack.getScreenManager().popActivity(this);
        super.onDestroy();
    }

    @Override
    public Resources getResources() {
        return getBaseContext().getResources();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
