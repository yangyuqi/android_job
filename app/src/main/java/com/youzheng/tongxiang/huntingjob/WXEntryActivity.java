package com.youzheng.tongxiang.huntingjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.youzheng.tongxiang.huntingjob.Model.Event.Response;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.UI.Utils.WXShare;

/**
 * Created by qiuweiyu on 2018/5/28.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    WXShare share ;

    private String type,content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        content = getIntent().getStringExtra("content");
//        share = new WXShare(this);
        api = WXAPIFactory.createWXAPI(this, "wx2322170b4b3e9077", false);
        api.registerApp("wx2322170b4b3e9077");
        try {
            if (!api.handleIntent(getIntent(), this)) {
                finish();
            }} catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        share.register();
    }

    @Override
    protected void onDestroy() {
        share.unregister();
        super.onDestroy();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Toast.makeText(this, "baseresp.getType = " + baseResp.getType(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(WXShare.ACTION_SHARE_RESPONSE);
//        intent.putExtra(WXShare.EXTRA_RESULT, new Response(baseResp));
//        sendBroadcast(intent);
//        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("http","onNewIntent");
        setIntent(intent);
        if (!api.handleIntent(intent, this)) {
            finish();
        }
    }
}
