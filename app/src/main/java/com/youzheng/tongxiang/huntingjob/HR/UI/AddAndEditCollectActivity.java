package com.youzheng.tongxiang.huntingjob.HR.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.tongxiang.huntingjob.Model.Event.BaseEntity;
import com.youzheng.tongxiang.huntingjob.Model.request.OkHttpClientManager;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.BaseActivity;
import com.youzheng.tongxiang.huntingjob.R;
import com.youzheng.tongxiang.huntingjob.UI.Utils.PublicUtils;
import com.youzheng.tongxiang.huntingjob.UI.Utils.UrlUtis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAndEditCollectActivity extends BaseActivity {
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.textHeadTitle)
    TextView textHeadTitle;
    @BindView(R.id.textHeadNext)
    TextView textHeadNext;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_note)
    EditText edtNote;

    private String type ,name ,note;
    private int id ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_and_edit_collect_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btnBack.setVisibility(View.VISIBLE);
        type = getIntent().getStringExtra("type");
        if (type!=null) {
            textHeadTitle.setText("添加收藏夹");
        }else {
            textHeadTitle.setText("编辑收藏夹");
            id = getIntent().getIntExtra("id",0);
            name = getIntent().getStringExtra("name");
            note = getIntent().getStringExtra("note");
            edtTitle.setText(name);
            edtNote.setText(note);
        }
        textHeadNext.setVisibility(View.VISIBLE);
        textHeadNext.setText("完成");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textHeadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTitle.getText().toString().equals("")){
                    showToast("请输入标题");
                    return;
                }

                if (edtNote.getText().toString().equals("")){
                    showToast("请输入备注");
                    return;
                }

                Map<String,Object> map = new HashMap<>();
                map.put("name",edtTitle.getText().toString());
                map.put("note",edtNote.getText().toString());
                if (type!=null){
                    map.put("uid",uid);
                    OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.ADD_CO_COLLECT_LIST, new OkHttpClientManager.StringCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                            if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                finish();
                            }else {
                                showToast(entity.getMsg());
                            }
                        }
                    });
                }else {
                    map.put("id",id);
                    OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtis.EDIT_CO_COLLECT_LIST, new OkHttpClientManager.StringCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                            if (entity.getCode().equals(PublicUtils.SUCCESS)){
                                finish();
                            }else {
                                showToast(entity.getMsg());
                            }
                        }
                    });
                }
            }
        });
    }
}
