package com.youzheng.tongxiang.huntingjob.UI.Utils;

/**
 * Created by qiuweiyu on 2018/2/11.
 */

public class UrlUtis {

    public static String FILE_URL = "http://101.132.125.42:8080/YoupinApi/";
    public static String BASE_URL = "http://101.132.125.42:8080/YoupinApi/api/app/v1/";

    public static String BASE_LUCENE = "http://101.132.125.42:8910/Lucene/api/app/v1/";

//    public static String FILE_URL = "http://101.132.125.42:8080/YoupinApi/";
//    public static String BASE_URL = "http://101.132.125.42:8080/YoupinApi/api/app/v1/";

    public static String LOGIN_URL =BASE_URL+"user/loginMove";//登录
    public static String REGISTER_URL = BASE_URL+"user/registerMove";//注册
    public static String SEND_CODE = BASE_URL+"user/sendMsg";//短信接口
    public static String BASE_RESUME = BASE_URL+"resume/insertResume";//资料新增
    public static String WORK_TIAOJIAN = BASE_URL+"jobResume/getComClassByCtype";//类别查询下拉列表
    public static String ALL_ADDRESS = BASE_URL+"area/selectAreaAll";//获取地区地址
    public static String TOKEN_LOGIN = BASE_URL+"user/tokenValidate";//access_token登录
    public static String USER_INFO_SEE = BASE_URL+"user/selectPersonInfomation";//个人资料查询
    public static String DELIEVERY_MSG_LIST = BASE_URL+"jobResume/getResumeDeliveryByStatus";//根据状态获取投递职位列表
    public static String LOGIN_OUT = BASE_URL+"user/loginOut";//退出功能
    public static String JOB_DETAILS=BASE_URL+"job/getJobById";//职位详情
    public static String JOB_COLLECT = BASE_URL+"jobResume/saveJobCollect";//收藏职位/简历
    public static String UNSCRIBE_JOB = BASE_URL+"jobResume/deleteJobCollectByUidAndJid";//收藏职位/简历
    public static String TOUJIANLI_JOB = BASE_URL+"jobResume/saveResumeDelivery";//投递简历
    public static String CO_DETAILS = BASE_URL+"company/selectCompanyById";//企业资料查询
    public static String MY_RESUME = BASE_URL + "resume/getResumeDetailById";//我的简历详情
    public static String HOME_INFO = BASE_URL+"job/getHotJobList";//热门职位、最新职位、相似职位、名企
    public static String UPDATE_BASE_INFO = BASE_URL+"resume/updateResumeDetail";//简历修改基本信息
    public static String ALL_TRADE = BASE_URL+"trade/selectTradeAll";//所有行业
    public static String WORK_EXPERENCE= BASE_URL+"resume/addResumeExperience";//我的简历工作经历新增
    public static String UPDATE_EXPERENCE = BASE_URL+"resume/insertResumeExperience";//工作经历新增或修改
    public static String ADD_EDUCITON = BASE_URL+"resume/addResumeEducation";//添加教育经历
    public static String ADD_PROJECT = BASE_URL +"resume/addResumeProject";//添加项目经历
    public static String UPDATE_PROJECT = BASE_URL+"resume/insertResumeProject";//修改简历项目经验
    public static String UPDATE_EDU = BASE_URL+"resume/insertResumeEducation";//
    public static String JINENG_URL = BASE_URL+"resume/insertResumeSkillLanguage";//添加技能
    public static String ADD_SELF_PINGJIA = BASE_URL+"resume/updateResumeSelfDescription";//自我评价
    public static String HOME_INTRODUCE = BASE_URL+"job/getAppRecommendList";//首页相关推荐
    public static String SHOUCAN_URL = BASE_URL+"jobResume/getCollectListByUid";//收藏职位/简历
    public static String GET_CO_JOB=BASE_URL+"job/getAllJobListByUid";//查询该公司所有的职位
    public static String SEARCH_JOB = BASE_URL+"job/getSearchJobList";//职位搜索
    public static String NEW_SEARCH_JOB = BASE_LUCENE+"lucene/searchJobByLucene";//职位搜索

    public static String GET_ALL_CATEGORY=BASE_URL+"trade/selectOccupationAll";//获取所有职能
    public static String SCHOOL_JOB = BASE_URL+"job/getJobListByJobsNature";//招聘会  根据工作性质查询职位列表
    public static String SCHOOL_SPEAK = BASE_URL+"career/getnewcareer";//最新的宣讲会
    public static String SCHOOL_APEAK_DETAIL = BASE_URL+"career/gettalkinfo";//具体宣讲会信息
    public static String SPEAK_GO = BASE_URL+"career/enroll";//报名
    public static String NEWS_DETAILS = BASE_URL+"job/getnewinfo";//资讯详情
    public static String JOB_NEWS = BASE_URL +"job/getnewjobnews";//
    public static String INVITED_JOB_LIST = BASE_URL+"jobResume/getResumeInvitationList";//邀请的职位列表
    public static String GET_MY_SPEAK = BASE_URL+"career/getmycareer";//获取我已经报名的宣讲会信息
    public static String QUERY_BANNER = BASE_URL+"banner/queryBanner";//查询轮播图
    public static String MESSAGE_LIST = BASE_URL+"career/selectNewsList";//消息
    public static String MESSAGE_NEW_LIST = BASE_URL+"message/getAllMessage";
    public static String UPLOAD_FILE = FILE_URL+"uploadFile";//上传文件
    public static String EDIT_PERSIOM_INFO = BASE_URL+"user/alertPersonInfomation";//个人资料修改
    public static String EDIT_RESUME_INFO = BASE_URL+"resume/updateResumePhoto";//上传简历头像
    public static String FIND_PWD = BASE_URL+"user/forgotPasswordMove";//找回密码
    public static String EDIT_PWD = BASE_URL+"user/alertPassWordMove";//修改密码
    public static String GET_NEWS = BASE_URL+"news/getnews";//获取类别下有哪些新闻

    public static String SCREAT_LANCHER = BASE_URL+"resume/getResumeHideByAccessToken";
    public static String OPEN_SECRET = BASE_URL+"resume/updateResumeFilter";//用户开放/隐藏 简历隐私
    public static String WHO_SEE_ME = BASE_URL+"jobResume/getWhoSeeMeByUserList";//谁看过我
    public static String ALL_BLACK = BASE_URL+"resume/getallblack";//黑名单中公司列表
    public static String ADD_BLACK = BASE_URL+"resume/addblack";//添加企业至黑名单
    public static String SEARCH_BLAVK=BASE_URL+"company/searchCompanyByName";//搜索公司(屏蔽公司)
    public static String DELETE_BLACK_CO=BASE_URL+"resume/deleteblack";//删除黑名单企业
    public static String TONGXIANG_ADDRESS=BASE_URL+"area/selectAreaTx";//浙江那一块的地区信息

    public static String ADD_COMPANY=BASE_URL+"company/addBaseCompany";//117.新增企业基本信息
    public static String CO_HOME=BASE_URL+"resume/getRecommendResumeList";//分页获取推荐的简历列表
    public static String JOB_PUBLISH=BASE_URL+"job/publish";//发布职位
    public static String  SEARCH_JIANLI = BASE_URL+"resume/getSearchResumeList";//搜索条件分页获取简历列表

    public static String  NEW_SEARCH_JIANLI = BASE_LUCENE+"lucene/searchResumeByLucene";//搜索条件分页获取简历列表

    public static String UPDATE_STATE=BASE_URL+"job/updateJobStateById";//更新职位状态
    public static String HANDLER_JIANLI=BASE_URL+"jobResume/getResumeByDeliveryTime";//根据投递时间查询收到的简历列表
    public static String SEE_CO_INFO=BASE_URL+"company/selectCompanyByUid";//企业资料
    public static String ADD_AND_UPDATE_JOB=BASE_URL+"company/addOrUpdateCompany";//企业资料新增或修改
    public static String CO_COLLECT_JL=BASE_URL+"jobResume/getCompanyCollectListByUid";//企业查询收藏的简历列表
    public static String ATTENTION_CO=BASE_URL+"company/authentication";//企业认证
    public static String UPDATE_CO_ATTENTION = BASE_URL+"company/updateAuthentication";//上传企业认证信息
    public static String UPDATE_DELIVERY = BASE_URL+"jobResume/updateDeliveryById";//更新投递状态
    public static String INVITE_JIANLI = BASE_URL+"jobResume/getInvitationListByUid";//根据邀请时间查询邀请的简历列表
    public static String SCAN_JIANLI=BASE_URL+"jobResume/getReadList";//浏览过的简历
    public static String DELETE_INVITE=BASE_URL+"jobResume/deleteInvitationById";//删除邀请
    public static String GET_HOT_CO=BASE_URL+"company/selectHotCompany";//获取热门公司
    public static String GET_HR_CO_JOB = BASE_URL+"job/getCompanyJob";//企业查看发布的职位用于查看简历直接邀请面试

    public static String HELP_AND_REBACK=BASE_URL+"help/queryAllHelpByType";//帮助中心
    public static String REBACK_DETAILS=BASE_URL+"help/queryHelpDeatilById";//反馈详情
    public static String ADD_REBACK = BASE_URL+"feedback/addFeedback";//添加反馈
    public static String MY_REBACK_LSIT=BASE_URL+"feedback/queryAllFeedback";//反馈
    public static String UPDATE_VERSION=BASE_URL+"system/checkVersion";//版本更新
    public static String NEW_DETAILS_CATEGORY=BASE_URL+"news/readnew";

    public static String CO_COLLECT_LIST = BASE_URL+"favorites/selectFavorites";//企业查询收藏夹企业查询收藏夹c
    public static String ADD_CO_COLLECT_LIST = BASE_URL+"favorites/insertFavorites";//企业新建收藏夹
    public static String EDIT_CO_COLLECT_LIST = BASE_URL+"favorites/updateFavorites";//企业修改收藏夹
    public static String YOUHUO_LABELS = BASE_URL+"job/getAllJobLured";//所有职位诱惑
    public static String GET_CO_COLLECT_LIST = BASE_URL+"favorites/selectListFavorites";//企业查询收藏夹列表并获取该列表下的简历
    public static String CO_DELETE_COLLECT = BASE_URL+"favorites/deleteFavorites";//企业删除收藏夹
    public static String DELETE_ALL_REVIEW = BASE_URL+"jobResume/deleteJobCollectByCollectId";//批量删除
    public static String MOVE_REVIEW_ALL = BASE_URL+"jobResume/moveCollectList";//批量移动收藏至另一个收藏夹

    public static String SEE_USER_INFO = BASE_URL+"user/selectUserInfo";//查看用户信息
    public static String UPDATE_USER_INFO = BASE_URL+"user/updateUserInfo";//修改用户信息
    public static String CO_GET_WHO_SEE_ME = BASE_URL+"jobResume/getWhoSeeMeList";//

    public static String UPLOAD_VIDEO = BASE_URL+"video/uploadVideo";//上传视频
    public static String FIND_JOB_VIDEO= BASE_URL+"video/getLatestJobVideo";//职位视频
    public static String RESUME_LIST_VIDEO = BASE_URL+"video/getLatestResumeVideo";//简历视频
    public static String VIDEO_ADD = BASE_URL+"video/updateVideoPageView";//视频浏览数+1

    public static String CODE_LOGIN = BASE_URL+"user/loginCode";//验证码dengl

    public static String DELETE_WORK_YEAR = BASE_URL+"resume/deleteResumeExperienceById";
    public static String DELETE_PROJECT_YEAR = BASE_URL+"resume/deleteResumeProjectById";
    public static String DELETE_EDUCTION_YEAR = BASE_URL+"resume/deleteResumeEducationById";
    public static String DELETE_JIENG_ = BASE_URL+"resume/deleteResumeSkillLanguageById";
    public static String INTRO_LIST_VIDEO= BASE_URL+"system/queryVideoModel";
}
