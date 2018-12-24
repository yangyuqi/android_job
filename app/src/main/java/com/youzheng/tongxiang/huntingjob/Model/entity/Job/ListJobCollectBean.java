package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

public class ListJobCollectBean {
    private int id ;
    private int uid ;
    private String name ;
    private String note ;
    private List<TxypResumeListBean> txypResumeList ;

    private  int type ;//表示 1 出现 0 隐藏
    private  boolean select ;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<TxypResumeListBean> getTxypResumeList() {
        return txypResumeList;
    }

    public void setTxypResumeList(List<TxypResumeListBean> txypResumeList) {
        this.txypResumeList = txypResumeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
