package com.youzheng.tongxiang.huntingjob.Model.Event;

import com.youzheng.tongxiang.huntingjob.Model.entity.jianli.BaseJianli;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2018/2/12.
 */

public class SelectJianLiBean implements Serializable{
    private String type ;
    private BaseJianli ji ;

    private int expe_id ;//经历id
    private int project_id ;//项目id
    private int educition_id;//教育id
    private int jineng_id ;//技能id

    public int getJineng_id() {
        return jineng_id;
    }

    public void setJineng_id(int jineng_id) {
        this.jineng_id = jineng_id;
    }

    public int getEducition_id() {
        return educition_id;
    }

    public void setEducition_id(int educition_id) {
        this.educition_id = educition_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BaseJianli getJi() {
        return ji;
    }

    public void setJi(BaseJianli ji) {
        this.ji = ji;
    }

    public int getExpe_id() {
        return expe_id;
    }

    public void setExpe_id(int expe_id) {
        this.expe_id = expe_id;
    }
}
