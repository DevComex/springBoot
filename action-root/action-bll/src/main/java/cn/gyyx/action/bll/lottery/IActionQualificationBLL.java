package cn.gyyx.action.bll.lottery;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

public interface IActionQualificationBLL {
	
	Logger logger = GYYXLoggerFactory.getLogger(IActionQualificationBLL.class);
	
	int getScore(int activityId, String userId);
	
	int addLotteryScore(int activityId, String userId, int score, String type, String ip, SqlSession session);
	
	int minusLotteryScore(int activityId, String userId, int score, String type, String ip, SqlSession session);

	boolean getState(int activityId, String userId, String type);
}
