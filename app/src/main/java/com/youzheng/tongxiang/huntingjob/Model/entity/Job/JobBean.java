package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.VideoBean;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/12.
 */

public class JobBean {
    private JobBeanDetails job ;
    private List<jobluredsBean> joblureds ;
    private VideoBean video ;

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public List<jobluredsBean> getJoblureds() {
        return joblureds;
    }

    public void setJoblureds(List<jobluredsBean> joblureds) {
        this.joblureds = joblureds;
    }

    public JobBeanDetails getJob() {
        return job;
    }

    public void setJob(JobBeanDetails job) {
        this.job = job;
    }
}
