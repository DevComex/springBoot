/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日上午11:13:31
 * 版本号：v1.0
 * 本类主要用途叙述：机会更改日志的实体
 */

package cn.gyyx.action.beans.lottery;

import java.util.Date;

public class LuckyDrawLogBean {
	// 主键
	private int code;
	// 建立时间
	private Date createTime;
	// 所增加的次数
	private int drawCount;
	// 来源
	// 1.报名审核：signUserInfo
	// 2.挑战别人   challengesomeone
	// 3.抽奖：lottery
	private String source;
	// 账户
	private String account;
	// 用户主键
	private int userId;
	//活动主键
	private int actionCode;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDrawCount() {
		return drawCount;
	}

	public void setDrawCount(int drawCount) {
		this.drawCount = drawCount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	@Override
	public String toString() {
		return "LuckyDrawLogBean [code=" + code + ", createTime=" + createTime
				+ ", drawCount=" + drawCount + ", source=" + source
				+ ", account=" + account + ", userId=" + userId
				+ ", actionCode=" + actionCode + "]";
	}

}
