/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日下午2:57:12
 * 版本号：v1.0
 * 本类主要用途叙述：分数进出日志的业务
 */

package cn.gyyx.action.bll.wdtenyearspicture;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.tenyearpicture.ScoreLogBean;
import cn.gyyx.action.dao.wdtenyearpicture.ScoreLogDao;

public class ScoreLogBll {
	private ScoreLogDao scoreLogDao = new ScoreLogDao();

	/***
	 * 增加一条分数进出日志
	 * 
	 * @param scoreLogBean
	 * @param sqlSession
	 */
	public void addScoreLog(ScoreLogBean scoreLogBean, SqlSession sqlSession) {
		scoreLogDao.addScoreLog(scoreLogBean, sqlSession);

	}

	/***
	 * 获取所有活动的名字
	 * 
	 * @return
	 */
	public List<String> getScoreLogHdName(SqlSession sqlSession) {
		return scoreLogDao.getScoreLogHdName(sqlSession);
	}

	/***
	 * 根据活动名和用户获取活动分数进出总和
	 * 
	 * @param hdName
	 * @return
	 */
	public Integer getSumScoreByHdNameAndAccount(String hdName,
			String account, SqlSession sqlSession) {
		Integer a= scoreLogDao.getSumScoreByHdNameAndAccount(hdName, account,
				sqlSession);
		if(a==null){
			a=0;
		}
		return a;
	}
}
