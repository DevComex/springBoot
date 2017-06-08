package cn.gyyx.action.bll.userinfo.impl;

import org.apache.ibatis.session.SqlSession;

import com.ibm.icu.util.Calendar;

import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.beans.userinfo.po.ActionGameInterimPO;
import cn.gyyx.action.beans.userinfo.po.ActionUserInfoPO;
import cn.gyyx.action.beans.userinfo.vo.UserInfoAndQualificationVO;
import cn.gyyx.action.bll.userinfo.IActionUserInfoBLL;
import cn.gyyx.action.dao.lottery.IActionQualificationDAO;
import cn.gyyx.action.dao.lottery.impl.ActionQualificationDAOImpl;
import cn.gyyx.action.dao.userinfo.IActionGameInterimDAO;
import cn.gyyx.action.dao.userinfo.IActionUserInfoDAO;
import cn.gyyx.action.dao.userinfo.impl.DefaultActionGameInterimDAO;
import cn.gyyx.action.dao.userinfo.impl.DefaultActionUserInfoDAO;

public class DefaultActionUserInfoBLL implements IActionUserInfoBLL {

	private IActionUserInfoDAO actionUserInfoDAO = new DefaultActionUserInfoDAO();
	private IActionQualificationDAO actionQualificationDAO = new ActionQualificationDAOImpl();
	private IActionGameInterimDAO actionGameInterimDAO = new DefaultActionGameInterimDAO();
	
	@Override
	public ActionUserInfoPO get(int activityId, String userId) {
		return actionUserInfoDAO.selectOne(activityId, userId);
	}
	
	@Override
	public UserInfoAndQualificationVO getUserInfo(int activityId, String userId) {
		return actionUserInfoDAO.selectUserInfo(activityId, userId);
	}
	
	@Override
	public int put(ActionUserInfoPO params, SqlSession session) {
		if (actionUserInfoDAO.insert(params, session) > 0) {
			if (null == actionQualificationDAO.getData(params.getUserId(), params.getActivityId())) {
				ActionQualificationPO po = new ActionQualificationPO();
				po.setActivityId(params.getActivityId());
				po.setUserId(params.getUserId());
				po.setCreateAt(Calendar.getInstance().getTime());
				po.setModifyAt(Calendar.getInstance().getTime());
				
				return actionQualificationDAO.insert(po, session);
			}
			else {
				return 1;
			}
		}
		
		return -1;
	}
	
	@Override
	public int push(ActionUserInfoPO params, SqlSession session) {
		if (actionUserInfoDAO.update(params, session) > 0) {
			ActionGameInterimPO gameParams = new ActionGameInterimPO();
			gameParams.setServerId(params.getServerId());
			gameParams.setRoleId(params.getRoleId());
			
			if (null == actionGameInterimDAO.selectOne(gameParams)) {
				gameParams.setUserId(params.getUserId());
				gameParams.setUserNick(params.getUserNick());
				gameParams.setServerName(params.getServerName());
				gameParams.setRoleName(params.getRoleName());
				gameParams.setCreateAt(Calendar.getInstance().getTime());
				
				return actionGameInterimDAO.insert(gameParams, session);
			}
		}
		
		return actionUserInfoDAO.update(params, session);
	}
}
