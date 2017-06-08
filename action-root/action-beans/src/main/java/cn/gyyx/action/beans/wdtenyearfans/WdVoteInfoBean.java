/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动关注拉黑实体bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdtenyearfans;

import java.util.Date;

public class WdVoteInfoBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 账号
	*/
	private String accountName;
	/** 
	* 类型（0为关注，1为拉黑）
	*/
	private int voteType;
	/** 
	* 日期
	*/
	private Date date;
	/** 
	* 所操作提名角色主键
	*/
	private int nominationCode;
	/** 
	* ip
	*/
	private String voteIp;
	/** 
	* 设置 code
	*/
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
	* 设置 vote_type
	*/
	public void setVoteType(int voteType){
 		this.voteType = voteType;
	}
	/** 
	* 获得 vote_type
	*/
	public int getVoteType(){
		return this.voteType;
	}
	/** 
	* 设置 date
	*/
	public void setDate(Date date){
 		this.date = date;
	}
	/** 
	* 获得 date
	*/
	public Date getDate(){
		return this.date;
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
	* 设置 vote_ip
	*/
	public void setVoteIp(String voteIp){
 		this.voteIp = voteIp;
	}
	/** 
	* 获得 vote_ip
	*/
	public String getVoteIp(){
		return this.voteIp;
	}
}