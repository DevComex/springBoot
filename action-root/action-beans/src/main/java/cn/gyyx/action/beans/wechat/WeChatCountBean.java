/*************************************************
       Copyright ©, 2015, GY Game
       Author: 范永良
       Created: 2015年-12月-23日
       Note:统计Bean
************************************************/
package cn.gyyx.action.beans.wechat;

import java.util.Date;

public class WeChatCountBean {
	private Integer code;
	private String countType;
	private String nickName;
	private Integer actionCode;
	private Date countTime;
	private Integer partakeTime;

	private String S1;
	private String S2;
	private String S3;
	private String S4;
	private String S5;
	private String S6;

	public WeChatCountBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeChatCountBean(String countType, String nickName,
			Integer actionCode, Date countTime, Integer partakeTime) {
		super();
		this.countType = countType;
		this.nickName = nickName;
		this.actionCode = actionCode;
		this.countTime = countTime;
		this.partakeTime = partakeTime;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getActionCode() {
		return actionCode;
	}

	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}

	public Date getCountTime() {
		return countTime;
	}

	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}

	public Integer getPartakeTime() {
		return partakeTime;
	}

	public void setPartakeTime(Integer partakeTime) {
		this.partakeTime = partakeTime;
	}

	public String getS1() {
		return S1;
	}

	public void setS1(String s1) {
		S1 = s1;
	}

	public String getS2() {
		return S2;
	}

	public void setS2(String s2) {
		S2 = s2;
	}

	public String getS3() {
		return S3;
	}

	public void setS3(String s3) {
		S3 = s3;
	}

	public String getS4() {
		return S4;
	}

	public void setS4(String s4) {
		S4 = s4;
	}

	public String getS5() {
		return S5;
	}

	public void setS5(String s5) {
		S5 = s5;
	}

	public String getS6() {
		return S6;
	}

	public void setS6(String s6) {
		S6 = s6;
	}

	@Override
	public String toString() {
		return "WeChatCountBean [countType=" + countType + ", nickName="
				+ nickName + ", actionCode=" + actionCode + ", countTime="
				+ countTime + ", partakeTime=" + partakeTime + ", S1=" + S1
				+ ", S2=" + S2 + ", S3=" + S3 + ", S4=" + S4 + ", S5=" + S5
				+ ", S6=" + S6 + "]";
	}

}
