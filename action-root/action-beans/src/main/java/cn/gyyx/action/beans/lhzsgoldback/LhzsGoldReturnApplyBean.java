package cn.gyyx.action.beans.lhzsgoldback;

import java.util.Date;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年12月16日 下午12:52:22
 * 描        述：申请
 */
public class LhzsGoldReturnApplyBean {
	private int code;
	private String account;
	private String ipAddr;
	private Date createTime;
	private Long applyGoldCount;
	
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
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getApplyGoldCount() {
		return applyGoldCount;
	}
	public void setApplyGoldCount(Long applyGoldCount) {
		this.applyGoldCount = applyGoldCount;
	}
	public LhzsGoldReturnApplyBean(String account, String ipAddr,
			Date createTime, Long applyGoldCount) {
		super();
		this.account = account;
		this.ipAddr = ipAddr;
		this.createTime = createTime;
		this.applyGoldCount = applyGoldCount;
	}
	@Override
	public String toString() {
		return "LhzsGoldReturnApplyBean [code=" + code + ", account=" + account
				+ ", ipAddr=" + ipAddr + ", createTime=" + createTime
				+ ", applyGoldCount=" + applyGoldCount + "]";
	}
	
}
