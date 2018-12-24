package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzheng.tongxiang.huntingjob.HR.HrJianLiFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/5/23.
 */

public class HrDelieveyMessageActivity extends BaseActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_delievey_message_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {


        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content,HrJianLiFragment.getInstance("")).commit();
    }


}
