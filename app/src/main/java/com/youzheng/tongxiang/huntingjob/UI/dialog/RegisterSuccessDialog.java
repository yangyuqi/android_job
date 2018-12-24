package com.youzheng.tongxiang.huntingjob.UI.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.youzheng.tongxiang.huntingjob.HR.UI.HrCoInfoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FillHRInfoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.FillInfoActivity;
import com.youzheng.tongxiang.huntingjob.Prestener.activity.RegisterPhoneActivity;
import com.youzheng.tongxiang.huntingjob.R;

import butterknife.BindView;

/**
 * Created by qiuweiyu on 2018/2/8.
 */

public class RegisterSuccessDialog extends Dialog {
    TextView tvTime ,tv_content;
    private Context context;
    private CountDownTimer downTimer ;
    private int countDownSecond = 3;//倒计时多少秒
    private String type ;

    public RegisterSuccessDialog(@NonNull Context context,String type) {
        super(context, R.style.DeleteDialogStyle);
        this.context = context;
        this.type=type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.register_success_dialog_layout, null);
        tvTime = view.findViewById(R.id.tv_time);
        tv_content = view.findViewById(R.id.tv_content);
        if (type.equals("2")){
            tv_content.setText("请填写公司基本信息，");
        }
        setContentView(view);
        downTimer = new CountDownTimer(countDownSecond*1000, 1000) {
            @Override
            public void onTick(long l) {
                tvTime.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                if (isShowing()) {
                    if (type.equals("1")) {
                        context.startActivity(new Intent(context, FillInfoActivity.class));
                    }else if (type.equals("2")){
                        Intent intent = new Intent(context,HrCoInfoActivity.class);
                        intent.putExtra("type","2");
                        context.startActivity(intent);
                    }
                    dismiss();
                    ((RegisterPhoneActivity)context).finish();
                }
            }
        };
        downTimer.start();
    }
}
