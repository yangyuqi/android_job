package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzheng.tongxiang.huntingjob.HR.UI.CoInfoOnePageFragment;
import com.youzheng.tongxiang.huntingjob.HR.UI.CoInfoTwoPageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Login.UserInfoOnePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.Login.UserInfoTwoPageFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.UserInfoFragmentPagerAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Widget.ViewPagerSlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/5/15.
 */

public class FillHRInfoActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.vp_layout)
    ViewPagerSlide vpLayout;
    @BindView(R.id.view_one)
    ImageView viewOne;
    @BindView(R.id.view_two)
    ImageView viewTwo;

    private List<Fragment> list = new ArrayList<>();
    private UserInfoFragmentPagerAdapter adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_hr_info_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textHeadTitle.setVisibility(View.GONE);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vpLayout.getCurrentItem()==1){
                    vpLayout.setCurrentItem(0);
                }else {
                    finish();
                }
            }
        });
        list.add(new CoInfoOnePageFragment());
        list.add(new CoInfoTwoPageFragment());
        adapter = new UserInfoFragmentPagerAdapter(fm,list);
        vpLayout.setAdapter(adapter);

        vpLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    viewOne.setImageResource(R.mipmap.no01);
                    viewTwo.setImageResource(R.mipmap.no02);
                }else if (position==1){
                    viewTwo.setImageResource(R.mipmap.no01);
                    viewOne.setImageResource(R.mipmap.no02);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
