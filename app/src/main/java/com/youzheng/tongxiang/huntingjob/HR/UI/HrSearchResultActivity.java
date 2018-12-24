package com.youzheng.tongxiang.huntingjob.HR.UI;

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
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.SearchDataList;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaSelectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.BeanCate;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CateBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewEducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationDetailsBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.SearchNewDataList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category.CategoryAreaFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category.CategoryNeedFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category.DetailsCategoryFragment;
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
 * Created by qiuweiyu on 2018/5/10.
 */

public class HrSearchResultActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.tv_go_search)
    TextView tvGoSearch;
    @BindView(R.id.tv_jingyan)
    TextView tvJingyan;
    @BindView(R.id.rl_zhineng)
    RelativeLayout rlZhineng;
    @BindView(R.id.tv_self)
    TextView tvSelf;
    @BindView(R.id.rl_work_self)
    RelativeLayout rlWorkSelf;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rl_gongzi)
    RelativeLayout rlGongzi;
    @BindView(R.id.tv_xueli)
    TextView tvXueli;
    @BindView(R.id.rl_xueli)
    RelativeLayout rlXueli;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    private int page = 1, rows = 20, position1 = -1, position2 = -2, position3 = -3, position4 = -4 ,position5=-1,position6=-1;
    Map<String,Object> map = new HashMap<>();

    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数
    private JZVideoPlayerStandard currPlayer;

    private int allCount ,layout_view;

    private String videoType ;

    List<UserJlBean> data = new ArrayList<>();
    CommonAdapter<UserJlBean> adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_search_result_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        String keyWord = getIntent().getStringExtra("keyWord");
        videoType = getIntent().getStringExtra("videoType");
        if (keyWord!=null){
            map.put("param",keyWord);
            tvSearch.setText(keyWord);
        }

        if (videoType!=null){
            map.put("hasVideo",1);
        }

        map.put("page",page);
        map.put("rows",rows);
        map.put("uid",uid);
        initView();

        initData(map);

        initEvent();
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
        JZVideoPlayerStandard.releaseAllVideos();
    }

    private void initEvent() {
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                map.put("page",page);
                initData(map);
            }

            @Override
            public void onLoadmore() {
                if (allCount>20){
                    if (page*20<allCount){
                        page++;
                        map.put("page",page);
                        initData(map);
                    }
                }
            }
        });
        tvGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();
                map.put("param",tvSearch.getText().toString());
                initData(map);
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

        if (videoType==null){
            layout_view = R.layout.hr_home_ls_item ;
        }else {
            layout_view = R.layout.show_job_fragment_ls_item;
        }

        adapter = new CommonAdapter<UserJlBean>(mContext,data,layout_view) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {
                if (videoType == null) {
                    if (item.getLastestCompany() == null) {
                        helper.setText(R.id.tv_style, "暂无公司");
                    } else {
                        helper.setText(R.id.tv_style, item.getLastestCompany());
                    }
                    helper.setText(R.id.tv_name, item.getTruename());
                    if (item.getGender() != 1) {
                        helper.setText(R.id.tv_sex, "女");
                        ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_24_nv);
                    } else {
                        helper.setText(R.id.tv_sex, "男");
                        ((ImageView) helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_25_nan);
                    }
                    helper.setText(R.id.tv_age, item.getBirthdate());
                    helper.setText(R.id.tv_work_year, "" + item.getWork_year() + "年" + " | " + item.getEducation() + " | " + item.getCitysName());
                    helper.setText(R.id.tv_status, item.getStatus());
                    helper.setText(R.id.tv_get_job, "" + item.getPosition());
                    Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((ImageView) helper.getView(R.id.civ));
                    helper.setText(R.id.tv_see_time, item.getUpdate_time() + "更新");
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, HrJianliDetailsActivity.class);
                            intent.putExtra("jid", item.getId());
                            startActivity(intent);
                        }
                    });
                    if (item.getVideoUpdate()!=null) {
                        if (item.getVideoUpdate() == 1) {
                            helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                        }else {
                            helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
                        }
                    }

                }else {
                    helper.setText(R.id.tv_job_name,item.getTruename());
                    helper.setText(R.id.tv_work_year,""+item.getWork_year());
                    helper.setText(R.id.tv_work_edu,item.getEducation());
                    helper.setText(R.id.tv_work_address,item.getCitysName());
                    helper.setText(R.id.tv_see_num,""+item.getVideoPageView());
                    helper.getView(R.id.tv_money).setVisibility(View.GONE);
                    helper.setText(R.id.text_label,"#"+item.getTrade()+"#");
                    Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((CircleImageView)helper.getView(R.id.civ));
                    final ImageView imageView = helper.getView(R.id.iv_introduce);
                    helper.setText(R.id.text_comment,item.getVideoComment());
                    ((TextView)helper.getView(R.id.tv_delivery)).setText("面试邀请");
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
                    Uri uri ;
                    if (item.getVideoIndexImg()==null){
                        uri = Uri.parse ("");
                    }else {
                        uri = Uri.parse(item.getVideoIndexImg());
                    }
                    videoPlayerStandard.setUp(item.getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");
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

                    helper.getView(R.id.ll_go).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext,HrJianliDetailsActivity.class);
                            intent.putExtra("jid",item.getId());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        ls.setAdapter(adapter);


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

    private void initData(Object o) {
        Log.e("ssssss",gson.toJson(o));
        OkHttpClientManager.postAsynJson(gson.toJson(o), UrlUtis.NEW_SEARCH_JIANLI, new OkHttpClientManager.StringCallback() {
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
                        allCount = dataList.getTotalCount();
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

    @Subscribe
    public void OnEvent(EducationDetailsBean bean){
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
                initData(map);
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
                initData(map);
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
        initData(map);
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
            initData(map);
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
        initData(map);
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
