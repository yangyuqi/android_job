package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzheng.tongxiang.huntingjob.Model.Event.EventModel;
import com.youzheng.tongxiang.huntingjob.Model.Event.SelectJianLiBean;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeFivePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeFourPageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeOnePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeSixPageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeThreePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeTwoPageFragment;
import com.youzheng.tongxiang.huntingjob.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/9.
 */

public class DescribeDetailsActivity extends BaseActivity {

    @BindView(R.id.rl_content)
    FrameLayout rlContent;
    private String type ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.describe_details_layout);
        ButterKnife.bind(this);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(EventModel model) {
        FragmentTransaction transaction = fm.beginTransaction();
        switch (model) {
            case GO_PINGJIA_INFO:
                transaction.replace(R.id.rl_content, new UserResumeSixPageFragment()).commit();
                break;
        }
    }

    @Subscribe
    public void onEvent(SelectJianLiBean liBean) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (liBean.getType().equals("1")) {
            transaction.replace(R.id.rl_content, UserResumeOnePageFragment.getInstance(liBean)).commit();
        }else if (liBean.getType().equals("2")){
            transaction.replace(R.id.rl_content,UserResumeTwoPageFragment.getInstance(liBean)).commit();
        }else if (liBean.getType().equals("3")){
            transaction.replace(R.id.rl_content,UserResumeFourPageFragment.getInstance(liBean)).commit();
        }else if (liBean.getType().equals("4")){
            transaction.replace(R.id.rl_content,UserResumeThreePageFragment.getInstance(liBean)).commit();
        }else if (liBean.getType().equals("5")){
            transaction.replace(R.id.rl_content,UserResumeFivePageFragment.getInstance(liBean)).commit();
        }else if (liBean.getType().equals("6")){
            transaction.replace(R.id.rl_content,UserResumeSixPageFragment.getInstance(liBean)).commit();
        }
    }



}
