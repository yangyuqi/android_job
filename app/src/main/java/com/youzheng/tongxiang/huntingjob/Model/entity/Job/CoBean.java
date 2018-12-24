package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.VideoBean;

/**
 * Created by qiuweiyu on 2018/2/12.
 */

public class CoBean {
    private CoBeanEntity company ;
    private VideoBean video ;

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public CoBeanEntity getCompany() {
        return company;
    }

    public void setCompany(CoBeanEntity company) {
        this.company = company;
    }
}
