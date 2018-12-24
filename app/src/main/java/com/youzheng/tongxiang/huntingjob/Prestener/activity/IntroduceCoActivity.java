package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBeanEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.VideoBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Introduce.IntroduceCoFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Introduce.IntroduceJobFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CustomViewPager;
import com.youzheng.tongxiang.huntingjob.UI.Widget.FullScreenView;
import com.youzheng.tongxiang.huntingjob.UI.Widget.NewVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by qiuweiyu on 2018/2/9.
 */

public class IntroduceCoActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.view_page)
    CustomViewPager viewPage;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_co_name)
    TextView tvCoName;
    @BindView(R.id.tv_ctype)
    TextView tvCtype;
    @BindView(R.id.tv_co_num)
    TextView tvCoNum;
    @BindView(R.id.tv_businness)
    TextView tvBusinness;

    public int com_id;
    @BindView(R.id.video_view)
    JZVideoPlayerStandard videoView;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.rl_has_video)
    RelativeLayout rlHasVideo;
    @BindView(R.id.iv_introduce)
    ImageView ivIntroduce;
    private String from;

    private List<Fragment> list = new ArrayList<>();
    private MyFragmentPagerAdapter adapter;
    private ArrayList<String> label = new ArrayList<>();
    private String[] titles = {"公司介绍", "招聘职位"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_co_layout);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        com_id = getIntent().getIntExtra("id", 0);
        from = getIntent().getStringExtra("from");
        initData();
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", com_id);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CO_DETAILS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    CoBean bean = gson.fromJson(gson.toJson(entity.getData()), CoBean.class);
                    Glide.with(mContext).load(bean.getCompany().getLogo()).placeholder(R.mipmap.gongsixinxi).into(ivLogo);
                    tvCoName.setText(bean.getCompany().getName());
                    tvCtype.setText(bean.getCompany().getCtypeName());
                    tvCoNum.setText(bean.getCompany().getScaleName());
                    tvBusinness.setText(bean.getCompany().getTradeName());
//                    EventBus.getDefault().post(bean.getCompany());
                    initView(bean.getCompany());
                    if (bean.getVideo() != null) {
                        if (bean.getVideo().getVideoUpdated() == 1) {
                            rlHasVideo.setVisibility(View.VISIBLE);
                            initMedia(bean.getVideo(),com_id);
                        } else {
                            rlHasVideo.setVisibility(View.GONE);
                        }
                    } else {
                        rlHasVideo.setVisibility(View.GONE);
                    }
                }
            }
        });
    }


    private void initMedia(final VideoBean videoBean, final int com_id) {
        videoView.setUp(videoBean.getUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");
        videoView.backButton.setVisibility(View.GONE);
        videoView.fullscreenButton.setVisibility(View.VISIBLE);
        Uri uri ;
        if (videoBean.getIndeximg()==null){
            uri = Uri.parse ("");
        }else {
            uri = Uri.parse(videoBean.getIndeximg());
        }
        videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        videoView.thumbImageView.setImageURI(uri);
        Glide.with (mContext).load (uri).error(R.mipmap.gggggroup).into (videoView.thumbImageView);

        videoView.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!videoBean.isAddNum()) {
                    addVideoNum(com_id);
                    videoBean.setAddNum(true);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        videoView.fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.startWindowFullscreen();
            }
        });
    }

    private void initView(CoBeanEntity coBeanEntity) {
        tab.post(new Runnable() {
            @Override
            public void run() {
                PublicUtils.setTabLine(tab, 65, 65);
            }
        });
        list.clear();
        list.add(IntroduceCoFragment.getInstance(coBeanEntity));
        if (from == null) {
            list.add(IntroduceJobFragment.getInstance(coBeanEntity.getUid(), ""));
        } else {
            list.add(IntroduceJobFragment.getInstance(coBeanEntity.getUid(), from));
        }
        adapter = new MyFragmentPagerAdapter(fm, list);
        viewPage.setAdapter(adapter);
        tab.setupWithViewPager(viewPage);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Integer integer) {
        if (integer == 2222) {
            viewPage.setCurrentItem(1);
        }
    }


    public void addVideoNum(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("videoType",1);
        map.put("id",id);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.VIDEO_ADD, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }
}
