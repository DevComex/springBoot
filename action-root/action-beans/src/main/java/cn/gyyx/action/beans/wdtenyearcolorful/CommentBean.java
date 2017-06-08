/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：bozhencheng
 * @联系方式：bozhencheng@gyyx.cn
 * @创建时间：2016年5月24日 
 * @版本号：
 * @本类主要用途描述： 问道十年缤纷活动
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdtenyearcolorful;

import java.util.Date;

/**
 * @ClassName: CommentBean
 * @description 评论bean
 * @author bozhencheng
 * @date 2016年5月24日
 */
public class CommentBean {

	public static final Integer INIT = 1;
	public static final Integer PASS = 2;
	public static final Integer FAIL = 3;
	
	
	/**
	 * 
	 */
	public CommentBean() {
		super();
	}

	/**
	 * 主键
	 */
	private int code;
	
	/**
	 * 评论内容
	 */
	private String content;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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
	 * @param content
	 * @param ip
	 * @param status
	 * @param createTime
	 */
	public CommentBean(String content, int status, Date createTime) {
		super();
		this.content = content;
		this.status = status;
		this.createTime = createTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommentBean [code=" + code + ", content=" + content + ", status=" + status + ", createTime="
				+ createTime + "]";
	}
	
}
