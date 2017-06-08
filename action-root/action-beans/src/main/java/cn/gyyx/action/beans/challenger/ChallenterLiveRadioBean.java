/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午5:52:04
 * 版本号：v1.0
 * 本类主要用途叙述：用户报名信息的数据库接口
 */

package cn.gyyx.action.beans.challenger;

import java.util.Date;

public class ChallenterLiveRadioBean {
	// 主键
	private int code;
	// 图片地址
	private String picUrl;
	// 视频地址
	private String radioUrl;
	// 是否显示推荐位置
	private String isTop;
	// 创建时间
	private Date createTime;
	// 是否删除(Y 删除 空表示不删除)
	private String isDelete;
	// 标题
	private String title;
	
	private int currentPage;
	private int pageSize;
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * @return the radioUrl
	 */
	public String getRadioUrl() {
		return radioUrl;
	}
	/**
	 * @param radioUrl the radioUrl to set
	 */
	public void setRadioUrl(String radioUrl) {
		this.radioUrl = radioUrl;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the isDelete
	 */
	public String getIsDelete() {
		return isDelete;
	}
	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the isTop
	 */
	public String getIsTop() {
		return isTop;
	}
	/**
	 * @param isTop the isTop to set
	 */
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	
}
