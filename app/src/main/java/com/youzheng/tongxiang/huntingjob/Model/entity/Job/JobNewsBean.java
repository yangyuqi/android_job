package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

/**
 * Created by qiuweiyu on 2018/2/21.
 */

public class JobNewsBean {
    private int id ;
    private String title ;
    private String date ;
    private int looktime ;
    private String photo ;
    private String needcontext ;

    private String context ;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLooktime() {
        return looktime;
    }

    public void setLooktime(int looktime) {
        this.looktime = looktime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNeedcontext() {
        return needcontext;
    }

    public void setNeedcontext(String needcontext) {
        this.needcontext = needcontext;
    }
}
