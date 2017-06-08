/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.wd9yearnovicebag;

public class ActionServerConfigBean {
	//主键
	private Integer code;
	//游戏编号
	private Integer gameId;
	//服务器id
	private Integer serverId;
	//服务器名称
	private String serverName;
	//活动编号
	private Integer activityId;
	//线路
	private Integer netTypeCode;
	//区组
	private String areaName;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
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
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getNetTypeCode() {
		return netTypeCode;
	}
	public void setNetTypeCode(Integer netTypeCode) {
		this.netTypeCode = netTypeCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public ActionServerConfigBean(Integer code, Integer gameId,
			Integer serverId, String serverName, Integer activityId,
			Integer netTypeCode, String areaName) {
		super();
		this.code = code;
		this.gameId = gameId;
		this.serverId = serverId;
		this.serverName = serverName;
		this.activityId = activityId;
		this.netTypeCode = netTypeCode;
		this.areaName = areaName;
	}
	public ActionServerConfigBean() {
		super();
	}
	
}
