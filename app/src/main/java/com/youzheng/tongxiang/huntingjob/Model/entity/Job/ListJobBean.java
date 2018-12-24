package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

/**
 * Created by qiuweiyu on 2018/6/1.
 */

public class ListJobBean {
    private int jid ;
    private String title ;

    private boolean isSelect ;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
