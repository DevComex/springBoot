/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2015年3月20日 上午11:22:54
 * @版本号：
 * @本类主要用途描述：服务器信息实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdninestory;

public class ServerBean {

	private int code;
	private String serverName;
	private Integer serverId;

	public ServerBean() {

	}

	public ServerBean(int code, String serverName) {
		this.code = code;
		this.serverName = serverName;
	}

	public ServerBean(int code, String serverName, Integer serverId) {
		super();
		this.code = code;
		this.serverName = serverName;
		this.serverId = serverId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}


}
