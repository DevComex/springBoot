package cn.gyyx.action.bll.lottery.impl;

import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.action.bll.lottery.ILotteryValidateBLL;
import cn.gyyx.action.dao.lottery.IActionQualificationDAO;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;

public class LotteryValidateBLLImplByCount implements ILotteryValidateBLL {

	private String message;
	private IActionQualificationDAO actionQualificationDAO = new ActionQualificationDAOImpl();
	
	// 抽奖相关校验
	@Override
	public boolean validate(LotteryValidateVO param) {
		// 校验抽奖次数
		if (actionQualificationDAO.getLotteryCount(param.getUserId(), param.getActivityId()) <= 0) {
			message = "抽奖次数已用完！";
			return false;
		}
		
		return true;
	}
		
	// 获得成功/失败信息
	public String getMessage() {
		return message;
	}
}
