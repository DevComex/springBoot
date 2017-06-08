/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.beans.wdqingchengshan;

import java.util.Date;

/** 
       问道青城山签到功能-签到日志表
 */
public class SignLogBean {
	private Integer code;//主键
	private String account;//用户名
	private Date signTime;//签到时间	
	private String signDateStr;//字符串时间
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getSignDateStr() {
		return signDateStr;
	}
	public void setSignDateStr(String signDateStr) {
		this.signDateStr = signDateStr;
	}
	
	
}
