package com.youzheng.tongxiang.huntingjob.Model.entity.Job;

import com.youzheng.tongxiang.huntingjob.Model.entity.deliver.JobTagsBean;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/2/12.
 */

public class JobBeanDetails {
    private int job_type ;
    private String company_type ;
    private String education ;
    private String jobtagid ;
    private String city ;
    private int jobs_natureid ;
    private String neturl ;
    private String description ;
    private String scale ;
    private String citysName ;
    private String title ;
    private String experience ;
    private int uid ;
    private int is_collect ;
    private String jobtag ;
    private int wage_face ;
    private int id ;
    private double wage_min ;
    private String keyword ;
    private String address ;
    private String create_time ;
    private String jobs_nature ;
    private int com_id ;
    private double wage_max ;
    private String requirement ;
    private int is_delivery ;
    private int experienceid ;
    private int educationid ;
    private int count ;
    private String ctype ;
    private String name ;
    private String com_logo ;
    private String jobType ;
    private String tradeName ;
    private String trade ;

    private String com_position ;
    private String com_address ;
    private String mobile_phone ;
    private String contact ;
    private String telphone ;
    private String email ;
    private String position;
    private String jobtypes ;

    private String jobtagId ;
    private String jobTagId ;

    private String citys ;


    private Integer videoUpdated ;
    private String videoUrl ;
    private String videoIndexImg ;
    private int videoPageView ;
    private String videoUpdateTime ;
    private String videoComment ;
    private Integer isCollect ;
    private Integer isDelivery ;

    private float width ;
    private float height ;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    private boolean addNum ;

    public boolean getAddNum() {
        return addNum;
    }

    public void setAddNum(boolean addNum) {
        this.addNum = addNum;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    public Integer getVideoUpdated() {
        return videoUpdated;
    }

    public void setVideoUpdated(Integer videoUpdated) {
        this.videoUpdated = videoUpdated;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoIndexImg() {
        return videoIndexImg;
    }

    public void setVideoIndexImg(String videoIndexImg) {
        this.videoIndexImg = videoIndexImg;
    }

    public int getVideoPageView() {
        return videoPageView;
    }

    public void setVideoPageView(int videoPageView) {
        this.videoPageView = videoPageView;
    }

    public String getVideoUpdateTime() {
        return videoUpdateTime;
    }

    public void setVideoUpdateTime(String videoUpdateTime) {
        this.videoUpdateTime = videoUpdateTime;
    }

    public String getVideoComment() {
        return videoComment;
    }

    public void setVideoComment(String videoComment) {
        this.videoComment = videoComment;
    }

    public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys;
    }

    public String getJobTagId() {
        return jobTagId;
    }

    public void setJobTagId(String jobTagId) {
        this.jobTagId = jobTagId;
    }

    private int deliverNum ;

    private String scaleName ;

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDeliverNum() {
        return deliverNum;
    }

    public void setDeliverNum(int deliverNum) {
        this.deliverNum = deliverNum;
    }

    public String getJobtagId() {
        return jobtagId;
    }

    public void setJobtagId(String jobtagId) {
        this.jobtagId = jobtagId;
    }

    public String getJobtypes() {
        return jobtypes;
    }

    public void setJobtypes(String jobtypes) {
        this.jobtypes = jobtypes;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    private List<JobTagsBean> jobTags ;

    private int state ;

    public List<JobTagsBean> getJobTags() {
        return jobTags;
    }

    public void setJobTags(List<JobTagsBean> jobTags) {
        this.jobTags = jobTags;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getCom_address() {
        return com_address;
    }

    public void setCom_address(String com_address) {
        this.com_address = com_address;
    }

    public String getCom_position() {
        return com_position;
    }

    public void setCom_position(String com_position) {
        this.com_position = com_position;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getJob_type() {
        return job_type;
    }

    public void setJob_type(int job_type) {
        this.job_type = job_type;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJobtagid() {
        return jobtagid;
    }

    public void setJobtagid(String jobtagid) {
        this.jobtagid = jobtagid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getJobs_natureid() {
        return jobs_natureid;
    }

    public void setJobs_natureid(int jobs_natureid) {
        this.jobs_natureid = jobs_natureid;
    }

    public String getNeturl() {
        return neturl;
    }

    public void setNeturl(String neturl) {
        this.neturl = neturl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getCitysName() {
        return citysName;
    }

    public void setCitysName(String citysName) {
        this.citysName = citysName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public String getJobtag() {
        return jobtag;
    }

    public void setJobtag(String jobtag) {
        this.jobtag = jobtag;
    }

    public int getWage_face() {
        return wage_face;
    }

    public void setWage_face(int wage_face) {
        this.wage_face = wage_face;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWage_min() {
        return wage_min;
    }

    public void setWage_min(double wage_min) {
        this.wage_min = wage_min;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getJobs_nature() {
        return jobs_nature;
    }

    public void setJobs_nature(String jobs_nature) {
        this.jobs_nature = jobs_nature;
    }

    public int getCom_id() {
        return com_id;
    }

    public void setCom_id(int com_id) {
        this.com_id = com_id;
    }

    public double getWage_max() {
        return wage_max;
    }

    public void setWage_max(double wage_max) {
        this.wage_max = wage_max;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getIs_delivery() {
        return is_delivery;
    }

    public void setIs_delivery(int is_delivery) {
        this.is_delivery = is_delivery;
    }

    public int getExperienceid() {
        return experienceid;
    }

    public void setExperienceid(int experienceid) {
        this.experienceid = experienceid;
    }

    public int getEducationid() {
        return educationid;
    }

    public void setEducationid(int educationid) {
        this.educationid = educationid;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCom_logo() {
        return com_logo;
    }

    public void setCom_logo(String com_logo) {
        this.com_logo = com_logo;
    }
}
