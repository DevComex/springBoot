package cn.gyyx.action.beans.wd10yearcoser;

import java.util.Date;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月9日 下午1:08:01
 * 描        述：
 */
public class CoserNovice {
	// 主键
	private int code;
	// 标题
	private String title;
	// 内容
	private String content;
	// 是否发布
	private String isPub;
	// 创建时间
	private Date createTime;
	private Date pubTime;
	
	private String time;
	
	private int currentPage;
	private int pageSize;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsPub() {
		return isPub;
	}
	public void setIsPub(String isPub) {
		this.isPub = isPub;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
	
}
