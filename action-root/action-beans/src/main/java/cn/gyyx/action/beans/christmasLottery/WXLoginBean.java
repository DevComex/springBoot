package cn.gyyx.action.beans.christmasLottery;

import java.util.Date;

public class WXLoginBean {
		private int code;
		private int actionCode;
		private String openId;
		private String account;
		private int userId;
		private Date loginTime;
		private String remark;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public int getActionCode() {
			return actionCode;
		}
		public void setActionCode(int actionCode) {
			this.actionCode = actionCode;
		}
		public String getOpenId() {
			return openId;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
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
		public Date getLoginTime() {
			return loginTime;
		}
		public void setLoginTime(Date loginTime) {
			this.loginTime = loginTime;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		@Override
		public String toString() {
			return "WXLoginBean [code=" + code + ", actionCode=" + actionCode + ", openId=" + openId + ", account="
					+ account + ", userId=" + userId + ", loginTime=" + loginTime + ", remark=" + remark + "]";
		}
		
}
