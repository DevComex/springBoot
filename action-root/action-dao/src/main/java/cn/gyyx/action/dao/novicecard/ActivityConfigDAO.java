/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月8日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.novicecard;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: ActivityConfigDAO
 * @Description: TODO 通过code获取活动配置
 * @author jh
 * @date 2014年12月9日 下午12:27:49
 * 
 */
public class ActivityConfigDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ActivityConfigDAO.class);

	/**
	 * 
	 * @日期：2014年12月11日
	 * @Title: selectActivityConfigByhdName
	 * @Description: TODO
	 * @param hdName
	 * @return ActivityConfigBean
	 */

	public ActivityConfigBean selectActivityConfigByhdName(String hdName) {
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		ActivityConfigBean activityConfig = null;
		try {
			ActivityConfigMapper acm = sqlsession
					.getMapper(ActivityConfigMapper.class);
			logger.debug("ActivityConfigMapper", acm);
			activityConfig = acm.selectActivityConfigByhdName(hdName);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			sqlsession.close();
		}
		logger.debug("selectActivityConfigByhdName activityConfig",
				activityConfig);
		return activityConfig;
	}
	public Integer updateActivityConfig(ActivityConfigBean activityConfigBean){
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Integer  result = null;
		try {
			ActivityConfigMapper acm = sqlsession
					.getMapper(ActivityConfigMapper.class);
			logger.debug("ActivityConfigMapper", acm);
			result = acm.updateActivityConfig(activityConfigBean);
			sqlsession.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			sqlsession.close();
		}
		logger.debug("selectActivityConfigByhdName activityConfig",
				result);
		return result;
	}
	
	public void updateActivity(ActivityConfigBean activityConfigBean){
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Integer  result = null;
		try {
			ActivityConfigMapper acm = sqlsession
					.getMapper(ActivityConfigMapper.class);
			logger.debug("ActivityConfigMapper", acm);
			result = acm.updateActivity(activityConfigBean);
			sqlsession.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			sqlsession.close();
		}
		logger.debug("updateActivity activityConfig",
				result);
	}

		
	public void insertActivity(ActivityConfigBean activityConfigBean){
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Integer  result = null;
		try {
			ActivityConfigMapper acm = sqlsession
					.getMapper(ActivityConfigMapper.class);
			logger.debug("insertActivity", acm);
			result = acm.insertActivity(activityConfigBean);
			sqlsession.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			sqlsession.close();
		}
		logger.debug("insertActivity activityConfig",
				result);
	}
	public ActivityConfigBean selectActivityConfigByCode(int code){
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		ActivityConfigBean activityConfig = null;
		try {
			ActivityConfigMapper acm = sqlsession
					.getMapper(ActivityConfigMapper.class);
			logger.debug("ActivityConfigMapper", acm);
			activityConfig = acm.selectActivityConfigByCode(code);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			sqlsession.close();
		}
		logger.debug("selectActivityConfigByCode activityConfig",
				activityConfig);
		return activityConfig;
	}
	
	/**
	 * 获得最近50条活动配置
	 * @return
	 */
	public List<ActivityConfigBean> getActivityConfig(){
		SqlSession sqlsession = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<ActivityConfigBean> activityConfigList = null;
		try {
			ActivityConfigMapper acm = sqlsession
					.getMapper(ActivityConfigMapper.class);
			logger.debug("ActivityConfigMapper", acm);
			activityConfigList = acm.getActivityConfig();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			sqlsession.close();
		}
		logger.debug("getActivityConfig activityConfigList",
				activityConfigList);
		return activityConfigList;
	}
}
