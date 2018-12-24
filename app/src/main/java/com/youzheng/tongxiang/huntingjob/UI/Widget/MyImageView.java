package com.youzheng.tongxiang.huntingjob.UI.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by qiuweiyu on 2018/5/9.
 */

public class MyImageView  extends ImageView
{

    public MyImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent event)
    {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

}
