/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:奖品补充业务逻辑
************************************************/
package cn.gyyx.action.bll.wechat;

import cn.gyyx.action.beans.wechat.LotteryChanceSupplyBean;
import cn.gyyx.action.dao.wechat.LotteryChanceSupplyDao;

public class LotteryChanceSupplyBLL {

	private LotteryChanceSupplyDao lotteryChanceSupplyDao = new LotteryChanceSupplyDao();
	
	/**
	 * 获取补充总数
	 * @param chanceCode
	 * @return
	 */
	public int getSupplyCount(int chanceCode){
		return lotteryChanceSupplyDao.getSupplyCount(chanceCode);
	}
	
	/**
	 * 增加奖品补充信息
	 * @param lotteryChanceSupplyBean
	 */
	public void addPrizeSupply(LotteryChanceSupplyBean lotteryChanceSupplyBean){
		lotteryChanceSupplyDao.addPrizeSupply(lotteryChanceSupplyBean);
	}
	
	/**
	 * 获取最新补充
	 * @param chanceCode
	 * @return
	 */
	public LotteryChanceSupplyBean getTopSupply(int chanceCode){
		return lotteryChanceSupplyDao.getTopSupply(chanceCode);
	}
}
