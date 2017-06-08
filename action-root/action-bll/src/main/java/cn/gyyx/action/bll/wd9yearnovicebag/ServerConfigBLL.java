/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.bll.wd9yearnovicebag;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wd9yearnovicebag.ActionServerConfigBean;
import cn.gyyx.action.beans.wd9yearnovicebag.ServerBean;
import cn.gyyx.action.dao.wd9yearnovicebag.ServerConfigDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ServerConfigBLL {

	private ServerConfigDAO serverConfigDAO = new ServerConfigDAO();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(BagActivityConfigBll.class);
	private ActionServerConfigBean actionServerConfigBean = new ActionServerConfigBean();

	/**
	 * @Title: setReceiveLog
	 * @Author: zhouzhongkai wanglei
	 * @date 2015年03月27日 下午7:12:45
	 * @Description: TODO 获取服务期
	 * @param param
	 * 
	 */
	public List<ServerBean> getServerConfig(Integer activityId) {
		logger.debug("activityId", activityId);
		List<ServerBean> result = new ArrayList<ServerBean>();
		List<String> areaList = serverConfigDAO
				.selectServerConfigInfo(activityId);
		List<ActionServerConfigBean> list = new ArrayList<ActionServerConfigBean>();
		if (areaList != null) {
			int i = 0;
			for (String area : areaList) {
				actionServerConfigBean.setActivityId(activityId);
				actionServerConfigBean.setAreaName(area);
				list = serverConfigDAO
						.selectServerConfigInfoBy(actionServerConfigBean);
				result.add(new ServerBean(i, area, list));
				i++;
				list = new ArrayList<ActionServerConfigBean>();
			}
		} else {
			return null;
		}
		logger.debug("result", result);
		return result;

	}
}
