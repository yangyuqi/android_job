package com.youzheng.tongxiang.huntingjob.UI.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.WXShare;
import com.youzheng.tongxiang.huntingjob.WXEntryActivity;

/**
 * Created by qiuweiyu on 2018/5/9.
 */

public class ShareDialog extends Dialog {

    private Context context ;
    private String title ,des;
    private String type ;
    private int id ;
    private TextView wx ,wxFriend ,wxWeibo ;
     String content = null ;
    private IWXAPI api;
    public ShareDialog(@NonNull Context context ,String type ,int id ,String title ,String desc ) {
        super(context, R.style.BottomDialog);
        this.context = context;
        this.type = type ;
        this.id = id ;
        this.title = title;
        this.des = desc ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(context, "wx088dbd12cd54a6eb", false);
        init();
    }

    private void initEvent(View view) {
        wx = view.findViewById(R.id.tv_wx);
        wxFriend = view.findViewById(R.id.tv_wx_friend);
        wxWeibo = view.findViewById(R.id.tv_weibo);
        if (type.equals("1")){
            content = "http://wx.upinhr.cn/findJob/jobDetail?jobId="+id;
        }else if (type.equals("2")){
            content = "http://wx.upinhr.cn/";
        }
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                WXTextObject textObj = new WXTextObject();
//                textObj.text = content;
//                WXMediaMessage msg = new WXMediaMessage();
//                msg.mediaObject = textObj;
//                msg.description = content;
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("text");
//                req.message = msg;
//                req.scene = SendMessageToWX.Req.WXSceneSession;
//                api.sendReq(req);
                WXShare wxShare = new WXShare(context);
                wxShare.share(content,des,title,"1");
                dismiss();
            }
        });

        wxFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WXShare wxShare = new WXShare(context);
                wxShare.share(content,des,title,"2");
                dismiss();
            }
        });

        wxWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"请安装最新微博客户端",Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.view_).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.share_dialog_layout, null);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        params.width = d.widthPixels;
        params.height = d.heightPixels;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(false);
        initEvent(view);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
