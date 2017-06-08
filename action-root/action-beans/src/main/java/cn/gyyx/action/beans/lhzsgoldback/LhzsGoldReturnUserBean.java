package cn.gyyx.action.beans.lhzsgoldback;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年12月16日 下午12:52:22
 * 描        述：用户信息
 */
public class LhzsGoldReturnUserBean {
	private int code;
	private String account;
	private int userId;
	private Long remainGoldCount;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Long getRemainGoldCount() {
		return remainGoldCount;
	}
	public void setRemainGoldCount(Long remainGoldCount) {
		this.remainGoldCount = remainGoldCount;
	}
	@Override
	public String toString() {
		return "LhzsGoldReturnUserBean [code=" + code + ", account=" + account
				+ ", userId=" + userId + ", remainGoldCount=" + remainGoldCount
				+ "]";
	}
	
}
