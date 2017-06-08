/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:53:27
 * @版本号：
 * @本类主要用途描述：评论消息实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.Date;

public class CommentsBean {

	// 主键编号
	private int code;
	// 评论者账号
	private String commentAccount;
	// 针对素材编号
	private Integer materialCode;
	// 内容
	private String content;
	// 内容
		private String contentSub;
	// 时间
	private Date commentDate;
	
	private String commentDateStr;
	
	// 
	private Integer isBest;
	// 
	private String ipAddress;
	// 
	private Integer isDelete;
	//
	private String materialName;
	//
	private String materialType;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCommentAccount() {
		return commentAccount;
	}

	public void setCommentAccount(String commentAccount) {
		this.commentAccount = commentAccount;
	}
	public Integer getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(Integer materialCode) {
		this.materialCode = materialCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	

	public String getContentSub() {
		return contentSub;
	}

	public void setContentSub(String contentSub) {
		this.contentSub = contentSub;
	}

	public String getCommentDateStr() {
		return commentDateStr;
	}

	public void setCommentDateStr(String commentDateStr) {
		this.commentDateStr = commentDateStr;
	}

	

	public Integer getIsBest() {
		return isBest;
	}

	public void setIsBest(Integer isBest) {
		this.isBest = isBest;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}




	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public CommentsBean(Integer materialCode) {
		super();
		this.materialCode = materialCode;
	}

	public CommentsBean() {
		super();
	}

	public CommentsBean(String commentAccount, Integer materialCode,
			String content, Date commentDate, Integer isBest, String ipAddress,
			Integer isDelete) {
		super();
		this.commentAccount = commentAccount;
		this.materialCode = materialCode;
		this.content = content;
		this.commentDate = commentDate;
		this.isBest = isBest;
		this.ipAddress = ipAddress;
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "CommentsBean [commentAccount=" + commentAccount
				+ ", materialCode=" + materialCode + ", content=" + content
				+ ", commentDate=" + commentDate + ", commentDateStr="
				+ commentDateStr + ", isBest=" + isBest + ", ipAddress="
				+ ipAddress + ", isDelete=" + isDelete + ", materialName="
				+ materialName + ", materialType=" + materialType + "]";
	}
	
	
	
}
