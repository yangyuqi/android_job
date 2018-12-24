package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

public class SearchJobData {
    private Integer totalPage ;
    private SearchJobBean list ;
    private Integer totalCount ;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public SearchJobBean getList() {
        return list;
    }

    public void setList(SearchJobBean list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
