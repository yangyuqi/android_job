package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2018/2/13.
 */

public class SkillListBean implements Serializable{
    private int degreeid ;
    private String skill ;
    private String degree ;
    private String skill_type ;
    private int id ;
    private int rid ;

    public int getDegreeid() {
        return degreeid;
    }

    public void setDegreeid(int degreeid) {
        this.degreeid = degreeid;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSkill_type() {
        return skill_type;
    }

    public void setSkill_type(String skill_type) {
        this.skill_type = skill_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
