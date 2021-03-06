package com.youzheng.tongxiang.huntingjob.UI.Utils;

import android.os.CountDownTimer;
import android.widget.Button;

import com.youzheng.tongxiang.huntingjob.R;


/**
 * Created by yangyuqi on 17-7-25.
 */

public class MyCountDownTimer extends CountDownTimer {

    private Button btn ;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(Button button , long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        btn = button ;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);
        btn.setText(millisUntilFinished/1000+"s");
        btn.setBackgroundResource(R.color.new_text_gray);
    }

    @Override
    public void onFinish() {
        btn.setText("验证码");
        //设置可点击
        btn.setClickable(true);
        btn.setBackgroundResource(R.color.text_blue_new);
    }
}
