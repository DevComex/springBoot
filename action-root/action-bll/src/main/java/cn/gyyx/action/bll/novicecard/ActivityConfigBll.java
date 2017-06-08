/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月9日 上午10:53:18 
 * 版本号：v1.0 
 * 本类主要用途描述： 对活动配置进行操作 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.novicecard;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.dao.novicecard.ActivityConfigDAO;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.Text;

/**
 * @ClassName: ActivityConfigBll
 * @Description: TODO 对活动配置进行操作
 * @author jh
 * @date 2014年12月10日 下午3:04:38
 * 
 */
public class ActivityConfigBll {
	private ActivityConfigDAO acd = new ActivityConfigDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ActivityConfigBll.class);

	/**
	 * 
	 * @日期：2014年12月11日
	 * @Title: getConfigMessage
	 * @Description: TODO
	 * @param hdName
	 * @param isLimit
	 * @return ResultInfo<ActivityConfigBean>
	 */

	public ResultBean<ActivityConfigBean> getConfigMessage(String hdName) {
		logger.debug("hdName", hdName);
		ResultBean<ActivityConfigBean> result = new ResultBean<ActivityConfigBean>(
				false, "未知错误", null);
		logger.debug("hdName", hdName);
		try {
			ActivityConfigBean activityConfig = acd
					.selectActivityConfigByhdName(hdName);
			logger.debug("activityConfig", activityConfig);
			// 检查活动是否开启
			if (activityConfig == null) {
				result.setMessage("没有读取到配置信息！");
				logger.debug("result", result);
				return result;
			}
			// 获取奖品信息和检查类
			if (!Text.isNullOrEmpty(activityConfig.getParas())) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = DataFormat.jsonToObj(
						activityConfig.getParas(), Map.class);
				logger.debug("map", map);
				activityConfig.setCheckType(map.get("CheckType").toString());
			}
			// 检查活动时间
			if (activityConfig.getActivityStart().compareTo(new Date()) > 0) {
				result.setMessage("活动尚未开启，敬请期待！");
				logger.debug("result", result);
				return result;
			}
			if (activityConfig.getActivityEnd().compareTo(new Date()) < 0) {
				result.setMessage("活动已结束，感谢参与！");
				logger.debug("result", result);
				return result;
			}
			result.setProperties(true, "根据活动描述获得活动配置信息成功", activityConfig);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result.setMessage("根据活动描述获得活动配置信息失败");
		}
		logger.debug("result", result);
		return result;
	}
	/**
	 * 获取活动信息
	 * @param hdName
	 * @return
	 */
	public ResultBean<ActivityConfigBean> getConfigMessageByHdName(String hdName) {
		logger.debug("hdName"+ hdName);
		ResultBean<ActivityConfigBean> result = new ResultBean<ActivityConfigBean>(
				false, "未知错误", null);
		logger.debug("hdName", hdName);
		try {
			ActivityConfigBean activityConfig = acd
					.selectActivityConfigByhdName(hdName);
			logger.debug("activityConfig", activityConfig);
			// 检查活动是否开启
			if (activityConfig == null) {
				result.setMessage("没有读取到配置信息！");
				logger.debug("result"+result);
				return result;
			}
			result.setProperties(true, "根据活动描述获得活动配置信息成功", activityConfig);
		}catch (Exception e) {
			logger.warn(e.getMessage());
			result.setMessage("根据活动描述获得活动配置信息失败");
		}
		logger.debug("result", result);
		return result;
	}
	public Integer closeActivityConfig(){
		ActivityConfigBean activityConfigBean = new ActivityConfigBean();
		activityConfigBean.setActivityName("炫舞吧抽奖");
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(new Date()); 
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);//让日期加1  
		activityConfigBean.setActivityEnd(calendar.getTime());
		Integer i  = acd.updateActivityConfig(activityConfigBean);
		return i ;
		
	}
	public Integer openActivityConfig(){
		ActivityConfigBean activityConfigBean = new ActivityConfigBean();
		activityConfigBean.setActivityName("炫舞吧抽奖");
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(new Date()); 
		calendar.add(Calendar.YEAR, +2); 
		activityConfigBean.setActivityEnd(calendar.getTime());
		Integer i  = acd.updateActivityConfig(activityConfigBean);
		return i ;
		
	}

	public void insertActivity(ActivityConfigBean activityConfigBean){
		acd.insertActivity(activityConfigBean);
	}
	public void updateActivity(ActivityConfigBean activityConfigBean){
		acd.updateActivity(activityConfigBean);
	}
	public ActivityConfigBean selectActivityConfigByCode(int code){
		return acd.selectActivityConfigByCode(code);
	}
	
	/**
	 * 获得最近50条活动配置
	 * @return
	 */
	public List<ActivityConfigBean> getActivityConfig(){
		return acd.getActivityConfig();
	}
}
