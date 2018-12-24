package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.HR.UI.GoVideoActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.GudieVideoList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.GudieVideoListBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.change.BaseRecAdapter;
import com.youzheng.tongxiang.huntingjob.UI.change.BaseRecViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.change.MyVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class GuideVideoActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.do_video)
    TextView doVideo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_click)
    RelativeLayout rlClick;

    private List<GudieVideoListBean> urlList;
    private ListVideoAdapter videoAdapter;
    private SnapHelper snapHelper;

    private LinearLayoutManager layoutManager;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_video_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();

        initData();

        addListener();
    }

    private void initData() {

        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtis.INTRO_LIST_VIDEO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    GudieVideoList list = gson.fromJson(gson.toJson(entity.getData()), GudieVideoList.class);
                    if (list.getVideo().size() > 0) {
                        videoAdapter = new ListVideoAdapter(list.getVideo());
                        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(videoAdapter);
                    }
                }
            }
        });

    }

    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        type = getIntent().getStringExtra("type");
        doVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intd = new Intent(mContext, GoVideoActivity.class);
                intd.putExtra("type", type);
                startActivity(intd);
            }
        });

        urlList = new ArrayList<>();

        rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlClick.setVisibility(View.GONE);
            }
        });

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        videoAdapter = new ListVideoAdapter(urlList);
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoAdapter);
    }


    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }


    class ListVideoAdapter extends BaseRecAdapter<GudieVideoListBean, VideoViewHolder> {


        public ListVideoAdapter(List<GudieVideoListBean> list) {
            super(list);
        }

        @Override
        public void onHolder(VideoViewHolder holder, GudieVideoListBean bean, int position) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

            holder.mp_video.setUp(bean.getUrl(), JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
//            if (position == 0) {
//                holder.mp_video.startVideo();
//            }
            Uri uri;
            if (bean.getIndexImg() == null) {
                uri = Uri.parse("");
            } else {
                uri = Uri.parse(bean.getIndexImg());
            }
            holder.mp_video.thumbImageView.setScaleType(ImageView.ScaleType.CENTER);
            Glide.with(mContext).load(uri).error(R.mipmap.gggggroup).into(holder.mp_video.thumbImageView);
        }

        @Override
        public VideoViewHolder onCreateHolder() {
            return new VideoViewHolder(getViewByRes(R.layout.item_page2));

        }


    }

    public class VideoViewHolder extends BaseRecViewHolder {
        public View rootView;
        public MyVideoPlayer mp_video;
        public TextView tv_title;

        public VideoViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mp_video = rootView.findViewById(R.id.mp_video);
            this.tv_title = rootView.findViewById(R.id.tv_title);
        }

    }

    private void addListener() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        View view = snapHelper.findSnapView(layoutManager);
                        JZVideoPlayer.releaseAllVideos();
                        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                        if (viewHolder != null && viewHolder instanceof VideoViewHolder) {
                            ((VideoViewHolder) viewHolder).mp_video.startVideo();
                        }

                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String s){
        if (s.equals("finish")){
            finish();
        }
    }
}
