package cn.gyyx.action.bll.lottery;

import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.vo.LotteryValidateVO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public interface ILotteryValidateBLL {

	Logger logger = GYYXLoggerFactory.getLogger(ILotteryValidateBLL.class);
	
	// 抽奖相关校验
	boolean validate(LotteryValidateVO param);
	
	// 获得成功/失败信息
	String getMessage();
}
