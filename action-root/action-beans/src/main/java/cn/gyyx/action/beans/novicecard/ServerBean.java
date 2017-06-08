/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月8日 下午4:10:41
 * @版本号：
 * @本类主要用途描述：服务器实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.novicecard;


public class ServerBean {
	private int code;
	private Integer gameId;
	private Integer serverId;
	private String serverName;
	private boolean state;
	private Integer areaId;
	private Integer batchNo;
	
	public ServerBean() {
		
	}
	
	public ServerBean(Integer gameId, boolean state, Integer areaId, Integer batchNo) {
		this.gameId = gameId;
		this.state = state;
		this.areaId = areaId;
		this.batchNo = batchNo;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

	
	
}
