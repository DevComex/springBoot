/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：wd weChat scratchCard
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2015年12月25日
 * @版本号：
 * @本类主要用途描述：获取当前用户当天获奖记录
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.wechat;

import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.dao.wechat.WeiXinSendpresentDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: WeiXinSendpresentBLL
 * @Description: TODO 获取当前用户当天获奖记录
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2015年12月25日 下午4:22:26
 *
 */
public class WeiXinSendpresentBLL {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WeiXinSendpresentBLL.class);
	WeiXinSendpresentDAO weiXinSendpresentDAO = new WeiXinSendpresentDAO();
	
	
	/**
	 * 
	* @Title: getUserInfoByOpenIdAndDate
	* @Description: TODO 获取当前用户当天获奖记录
	* @param @param account
	* @param @param time
	* @param @return    
	* @return List<UserInfoBean>    
	* @throws
	 */
	public List<UserInfoBean> getUserInfoByOpenIdAndDate(String account, String time) {
		logger.debug("account:"+ account);
		List<UserInfoBean> list = weiXinSendpresentDAO.getUserInfoByOpenIdAndDate(account, time);
		logger.debug("list", list);
		return list;
	}
	
	/**
	 * 
	* @Title: getUserCount
	* @Description: TODO 获取当前用户当天获奖记录
	* @param @param actionCode
	* @param @return    
	* @return Integer    
	* @throws
	 */
	public Integer getUserCount(Integer actionCode) {
		logger.debug("actionCode:"+ actionCode);
		return weiXinSendpresentDAO.getUserCount(actionCode);
		
	}
	
	
}
