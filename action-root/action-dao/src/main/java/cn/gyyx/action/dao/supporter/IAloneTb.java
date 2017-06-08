package cn.gyyx.action.dao.supporter;

import java.util.List;
import java.util.Map;

import cn.gyyx.action.common.beans.InnerTransmissionData;

public interface IAloneTb {
	// 独立表相关
	public void addAloneForm(Map<String, Object> aloneForm);

	public void updateAloneForm(Map<String, Object> aloneForm);

	public List<Map<String, Object>> queryAloneForm(InnerTransmissionData queryBean);

	public int queryAloneFormNum(InnerTransmissionData queryBean);
}
