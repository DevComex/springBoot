/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:自定义参数Bean
************************************************/
package cn.gyyx.action.beans.wechat;

public class CustomBean {
	// 主键
	private Integer code;
	// 配置编号
	private Integer configCode;
	// 操作编号
	private Integer operateCode;
	
	/*
	 * new   操作描述
	 */
	private String operateDesc;
	
	// 自定义类型
	private String customType;
	// 自定义描述
	private String customDesc;
	// 删除标识
	private boolean isDelete;

	public CustomBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomBean(Integer configCode, Integer operateCode,
			String customType, String customDesc, boolean isDelete) {
		super();
		this.configCode = configCode;
		this.operateCode = operateCode;
		this.customType = customType;
		this.customDesc = customDesc;
		this.isDelete = isDelete;
	}
	
	
	
	public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getConfigCode() {
		return configCode;
	}

	public void setConfigCode(Integer configCode) {
		this.configCode = configCode;
	}

	public Integer getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(Integer operateCode) {
		this.operateCode = operateCode;
	}

	public String getCustomType() {
		return customType;
	}

	public void setCustomType(String customType) {
		this.customType = customType;
	}

	public String getCustomDesc() {
		return customDesc;
	}

	public void setCustomDesc(String customDesc) {
		this.customDesc = customDesc;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "CustomBean [configCode=" + configCode + ", operateCode="
				+ operateCode + ", customType=" + customType + ", customDesc="
				+ customDesc + ", isDelete=" + isDelete + "]";
	}

}
