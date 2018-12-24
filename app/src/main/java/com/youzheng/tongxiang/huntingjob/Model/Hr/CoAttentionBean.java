package com.youzheng.tongxiang.huntingjob.Model.Hr;

/**
 * Created by qiuweiyu on 2018/5/23.
 */

public class CoAttentionBean {
    private String corporationId ;
    private int ctype ;
    private String mainTrade ;
    private String corporation ;
    private String name ;
    private String state ;
    private int id ;
    private String license_photo ;
    private int mainTradeId ;
    private int ctypeId ;
    private String ctypeName ;

    public String getCtypeName() {
        return ctypeName;
    }

    public void setCtypeName(String ctypeName) {
        this.ctypeName = ctypeName;
    }

    public int getMainTradeId() {
        return mainTradeId;
    }

    public void setMainTradeId(int mainTradeId) {
        this.mainTradeId = mainTradeId;
    }

    public int getCtypeId() {
        return ctypeId;
    }

    public void setCtypeId(int ctypeId) {
        this.ctypeId = ctypeId;
    }

    public String getCorporationId() {
        return corporationId;
    }

    public void setCorporationId(String corporationId) {
        this.corporationId = corporationId;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public String getMainTrade() {
        return mainTrade;
    }

    public void setMainTrade(String mainTrade) {
        this.mainTrade = mainTrade;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicense_photo() {
        return license_photo;
    }

    public void setLicense_photo(String license_photo) {
        this.license_photo = license_photo;
    }
}
