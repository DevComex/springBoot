package cn.gyyx.action.bll.lottery.impl;

import java.util.List;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.bll.lottery.ILotteryValidateBLL;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;

public class LotteryValidateBLLImplByAvailable implements ILotteryValidateBLL {

	private String message;
	private IActionLotteryLogDAO actionLotteryLogDAO = new ActionLotteryLogDAOImpl();
	
	// 抽奖相关校验
	@Override
	public boolean validate(LotteryValidateVO param) {
		
		ActionLotteryLogPO po = new ActionLotteryLogPO();
		po.setActivityType(param.getActivityType() == null ? "lottery" : param.getActivityType());
		po.setActivityId(param.getActivityId());
		po.setUserId(param.getUserId());
		po.setIsAvailable(1);
		
		// 校验是否抽中过有效奖品
		List<ActionLotteryLogPO> poList = actionLotteryLogDAO.getDataList(po);
		if (poList == null || poList.size() == 0) {
			return false;
		}
		
		return true;
	}
		
	// 获得成功/失败信息
	@Override
	public String getMessage() {
		return message;
	}
}
