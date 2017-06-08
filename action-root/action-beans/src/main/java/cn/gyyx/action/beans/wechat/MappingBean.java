/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:自定义参数映射Bean
************************************************/
package cn.gyyx.action.beans.wechat;

public class MappingBean {
	// 主键
	private Integer code;
	// 映射名
	private String mappingName;
	// 参数名
	private String paraName;
	// 参数值
	private String paraValue;

	public MappingBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MappingBean(String mappingName, String paraName, String paraValue) {
		super();
		this.mappingName = mappingName;
		this.paraName = paraName;
		this.paraValue = paraValue;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

	@Override
	public String toString() {
		return "MappingBean [mappingName=" + mappingName + ", paraName="
				+ paraName + ", paraValue=" + paraValue + "]";
	}

}
