package cn.gyyx.action.bll.exchange.impl;

import cn.gyyx.action.beans.exchange.po.ActionExchangePO;
import cn.gyyx.action.beans.exchange.vo.ActionExchangeVO;
import cn.gyyx.action.bll.exchange.IActionExchangeValidateBLL;

public class ActionExchangeValidateBLLImplByCount implements IActionExchangeValidateBLL {
	private String message;
	private ActionExchangePO actionExchangePO;
	
	public ActionExchangeValidateBLLImplByCount(ActionExchangePO po) {
		this.actionExchangePO = po;
	}
	
	// 校验兑换资格
	@Override
	public boolean validate(ActionExchangeVO params) {
		if (null == params) {
			message = "参数不能为空！";
		}
		
		if (null == actionExchangePO) {
			message = "兑换奖品不能为空！";
		}
		
		if (null != actionExchangePO.getItemsQuantity() && actionExchangePO.getExchangeCondition() > 0) {
			return true;
		}
		else {
			message = "您来晚了，奖品已经兑换完了！";
		}
		
		return false;
	}
	
	// 获得提示信息
	@Override
	public String getMessage() {
		return message;
	}
}
