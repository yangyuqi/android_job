package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

/**
 * Created by qiuweiyu on 2018/2/11.
 */

public class EducationDetailsBean {
    private String ctype ;
    private String name ;
    private int id ;
    private int selected;
    private int position ;



    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSelected() {
        return selected;
    }

    public EducationDetailsBean(String ctype, String name, int id, int selected, int position) {
        this.ctype = ctype;
        this.name = name;
        this.id = id;
        this.selected = selected;
        this.position = position;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
