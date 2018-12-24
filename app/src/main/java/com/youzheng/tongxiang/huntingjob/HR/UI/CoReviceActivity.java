package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.youzheng.tongxiang.huntingjob.HR.HrJianLiFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;

public class CoReviceActivity extends BaseActivity{

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.co_revice_layout);

        fragmentManager = getSupportFragmentManager();

        HrJianLiFragment cartFragment = HrJianLiFragment.getInstance("CoReviceActivity");

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,cartFragment);
        fragmentTransaction.commit();
    }
}
