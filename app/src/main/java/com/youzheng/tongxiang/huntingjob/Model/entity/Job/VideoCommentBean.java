package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

public class VideoCommentBean {
    private String comment ;
    private String url ;
    private String video_img ;
    private float width ;
    private float height ;

    public float getWidth() {
        return width;
    }

    public VideoCommentBean(String comment, String url, String video_img, float width, float height) {
        this.comment = comment;
        this.url = url;
        this.video_img = video_img;
        this.width = width;
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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

    public String getVideo_img() {
        return video_img;
    }

    public void setVideo_img(String video_img) {
        this.video_img = video_img;
    }

    public VideoCommentBean(String comment, String url, String video_img) {

        this.comment = comment;
        this.url = url;
        this.video_img = video_img;
    }
}
