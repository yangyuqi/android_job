package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

/**
 * Created by qiuweiyu on 2018/5/3.
 */

public class NewEducationBean {
    private String ctype ;
    private String name ;
    private int id ;
    private int selected;
    private int position ;

    private String ctype2 ;
    private String name2 ;
    private int id2 ;
    private int selected2;
    private int position2 ;

    public NewEducationBean(){

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

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCtype2() {
        return ctype2;
    }

    public void setCtype2(String ctype2) {
        this.ctype2 = ctype2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public int getSelected2() {
        return selected2;
    }

    public void setSelected2(int selected2) {
        this.selected2 = selected2;
    }

    public int getPosition2() {
        return position2;
    }

    public void setPosition2(int position2) {
        this.position2 = position2;
    }

    public NewEducationBean(String ctype, String name, int id, int selected, int position, String ctype2, String name2, int id2, int selected2, int position2) {

        this.ctype = ctype;
        this.name = name;
        this.id = id;
        this.selected = selected;
        this.position = position;
        this.ctype2 = ctype2;
        this.name2 = name2;
        this.id2 = id2;
        this.selected2 = selected2;
        this.position2 = position2;
    }
}
