package cn.gyyx.action.bll.exchange;

import cn.gyyx.action.beans.exchange.vo.ActionExchangeVO;

public interface IActionExchangeValidateBLL {

	// 校验兑换资格
	boolean validate(ActionExchangeVO params);
	
	// 获得提示信息
	String getMessage();
}
