package com.youzheng.tongxiang.huntingjob.Model.Hr;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/5/21.
 */

public class ResumeListBean {
    private int count ;
    private List<UserJlBean> resumeList ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserJlBean> getResumeList() {
        return resumeList;
    }

    public void setResumeList(List<UserJlBean> resumeList) {
        this.resumeList = resumeList;
    }
}
