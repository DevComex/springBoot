package cn.gyyx.action.dao.wechat;

import cn.gyyx.action.beans.wechat.WechatPresentLogBean;



public interface IWechatSendPresentLogMapper {
	/**
	 * 获取已抽奖品的数量
	 * @param wechatPresentLogBean
	 * @return
	 */
	public int getLotteryPrizeCount(WechatPresentLogBean wechatPresentLogBean);
}
