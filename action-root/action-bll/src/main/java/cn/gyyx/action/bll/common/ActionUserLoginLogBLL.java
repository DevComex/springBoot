package cn.gyyx.action.bll.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.common.ActionUserLoginLog;
import cn.gyyx.action.dao.common.ActionUserLoginLogDao;

public class ActionUserLoginLogBLL {
	ActionUserLoginLogDao actionUserLoginLogDao = new ActionUserLoginLogDao();
	
	public void insert(ActionUserLoginLog bean) {
		actionUserLoginLogDao.insert(bean);
	}
	
	public ActionUserLoginLog getBeanByLoginId(String loginId) {
		return actionUserLoginLogDao.getBeanByLoginId(loginId);
	}

	public List<ActionUserLoginLog> getListPaging(ActionUserLoginLog bean) {
		return actionUserLoginLogDao.getListPaging(bean);
	}

	public int getListPagingCount(ActionUserLoginLog bean) {
		return actionUserLoginLogDao.getListPagingCount(bean);
	}

	public List<Map<String, String>> getLoginCountGroupByDate(int activityCode) {
		return actionUserLoginLogDao.getLoginCountGroupByDate(activityCode);
	}

	public Map<String, String> covertMapFromListByDate(
			List<Map<String, String>> loginLog) {
		Map<String,String> s = new HashMap<>();
		if(loginLog != null){
			for(Map<String,String> d : loginLog){
				String a = d.get("loginTime");
				String b = d.get("loginCount");
				s.put(a, b+"");
			}
		}
		return s;
	}
}
