package cn.gyyx.action.dao.supporter;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.common.beans.InnerTransmissionData;

public interface IEventTb {
	// 事件表相关(没有更新接口)
	public void addEventForm(Map<String, Object> eventForm);

	public List<Map<String, Object>> queryEventForm(InnerTransmissionData queryBean);

	public int queryEventFormNum(InnerTransmissionData queryBean);
}
