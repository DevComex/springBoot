package cn.gyyx.action.beans.wd10yearcoser;

import java.util.Date;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月9日 下午1:08:01
 * 描        述：
 */
public class CoserOfficialVideo {
	// 主键
	private int code;
	// 作品名称
	private String worksName;
	// 作品外链
	private String worksUrl;
	// 封面图片
	private String worksPic;
	// 是否推荐
	// 作品简介
	private String isTop;
	private String content;
	// 点赞次数
	private Integer favoriteNum;
	// 点击量
	private Integer clickNum;
	// 创建时间
	private Date createTime;
	private Date commendTime;
	
	private boolean flag;
	private int pageIndex;
	private int pageSize;

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getWorksName() {
		return worksName;
	}
	public void setWorksName(String worksName) {
		this.worksName = worksName;
	}
	public String getWorksUrl() {
		return worksUrl;
	}
	public void setWorksUrl(String worksUrl) {
		this.worksUrl = worksUrl;
	}
	public String getWorksPic() {
		return worksPic;
	}
	public void setWorksPic(String worksPic) {
		this.worksPic = worksPic;
	}
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getFavoriteNum() {
		return favoriteNum;
	}
	public void setFavoriteNum(Integer favoriteNum) {
		this.favoriteNum = favoriteNum;
	}
	public Integer getClickNum() {
		return clickNum;
	}
	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCommendTime() {
		return commendTime;
	}
	public void setCommendTime(Date commendTime) {
		this.commendTime = commendTime;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
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
}
