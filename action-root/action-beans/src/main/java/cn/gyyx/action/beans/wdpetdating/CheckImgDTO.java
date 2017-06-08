package cn.gyyx.action.beans.wdpetdating;

public class CheckImgDTO {
	private String account;
	private String area;
	private String netType;
	private String status;
	private int pageIndex;
	private Integer actionCode;
	private Integer count;
	private boolean beginPage;
	private boolean lastPage;
	private String sDate;
	
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public boolean isBeginPage() {
		return beginPage;
	}
	public void setBeginPage(boolean beginPage) {
		this.beginPage = beginPage;
	}
	public boolean isLastPage() {
		return lastPage;
	}
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getActionCode() {
		return actionCode;
	}
	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}
	public String getAccount() {
		return account == null?"":account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getArea() {
		return area==null?"":area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getStatus() {
		return status==null?"":status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNetType() {
		return netType==null?"":netType;
	}
	public void setNetType(String netType) {
		this.netType = netType;
	}
	
}
