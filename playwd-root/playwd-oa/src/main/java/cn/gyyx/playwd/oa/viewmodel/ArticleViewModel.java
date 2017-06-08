package cn.gyyx.playwd.oa.viewmodel;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 图文列表搜索viewmodel
 * @author yangteng
 *
 */
public class ArticleViewModel {	
	/**
	 * 文章标题 title,上传账号 account,昵称 author
	 */
	private String type;
	/**
	 * 搜索内容
	 */
	private String key;
	/**
	 * 展示状态 全部 -1,隐藏 0,展示 1
	 */
	private Integer displayStatus;
	/**
	 * 一级分类
	 */
	private Integer firstCategoryId;
	/**
	 * 二级分类
	 */
	private Integer secondCategoryId;
	/**
	 * 发布人 玩家player,官方official,全部all
	 */
	private String authorType;
	/**
	 * 开始时间
	 */
	@NotBlank(message="invalid-startTime|开始时间不能为空")
	@Pattern(regexp="((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])",message="开始时间格式不正确")
	private String startTime;
	/**
	 * 结束时间
	 */
	@NotBlank(message="invalid-endTime|结束时间不能为空")
	@Pattern(regexp="((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])",message="结束时间格式不正确")
	private String endTime;
	/**
	 * 审核状态 待审核unreviewd,审核通过(展示)passed,审核不通过failed,通过但不显示hidden,通过且推荐recommended
	 */
	private String status;
	/**
	 * 第几页
	 */	
	private Integer pageIndex;
	/**
	 * 每页显示多少条
	 */
	private Integer pageSize;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getDisplayStatus() {
		return displayStatus;
	}
	public void setDisplayStatus(Integer displayStatus) {
		this.displayStatus = displayStatus;
	}
	public Integer getFirstCategoryId() {
		return firstCategoryId;
	}
	public void setFirstCategoryId(Integer firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}
	public Integer getSecondCategoryId() {
		return secondCategoryId;
	}
	public void setSecondCategoryId(Integer secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}
	public String getAuthorType() {
		return authorType;
	}
	public void setAuthorType(String authorType) {
		this.authorType = authorType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
