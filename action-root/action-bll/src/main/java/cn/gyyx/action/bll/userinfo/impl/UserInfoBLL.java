package cn.gyyx.action.bll.userinfo.impl;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.bll.userinfo.IUserInfoBLL;
import cn.gyyx.action.dao.lottery.IActionQualificationDAO;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;

public class UserInfoBLL implements IUserInfoBLL {

	@Override
	public int putScore(int activityId, String userId, int score, SqlSession session) {
		return 1;
	}
	
	@Override
	public int getScore(int activityId, String userId) {
		IActionQualificationDAO dao = new ActionQualificationDAOImpl();
		
		ActionQualificationPO result = dao.getData(userId, activityId);
		if (null != result && null != result.getLotteryScore() && result.getLotteryScore() > 0) {
			return result.getLotteryScore();
		}
		
		return 0;
	}
	
}
