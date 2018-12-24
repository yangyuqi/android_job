package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

public class SearchNewDataList {
    private Integer totalPage ;
    private Integer totalCount ;
    private SearchJianliBean list ;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public SearchJianliBean getList() {
        return list;
    }

    public void setList(SearchJianliBean list) {
        this.list = list;
    }
}
