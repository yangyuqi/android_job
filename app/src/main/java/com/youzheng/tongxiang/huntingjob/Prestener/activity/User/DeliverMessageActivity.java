package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.DeliverBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.DeliverBeanList;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.IntroduceJobActivity;
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

/**
 * Created by qiuweiyu on 2018/2/11.
 */

public class DeliverMessageActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.sv)
    SpringView sv;

    private CommonAdapter<DeliverBean> adapter;
    private List<DeliverBean> data = new ArrayList<>();
    private int status = 1;
    private int page = 1;
    private int rows = 20;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliver_message_layout);
        ButterKnife.bind(this);
        initView();
        iniDate(status);
    }

    private void iniDate(int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("status", status);
        map.put("page", page);
        map.put("rows", rows);
        map.put("accessToken", accessToken);
        map.put("type", 0);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.DELIEVERY_MSG_LIST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onResponse(String response) {
                sv.onFinishFreshAndLoad();
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    DeliverBeanList list = gson.fromJson(gson.toJson(entity.getData()), DeliverBeanList.class);
                    if (list != null) {
                        if (page==1) {
                                data.clear();
                                if (list.getDeliveryList().size() > 0) {
                                    data.addAll(list.getDeliveryList());
                                    adapter.setData(list.getDeliveryList());
                                    adapter.notifyDataSetChanged();
                                }else {
                                    adapter.setData(new ArrayList<DeliverBean>());
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                if (list.getDeliveryList().size() > 0) {
                                    data.addAll(list.getDeliveryList());
                                    adapter.setData(data);
                                    adapter.notifyDataSetChanged();


                            }
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadTitle.setText("投递箱");

        adapter = new CommonAdapter<DeliverBean>(mContext, data, R.layout.deliever_ls_item) {
            @Override
            public void convert(ViewHolder helper, final DeliverBean item) {
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_level_l, item.getEducation() + "/" + item.getCitysName());
                helper.setText(R.id.tv_name_co, item.getName());
                helper.setText(R.id.tv_below, item.getTradeName());
                helper.setText(R.id.tv_time,item.getCreate_time());
                CircleImageView imageView = helper.getView(R.id.iv_logo);
                Glide.with(mContext).load(item.getCom_logo()).error(R.mipmap.youyuzhanweicopy2).into(imageView);
                if (item.getWage_face() == 0) {
                    helper.setText(R.id.tv_money, "" + item.getWage_min() / 1000 + "K" + "-" + item.getWage_max() / 1000 + "K");
                } else {
                    helper.setText(R.id.tv_money, "面议");
                }
                if (item.getStatus() == 1) {
                    ((ImageView) helper.getView(R.id.iv_icon)).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_bg).setBackgroundResource(R.mipmap.groweup);
                    (helper.getView(R.id.rl_hong)).setVisibility(View.GONE);
                    ((ImageView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.toudichenggong);
                } else if (item.getStatus() == 2) {
                    ((ImageView) helper.getView(R.id.iv_icon)).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_bg).setBackgroundResource(R.mipmap.groweup);
                    (helper.getView(R.id.rl_hong)).setVisibility(View.GONE);
                    ((ImageView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.chakan);
                } else if (item.getStatus() == 3) {
//                    ((ImageView) helper.getView(R.id.iv_icon)).setVisibility(View.GONE);
//                    helper.getView(R.id.rl_bg).setBackgroundResource(R.drawable.invite_bg_);
//                    (helper.getView(R.id.rl_hong)).setVisibility(View.VISIBLE);
                    ((ImageView) helper.getView(R.id.iv_icon)).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_bg).setBackgroundResource(R.mipmap.groweup);
                    (helper.getView(R.id.rl_hong)).setVisibility(View.GONE);
                    ((ImageView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.toudichenggo__ng);

                } else if (item.getStatus() == 4) {
                    ((ImageView) helper.getView(R.id.iv_icon)).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_bg).setBackgroundResource(R.mipmap.groweup);
                    (helper.getView(R.id.rl_hong)).setVisibility(View.GONE);
                    ((ImageView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.buheshi);
                }

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, IntroduceJobActivity.class);
                        intent.putExtra("id", item.getId());
                        startActivity(intent);
                    }
                });
            }

        };

        ls.setAdapter(adapter);

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                page=1;
                if (tab.getPosition() == 4) {
                    iniDate(0);
                    status = 0 ;
                } else {
                    iniDate(tab.getPosition() + 1);
                    status = tab.getPosition()+1;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                iniDate(status);
            }

            @Override
            public void onLoadmore() {
                 page++;
                iniDate(status);
            }
        });
    }
}
