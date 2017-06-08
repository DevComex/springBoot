package cn.gyyx.action.service.lottery;

import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

public interface ILotterySendPrizesService {
	
	Logger logger = GYYXLoggerFactory.getLogger(ILotterySendPrizesService.class);
	
	boolean pushPrizes(String userId, String prizesCode);

	String getMessage();
}
