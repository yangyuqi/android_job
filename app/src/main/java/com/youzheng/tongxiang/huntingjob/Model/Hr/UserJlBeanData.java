package com.youzheng.tongxiang.huntingjob.Model.Hr;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/5/16.
 */

public class UserJlBeanData {
    public List<UserJlBean> getResumeList() {
        return resumeList;
    }

    public void setResumeList(List<UserJlBean> resumeList) {
        this.resumeList = resumeList;
    }
    private List<UserJlBean> resumeList ;
    private int count ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
