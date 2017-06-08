package cn.gyyx.action.service.lottery;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.service.ILoggerService;

public interface IActionLotteryLogService extends ILoggerService {
	
	// 奖品是否存在
	boolean isExsits(ActionLotteryLogPO params);
}
