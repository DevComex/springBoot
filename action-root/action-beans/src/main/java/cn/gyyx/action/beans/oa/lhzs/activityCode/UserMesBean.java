package cn.gyyx.action.beans.oa.lhzs.activityCode;

/**
 * 统计获取数据Bean
 * @author Administrator
 *
 */
public class UserMesBean {
	private int code;
	private String activationCode;
	private int userId;
	private String activationTime;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getActivationTime() {
		return activationTime;
	}
	public void setActivationTime(String activationTime) {
		this.activationTime = activationTime;
	}
	
}
