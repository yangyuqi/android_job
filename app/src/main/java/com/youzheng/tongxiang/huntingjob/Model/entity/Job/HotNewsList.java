package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/21.
 */

public class HotNewsList {
    private List<JobNewsBean> hotnews ;

    private List<JobNewsBean> jobnews ;

    public List<JobNewsBean> getJobnews() {
        return jobnews;
    }

    public void setJobnews(List<JobNewsBean> jobnews) {
        this.jobnews = jobnews;
    }

    public List<JobNewsBean> getHotnews() {
        return hotnews;
    }

    public void setHotnews(List<JobNewsBean> hotnews) {
        this.hotnews = hotnews;
    }
}
