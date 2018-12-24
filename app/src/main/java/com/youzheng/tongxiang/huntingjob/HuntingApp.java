package com.youzheng.tongxiang.huntingjob;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;


import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.commonsdk.UMConfigure;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public class HuntingApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化sdk
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        System.loadLibrary("fdk-aac");
        System.loadLibrary("live-openh264");
        System.loadLibrary("svideo_alivcffmpeg");
        System.loadLibrary("QuCore");
        QupaiHttpFinal.getInstance().initOkHttpFinal();
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);

        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5c05dd72f1f556957e000d4b");
        UMConfigure.setLogEnabled(true);
    }
}
