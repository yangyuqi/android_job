package com.youzheng.tongxiang.huntingjob.Model.entity.deliver;

public class ComJpushData {
    private ComJpushDataBean data ;
    private String type ;
    public ComJpushDataBean getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(ComJpushDataBean data) {
        this.data = data;
    }
}
