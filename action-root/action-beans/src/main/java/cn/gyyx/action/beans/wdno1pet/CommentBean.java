/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午3:28:54
 * 版本号：v1.0
 * 本类主要用途叙述：这是评论的实体
 */

package cn.gyyx.action.beans.wdno1pet;

import java.sql.Timestamp;

public class CommentBean {
	// 评论的主键
	private int commentCode;
	// 作品主键
	private int petCode;
	// 昵称
	private String nickName;
	// 评论的内容
	private String commentContent;
	// 评论的时间
	private Timestamp commentTime;
	// 评论的状态
	private String commentStatus;
	// 评论的IP地址
	private int CommentIp;

	/**
	 * 得到评论的主键
	 * 
	 * @return commentCode
	 */
	public int getCommentCode() {
		return commentCode;
	}

	/**
	 * 设定评论的主键
	 * 
	 * @param commentCode
	 */
	public void setCommentCode(int commentCode) {
		this.commentCode = commentCode;
	}

	/**
	 * 得到作品的主键
	 * 
	 * @return petCode
	 */
	public Integer getPetCode() {
		return petCode;
	}

	/**
	 * 设定作品的主键
	 * 
	 * @param petCode
	 */
	public void setPetCode(Integer petCode) {
		this.petCode = petCode;
	}

	/**
	 * 得到昵称
	 * 
	 * @return nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 设定昵称
	 * 
	 * @param nickName
	 */

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 得到评论的内容
	 * 
	 * @return commentContent
	 */
	public String getCommentContent() {
		return commentContent;
	}

	/**
	 * 设定评论的内容
	 * 
	 * @param commentContent
	 */
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	/**
	 * 得到评论的时间
	 * 
	 * @return commentTime
	 */
	public Timestamp getCommentTime() {
		return commentTime;
	}

	/**
	 * 设定评论的时间
	 * 
	 * @param commentTime
	 */
	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	/**
	 * 得到评论的状态
	 * 
	 * @return commentStatus
	 */
	public String getCommentStatus() {
		return commentStatus;
	}

	/**
	 * 设定评论的状态
	 * 
	 * @param commentStatus
	 */
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	/**
	 * 得到评论发出者的IP
	 * 
	 * @return
	 */
	public Integer getCommentIp() {
		return CommentIp;
	}

	/**
	 * 设定IP
	 * 
	 * @param ip
	 */
	public void setCommentIp(Integer ip) {
		this.CommentIp = ip;
	}

}
