package cn.gyyx.action.service.address;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.service.ILoggerService;

public interface IActionPrizesAddressService extends ILoggerService {

	// 根据活动ID，用户ID，类型查询地址信息
	<T> ResultBean<T> get(int activityId, String userId, String activityType);
	
	// 保存地址
	ResultBean<String> post(ActionPrizesAddressPO params);
}
