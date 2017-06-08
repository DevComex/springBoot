/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年9月18日上午9:40:24
 * 版本号：v1.0
 * 本类主要用途叙述：投票实体
 */

package cn.gyyx.action.beans.lottery;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VoteBean {
	// 主键
	private int code;
	// 作品主键
	private int writingCode;
	// 用户主键
	private int userCode;
	// 用户IP
	private String ip;
	// 活动主键
	private int actionCode;
	// 建立时间
	private Date creatTime;
	// 账户
	private String account;
	// 名称
	private String userName;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getWritingCode() {
		return writingCode;
	}

	public void setWritingCode(int writingCode) {
		this.writingCode = writingCode;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "VoteBean [code=" + code + ", writingCode=" + writingCode
				+ ", userCode=" + userCode + ", ip=" + ip + ", actionCode="
				+ actionCode + ", creatTime=" + creatTime + ", account="
				+ account + ", userName=" + userName + "]";
	}

	public VoteBean() {
	}

	public VoteBean(int code, int writingCode, int userCode, String ip,
			int actionCode, Date creatTime, String account, String userName) {
		this.code = code;
		this.writingCode = writingCode;
		this.userCode = userCode;
		this.ip = ip;
		this.actionCode = actionCode;
		this.creatTime = creatTime;
		this.account = account;
		this.userName = userName;
	}

	/**
	 * 建立时间格式转换
	 * 
	 * @return
	 */
	public String getStringDate() {
		if (this.creatTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String str = sdf.format(this.creatTime);
			return str;
		} else {
			return "";
		}

	}

}
