/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年1月25日下午4:10:50
 * 版本号：v1.0
 * 本类主要用途叙述：绝世武神积分数据库交互层
 */

package cn.gyyx.action.dao.jswsexchangedao;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.jswsexchange.JSWSScoreBean;

public class ScoreDao {
	/***
	 * 根据id获取绝世武神的分数
	 * 
	 * @param openId
	 * @return
	 */
	public JSWSScoreBean getJswsScoreBeanByOpenId(String openId, String type,
			SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		return iScore.getJswsScoreBeanByOpenId(openId, type);
	}

	/***
	 * 减少积分
	 * 
	 * @param code
	 * @param score
	 */
	public void reduceJswsScoreByCode(int code, int score,SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		iScore.reduceJswsScoreByCode(code, score);
	}

	public void reduceJswsScoreByOpenId(String OpenId, int score, SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		iScore.reduceJswsScoreByOpenId(OpenId, score);
		
	}
}
