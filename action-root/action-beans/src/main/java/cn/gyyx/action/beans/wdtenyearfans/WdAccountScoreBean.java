/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动积分实体bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdtenyearfans;


public class WdAccountScoreBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 账号
	*/
	private String accountName;
	/** 
	* 积分
	*/
	private int score;
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
	* 设置 score
	*/
	public void setScore(int score){
 		this.score = score;
	}
	/** 
	* 获得 score
	*/
	public int getScore(){
		return this.score;
	}
}