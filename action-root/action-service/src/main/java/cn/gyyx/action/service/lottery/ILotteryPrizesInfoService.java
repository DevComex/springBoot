package cn.gyyx.action.service.lottery;

import cn.gyyx.action.beans.ResultBean;

public interface ILotteryPrizesInfoService {

	<T> ResultBean<T> get(int activityId, String userId);
}
