package cn.gyyx.action.service.exchange;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.exchange.vo.ActionExchangeVO;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.service.ILoggerService;

public interface IActionExchangeService extends ILoggerService {
	
	// 兑换奖品
	ResultBean<ActionPrizesAddressPO> exchange(ActionExchangeVO params);
}
