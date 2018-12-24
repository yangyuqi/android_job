package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.ArrayList;

/**
 * Created by qiuweiyu on 2018/2/20.
 */

public class OccupationListBean {
    private int id ;
    private String occupationName ;
    private ArrayList<OccupationListBean> childs ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOccupationName() {
        return occupationName;
    }

    public void setOccupationName(String occupationName) {
        this.occupationName = occupationName;
    }

    public ArrayList<OccupationListBean> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<OccupationListBean> childs) {
        this.childs = childs;
    }
}
