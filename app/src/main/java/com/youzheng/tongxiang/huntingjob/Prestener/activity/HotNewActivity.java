package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzheng.tongxiang.huntingjob.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/21.
 */

public class HotNewActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.iv_four)
    ImageView ivFour;
    @BindView(R.id.iv_five)
    ImageView ivFive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_news_layout);
        ButterKnife.bind(this);

        textHeadTitle.setText("职场资讯");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,HotNewsListActivity.class);
                intent.putExtra("id",1);
                intent.putExtra("title","人社局政策服务关注");
                startActivity(intent);
            }
        });

        ivTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,HotNewsListActivity.class);
                intent.putExtra("id",2);
                intent.putExtra("title","公司报道");
                startActivity(intent);
            }
        });

        ivThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,HotNewsListActivity.class);
                intent.putExtra("id",3);
                intent.putExtra("title","高层访谈");
                startActivity(intent);
            }
        });

        ivFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,HotNewsListActivity.class);
                intent.putExtra("id",4);
                intent.putExtra("title","职场江湖");
                startActivity(intent);
            }
        });

        ivFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,HotNewsListActivity.class);
                intent.putExtra("id",5);
                intent.putExtra("title","调查报告");
                startActivity(intent);
            }
        });
    }
}
