package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/6/1.
 */

public class ListJobData {
    private List<ListJobBean> list ;

    private List<ListJobCollectBean> data ;

    public List<ListJobCollectBean> getData() {
        return data;
    }

    public void setData(List<ListJobCollectBean> data) {
        this.data = data;
    }

    public List<ListJobBean> getList() {
        return list;
    }

    public void setList(List<ListJobBean> list) {
        this.list = list;
    }
}
