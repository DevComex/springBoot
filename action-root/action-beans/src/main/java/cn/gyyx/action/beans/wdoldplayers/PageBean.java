package cn.gyyx.action.beans.wdoldplayers;

import java.util.List;


public class PageBean<T> {
	private int pageIndex = 1; // 需要显示的页码 当前页
	private int totalPages = 1; // 总页数
	private int pageSize = 9; // 每页记录数
	private int totalRecords = 0; // 总记录数
	private boolean isHavePrePage = false; // 是否有上一页
	private boolean isHaveNextPage = false; // 是否有下一页
	private int area;
	private int serverId;
	private String serverName;
	

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
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