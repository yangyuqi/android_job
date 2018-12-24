package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobCollectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobData;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.TxypResumeListBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
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

public class NewHrCollectActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.ls)
    ListView ls;

    List<ListJobCollectBean> data = new ArrayList<>();
    @BindView(R.id.tv_add_new_collect)
    TextView tvAddNewCollect;
    @BindView(R.id.tv_add_new_delete)
    TextView tvAddNewDelete;
    private CommonAdapter<ListJobCollectBean> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_hr_collect_layout);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        data.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.GET_CO_COLLECT_LIST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    ListJobData jobData = gson.fromJson(gson.toJson(entity.getData()), ListJobData.class);
                    if (jobData.getData().size() > 0) {
                        data.addAll(jobData.getData());
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
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
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("管理");
        textHeadTitle.setText("收藏夹");


        adapter = new CommonAdapter<ListJobCollectBean>(mContext, data, R.layout.new_hr_ls_collect_item) {
            @Override
            public void convert(final ViewHolder helper, final ListJobCollectBean item1) {

                if (item1.getType() == 0) {
                    helper.getView(R.id.iv_select_icon).setVisibility(View.GONE);
                } else if (item1.getType() == 1) {
                    helper.getView(R.id.iv_select_icon).setVisibility(View.VISIBLE);
                    if (item1.isSelect()) {
                        ((ImageView) helper.getView(R.id.iv_select_icon)).setImageResource(R.mipmap.group_20_1);
                    } else {
                        ((ImageView) helper.getView(R.id.iv_select_icon)).setImageResource(R.mipmap.group_21_1);
                    }
                }

                helper.getView(R.id.iv_select_icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item1.isSelect()) {
                            ((ImageView) helper.getView(R.id.iv_select_icon)).setImageResource(R.mipmap.group_21_1);
                            item1.setSelect(false);
                        } else {
                            ((ImageView) helper.getView(R.id.iv_select_icon)).setImageResource(R.mipmap.group_20_1);
                            item1.setSelect(true);
                        }
                    }
                });


                helper.setText(R.id.tv_collect_name, item1.getName());
                helper.setText(R.id.tv_collect_num, "" + item1.getNote());
                GridView gv = helper.getView(R.id.gv);
                CommonAdapter<TxypResumeListBean> commonAdapter = new CommonAdapter<TxypResumeListBean>(mContext, item1.getTxypResumeList(), R.layout.collect_gv_sex_item) {
                    @Override
                    public void convert(ViewHolder helper, TxypResumeListBean item) {
                        helper.setText(R.id.tv_name,item.getTruename());
                        Glide.with(mContext).load(item.getPhoto()).error(R.mipmap.gggggroup).into((CircleImageView) helper.getView(R.id.civ));
                        if (item1.getType()==0) {
                            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(mContext, NewHrCollectDetailActivity.class);
                                    intent.putExtra("name", item1.getName());
                                    intent.putExtra("note", item1.getNote());
                                    intent.putExtra("id", item1.getId());
                                    startActivity(intent);
                                }
                            });
                        }

                        if (item.getGender()!=1){
                            ((ImageView)helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_24_nv);
                        }else {
                            ((ImageView)helper.getView(R.id.iv_sex)).setImageResource(R.mipmap.group_25_nan);
                        }
                    }
                };
                gv.setAdapter(commonAdapter);
            }
        };
        ls.setAdapter(adapter);

        tvAddNewCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddAndEditCollectActivity.class);
                intent.putExtra("type", "");
                startActivity(intent);
            }
        });

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textHeadNext.getText().toString().equals("管理")) {
                    textHeadNext.setText("完成");
                    tvAddNewCollect.setVisibility(View.GONE);
                    tvAddNewDelete.setVisibility(View.VISIBLE);
                    if (data.size() > 0) {
                        for (ListJobCollectBean bean : data) {
                            bean.setType(1);
                        }
                    }
                } else {
                    textHeadNext.setText("管理");
                    tvAddNewCollect.setVisibility(View.VISIBLE);
                    tvAddNewDelete.setVisibility(View.GONE);
                    if (data.size() > 0) {
                        for (ListJobCollectBean bean : data) {
                            bean.setType(0);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        tvAddNewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.size()>0){
                    List<Integer> d = new ArrayList<>();
                    for (ListJobCollectBean bean : data){
                        if (bean.isSelect()){
                            d.add(bean.getId());
                        }
                    }
                    if (d.size()>0){
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",d);
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CO_DELETE_COLLECT, new OkHttpClientManager.StringCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                    initData();
                                }else {
                                    showToast(entity.getMsg());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

}
