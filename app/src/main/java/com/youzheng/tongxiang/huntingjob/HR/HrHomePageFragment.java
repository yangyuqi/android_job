package com.youzheng.tongxiang.huntingjob.HR;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrDelieveyMessageActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrJianliDetailsActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrSchoolAdvertiseActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.HrSearchResultActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.MoreInterViewActivity;
import com.youzheng.tongxiang.huntingjob.HR.UI.NewHrCollectActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;
import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBeanData;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.BannerList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.GovernmentActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.HotNewsListActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.SchoolSpeakActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.BannerNormalAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.MyIconHintView;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.NoScrollListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/5/10.
 */

public class HrHomePageFragment extends BaseFragment {


    @BindView(R.id.roll_view)
    RollPagerView rollView;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_shoucang)
    TextView tvShoucang;
    @BindView(R.id.tv_deliever)
    TextView tvDeliever;
    @BindView(R.id.iv_go2)
    TextView ivGo2;
    @BindView(R.id.tv_hot_news)
    TextView tvHotNews;
    @BindView(R.id.tv_consule)
    TextView tvConsule;
    @BindView(R.id.tv_delay_name)
    TextView tvDelayName;
    @BindView(R.id.tv_more_delay)
    TextView tvMoreDelay;
    @BindView(R.id.ls_replay)
    NoScrollListView lsReplay;
    @BindView(R.id.tv_hot_name)
    TextView tvHotName;
    @BindView(R.id.tv_more4)
    TextView tvMore4;
    @BindView(R.id.ls)
    NoScrollListView ls;
    @BindView(R.id.tv_new_name)
    TextView tvNewName;
    @BindView(R.id.tv_more5)
    TextView tvMore5;
    @BindView(R.id.ls_new)
    NoScrollListView lsNew;
    Unbinder unbinder;

    List<UserJlBean> data = new ArrayList<>();
    CommonAdapter<UserJlBean> adapter, adapter1, adapter2;
    @BindView(R.id.iv_consulte)
    ImageView ivConsulte;
    @BindView(R.id.iv_xuanjiang)
    ImageView ivXuanjiang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hr_homepage_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        adapter = new CommonAdapter<UserJlBean>(mContext, data, R.layout.hr_home_ls_item) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {
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
                helper.setText(R.id.tv_see_time, item.getCreate_time() + "更新");
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, HrJianliDetailsActivity.class);
                        intent.putExtra("jid", item.getId());
                        startActivity(intent);
                    }
                });

//                if(it)
                if (item.getVideoUpdated()==1){
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                }else {
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
                }
            }
        };

        adapter1 = new CommonAdapter<UserJlBean>(mContext, data, R.layout.hr_home_ls_item) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {
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
                helper.setText(R.id.tv_see_time, item.getCreate_time() + "更新");
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, HrJianliDetailsActivity.class);
                        intent.putExtra("jid", item.getId());
                        startActivity(intent);
                    }
                });
                if (item.getVideoUpdated()==1){
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                }else {
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
                }
            }
        };
        adapter2 = new CommonAdapter<UserJlBean>(mContext, data, R.layout.hr_home_ls_item) {
            @Override
            public void convert(ViewHolder helper, final UserJlBean item) {
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
                helper.setText(R.id.tv_see_time, item.getCreate_time() + "更新");
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, HrJianliDetailsActivity.class);
                        intent.putExtra("jid", item.getId());
                        startActivity(intent);
                    }
                });
                if (item.getVideoUpdated()==1){
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.VISIBLE);
                }else {
                    helper.getView(R.id.iv_has_vodeo).setVisibility(View.GONE);
                }
            }
        };

        lsReplay.setAdapter(adapter);
        lsNew.setAdapter(adapter1);
        ls.setAdapter(adapter2);
        lsReplay.setFocusable(false);
        lsNew.setFocusable(false);
        ls.setFocusable(false);

        rollView.setPlayDelay(6000);
        rollView.setHintView(new MyIconHintView(mContext, R.mipmap.select_rv_bg, R.mipmap.two_pg));
        Map<String, Object> Mmap = new HashMap<>();
        Mmap.put("showType", "app");
        Mmap.put("placement", "home");
        OkHttpClientManager.postAsynJson(gson.toJson(Mmap), UrlUtis.QUERY_BANNER, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    BannerList list = gson.fromJson(gson.toJson(entity.getData()), BannerList.class);
                    if (list.getBanner().size() > 0) {
                        rollView.setAdapter(new BannerNormalAdapter(list.getBanner(), accessToken));
                    }
                }
            }
        });


    }

    @OnClick({R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(mContext, HrSearchResultActivity.class));
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("flag", "recommend");
        map.put("page", 1);
        map.put("rows", 5);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CO_HOME, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    UserJlBeanData userJlBeanData = gson.fromJson(gson.toJson(entity.getData()), UserJlBeanData.class);
                    if (userJlBeanData.getResumeList().size() > 0) {
                        data.addAll(userJlBeanData.getResumeList());
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        Map<String, Object> map1 = new HashMap<>();
        map1.put("uid", uid);
        map1.put("flag", "recent");
        map1.put("page", 1);
        map1.put("rows", 5);
        OkHttpClientManager.postAsynJson(gson.toJson(map1), UrlUtis.CO_HOME, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    UserJlBeanData userJlBeanData = gson.fromJson(gson.toJson(entity.getData()), UserJlBeanData.class);
                    if (userJlBeanData.getResumeList().size() > 0) {
                        adapter1.setData(userJlBeanData.getResumeList());
                        adapter1.notifyDataSetChanged();
                    }
                }
            }
        });

        Map<String, Object> map2 = new HashMap<>();
        map2.put("uid", uid);
        map2.put("flag", "elite");
        map2.put("page", 1);
        map2.put("rows", 5);
        OkHttpClientManager.postAsynJson(gson.toJson(map2), UrlUtis.CO_HOME, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    UserJlBeanData userJlBeanData = gson.fromJson(gson.toJson(entity.getData()), UserJlBeanData.class);
                    if (userJlBeanData.getResumeList().size() > 0) {
                        adapter2.setData(userJlBeanData.getResumeList());
                        adapter2.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_shoucang, R.id.tv_hot_news, R.id.tv_deliever, R.id.tv_more_delay, R.id.tv_more4, R.id.tv_more5, R.id.iv_go2, R.id.iv_xuanjiang,R.id.iv_consulte})
    public void OnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_shoucang:
                startActivity(new Intent(mContext, NewHrCollectActivity.class));
                break;
            case R.id.tv_hot_news:
                startActivity(new Intent(mContext, GovernmentActivity.class));
                break;
            case R.id.tv_deliever:
                startActivity(new Intent(mContext, HrDelieveyMessageActivity.class));
                break;
            case R.id.tv_more_delay:
                intent = new Intent(mContext, MoreInterViewActivity.class);
                intent.putExtra("type", "recommend");
                intent.putExtra("title", "推荐人才");
                startActivity(intent);
                break;
            case R.id.tv_more4:
                intent = new Intent(mContext, MoreInterViewActivity.class);
                intent.putExtra("type", "recent");
                intent.putExtra("title", "最新人才");
                startActivity(intent);
                break;
            case R.id.tv_more5:
                intent = new Intent(mContext, MoreInterViewActivity.class);
                intent.putExtra("type", "elite");
                intent.putExtra("title", "精英人才");
                startActivity(intent);
                break;
            case R.id.iv_go2:
                startActivity(new Intent(mContext, HrSchoolAdvertiseActivity.class));
                break;
            case R.id.iv_consulte:
                startActivity(new Intent(mContext, SchoolSpeakActivity.class));
                break;

            case R.id.iv_xuanjiang:
                Intent intente = new Intent(mContext,HotNewsListActivity.class);
                intente.putExtra("id",4);
                intente.putExtra("title","职场干货");
                startActivity(intente);
                break;
        }
    }
}
