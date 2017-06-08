/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.wd9yearnovicebag;

import cn.gyyx.action.beans.wd9yearnovicebag.BagActivityConfigBean;

/** 
* @ClassName: ActivityConfigMapper 
* @Description: TODO 
* 活动配置操作接口
* @author jh 
* @date 2014年12月10日 下午2:52:52 
*  
*/
public interface BagActivityConfigMapper {
	
	/**
	 * 
	 * @日期：2014年12月11日
	 * @Title: selectActivityConfigByhdName 
	 * @Description: TODO 
	 * 根据活动名称获取配置信息
	 * @param hdName
	 * @return ActivityConfigBean
	 */
	
	public BagActivityConfigBean selectActivityConfigByhdName(String hdName);

}
