package cn.gyyx.action.beans.wdpetdating;

import java.util.List;

public class PageResult {
	int crtPageNum;
	int maxNum;
	List<WdDatingPet> listData;
	public int getCrtPageNum() {
		return crtPageNum;
	}
	public void setCrtPageNum(int crtPageNum) {
		this.crtPageNum = crtPageNum;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public List<WdDatingPet> getListData() {
		return listData;
	}
	public void setListData(List<WdDatingPet> listData) {
		this.listData = listData;
	}
	
}
