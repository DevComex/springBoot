/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年8月11日 
 * @版本号：
 * @本类主要用途描述：问道康师傅推广活动注册信息实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdmaster;

public class WDMasterRegisterInfoBean {
	//邮箱
	String email;
	//身份证号码
	String idcard;
	//密码
	String password;
	//确认密码
	String passwordCompare;
	//性别(0/1)
	Integer sex;
	//来源类型
	String sourceType;
	//真实姓名
	String truename;
	//用户名(通行账号)
	String userName;
	//游戏Id
	Integer gameId;
	//推广员
	String extender;
	//推广码
	String spreadingCode;
	
	public String getSpreadingCode() {
		return spreadingCode;
	}
	public void setSpreadingCode(String spreadingCode) {
		this.spreadingCode = spreadingCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordCompare() {
		return passwordCompare;
	}
	public void setPasswordCompare(String passwordCompare) {
		this.passwordCompare = passwordCompare;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public String getExtender() {
		return extender;
	}
	public void setExtender(String extender) {
		this.extender = extender;
	}
	public WDMasterRegisterInfoBean() {
		super();
		this.sourceType = "web";
		this.sex = 0;
	}
	public WDMasterRegisterInfoBean(String email, String idcard,
			String password, String passwordCompare, String truename,
			String userName, Integer gameId, String extender) {
		super();
		this.email = email;
		this.idcard = idcard;
		this.password = password;
		this.passwordCompare = passwordCompare;
		this.truename = truename;
		this.userName = userName;
		this.gameId = gameId;
		this.extender = extender;
		this.sourceType = "web";
		this.sex = 0;
	}
	@Override
	public String toString() {
		return "WDMasterRegisterInfoBean [email=" + email + ", idcard="
				+ idcard + ", password=" + password + ", passwordCompare="
				+ passwordCompare + ", sex=" + sex + ", sourceType="
				+ sourceType + ", truename=" + truename + ", userName="
				+ userName + ", gameId=" + gameId + ", extender=" + extender
				+ "]";
	}
	
	
}
