package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaSelectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.BeanCate;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CateBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewEducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.SearchJobBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.SearchJobData;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationDetailsBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category.CategoryAreaFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category.CategoryNeedFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category.DetailsCategoryFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.CategoryFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class SearchJobActivity extends BaseActivity {
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_zhineng)
    RelativeLayout rlZhineng;
    @BindView(R.id.tv_go_search)
    TextView tvGoSearch;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rl_gongzi)
    RelativeLayout rlGongzi;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rl_work_self)
    RelativeLayout rlWorkSelf;
    @BindView(R.id.tv_self)
    TextView tvSelf;
    @BindView(R.id.tv_jingyan)
    TextView tvJingyan;
    @BindView(R.id.tv_xueli)
    TextView tvXueli;
    @BindView(R.id.rl_xueli)
    RelativeLayout rlXueli;
    @BindView(R.id.sv)
    SpringView sv;

    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数
    private JZVideoPlayerStandard currPlayer;

    private List<JobBeanDetails> data_data = new ArrayList<>();
    private CommonAdapter<JobBeanDetails> adapter;

    private int page = 1, rows = 20, position1 = -1, position2 = -2, position3 = -3, position4 = -4 ,position5=-1,position6=-1;

    Map<String, Object> map = new HashMap<>();
    private String keyWords ,type ,more ,videoType;

    private int occupation ,layoutView ,allPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_job_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        keyWords = getIntent().getStringExtra("keyWord");
        type = getIntent().getStringExtra("type");
        more = getIntent().getStringExtra("more");
        occupation = getIntent().getIntExtra("occupation",-1);
        videoType = getIntent().getStringExtra("videoType");
        initData();
    }

    private void initData() {
        ls.setFocusable(false);
        if (videoType==null){
            layoutView = R.layout.home_page_ls_item ;
        }else {
            layoutView = R.layout.show_job_fragment_ls_item ;
        }


        adapter = new CommonAdapter<JobBeanDetails>(mContext, data_data, layoutView) {
            @Override
            public void convert(ViewHolder helper, final JobBeanDetails item) {
                if (videoType==null) {
                    helper.setText(R.id.tv_name, item.getTitle());
                    helper.setText(R.id.tv_level_l, item.getEducation() + "/" + item.getExperience() + "/" + item.getCity());
                    if (item.getWage_face() == 0) {
                        helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                    } else {
                        helper.setText(R.id.tv_money, "面议");
                    }
                    helper.setText(R.id.tv_name_co, item.getName());
                    helper.setText(R.id.tv_below, item.getTrade());
                    Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.youyuzhanweicopy2).into((ImageView) helper.getView(R.id.iv_logo));
                    helper.setText(R.id.tv_update_time, item.getCreate_time());
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, IntroduceJobActivity.class);
                            intent.putExtra("id", item.getId());
                            startActivity(intent);
                        }
                    });
                    ArrayList<String> arrayList = new ArrayList<>();
                    LabelsView labelsView = helper.getView(R.id.labelView);
                    if (item.getJobtag()!=null) {
                        String[] strings = item.getJobtag().split(",");
                        for (int i = 0; i < strings.length; i++) {
                            arrayList.add(strings[i]);
                        }
                    }
                    labelsView.setLabels(arrayList);

                    if (item.getVideoUpdated()==1){
                        helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                    }else {
                        helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
                    }
                }else {

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
                    Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.gggggroup).into((CircleImageView)helper.getView(R.id.civ));
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
                    videoPlayerStandard.fullscreenButton.setVisibility(View.GONE);
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

                    helper.getView(R.id.ll_go).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, IntroduceJobActivity.class);
                            intent.putExtra("id",item.getId());
                            startActivity(intent);
                        }
                    });

                    helper.getView(R.id.tv_collect).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (accessToken.equals("")){
                                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            }
                        }
                    });

                    helper.getView(R.id.tv_delivery).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (accessToken.equals("")){
                                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            }
                        }
                    });

                }
            }
        };
        ls.setAdapter(adapter);

        if (videoType!=null){
            map.put("hasVideo",1);
        }

        if (occupation!=-1){
            map.put("occupation",occupation);
        }

        if (more!=null){
            map.put("accessToken",accessToken);
        }

        if (keyWords!=null){
            tvSearch.setText(keyWords);
            if (!keyWords.equals("兼职")) {
                if (occupation ==-1) {
                    map.put("param", keyWords);
                }
            }
            if (keyWords.equals("兼职")){
                tvXueli.setText("兼职");
                Map<String, Object> map2 = new HashMap<>();
                map2.put("ctype", "jobs_nature");
                OkHttpClientManager.postAsynJson(gson.toJson(map2), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            EducationBean comclass = gson.fromJson(gson.toJson(entity.getData()), EducationBean.class);
                            for (int i = 0 ;i <comclass.getComclass().size();i++){
                                if (comclass.getComclass().get(i).getName().equals("兼职")){
                                    map.put("page", page);
                                    map.put("rows", rows);
                                    map.put("jobs_nature",comclass.getComclass().get(i).getId());
                                    cataSearch(map);
                                }
                            }
                        }
                    }
                });
            }

        }

        if (type!=null){
            if (type.equals("应届生")){
                tvJingyan.setText("应届毕业生");
                Map<String, Object> map1 = new HashMap<>();
                map1.put("ctype", "experience");
                OkHttpClientManager.postAsynJson(gson.toJson(map1), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            EducationBean comclass = gson.fromJson(gson.toJson(entity.getData()), EducationBean.class);
                            for (int i = 0 ;i <comclass.getComclass().size();i++){
                                if (comclass.getComclass().get(i).getName().equals("应届毕业生")){
                                    map.put("page", page);
                                    map.put("rows", rows);
                                    map.put("experience",comclass.getComclass().get(i).getId());
                                    cataSearch(map);
                                }
                            }
                        }
                    }
                });
            }

        }

        if (type==null) {
            if (keyWords!=null) {
                if (!keyWords.equals("兼职")) {
                    map.put("page", page);
                    map.put("rows", rows);
                    cataSearch(map);
                }
            }else {
                map.put("page", page);
                map.put("rows", rows);
                cataSearch(map);
            }
        }

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                data_data.clear();
                map.put("page",page);
                cataSearch(map);
            }

            @Override
            public void onLoadmore() {
                if (allPage>0){
                    if (page<allPage){
                        page++;
                        map.put("page",page);
                        cataSearch(map);
                    }else {
                        sv.onFinishFreshAndLoad();
                    }
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }


    @OnClick({R.id.iv_back, R.id.rl_zhineng, R.id.rl_gongzi, R.id.tv_go_search, R.id.rl_work_self, R.id.rl_xueli})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.rl_zhineng:
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fl_content, DetailsCategoryFragment.getInstance(new BeanCate(position1, "experience"))).commit();
                break;
            case R.id.rl_gongzi:
                FragmentTransaction transaction1 = fm.beginTransaction();
                transaction1.replace(R.id.fl_content, DetailsCategoryFragment.getInstance(new BeanCate(position3, "wage"))).commit();
                break;
            case R.id.tv_go_search:
                map.put("param", tvSearch.getText().toString());
                cataSearch(map);
                PublicUtils.closeKeybord(tvSearch, mContext);
                break;
            case R.id.rl_work_self:
                FragmentTransaction transaction2 = fm.beginTransaction();
                transaction2.replace(R.id.fl_content, new CategoryAreaFragment()).commit();
                break;
            case R.id.rl_xueli:
                FragmentTransaction transaction3 = fm.beginTransaction();
                transaction3.replace(R.id.fl_content, CategoryNeedFragment.getInstance(new CateBean(position5,position6))).commit();
                break;
        }
    }

    public void cataSearch(Object o) {
        Log.e("ssssss",gson.toJson(o));
        OkHttpClientManager.postAsynJson(gson.toJson(o), UrlUtis.NEW_SEARCH_JOB, new OkHttpClientManager.StringCallback() {
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
                    if (jobBean.getTotalCount()>=page*rows){
                        if (page==1){
                            data_data.clear();
                        }

                        data_data.addAll(jobBean.getList().getVo_list());
                        adapter.setData(data_data);
                    }else {
                        adapter.setData(jobBean.getList().getVo_list());
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Subscribe
    public void onEvent(EducationDetailsBean bean) {
        if (bean != null) {
            if (bean.getCtype().equals("wage")) {
                if (bean.getSelected()==1){
                    position3 = bean.getPosition();
                    if (position3 != -3) {
                        map.put("wageLevel", bean.getId());
                        tvMoney.setText(bean.getName());
                    }else {
                        if (map.containsKey("wageLevel")) {
                            map.remove("wageLevel");
                        }
                        tvMoney.setText("薪资范围");
                    }
                }else {
                    position3 = -3 ;
                    if (map.containsKey("wageLevel")) {
                        map.remove("wageLevel");
                    }
                    tvMoney.setText("薪资范围");
                }
                cataSearch(map);
            } else if (bean.getCtype().equals("experience")) {
                if (bean.getSelected()==1){
                    position1 = bean.getPosition();
                    if (position1 != -1) {
                        map.put("experience", bean.getId());
                        tvJingyan.setText(bean.getName());
                    } else {
                        if (map.containsKey("experience")) {
                            map.remove("experience");
                        }
                        tvJingyan.setText("工作经验");
                    }
                }else {
                    if (map.containsKey("experience")) {
                        map.remove("experience");
                    }
                    position1=-1;
                    tvJingyan.setText("工作经验");
                }
                cataSearch(map);
            }
        }
    }

    @Subscribe
    public void onEvent(NewEducationBean bean){
        StringBuilder builder = new StringBuilder();
        if (bean!=null){
            if (bean.getCtype()!=null) {
                if (bean.getCtype().equals("jobs_nature")) {
                    if (bean.getSelected()==1) {
                        builder.append(bean.getName());
                        map.put("jobs_nature", bean.getId());
                        position5 = bean.getPosition();
                    }else {
                        position5=-1;
                    }
                }
            }else {
                if (map.containsKey("jobs_nature")){
                    map.remove("jobs_nature");
                    position5=-1;
                }
            }
            if (bean.getCtype2()!=null) {
                if (bean.getCtype2().equals("education")) {
                    if (bean.getSelected2()==1) {
                        builder.append(bean.getName2());
                        map.put("education", bean.getId2());
                        position6 = bean.getPosition2();
                    }else {
                        position6=-1;
                    }
                }
            }else {
                if (map.containsKey("education")){
                    position6=-1;
                    map.remove("education");
                }
            }
        }

        tvXueli.setText(builder.toString());
        cataSearch(map);
    }

    @Subscribe
    public void onEvent(String s){
        if (s.equals("refresh")){
            tvXueli.setText("要求");
            position5 = -1;position6=-1;
            if (map.containsKey("jobs_nature")){
                map.remove("jobs_nature");
            }
            if (map.containsKey("education")){
                map.remove("education");
            }
            cataSearch(map);
        }
    }

    @Subscribe
    public void onEvent(AreaSelectBean bean){
        tvSelf.setText(bean.getName());
        if (bean.getArrayList().size()>0){
                map.put("cityid",bean.getArrayList());
        }else {
            if (map.containsKey("cityid")){
                map.remove("cityid");
            }
        }
        cataSearch(map);
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
