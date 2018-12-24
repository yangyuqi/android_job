package com.youzheng.tongxiang.huntingjob.Prestener.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.MainActivity;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Event.SelectJianLiBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.BaseJianli;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducitionBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.ProjectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.SkillListBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.WorkExperenceBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.user.PhotoBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.DescribeDetailsActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FullScreenActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.LoginActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.WorkExperienceActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;
import com.youzheng.tongxiang.huntingjob.UI.Widget.NoScrollListView;
import com.youzheng.tongxiang.huntingjob.UI.dialog.BottomPhotoDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayerStandard;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;


import static android.app.Activity.RESULT_OK;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public class JianLiFragment extends BaseFragment {

    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    Unbinder unbinder;
    @BindView(R.id.rl_desc)
    ImageView rlDesc;
    @BindView(R.id.rl_jinyan)
    ImageView rlJinyan;
    @BindView(R.id.rl_study)
    ImageView rlStudy;
    @BindView(R.id.rl_jineng)
    ImageView rlJineng;
    @BindView(R.id.rl_pingjia)
    ImageView rlPingjia;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_experience)
    TextView tvExperience;

    @BindView(R.id.ls_experence)
    NoScrollListView lsExperence;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.ls_project)
    NoScrollListView lsProject;
    @BindView(R.id.tv_jiaoyue)
    TextView tvJiaoyue;
    @BindView(R.id.ls_jiaoyue)
    NoScrollListView lsJiaoyue;
    @BindView(R.id.tv_jineng)
    TextView tvJineng;
    @BindView(R.id.labelView)
    LabelsView labelView;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_desc_desc)
    TextView tvDescDesc;
    @BindView(R.id.iv_co_icon)
    CircleImageView ivCoIcon;

    ImageCaptureManager captureManager;

    @BindView(R.id.tv_get_attention)
    TextView tvGetAttention;
    @BindView(R.id.tv_get_education)
    TextView tvGetEducation;
    @BindView(R.id.tv_get_brith_place)
    TextView tvGetBrithPlace;
    @BindView(R.id.tv_get_hope_job)
    TextView tvGetHopeJob;
    @BindView(R.id.tv_get_hope_money)
    TextView tvGetHopeMoney;
    @BindView(R.id.tv_get_brith)
    TextView getTvGetBrith;
    @BindView(R.id.tv_get_hope_trade)
    TextView tvGetHopeTrade;
    @BindView(R.id.tv_get_hope_address)
    TextView tvGetHopeAddress;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_co_get_name)
    TextView tvCoGetName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_work_mean)
    TextView tvWorkMean;
    @BindView(R.id.rl_rl_two)
    RelativeLayout rlRlTwo;
    @BindView(R.id.rl_rl_three)
    RelativeLayout rlRlThree;
    @BindView(R.id.rl_rl_four)
    RelativeLayout rlRlFour;
    @BindView(R.id.rl_show_no_video)
    RelativeLayout rlShowNoVideo;
    @BindView(R.id.video_view)
    JZVideoPlayerStandard videoView;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_bofang)
    ImageView ivBofang;
    @BindView(R.id.tv_state_name)
    TextView tvStateName;
    @BindView(R.id.tv_get_state_attention)
    TextView tvGetStateAttention;
    @BindView(R.id.tv_get_state_education)
    TextView tvGetStateEducation;
    @BindView(R.id.tv_get_state_brith_place)
    TextView tvGetStateBrithPlace;
    @BindView(R.id.rl_show_has_video)
    LinearLayout rlShowHasVideo;
    @BindView(R.id.tv_test_a)
    TextView tvTestA;
    @BindView(R.id.tv_test_b)
    TextView tvTestB;
    @BindView(R.id.tv_test_c)
    TextView tvTestC;
    @BindView(R.id.rl_rl_five)
    RelativeLayout rlRlFive;
    @BindView(R.id.view_test_three)
    View viewTestThree;
    @BindView(R.id.tv_tv_test_two)
    TextView tvTvTestTwo;
    @BindView(R.id.rl_rl_six)
    RelativeLayout rlRlSix;
    @BindView(R.id.view_test_four)
    View viewTestFour;
    @BindView(R.id.tv_tv_test_four)
    TextView tvTvTestFour;
    @BindView(R.id.rl_rl_seven)
    RelativeLayout rlRlSeven;
    @BindView(R.id.view_test_five)
    View viewTestFive;
    @BindView(R.id.tv_tv_testfive)
    TextView tvTvTestfive;
    @BindView(R.id.rl_rl_night)
    RelativeLayout rlRlNight;
    @BindView(R.id.view_test_six)
    View viewTestSix;
    @BindView(R.id.tv_tv_test_six)
    TextView tvTvTestSix;
    @BindView(R.id.rl_rl_nine)
    RelativeLayout rlRlNine;
    @BindView(R.id.view_test_seven)
    View viewTestSeven;
    @BindView(R.id.tv_tv_test_seven)
    TextView tvTvTestSeven;
    @BindView(R.id.rl_rl_ten)
    RelativeLayout rlRlTen;
    @BindView(R.id.tv_all_time)
    TextView tvAllTime;
    @BindView(R.id.video_bitmap_image)
    ImageView videoBitmapImage;
    @BindView(R.id.iv_start_left)
    ImageView ivStartLeft;


    private CommonAdapter<WorkExperenceBean> ex_adapter;
    private List<WorkExperenceBean> ex_data = new ArrayList<>();

    private CommonAdapter<ProjectBean> pr_adapter;
    private List<ProjectBean> pr_data = new ArrayList<>();

    private CommonAdapter<EducitionBean> ed_adapter;
    private List<EducitionBean> ed_data = new ArrayList<>();

    private CommonAdapter<SkillListBean> sk_adapter;
    private List<SkillListBean> sk_data = new ArrayList<>();

    private String which;
    @BindView(R.id.rl_work)
    ImageView rlWork;

    BaseJianli jianli;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_jianli_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!accessToken.equals("")) {
//            initData();
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
        if (!accessToken.equals("")) {
            initData();
        }
    }


    private void initData() {
        if (!accessToken.equals("")) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", accessToken);
            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.MY_RESUME, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                    if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                        final BaseJianli baseJianli = gson.fromJson(gson.toJson(entity.getData()), BaseJianli.class);
                        jianli = baseJianli;
                        tvName.setText(baseJianli.getResume().getTruename());
                        if (baseJianli.getResume().getGender() == 1) {
                            ivSex.setImageResource(R.mipmap.group_25_nan);
                        } else {
                            ivSex.setImageResource(R.mipmap.group_24_nv);
                        }

                        if (baseJianli.getResume().getGender() == 1) {
                            Glide.with(mContext).load(baseJianli.getResume().getPhoto()).error(R.mipmap.morentouxiangnansheng).into(ivCoIcon);
                        } else {
                            Glide.with(mContext).load(baseJianli.getResume().getPhoto()).error(R.mipmap.morentouxiangnvsheng).into(ivCoIcon);
                        }

                        tvGetAttention.setText("" + baseJianli.getResume().getWork_year() + "年");
                        tvGetEducation.setText(baseJianli.getResume().getEducation());
                        tvGetBrithPlace.setText(baseJianli.getResume().getCitysName());
                        tvGetHopeJob.setText(baseJianli.getResume().getPosition());
                        tvGetHopeMoney.setText(baseJianli.getResume().getWageName());
                        getTvGetBrith.setText(baseJianli.getResume().getBirthdate());
                        tvGetHopeTrade.setText(baseJianli.getResume().getTradeName());
                        tvGetHopeAddress.setText(baseJianli.getResume().getHopeCityName());
                        if (baseJianli.getResume().getLastestCompany() == null) {
                            tvCoGetName.setText("暂无第一家公司");
                        } else {
                            tvCoGetName.setText(baseJianli.getResume().getLastestCompany());
                        }
                        tvPhone.setText(baseJianli.getResume().getTelephone());
                        tvEmail.setText(baseJianli.getResume().getEmail());
                        tvWorkMean.setText(baseJianli.getResume().getJobs_natureName());
                        if (baseJianli.getExperienceList().size() > 0) {
                            tvExperience.setVisibility(View.GONE);
                            lsExperence.setVisibility(View.VISIBLE);
                            ex_adapter.setData(baseJianli.getExperienceList());
                            ex_adapter.notifyDataSetChanged();
                        } else {
                            tvExperience.setVisibility(View.VISIBLE);
                            lsExperence.setVisibility(View.GONE);
                        }

                        if (baseJianli.getProjectList().size() > 0) {
                            tvProject.setVisibility(View.GONE);
                            lsProject.setVisibility(View.VISIBLE);
                            pr_adapter.setData(baseJianli.getProjectList());
                            pr_adapter.notifyDataSetChanged();
                        } else {
                            lsProject.setVisibility(View.GONE);
                            tvProject.setVisibility(View.VISIBLE);
                        }

                        if (baseJianli.getEducationList().size() > 0) {
                            tvJiaoyue.setVisibility(View.GONE);
                            lsJiaoyue.setVisibility(View.VISIBLE);
                            ed_adapter.setData(baseJianli.getEducationList());
                            ed_adapter.notifyDataSetChanged();
                        } else {
                            lsJiaoyue.setVisibility(View.GONE);
                            tvJiaoyue.setVisibility(View.VISIBLE);
                        }

                        if (baseJianli.getSkillList().size() > 0) {
                            tvJineng.setVisibility(View.GONE);
                            labelView.setVisibility(View.VISIBLE);
                            ArrayList<String> data = new ArrayList<String>();
                            for (int i = 0; i < baseJianli.getSkillList().size(); i++) {
                                data.add(baseJianli.getSkillList().get(i).getSkill() + "  " + baseJianli.getSkillList().get(i).getDegree());
                            }
                            labelView.setLabels(data);
                        } else {
                            tvJineng.setVisibility(View.VISIBLE);
                            labelView.setVisibility(View.GONE);
                        }

                        if (baseJianli.getResume().getSelf_description() == null) {
                            tvDescDesc.setVisibility(View.GONE);
                            tvDesc.setVisibility(View.VISIBLE);
                        } else {
                            tvDesc.setVisibility(View.GONE);
                            tvDescDesc.setVisibility(View.VISIBLE);
                            tvDescDesc.setText(baseJianli.getResume().getSelf_description());
                        }

                        if (baseJianli.getVideo() != null) {
                            if (baseJianli.getVideo().getVideoUpdated() == 1) {
                                rlShowHasVideo.setVisibility(View.VISIBLE);
                                rlShowNoVideo.setVisibility(View.INVISIBLE);
                                tvGetStateAttention.setText("" + baseJianli.getResume().getWork_year()+"年");
                                tvGetStateEducation.setText(baseJianli.getResume().getEducation());
                                tvGetStateBrithPlace.setText(baseJianli.getResume().getCitysName());
                                tvStateName.setText(baseJianli.getResume().getTruename());
                                videoView.setUp(baseJianli.getVideo().getUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                                videoView.backButton.setVisibility(View.GONE);
                                videoView.fullscreenButton.setVisibility(View.VISIBLE);
                                tvAllTime.setText(videoView.currentTimeTextView.getText().toString());
                                Uri uri = Uri.parse (baseJianli.getVideo().getIndeximg());
                                videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                videoView.thumbImageView.setImageURI(uri);
                                Glide.with (mContext).load (uri).error(R.mipmap.gggggroup).into (videoView.thumbImageView);
                                videoView.fullscreenButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        videoView.startWindowFullscreen();
                                    }
                                });
                                videoView.startVideo();
                                videoView.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                        tvAllTime.setText(""+videoView.currentTimeTextView.getText().toString());
                                        if (!baseJianli.getVideo().isAddNum()) {
                                            addVideoNum(baseJianli.getVideo().getId());
                                            baseJianli.getVideo().setAddNum(true);
                                        }

                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });


                            } else if (baseJianli.getVideo().getVideoUpdated()==0){
                                rlShowHasVideo.setVerticalGravity(View.GONE);
                                rlShowNoVideo.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
        JZVideoPlayerStandard.backPress();
    }

    private void initView(View view) {
        textHeadTitle.setText("简历");
        ex_adapter = new CommonAdapter<WorkExperenceBean>(mContext, ex_data, R.layout.home_experence_ls_item) {
            @Override
            public void convert(ViewHolder helper, WorkExperenceBean item) {
                helper.setText(R.id.tv_experence_desc, "工作描述");
                helper.setText(R.id.tv_co, item.getCompany());
                helper.setText(R.id.tv_experence_position, item.getPosition());
                helper.setText(R.id.tv_time, item.getStart_time() + "至" + item.getEnd_time() + "    " + item.getPosition());
                helper.setText(R.id.tv_desc, item.getDescription());
            }
        };
        lsExperence.setAdapter(ex_adapter);
        lsExperence.setFocusable(false);

        pr_adapter = new CommonAdapter<ProjectBean>(mContext, pr_data, R.layout.home_experence_ls_item) {
            @Override
            public void convert(ViewHolder helper, ProjectBean item) {
                helper.setText(R.id.tv_experence_desc, "项目描述");
                helper.setText(R.id.tv_co, item.getName());
                helper.setText(R.id.tv_experence_position, item.getDuty());
                helper.setText(R.id.tv_time, item.getStart_time() + "至" + item.getEnd_time() + "    ");
                helper.setText(R.id.tv_desc, item.getDescription());
            }
        };
        lsProject.setAdapter(pr_adapter);
        lsProject.setFocusable(false);

        ed_adapter = new CommonAdapter<EducitionBean>(mContext, ed_data, R.layout.home_experence_ls_item) {
            @Override
            public void convert(ViewHolder helper, EducitionBean item) {
                helper.getView(R.id.tv_co).setVisibility(View.GONE);
                helper.getView(R.id.tv_experence_desc).setVisibility(View.GONE);
                helper.setText(R.id.tv_experence_position, item.getSchool());
                helper.setText(R.id.tv_time, item.getStarttime() + "至" + item.getEndtime());
                helper.setText(R.id.tv_desc, item.getEducation() + " |  " + item.getMajor());
            }
        };
        lsJiaoyue.setFocusable(false);
        lsJiaoyue.setAdapter(ed_adapter);
    }

    @OnClick({R.id.rl_desc, R.id.rl_work, R.id.rl_jinyan, R.id.rl_study, R.id.rl_jineng, R.id.rl_pingjia, R.id.iv_co_icon, R.id.rl_rl_two, R.id.rl_rl_three, R.id.rl_rl_four})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_desc:
            case R.id.rl_rl_two:
            case R.id.rl_rl_three:
            case R.id.rl_rl_four:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                which = "1";
                startActivity(new Intent(mContext, DescribeDetailsActivity.class));
                break;
            case R.id.rl_work:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                which = "2";
                startActivity(new Intent(mContext, WorkExperienceActivity.class));
                break;
            case R.id.rl_jinyan:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                which = "3";
                startActivity(new Intent(mContext, WorkExperienceActivity.class));
                break;
            case R.id.rl_study:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                which = "4";
                startActivity(new Intent(mContext, WorkExperienceActivity.class));
                break;
            case R.id.rl_jineng:
//                if (accessToken.equals("")) {
//                    doLogin();
//                    return;
//                }
//                which = "5";
//                startActivity(new Intent(mContext, DescribeDetailsActivity.class));

                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                which = "5";
                startActivity(new Intent(mContext, WorkExperienceActivity.class));

                break;
            case R.id.rl_pingjia:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                which = "6";
                startActivity(new Intent(mContext, DescribeDetailsActivity.class));
                break;
            case R.id.iv_co_icon:
                if (accessToken.equals("")) {
                    doLogin();
                    return;
                }
                final BottomPhotoDialog dialog = new BottomPhotoDialog(mContext, R.layout.view_popupwindow);
                dialog.show();
                dialog.getTv_pick_phone().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        takePhoto();
                    }
                });

                dialog.getTv_select_photo().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        selectPhoto();
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (which != null) {
            SelectJianLiBean selectJianLiBean = new SelectJianLiBean();
            BaseJianli baseJianli = new BaseJianli();
            switch (which) {
                case "1":
                    if (jianli != null) {
                        baseJianli.setResume(jianli.getResume());
                        selectJianLiBean.setJi(baseJianli);
                    }
                    selectJianLiBean.setType("1");
                    EventBus.getDefault().post(selectJianLiBean);
                    break;
                case "2":
                    selectJianLiBean.setType("2");
                    baseJianli.setExperienceList(jianli.getExperienceList());
                    selectJianLiBean.setJi(baseJianli);
                    EventBus.getDefault().post(selectJianLiBean);
                    break;
                case "3":
                    selectJianLiBean.setType("3");
                    baseJianli.setProjectList(jianli.getProjectList());
                    selectJianLiBean.setJi(baseJianli);
                    EventBus.getDefault().post(selectJianLiBean);
                    break;
                case "4":
                    selectJianLiBean.setType("4");
                    baseJianli.setEducationList(jianli.getEducationList());
                    selectJianLiBean.setJi(baseJianli);
                    EventBus.getDefault().post(selectJianLiBean);
                    break;
                case "5":
                    selectJianLiBean.setType("5");
                    baseJianli.setSkillList(jianli.getSkillList());
                    selectJianLiBean.setJi(baseJianli);
                    EventBus.getDefault().post(selectJianLiBean);
                    break;
                case "6":
                    if (jianli != null) {
                        baseJianli.setResume(jianli.getResume());
                        selectJianLiBean.setJi(baseJianli);
                    }
                    selectJianLiBean.setType("6");
                    EventBus.getDefault().post(selectJianLiBean);
                    break;

            }
        }
    }

    private void takePhoto() {
        RxPermissions permissions = new RxPermissions((MainActivity) mContext);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    captureManager = new ImageCaptureManager(mContext);
                    Intent intent = null;
                    try {
                        intent = captureManager.dispatchTakePictureIntent();
                        startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void selectPhoto() {
        RxPermissions permissions = new RxPermissions((MainActivity) mContext);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    //选择相册
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(mContext, JianLiFragment.this, PhotoPicker.REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        // 照片地址
                        String imagePaht = captureManager.getCurrentPhotoPath();
                        updatePhoto(imagePaht);
                    }
                    break;
            }
        }
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                String imagePaht = photos.get(0);
                updatePhoto(imagePaht);
            }
        }
    }

    private void updatePhoto(String imagePaht) {
        if (imagePaht != null) {
            File file = new File(imagePaht);
            try {
                OkHttpClientManager.postAsyn(UrlUtis.UPLOAD_FILE, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            PhotoBean photoBean = gson.fromJson(gson.toJson(entity.getData()), PhotoBean.class);
                            showToast(photoBean.getInfo());
                            Map<String, Object> h_map = new HashMap<>();
                            h_map.put("rid", rid);
                            h_map.put("photo", photoBean.getFilepath());
                            OkHttpClientManager.postAsynJson(gson.toJson(h_map), UrlUtis.EDIT_RESUME_INFO, new OkHttpClientManager.StringCallback() {
                                @Override
                                public void onFailure(Request request, IOException e) {

                                }

                                @Override
                                public void onResponse(String response) {
                                    BaseEntity entity1 = gson.fromJson(response, BaseEntity.class);
                                    if (entity1.getCode().equals(PublicUtils.SUCCESS)) {
                                        initData();
                                    }
                                }
                            });
                        }
                    }
                }, file, "file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (accessToken.equals("")) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        }
    }

    public void addVideoNum(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("videoType",0);
        map.put("id",id);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.VIDEO_ADD, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }
}
