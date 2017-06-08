package cn.gyyx.action.beans.xwbcreditsluckydraw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	// 服务器ID
	@JsonProperty(value="Code")
	private Integer code;
	// 游戏信息
	@JsonProperty(value="Game")
	private Game game;
	// 线路ID 1：网通 2：电信 3：双线
	@JsonProperty(value="NetTypeCode")
	private Integer netTypeCode;
	// 线路ID 1:安卓 2:IOS官方 3:IOS越狱
	@JsonProperty(value="PlatFormTypeCode")
	private Integer platFormTypeCode;
	// 服务器名称
	@JsonProperty(value="ServerName")
	private String serverName;
	// 服务器IP
	@JsonProperty(value="ServerIp")
	private String serverIp;
	// 服务器端口
	@JsonProperty(value="ServerPort")
	private Integer serverPort;
	// 是否打开
	@JsonProperty(value="IsOpen")
	private boolean isOpen;
	// 是否可以充值
	@JsonProperty(value="IsCash")
	private boolean isCash;
	// 是否可以激活
	@JsonProperty(value="IsActivation")
	private boolean isActivation;
	// 是否游戏客户端显示
	@JsonProperty(value="IsClientOpen")
	private boolean isClientOpen;
	// IDC机房IP
	@JsonProperty(value="IdcIp")
	private String idcIp;
	// 服务器是否有效
	@JsonProperty(value="IsValid")
	private boolean isValid;
	// 服务器唯一标识（页游专用）
	@JsonProperty(value="ServerMark")
	private String serverMark;
	// 服务器是否需要角色，主要是兑换时，是否需要验证角色
	@JsonProperty(value="IsNeedRole")
	private boolean isNeedRole;
	// 是否为新开服务器
	@JsonProperty(value="IsNewServer")
	private boolean isNewServer;
	// 是否有新手卡
	@JsonProperty(value="IsHasNewPlayerCard")
	private boolean isHasNewPlayerCard;
	// 服务器状态枚举 Full:爆满 Good:良好 Maintain:维护
	@JsonProperty(value="ServerStatus")
	private String serverStatus;
	// 服务器地址
	@JsonProperty(value="ServerUrl")
	private String serverUrl;
	// 开服时间
	@JsonProperty(value="OpenServerTime")
	private String openServerTime;
	// 排序标识
	@JsonProperty(value="ServerOrder")
	private Integer serverOrder;
	// 服务器区组
	@JsonProperty(value="AreaName")
	private String areaName;
	// 合服到哪个服务器
	@JsonProperty(value="UnionServerName")
	private String unionServerName;
	// 是否推荐区组
	@JsonProperty(value="IsHot")
	private boolean isHot;
	// 服务器监控状态 normal：正常，unusual：异常，novalid：无效
	@JsonProperty(value="ServerMonitorStatus")
	private String serverMonitorStatus;
	// 服务器监控状态：是否在维护中
	@JsonProperty(value="IsMaintain")
	private boolean isMaintain;
	// 服务器监控状态更改时间
	@JsonProperty(value="UpdateTime")
	private String updateTime;

	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Integer getNetTypeCode() {
		return netTypeCode;
	}

	public void setNetTypeCode(Integer netTypeCode) {
		this.netTypeCode = netTypeCode;
	}

	public Integer getPlatFormTypeCode() {
		return platFormTypeCode;
	}

	public void setPlatFormTypeCode(Integer platFormTypeCode) {
		this.platFormTypeCode = platFormTypeCode;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isCash() {
		return isCash;
	}

	public void setCash(boolean isCash) {
		this.isCash = isCash;
	}

	public boolean isActivation() {
		return isActivation;
	}

	public void setActivation(boolean isActivation) {
		this.isActivation = isActivation;
	}

	public boolean isClientOpen() {
		return isClientOpen;
	}

	public void setClientOpen(boolean isClientOpen) {
		this.isClientOpen = isClientOpen;
	}

	public String getIdcIp() {
		return idcIp;
	}

	public void setIdcIp(String idcIp) {
		this.idcIp = idcIp;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getServerMark() {
		return serverMark;
	}

	public void setServerMark(String serverMark) {
		this.serverMark = serverMark;
	}

	public boolean isNeedRole() {
		return isNeedRole;
	}

	public void setNeedRole(boolean isNeedRole) {
		this.isNeedRole = isNeedRole;
	}

	public boolean isNewServer() {
		return isNewServer;
	}

	public void setNewServer(boolean isNewServer) {
		this.isNewServer = isNewServer;
	}

	public boolean isHasNewPlayerCard() {
		return isHasNewPlayerCard;
	}

	public void setHasNewPlayerCard(boolean isHasNewPlayerCard) {
		this.isHasNewPlayerCard = isHasNewPlayerCard;
	}

	public String getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getOpenServerTime() {
		return openServerTime;
	}

	public void setOpenServerTime(String openServerTime) {
		this.openServerTime = openServerTime;
	}

	public Integer getServerOrder() {
		return serverOrder;
	}

	public void setServerOrder(Integer serverOrder) {
		this.serverOrder = serverOrder;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getUnionServerName() {
		return unionServerName;
	}

	public void setUnionServerName(String unionServerName) {
		this.unionServerName = unionServerName;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public String getServerMonitorStatus() {
		return serverMonitorStatus;
	}

	public void setServerMonitorStatus(String serverMonitorStatus) {
		this.serverMonitorStatus = serverMonitorStatus;
	}

	public boolean isMaintain() {
		return isMaintain;
	}

	public void setMaintain(boolean isMaintain) {
		this.isMaintain = isMaintain;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}