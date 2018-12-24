package com.youzheng.tongxiang.huntingjob.HR;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrJianliDetailsActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrSearchResultActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.InterViewListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.InterViewListData;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobCollectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobData;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.JianLiViewListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.JianliViewListData;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.SearchNewDataList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FullScreenActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayerStandard;


public class HrInterViewFragment extends BaseFragment {
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;
    Unbinder unbinder;

    CommonAdapter<UserJlBean> adapter ;
    List<UserJlBean> data = new ArrayList<>();

    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数
    private JZVideoPlayerStandard currPlayer;

    private int page  = 1 ,rows = 10 ,allCount,totalPage;
    int width ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        width = display.getWidth();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_job_fragment_layout,null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            JZVideoPlayerStandard.releaseAllVideos();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",rows);
        map.put("hasVideo",1);
        map.put("uid",uid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.NEW_SEARCH_JIANLI, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    SearchNewDataList dataList = gson.fromJson(gson.toJson(entity.getData()),SearchNewDataList.class);
                    if (dataList.getTotalCount()>0){
//                        allCount = dataList.getTotalCount();
                        totalPage = dataList.getTotalPage();
                        if (dataList.getList().getVo_list().size()>0&&dataList.getList().getVo_list().size()<=20){

                            if (page==1){
                                data.clear();
                            }
                            data.addAll(dataList.getList().getVo_list());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }else {
                            data.addAll(dataList.getList().getVo_list());
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        adapter.setData(new ArrayList<UserJlBean>());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initView() {
        adapter = new CommonAdapter<UserJlBean>(mContext,data,R.layout.show_job_fragment_ls_item) {
            @Override
            public void convert(final ViewHolder helper, final UserJlBean item) {
                helper.setText(R.id.tv_job_name,item.getTruename());
                helper.setText(R.id.tv_work_year,""+item.getWork_year());
                helper.setText(R.id.tv_work_edu,item.getEducation());
                helper.setText(R.id.tv_work_address,item.getCitysName());
                helper.setText(R.id.tv_see_num,""+item.getVideoPageView());
                helper.getView(R.id.tv_money).setVisibility(View.GONE);
                Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((CircleImageView)helper.getView(R.id.civ));
                final ImageView imageView = helper.getView(R.id.iv_introduce);
                helper.setText(R.id.text_comment,item.getVideoComment());
                ((TextView)helper.getView(R.id.tv_delivery)).setText("面试邀请");
                helper.setText(R.id.text_label,"#"+item.getTrade()+"#");
                helper.setText(R.id.tv_hour,item.getUpdate_time());
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

                if (videoPlayerStandard==null){

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

                videoPlayerStandard.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        if (!item.isAddNum()) {
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

                helper.getView(R.id.tv_collect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (accessToken.equals("")){
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }else {
                            doActionCollect(item.getId());
                        }
                    }
                });

                helper.getView(R.id.tv_delivery).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (accessToken.equals("")){
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }else {
                            inviteInterView(item.getId(),((TextView)helper.getView(R.id.tv_delivery)));
                        }
                    }
                });

                helper.getView(R.id.ll_go).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,HrJianliDetailsActivity.class);
                        intent.putExtra("jid",item.getId());
                        startActivity(intent);
                    }
                });
            }
        };

        ls.setAdapter(adapter);


        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HrSearchResultActivity.class);
                intent.putExtra("videoType","");
                startActivity(intent);
            }
        });

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                initData();
            }

            @Override
            public void onLoadmore() {
                page++;
               if (page<=totalPage){
                        initData();
                    }else {
                        sv.onFinishFreshAndLoad();
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

    public void doActionCollect(final int id){
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CO_COLLECT_LIST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    final ListJobData listJobData = gson.fromJson(gson.toJson(entity.getData()), ListJobData.class);
                    if (listJobData.getData().size() > 0) {
                        final List<Integer> id_list = new ArrayList<Integer>();
                        final List<String> title_list = new ArrayList<String>();
                        id_list.add(-1);
//                        title_list.add("创建新的收藏夹");
                        for (int i = 0; i < listJobData.getData().size(); i++) {
                            id_list.add(listJobData.getData().get(i).getId());
                            title_list.add(listJobData.getData().get(i).getName());
                        }
                        OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                if (title_list.get(options1).equals("创建新的收藏夹")) {
//                                    addNewCollect();
                                } else {
                                    try {
                                        addCollect(listJobData.getData().get(options1),id);
                                    }catch (Exception e){}
                                }

                            }
                        }).setTitleText("收藏夹选择").build();
                        pvCustomTime.setPicker(title_list);
                        pvCustomTime.show();
                    }
                }
            }
        });
    }

    private void addCollect(ListJobCollectBean listJobCollectBean,int jid) {
        Map<String, Object> t_map = new HashMap<>();
        t_map.put("jid", jid);
        t_map.put("uid", uid);
        t_map.put("ctype", "0");
        t_map.put("favoritesId", listJobCollectBean.getId());
        OkHttpClientManager.postAsynJson(gson.toJson(t_map), UrlUtis.JOB_COLLECT, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                showToast(entity.getMsg());
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    initData();
                }
            }
        });
    }

    public void inviteInterView(int jid, final TextView view){
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("uid", uid);
        objectMap.put("rid", jid);
        OkHttpClientManager.postAsynJson(gson.toJson(objectMap), UrlUtis.GET_HR_CO_JOB, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    final ListJobData listJobData = gson.fromJson(gson.toJson(entity.getData()), ListJobData.class);
                    if (listJobData.getList().size() > 0) {
                        final List<Integer> id_list = new ArrayList<Integer>();
                        List<String> title_list = new ArrayList<String>();
                        for (int i = 0; i < listJobData.getList().size(); i++) {
                            id_list.add(listJobData.getList().get(i).getJid());
                            title_list.add(listJobData.getList().get(i).getTitle());
                        }


                        OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                int m_id = listJobData.getList().get(options1).getJid();
                                String time = PublicUtils.testDay(7).get(options2) + "   " + PublicUtils.showtime().get(options3);
                                initInter(m_id, "3", time,view);
                            }
                        }).setTitleText("职位选择").build();
                        pvCustomTime.setNPicker(title_list, PublicUtils.testDay(7), PublicUtils.showtime());
                        pvCustomTime.show();
                    }
                }
            }
        });
    }

    private void initInter(int m_id, final String status, String time, final TextView view) {
        Map<String, Object> map = new HashMap<>();
        map.put("rid", String.valueOf(rid));
        map.put("status", status);
        map.put("id", "");
        map.put("jid", String.valueOf(m_id));
        map.put("interview_time", time);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_DELIVERY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    if (status.equals("3")) {
                        view.setText("已邀请面试");
                    } else if (status.equals("4")) {
                        view.setText("邀请面试");
                    }
                }
            }
        });
    }

    public void addVideoNum(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("videoType",0);
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
