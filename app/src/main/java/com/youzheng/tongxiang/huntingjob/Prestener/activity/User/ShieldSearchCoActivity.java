package com.youzheng.tongxiang.huntingjob.Prestener.activity.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.BlackCoList;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.SearchBlackBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/5/8.
 */

public class ShieldSearchCoActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.tv_go_search)
    TextView tvGoSearch;
    @BindView(R.id.ll_show)
    LinearLayout llShow;
    @BindView(R.id.tv_search_name)
    TextView tvSearchName;
    @BindView(R.id.ls)
    ListView ls;
    @BindView(R.id.tv2)
    TextView tv2;

    List<SearchBlackBean> data = new ArrayList<>();
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    private CommonAdapter<SearchBlackBean> adapter;
    List<Integer> co_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shield_search_co_layout);
        ButterKnife.bind(this);

        initView();

        initEvent();
    }

    private void initEvent() {
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0 ;i<llContent.getChildCount();i++){
                    LinearLayout linearLayout = (LinearLayout) llContent.getChildAt(i);
                    RelativeLayout relativeLayout = (RelativeLayout) linearLayout.getChildAt(0);
                    CheckBox cb = relativeLayout.findViewById(R.id.cb);
                    if (cb.isChecked()){
                        co_list.add(data.get(i).getCid());
                    }
                }

                Map<String, Object> map = new HashMap<>();
                map.put("rid", rid);
                map.put("filter", 2);
                map.put("cid",co_list);
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.OPEN_SECRET, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                        showToast(entity.getMsg());
                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                            finish();
                        }
                    }
                });
            }
        });

        tvGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", accessToken);
                map.put("name", tvSearch.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.SEARCH_BLAVK, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        BlackCoList list = gson.fromJson(gson.toJson(entity.getData()), BlackCoList.class);
                        if (list.getResult().size() > 0) {
                            data.addAll(list.getResult());
                            for (int i = 0; i < list.getResult().size(); i++) {
                                View view1 = LayoutInflater.from(mContext).inflate(R.layout.sgield_ls_cb, null);
                                TextView textView = view1.findViewById(R.id.tv_co);
                                textView.setText(list.getResult().get(i).getName());
                                llContent.addView(view1);
                            }

                            rl2.setVisibility(View.VISIBLE);
                            llShow.setVisibility(View.GONE);
                            tvSearchName.setText("与“" + tvSearch.getText().toString() + "”有关的公司，共有" + list.getResult().size() + "个");

                        } else {
                            llShow.setVisibility(View.VISIBLE);
                            rl2.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        tvSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0 ;i<llContent.getChildCount();i++){
                      LinearLayout linearLayout = (LinearLayout) llContent.getChildAt(i);
                      RelativeLayout relativeLayout = (RelativeLayout) linearLayout.getChildAt(0);
                      CheckBox cb = relativeLayout.findViewById(R.id.cb);
                      cb.setChecked(true);
                }
            }
        });
    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
