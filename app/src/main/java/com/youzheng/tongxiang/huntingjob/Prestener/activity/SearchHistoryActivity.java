package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.youzheng.tongxiang.huntingjob.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/10.
 */

public class SearchHistoryActivity extends BaseActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.tv_go_search)
    TextView tvGoSearch;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.labelView)
    LabelsView labelView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_history_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,SearchJobActivity.class));
            }
        });
    }

}
