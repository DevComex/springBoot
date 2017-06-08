package cn.gyyx.action.service.lottery;

import java.util.List;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;

public interface ILotteryPrizesService {
	// 根据活动ID、用户ID获得奖品信息，适用于不要求用户登录
	List<LotteryPrizesVO> getPrizesInfomations(int activityId, String userId);
	
	// 根据活动ID、用户Code、用户ID获得奖品信息，适用于要求用户登录
	List<LotteryPrizesVO> getPrizesInfomations(int activityId, int userCode, String userId);
	
	// 如果没有地址，则创建地址；否则更新。
	ResultBean<String> putAddress(LotteryPrizesVO vo);
}
