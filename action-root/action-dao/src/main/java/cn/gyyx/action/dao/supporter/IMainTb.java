package cn.gyyx.action.dao.supporter;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.common.beans.InnerTransmissionData;

public interface IMainTb {
	//主表相关
	public void addMainForm(Map<String,Object> mainForm);
	public void addSearchMap(Map<String,Object> searchMap);
	
	public void updateMainForm(Map<String,Object> mainForm);
	public void updateSearchForm(Map<String,Object> searchMap);
	
	public List<Map<String,Object>> queryMainForm(InnerTransmissionData queryBean);
	public int queryMainFormNum(InnerTransmissionData queryBean);
}
