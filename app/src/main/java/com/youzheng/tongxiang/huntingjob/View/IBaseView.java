package com.youzheng.tongxiang.huntingjob.View;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public interface IBaseView {
    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载动画
     */
    void hideLoading();

    /**
     * 网络错误时，显示加载错误
     */
    void showNetError();
}
