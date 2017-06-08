package cn.gyyx.action.service.address.impl;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.service.address.ActionPrizesAddressService;

public class ActionPrizesAddressServiceImpl extends ActionPrizesAddressService {
	
	// 根据活动ID，用户ID，类型查询地址信息
	@Override
	public ResultBean<ActionPrizesAddressPO> get(int activityId, String userId, String activityType) {
		ResultBean<ActionPrizesAddressPO> result = new ResultBean<ActionPrizesAddressPO>();
		result.setIsSuccess(false);
		
		if (activityId < 1) {
			result.setMessage("活动ID不能为空！");
			return result;
		}
		if (null == userId) {
			result.setMessage("用户账号不能为空！");
			return result;
		}
		// 默认为抽奖地址
		if (null == activityType) activityType = ActivityTypeEnums.Lottery.toString();
		
		ActionPrizesAddressPO addressPO = actionPrizesAddressBLL.get(activityId, userId, activityType);
		if (null != addressPO) {
			result.setData(addressPO);
			result.setIsSuccess(true);
		}
		
		return result;
	}
	
}
