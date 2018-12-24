package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2018/5/4.
 */

public class CateBean implements Serializable{
    private int position ;
    private int position2 ;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition2() {
        return position2;
    }

    public void setPosition2(int position2) {
        this.position2 = position2;
    }

    public CateBean(int position, int position2) {

        this.position = position;
        this.position2 = position2;
    }
}
