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

import cn.gyyx.action.beans.challenger.ChallenterTeamBean;
import cn.gyyx.action.beans.challenger.ChallenterTeamUserBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ChallenterTeamDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public ChallenterTeamBean getUserTeamInfo(ChallenterTeamBean obj) {
		SqlSession session = getSession();
		IChallenterTeam iChallenterTeam = session
				.getMapper(IChallenterTeam.class);
		return iChallenterTeam.getUserTeamInfo(obj);
	}
	
	public ChallenterTeamBean getUserCreateTeam(int userId) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.getUserCreateTeam(userId);
		} 
	}

	public ChallenterTeamBean getUserTeamInfo(int userId) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.getUserTeamInfo(userId);
		} 
	}

	public int insert(ChallenterTeamBean obj) {
		int result = 0;
		SqlSession session = getSession();
		IChallenterTeam iChallenterTeam = session
				.getMapper(IChallenterTeam.class);
		try {
			result = iChallenterTeam.insert(obj);
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}

	public int checkTeamNameExist(String teamName) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.checkTeamNameExist(teamName);
		}
	}
	
	public int teamListPagingCount(ChallenterTeamBean b) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.teamListPagingCount(b);
		}
	}
	
	
	public List<ChallenterTeamBean> teamListPaging(ChallenterTeamBean b) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.teamListPaging(b);
		}
	}

	public ChallenterTeamBean getTeam(int teamId) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.getTeam(teamId);
		}
	}
	
	public void updateTeamApplyCountAddOne(SqlSession session, int teamId) {
		IChallenterTeam iChallenterTeam = session
				.getMapper(IChallenterTeam.class);
		iChallenterTeam.updateTeamApplyCountAddOne(teamId);
	}
	
	public List<ChallenterUserInfoBean> getTeamMemberNamesNotLeader(int teamId) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.getTeamMemberNamesNotLeader(teamId);
		}
	}
	
	public List<ChallenterTeamBean> getTeamListData(ChallenterTeamBean bean) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.getTeamListData(bean);
		}
	}
	
	public int getTeamListDataCount(ChallenterTeamBean bean) {
		try (SqlSession session = getSession()) {
			IChallenterTeam iChallenterTeam = session
					.getMapper(IChallenterTeam.class);
			return iChallenterTeam.getTeamListDataCount(bean);
		}
	}
	
	public void reviewChallenterTeam(int code, String state, SqlSession session) {
		IChallenterTeam iChallenterTeam = session
				.getMapper(IChallenterTeam.class);
		iChallenterTeam.reviewChallenterTeam(code,state);
	}

}
