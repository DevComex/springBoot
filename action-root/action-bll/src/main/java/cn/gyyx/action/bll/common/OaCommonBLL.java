package cn.gyyx.action.bll.common;

import java.util.List;

import cn.gyyx.action.beans.common.ActionCommonCommentBean;
import cn.gyyx.action.beans.common.ActionCommonImageBean;
import cn.gyyx.action.beans.wdpetdating.CheckImgDTO;
import cn.gyyx.action.dao.common.ActionCommonCommentDAO;
import cn.gyyx.action.dao.common.ActionCommonImageDAO;

public class OaCommonBLL {
	private ActionCommonImageDAO actionCommonImageDAO = new ActionCommonImageDAO();
	private ActionCommonCommentDAO actionCommonCommentDAO = new ActionCommonCommentDAO();
	public List<ActionCommonImageBean> getImgBeanByPage(CheckImgDTO checkImgDTO) {
		return actionCommonImageDAO.getImgBeanByPage(checkImgDTO );
		
	}
	public ActionCommonImageBean getImgBycode(int code) {
		return actionCommonImageDAO.selectImgBycode(code);
		
	}
	public void updateImg(ActionCommonImageBean bean) {
		actionCommonImageDAO.updateImg(bean);
	}
	public List<ActionCommonImageBean> getImgBeanByPagePass(CheckImgDTO checkImgDTO) {
		return actionCommonImageDAO.getImgBeanByPagePass(checkImgDTO);
		
	}
	public void updateTalk(ActionCommonCommentBean bean) {
		actionCommonCommentDAO.updateTalk(bean);
	}
	public List<ActionCommonCommentBean> getTalkByPage(int pageIndex,
			int actionCode, String status) {
				return actionCommonCommentDAO.getTalkByPage(pageIndex, actionCode, status);
		
	}
	public ActionCommonCommentBean getTalkBycode(int code) {
		return actionCommonCommentDAO.selectTalkBycode(code);
		
	}
	public void addTalk(ActionCommonCommentBean bean) {
		actionCommonCommentDAO.insertActionCommonCommentBean(bean);
		
	}
	public Integer getTalk(int actionCode) {
		List<ActionCommonCommentBean> list = actionCommonCommentDAO.getTalk(actionCode);
		return list.size();
	}
}
