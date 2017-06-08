package cn.gyyx.action.service.lottery;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.ILoggerService;

public interface ILotteryService extends ILoggerService {

	<T> ResultBean<T> getPrizes(int activityId, String userId, String ip);
}
