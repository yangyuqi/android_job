package com.youzheng.tongxiang.huntingjob.UI.Utils.apkupdate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;


import com.youzheng.tongxiang.huntingjob.R;

import java.util.List;

/**
 * Created by wqd on 2017/4/11 0011.
 */
public class DownloadManager {

    private Context mContext;
    public DownloadManager(Context context){
        mContext = context;
    }
    public void download(String url) {
        if (!canDownloadState()) {
            Toast.makeText(mContext, "下载服务不可用,请您启用", Toast.LENGTH_SHORT).show();
            showDownloadSetting();
            return;
        }
        ApkUpdateUtils.download(mContext, url, mContext.getResources().getString(R.string.app_name));
    }

    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(intent)) {
            mContext.startActivity(intent);
        }
    }

    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = mContext.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    private boolean canDownloadState() {
        try {
            int state = mContext.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getVersionName()
    {
        try{
            // 获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(),0);
            String version = packInfo.versionName;
            return version;
        }catch (Exception e){
            return "";
        }
    }
}
