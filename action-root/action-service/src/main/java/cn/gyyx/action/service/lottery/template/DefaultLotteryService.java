package cn.gyyx.action.service.lottery.template;

import java.util.Calendar;
import java.util.List;

import cn.gyyx.action.beans.config.po.ActionPrizePO;
import cn.gyyx.action.beans.enums.PrizesTypeEnums;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.service.lottery.ILotteryService;

public abstract class DefaultLotteryService implements ILotteryService {

	protected ActionPrizePO thankYouPrizes = null;
	
	// 抽奖日志实体
	protected ActionLotteryLogPO getActionLotteryLogPO(String activityType, String userId, String cardCode, String ip, ActionPrizePO params) {
		ActionLotteryLogPO result = new ActionLotteryLogPO();
		result.setActivityType(activityType);
		result.setActivityId(params.getActionCode());
		result.setUserId(userId);
		result.setServerId("2");
		result.setPrizeType(params.getIsReal());
		result.setPrizeCode(params.getCode());
		result.setPrizeName(params.getChinese());
		result.setPrizeNum(params.getNum());
		result.setCardCode(cardCode);
		result.setWinningAt(Calendar.getInstance().getTime());
		result.setWinningIp(ip);
		result.setIsAvailable(params.getIsAvailable());
		
		return result;
	}
	
	// 返回谢谢参与
	protected ActionPrizePO getThankYouPrizes(int activityId, List<ActionPrizePO> paramList) {
		if (null == this.thankYouPrizes) {
			for (ActionPrizePO item : paramList) {
				if (item.getIsReal().equalsIgnoreCase(PrizesTypeEnums.ThankYou.toString())) {
					this.thankYouPrizes = item;
					break;
				}
			}
		}
		
		if (null == this.thankYouPrizes) {
			thankYouPrizes = new ActionPrizePO();
			thankYouPrizes.setActionCode(activityId);
			thankYouPrizes.setChinese("谢谢参与");
			thankYouPrizes.setEnglish(PrizesTypeEnums.ThankYou.toString());
			thankYouPrizes.setIsReal(PrizesTypeEnums.ThankYou.toString());
			thankYouPrizes.setNum(0);
			thankYouPrizes.setIsAvailable(0);
			thankYouPrizes.setServicePrizesCode("");
		}
		
		return this.thankYouPrizes;
	}
}
