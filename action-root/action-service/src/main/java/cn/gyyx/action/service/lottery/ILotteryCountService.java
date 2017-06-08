package cn.gyyx.action.service.lottery;

import java.util.Map;

// 获得用户抽奖次数接口
public interface ILotteryCountService {

	// 根据用户ID查询抽奖次数
	Map<String, Integer> getLotteryCountAndMustWinCount(String userId, int activityId);
}
