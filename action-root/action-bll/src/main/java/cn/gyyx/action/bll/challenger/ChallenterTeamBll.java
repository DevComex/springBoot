/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午8:32:47
 * 版本号：v1.0
 * 本类主要用途叙述：后台操作日志Bll
 */

package cn.gyyx.action.bll.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.ChallenterTeamBean;
import cn.gyyx.action.beans.challenger.ChallenterTeamUserBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.dao.challenger.ChallenterTeamDao;
import cn.gyyx.action.dao.challenger.ChallenterTeamUserDao;

public class ChallenterTeamBll {
	private ChallenterTeamDao challenterTeamDao = new ChallenterTeamDao();
	private ChallenterTeamUserDao challenterTeamUserDao = new ChallenterTeamUserDao();

	public ChallenterTeamBean getUserTeamInfo(ChallenterTeamBean q) {
		return challenterTeamDao.getUserTeamInfo(q);
	}
	
	public ChallenterTeamBean getUserCreateTeam(int userId) {
		return challenterTeamDao.getUserCreateTeam(userId);
	}

	public ChallenterTeamBean getUserTeamInfo(int userId) {
		return challenterTeamDao.getUserTeamInfo(userId);
	}

	public int insert(ChallenterTeamBean obj) {
		return challenterTeamDao.insert(obj);
	}

	public boolean checkTeamNameExist(String teamName) {
		return challenterTeamDao.checkTeamNameExist(teamName)>0?true:false;
	}

	public int teamListPagingCount(ChallenterTeamBean b) {
		return challenterTeamDao.teamListPagingCount(b);
	}

	public List<ChallenterTeamBean> teamListPaging(ChallenterTeamBean b) {
		return challenterTeamDao.teamListPaging(b);
	}

	public ChallenterTeamBean getTeam(int teamId) {
		return challenterTeamDao.getTeam(teamId);
	}

	public void updateTeamApplyCountAddOne(SqlSession session,int teamId) {
		challenterTeamDao.updateTeamApplyCountAddOne(session,teamId);
	}

	public List<ChallenterUserInfoBean> getTeamMemberNamesNotLeader(int teamId) {
		return challenterTeamDao.getTeamMemberNamesNotLeader(teamId);
	}

	public void insertTeamAndUser(SqlSession session, ChallenterTeamUserBean ub) {
		challenterTeamUserDao.insertTeamAndUser(session,ub);
	}

	public List<ChallenterTeamBean> getTeamListData(ChallenterTeamBean bean) {
		return challenterTeamDao.getTeamListData(bean);
	}

	public int getTeamListDataCount(ChallenterTeamBean bean) {
		return challenterTeamDao.getTeamListDataCount(bean);
	}

	public void reviewChallenterTeam(int code, String state, SqlSession session) {
		challenterTeamDao.reviewChallenterTeam(code,state,session);
	}
	
	public List<ChallenterTeamUserBean> getTeamUserBearListByTeamIds(
			List<Integer> teamIds) {
		return challenterTeamUserDao.getTeamUserBearListByTeamIds(teamIds);
	}
}
