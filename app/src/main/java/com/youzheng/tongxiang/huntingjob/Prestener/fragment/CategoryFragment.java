package com.youzheng.tongxiang.huntingjob.Prestener.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.OccupationList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.OccupationListBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.SearchJobActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    Unbinder unbinder;
    @BindView(R.id.rg_one)
    RadioGroup rgOne;
    @BindView(R.id.rg_two)
    RadioGroup rgTwo;
    @BindView(R.id.dl_drawer)
    DrawerLayout llDrawer;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.layout_header)
    RelativeLayout layoutHeader;
    @BindView(R.id.ls_three)
    ListView lsThree;

    private CommonAdapter<OccupationListBean> adapter ;
    private List<OccupationListBean> data = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance(String da) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", da);
        //fragment保存参数，传入一个Bundle对象
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        textHeadTitle.setText("分类");
        if (getArguments() != null) {
            layoutHeader.setVisibility(View.GONE);
        }
        PublicUtils.setDrawerLeftEdgeSize(((MainActivity)mContext),llDrawer,0.7f);
        llDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtis.GET_ALL_CATEGORY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)){
                    final OccupationList list = gson.fromJson(gson.toJson(entity.getData()),OccupationList.class);
                    for (int i = 0 ;i<list.getOccupationList().size();i++) {
                        RadioButton tempButton = new RadioButton(mContext);
                        tempButton.setBackgroundResource(R.drawable.category_bg_item);   // 设置RadioButton的背景图片
                        tempButton.setButtonDrawable(R.drawable.drawable_category_item);           // 设置按钮的样式
                        tempButton.setPadding(80, 0, 0, 0);                 // 设置文字距离按钮四周的距离
                        tempButton.setText(list.getOccupationList().get(i).getOccupationName());
                        tempButton.setTextColor(mContext.getResources().getColorStateList(R.color.category_text_color));
                        rgOne.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        final int finalI = i;
                        if (finalI == 0) {
                            tempButton.setChecked(true);
                            rgTwo.removeAllViews();
                            for (int j = 0; j < list.getOccupationList().get(finalI).getChilds().size(); j++) {
                                RadioButton tempButton1 = new RadioButton(mContext);
                                tempButton1.setBackgroundResource(R.drawable.category_bg_item2);
                                tempButton1.setButtonDrawable(R.drawable.drawable_category_item2);
                                tempButton1.setPadding(80, 0, 0, 0);
                                tempButton1.setText(list.getOccupationList().get(finalI).getChilds().get(j).getOccupationName());
                                tempButton1.setTextColor(mContext.getResources().getColorStateList(R.color.category_text_color));
                                rgTwo.addView(tempButton1, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                final int m = j;
                                tempButton1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(mContext, SearchJobActivity.class);
                                        intent.putExtra("keyWord", list.getOccupationList().get(finalI).getChilds().get(m).getOccupationName());
                                        intent.putExtra("occupation",list.getOccupationList().get(finalI).getChilds().get(m).getId());
                                        startActivity(intent);

                                    }
                                });
                            }
                        }
                            tempButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    rgTwo.removeAllViews();
                                    for (int j = 0; j < list.getOccupationList().get(finalI).getChilds().size(); j++) {
                                        RadioButton tempButton = new RadioButton(mContext);
                                        tempButton.setBackgroundResource(R.drawable.category_bg_item2);
                                        tempButton.setButtonDrawable(R.drawable.drawable_category_item2);
                                        tempButton.setPadding(80, 0, 0, 0);
                                        tempButton.setText(list.getOccupationList().get(finalI).getChilds().get(j).getOccupationName());
                                        tempButton.setTextColor(mContext.getResources().getColorStateList(R.color.category_text_color));
                                        rgTwo.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        final int m = j;
                                        tempButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(mContext, SearchJobActivity.class);
                                                intent.putExtra("keyWord", list.getOccupationList().get(finalI).getChilds().get(m).getOccupationName());
                                                intent.putExtra("occupation",list.getOccupationList().get(finalI).getChilds().get(m).getId());
                                                startActivity(intent);

                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        llDrawer.closeDrawers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.textHeadNext})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.textHeadNext:

                break;
        }
    }

}
