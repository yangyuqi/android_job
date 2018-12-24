package com.youzheng.tongxiang.huntingjob.Model.entity.user;

/**
 * Created by qiuweiyu on 2018/5/31.
 */

public class WhoSeeMeBean {
    private String logo ;
    private String name ;
    private String introduction ;
    private int id ;
    private String create_time ;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
