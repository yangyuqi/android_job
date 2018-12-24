package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

import java.io.Serializable;

public class VideoBean implements Serializable{
    private Integer videoUpdated ;
    private int id ;
    private String indeximg ;
    private String comment ;
    private String url ;
    private int pageView ;

    private boolean addNum ;

    public boolean isAddNum() {
        return addNum;
    }

    public void setAddNum(boolean addNum) {
        this.addNum = addNum;
    }

    public Integer getVideoUpdated() {
        return videoUpdated;
    }

    public void setVideoUpdated(Integer videoUpdated) {
        this.videoUpdated = videoUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndeximg() {
        return indeximg;
    }

    public void setIndeximg(String indeximg) {
        this.indeximg = indeximg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPageView() {
        return pageView;
    }

    public void setPageView(int pageView) {
        this.pageView = pageView;
    }
}
