package com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.BeanCate;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CateBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.NewEducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationDetailsBean;
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
 * Created by qiuweiyu on 2018/5/3.
 */

public class CategoryNeedFragment extends BaseFragment {

    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.gv2)
    GridView gv2;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    Unbinder unbinder;
    @BindView(R.id.ll_show)
    LinearLayout llShow;
    private View view;
    private int position , position2 ,oldPosition1 = -1 ,oldPosition2=-1 ,cliclTime1= 0, clickTimes=0;
    private CommonAdapter<EducationDetailsBean> adapter;
    private ArrayList<EducationDetailsBean> data = new ArrayList<>();

    private CommonAdapter<EducationDetailsBean> adapter2;
    private ArrayList<EducationDetailsBean> data2 = new ArrayList<>();
    CateBean type ;
    public static CategoryNeedFragment getInstance(CateBean type) {
        CategoryNeedFragment fragment = new CategoryNeedFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_need_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        if (getArguments() != null) {
            type = (CateBean) getArguments().getSerializable("type");
            if (type != null) {
                oldPosition1 =position=type.getPosition();
                oldPosition2 =position2=type.getPosition2();
                initData(type);
            }
        }
        return view;
    }

    private void initView() {
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

        adapter2 = new CommonAdapter<EducationDetailsBean>(mContext, data, R.layout.cate_ls_item) {
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

        gv2.setAdapter(adapter2);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
    }

    private void initEvent() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EducationDetailsBean bean = null;
                clickTimes=clickTimes+1;
                for (int j = 0; j < data.size(); j++) {
                    if (j == i) {
                        if (oldPosition1!=i) {
                            bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 1, i);
                            data.set(i, bean);
                        }else {
                            if (clickTimes%2!=0) {
                                bean = new EducationDetailsBean(data.get(j).getCtype(), data.get(j).getName(), data.get(j).getId(), 0, i);
                                data.set(j, bean);
                            }else {
                                bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 1, i);
                                data.set(i, bean);
                            }
                        }
                    } else {
                        bean = new EducationDetailsBean(data.get(j).getCtype(), data.get(j).getName(), data.get(j).getId(), 0, i);
                        data.set(j, bean);
                    }
                }
                oldPosition1 = i;
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }
        });

        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EducationDetailsBean bean = null;
                cliclTime1=cliclTime1+1;
                for (int j = 0; j < data2.size(); j++) {
                    if (j == i) {
                        if (oldPosition2!=i) {
                            bean = new EducationDetailsBean(data2.get(i).getCtype(), data2.get(i).getName(), data2.get(i).getId(), 1, j);
                            data2.set(i, bean);
                        }else {
                            if (cliclTime1%2!=0) {
                                bean = new EducationDetailsBean(data2.get(j).getCtype(), data2.get(j).getName(), data2.get(j).getId(), 0, j);
                                data2.set(j, bean);
                            }else {
                                bean = new EducationDetailsBean(data2.get(i).getCtype(), data2.get(i).getName(), data2.get(i).getId(), 1, j);
                                data2.set(i, bean);
                            }
                        }
                    } else {
                        bean = new EducationDetailsBean(data2.get(j).getCtype(), data2.get(j).getName(), data2.get(j).getId(), 0, j);
                        data2.set(j, bean);
                    }
                }
                oldPosition2=i;
                adapter2.setData(data2);
                adapter2.notifyDataSetChanged();
            }
        });


        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.size() > 0 || data2.size() > 0) {
                    NewEducationBean bean = new NewEducationBean();
                    int m = 0 ,n= 0 ;
                    for (int i = 0;i<data.size();i++){
                        if (data.get(i).getSelected()==1){
                            m=-1;
                            bean.setCtype(data.get(i).getCtype());
                            bean.setId(data.get(i).getId());
                            bean.setName(data.get(i).getName());
                            bean.setSelected(data.get(i).getSelected());
                            bean.setPosition(i);
                        }
                    }

                    for (int j = 0 ; j<data2.size();j++){
                        if (data2.get(j).getSelected()==1){
                            n=-1;
                            bean.setCtype2(data2.get(j).getCtype());
                            bean.setId2(data2.get(j).getId());
                            bean.setName2(data2.get(j).getName());
                            bean.setSelected2(data2.get(j).getSelected());
                            bean.setPosition2(j);
                        }
                    }
                    if (m==-1||n==-1){
                        EventBus.getDefault().post(bean);
                    }else {
                        EventBus.getDefault().post("refresh");
                    }
                    llShow.setVisibility(View.GONE);
                }
            }
        });
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doReset();
            }
        });

    }

    public void doReset(){
        position=-1;position2=-1;
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                EducationDetailsBean bean = new EducationDetailsBean(data.get(i).getCtype(), data.get(i).getName(), data.get(i).getId(), 0, data.get(i).getPosition());
                data.set(i, bean);
            }
        }
        if (data2.size() > 0) {
            for (int i = 0; i < data2.size(); i++) {
                EducationDetailsBean bean = new EducationDetailsBean(data2.get(i).getCtype(), data2.get(i).getName(), data2.get(i).getId(), 0, data2.get(i).getPosition());
                data2.set(i, bean);
            }
        }
        adapter.setData(data);
        adapter.notifyDataSetChanged();
        adapter2.setData(data2);
        adapter2.notifyDataSetChanged();
    }

    private void initData(CateBean bean) {
        cliclTime1 = cliclTime1+1;
        clickTimes=clickTimes+1;
        Map<String, Object> map = new HashMap<>();
        map.put("ctype", "jobs_nature");
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
                    if (position==-1) {
                        adapter.setData(comclass.getComclass());
                        adapter.notifyDataSetChanged();
                    }else {
                        EducationDetailsBean educationDetailsBean = new EducationDetailsBean(data.get(position).getCtype(),data.get(position).getName(),data.get(position).getId(),1,position);
                        data.set(position,educationDetailsBean);
                        clickTimes=clickTimes+1;
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        Map<String, Object> map1 = new HashMap<>();
        map1.put("ctype", "education");
        OkHttpClientManager.postAsynJson(gson.toJson(map1), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    EducationBean comclass = gson.fromJson(gson.toJson(entity.getData()), EducationBean.class);
                    data2.addAll(comclass.getComclass());
                    if (position2==-1){
                        adapter2.setData(comclass.getComclass());
                        adapter2.notifyDataSetChanged();
                    }else {
                        EducationDetailsBean educationDetailsBean = new EducationDetailsBean(data2.get(position2).getCtype(),data2.get(position2).getName(),data2.get(position2).getId(),1,position2);
                        data2.set(position2,educationDetailsBean);
                        cliclTime1=cliclTime1+1;
                        adapter2.setData(data2);
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
}
