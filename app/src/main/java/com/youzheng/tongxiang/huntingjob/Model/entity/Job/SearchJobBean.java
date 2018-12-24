package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/20.
 */

public class SearchJobBean {
    private List<JobBeanDetails> recentList ;
    private List<JobBeanDetails> vo_list ;
    private Integer pages ;
    private Integer rows ;
    private Integer currentPage ;
    private Integer pageSize ;

    public List<JobBeanDetails> getVo_list() {
        return vo_list;
    }

    public void setVo_list(List<JobBeanDetails> vo_list) {
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

    public List<JobBeanDetails> getRecentList() {
        return recentList;
    }

    public void setRecentList(List<JobBeanDetails> recentList) {
        this.recentList = recentList;
    }
}
