package com.youzheng.tongxiang.huntingjob.Prestener.fragment.Category;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.AreaSelectBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/5/4.
 */

public class CategoryAreaFragment extends BaseFragment {

    @BindView(R.id.rg_one)
    RadioGroup rgOne;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    Unbinder unbinder;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ll_show)
    LinearLayout llShow;

    private List<String> data = new ArrayList<>();
    private List<AreaListBean> list_data = new ArrayList<>();
    private int parentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_area_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
    }

    private void initEvent() {
        rgOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
//                llContent.removeAllViews();
//                if (list_data.size()>0) {
//                    for (int j = 0; j < list_data.get(i - 1).getChilds().size(); j++) {
//                        View view1 = LayoutInflater.from(mContext).inflate(R.layout.cate_area_ls_item, null);
//                        CheckBox checkBox = view1.findViewById(R.id.cb);
//                        checkBox.setText(list_data.get(i - 1).getChilds().get(j).getAreaName());
//                        llContent.addView(view1);
//                    }
//                }
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list_data.clear();
                initData();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                if (list_data.get(parentPosition).getChilds().size() > 1) {
                    for (int i = 0; i < llContent.getChildCount(); i++) {
                        RelativeLayout relativeLayout = (RelativeLayout) llContent.getChildAt(i);
                        CheckBox checkBox = (CheckBox) ((LinearLayout) relativeLayout.getChildAt(0)).getChildAt(0);
                        if (checkBox.isChecked()) {
                            if (list_data.get(parentPosition).getChilds().size() > 0) {
                                arrayList.add(list_data.get(parentPosition).getChilds().get(i).getId());
                            }
                        }
                    }

                }else {
                    arrayList.add(list_data.get(parentPosition).getId());
                }
                EventBus.getDefault().post(new AreaSelectBean(list_data.get(parentPosition).getAreaName(),arrayList));
                llShow.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        rgOne.removeAllViews();
        parentPosition = 0;
        final Map<String, Object> map = new HashMap<>();
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.TONGXIANG_ADDRESS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    AreaList list = gson.fromJson(gson.toJson(entity.getData()), AreaList.class);
                    if (list.getAreaList().size() > 0) {
                        list_data.addAll(list.getAreaList());
                        for (int i = 0; i < list.getAreaList().size(); i++) {
                            RadioButton tempButton = new RadioButton(mContext);
                            tempButton.setBackgroundResource(R.drawable.area_frist_bg);   // 设置RadioButton的背景图片
                            tempButton.setButtonDrawable(R.mipmap.bai);           // 设置按钮的样式
                            tempButton.setPadding(80, 0, 0, 0);                 // 设置文字距离按钮四周的距离
                            tempButton.setText(list.getAreaList().get(i).getAreaName());
                            tempButton.setTextColor(mContext.getResources().getColorStateList(R.color.cate_area_frist_color));
                            rgOne.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            if (i == 0) {
                                tempButton.setChecked(true);
                                llContent.removeAllViews();
                                for (int j = 0; j < list.getAreaList().get(0).getChilds().size(); j++) {
                                    View view1 = LayoutInflater.from(mContext).inflate(R.layout.cate_area_ls_item, null);
                                    CheckBox checkBox = view1.findViewById(R.id.cb);
                                    checkBox.setText(list.getAreaList().get(0).getChilds().get(j).getAreaName());
                                    llContent.addView(view1);
                                }
                            }
                            final int finalI = i;
                            tempButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    llContent.removeAllViews();
                                    parentPosition = finalI;
                                    if (list_data.size() > 0 && list_data.get(finalI).getChilds().size() > 1) {
                                        for (int j = 0; j < list_data.get(finalI).getChilds().size(); j++) {
                                            View view1 = LayoutInflater.from(mContext).inflate(R.layout.cate_area_ls_item, null);
                                            CheckBox checkBox = view1.findViewById(R.id.cb);
                                            checkBox.setText(list_data.get(finalI).getChilds().get(j).getAreaName());
                                            llContent.addView(view1);
                                        }
                                    } else {
                                        View view2 = LayoutInflater.from(mContext).inflate(R.layout.no_area_layout, null);
                                        llContent.addView(view2);
                                    }
                                }
                            });
                        }
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
