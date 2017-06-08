/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日下午2:30:43
 * 版本号：v1.0
 * 本类主要用途叙述：分数的业务层
 */

package cn.gyyx.action.bll.wdtenyearspicture;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.tenyearpicture.ScoreBean;
import cn.gyyx.action.dao.wdtenyearpicture.ScoreDao;

public class ScoreBll {
	private ScoreDao scoreDao = new ScoreDao();

	/***
	 * 根据分数主键获取用户积分信息
	 * 
	 * @param userCode
	 * @param sqlSession
	 * @return
	 */
	public ScoreBean getScoreBean(int userCode, SqlSession sqlSession) {
		return scoreDao.getScoreBean(userCode, sqlSession);

	}

	/***
	 * 根据主键设定相应的分数
	 * 
	 * @param userCode
	 * @param score
	 */
	public void setScoreBean(int userCode, int score, SqlSession sqlSession) {
		scoreDao.setScoreBean(userCode, score, sqlSession);
	}

	/***
	 * 增加十年记录
	 * 
	 * @param scoreBean
	 */
	public void addScoreBean(ScoreBean scoreBean, SqlSession sqlSession) {
		scoreDao.addScoreBean(scoreBean, sqlSession);
	}

	/***
	 * 获取积分绑定的总账户数
	 * 
	 * @return
	 */
	public Integer getCountByAccount(SqlSession sqlSession) {
		return scoreDao.getCountByAccount(sqlSession);
	}

	/***
	 * 获取所有积分信息
	 * 
	 * @return
	 */
	public List<ScoreBean> getScoreBeanas(SqlSession sqlSession) {
		return scoreDao.getScoreBeanas(sqlSession);
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

		return scoreDao.getTopScoreByPage(pageNo, pageSize, sqlSession);
	}

	/***
	 * 得到排名前一千玩家
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<ScoreBean> getTopScore(SqlSession sqlSession) {
		return scoreDao.getTopScore(sqlSession);
	}

	/****
	 * 得到积分排名
	 * 
	 * @param score
	 * @return
	 */
	public Integer getCountScore(int score, SqlSession sqlSession) {
		return scoreDao.getCountScore(score, sqlSession);
	}
}
