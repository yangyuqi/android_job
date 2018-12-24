package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.youzheng.tongxiang.huntingjob.R;

import java.lang.reflect.Constructor;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;


public class FullScreenActivity extends BaseActivity {

    @BindView(R.id.video_view)
    JZVideoPlayerStandard videoView;

    String url, comment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_layout);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        comment = getIntent().getStringExtra("title");
        if (url == null) {
            finish();
            return;
        }
        if (comment == null) {
            finish();
            return;
        }
        videoView.fullscreenButton.setVisibility(View.GONE);
        videoView.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        videoView.getCurrentPositionWhenPlaying();
        videoView.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN,comment);
        videoView.startWindowFullscreen();
//        JZVideoPlayerStandard.instance().mediaPlayer.seekTo(4000);
//       startFullscreen(mContext, JCVideoPlayerStandard.class, url, comment);
//        JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class, url, "嫂子辛苦了");
    }



    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayerStandard.releaseAllVideos();
    }

//    public  void startFullscreen(Context context, Class _class, String url, Object... objects) {
//
////        hideSupportActionBar(context);
//        ViewGroup vp = (ViewGroup) (JCUtils.getAppCompActivity(context)).findViewById(Window.ID_ANDROID_CONTENT);
//        View old = vp.findViewById(JCVideoPlayer.FULLSCREEN_ID);
//        if (old != null) {
//            vp.removeView(old);
//        }
//        try {
//            Constructor<JCVideoPlayerStandard> constructor = _class.getConstructor(Context.class);
//            JCVideoPlayerStandard jcVideoPlayer = constructor.newInstance(context);
//            jcVideoPlayer.setId(JCVideoPlayerStandard.FULLSCREEN_ID);
//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            int w = wm.getDefaultDisplay().getWidth();
//            int h = wm.getDefaultDisplay().getHeight();
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(h, w);
//            lp.setMargins((w - h) / 2, -(w - h) / 2, 0, 0);
//            vp.addView(jcVideoPlayer, lp);
//
////            final Animation ra = AnimationUtils.loadAnimation(context, fm.jiecao.jcvideoplayer_lib.R.anim.start_fullscreen);
////            jcVideoPlayer.setAnimation(ra);
//
//            jcVideoPlayer.setUp(url, JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, objects);
//            jcVideoPlayer.addTextureView();
//            jcVideoPlayer.setRotation(90);
//
//            jcVideoPlayer.backButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   finish();
//                }
//            });
//            jcVideoPlayer.fullscreenButton.setVisibility(View.GONE);
////            jcVideoPlayer.startButton.performClick();
////            jcVideoPlayer.startPlayLocic();
//
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
