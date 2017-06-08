package cn.gyyx.action.service.lottery.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.gyyx.action.bll.lottery.ILotteryCountBLL;
import cn.gyyx.action.bll.lottery.impl.LotteryCountBLLImpl;
import cn.gyyx.action.service.lottery.ILotteryCountService;

//获得用户抽奖次数接口
@Service
public class LotteryCountService implements ILotteryCountService {

	private ILotteryCountBLL lotteryCountBLL = new LotteryCountBLLImpl();;
	
	// 根据用户ID查询抽奖次数、必中奖次数
	public Map<String, Integer> getLotteryCountAndMustWinCount(String userId, int activityId) {
		return lotteryCountBLL.getLotteryCountAndMustWinCount(userId, activityId);
	}
}
