package com.youzheng.tongxiang.huntingjob.Prestener.fragment.resume;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.Event.SelectJianLiBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.DescribeDetailsActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class UserResumeSixPageFragment extends BaseFragment {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    Unbinder unbinder;
    @BindView(R.id.edt_des)
    EditText edtDes;

    public static UserResumeSixPageFragment getInstance(SelectJianLiBean bean) {
        UserResumeSixPageFragment fragment = new UserResumeSixPageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_resume_six_page_layout, null);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments()!=null){
            SelectJianLiBean jianLiBean = (SelectJianLiBean) getArguments().getSerializable("data");
            if (jianLiBean.getJi().getResume().getSelf_description()!=null) {
                edtDes.setText(jianLiBean.getJi().getResume().getSelf_description());
            }
        }

        initView();

        return view;
    }

    private void initView() {
        textHeadNext.setText("确定");
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadTitle.setVisibility(View.GONE);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DescribeDetailsActivity)mContext).finish();
            }
        });

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("rid",rid);
                if (edtDes.getText().toString().equals("")){
                    showToast("填写个人评价");
                    return;
                }
                map.put("self_description",edtDes.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ADD_SELF_PINGJIA, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                        if (entity.getCode().equals(PublicUtils.SUCCESS)){
                            ((DescribeDetailsActivity)mContext).finish();
                        }else {
                            showToast(entity.getMsg());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
