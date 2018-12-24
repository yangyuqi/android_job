package com.youzheng.tongxiang.huntingjob.Model.entity.user;

/**
 * Created by qiuweiyu on 2018/5/8.
 */

public class BlackCoBean {
    private String rid ;
    private String company ;
    private String create_time ;
    private String delegatee ;
    private boolean unwrappingSerializer ;
    private String name ;
    private int cid ;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDelegatee() {
        return delegatee;
    }

    public void setDelegatee(String delegatee) {
        this.delegatee = delegatee;
    }

    public boolean isUnwrappingSerializer() {
        return unwrappingSerializer;
    }

    public void setUnwrappingSerializer(boolean unwrappingSerializer) {
        this.unwrappingSerializer = unwrappingSerializer;
    }
}
