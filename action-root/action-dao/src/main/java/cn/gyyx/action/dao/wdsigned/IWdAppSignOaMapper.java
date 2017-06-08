package cn.gyyx.action.dao.wdsigned;

import cn.gyyx.action.beans.wdsigned.WdAppSignOaBean;


public interface IWdAppSignOaMapper {

	/**
	 * 插入时间配置信息
	 * @param bean
	 * @return
	 */
	int insert(WdAppSignOaBean bean);

	//查询活动配置时间信息
	WdAppSignOaBean getWdAppSignOaBean(String batch);
}
