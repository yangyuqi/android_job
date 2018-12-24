package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

public class InterViewListData {
    private Integer count ;
    private int page ;
    private int rows ;
    private List<InterViewListBean> list ;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<InterViewListBean> getList() {
        return list;
    }

    public void setList(List<InterViewListBean> list) {
        this.list = list;
    }
}
