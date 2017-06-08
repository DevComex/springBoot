package cn.gyyx.action.dao.actionSurvey;

import cn.gyyx.action.beans.actionsurvey.ActionSurveyInfoBean;


public interface IActionSurveyInfoBeanMapper {
	public void insertSurveyInfoBean(ActionSurveyInfoBean actionSurveyInfoBean);
	public ActionSurveyInfoBean getInfoByPhoneNum(ActionSurveyInfoBean actionSurveyInfoBean);
	
}
