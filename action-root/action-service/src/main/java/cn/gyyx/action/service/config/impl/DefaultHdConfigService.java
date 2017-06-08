package cn.gyyx.action.service.config.impl;

import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.service.config.IHdConfigService;

public class DefaultHdConfigService implements IHdConfigService {

	private IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();
	
	public int getState(int activityId) {
		return hdConfigBLL.getState(activityId);
	}
}
