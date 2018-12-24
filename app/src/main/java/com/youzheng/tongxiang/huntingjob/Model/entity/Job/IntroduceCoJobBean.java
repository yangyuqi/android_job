package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/21.
 */

public class IntroduceCoJobBean {
    private int count ;
    private List<JobBeanDetails> allJobList ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<JobBeanDetails> getAllJobList() {
        return allJobList;
    }

    public void setAllJobList(List<JobBeanDetails> allJobList) {
        this.allJobList = allJobList;
    }
}
