package cn.gyyx.action.beans.cs2sign;


public class Cs2BindInfoBean {
	/** 
	* 主键
	*/
	private int code;
	/** 
	* 服务器
	*/
	private String server;
	/**
	 * IP
	 */
	private String serverIp;
	/**
	 * 端口
	 */
	private String serverPort;
	/** 
	* 角色名
	*/
	private String character;
	/** 
	* 关联信息主键
	*/
	private String account;
	
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	/** 
	* 设置 主键
	*/
	public void setCode(int code){
 		this.code = code;
	}
	/** 
	* 获得 主键
	*/
	public int getCode(){
		return this.code;
	}
	/** 
	* 设置 服务器
	*/
	public void setServer(String server){
 		this.server = server;
	}
	/** 
	* 获得 服务器
	*/
	public String getServer(){
		return this.server;
	}
	/** 
	* 设置 角色名
	*/
	public void setCharacter(String character){
 		this.character = character;
	}
	/** 
	* 获得 角色名
	*/
	public String getCharacter(){
		return this.character;
	}
	/** 
	* 设置 关联信息主键
	*/
	public void setAccount(String account){
 		this.account = account;
	}
	/** 
	* 获得 关联信息主键
	*/
	public String getAccount(){
		return this.account;
	}
	
	@Override
	public String toString() {
		return "[" + account + "," + server + "," + character + "]";
	}
}