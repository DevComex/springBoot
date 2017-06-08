/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：玩家元宝信息业务逻辑
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.online;

import org.slf4j.Logger;

import cn.gyyx.action.beans.online.OnlineChangeBean;
import cn.gyyx.action.dao.online.OnlineChangeDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class OnlineChangeBll {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OnlineChangeBll.class);
	private OnlineChangeDAO onlineChangeDAO = new OnlineChangeDAO();
	
	/**
	 * 查询用户的资格
	 * @param account
	 * @return
	 */
	public OnlineChangeBean getAccount(String account) {
		return onlineChangeDAO.selectAccount(account);
	}

}
