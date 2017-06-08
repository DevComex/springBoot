package cn.gyyx.action.dao.wechat;

import java.util.List;

import cn.gyyx.action.beans.wechat.ConfigBean;

public interface IConfigMapper {
	/**
	 * 获取所有的后台统计配置信息
	 * @return
	 */
	public List<ConfigBean> getWechatConfig();
	/**
	 * 获取最新的统计编号
	 */
	public Integer getMaxCodeFromConfig();
	/**
	 * 根据配置名称查询统计配置
	 * @param configName
	 * @return
	 */
	public Integer getWechatConfigByConfigName(String configName);
	/**
	 * 插入统计配置信息
	 * @param weChatConfigBean
	 */
	public void addWechatConfig(ConfigBean weChatConfigBean);
	/**
	 * 根据code获取统计配置信息
	 * @param code
	 * @return
	 */
	public ConfigBean getWechatConfigInfoByCode(Integer code);
	/**
	 * 修改统计配置信息
	 * @param weChatConfigBean
	 */
	public void updateWechatConfig(ConfigBean weChatConfigBean);
	/**
	 * 修改统计开启状态
	 * @param isOpen
	 */
	public void updateConfigIsOpen(ConfigBean weChatConfigBean);
}
