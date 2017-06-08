/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年1月25日下午4:15:26
 * 版本号：v1.0
 * 本类主要用途叙述：绝世武神积分兑换积分的逻辑层
 */

package cn.gyyx.action.bll.jswsexchangeBll;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.jswsexchange.JSWSScoreBean;
import cn.gyyx.action.dao.jswsexchangedao.ScoreDao;

public class ScoreBll {
	private ScoreDao scoreDao = new ScoreDao();

	/***
	 * 根据id获取绝世武神的分数
	 * 
	 * @param openId
	 * @return
	 */
	public JSWSScoreBean getJswsScoreBeanByOpenId(String openId, String type,
			SqlSession sqlSession) {
		return scoreDao.getJswsScoreBeanByOpenId(openId, type, sqlSession);
	}

	/***
	 * 减少积分
	 * 
	 * @param code
	 * @param score
	 */
	public void reduceJswsScoreByCode(int code, int score, SqlSession sqlSession) {
		scoreDao.reduceJswsScoreByCode(code, score, sqlSession);
	}
	/***
	 * 减少积分
	 * 
	 * @param code
	 * @param score
	 */
	public void reduceJswsScoreByOpenId(String OpenId, int score, SqlSession sqlSession) {
		scoreDao.reduceJswsScoreByOpenId(OpenId, score, sqlSession);
	}
}
