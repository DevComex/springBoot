/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午6:19:51
 * 版本号：v1.0
 * 本类主要用途叙述：挑战关系Bll
 */

package cn.gyyx.action.bll.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.ChallengeRelationshipBean;
import cn.gyyx.action.dao.challenger.ChallengeRelationDao;

public class ChallengeRelationBll {

	private ChallengeRelationDao challengeRelationDao = new ChallengeRelationDao();

	/****
	 * 根据挑战人获取挑战信息
	 * 
	 * @param begin
	 * @param end
	 * @param dareId
	 * @return
	 */
	public Integer getCountDareIdChallengeRelationshipBeanInTimeo(String begin,
			String end, int dareId) {
		return challengeRelationDao
				.getCountDareIdChallengeRelationshipBeanInTimeo(begin, end,
						dareId);
	}

	public Integer getCountDareIdChallengeRelationshipBeanInTimeo(String begin,
			String end, int dareId, SqlSession sqlSession) {
		return challengeRelationDao
				.getCountDareIdChallengeRelationshipBeanInTimeo(begin, end,
						dareId, sqlSession);
	}

	/****
	 * 挑战人某天挑战某人的信息
	 * 
	 * @param begin
	 * @param end
	 * @param dareId
	 * @return
	 */
	public ChallengeRelationshipBean getOneTodayChallengeSomeOne(String begin,
			String end, int dareId, int userId) {
		return challengeRelationDao.getOneTodayChallengeSomeOne(begin, end,
				dareId, userId);
	}
	public ChallengeRelationshipBean getOneTodayChallengeSomeOne(String begin,
			String end, int dareId, int userId,SqlSession sqlSession) {
		return challengeRelationDao.getOneTodayChallengeSomeOne(begin, end,
				dareId, userId,sqlSession);
	}

	/**
	 * 增加一条挑战关系记录
	 * 
	 * @param challengeRelationshipBean
	 */
	public void addChallengeRelationshipBean(
			ChallengeRelationshipBean challengeRelationshipBean,
			SqlSession sqlSession) {
		challengeRelationDao.addChallengeRelationshipBean(
				challengeRelationshipBean, sqlSession);
	}

	/***
	 * 得到我挑战的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getRoleNameIChallenge(int userId) {
		return challengeRelationDao.getRoleNameIChallenge(userId);
	}

	/***
	 * 得到挑战我的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getRoleNameChallengeMe(int userId) {
		return challengeRelationDao.getRoleNameChallengeMe(userId);
	}

	/****
	 * 根据挑战别人的数量
	 * 
	 * @param begin
	 * @param end
	 * @param dareId
	 * @return
	 */
	public Integer getCountDareIdChallengeRelation(int dareId) {
		return challengeRelationDao.getCountDareIdChallengeRelation(dareId);
	}
}
