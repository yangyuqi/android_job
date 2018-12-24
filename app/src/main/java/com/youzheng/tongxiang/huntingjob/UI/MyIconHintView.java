package com.youzheng.tongxiang.huntingjob.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.jude.rollviewpager.hintview.ShapeHintView;
import com.youzheng.tongxiang.huntingjob.R;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public class MyIconHintView extends ShapeHintView {
    private int focusResId;
    private int normalResId;
    private int pointWidth;
    private int pointHeight;

    public MyIconHintView(Context context, @DrawableRes int focusResId, @DrawableRes int normalResId) {
        super(context);
        this.focusResId = focusResId;
        this.normalResId = normalResId;
        pointWidth = context.getResources().getDimensionPixelOffset(R.dimen.banner_point_width);
        pointHeight = context.getResources().getDimensionPixelOffset(R.dimen.banner_point_height);
    }


    @Override
    public Drawable makeFocusDrawable() {
        Drawable drawable = getContext().getResources().getDrawable(focusResId);
        drawable = zoomDrawable(drawable, pointWidth, pointHeight);
        return drawable;
    }

    @Override
    public Drawable makeNormalDrawable() {
        Drawable drawable = getContext().getResources().getDrawable(normalResId);
        drawable = zoomDrawable(drawable, pointHeight, pointHeight);
        return drawable;
    }


    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, newbmp);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
}
