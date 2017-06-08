package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Arrays;
import java.util.List;

public class ListPageBean<T> {
	private List<T> list;
	private int[] pageArray;
	private int currentPage;
	private int TotalCount;

	public ListPageBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public ListPageBean(List<T> list, int[] pageArray, int currentPage,
			int totalCount) {
		super();
		this.list = list;
		this.pageArray = pageArray;
		this.currentPage = currentPage;
		TotalCount = totalCount;
	}



	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int[] getPageArray() {
		return pageArray;
	}

	public void setPageArray(int[] pageArray) {
		this.pageArray = pageArray;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}



	public int getTotalCount() {
		return TotalCount;
	}



	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}



	@Override
	public String toString() {
		return "ListPageBean [list=" + list + ", pageArray="
				+ Arrays.toString(pageArray) + ", currentPage=" + currentPage
				+ ", TotalCount=" + TotalCount + "]";
	}

	

}
