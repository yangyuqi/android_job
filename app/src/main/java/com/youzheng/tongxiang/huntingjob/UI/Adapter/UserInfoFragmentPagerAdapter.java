package com.youzheng.tongxiang.huntingjob.UI.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class UserInfoFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list ;
    public UserInfoFragmentPagerAdapter(FragmentManager fm ,List<Fragment> list) {
        super(fm);
        this.list = list ;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getCount() {
        return list.size();
    }
}
