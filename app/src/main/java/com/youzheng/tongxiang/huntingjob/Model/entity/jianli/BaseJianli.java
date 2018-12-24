package com.youzheng.tongxiang.huntingjob.Model.entity.jianli;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qiuweiyu on 2018/2/12.
 */

public class BaseJianli implements Serializable{
    private BaseJianliBean resume ;
    private ArrayList<WorkExperenceBean> experienceList ;
    private ArrayList<ProjectBean> projectList ;
    private ArrayList<EducitionBean> educationList ;
    private ArrayList<SkillListBean> skillList ;
    private VideoBean video ;

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public ArrayList<SkillListBean> getSkillList() {
        return skillList;
    }

    public void setSkillList(ArrayList<SkillListBean> skillList) {
        this.skillList = skillList;
    }

    public ArrayList<EducitionBean> getEducationList() {
        return educationList;
    }

    public void setEducationList(ArrayList<EducitionBean> educationList) {
        this.educationList = educationList;
    }

    public ArrayList<ProjectBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList<ProjectBean> projectList) {
        this.projectList = projectList;
    }

    public BaseJianliBean getResume() {
        return resume;
    }

    public void setResume(BaseJianliBean resume) {
        this.resume = resume;
    }

    public ArrayList<WorkExperenceBean> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(ArrayList<WorkExperenceBean> experienceList) {
        this.experienceList = experienceList;
    }
}
