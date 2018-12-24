package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

import java.util.List;
import java.util.Map;

/**
 * Created by qiuweiyu on 2018/2/11.
 */

public class AreaInfoBean {
    private String code ;
    private Map<String ,List<AreaInfoChildBean>> data ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, List<AreaInfoChildBean>> getData() {
        return data;
    }

    public void setData(Map<String, List<AreaInfoChildBean>> data) {
        this.data = data;
    }

    public AreaInfoBean(String code, Map<String, List<AreaInfoChildBean>> data) {

        this.code = code;
        this.data = data;
    }
}
