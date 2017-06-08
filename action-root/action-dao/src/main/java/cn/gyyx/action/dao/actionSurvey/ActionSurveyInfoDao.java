package cn.gyyx.action.dao.actionSurvey;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import cn.gyyx.action.beans.actionsurvey.ActionSurveyInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;


public class ActionSurveyInfoDao {
	SqlSessionFactory factory = MyBatisConnectionFactory
			.getSqlActionDBV2SessionFactory();

	/**
	 * 
	 * @Title: insertSurveyInfoBean
	 * @Description: TODO 增加问卷调查记录
	 * @param @param surveyInfoBean
	 * @return void
	 * @throws
	 */
	public void insertSurveyInfoBean(ActionSurveyInfoBean actionSurveyInfoBean) {
		try (SqlSession session = factory.openSession()) {
			IActionSurveyInfoBeanMapper mapper = session
					.getMapper(IActionSurveyInfoBeanMapper.class);
			mapper.insertSurveyInfoBean(actionSurveyInfoBean);
			session.commit();
		}
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
		try (SqlSession session = factory.openSession()) {
			IActionSurveyInfoBeanMapper mapper = session
					.getMapper(IActionSurveyInfoBeanMapper.class);
			return mapper.getInfoByPhoneNum(actionSurveyInfoBean);
		}
	}
}
