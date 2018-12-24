package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/14.
 */

public class HomeBeanList {
    private List<JobBeanDetails> jobList ;
    private List<CoBeanDetails> comList ;

    public List<CoBeanDetails> getComList() {
        return comList;
    }

    public void setComList(List<CoBeanDetails> comList) {
        this.comList = comList;
    }

    public List<JobBeanDetails> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobBeanDetails> jobList) {
        this.jobList = jobList;
    }
}
