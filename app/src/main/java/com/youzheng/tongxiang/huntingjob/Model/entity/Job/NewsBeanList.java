package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/23.
 */

public class NewsBeanList {
    private String allcount ;
    private List<NewsBeanDetails> news ;

    public String getAllcount() {
        return allcount;
    }

    public void setAllcount(String allcount) {
        this.allcount = allcount;
    }

    public List<NewsBeanDetails> getNews() {
        return news;
    }

    public void setNews(List<NewsBeanDetails> news) {
        this.news = news;
    }
}
