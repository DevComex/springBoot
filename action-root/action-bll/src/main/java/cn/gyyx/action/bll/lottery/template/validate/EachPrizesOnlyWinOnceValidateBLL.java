package cn.gyyx.action.bll.lottery.template.validate;

import java.util.List;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;

public class EachPrizesOnlyWinOnceValidateBLL extends DefaultLotteryValidateBLL {

	private IActionLotteryLogBLL actionLotteryLogBLL = new ActionLotteryLogBLLImpl();
	
	// 每个奖品只能中一次
	@Override
	public boolean validate(LotteryValidateVO params) {
		
		ActionLotteryLogPO logParams = new ActionLotteryLogPO();
		logParams.setActivityType(params.getActivityType());
		logParams.setActivityId(params.getActivityId());
		logParams.setUserId(params.getUserId());
		logParams.setPrizeType(params.getPrizeType());
		logParams.setPrizeName(params.getPrizeName());
		
		List<ActionLotteryLogPO> logList = actionLotteryLogBLL.getDataList(logParams);
		if (null != logList && logList.size() > 0) {
			return false;
		}
		
		return true;
	}
}
