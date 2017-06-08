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

import cn.gyyx.action.beans.challenger.ChallenterTeamApplyLogBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ChallenterTeamApplyLogDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public int getUserApplyTeamCount(int userId, String state) {
		try (SqlSession session = getSession()) {
			IChallenterTeamApplyLog iChallenterTeamApplyLog = session
					.getMapper(IChallenterTeamApplyLog.class);
			return iChallenterTeamApplyLog.getUserApplyTeamCount(userId,state);
		} 
	}

	public int insert(ChallenterTeamApplyLogBean log, SqlSession session) {
		IChallenterTeamApplyLog iChallenterTeamApplyLog = session
				.getMapper(IChallenterTeamApplyLog.class);
		return iChallenterTeamApplyLog.insert(log);
	}

	public List<ChallenterUserInfoBean> getUserTeamApplyLogList(int teamId) {
		try (SqlSession session = getSession()) {
			IChallenterTeamApplyLog iChallenterTeamApplyLog = session
					.getMapper(IChallenterTeamApplyLog.class);
			return iChallenterTeamApplyLog.getUserTeamApplyLogList(teamId);
		} 
	}

	public ChallenterTeamApplyLogBean getChallenterTeamApplyLogByCode(int code) {
		try (SqlSession session = getSession()) {
			IChallenterTeamApplyLog iChallenterTeamApplyLog = session
					.getMapper(IChallenterTeamApplyLog.class);
			return iChallenterTeamApplyLog.getChallenterTeamApplyLogByCode(code);
		} 
	}

	public void updateApplyState(String status, SqlSession session, int applyId) {
		IChallenterTeamApplyLog iChallenterTeamApplyLog = session
				.getMapper(IChallenterTeamApplyLog.class);
		iChallenterTeamApplyLog.updateApplyState(status,applyId);
	}

	public int getApplyCount(ChallenterTeamApplyLogBean t) {
		try (SqlSession session = getSession()) {
			IChallenterTeamApplyLog iChallenterTeamApplyLog = session
					.getMapper(IChallenterTeamApplyLog.class);
			return iChallenterTeamApplyLog.getApplyCount(t);
		} 
	}

	public int updateUserTeamApplyStateUnPass(SqlSession session, int applyId,
			int userId) {
		IChallenterTeamApplyLog iChallenterTeamApplyLog = session
				.getMapper(IChallenterTeamApplyLog.class);
		return iChallenterTeamApplyLog.updateUserTeamApplyStateUnPass(applyId,userId);
	}

	
}
