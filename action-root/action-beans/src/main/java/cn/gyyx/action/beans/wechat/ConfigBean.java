/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:后台统计Bean
************************************************/
package cn.gyyx.action.beans.wechat;

public class ConfigBean {
	// 主键
	private Integer code;
	// 统计名称
	private String configName;
	// 是否抽奖
	private boolean isLottery;
	// 抽奖活动号
	private Integer actionCode;
	// 是否开启
	private boolean isOpen;

	public ConfigBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConfigBean(Integer code, String configName, boolean isLottery,
			Integer actionCode, boolean isOpen) {
		super();
		this.code = code;
		this.configName = configName;
		this.isLottery = isLottery;
		this.actionCode = actionCode;
		this.isOpen = isOpen;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public boolean getIsLottery() {
		return isLottery;
	}

	public void setIsLottery(boolean isLottery) {
		this.isLottery = isLottery;
	}

	public Integer getActionCode() {
		return actionCode;
	}

	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	@Override
	public String toString() {
		return "WeChatConfigBean [code=" + code + ", configName=" + configName
				+ ", isLottery=" + isLottery + ", actionCode=" + actionCode
				+ ", isOpen=" + isOpen + "]";
	}
	
}
