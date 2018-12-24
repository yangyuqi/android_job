package com.youzheng.tongxiang.huntingjob.Prestener.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;


import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.HR.UI.TestActivity;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.InterViewListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.InterViewListData;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.SearchJobData;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.VideoBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FullScreenActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.IntroduceJobActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.SearchJobActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;
import com.youzheng.tongxiang.huntingjob.UI.Widget.NewVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import cn.jzvd.JZVideoPlayerStandard;


public class ShowJobFragment extends BaseFragment {
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;
    Unbinder unbinder;

    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数
    private JZVideoPlayerStandard currPlayer;

    CommonAdapter<JobBeanDetails> adapter ;
    List<JobBeanDetails> data_data = new ArrayList<>();

    private int page  = 1 ,row = 10 ,alCount,allPage,width;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        width = display.getWidth();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_job_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        adapter = new CommonAdapter<JobBeanDetails>(mContext,data_data,R.layout.show_job_fragment_ls_item) {
            @Override
            public void convert(ViewHolder helper, final JobBeanDetails item) {
                helper.setText(R.id.tv_job_name,item.getTitle());
                helper.setText(R.id.tv_work_year,item.getExperience());
                helper.setText(R.id.tv_work_edu,item.getEducation());
                helper.setText(R.id.tv_work_address,item.getCity());
                helper.setText(R.id.tv_see_num,""+item.getVideoPageView());
                if (item.getWage_face()==1){
                    helper.setText(R.id.tv_money,"面议");
                }else {
                    helper.setText(R.id.tv_money,""+item.getWage_min()/1000+"K"+"-"+item.getWage_max()/1000+"K");
                }
                helper.setText(R.id.text_label,"#"+item.getTrade()+"#");
                final TextView tv_collect = helper.getView(R.id.tv_collect);
                if (item.getIsCollect()==0){
                    tv_collect.setText("收藏");
                    Drawable nav_up=getResources().getDrawable(R.mipmap.zz_un_collect);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    tv_collect.setCompoundDrawables(nav_up, null, null, null);
                }else if (item.getIsCollect()==1){
                    Drawable nav_up=getResources().getDrawable(R.mipmap.zz_collect);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    tv_collect.setCompoundDrawables(nav_up, null, null, null);
                    tv_collect.setText("取消收藏");
                }
                final TextView tv_delivery = helper.getView(R.id.tv_delivery);
                if (item.getIsDelivery()==0){
                    tv_delivery.setText("投递简历");
                    Drawable nav_up=getResources().getDrawable(R.mipmap.zz_invite);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    tv_delivery.setCompoundDrawables(nav_up, null, null, null);
                }else if (item.getIsDelivery()==1){
                    tv_delivery.setText("已投递");
                    Drawable nav_up=getResources().getDrawable(R.mipmap.zz_un_invite);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    tv_delivery.setCompoundDrawables(nav_up, null, null, null);
                }
                Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.youyuzhanweicopy2).into((CircleImageView)helper.getView(R.id.civ));
                final ImageView imageView = helper.getView(R.id.iv_introduce);
                helper.setText(R.id.text_comment,item.getVideoComment());
                helper.setText(R.id.tv_hour,item.getVideoUpdateTime());
                JZVideoPlayerStandard videoPlayerStandard = null ;
                JZVideoPlayerStandard one = helper.getView(R.id.video_view);
                JZVideoPlayerStandard two = helper.getView(R.id.video_view2);
                if ((int)item.getWidth()!=0){
                    if (item.getWidth()<item.getHeight()) {
                        one.setVisibility(View.GONE);
                        two.setVisibility(View.VISIBLE);
                    }else {
                        one.setVisibility(View.VISIBLE);
                        two.setVisibility(View.GONE);
                    }
                }else {
                    one.setVisibility(View.VISIBLE);
                    two.setVisibility(View.GONE);
                }
                if (one.getVisibility()==View.VISIBLE){
                    videoPlayerStandard =one;
                }
                if (two.getVisibility()==View.VISIBLE){
                    videoPlayerStandard=two;
                }
                videoPlayerStandard.setUp(item.getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");
                videoPlayerStandard.backButton.setVisibility(View.GONE);
                videoPlayerStandard.fullscreenButton.setVisibility(View.VISIBLE);
                Uri uri ;
                if (item.getVideoIndexImg()==null){
                    uri = Uri.parse ("");
                }else {
                    uri = Uri.parse(item.getVideoIndexImg());
                }
                videoPlayerStandard.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                videoPlayerStandard.thumbImageView.setImageURI(uri);
                Glide.with (mContext).load (uri).error(R.mipmap.gggggroup).into (videoPlayerStandard.thumbImageView);
                final JZVideoPlayerStandard finalVideoPlayerStandard = videoPlayerStandard;
                videoPlayerStandard.fullscreenButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finalVideoPlayerStandard.startWindowFullscreen();
                    }
                });
                if (helper.getPosition()==0){
//                    finalVideoPlayerStandard.startVideo();
                }

                videoPlayerStandard.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            if (!item.getAddNum()) {
                                addVideoNum(item.getId());
                                item.setAddNum(true);
                            }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                helper.getView(R.id.ll_go).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, TestActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    }
                });

                helper.getView(R.id.tv_collect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (accessToken.equals("")){
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }else {
                            if (item.getIsCollect()==0){
                                doCollect(item.getId(),tv_collect,item);
                            }else if (item.getIsCollect()==1){
                                unCollect(item.getId(),tv_collect,item);
                            }
                        }
                    }
                });

                helper.getView(R.id.tv_delivery).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (accessToken.equals("")){
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }else {
                            if (item.getIsDelivery()==1){
                                showToast("您已投递该职位");
                            }else {
                                doDelivery(item.getId(),tv_delivery,item);
                            }

                        }
                    }
                });
            }
        };

        ls.setAdapter(adapter);

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SearchJobActivity.class);
                intent.putExtra("videoType","");
                intent.putExtra("more","");
                startActivity(intent);
            }
        });

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (page>1){
                    page=page-1;
                    initData();
                }else {
                    sv.onFinishFreshAndLoad();
                }
            }

            @Override
            public void onLoadmore() {
                if (allPage>0){
                    if (page<allPage){
                        page++;
                        initData();
                    }else {
                        sv.onFinishFreshAndLoad();
                    }
                }

            }
        });

        ls.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        autoPlayVideo(absListView);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int i2) {
                if (firstVisible == firstVisibleItem) {
                    return;
                }

                firstVisible = firstVisibleItem;
                visibleCount = visibleItemCount;

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",row);
        map.put("hasVideo",1);
        if (!accessToken.equals("")){
            map.put("accessToken",accessToken);
        }
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.NEW_SEARCH_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    SearchJobData jobBean = gson.fromJson(gson.toJson(entity.getData()), SearchJobData.class);
                    allPage = jobBean.getTotalPage();
                    if (page==1){
                        data_data.clear();
                    }
                    data_data.addAll(jobBean.getList().getVo_list());
                    adapter.setData(data_data);
//                    if (jobBean.getTotalCount()>=page*row){
//                        data_data.addAll(jobBean.getList().getVo_list());
//                        adapter.setData(data_data);
//                    }else {
//                        adapter.setData(jobBean.getList().getVo_list());
//                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void doCollect(int mmid, final TextView tv_collect, final JobBeanDetails item){
        Map<String, Object> map = new HashMap<>();
        map.put("jid", mmid);
        map.put("ctype", 1);
        map.put("uid", uid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.JOB_COLLECT, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
//                    ivShoucan.setImageResource(R.mipmap.shoucang1);
                    try {
                        JSONObject object = new JSONObject(gson.toJson(entity.getData()));
                        Drawable nav_up=getResources().getDrawable(R.mipmap.zz_collect);
                        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                        tv_collect.setCompoundDrawables(nav_up, null, null, null);
                        tv_collect.setText("取消收藏");
                        item.setIsCollect(1);
//                        if ()object.getInt("is_collect");
//                        initData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void unCollect(int mmid, final TextView tv_collect, final JobBeanDetails item){
        Map<String, Object> map = new HashMap<>();
        map.put("jid", mmid);
        map.put("ctype", 1);
        map.put("uid", uid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UNSCRIBE_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
//                    ivShoucan.setImageResource(R.mipmap.shoucang4);
                    try {
                        JSONObject object = new JSONObject(gson.toJson(entity.getData()));
                        tv_collect.setText("收藏");
                        Drawable nav_up=getResources().getDrawable(R.mipmap.zz_un_collect);
                        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                        tv_collect.setCompoundDrawables(nav_up, null, null, null);
                        item.setIsCollect(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void doDelivery(int mmid, final TextView tv_delievery, final JobBeanDetails item){
        Map<String, Object> map = new HashMap<>();
        map.put("jid", mmid);
        map.put("rid", rid);
        map.put("uid", uid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.TOUJIANLI_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    item.setIsDelivery(1);
                    tv_delievery.setText("已投递");
                    Drawable nav_up=getResources().getDrawable(R.mipmap.zz_invite);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    tv_delievery.setCompoundDrawables(nav_up, null, null, null);
                }
            }
        });
    }

    public void addVideoNum(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("videoType","2");
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            JZVideoPlayerStandard.releaseAllVideos();
        }
    }

    /**
     * 滑动停止自动播放视频
     */
    private void autoPlayVideo(AbsListView view) {

        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.video_view) != null&&view.getChildAt(i).findViewById(R.id.video_view2) != null) {
                if ( view.getChildAt(i).findViewById(R.id.video_view).getVisibility()==View.VISIBLE) {
                    currPlayer = (JZVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.video_view);
                }else {
                    currPlayer = (JZVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.video_view2);
                }
                Rect rect = new Rect();
                //获取当前view 的 位置
                currPlayer.getLocalVisibleRect(rect);
                int videoheight = currPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (currPlayer.currentState == JZVideoPlayerStandard.CURRENT_STATE_NORMAL
                            || currPlayer.currentState == JZVideoPlayerStandard.CURRENT_STATE_ERROR) {
                        currPlayer.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JZVideoPlayerStandard.releaseAllVideos();
    }


}
