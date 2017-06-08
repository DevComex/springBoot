/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:自定义参数映射业务逻辑
************************************************/
package cn.gyyx.action.bll.wechat;

import cn.gyyx.action.beans.wechat.MappingBean;
import cn.gyyx.action.dao.wechat.MappingDao;

public class MappingBLL {

	private MappingDao mappingDao = new MappingDao();
	
	/**
	 * 增加映射信息
	 * @return
	 */
	public void addMappingInfo(MappingBean mappingBean) {
		mappingDao.addMappingInfo(mappingBean);
	}
}
