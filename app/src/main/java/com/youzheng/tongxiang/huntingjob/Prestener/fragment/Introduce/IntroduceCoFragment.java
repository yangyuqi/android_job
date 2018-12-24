package com.youzheng.tongxiang.huntingjob.Prestener.fragment.Introduce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBeanDetails;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.CoBeanEntity;
import com.youzheng.tongxiang.huntingjob.Prestener.fragment.BaseFragment;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2018/2/9.
 */

public class IntroduceCoFragment extends BaseFragment {
    @BindView(R.id.ls_details)
    WebView lsDetails;
    @BindView(R.id.labelView)
    LabelsView labelView;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    Unbinder unbinder;

    private ArrayList<String> label = new ArrayList<>();
    CoBeanEntity type ;
    public static IntroduceCoFragment getInstance(CoBeanEntity type) {
        IntroduceCoFragment fragment = new IntroduceCoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.introduce_co_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            type = (CoBeanEntity) getArguments().getSerializable("type");
            if (type!=null) {
                initView(type);
            }
        }

        return view;
    }

    private void initView(CoBeanEntity type) {
        if (type.getAddress()!=null){
            ArrayList<String> arrayList = new ArrayList<>();

            tvAddress.setText(type.getAddress());

            if (type.getJobtag()!=null) {
                if (type.getJobtag().indexOf(",") > 0) {
                    String[] strings = type.getJobtag().split(",");
                    for (int i = 0; i < strings.length; i++) {
                        arrayList.add(strings[i]);
                    }
                    labelView.setLabels(arrayList);
                } else {
                    arrayList.add(type.getJobtag());
                    labelView.setLabels(arrayList);
                }
            }

            lsDetails.loadDataWithBaseURL(null, PublicUtils.getNewContent(type.getSummary()),"text/html","utf-8",null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
