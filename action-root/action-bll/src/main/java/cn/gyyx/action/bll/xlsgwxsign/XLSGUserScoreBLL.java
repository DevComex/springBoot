/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.bll.xlsgwxsign;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.xlsgwxsign.XlsgUserScore;
import cn.gyyx.action.dao.xlsgwxsign.XlsgUserScoreDAO;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 驯龙三国微信签到功能用户积分逻辑类
 */
public class XLSGUserScoreBLL {
	private XlsgUserScoreDAO userScoreDAO = new XlsgUserScoreDAO();

	/***
	 * 根据openId获取用户当前分数
	 */
	public XlsgUserScore getUserScore(String openId){
		XlsgUserScore param = new XlsgUserScore();
		param.setOpenId(openId);
		XlsgUserScore result = userScoreDAO.getUserScore(param);
		return result;
	}

	/***
	 * 新增
	 */
	public int insert(XlsgUserScore userScore){
		return userScoreDAO.insertUserScore(userScore);
	}
	
	/***
	 * 新增  带session
	 */
	public int insert(XlsgUserScore userScore,SqlSession session){
		return userScoreDAO.insertUserScore(userScore,session);
	}
	
	/***
	 * 更新
	 */
	public int update(XlsgUserScore userScore){
		return userScoreDAO.updateUserScore(userScore);
	}
	
	/***
	 * 更新  带session
	 */
	public int update(XlsgUserScore userScore,SqlSession session){
		return userScoreDAO.updateUserScore(userScore,session);
	}

	/***
	 * 根据openId获取用户当前分数
	 */
	public XlsgUserScore getUserScore(String openId, SqlSession session){
		XlsgUserScore param = new XlsgUserScore();
		param.setOpenId(openId);
		XlsgUserScore result = userScoreDAO.getUserScore(param,session);
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
