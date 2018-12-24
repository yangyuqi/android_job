package com.youzheng.tongxiang.huntingjob;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.HR.HrHomePageFragment;
import com.youzheng.tongxiang.huntingjob.HR.HrInterViewFragment;
import com.youzheng.tongxiang.huntingjob.HR.HrJianLiFragment;
import com.youzheng.tongxiang.huntingjob.HR.HrJobFragment;
import com.youzheng.tongxiang.huntingjob.HR.HrMesageFragment;
import com.youzheng.tongxiang.huntingjob.HR.HrUserCenterFragment;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.ComJpushData;
import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.ComJpushDataBean;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.CategoryFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.HomePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.JianLiFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.MessageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.UserCenterFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/5/10.
 */

public class CoMainActivity extends BaseActivity {

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.foot_bar_home)
    RadioButton footBarHome;
    @BindView(R.id.foot_bar_im)
    RadioButton footBarIm;
    @BindView(R.id.foot_bar_im2)
    RadioButton footBarIm2;
    @BindView(R.id.foot_bar_interest)
    RadioButton footBarInterest;
    @BindView(R.id.main_footbar_user)
    RadioButton mainFootbarUser;
    @BindView(R.id.group)
    RadioGroup group;
    private ArrayList<String> fragmentTags;
    private  String CURR_INDEX = "currIndex";
    private  int currIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.co_activity_main);
        ButterKnife.bind(this);
        initData(savedInstanceState);
        showFragment();
        initEvent();

        String d = "{\"data\":\"{\\\"data\\\":{\\\"rid\\\":\\\"149\\\"},\\\"type\\\":\\\"seejob\\\"}\"}";

    }

    private void initEvent() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.foot_bar_home : currIndex = 2 ;break;
                    case R.id.foot_bar_im : currIndex = 1 ;break;
                    case R.id.foot_bar_im2 : currIndex = 3 ;break;
                    case R.id.foot_bar_interest : currIndex = 0 ;break;
                    case R.id.main_footbar_user : currIndex = 4 ;break;
                }
                showFragment();
            }
        });
    }

    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("HomePageFragment", "CategoryFragment", "MessageFragment", "JianLiFragment","UserCenterFragment"));
        if(savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void showFragment() {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fm.findFragmentByTag(fragmentTags.get(i));
            if(f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fm.executePendingTransactions();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0 :
                return new HrInterViewFragment();
            case 1:
                return new HrJobFragment();
            case 2:
                return new HrHomePageFragment();
            case 3:
//                return new HrJianLiFragment();
                return new HrMesageFragment();
            case 4:
                return new HrUserCenterFragment();

            default:
                return null;
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fm.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment != null) {
            fm.beginTransaction().hide(fragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onPageStart("co_activity_main");
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("co_activity_main");
        MobclickAgent.onPause(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if(true){
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(),
                            R.string.tip_exit, Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    currIndex=0;
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
