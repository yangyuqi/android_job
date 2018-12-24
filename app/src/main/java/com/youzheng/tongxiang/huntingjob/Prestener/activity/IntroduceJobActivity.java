package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.JobBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.jobluredsBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.VideoBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.dialog.DeleteDialog;
import com.youzheng.tongxiang.huntingjob.UI.dialog.DeleteDialogInterface;
import com.youzheng.tongxiang.huntingjob.UI.dialog.ShareDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;

import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2018/2/9.
 */

public class IntroduceJobActivity extends BaseActivity implements AMapLocationListener {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.rl_see_more)
    RelativeLayout rlSeeMore;
    @BindView(R.id.ls_details)
    WebView lsDetails;
    @BindView(R.id.iv_see_co)
    RelativeLayout ivSeeCo;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_work_style)
    TextView tvWorkStyle;
    @BindView(R.id.tv_experice)
    TextView tvExperice;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.iv_co_icon)
    ImageView ivCoIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_co_name)
    TextView tvCoName;
    @BindView(R.id.tv_co_type)
    TextView tvCoType;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_ctype)
    TextView tvCtype;
    @BindView(R.id.iv_mm)
    ImageView ivMm;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.iv_shoucan)
    ImageView ivShoucan;
    @BindView(R.id.ll_click_shoucan)
    LinearLayout llClickShoucan;
    @BindView(R.id.rl_deliver_msg)
    RelativeLayout rlDeliverMsg;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.rl_shoucan)
    RelativeLayout rlShoucan;
    @BindView(R.id.map)
    MapView map;
    AMap aMap;
    //    @BindView(R.id.labelView)
//    LabelsView labelView;
    @BindView(R.id.top_header_ll_right_iv_caidan)
    ImageView topHeaderLlRightIvCaidan;
    @BindView(R.id.ll_name_2)
    LinearLayout llName2;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.hori_scroll_view)
    HorizontalScrollView horiScrollView;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ls_requried)
    WebView lsRequried;
    @BindView(R.id.video_view)
    JZVideoPlayerStandard playView;
    @BindView(R.id.iv_play)
    ImageView ivPlay;

    @BindView(R.id.rl_has_video)
    RelativeLayout rlHasVideo;

    private int id, collect_id, is_delivery, com_id;
    private double location_wei, location_jing, current_location_wei, current_location_jing;
    private String m_com_address, current_m_address, from;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_job_layout);
        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);
        initView();
        if (aMap == null) {
            aMap = map.getMap();
        }
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initData();
        MobclickAgent.onResume(mContext);
    }



    private void setUpMap(double wei, double jing, String com_address, final String mobile) {
        location_wei = wei;
        location_jing = jing;
        m_com_address = com_address;
        LatLng latLng = new LatLng(wei, jing);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        UiSettings settings = aMap.getUiSettings();
        settings.setZoomGesturesEnabled(false);
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .draggable(false));
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.co_area_layout, null);
        TextView textView = view.findViewById(R.id.tv_address);
        textView.setText(com_address);
//        marker.showInfoWindow();// 设置默认显示一个infowinfow
        marker.setIcon(BitmapDescriptorFactory.fromView(view));
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (ActivityCompat.checkSelfPermission(IntroduceJobActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }

                DeleteDialog dialog = new DeleteDialog(mContext, "提示", "是否拨打" + mobile, "确定");
                dialog.show();
                dialog.OnDeleteBtn(new DeleteDialogInterface() {
                    @Override
                    public void isDelete(boolean isdelete) {
                        if (isdelete) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                                    + mobile));
                            startActivity(intent);
                        }
                    }
                });
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
       JZVideoPlayerStandard.releaseAllVideos();
    }

    private void initMedia(final VideoBean video) {
        playView.setUp(video.getUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, "");
        playView.backButton.setVisibility(View.GONE);
        playView.fullscreenButton.setVisibility(View.VISIBLE);
        Uri uri;
        if (video.getIndeximg() == null) {
            uri = Uri.parse("");
        } else {
            uri = Uri.parse(video.getIndeximg());
        }
        playView.thumbImageView.setImageURI(uri);
        playView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(mContext).load(uri).error(R.mipmap.gggggroup).into(playView.thumbImageView);

        playView.fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FullScreenActivity.class);
                intent.putExtra("url",video.getUrl());
                intent.putExtra("title",video.getComment());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
        JZVideoPlayerStandard.releaseAllVideos();
    }

    private void initData() {
        final Map<String, Object> m_map = new HashMap<>();
        m_map.put("jid", id);
        m_map.put("uid", uid);
        OkHttpClientManager.postAsynJson(gson.toJson(m_map), UrlUtis.JOB_DETAILS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    try {
                        JobBean mm = gson.fromJson(gson.toJson(entity.getData()), JobBean.class);
                        JobBeanDetails details = mm.getJob();
                        tvPlace.setText(details.getCity());
                        if (details.getEducation() != null) {
                            tvClass.setText(details.getEducation());
                        }
                        if (details.getJobs_nature() != null) {
                            tvWorkStyle.setText(details.getJobs_nature());
                        }
                        if (details.getIs_delivery() == 0) {
                            tvSend.setText("投递简历");
                            rlDeliverMsg.setBackgroundResource(R.drawable.toujian_li_bg);
                        } else {
                            tvSend.setText("已投递");
                            rlDeliverMsg.setBackgroundResource(R.drawable.bg_blue_deeper);
                        }

                        tvExperice.setText(details.getExperience());
                        tvYear.setText("" + details.getCount());
                        tvTitle.setText(details.getTitle());
                        if (details.getWage_face() == 0) {
                            tvMoney.setText("" + details.getWage_min() / 1000 + "K" + "-" + details.getWage_max() / 1000 + "K" + " /月");
                        } else {
                            tvMoney.setText("面议");
                        }
                        Glide.with(mContext).load(details.getCom_logo()).placeholder(R.mipmap.gongsilogo).into(ivCoIcon);
                        tvCoName.setText(details.getName());
                        tvCoType.setText(details.getCtype());
                        tvNum.setText(details.getScaleName());
                        tvCtype.setText(details.getTradeName());
                        collect_id = details.getIs_collect();
                        is_delivery = details.getIs_delivery();
                        lsDetails.loadDataWithBaseURL(null, getNewContent(details.getDescription()), "text/html", "utf-8", null);
                        lsRequried.loadDataWithBaseURL(null, getNewContent(details.getRequirement()), "text/html", "utf-8", null);
                        if (details.getIs_collect() == 0) {
                            ivShoucan.setImageResource(R.mipmap.shoucang4);
                            rlShoucan.setBackgroundResource(R.drawable.login_bg_rl);
                            tvCollect.setText("收藏");
                            tvCollect.setTextColor(mContext.getResources().getColor(R.color.text_red));
                        } else if (details.getIs_collect() == 1) {
                            ivShoucan.setImageResource(R.mipmap.shoucang1);
                            rlShoucan.setBackgroundResource(R.drawable.bg_blue_deeper);
                            tvCollect.setText("取消收藏");
                            tvCollect.setTextColor(mContext.getResources().getColor(R.color.text_white));
                        }
                        com_id = details.getCom_id();

                        if (mm.getJoblureds().size() > 0) {
                            addYHView(mm.getJoblureds());
                        } else {
                            horiScrollView.setVisibility(View.GONE);
                        }

                        if (mm.getVideo() != null) {
                            if (mm.getVideo().getVideoUpdated() != null) {
                                if (mm.getVideo().getVideoUpdated() == 0) {
                                    rlHasVideo.setVisibility(View.GONE);
                                } else {
                                    rlHasVideo.setVisibility(View.VISIBLE);
                                    initMedia(mm.getVideo());
                                }
                            }
                        }

                        if (!mm.getJob().getCom_position().equals("0.0,0.0")) {
                            String[] strings = mm.getJob().getCom_position().split(",");
                            setUpMap(Double.valueOf(strings[1]), Double.valueOf(strings[0]), mm.getJob().getAddress(), details.getMobile_phone() != null ? details.getMobile_phone() : details.getTelphone());
                        }else {
                            String[] strings = mm.getJob().getPosition().split(",");
                            setUpMap(Double.valueOf(strings[1]), Double.valueOf(strings[0]), mm.getJob().getAddress(), details.getMobile_phone() != null ? details.getMobile_phone() : details.getTelphone());
                        }

                    } catch (Exception e) {

                    }
                }
            }
        });
    }



    private void addYHView(List<jobluredsBean> joblureds) {
        llContent.removeAllViews();
        for (int i = 0; i < joblureds.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.youhuo_ll_item, null);
            ImageView imageView = view.findViewById(R.id.iv_icon);
            TextView textView = view.findViewById(R.id.tv_name);
            textView.setText(joblureds.get(i).getJobtag());
            Glide.with(mContext).load(joblureds.get(i).getLogo()).error(R.mipmap.gggggroup).into(imageView);
            llContent.addView(view);
        }
    }

    private void initView() {
        textHeadTitle.setText("职位信息");
        btnBack.setVisibility(View.VISIBLE);
        id = getIntent().getIntExtra("id", 0);
        from = getIntent().getStringExtra("from");
        if (from != null) {
            llBottom.setVisibility(View.GONE);
        }
        topHeaderLlRightIvCaidan.setVisibility(View.VISIBLE);
        topHeaderLlRightIvCaidan.setImageResource(R.mipmap.share_icon);
        mlocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mlocationClient.setLocationOption(mLocationOption);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RxPermissions permissions = new RxPermissions(IntroduceJobActivity.this);
            permissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        mlocationClient.startLocation();
                    }
                }
            });
        } else {
            mlocationClient.startLocation();
        }

        map.getMap().setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (!PublicUtils.isAvilible(mContext, "com.autonavi.minimap")) {
                    showToast("请安装高德地图方可导航");
                    return;
                }

                if (current_m_address == null) {
                    showToast("当前定位失败");
                    return;
                }

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                //将功能Scheme以URI的方式传入data
                Uri uri = Uri.parse("androidamap://route?sourceApplication=softname&slat=" + current_location_wei + "&slon=" + current_location_jing + "&sname=当前位置&dlat=" + location_wei + "&dlon=" + location_jing + "&dname=" + m_com_address + "&dev=0&m=0&t=1&showType=1");
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.btnBack, R.id.iv_see_co, R.id.ll_click_shoucan, R.id.rl_deliver_msg, R.id.rl_see_more, R.id.top_header_ll_right_iv_caidan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.iv_see_co:
                Intent intent = new Intent(mContext, IntroduceCoActivity.class);
                intent.putExtra("id", com_id);
                if (from != null) {
                    if (from.equals("co")) {
                        intent.putExtra("from", "co");
                    }
                }
                startActivity(intent);
                break;
            case R.id.ll_click_shoucan:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }

                if (collect_id == 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jid", id);
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
                                ivShoucan.setImageResource(R.mipmap.shoucang1);
                                try {
                                    JSONObject object = new JSONObject(gson.toJson(entity.getData()));
                                    collect_id = object.getInt("is_collect");
                                    initData();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else if (collect_id == 1) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jid", id);
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
                                ivShoucan.setImageResource(R.mipmap.shoucang4);
                                try {
                                    JSONObject object = new JSONObject(gson.toJson(entity.getData()));
                                    collect_id = object.getInt("is_collect");
                                    initData();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                break;

            case R.id.rl_deliver_msg:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                if (is_delivery == 1) {
                    showToast("您已投递该职位");
                } else if (is_delivery == 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jid", id);
                    map.put("rid", rid);
                    map.put("uid", uid);
                    OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.TOUJIANLI_JOB, new OkHttpClientManager.StringCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                            MobclickAgent.onEvent(mContext,"saveResumeDelivery");
                            showToast(entity.getMsg());
                            initData();
                        }
                    });
                }
                break;

            case R.id.rl_see_more:
                num = 2222;
                Intent intent1 = new Intent(mContext, IntroduceCoActivity.class);
                intent1.putExtra("id", com_id);
                if (from != null) {
                    intent1.putExtra("from", "co");
                }
                startActivity(intent1);
                break;
            case R.id.top_header_ll_right_iv_caidan:
                ShareDialog dialog = new ShareDialog(mContext, "1", id ,tvTitle.getText().toString(),tvCoName.getText().toString());
                dialog.show();
                break;

        }
    }

    private int num;

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().post(num);
        num = 0;
    }

    private String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            try {
                JSONObject object = new JSONObject(gson.toJson(aMapLocation));
                if (object.getString("q") != null) {
                    if (object.getString("q").equals("success")) {
                        current_m_address = "";
                        current_location_wei = object.getDouble("t");
                        current_location_jing = object.getDouble("u");

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
