package com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Event.SelectJianLiBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.EducationBean;
import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.SkillListBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.DescribeDetailsActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class UserResumeFivePageFragment extends BaseFragment {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.tv_city)
    EditText tvCity;
    @BindView(R.id.tv_use_deeper)
    TextView tvUseDeeper;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    Unbinder unbinder;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.iv_iocn)
    ImageView ivIocn;
    SkillListBean bean ;
    private int deep_id ,rslid;
    SelectJianLiBean jianLiBean ;
    private OptionsPickerView pvCustomTime;
     int id = 0;
    private List<Integer> id_list = new ArrayList<>();
    private List<String> str_list = new ArrayList<>();

    public static UserResumeFivePageFragment getInstance(SelectJianLiBean bean) {
        UserResumeFivePageFragment fragment = new UserResumeFivePageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_resume_five_page_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        textHeadTitle.setVisibility(View.GONE);
        btnBack.setVisibility(View.VISIBLE);
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("完成");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DescribeDetailsActivity) mContext).finish();
            }
        });

        if (getArguments() != null) {
            jianLiBean = (SelectJianLiBean) getArguments().getSerializable("data");
            Log.e("ssssssddddd",gson.toJson(jianLiBean));
            id = jianLiBean.getJineng_id();
            if (id!=0) {
                for (int j = 0; j < jianLiBean.getJi().getSkillList().size(); j++) {
                    if (jianLiBean.getJi().getSkillList().get(j).getId() == id) {
                        bean = jianLiBean.getJi().getSkillList().get(j);
                    }
                    if (bean!=null){
                        tvCity.setText(bean.getSkill());
                        tvUseDeeper.setText(bean.getDegree());
                        deep_id = bean.getDegreeid();
                        rslid = jianLiBean.getJineng_id();
                    }

                }
            }
        }

        ivIocn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> school_map = new HashMap<>();
                school_map.put("ctype", "language_level");
                OkHttpClientManager.postAsynJson(gson.toJson(school_map), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                            final EducationBean comclass = gson.fromJson(gson.toJson(entity.getData()), EducationBean.class);
                            if (comclass.getComclass().size() > 0) {
                                final List<String> date = new ArrayList<String>();
                                for (int i = 0; i < comclass.getComclass().size(); i++) {
                                    date.add(comclass.getComclass().get(i).getName());
                                }
                                pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                        for (int i = 0; i < comclass.getComclass().size(); i++) {
                                            if (date.get(options1).equals(comclass.getComclass().get(i).getName())) {
                                                tvUseDeeper.setText(date.get(options1));
                                                deep_id = comclass.getComclass().get(i).getId();
//                                                id_list.add(comclass.getComclass().get(i).getId());
                                            }
                                        }
                                    }
                                }).setTitleText("选择掌握难度").build();
                                pvCustomTime.setPicker(date);
                                pvCustomTime.show();
                            }
                        }
                    }
                });
            }
        });

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (id==0) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("rid", rid);
                        map.put("skill", tvCity.getText().toString());
                        map.put("degree", String.valueOf(deep_id));
                        map.put("flag", "0");
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.JINENG_URL, new OkHttpClientManager.StringCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                                    ((DescribeDetailsActivity) mContext).finish();
                                } else {
                                    showToast(entity.getMsg());
                                }
                            }
                        });
                    }else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("rid", rid);
                        map.put("skill", tvCity.getText().toString());
                        map.put("degree", String.valueOf(deep_id));
                        map.put("flag","1");
                        map.put("rslid",rslid);
                        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.JINENG_URL, new OkHttpClientManager.StringCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                                    ((DescribeDetailsActivity) mContext).finish();
                                } else {
                                    showToast(entity.getMsg());
                                }
                            }
                        });
                    }
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View my = LayoutInflater.from(mContext).inflate(R.layout.add_jineng_layout,null);
                final EditText tv_city = my.findViewById(R.id.tv_city);
                ImageView iv_iocn = my.findViewById(R.id.iv_iocn);
                final TextView tv_use_deeper = my.findViewById(R.id.tv_use_deeper);
                iv_iocn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        str_list.add(tv_city.getText().toString());
                        doJi(tv_city,tv_use_deeper);
                    }

                });
                llContent.addView(my);
            }
        });
    }
    OptionsPickerView mpvCustomTime ;
    int mmid;
    private void doJi(final EditText edt_city, final TextView tv_use_deeper) {
        Map<String, Object> school_map = new HashMap<>();
        school_map.put("ctype", "language_level");
        OkHttpClientManager.postAsynJson(gson.toJson(school_map), UrlUtis.WORK_TIAOJIAN, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                    final EducationBean comclass = gson.fromJson(gson.toJson(entity.getData()), EducationBean.class);
                    if (comclass.getComclass().size() > 0) {
                        final List<String> date = new ArrayList<String>();
                        for (int i = 0; i < comclass.getComclass().size(); i++) {
                            date.add(comclass.getComclass().get(i).getName());
                        }
                        mpvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                                for (int i = 0; i < comclass.getComclass().size(); i++) {
                                    if (date.get(options1).equals(comclass.getComclass().get(i).getName())) {
                                        tv_use_deeper.setText(date.get(options1));
                                        mmid =comclass.getComclass().get(i).getId();
                                    }
                                }
                               addJineng(mmid,edt_city);
                            }

                            private void addJineng(int mmid, EditText edt_city) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("rid", rid);
                                map.put("skill", edt_city.getText().toString());
                                map.put("degree", mmid);
                                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.JINENG_URL, new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                                        if (entity.getCode().equals(PublicUtils.SUCCESS)) {
                                        } else {
                                            showToast(entity.getMsg());
                                        }
                                    }
                                });
                            }
                        }).setTitleText("选择掌握难度").build();
                        mpvCustomTime.setPicker(date);
                        mpvCustomTime.show();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
