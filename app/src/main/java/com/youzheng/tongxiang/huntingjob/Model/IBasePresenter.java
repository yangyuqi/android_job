package com.youzheng.tongxiang.huntingjob.Model;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public interface IBasePresenter {
    /**
     * 获取网络数据，更新界面
     */
    void getData();

    /**
     * 加载更多数据
     */
    void getMoreData();
}
