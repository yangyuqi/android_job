package com.youzheng.tongxiang.huntingjob.Model.entity.user;

/**
 * Created by qiuweiyu on 2018/2/12.
 */

public class UserInfoDetailsBean {
    private String username ;
    private String nickname ;
    private int userType ;
    private String gender ;
    private int authentication ;
    private String photo ;

    private String  personal ;
    private String hidetype ;
    private int display ;

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getHidetype() {
        return hidetype;
    }

    public void setHidetype(String hidetype) {
        this.hidetype = hidetype;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAuthentication() {
        return authentication;
    }

    public void setAuthentication(int authentication) {
        this.authentication = authentication;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
