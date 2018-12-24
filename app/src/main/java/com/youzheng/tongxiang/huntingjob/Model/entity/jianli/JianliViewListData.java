package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

import com.youzheng.tongxiang.huntingjob.Model.entity.Job.InterViewListBean;

import java.util.List;

public class JianliViewListData {
    private Integer count ;
    private int page ;
    private int rows ;
    private List<JianLiViewListBean> list ;

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

    public List<JianLiViewListBean> getList() {
        return list;
    }

    public void setList(List<JianLiViewListBean> list) {
        this.list = list;
    }
}
