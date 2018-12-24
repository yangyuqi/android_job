package com.youzheng.tongxiang.huntingjob.Prestener.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrCoInfoActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrJianliDetailsActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.TestActivity;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.ComJpushData;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.IntroduceCoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.UI.Utils.ActiivtyStack;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by qiuweiyu on 2018/2/22.
 */

public class MyJushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle,context));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            Gson gson = new Gson();
            try {
                JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Object object = jsonObject.get("data");
                String cim = gson.toJson(object).replaceAll("\\\\","");
                ComJpushData comJpushDataBean = gson.fromJson(cim.substring(1,cim.length()-1),ComJpushData.class);
                if (comJpushDataBean.getType().equals("seejob")||comJpushDataBean.getType().equals("deliveryresume")) {
                    Log.e("sssss","----"+comJpushDataBean.getData().getRid()+"---"+gson.toJson(comJpushDataBean.getData()));
                    Intent i = new Intent(context, HrJianliDetailsActivity.class);
                    i.putExtra("jid", Integer.parseInt(comJpushDataBean.getData().getRid()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }else if (comJpushDataBean.getType().equals("seeresume")){
                    Intent i = new Intent(context, IntroduceCoActivity.class);
                    i.putExtra("id", Integer.parseInt(comJpushDataBean.getData().getCid()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }else if (comJpushDataBean.getType().equals("deliveryjob")){
                    Intent i = new Intent(context, TestActivity.class);
                    i.putExtra("id", Integer.parseInt(comJpushDataBean.getData().getJid()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            Gson gson = new Gson();
//            String type = bundle.getString(JPushInterface.EXTRA_EXTRA).replaceAll("\\\\","");
//
//            try {
//                JSONObject jsonObject = new JSONObject(type).getJSONObject("data");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            ComJpushData jpushData = gson.fromJson(type,ComJpushData.class);
//            if (jpushData.getData().getType().equals("seejob")){
//              Intent i = new Intent(context, HrJianliDetailsActivity.class);
//              i.putExtra("jid",jpushData.getData().getRid());
//          //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                context.startActivity(i);
//            }
//
//          //打开自定义的Activity
//          Intent i = new Intent(context, TestActivity.class);
//          i.putExtras(bundle);
//          //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//          context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(final Bundle bundle ,final Context context) {
        try {
            String type = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (type.indexOf("login") > 0){
                SharedPreferencesUtils.clear(context);
                SharedPreferencesUtils.setParam(context, SharedPreferencesUtils.fiet, 1);
                ActiivtyStack.getScreenManager().clearAllActivity();
                Intent i = new Intent(context, LoginActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        }catch (Exception e){

        }
        return bundle.getString(JPushInterface.EXTRA_EXTRA);

    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {

    }
}
