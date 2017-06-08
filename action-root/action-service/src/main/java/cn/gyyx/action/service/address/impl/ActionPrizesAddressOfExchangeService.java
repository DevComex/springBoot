package cn.gyyx.action.service.address.impl;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.address.vo.ActionPrizesAddressOfExchangeVO;
import cn.gyyx.action.beans.enums.ActivityTypeEnums;
import cn.gyyx.action.beans.enums.PrizesTypeEnums;
import cn.gyyx.action.beans.exchange.po.ActionExchangePO;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.bll.exchange.IActionExchangeBLL;
import cn.gyyx.action.bll.exchange.impl.ActionExchangeBLLImpl;
import cn.gyyx.action.bll.lottery.IActionLotteryLogBLL;
import cn.gyyx.action.bll.lottery.impl.ActionLotteryLogBLLImpl;
import cn.gyyx.action.service.address.ActionPrizesAddressService;
import cn.gyyx.distribute.lock.DistributedLock;

public class ActionPrizesAddressOfExchangeService extends ActionPrizesAddressService {

	private IActionExchangeBLL actionExchangeBLL = new ActionExchangeBLLImpl();
	private IActionLotteryLogBLL actionLotteryLogBLL = new ActionLotteryLogBLLImpl();
	
	// 根据活动ID，用户ID，类型查询地址信息
	@Override
	public ResultBean<ActionPrizesAddressOfExchangeVO> get(int activityId, String userId, String activityType) {
		ResultBean<ActionPrizesAddressOfExchangeVO> result = new ResultBean<ActionPrizesAddressOfExchangeVO>();
		result.setIsSuccess(false);
		
		logger.info("ActionPrizesAddressOfExchangeService->activityId=" + activityId + ";userId=" + userId + ";activityType=" + activityType);
		
		// 防止同一用户重复提交
		try(DistributedLock lock = new DistributedLock(activityId + "-" + userId + "-address-exchange-service")) {
			lock.weakLock(30, 0);
			
			// 查询邀请函
			ActionLotteryLogPO logParams = new ActionLotteryLogPO();
			logParams.setActivityId(activityId);
			logParams.setUserId(userId);
			logParams.setPrizeType(PrizesTypeEnums.Invitation.toString());
			
			if (actionLotteryLogBLL.isExsits(logParams) > 0) {
				ActionPrizesAddressOfExchangeVO resultVO = null;
				
				// 获得兑换奖品地址
				ActionPrizesAddressPO addressResult = actionPrizesAddressBLL.get(activityId, userId, (StringUtils.isEmpty(activityType) ? ActivityTypeEnums.Exchange.toString() : activityType));
				logger.info("ActionPrizesAddressOfExchangeService->ActionPrizesAddressPO=" + JSON.toJSONString(addressResult));
				
				if (null != addressResult) {
					// 奖品地址赋值
					resultVO = JSON.parseObject(JSON.toJSONString(addressResult), ActionPrizesAddressOfExchangeVO.class);
					result.setData(resultVO);
				}
				
				result.setIsSuccess(true);
			}
		}
		catch(Exception e) {
			logger.error("ActionPrizesAddressOfExchangeService->get->Cause:" + e.getCause());
			logger.error("ActionPrizesAddressOfExchangeService->get->Message:" + e.getMessage());
			logger.error("ActionPrizesAddressOfExchangeService->get->StackTrace:" + e.getStackTrace());
		}
		
		logger.info("ActionPrizesAddressOfExchangeService->result=" + JSON.toJSONString(result));
		
		return result;
	}
}
