package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.jude.rollviewpager.RollPagerView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.BannerList;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.BannerListBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.BannerNormalAdapter;
import com.youzheng.tongxiang.huntingjob.UI.MyIconHintView;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.SharedPreferencesUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/22.
 */

public class AdActivity extends BaseActivity {

    @BindView(R.id.roll_view)
    RollPagerView rollView;
    private List<BannerListBean> banner_date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_layout);
        ButterKnife.bind(this);
        rollView.setPlayDelay(60000);
        rollView.setHintView(new MyIconHintView(mContext, R.mipmap.select_rv_bg, R.mipmap.two_pg));
        final Map<String,Object> map = new HashMap<>();
        map.put("showType","app");
        map.put("placement","start");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.QUERY_BANNER, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                    try {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            BannerList list = gson.fromJson(gson.toJson(entity.getData()), BannerList.class);
                            if (list.getBanner().size() >= 0) {
                                banner_date = list.getBanner();
                                rollView.setAdapter(new BannerNormalAdapter(list.getBanner(),accessToken));

                                if (banner_date.size() >= 0) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.fiet, 1);
                                            if (!accessToken.equals("")) {
                                                startActivity(new Intent(mContext, MainActivity.class));
                                                finish();
                                            } else {
                                                startActivity(new Intent(mContext, MainActivity.class));
                                                finish();
                                            }
                                        }
                                    }, 1500);
                                }


                                rollView.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        if (position + 1 == banner_date.size()) {
                                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.fiet, 1);
                                            if (!accessToken.equals("")) {
                                                startActivity(new Intent(mContext, MainActivity.class));
                                                finish();
                                            } else {
                                                startActivity(new Intent(mContext, LoginActivity.class));
                                                finish();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });
                            }
                        }
                    }catch (Exception e){
                        startActivity(new Intent(mContext,MainActivity.class));//游客模式
                        finish();
                    }
                }

        });
    }
}
