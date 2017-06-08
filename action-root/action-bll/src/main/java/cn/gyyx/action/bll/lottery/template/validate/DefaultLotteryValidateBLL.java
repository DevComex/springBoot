package cn.gyyx.action.bll.lottery.template.validate;

import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.bll.lottery.ILotteryValidateBLL;

public abstract class DefaultLotteryValidateBLL implements ILotteryValidateBLL {

	protected String message;
	
	protected ActionPrizePO thankYouPrizes = null;
	
	// 获得成功/失败信息
	@Override
	public String getMessage() {
		return message;
	}
}
