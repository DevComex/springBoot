package cn.gyyx.action.beans.xwbcreditsluckydraw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Game {

	// 游戏编号
	@JsonProperty(value="Code")
	private Integer code;
	// 游戏名称
	@JsonProperty(value="GameName")
	private String gameName;
	// 游戏全拼
	@JsonProperty(value="GameSpelling")
	private String gameSpelling;
	// 游戏所属类别
	@JsonProperty(value="GameSeriesInfo")
	private String gameSeriesInfo;
	// 游戏所属类别描述
	@JsonProperty(value="SeriesDescription")
	private String seriesDescription;
	// 游戏状态枚举
	@JsonProperty(value="GameStatus")
	private String gameStatus;
	// 游戏简介
	@JsonProperty(value="Introduce")
	private String introduce;
	// 官网地址
	@JsonProperty(value="GameWebSite")
	private String gameWebSite;
	// 新手礼包描述
	@JsonProperty(value="NewUser")
	private String newUser;
	// 开服公告信息
	@JsonProperty(value="OpenServer")
	private String openServer;
	@JsonProperty(value="LoginDomain")
	private String loginDomain;
	// 激活方式
	@JsonProperty(value="ActiveType")
	private String activeType;
	// 游戏类型
	@JsonProperty(value="GameType")
	private String gameType;
	// 
	@JsonProperty(value="ClientSecret")
	private String clientSecret;
	// 
	@JsonProperty(value="IsShow")
	private boolean isShow;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameSpelling() {
		return gameSpelling;
	}
	public void setGameSpelling(String gameSpelling) {
		this.gameSpelling = gameSpelling;
	}
	public String getGameSeriesInfo() {
		return gameSeriesInfo;
	}
	public void setGameSeriesInfo(String gameSeriesInfo) {
		this.gameSeriesInfo = gameSeriesInfo;
	}
	public String getSeriesDescription() {
		return seriesDescription;
	}
	public void setSeriesDescription(String seriesDescription) {
		this.seriesDescription = seriesDescription;
	}
	public String getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getGameWebSite() {
		return gameWebSite;
	}
	public void setGameWebSite(String gameWebSite) {
		this.gameWebSite = gameWebSite;
	}
	public String getNewUser() {
		return newUser;
	}
	public void setNewUser(String newUser) {
		this.newUser = newUser;
	}
	public String getOpenServer() {
		return openServer;
	}
	public void setOpenServer(String openServer) {
		this.openServer = openServer;
	}
	public String getLoginDomain() {
		return loginDomain;
	}
	public void setLoginDomain(String loginDomain) {
		this.loginDomain = loginDomain;
	}
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public boolean isShow() {
		return isShow;
	}
	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}
	
}
