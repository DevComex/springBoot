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

import cn.gyyx.action.beans.challenger.ChallenterTeamApplyLogBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.dao.challenger.ChallenterTeamApplyLogDao;
import cn.gyyx.action.dao.challenger.ChallenterTeamDao;

public class ChallenterTeamApplyLogBll {
	private ChallenterTeamApplyLogDao challenterTeamApplyLogDao = new ChallenterTeamApplyLogDao();
	private ChallenterTeamDao challenterTeamDao = new ChallenterTeamDao();

	public int getUserApplyTeamCount(int userId, String state) {
		return challenterTeamApplyLogDao.getUserApplyTeamCount(userId,state);
	}

	public int insert(ChallenterTeamApplyLogBean log, SqlSession session) {
		return challenterTeamApplyLogDao.insert(log,session);
	}

	public List<ChallenterUserInfoBean> getUserTeamApplyLogList(int teamId) {
		return challenterTeamApplyLogDao.getUserTeamApplyLogList(teamId);
	}

	public ChallenterTeamApplyLogBean getChallenterTeamApplyLogByCode(
			int code) {
		return challenterTeamApplyLogDao.getChallenterTeamApplyLogByCode(code);
	}

	public void updateApplyState(String status, SqlSession session, int applyId) {
		challenterTeamApplyLogDao.updateApplyState(status,session,applyId);
	}

	public int getApplyCount(ChallenterTeamApplyLogBean t) {
		return challenterTeamApplyLogDao.getApplyCount(t);
	}

	public int updateUserTeamApplyStateUnPass(SqlSession session, int applyId,
			int userId) {
		return challenterTeamApplyLogDao.updateUserTeamApplyStateUnPass(session,applyId,userId);
	}

}
