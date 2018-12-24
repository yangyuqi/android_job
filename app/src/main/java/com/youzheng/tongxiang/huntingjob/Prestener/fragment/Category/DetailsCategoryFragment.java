package com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.BeanCate;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationDetailsBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducitionBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/2/10.
 */

public class DetailsCategoryFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.labelView)
    LabelsView labelView;
    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ll_show)
    LinearLayout llShow;
    private BeanCate type;
    private int position ,oldPosition =- 1 ,clickTimes = 0;
    private CommonAdapter<EducationDetailsBean> adapter;
    private ArrayList<EducationDetailsBean> data = new ArrayList<>();

    public static DetailsCategoryFragment getInstance(BeanCate type) {
        DetailsCategoryFragment fragment = new DetailsCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_category_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        if (getArguments() != null) {
            type = (BeanCate) getArguments().getSerializable("type");
            if (type != null) {
                initData(type);
            }
        }
        return view;
    }

    private void initData(final BeanCate cate) {
        oldPosition = cate.getPosition();
        clickTimes = clickTimes+1 ;
        Map<String, Object> map = new HashMap<>();
        map.put("ctype", cate.getType());
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    EducationBean comclass = gson.fromJson(gson.toJson(entity.getData()), EducationBean.class);
                    data.addAll(comclass.getComclass());

                    if (cate.getType().equals("wage")){
                        if (cate.getPosition()==-3){
                            adapter.setData(comclass.getComclass());
                            adapter.notifyDataSetChanged();
                        }else {
                            position = cate.getPosition();
                            EducationDetailsBean bean = new EducationDetailsBean(data.get(cate.getPosition()).getCtype(),data.get(cate.getPosition()).getName(),data.get(cate.getPosition()).getId(),1,cate.getPosition());
                            clickTimes=clickTimes+1;
                            data.set(cate.getPosition(),bean);
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }else if (cate.getType().equals("jobs_nature")){
                        if (cate.getPosition()==-2){
                            adapter.setData(comclass.getComclass());
                            adapter.notifyDataSetChanged();
                        }else {
                            position = cate.getPosition();
                            EducationDetailsBean bean = new EducationDetailsBean(data.get(cate.getPosition()).getCtype(),data.get(cate.getPosition()).getName(),data.get(cate.getPosition()).getId(),1,cate.getPosition());
                            data.set(cate.getPosition(),bean);
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }else if (cate.getType().equals("experience")){
                        if (cate.getPosition()==-1){
                            adapter.setData(comclass.getComclass());
                            adapter.notifyDataSetChanged();
                        }else {
                            position = cate.getPosition();
                            EducationDetailsBean bean = new EducationDetailsBean(data.get(cate.getPosition()).getCtype(),data.get(cate.getPosition()).getName(),data.get(cate.getPosition()).getId(),1,cate.getPosition());
                            data.set(cate.getPosition(),bean);
                            clickTimes=clickTimes+1;
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }else if (cate.getType().equals("education")){
                        if (cate.getPosition()==-4){
                            adapter.setData(comclass.getComclass());
                            adapter.notifyDataSetChanged();
                        }else {
                            position = cate.getPosition();
                            EducationDetailsBean bean = new EducationDetailsBean(data.get(cate.getPosition()).getCtype(),data.get(cate.getPosition()).getName(),data.get(cate.getPosition()).getId(),1,cate.getPosition());
                            data.set(cate.getPosition(),bean);
                            adapter.setData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        labelView.setVisibility(View.GONE);
        adapter = new CommonAdapter<EducationDetailsBean>(mContext, data, R.layout.cate_ls_item) {
            @Override
            public void convert(ViewHolder helper, EducationDetailsBean item) {
                TextView textView = helper.getView(R.id.tv_name);
                helper.setText(R.id.tv_name, item.getName());
                if (item.getSelected() == 1) {
                    textView.setBackgroundResource(R.drawable.unnormal_shai_xuan_bg);
                    textView.setTextColor(mContext.getResources().getColor(R.color.text_white));
                } else {
                    textView.setBackgroundResource(R.drawable.normal_shai_xuan_bg);
                    textView.setTextColor(mContext.getResources().getColor(R.color.text_normal_gray));
                }

            }
        };

        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (type.getType().equals("wage")) {
                    position = i;
                    clickTimes=clickTimes+1;
                    EducationDetailsBean bean = null;
                    for (int j = 0; j < data.size(); j++) {
                        if (j == i) {
                            if (oldPosition!=i) {
                                bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 1, position);
                                data.set(i, bean);
                            }else {
                                if (clickTimes%2!=0) {
                                    bean = new EducationDetailsBean(data.get(j).getCtype(), data.get(j).getName(), data.get(j).getId(), 0, position);
                                    data.set(j, bean);
                                }else {
                                    bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 1, position);
                                    data.set(i, bean);
                                }
                            }
                        } else {
                            bean = new EducationDetailsBean(data.get(j).getCtype(), data.get(j).getName(), data.get(j).getId(), 0,position);
                            data.set(j, bean);
                        }
                    }
                    oldPosition = position;
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }else if (type.getType().equals("jobs_nature")){
                    position = i;
                    EducationDetailsBean bean = null;
                    for (int j = 0; j < data.size(); j++) {
                        if (j == i) {
                            bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 1,position);
                            data.set(i, bean);
                        } else {
                            bean = new EducationDetailsBean(data.get(j).getCtype(), data.get(j).getName(), data.get(j).getId(), 0,position);
                            data.set(j, bean);
                        }
                    }
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }else if (type.getType().equals("experience")){
                    position = i;
                    clickTimes=clickTimes+1;
                    EducationDetailsBean bean = null;
                    for (int j = 0; j < data.size(); j++) {
                        if (j == i) {
                            if (oldPosition!=i) {
                                bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 1, position);
                                data.set(i, bean);
                            }else {
                                if (clickTimes%2!=0) {
                                    bean = new EducationDetailsBean(data.get(j).getCtype(), data.get(j).getName(), data.get(j).getId(), 0, position);
                                    data.set(j, bean);
                                }else {
                                    bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 1, position);
                                    data.set(i, bean);
                                }
                            }
                        } else {
                            bean = new EducationDetailsBean(data.get(j).getCtype(), data.get(j).getName(), data.get(j).getId(), 0,position);
                            data.set(j, bean);
                        }
                    }
                    oldPosition = position;
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }else if (type.getType().equals("education")){
                    position = i;
                    EducationDetailsBean bean = null;
                    for (int j = 0; j < data.size(); j++) {
                        if (j == i) {
                            bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 1,position);
                            data.set(i, bean);
                        } else {
                            bean = new EducationDetailsBean(data.get(j).getCtype(), data.get(j).getName(), data.get(j).getId(), 0,position);
                            data.set(j, bean);
                        }
                    }
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.size() > 0) {
                    if (type.getType().equals("wage")) {
                        EventBus.getDefault().post(data.get(position));
                    }else if (type.getType().equals("jobs_nature")){
                        EventBus.getDefault().post(data.get(position));
                    }else if (type.getType().equals("experience")){
                        EventBus.getDefault().post(data.get(position));
                    }else if (type.getType().equals("education")){
                        EventBus.getDefault().post(data.get(position));
                    }
                    llShow.setVisibility(View.GONE);
                }
            }
        });
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.size()>0){
                    if (type.getType().equals("wage")){
                        EducationDetailsBean bean = data.get(position);
                        data.set(position,new EducationDetailsBean(bean.getCtype(),bean.getName(),bean.getId(),0,-3));
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }else if (type.getType().equals("jobs_nature")){
                        EducationDetailsBean bean = data.get(position);
                        data.set(position,new EducationDetailsBean(bean.getCtype(),bean.getName(),bean.getId(),0,-2));
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }else if (type.getType().equals("experience")){
                        EducationDetailsBean bean = data.get(position);
                        data.set(position,new EducationDetailsBean(bean.getCtype(),bean.getName(),bean.getId(),0,-1));
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }else if (type.getType().equals("education")){
                        EducationDetailsBean bean = data.get(position);
                        data.set(position,new EducationDetailsBean(bean.getCtype(),bean.getName(),bean.getId(),0,-4));
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
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
}
