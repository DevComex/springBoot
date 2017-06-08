package cn.gyyx.action.beans.wdoldplayers;

import java.util.List;


public class NewPageBean<T> {
	private int pageIndex = 1; // 需要显示的页码 当前页
	private int totalPages = 1; // 总页数
	private int pageSize = 10; // 每页记录数
	private int totalRecords = 0; // 总记录数
	private boolean isHavePrePage = false; // 是否有上一页
	private boolean isHaveNextPage = false; // 是否有下一页
	private String account;
    private int flag;
    private String startDate;
	private String endDate;
	private String role;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	// 分页数据
	private List<T> list;


	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
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

 
 
	public int getPageIndex() {
		return pageIndex;
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