package cn.gyyx.action.dao.wechat;

import cn.gyyx.action.beans.wechat.LotteryChanceSupplyBean;


public interface ILotteryChanceSupplyMapper {
	/**
	 * 获取补充总数
	 * @param chanceCode
	 * @return
	 */
	public int getSupplyCount(int chanceCode);
	/**
	 * 增加奖品补充信息
	 * @param lotteryChanceSupplyBean
	 */
	public void addPrizeSupply(LotteryChanceSupplyBean lotteryChanceSupplyBean);
	/**
	 * 获取最新补充
	 * @param chanceCode
	 * @return
	 */
	public LotteryChanceSupplyBean getTopSupply(int chanceCode);
}
