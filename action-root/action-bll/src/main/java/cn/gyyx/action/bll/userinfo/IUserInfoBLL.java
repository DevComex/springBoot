package cn.gyyx.action.bll.userinfo;

import org.apache.ibatis.session.SqlSession;

public interface IUserInfoBLL {
	
	int putScore(int activityId, String userId, int score, SqlSession session);
	
	int getScore(int activityId, String userId);
}
