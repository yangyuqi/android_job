package com.youzheng.tongxiang.huntingjob.Model.Event;

/**
 * Created by qiuweiyu on 2018/2/11.
 */

public class BaseEntity<T> {
    private String code ;
    private T data ;
    private String msg ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
