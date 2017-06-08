package cn.gyyx.action.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.common.ActionCommonImageBean;
import cn.gyyx.action.beans.wdpetdating.CheckImgDTO;

public interface IActionCommonImageBean {
	public void insertActionCommonImageBean(ActionCommonImageBean bean);
	public List<ActionCommonImageBean> getImgBeanByPage(CheckImgDTO checkImgDTO);
	public ActionCommonImageBean selectImgBycode(@Param("code")int code);
	public void updateImg(ActionCommonImageBean bean);
	public List<ActionCommonImageBean> getImgBeanByPagePass(CheckImgDTO checkImgDTO);
	public List<ActionCommonImageBean> getImgBeanByFormCode(@Param("formCode")int formCode);
}