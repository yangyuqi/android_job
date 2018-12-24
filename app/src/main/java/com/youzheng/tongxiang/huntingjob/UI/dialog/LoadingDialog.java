package com.youzheng.tongxiang.huntingjob.UI.dialog;

/*
    Android LoadingDialog for request data
    Copyright (c) 2015 Wang jun<835502742@qq.com> 
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;

import com.youzheng.tongxiang.huntingjob.R;


public class LoadingDialog extends Dialog {

	private ProgressBar mLoading;
	private RotateAnimation mRotateAnimation;
	private float rotateAngle = 359;
	private long FLIP_ANIMATION_DURATION = 1000;
	private static LoadingDialog mLoadingDialog;


	private static Handler handler;
	private int waitTime = 3*60*1000;
	private static final int MSG_DISMISS = 0x00012;


	private LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init();
	}
	
	private LoadingDialog(Context context) {
		super(context);
		init();
	}

	private LoadingDialog(Context context, int style) {
		super(context, style);
		init();
	}

	public static synchronized LoadingDialog getInstance(Context context) {
		if(mLoadingDialog == null && context != null) {
			mLoadingDialog = new LoadingDialog(context, R.style.iphone_progress_dialog);
			mLoadingDialog.setCancelable(false);
		}
		return mLoadingDialog;
	}

	private void init() {
		setContentView(R.layout.dialog_loading);
		mLoading = (ProgressBar)findViewById(R.id.progress_loading);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;

//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//				| View.SYSTEM_UI_FLAG_IMMERSIVE
//				| View.SYSTEM_UI_FLAG_FULLSCREEN);

		window.setBackgroundDrawableResource(android.R.color.transparent);

		window.setAttributes(params);

		mRotateAnimation = new RotateAnimation(0, rotateAngle , Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateAnimation.setInterpolator(new LinearInterpolator());
		mRotateAnimation.setDuration(FLIP_ANIMATION_DURATION );
		mRotateAnimation.setFillAfter(true);
		mRotateAnimation.setRepeatMode(Animation.RESTART);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);

		mLoading.startAnimation(mRotateAnimation);

		initTimer();
	}

	private void initTimer(){
		if(handler == null){
			handler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					switch (msg.what){
						case MSG_DISMISS:
							 dismiss();
							 break;
					}
				}
			};
		}
	}

	@Override
	public void show() {
		super.show();
		mLoading.startAnimation(mRotateAnimation);
		handler.sendEmptyMessageDelayed(MSG_DISMISS, waitTime);
	}

	public static void dismissDialog() {
		if(mLoadingDialog != null) {
			mLoadingDialog.dismiss();
		}
	}

	@Override
	public void dismiss() {
		super.dismiss();
		mLoading.clearAnimation();
		mLoadingDialog = null;
	}
	
}
