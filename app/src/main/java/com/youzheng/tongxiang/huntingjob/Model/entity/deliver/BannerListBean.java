package com.youzheng.tongxiang.huntingjob.Model.entity.deliver;

/**
 * Created by qiuweiyu on 2018/2/22.
 */

public class BannerListBean {
    private int id ;
    private String imgUrl ;
    private String state ;
    private String link ;
    private int orderNum ;
    private String showType ;
    private String placement ;
    private String title ;
    private String linkType ;
    private String childLinkType ;

    public String getChildLinkType() {
        return childLinkType;
    }

    public void setChildLinkType(String childLinkType) {
        this.childLinkType = childLinkType;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
