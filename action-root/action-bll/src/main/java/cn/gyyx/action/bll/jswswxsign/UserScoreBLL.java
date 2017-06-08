/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.jswswxsign;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.jswswxsign.UserScore;
import cn.gyyx.action.dao.jswswxsign.UserScoreDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能用户积分逻辑类
 */
public class UserScoreBLL {
	private UserScoreDAO userScoreDAO = new UserScoreDAO();
	@SuppressWarnings("unused")
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(UserScoreBLL.class);

	/***
	 * 根据openId获取用户当前分数
	 */
	public UserScore getUserScore(String openId){
		UserScore param = new UserScore();
		param.setOpenId(openId);
		UserScore result = userScoreDAO.getUserScore(param);
		return result;
	}

	/***
	 * 新增
	 */
	public int insert(UserScore userScore){
		return userScoreDAO.insertUserScore(userScore);
	}
	
	/***
	 * 新增  带session
	 */
	public int insert(UserScore userScore,SqlSession session){
		return userScoreDAO.insertUserScore(userScore,session);
	}
	
	/***
	 * 更新
	 */
	public int update(UserScore userScore){
		return userScoreDAO.updateUserScore(userScore);
	}
	
	/***
	 * 更新  带session
	 */
	public int update(UserScore userScore,SqlSession session){
		return userScoreDAO.updateUserScore(userScore,session);
	}

	/***
	 * 根据openId获取用户当前分数
	 */
	public UserScore getUserScore(String openId, SqlSession session){
		UserScore param = new UserScore();
		param.setOpenId(openId);
		UserScore result = userScoreDAO.getUserScore(param,session);
		return result;
	}

	/***
	 * 积分清0
	 */
	public void updateUserScoreZero() {
		userScoreDAO.updateUserScoreZero();
	}
	
	/***
	 * 积分清0 带session
	 */
	public void updateUserScoreZero(SqlSession session) {
		userScoreDAO.updateUserScoreZero(session);
	}




}
