package com.youzheng.tongxiang.huntingjob.Model.Event;

import android.os.Parcel;
import android.os.Parcelable;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.youzheng.tongxiang.huntingjob.UI.Utils.WXShare;

/**
 * Created by qiuweiyu on 2018/6/5.
 */

public  class  Response extends BaseResp implements Parcelable {
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
