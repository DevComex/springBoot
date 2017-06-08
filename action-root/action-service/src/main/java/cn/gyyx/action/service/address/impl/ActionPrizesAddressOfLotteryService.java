package cn.gyyx.action.service.address.impl;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.address.vo.ActionPrizesAddressOfExchangeVO;
import cn.gyyx.action.beans.address.vo.ActionPrizesAddressOfLotteryVO;
import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.service.address.ActionPrizesAddressService;
import cn.gyyx.distribute.lock.DistributedLock;

public class ActionPrizesAddressOfLotteryService extends ActionPrizesAddressService {

	// 根据活动ID，用户ID，类型查询地址信息
	@Override
	public ResultBean<ActionPrizesAddressOfLotteryVO> get(int activityId, String userId, String activityType) {
		ResultBean<ActionPrizesAddressOfLotteryVO> result = new ResultBean<ActionPrizesAddressOfLotteryVO>();
		result.setIsSuccess(false);
		
		logger.info("ActionPrizesAddressOfLotteryService->activityId=" + activityId + ";userId=" + userId + ";activityType=" + activityType);
		
		// 防止同一用户重复提交
		try(DistributedLock lock = new DistributedLock(activityId + "-" + userId + "-address-exchange-service")) {
			lock.weakLock(30, 0);
			
			ActionPrizesAddressOfLotteryVO lotteryVO = null;
			
			// 获得兑换奖品地址
			ActionPrizesAddressPO addressResult = actionPrizesAddressBLL.get(activityId, userId, (StringUtils.isEmpty(activityType) ? ActivityTypeEnums.Lottery.toString() : activityType));
			logger.info("ActionPrizesAddressOfLotteryService->ActionPrizesAddressPO=" + JSON.toJSONString(addressResult));
			
			if (null != addressResult) {
				lotteryVO = JSON.parseObject(JSON.toJSONString(addressResult), ActionPrizesAddressOfLotteryVO.class);
			}
			
			if (null == lotteryVO) lotteryVO = new ActionPrizesAddressOfLotteryVO();
			
			result.setData(lotteryVO);
			result.setIsSuccess(true);
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressOfLotteryService->get->Cause:" + e.getCause());
			logger.error("ActionPrizesAddressOfLotteryService->get->Message:" + e.getMessage());
			logger.error("ActionPrizesAddressOfLotteryService->get->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionPrizesAddressOfLotteryService->result=" + JSON.toJSONString(result));
		
		return result;
	}
		
}
