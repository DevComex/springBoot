package cn.gyyx.action.beans.xwbcreditsluckydraw;

public class NewPageBean {
	private int pageIndex = 1; // 需要显示的页码 当前页
	private int totalPages = 1; // 总页数
	private int pageSize = 10; // 每页记录数
	private int totalRecords = 0; // 总记录数
	private boolean isHavePrePage = false; // 是否有上一页
	private boolean isHaveNextPage = false; // 是否有下一页
	private String recommendTags;
	private String missionType;
	private String missionCloseStatus;
	private int code;
	private String account;
	private String dateS;
	private String prizeName;
	private int activityCode;
	
	

	public int getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(int activityCode) {
		this.activityCode = activityCode;
	}

	public String getAccount() {
		return account== null?"":account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDateS() {
		return dateS== null?"":dateS;
	}

	public void setDateS(String dateS) {
		this.dateS = dateS;
	}

	public String getPrizeName() {
		return prizeName== null?"":prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setHavePrePage(boolean isHavePrePage) {
		this.isHavePrePage = isHavePrePage;
	}

	public void setHaveNextPage(boolean isHaveNextPage) {
		this.isHaveNextPage = isHaveNextPage;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public String getRecommendTags() {
		return recommendTags== null?"":recommendTags;
	}

	public void setRecommendTags(String recommendTags) {
		this.recommendTags = recommendTags;
	}

	public String getMissionType() {
		return missionType== null?"":missionType;
	}

	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}

	public String getMissionCloseStatus() {
		return missionCloseStatus== null?"":missionCloseStatus;
	}

	public void setMissionCloseStatus(String missionCloseStatus) {
		this.missionCloseStatus = missionCloseStatus;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		if (totalRecords < 0) {
			throw new RuntimeException("总记录数不能小于0!");
		}
		// 设置总记录数
		this.totalRecords = totalRecords;
		// 计算总页数
		this.totalPages = this.totalRecords / this.pageSize;
		if (this.totalRecords % this.pageSize != 0) {
			this.totalPages++;
		}
		// 计算是否有上一页
		if (this.pageIndex > 1) {
			this.isHavePrePage = true;
		} else {
			this.isHavePrePage = false;
		}
		// 计算是否有下一页
		if (this.pageIndex < this.totalPages) {
			this.isHaveNextPage = true;
		} else {
			this.isHaveNextPage = false;
		}
	}

	public int getTotalPages() {
		return totalPages;
	}

	public boolean getIsHavePrePage() {
		return isHavePrePage;
	}

	public boolean getIsHaveNextPage() {
		return isHaveNextPage;
	}

}