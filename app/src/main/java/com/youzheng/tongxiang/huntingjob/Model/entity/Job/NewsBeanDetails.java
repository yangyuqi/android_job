package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

/**
 * Created by qiuweiyu on 2018/2/23.
 */

public class NewsBeanDetails {
    private String id ;
    private String cid ;
    private String title ;
    private String create_time ;
    private int read_count ;
    private String photo ;
    private boolean unwrappingSerializer ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isUnwrappingSerializer() {
        return unwrappingSerializer;
    }

    public void setUnwrappingSerializer(boolean unwrappingSerializer) {
        this.unwrappingSerializer = unwrappingSerializer;
    }
}
