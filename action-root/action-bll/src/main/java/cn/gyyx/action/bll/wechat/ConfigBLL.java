/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:统计配置业务逻辑
************************************************/
package cn.gyyx.action.bll.wechat;

import java.util.List;

import cn.gyyx.action.beans.wechat.ConfigBean;
import cn.gyyx.action.dao.wechat.ConfigDao;

public class ConfigBLL {

	private ConfigDao weChatConfigDao = new ConfigDao();
	
	/**
	 * 获取所有的后台统计配置信息
	 * @return
	 */
	public List<ConfigBean> getWechatConfig() {
		return weChatConfigDao.getWechatConfig();
	}
	/**
	 * 获取最新的统计编号
	 */
	public Integer getMaxCodeFromConfig(){
		return weChatConfigDao.getMaxCodeFromConfig();
	}
	
	/**
	 * 根据配置名称查询统计配置
	 * @param configName
	 * @return
	 */
	public Integer getWechatConfigByConfigName(String configName){
		return weChatConfigDao.getWechatConfigByConfigName(configName);
	}
	
	/**
	 * 插入统计配置信息
	 * @param weChatConfigBean
	 */
	public void addWechatConfig(ConfigBean weChatConfigBean){
		weChatConfigDao.addWechatConfig(weChatConfigBean);
	}
	
	/**
	 * 根据code获取统计配置信息
	 * @param code
	 * @return
	 */
	public ConfigBean getWechatConfigInfoByCode(Integer code){
		return weChatConfigDao.getWechatConfigInfoByCode(code);
	}
	
	/**
	 * 修改统计配置信息
	 * @param weChatConfigBean
	 */
	public void updateWechatConfig(ConfigBean weChatConfigBean){
		weChatConfigDao.updateWechatConfig(weChatConfigBean);
	}
	
	/**
	 * 修改统计开启状态
	 * @param isOpen
	 */
	public void updateConfigIsOpen(ConfigBean weChatConfigBean){
		weChatConfigDao.updateConfigIsOpen(weChatConfigBean);
	}
}
