package cn.gyyx.action.bll.common;

import java.util.Date;

import net.sf.json.JSONObject;
import cn.gyyx.action.beans.common.ActionCommonCommentBean;
import cn.gyyx.action.beans.common.ActionCommonFormBean;
import cn.gyyx.action.beans.common.ActionCommonImageBean;
import cn.gyyx.action.dao.common.ActionCommonCommentDAO;
import cn.gyyx.action.dao.common.ActionCommonFormDAO;
import cn.gyyx.action.dao.common.ActionCommonImageDAO;

public class ActionCommonBLL {
	ActionCommonFormDAO actionCommonDao = new ActionCommonFormDAO();
	ActionCommonImageDAO imageDao = new ActionCommonImageDAO();
	ActionCommonCommentDAO actionCommonCommentDAO = new ActionCommonCommentDAO();
	public int addActionCommonImage(ActionCommonImageBean bean) {
		int result = -1;
		try {
			imageDao.insertActionCommonImageBean(bean);
			result = 1;
		} catch (Exception e) {
			result = -1;
		}
		return result;
	}
	
	public int addActionCommonForm(int actionCode,int accountCode, Object formBean) {
		int result = -1;
		try {
			String str = JSONObject.fromObject(formBean).toString();
			ActionCommonFormBean bean = new ActionCommonFormBean();
			bean.setAccountCode(accountCode);
			bean.setActionCode(actionCode);
			bean.setJsonStr(str);
			actionCommonDao.insertActionCommonFormBean(bean);
			result = bean.getCode();
		} catch (Exception e) {
			result = -1;
		}
		return result;
	}

	public int countActionCommonForm(int actionCode,int accountCode) {
		return actionCommonDao.selectActionCommonFormByActionAndAccountCode(actionCode,accountCode).size();
	}
	public String addActionCommonForm(int actionCode, String jsonStr) {
		String result = "SUCCESS";
		try {
			ActionCommonFormBean bean = new ActionCommonFormBean();
			bean.setActionCode(actionCode);
			bean.setJsonStr(jsonStr);
			actionCommonDao.insertActionCommonFormBean(bean);
		} catch (Exception e) {
			result = e.getClass().getSimpleName() + ":" + e.getMessage();
		}
		return result;
	}
	public void addTalk(ActionCommonCommentBean bean) {
		Date date =new Date();
		bean.setUploadDate(date);
		actionCommonCommentDAO.insertActionCommonCommentBean(bean);
		
	}
}
