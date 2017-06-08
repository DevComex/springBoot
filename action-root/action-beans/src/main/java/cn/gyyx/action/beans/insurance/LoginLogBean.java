/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月15日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——登陆日志表实体类
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.insurance;

import java.util.Date;

public class LoginLogBean {

	//主键
	int code;
	//账号Id
	int countId;
	//登陆时间
	Date loginTime;
	//登陆IP
	String loginIp;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCountId() {
		return countId;
	}
	public void setCountId(int countId) {
		this.countId = countId;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public LoginLogBean(int countId, Date loginTime, String loginIp) {
		super();
		this.countId = countId;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
	}
	public LoginLogBean() {
		super();
	}
	
}
