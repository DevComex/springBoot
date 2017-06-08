/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日下午2:35:44
 * 版本号：v1.0
 * 本类主要用途叙述：分数进出日志的数据库交互
 */

package cn.gyyx.action.dao.wdtenyearpicture;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.tenyearpicture.ScoreLogBean;

public class ScoreLogDao {
	/***
	 * 增加一条分数进出日志
	 * 
	 * @param scoreLogBean
	 * @param sqlSession
	 */
	public void addScoreLog(ScoreLogBean scoreLogBean, SqlSession sqlSession) {
		IScoreLog iScoreLog = sqlSession.getMapper(IScoreLog.class);
		iScoreLog.addScoreLog(scoreLogBean);
	}

	/***
	 * 获取所有活动的名字
	 * 
	 * @return
	 */
	public List<String> getScoreLogHdName(SqlSession sqlSession) {
		IScoreLog iScoreLog = sqlSession.getMapper(IScoreLog.class);
		return iScoreLog.getScoreLogHdName();
	}

	/***
	 * 根据活动名和用户获取活动分数进出总和
	 * 
	 * @param hdName
	 * @return
	 */
	public Integer getSumScoreByHdNameAndAccount(String hdName,
			String account, SqlSession sqlSession) {
		IScoreLog iScoreLog = sqlSession.getMapper(IScoreLog.class);
		return iScoreLog.getSumScoreByHdNameAndAccount(hdName, account);
	}
}
