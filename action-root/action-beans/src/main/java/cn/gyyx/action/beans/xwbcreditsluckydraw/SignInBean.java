/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-creditsLuckyDraw-backstage
 * @作者：mawenbin
 * @联系方式：mawenbin@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:16:53
 * @版本号：v1.204
 * @本类主要用途描述：玩家签到表
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;
/**
 * 玩家签到表
 * */
public class SignInBean {
	//主键
	private int code;
	//账号
	private String account;
	//签到时间
	private Date signInDate;
	//字符串形式签到时间
	private String strSignInDate;
	
	//查询需要
	//查询的开始时间
	private String startTime;
	//查询的结束时间
	private String endTime;
	//查找精确账号的信息
	private String accurateAccount;
	
	
	public String getAccurateAccount() {
		return accurateAccount;
	}
	public void setAccurateAccount(String accurateAccount) {
		this.accurateAccount = accurateAccount;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getStrSignInDate() {
		return strSignInDate;
	}
	public void setStrSignInDate(String strSignInDate) {
		this.strSignInDate = strSignInDate;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getSignInDate() {
		return signInDate;
	}
	public void setSignInDate(Date signInDate) {
		this.signInDate = signInDate;
	}
	public SignInBean(String startTime, String endTime, String accurateAccount) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.accurateAccount = accurateAccount;
	}
	public SignInBean() {
		super();
	}
	public SignInBean(String accurateAccount) {
		super();
		this.accurateAccount = accurateAccount;
	}
	public SignInBean(String accurateAccount ,Date signInDate) {
		super();
		this.accurateAccount = accurateAccount;
		this.signInDate = signInDate;
	}
	public SignInBean(String strSignInDate, String account) {
		super();
		this.account = account;
		this.strSignInDate = strSignInDate;
	}
	
}
