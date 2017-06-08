package cn.gyyx.action.bll.wechat;

import cn.gyyx.action.beans.wechat.WechatPresentLogBean;
import cn.gyyx.action.dao.wechat.WechatSendPresentLogDAO;

public class WechatSendPresentLogBLL  {
	private WechatSendPresentLogDAO wechatSendPresentLogDAO = new WechatSendPresentLogDAO();
	
	/**
	 * 获取已抽奖品的数量
	 * @param wechatPresentLogBean
	 * @return
	 */
	public int getLotteryPrizeCount(WechatPresentLogBean wechatPresentLogBean){
		return wechatSendPresentLogDAO.getLotteryPrizeCount(wechatPresentLogBean);
	}
}
