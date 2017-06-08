
/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午1:17:42
 * 版本号：v1.0
 * 本类主要用途叙述：发起挑战信息的实体
 */

package cn.gyyx.action.beans.challenger;

import java.util.Date;

public class ChallenterInfoBean {
	// 主键
	private int code;
	// 曾经角色
	private String oldRole;
	// 宣言
	private String declaration;
	// 时间
	private Date createTime;
	// 状态
	private String state;
	// 用户主键
	private int userId;
	// 账户
	private String account;
	// 挑被战次数
	private int dareCount;
	
	private String roleName;
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getOldRole() {
		return oldRole;
	}

	public void setOldRole(String oldRole) {
		this.oldRole = oldRole;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getDareCount() {
		return dareCount;
	}

	public void setDareCount(int dareCount) {
		this.dareCount = dareCount;
	}

	@Override
	public String toString() {
		return "ChallenterInfoBean [code=" + code + ", oldRole=" + oldRole
				+ ", declaration=" + declaration + ", createTime=" + createTime
				+ ", state=" + state + ", userId=" + userId + ", account="
				+ account + ", dareCount=" + dareCount + "]";
	}
}


