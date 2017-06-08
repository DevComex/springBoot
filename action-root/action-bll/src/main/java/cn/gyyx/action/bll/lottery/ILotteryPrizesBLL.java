package cn.gyyx.action.bll.lottery;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.beans.lottery.vo.LotteryPrizesVO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public interface ILotteryPrizesBLL {
	
	Logger logger = GYYXLoggerFactory.getLogger(ILotteryPrizesBLL.class);
	
	List<LotteryPrizesVO> getPrizesInfomations(int activityId, String userId);
	
	int putPrizesNumber(int activityId, int code, SqlSession session) throws Exception;
	
	int putLotteryCountMinusOne(int activityId, String userId, SqlSession session) throws Exception;
	
	int putMustWinCountMinusOne(int activityId, String userId, SqlSession session) throws Exception;

	int putLotteryCountMinusOneByDate(int activityId, String userId,Date createAt ,SqlSession session) throws Exception;
}
