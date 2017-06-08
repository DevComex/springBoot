/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：1.214
 * @本类主要用途描述：首页显示素材内容存储
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

public class ShowTopBean {
	//主键
	private Integer code;
	//审核编号
	private Integer auditCode;
	//素材类型
	private String materialType;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getAuditCode() {
		return auditCode;
	}

	public void setAuditCode(Integer auditCode) {
		this.auditCode = auditCode;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

}
