package cn.gyyx.action.service.lottery.template;

import java.util.List;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.address.vo.ActionPrizesAddressOfLotteryVO;
import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;
import cn.gyyx.action.service.lottery.ILotteryPrizesInfoService;

public class DefaultLotteryPrizesInfoService implements ILotteryPrizesInfoService {

	private IActionLotteryLogBLL actionLotteryLogBLL = new ActionLotteryLogBLLImpl();
	
	public ResultBean<ActionLotteryLogPO> get(int activityId, String userId) {
		ResultBean<ActionLotteryLogPO> result = new ResultBean<ActionLotteryLogPO>();
		result.setIsSuccess(false);
		
		ActionLotteryLogPO logParams = new ActionLotteryLogPO();
		logParams.setActivityId(activityId);
		logParams.setUserId(userId);
		
		List<ActionLotteryLogPO> poList = actionLotteryLogBLL.getDataExceptThankYou(logParams);
		if (null != poList && poList.size() > 0) {
			result.setRows(poList);
			result.setIsSuccess(true);
		}
		
		return result;
	}
}
