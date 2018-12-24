package com.youzheng.tongxiang.huntingjob.Model.entity.user;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/5/31.
 */

public class WhoSeeMeList {
    private int count ;
    private List<WhoSeeMeBean> whoSeeMeList ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<WhoSeeMeBean> getWhoSeeMeList() {
        return whoSeeMeList;
    }

    public void setWhoSeeMeList(List<WhoSeeMeBean> whoSeeMeList) {
        this.whoSeeMeList = whoSeeMeList;
    }
}
