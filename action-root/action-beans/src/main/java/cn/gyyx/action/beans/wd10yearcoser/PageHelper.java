package cn.gyyx.action.beans.wd10yearcoser;

public class PageHelper extends ResourceBean {
	
	private int pageSize;
	private int pageRows;
	private String name;
	private String type;
	private Integer userCode;
	
	public PageHelper() {
		super();
	}
	public PageHelper(int pageSize, int pageRows, String name, Integer userCode) {
		super();
		this.pageSize = pageSize;
		this.pageRows = pageRows;
		this.name = name;
		this.userCode = userCode;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageRows() {
		return pageRows;
	}
	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserCode() {
		return userCode;
	}
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
