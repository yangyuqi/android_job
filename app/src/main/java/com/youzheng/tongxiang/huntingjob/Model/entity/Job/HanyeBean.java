package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/13.
 */

public class HanyeBean {
    private int id ;
    private String tradeName ;
    private List<HanyeBean> childs ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public List<HanyeBean> getChilds() {
        return childs;
    }

    public void setChilds(List<HanyeBean> childs) {
        this.childs = childs;
    }
}
