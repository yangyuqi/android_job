package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

import com.youzheng.tongxiang.huntingjob.Model.Hr.UserJlBean;

import java.util.List;

public class SearchJianliBean {
    private List<UserJlBean>  vo_list ;
    private Integer pages ;
    private Integer rows ;
    private Integer currentPage ;
    private Integer pageSize ;

    public List<UserJlBean> getVo_list() {
        return vo_list;
    }

    public void setVo_list(List<UserJlBean> vo_list) {
        this.vo_list = vo_list;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
