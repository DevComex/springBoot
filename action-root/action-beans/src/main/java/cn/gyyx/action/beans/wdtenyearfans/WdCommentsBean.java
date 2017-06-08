/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动评论实体bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdtenyearfans;

import java.util.Date;

public class WdCommentsBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 账号（可为空）
	*/
	private String accountName;
	/** 
	* 评论内容
	*/
	private String commentsContent;
	/** 
	* 评论日期
	*/
	private Date commentsDate;
	/** 
	* 评论的提名角色主键
	*/
	private int nominationCode;
	/** 
	* 审核标记（0为审核中，1为通过，-1为未通过）
	*/
	private int checkFlag;
	/** 
	* 设置 code
	*/
	private String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setCode(int code){
 		this.code = code;
	}
	/** 
	* 获得 code
	*/
	public int getCode(){
		return this.code;
	}
	/** 
	* 设置 account_name
	*/
	public void setAccountName(String accountName){
 		this.accountName = accountName;
	}
	/** 
	* 获得 account_name
	*/
	public String getAccountName(){
		return this.accountName;
	}
	/** 
	* 设置 comments_content
	*/
	public void setCommentsContent(String commentsContent){
 		this.commentsContent = commentsContent;
	}
	/** 
	* 获得 comments_content
	*/
	public String getCommentsContent(){
		return this.commentsContent;
	}
	/** 
	* 设置 comments_date
	*/
	public void setCommentsDate(Date commentsDate){
 		this.commentsDate = commentsDate;
	}
	/** 
	* 获得 comments_date
	*/
	public Date getCommentsDate(){
		return this.commentsDate;
	}
	/** 
	* 设置 nomination_code
	*/
	public void setNominationCode(int nominationCode){
 		this.nominationCode = nominationCode;
	}
	/** 
	* 获得 nomination_code
	*/
	public int getNominationCode(){
		return this.nominationCode;
	}
	/** 
	* 设置 check_flag
	*/
	public void setCheckFlag(int checkFlag){
 		this.checkFlag = checkFlag;
	}
	/** 
	* 获得 check_flag
	*/
	public int getCheckFlag(){
		return this.checkFlag;
	}
}