package com.youzheng.tongxiang.huntingjob.Prestener.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeFivePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeFourPageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeOnePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeSixPageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeThreePageFragment;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume.UserResumeTwoPageFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.UserInfoFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class UserResumeActivity extends BaseActivity {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.vp_layout)
    ViewPager vpLayout;
    @BindView(R.id.view_one)
    ImageView viewOne;
    @BindView(R.id.view_two)
    ImageView viewTwo;
    @BindView(R.id.view_three)
    ImageView viewThree;
    @BindView(R.id.view_four)
    ImageView viewFour;
    @BindView(R.id.view_five)
    ImageView viewFive;
    @BindView(R.id.view_six)
    ImageView viewSix;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private List<Fragment> list = new ArrayList<>();
    private UserInfoFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.user_resume_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }


    private void initView() {
        textHeadTitle.setText("我的简历");
        btnBack.setVisibility(View.VISIBLE);
        list.add(new UserResumeOnePageFragment());
        list.add(new UserResumeTwoPageFragment());
        list.add(new UserResumeThreePageFragment());
        list.add(new UserResumeFourPageFragment());
        list.add(new UserResumeFivePageFragment());
        list.add(new UserResumeSixPageFragment());
        adapter = new UserInfoFragmentPagerAdapter(fm, list);
        vpLayout.setAdapter(adapter);
    }

    private void initEvent() {
        vpLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        changeRow(viewOne, viewTwo, viewThree, viewFour, viewFive, viewSix);
                        break;
                    case 1:
                        changeRow(viewTwo, viewOne, viewThree, viewFour, viewFive, viewSix);
                        break;
                    case 2:
                        changeRow(viewThree, viewOne, viewTwo, viewFour, viewFive, viewSix);
                        break;
                    case 3:
                        changeRow(viewFour, viewOne, viewTwo, viewThree, viewSix, viewFive);
                        break;
                    case 4:
                        changeRow(viewFive, viewOne, viewTwo, viewThree, viewFour, viewSix);
                        break;
                    case 5:
                        changeRow(viewSix, viewOne, viewTwo, viewThree, viewFour, viewFive);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBtn(vpLayout.getCurrentItem());
                if (vpLayout.getCurrentItem()==0){
                    vpLayout.setCurrentItem(1);
                }else if (vpLayout.getCurrentItem()==1){
                    vpLayout.setCurrentItem(2);
                }else if (vpLayout.getCurrentItem()==2){
                    vpLayout.setCurrentItem(3);
                }else if (vpLayout.getCurrentItem()==3){
                    vpLayout.setCurrentItem(4);
                }else if (vpLayout.getCurrentItem()==4){
                    vpLayout.setCurrentItem(5);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vpLayout.getCurrentItem()==0){
                    finish();
                }else if (vpLayout.getCurrentItem()==1){
                    vpLayout.setCurrentItem(0);
                }else if (vpLayout.getCurrentItem()==2){
                    vpLayout.setCurrentItem(1);
                }else if (vpLayout.getCurrentItem()==3){
                    vpLayout.setCurrentItem(2);
                }else if (vpLayout.getCurrentItem()==4){
                    vpLayout.setCurrentItem(3);
                }else if (vpLayout.getCurrentItem()==5){
                    vpLayout.setCurrentItem(4);
                }
                changeBtn(vpLayout.getCurrentItem());
            }
        });
    }

    private void changeRow(ImageView viewOne1, ImageView viewTwo2, ImageView viewThree3, ImageView viewFour4, ImageView viewFive5, ImageView viewSix6) {
        viewOne1.setImageResource(R.mipmap.no01);
        viewTwo2.setImageResource(R.mipmap.no02);
        viewThree3.setImageResource(R.mipmap.no02);
        viewFour4.setImageResource(R.mipmap.no02);
        viewFive5.setImageResource(R.mipmap.no02);
        viewSix6.setImageResource(R.mipmap.no02);
    }

    public void changeBtn(int position){
        if (position==5){
            btnLogin.setText("完成");
        }else {
            btnLogin.setText("下一步");
        }
    }
}
