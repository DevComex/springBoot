/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月24日上午10:23:08
 * 版本号：v1.0
 * 本类主要用途叙述：清除memcacheService
 */



package cn.gyyx.action.service.lottery;

import java.util.ArrayList;
import java.util.List;

import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.bll.lottery.LotteryMemcacheBll;
import cn.gyyx.action.bll.lottery.UserLotteryBll;

public class CleanMemcacheService {
	/**
	 * 清除缓存
	 * @param actionCode
	 */
	public void cleanMemcache(int actionCode){
		UserLotteryBll userLotteryBll = new UserLotteryBll();
		ContrParmBean controParm = new ContrParmBean();
		List<PrizeBean> listPrizeBean = new ArrayList<PrizeBean>();
		List<OrderAndPrizeBean> listOrderPrizeBean = new ArrayList<OrderAndPrizeBean>();
		LotteryMemcacheBll lotteryMemcacheBll = new LotteryMemcacheBll();
		UserLotteryBll userLotteryBLL = new UserLotteryBll();
		lotteryMemcacheBll.setUserInfoBeanMem(
				userLotteryBLL.getUserLottery(actionCode), actionCode);
		listOrderPrizeBean = userLotteryBLL.getPrizeAndOrderInfo(actionCode);
		lotteryMemcacheBll.setPrizeAndOrderInfonMem(listOrderPrizeBean, actionCode);
		controParm = userLotteryBll.getContrParm(actionCode);
		lotteryMemcacheBll.setContrParmMem(controParm, actionCode);
		listPrizeBean = userLotteryBll.getPrize(actionCode);
		lotteryMemcacheBll.setPrizeBeanMem(listPrizeBean, actionCode);
	}

}
