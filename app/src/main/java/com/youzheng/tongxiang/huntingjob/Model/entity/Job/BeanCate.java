package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2018/2/20.
 */

public class BeanCate implements Serializable{
    private int position ;
    private String type ;

    public BeanCate(int position, String type) {
        this.position = position;
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
