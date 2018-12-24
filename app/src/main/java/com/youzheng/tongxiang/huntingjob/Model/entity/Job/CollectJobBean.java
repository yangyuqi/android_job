package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/14.
 */

public class CollectJobBean {
    private int count ;
    private List<JobBeanDetails> collectList ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<JobBeanDetails> getCollectList() {
        return collectList;
    }

    public void setCollectList(List<JobBeanDetails> collectList) {
        this.collectList = collectList;
    }
}
