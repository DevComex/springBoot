/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动账号实体bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdtenyearfans;


public class WdAccountInfoBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 账号名称
	*/
	private String accountName;
	/** 
	* 大区
	*/
	private int area;
	/** 
	* 区服id
	*/
	private int serverId;
	/** 
	* 区服名称
	*/
	private String serverName;
	/** 
	* 绑定角色名称
	*/
	private String roleName;
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
	* 设置 area
	*/
	public void setArea(int area){
 		this.area = area;
	}
	/** 
	* 获得 area
	*/
	public int getArea(){
		return this.area;
	}
	/** 
	* 设置 server_id
	*/
	public void setServerId(int serverId){
 		this.serverId = serverId;
	}
	/** 
	* 获得 server_id
	*/
	public int getServerId(){
		return this.serverId;
	}
	/** 
	* 设置 server_name
	*/
	public void setServerName(String serverName){
 		this.serverName = serverName;
	}
	/** 
	* 获得 server_name
	*/
	public String getServerName(){
		return this.serverName;
	}
	/** 
	* 设置 role_name
	*/
	public void setRoleName(String roleName){
 		this.roleName = roleName;
	}
	/** 
	* 获得 role_name
	*/
	public String getRoleName(){
		return this.roleName;
	}
	
}