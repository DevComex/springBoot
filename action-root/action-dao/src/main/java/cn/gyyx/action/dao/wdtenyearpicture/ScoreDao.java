/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日上午11:41:10
 * 版本号：v1.0
 * 本类主要用途叙述：问道十年总分数据库交互
 */

package cn.gyyx.action.dao.wdtenyearpicture;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.tenyearpicture.ScoreBean;

public class ScoreDao {
	/***
	 * 根据用户主键获取总分信息
	 * 
	 * @param userCode
	 * @return
	 */
	public ScoreBean getScoreBean(int userCode, SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		return iScore.getScoreBean(userCode);
	}

	/***
	 * 根据主键设定相应的分数
	 * 
	 * @param userCode
	 * @param score
	 */
	public void setScoreBean(int userCode, int score, SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		iScore.setScoreBean(userCode, score);
	}

	/***
	 * 增加十年记录
	 * 
	 * @param scoreBean
	 */
	public void addScoreBean(ScoreBean scoreBean, SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		iScore.addScoreBean(scoreBean);
	}

	/***
	 * 获取积分绑定的总账户数
	 * 
	 * @return
	 */
	public Integer getCountByAccount(SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		return iScore.getCountByAccount();
	}

	/***
	 * 获取所有积分信息
	 * 
	 * @return
	 */
	public List<ScoreBean> getScoreBeanas(SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		return iScore.getScoreBeanas();
	}

	/***
	 * 分页得到排名前一千玩家
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<ScoreBean> getTopScoreByPage(int pageNo, int pageSize,
			SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		return iScore.getTopScoreByPage(pageNo, pageSize);
	}

	/***
	 * 得到排名前一千玩家
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<ScoreBean> getTopScore(SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		return iScore.getTopScore();
	}

	/****
	 * 得到积分排名
	 * 
	 * @param score
	 * @return
	 */
	public Integer getCountScore(int score, SqlSession sqlSession) {
		IScore iScore = sqlSession.getMapper(IScore.class);
		return iScore.getCountScore(score);
	}

}
