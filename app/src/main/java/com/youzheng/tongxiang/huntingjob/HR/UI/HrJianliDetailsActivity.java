package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobCollectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.ListJobData;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.BaseJianli;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducitionBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.ProjectBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.WorkExperenceBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FullScreenActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.CommonAdapter;
import com.youzheng.tongxiang.huntingjob.UI.Adapter.ViewHolder;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;
import com.youzheng.tongxiang.huntingjob.UI.Widget.CircleImageView;
import com.youzheng.tongxiang.huntingjob.UI.Widget.NoScrollListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by qiuweiyu on 2018/5/10.
 */

public class HrJianliDetailsActivity extends BaseActivity {

    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;

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

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.rl_shoucan)
    RelativeLayout rlShoucan;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_no_good)
    TextView tvNoGood;
    @BindView(R.id.tv_go_interview)
    TextView tvGoInterview;
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
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
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
    @BindView(R.id.tv_work_mean)
    TextView tvWorkMean;
    @BindView(R.id.tv_co_get_name)
    TextView tvCoGetName;

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
    @BindView(R.id.tv_all_time)
    TextView tvAllTime;

    private String which;

    BaseJianli jianli;
    private CommonAdapter<WorkExperenceBean> ex_adapter;
    private List<WorkExperenceBean> ex_data = new ArrayList<>();

    private CommonAdapter<ProjectBean> pr_adapter;
    private List<ProjectBean> pr_data = new ArrayList<>();

    private CommonAdapter<EducitionBean> ed_adapter;
    private List<EducitionBean> ed_data = new ArrayList<>();

    private int jid, rid;

    private ArrayList<ArrayList<String>> time = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_hr_jianli_layout);
        ButterKnife.bind(this);
        initView();
        jid = getIntent().getIntExtra("jid", 0);
        which = getIntent().getStringExtra("type");
        if (which != null && which.equals("show")) {
            tvNoGood.setVisibility(View.VISIBLE);
        }

        initData();
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("rid", jid);
        map.put("uid", uid);
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
                    if (baseJianli.getResume().getPhoto() != null) {
                        Glide.with(mContext).load(baseJianli.getResume().getPhoto()).error(R.mipmap.gggggroup).into(ivCoIcon);
                    }
                    tvGetAttention.setText("" + baseJianli.getResume().getWork_year() + "年");
                    tvGetEducation.setText(baseJianli.getResume().getEducation());
                    tvGetBrithPlace.setText(baseJianli.getResume().getCitysName());
                    tvGetHopeJob.setText(baseJianli.getResume().getPosition());
                    tvGetHopeMoney.setText(baseJianli.getResume().getWageName());
                    getTvGetBrith.setText(baseJianli.getResume().getBirthdate());
                    tvGetHopeTrade.setText(baseJianli.getResume().getTradeName());
                    tvGetHopeAddress.setText(baseJianli.getResume().getHopeCityName());
                    tvPhone.setText(baseJianli.getResume().getTelephone());
                    tvEmail.setText(baseJianli.getResume().getEmail());
                    tvWorkMean.setText(baseJianli.getResume().getJobs_natureName());
                    if (baseJianli.getResume().getLastestCompany() == null) {
                        tvCoGetName.setText("暂无第一家公司");
                    } else {
                        tvCoGetName.setText(baseJianli.getResume().getLastestCompany());
                    }
                    rid = baseJianli.getResume().getId();
                    if (baseJianli.getResume().getResumeStatus() != null) {
                        if (baseJianli.getResume().getResumeStatus().equals("3")) {
                            tvGoInterview.setBackgroundResource(R.color.text_gray);
                            tvNoGood.setBackgroundResource(R.color.colorPrimary);
                            tvGoInterview.setEnabled(false);
                            tvNoGood.setEnabled(true);
                        } else if (baseJianli.getResume().getResumeStatus().equals("4")) {
                            tvNoGood.setBackgroundResource(R.color.text_gray);
                            tvGoInterview.setBackgroundResource(R.color.text_blue_new);
                            tvNoGood.setEnabled(false);
                            tvGoInterview.setEnabled(true);
                        } else {

                        }
                    }
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

                    if (baseJianli.getResume().getIs_collect() == 0) {
                        cb.setChecked(false);
                        cb.setText("收藏");
                    } else {
                        cb.setChecked(true);
                        cb.setText("收藏");
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



                        } else {
                            rlShowHasVideo.setVerticalGravity(View.INVISIBLE);
                            rlShowNoVideo.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }
        });
    }


    private void initView() {
        textHeadTitle.setText("简历详情");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

        tvNoGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("uid", uid);
                objectMap.put("rid", jid);
                OkHttpClientManager.postAsynJson(gson.toJson(objectMap), UrlUtis.GET_HR_CO_JOB, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            final ListJobData listJobData = gson.fromJson(gson.toJson(entity.getData()), ListJobData.class);
                            if (listJobData.getList().size() > 0) {
                                final List<Integer> id_list = new ArrayList<Integer>();
                                List<String> title_list = new ArrayList<String>();
                                for (int i = 0; i < listJobData.getList().size(); i++) {
                                    id_list.add(listJobData.getList().get(i).getJid());
                                    title_list.add(listJobData.getList().get(i).getTitle());
                                }
                                OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        int m_id = listJobData.getList().get(options1).getJid();
                                        initInter(m_id, "4", null);
                                    }
                                }).setTitleText("职位选择").build();
                                pvCustomTime.setPicker(title_list);
                                pvCustomTime.show();
                            }else {
                                showToast("您已邀请面试");
                            }
                        }
                    }
                });

            }
        });


        tvGoInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("uid", uid);
                objectMap.put("rid", jid);
                OkHttpClientManager.postAsynJson(gson.toJson(objectMap), UrlUtis.GET_HR_CO_JOB, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            final ListJobData listJobData = gson.fromJson(gson.toJson(entity.getData()), ListJobData.class);
                            if (listJobData.getList().size() > 0) {
                                final List<Integer> id_list = new ArrayList<Integer>();
                                List<String> title_list = new ArrayList<String>();
                                for (int i = 0; i < listJobData.getList().size(); i++) {
                                    id_list.add(listJobData.getList().get(i).getJid());
                                    title_list.add(listJobData.getList().get(i).getTitle());
                                }


                                OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        int m_id = listJobData.getList().get(options1).getJid();
                                        String time = PublicUtils.testDay(7).get(options2) + "   " + PublicUtils.showtime().get(options3);
                                        initInter(m_id, "3", time);
                                    }
                                }).setTitleText("职位选择").build();
                                pvCustomTime.setNPicker(title_list, PublicUtils.testDay(7), PublicUtils.showtime());
                                pvCustomTime.show();
                            }
                        }
                    }
                });

            }
        });


        rlShoucan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("uid", uid);
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.CO_COLLECT_LIST, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            final ListJobData listJobData = gson.fromJson(gson.toJson(entity.getData()), ListJobData.class);
                            if (listJobData.getData().size() > 0) {
                                final List<Integer> id_list = new ArrayList<Integer>();
                                final List<String> title_list = new ArrayList<String>();
                                id_list.add(-1);
                                title_list.add("创建新的收藏夹");
                                for (int i = 0; i < listJobData.getData().size(); i++) {
                                    id_list.add(listJobData.getData().get(i).getId());
                                    title_list.add(listJobData.getData().get(i).getName());
                                }
                                OptionsPickerView pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        if (title_list.get(options1).equals("创建新的收藏夹")) {
                                            addNewCollect();
                                        } else {
                                            try {
                                                addCollect(listJobData.getData().get(options1-1));
                                            }catch (Exception e){}
                                        }

                                    }
                                }).setTitleText("收藏夹选择").build();
                                pvCustomTime.setPicker(title_list);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
            }
        });

    }

    private void addCollect(ListJobCollectBean listJobCollectBean) {
        Map<String, Object> t_map = new HashMap<>();
        t_map.put("jid", jid);
        t_map.put("uid", uid);
        t_map.put("ctype", "0");
        t_map.put("favoritesId", listJobCollectBean.getId());
        OkHttpClientManager.postAsynJson(gson.toJson(t_map), UrlUtis.JOB_COLLECT, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                showToast(entity.getMsg());
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    initData();
                }
            }
        });
    }

    private void addNewCollect() {
        Intent intent = new Intent(mContext, AddAndEditCollectActivity.class);
        intent.putExtra("type", "");
        startActivity(intent);
    }

    private void initInter(int m_id, final String status, String time) {
        Map<String, Object> map = new HashMap<>();
        map.put("rid", String.valueOf(rid));
        map.put("status", status);
        map.put("id", "");
        map.put("jid", String.valueOf(m_id));
        map.put("interview_time", time);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.UPDATE_DELIVERY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                MobclickAgent.onEvent(mContext,"tv_go_interview");
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    if (status.equals("3")) {
                        tvGoInterview.setBackgroundResource(R.color.text_gray);
                        tvNoGood.setBackgroundResource(R.color.colorPrimary);
                        tvGoInterview.setEnabled(false);
                        tvNoGood.setEnabled(true);
                    } else if (status.equals("4")) {
                        tvNoGood.setBackgroundResource(R.color.text_gray);
                        tvGoInterview.setBackgroundResource(R.color.text_blue_new);
                        tvNoGood.setEnabled(false);
                        tvGoInterview.setEnabled(true);
                    }
                }
            }
        });
    }

    public void addVideoNum(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("videoType",0);
        map.put("id",jid);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.VIDEO_ADD, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
        MobclickAgent.onPageEnd("new_hr_jianli_layout");
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("new_hr_jianli_layout");
        MobclickAgent.onResume(mContext);
    }
}
