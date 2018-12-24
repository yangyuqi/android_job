package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/11.
 */

public class AreaInfoChildBean {
    private String id ;
    private String areaName ;
    private List<AreaInfoChildBean> childs ;

    public AreaInfoChildBean(String id, String areaName, List<AreaInfoChildBean> childs) {
        this.id = id;
        this.areaName = areaName;
        this.childs = childs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<AreaInfoChildBean> getChilds() {
        return childs;
    }

    public void setChilds(List<AreaInfoChildBean> childs) {
        this.childs = childs;
    }
}
