/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午6:15:50
 * 版本号：v1.0
 * 本类主要用途叙述：挑战关系数据库
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.challenger.ChallengeRelationshipBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ChallengeRelationDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

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

		try (SqlSession sqlSession = getSession()) {
			IChallengeRelationship iChallengeRelationship = sqlSession
					.getMapper(IChallengeRelationship.class);
			return iChallengeRelationship
					.getCountDareIdChallengeRelationshipBeanInTimeo(begin, end,
							dareId);
		}

	}

	public Integer getCountDareIdChallengeRelationshipBeanInTimeo(String begin,
			String end, int dareId, SqlSession sqlSession) {

		IChallengeRelationship iChallengeRelationship = sqlSession
				.getMapper(IChallengeRelationship.class);
		return iChallengeRelationship
				.getCountDareIdChallengeRelationshipBeanInTimeo(begin, end,
						dareId);

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
		try (SqlSession sqlSession = getSession()) {
			IChallengeRelationship iChallengeRelationship = sqlSession
					.getMapper(IChallengeRelationship.class);
			return iChallengeRelationship.getOneTodayChallengeSomeOne(begin,
					end, dareId, userId);
		}

	}
	
	public ChallengeRelationshipBean getOneTodayChallengeSomeOne(String begin,
			String end, int dareId, int userId,SqlSession sqlSession) {
			IChallengeRelationship iChallengeRelationship = sqlSession
					.getMapper(IChallengeRelationship.class);
			return iChallengeRelationship.getOneTodayChallengeSomeOne(begin,
					end, dareId, userId);

	}

	/**
	 * 增加一条挑战关系记录
	 * 
	 * @param challengeRelationshipBean
	 */
	public void addChallengeRelationshipBean(
			ChallengeRelationshipBean challengeRelationshipBean,
			SqlSession sqlSession) {
		IChallengeRelationship iChallengeRelationship = sqlSession
				.getMapper(IChallengeRelationship.class);
		iChallengeRelationship
				.addChallengeRelationshipBean(challengeRelationshipBean);
	}

	/***
	 * 得到我挑战的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getRoleNameIChallenge(int userId) {
		try (SqlSession sqlSession = getSession()) {
			IChallengeRelationship iChallengeRelationship = sqlSession
					.getMapper(IChallengeRelationship.class);
			return iChallengeRelationship.getRoleNameIChallenge(userId);
		}
	}

	/***
	 * 得到挑战我的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getRoleNameChallengeMe(int userId) {
		try (SqlSession sqlSession = getSession()) {
			IChallengeRelationship iChallengeRelationship = sqlSession
					.getMapper(IChallengeRelationship.class);
			return iChallengeRelationship.getRoleNameChallengeMe(userId);
		}
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
		try (SqlSession sqlSession = getSession()) {
			IChallengeRelationship iChallengeRelationship = sqlSession
					.getMapper(IChallengeRelationship.class);
			return iChallengeRelationship
					.getCountDareIdChallengeRelation(dareId);
		}
	}

}
