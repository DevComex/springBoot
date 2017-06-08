/*------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
* 作者：姜晗
* 联系方式：jianghan@gyyx.cn 
* 创建时间：2014年12月8日 下午4:52:25 
* 版本号：v1.0 
* 本类主要用途描述： 
* 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.novicecard;

import java.util.List;

import cn.gyyx.action.beans.novicecard.ActivityConfigBean;

/** 
* @ClassName: ActivityConfigMapper 
* @Description: TODO 
* 活动配置操作接口
* @author jh 
* @date 2014年12月10日 下午2:52:52 
*  
*/
public interface ActivityConfigMapper {
	
	/**
	 * 
	 * @日期：2014年12月11日
	 * @Title: selectActivityConfigByhdName 
	 * @Description: TODO 
	 * 根据活动名称获取配置信息
	 * @param hdName
	 * @return ActivityConfigBean
	 */
	public ActivityConfigBean selectActivityConfigByCode(int code);
	public ActivityConfigBean selectActivityConfigByhdName(String hdName);
	public Integer updateActivityConfig(ActivityConfigBean activityConfigBean);
	public Integer insertActivity(ActivityConfigBean activityConfigBean);
	public Integer updateActivity(ActivityConfigBean activityConfigBean);

	/**
	 * 获得最近50条活动配置
	 * @return
	 */
	public List<ActivityConfigBean> getActivityConfig();


}
