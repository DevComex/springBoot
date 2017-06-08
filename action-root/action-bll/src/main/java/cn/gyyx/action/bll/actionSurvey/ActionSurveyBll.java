package cn.gyyx.action.bll.actionSurvey;

import org.slf4j.Logger;

import cn.gyyx.action.beans.actionsurvey.ActionSurveyInfoBean;
import cn.gyyx.action.dao.actionSurvey.ActionSurveyInfoDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ActionSurveyBll {
	private ActionSurveyInfoDao actionSurveyInfoDao =new ActionSurveyInfoDao();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ActionSurveyBll.class);

	/**
	 * 
	 * @Title: addSurveyInfoBean
	 * @Description: TODO 记录调查问卷信息
	 * @param @param surveyInfoBean
	 * @return void
	 * @throws
	 */
	public void addSurveyInfoBean(ActionSurveyInfoBean actionSurveyInfoBean) {
		logger.debug("ActionSurveyInfoBean" + actionSurveyInfoBean);
		actionSurveyInfoDao.insertSurveyInfoBean(actionSurveyInfoBean);
	}

	/**
	 * 
	* @Title: getInfoByPhoneNum
	* @Description: TODO 查询是否提交过调查问卷
	* @param @param surveyInfoBean
	* @param @return    
	* @return SurveyInfoBean    
	* @throws
	 */
	public ActionSurveyInfoBean getInfoByPhoneNum(ActionSurveyInfoBean actionSurveyInfoBean) {
		logger.debug("surveyInfoBean" + actionSurveyInfoBean);
		return actionSurveyInfoDao.getInfoByPhoneNum(actionSurveyInfoBean);
	}
}
