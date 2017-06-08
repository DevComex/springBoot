package cn.gyyx.action.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.common.ActionCommonFormBean;

public interface IActionCommonFormBean {
	public void insertActionCommonFormBean(ActionCommonFormBean bean);
	public ActionCommonFormBean selectActionCommonFormBeanBycode(@Param("code")int code);
	public void updateActionCommonFormBean(ActionCommonFormBean bean);
	public void deleteActionCommonFormBeanBycode(@Param("code")int code);
	public List<ActionCommonFormBean> selectActionCommonFormByActionAndAccountCode(@Param("actionCode")int actionCode,@Param("accountCode")int accountCode);
	public List<ActionCommonFormBean> selectActionCommonFormByActionCode(@Param("actionCode")int actionCode);
}