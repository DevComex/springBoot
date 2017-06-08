/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午5:52:04
 * 版本号：v1.0
 * 本类主要用途叙述：用户报名信息的数据库接口
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/**
 * --------------------------------------------------- 版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 联系方式：wangyufei@gyyx.cn 创建时间：2016年7月12日下午5:52:04 版本号：v1.0
 * 本类主要用途叙述：用户报名信息的数据库接口
 */

public class ChallenterUserInfoDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 更改用户报名信息状态
	 * 
	 * @param userCode
	 * @param status
	 * @param sqlSession
	 */
	public Integer setUserInfoState(int code, String status,
			SqlSession sqlSession) {
		IChallenterUserInfo iChallenterUserInfo = sqlSession
				.getMapper(IChallenterUserInfo.class);
		return iChallenterUserInfo.setUserInfoState(code, status);

	}

	/***
	 * 根据状态获取某人的报名信息
	 * 
	 * @param userCode
	 * @param status
	 * @return
	 */
	public ChallenterUserInfoBean getOneChallenterUserInfo(int userCode,
			String status) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterUserInfo iChallenterUserInfo = sqlSession
					.getMapper(IChallenterUserInfo.class);
			return iChallenterUserInfo.getOneChallenterUserInfo(userCode,
					status);
		}
	}

	/***
	 * 根据状态获取某人的报名信息
	 * 
	 * @param userCode
	 * @param status
	 * @return
	 */
	public ChallenterUserInfoBean getCodeChallenterUserInfo(int code,
			String status) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterUserInfo iChallenterUserInfo = sqlSession
					.getMapper(IChallenterUserInfo.class);
			return iChallenterUserInfo.getCodeChallenterUserInfo(code, status);
		}
	}

	public ChallenterUserInfoBean getUserInfoBean(
			ChallenterUserInfoBean userInfoBean) {
		SqlSession session = getSession();
		IChallenterUserInfo iChallenterUserInfo = session
				.getMapper(IChallenterUserInfo.class);
		return iChallenterUserInfo.getUserInfoBean(userInfoBean);
	}

	public int getUserInfoCount(ChallenterUserInfoBean userInfoBean) {
		SqlSession session = getSession();
		IChallenterUserInfo iChallenterUserInfo = session
				.getMapper(IChallenterUserInfo.class);
		return iChallenterUserInfo.getUserInfoCount(userInfoBean);
	}

	public int insert(ChallenterUserInfoBean userInfoBean) {
		int result = 0;
		SqlSession session = getSession();
		IChallenterUserInfo iChallenterUserInfo = session
				.getMapper(IChallenterUserInfo.class);
		try {
			result = iChallenterUserInfo.insert(userInfoBean);
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}

	/***
	 * 根据状态获取报名信息
	 * 
	 * @param status
	 * @return
	 */
	public List<ChallenterUserInfoBean> getStatusChallenterUserInfo(int pageNo,
			int pageSize, String status) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterUserInfo iChallenterUserInfo = sqlSession
					.getMapper(IChallenterUserInfo.class);
			return iChallenterUserInfo.getStatusChallenterUserInfo(pageNo,
					pageSize, status);
		}

	}

	/***
	 * 根据状态查询数量
	 */
	public Integer getCountStatusChallenterUserInfo(String status) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterUserInfo iChallenterUserInfo = sqlSession
					.getMapper(IChallenterUserInfo.class);
			return iChallenterUserInfo.getCountStatusChallenterUserInfo(status);
		}

	}

	/**
	 * 得到用户最后一条中奖信息
	 * 
	 * @param userCode
	 * @return
	 */
	public ChallenterUserInfoBean getLastOneChallenterUserInfo(int userCode) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterUserInfo iChallenterUserInfo = sqlSession
					.getMapper(IChallenterUserInfo.class);
			return iChallenterUserInfo.getLastOneChallenterUserInfo(userCode);
		}
	}

	public List<ChallenterUserInfoBean> getUserInfoListByIds(
			List<Integer> userIds) {
		try (SqlSession sqlSession = getSession()) {
			IChallenterUserInfo iChallenterUserInfo = sqlSession
					.getMapper(IChallenterUserInfo.class);
			return iChallenterUserInfo.getUserInfoListByIds(userIds);
		}
	}
	
	public List<ChallenterUserInfoBean> getTeamMemberUserInfoListByTeamIds(
			List<Integer> teamIds) {
		try (SqlSession session = getSession()) {
			IChallenterUserInfo iChallenterUserInfo = session
					.getMapper(IChallenterUserInfo.class);
			return iChallenterUserInfo.getTeamMemberUserInfoListByTeamIds(teamIds);
		}
	}
}
