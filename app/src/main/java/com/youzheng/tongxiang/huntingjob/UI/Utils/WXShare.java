package com.youzheng.tongxiang.huntingjob.UI.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.youzheng.tongxiang.huntingjob.R;

/**
 * Created by qiuweiyu on 2018/5/28.
 */

public class WXShare {
    public static final String APP_ID = "wx2322170b4b3e9077";
    public static final String ACTION_SHARE_RESPONSE = "action_wx_share_response";
    public static final String EXTRA_RESULT = "result";
    private  Context context;
    private  IWXAPI api;
    private OnResponseListener listener;
    private ResponseReceiver receiver;


    public WXShare(Context context) {
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        this.context = context;
    }

    public WXShare register() {
        api.registerApp(APP_ID);
        receiver = new ResponseReceiver();
        IntentFilter filter = new IntentFilter(ACTION_SHARE_RESPONSE);
        context.registerReceiver(receiver, filter);
        return this;
    }

    public void unregister() {
        try {
            api.unregisterApp();
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WXShare share(String url , String descroption ,String title ,String type) {
//        WXTextObject textObj = new WXTextObject();
//        textObj.text = text;
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObj;
//        msg.description = text;

        WXWebpageObject webpageObject =new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage wxMediaMessage =new WXMediaMessage(webpageObject);
        wxMediaMessage.description = descroption ;
        wxMediaMessage.title = title;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon);
        wxMediaMessage.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = wxMediaMessage;
        if (type.equals("1")) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }else if (type.equals("2")){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        boolean result = api.sendReq(req);
        Log.e("http","text shared: " + result);
        return this;
    }

    public IWXAPI getApi() {
        return api;
    }
    public void setListener(OnResponseListener listener) {
        this.listener = listener;
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    private class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Response response = intent.getParcelableExtra(EXTRA_RESULT);
            Log.e("http ","type:" + response.getType());
            Log.e("http","errCode: " + response.errCode);
            String result;
            if (listener != null) {
                if (response.errCode == BaseResp.ErrCode.ERR_OK) {
                    listener.onSuccess();
                }else if (response.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                    listener.onCancel();
                }else {
                    switch (response.errCode) {
                        case BaseResp.ErrCode.ERR_AUTH_DENIED:
                            result = "发送被拒绝";
                            break;
                        case BaseResp.ErrCode.ERR_UNSUPPORT:
                            result = "不支持错误";
                            break;
                        default:
                            result = "发送返回";
                            break;
                    }
                    listener.onFail(result);
                }
            }
        }
    }

    public static class Response extends BaseResp implements Parcelable {
        public int errCode;
        public String errStr;
        public String transaction;
        public String openId;
        private int type;
        private boolean checkResult;
        public Response(BaseResp baseResp) {
            errCode = baseResp.errCode;
            errStr = baseResp.errStr;
            transaction = baseResp.transaction;
            openId = baseResp.openId;
            type = baseResp.getType();
            checkResult = baseResp.checkArgs();
        }
        @Override
        public int getType() {
            return type;
        }
        @Override
        public boolean checkArgs() {
            return checkResult;
        }
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.errCode);
            dest.writeString(this.errStr);
            dest.writeString(this.transaction);
            dest.writeString(this.openId);
            dest.writeInt(this.type);
            dest.writeByte(this.checkResult ? (byte) 1 : (byte) 0);
        }

        protected Response(Parcel in) {
            this.errCode = in.readInt();
            this.errStr = in.readString();
            this.transaction = in.readString();
            this.openId = in.readString();
            this.type = in.readInt();
            this.checkResult = in.readByte() != 0;
        }
        public static final Creator<Response> CREATOR = new Creator<Response>() {
            @Override
            public Response createFromParcel(Parcel source) {
                return new Response(source);
            }
            @Override
            public Response[] newArray(int size) {
                return new Response[size];
            }
        };
    }
    public interface OnResponseListener{
        void onSuccess();

        void onCancel();

        void onFail(String message);
    }

    public static class AppRegister extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
            api.registerApp(WXShare.APP_ID);
        }
    }
}
