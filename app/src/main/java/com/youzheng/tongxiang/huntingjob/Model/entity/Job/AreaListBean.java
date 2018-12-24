package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/5/9.
 */

public class AreaListBean {
    private int id ;
    private String areaName ;
    private List<AreaListBean> childs ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<AreaListBean> getChilds() {
        return childs;
    }

    public void setChilds(List<AreaListBean> childs) {
        this.childs = childs;
    }
}
