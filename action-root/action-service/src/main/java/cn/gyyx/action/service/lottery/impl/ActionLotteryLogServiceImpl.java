package cn.gyyx.action.service.lottery.impl;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;
import cn.gyyx.action.service.lottery.IActionLotteryLogService;

public class ActionLotteryLogServiceImpl implements IActionLotteryLogService {

	private IActionLotteryLogBLL actionLotteryLogBLL;
	
	@Override
	public boolean isExsits(ActionLotteryLogPO params) {
		actionLotteryLogBLL = new ActionLotteryLogBLLImpl();
		
		logger.info("ActionLotteryLogServiceImpl->isExsits->params=" + JSON.toJSONString(params));
		
		if (actionLotteryLogBLL.isExsits(params) > 0) {
			logger.info("ActionLotteryLogServiceImpl->isExsits->result=true");
			return false;
		}
		
		logger.info("ActionLotteryLogServiceImpl->isExsits->result=false");
		
		return true;
	}
}
