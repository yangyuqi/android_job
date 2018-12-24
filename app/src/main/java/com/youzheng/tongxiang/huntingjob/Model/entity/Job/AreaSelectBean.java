package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.ArrayList;

/**
 * Created by qiuweiyu on 2018/5/10.
 */

public class AreaSelectBean {
    private String name ;
    private ArrayList<Integer> arrayList ;

    public AreaSelectBean(String name, ArrayList<Integer> arrayList) {
        this.name = name;
        this.arrayList = arrayList;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
    }
}
