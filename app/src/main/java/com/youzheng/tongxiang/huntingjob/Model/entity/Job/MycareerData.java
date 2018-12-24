package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/21.
 */

public class MycareerData {
    private int count ;
    private List<NewCareerBean> mycareer ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NewCareerBean> getMycareer() {
        return mycareer;
    }

    public void setMycareer(List<NewCareerBean> mycareer) {
        this.mycareer = mycareer;
    }
}
