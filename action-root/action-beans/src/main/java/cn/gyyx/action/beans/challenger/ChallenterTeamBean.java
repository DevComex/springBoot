
package cn.gyyx.action.beans.challenger;

import java.util.Date;

public class ChallenterTeamBean {
	// 用户主键
	private int code;
	// 队伍名称
	private String teamName;
	// 联系方式类型
	private String contactType;
	// 联系方式
	private String contactName;
	// 创建时间
	private Date createTime;
	// 状态（oncheck 待审核 pass 通过  unpass 忽略）
	private String state;
	// 队伍目标
	private String target;
	// 队伍宣言
	private String declaration;
	// 用户id
	private int userId;
	// 账号名称
	private String account;
	// 申请次数
	private int applyCount;
	private Date reviewTime;
	
	private int currentPage;
	private int pageSize;
	
	private String teamMember;//、隔开
	private String roleName;//角色名称
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return the declaration
	 */
	public String getDeclaration() {
		return declaration;
	}
	/**
	 * @param declaration the declaration to set
	 */
	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the applyCount
	 */
	public int getApplyCount() {
		return applyCount;
	}
	/**
	 * @param applyCount the applyCount to set
	 */
	public void setApplyCount(int applyCount) {
		this.applyCount = applyCount;
	}
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the teamMember
	 */
	public String getTeamMember() {
		return teamMember;
	}
	/**
	 * @param teamMember the teamMember to set
	 */
	public void setTeamMember(String teamMember) {
		this.teamMember = teamMember;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the contactType
	 */
	public String getContactType() {
		return contactType;
	}
	/**
	 * @param contactType the contactType to set
	 */
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	/**
	 * @return the reviewTime
	 */
	public Date getReviewTime() {
		return reviewTime;
	}
	/**
	 * @param reviewTime the reviewTime to set
	 */
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	
}
