package com.youzheng.tongxiang.huntingjob.UI.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class FullScreenView extends VideoView {

    private int width;
    private int height;

    public FullScreenView(Context context) {
        super(context);
    }

    public FullScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMeasure(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        if (this.width > 0 && this.height > 0) {
            width = this.width;
            height = this.height;
        }
        setMeasuredDimension(width, height);
    }
}
