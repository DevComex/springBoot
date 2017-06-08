/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.beans.jswswxsign;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能-礼包兑换码表
 */
public class GiftExchangeCode {
	private Integer code;//主键
	private String exchangeCode;//兑换码
	private Integer isUse;//是否被使用 1是 0否  默认0
	private Integer gid;//礼包ID
	private String os;  // 手机操作系统
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
}
