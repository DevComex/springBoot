package cn.gyyx.action.dao.supporter;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.common.beans.InnerTransmissionData;

public interface ISubTb {
	// 子表相关
	public void addSubForm(Map<String, Object> mainForm);
	public void updateSubForm(Map<String, Object> mainForm);

	public List<Map<String, Object>> querySubForm(InnerTransmissionData queryBean);
	public int querySubFormNum(InnerTransmissionData queryBean);
	
	public List<Map<String,Object>> getAllSubForm(@Param("actionCode")int actionCode,@Param("mainCode")int mainCode,@Param("type")String type);
}
