package cn.gyyx.action.bll.lottery.impl;

import java.math.BigDecimal;
import java.util.List;

import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;
import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.bll.lottery.ILotteryBLL;
import cn.gyyx.action.dao.config.IActionLotteryChanceDAO;
import cn.gyyx.action.dao.config.IActionPrizeDAO;
import cn.gyyx.action.dao.config.Impl.ActionLotteryChanceDAOImpl;
import cn.gyyx.action.dao.config.Impl.ActionPrizeDAOImpl;

public class LotteryBLLImpl implements ILotteryBLL  {
	
	private List<ActionPrizePO> prizesList = null;
	private List<ActionLotteryChancePO> probabilityList = null;
	
	// 设置奖品
	@Override
	public void setPrizesList(List<ActionPrizePO> prizesList) {
		this.prizesList = prizesList;
	}
	
	// 设置奖品概率
	@Override
	public void setProbability(List<ActionLotteryChancePO> probabilityList) {
		this.probabilityList = probabilityList;
	}
	
	// 抽奖
	@Override
	public ActionPrizePO getPrizes() {
		ActionPrizePO result = null;
		
		// 奖品的概率
		if (this.probabilityList == null || this.probabilityList.size() == 0) return null;
		
		// 抽奖
		int prizesIndex = this.doLottery(this.probabilityList);
		if (prizesIndex < 0) return null;  // 没有抽到奖品
		
		// 奖品信息
		if (this.prizesList == null || this.prizesList.size() == 0) return null;
		
		// 根据奖品ID获得奖品
		for(ActionPrizePO item : this.prizesList) {
			if (prizesIndex == item.getCode()) {
				result = item;
				break;
			}
		}
		
		return result;
	}
	
	// 抽奖
	protected int doLottery(List<ActionLotteryChancePO> dataList) {
		int result = -100;
		
		if (dataList == null || dataList.size() == 0) return result;
		
		BigDecimal total = BigDecimal.ZERO;
		for (ActionLotteryChancePO item : dataList) {
			total = total.add(item.getProbability() == null ? BigDecimal.ZERO : item.getProbability());
		}
		
		// 如果奖品概率总和小于1，则总和等于1
		if (total.compareTo(BigDecimal.ONE) < 0) {
			total = BigDecimal.ONE;
		}
		
		// 产生随机数
		BigDecimal randomNumber = BigDecimal.valueOf(Math.random());
		
		// 计算概率分布
		BigDecimal rangeStart = BigDecimal.ZERO;
		BigDecimal rangeEnd = BigDecimal.ZERO;
		for (int i = 0; i < dataList.size(); i++) {
			rangeStart = rangeEnd;
			rangeEnd = rangeEnd.add(dataList.get(i).getProbability().divide(total, BigDecimal.ROUND_HALF_EVEN));
			
			// 随机数落在奖品分布概率区间就中奖
			if (randomNumber.compareTo(rangeStart) >= 0 && randomNumber.compareTo(rangeEnd) < 0) {
				result = dataList.get(i).getPrizeCode();
				break;
			}
		}
		
		return result;
	}
}
