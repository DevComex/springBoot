package cn.gyyx.action.bll.exchange.impl;

import cn.gyyx.action.beans.exchange.po.ActionExchangePO;
import cn.gyyx.action.beans.exchange.vo.ActionExchangeVO;
import cn.gyyx.action.bll.exchange.IActionExchangeValidateBLL;

public class ActionExchangeValidateBLLImplByCondition implements IActionExchangeValidateBLL {
	private String message;
	private ActionExchangePO actionExchangePO;
	
	public ActionExchangeValidateBLLImplByCondition(ActionExchangePO po) {
		this.actionExchangePO = po;
	}
	
	// 校验兑换资格
	@Override
	public boolean validate(ActionExchangeVO params) {
		if (null == params || null == params.getUserPoint()) {
			message = "参数不能为空！";
		}
		
		if (null == actionExchangePO || null == actionExchangePO.getExchangeCondition()) {
			message = "兑换奖品不能为空！";
		}
		
		if (params.getUserPoint() >= actionExchangePO.getExchangeCondition()) {
			return true;
		}
		else {
			message = "您的积分不够兑换！";
		}
		
		return false;
	}
	
	// 获得提示信息
	@Override
	public String getMessage() {
		return message;
	}
}
