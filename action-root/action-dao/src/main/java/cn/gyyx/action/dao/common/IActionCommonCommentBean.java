package cn.gyyx.action.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.common.ActionCommonCommentBean;

public interface IActionCommonCommentBean {
	public void insertActionCommonCommentBean(ActionCommonCommentBean bean);
	public ActionCommonCommentBean selectActionCommonCommentBeanBycode(@Param("code")int code);
	public void updateActionCommonCommentBean(ActionCommonCommentBean bean);
	public void deleteActionCommonCommentBeanBycode(@Param("code")int code);
	public void updateTalk(ActionCommonCommentBean bean);
	public List<ActionCommonCommentBean> getTalkByPage(@Param("pageIndex")int pageIndex,@Param("actionCode")int actionCode,@Param("status")String status);
	public ActionCommonCommentBean selectTalkBycode(@Param("code")int code);
	public List<ActionCommonCommentBean> getTalkByFormCode(@Param("formCode")int formCode);
	public List<ActionCommonCommentBean> getTalk(@Param("actionCode")int actionCode);
	
}