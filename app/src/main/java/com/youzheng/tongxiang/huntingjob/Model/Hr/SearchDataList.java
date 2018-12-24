package com.youzheng.tongxiang.huntingjob.Model.Hr;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/5/21.
 */

public class SearchDataList {
    private List<UserJlBean> searchList ;
    private int count ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserJlBean> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<UserJlBean> searchList) {
        this.searchList = searchList;
    }
}
