package cn.gyyx.action.bll.lottery;

import java.util.Map;

// 根据用户ID、活动ID获得抽奖次数
public interface ILotteryCountBLL {

	// 根据用户ID、活动ID获得抽奖次数
	Map<String, Integer> getLotteryCountAndMustWinCount(String userId, int activityId);
}
