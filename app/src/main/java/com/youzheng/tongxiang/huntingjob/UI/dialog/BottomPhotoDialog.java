package com.youzheng.tongxiang.huntingjob.UI.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.tongxiang.huntingjob.R;

import java.io.IOException;

import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2018/2/22.
 */

public class BottomPhotoDialog extends Dialog {

    private Context context;
    ImageCaptureManager captureManager ;
    private int layout ;
    View view ;
    private TextView tv_pick_phone ,tv_select_photo,tv_cancel ;
    private String name ,name2 ;

    public BottomPhotoDialog(Context context ,int mlayout) {
        super( context , R.style.BottomDialog);
        this.context = context;
        layout = mlayout ;
    }

    public BottomPhotoDialog(Context context ,int mlayout,String name ,String name2) {
        super( context , R.style.BottomDialog);
        this.context = context;
        layout = mlayout ;
        this.name = name;
        this.name2 = name2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    public View getView() {
        return view;
    }

    public TextView getTv_pick_phone() {
        return tv_pick_phone;
    }

    public TextView getTv_select_photo() {
        return tv_select_photo;
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view =inflater.inflate(layout, null);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        params.width = d.widthPixels;
        params.height = d.heightPixels;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);

        tv_pick_phone = view.findViewById(R.id.tv_pick_phone);
        tv_select_photo = view.findViewById(R.id.tv_pick_zone);
        tv_cancel = view.findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if (name!=null){
            tv_pick_phone.setText(name);
            tv_select_photo.setText(name2);
        }
    }

}

