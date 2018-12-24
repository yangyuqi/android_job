package com.youzheng.tongxiang.huntingjob.Model.Hr;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/5/22.
 */

public class CollectListBean {
    private int count ;
    private List<UserJlBean> collectList ;
    private List<UserJlBean> resumeList ;
    private List<UserJlBean> whoSeeMeList ;

    public List<UserJlBean> getWhoSeeMeList() {
        return whoSeeMeList;
    }

    public void setWhoSeeMeList(List<UserJlBean> whoSeeMeList) {
        this.whoSeeMeList = whoSeeMeList;
    }

    public List<UserJlBean> getResumeList() {
        return resumeList;
    }

    public void setResumeList(List<UserJlBean> resumeList) {
        this.resumeList = resumeList;
    }

    private List<UserJlBean> invitationList ;

    private List<UserJlBean> readList ;

    public List<UserJlBean> getReadList() {
        return readList;
    }

    public void setReadList(List<UserJlBean> readList) {
        this.readList = readList;
    }

    public List<UserJlBean> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(List<UserJlBean> invitationList) {
        this.invitationList = invitationList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserJlBean> getCollectList() {
        return collectList;
    }

    public void setCollectList(List<UserJlBean> collectList) {
        this.collectList = collectList;
    }
}
