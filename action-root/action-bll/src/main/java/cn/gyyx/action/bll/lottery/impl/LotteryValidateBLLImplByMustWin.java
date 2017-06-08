package cn.gyyx.action.bll.lottery.impl;

import java.util.List;

import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.bll.lottery.ILotteryValidateBLL;
import cn.gyyx.action.dao.lottery.IActionLotteryLogDAO;
import cn.gyyx.action.dao.lottery.IActionQualificationDAO;
import cn.gyyx.action.dao.lottery.impl.ActionLotteryLogDAOImpl;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;

public class LotteryValidateBLLImplByMustWin implements ILotteryValidateBLL {

	private String message;
	private IActionQualificationDAO actionQualificationDAO = new ActionQualificationDAOImpl();
	private IActionLotteryLogDAO actionLotteryLogDAO = new ActionLotteryLogDAOImpl();
	
	// 抽奖相关校验
	@Override
	public boolean validate(LotteryValidateVO param) {
		// 校验必中奖券次数
		if (actionQualificationDAO.getMustWinCount(param.getUserId(), param.getActivityId()) <= 0) {
			message = "没有必中奖券可用！";
			return false;
		}
		
		// 使用必中奖券是否中奖过
		ActionLotteryLogPO po = new ActionLotteryLogPO();
		po.setActivityType(param.getActivityType() == null ? "lottery" : param.getActivityType());
		po.setActivityId(param.getActivityId());
		po.setUserId(param.getUserId());
		po.setIsAvailable(1);
		po.setRemark("MustWin");
		
		List<ActionLotteryLogPO> poList = actionLotteryLogDAO.getDataList(po);
		if (poList != null && poList.size() > 0) {
			message = "已经使用过必中券！";
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
