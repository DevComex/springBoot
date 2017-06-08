package cn.gyyx.action.bll.lottery;

import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.config.po.ActionLotteryChancePO;
import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public interface ILotteryBLL {

	Logger logger = GYYXLoggerFactory.getLogger(ILotteryBLL.class);
	
	// 设置奖品
	void setPrizesList(List<ActionPrizePO> prizesList);
	
	// 设置奖品概率
	void setProbability(List<ActionLotteryChancePO> probabilityList);
	
	// 抽奖
	ActionPrizePO getPrizes();
}
