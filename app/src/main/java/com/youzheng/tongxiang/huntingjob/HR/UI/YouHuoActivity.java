package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.YouHuoList;
import com.youzheng.tongxiang.huntingjob.Model.entity.Job.YouHuoListBean;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YouHuoActivity extends BaseActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.labelView)
    LabelsView labelView;
    ArrayList<YouHuoListBean> data = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    private ArrayList<YouHuoListBean> new_a = new ArrayList<>();
    private String type ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youhuo_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textHeadNext.setText("确定");
        if (type==null) {
            textHeadTitle.setText("职位诱惑");
        }else {
            textHeadTitle.setText("公司福利");
            ((TextView)findViewById(R.id.tv_nnnn)).setText("选择公司福利，提升公司吸引力，有效增加职位投递");
        }
        textHeadNext.setVisibility(View.VISIBLE);
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtis.YOUHUO_LABELS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                BaseEntity entity = gson.fromJson(response, BaseEntity.class);
                YouHuoList list = gson.fromJson(gson.toJson(entity.getData()), YouHuoList.class);
                if (list.getResult().size() > 0) {
                    arrayList.clear();
                    data = list.getResult();
                    for (int i = 0; i < list.getResult().size(); i++) {
                        arrayList.add(list.getResult().get(i).getName());
                    }

                    labelView.setLabels(arrayList);
                }
            }
        });

        labelView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(View label, String labelText, boolean isSelect, int position) {
                if (isSelect) {
                    new_a.add(new YouHuoListBean(labelText, data.get(position).getId()));
                }else {
                    if (new_a.size() > 0) {
                        for (int i = 0 ;i<new_a.size();i++){
                            if (labelText.equals(new_a.get(i).getName())){
                                new_a.remove(i);
                            }
                        }
                    }
                }
            }
        });

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new_a.size()>0){
                    Intent intent = new Intent();
                    intent.putExtra("data",new_a);
                    setResult(2,intent);
                    finish();
                }else {
                    showToast("请选择标签");
                }
            }
        });
    }
}
