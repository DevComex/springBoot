package cn.gyyx.playwd.beans.agent;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月14日 下午1:42:13
 * 描        述：
 */
public class GameInfoBean {
	@JsonProperty("GameWebSite")
	private String GameWebSite;
	@JsonProperty("OpenServer")
	private String OpenServer;
	@JsonProperty("Introduce")
	private String Introduce;
	@JsonProperty("GameSeriesInfo")
	private String GameSeriesInfo;
	@JsonProperty("LoginDomain")
	private String LoginDomain;
	@JsonProperty("GameStatus")
	private String GameStatus;
	@JsonProperty("NewUser")
	private String NewUser;
	@JsonProperty("SeriesDescription")
	private String SeriesDescription;
	@JsonProperty("GameSpelling")
	private String GameSpelling;
	@JsonProperty("Code")
	private int Code;
	@JsonProperty("ActiveType")
	private String ActiveType;
	@JsonProperty("GameType")
	private String GameType;
	@JsonProperty("GameName")
	private String GameName;
	@JsonProperty("ClientSecret")
	private String ClientSecret;
	@JsonProperty("IsShow")
	private boolean IsShow;
	public String getGameWebSite() {
		return GameWebSite;
	}
	public void setGameWebSite(String gameWebSite) {
		GameWebSite = gameWebSite;
	}
	public String getOpenServer() {
		return OpenServer;
	}
	public void setOpenServer(String openServer) {
		OpenServer = openServer;
	}
	public String getIntroduce() {
		return Introduce;
	}
	public void setIntroduce(String introduce) {
		Introduce = introduce;
	}
	public String getGameSeriesInfo() {
		return GameSeriesInfo;
	}
	public void setGameSeriesInfo(String gameSeriesInfo) {
		GameSeriesInfo = gameSeriesInfo;
	}
	public String getLoginDomain() {
		return LoginDomain;
	}
	public void setLoginDomain(String loginDomain) {
		LoginDomain = loginDomain;
	}
	public String getGameStatus() {
		return GameStatus;
	}
	public void setGameStatus(String gameStatus) {
		GameStatus = gameStatus;
	}
	public String getNewUser() {
		return NewUser;
	}
	public void setNewUser(String newUser) {
		NewUser = newUser;
	}
	public String getSeriesDescription() {
		return SeriesDescription;
	}
	public void setSeriesDescription(String seriesDescription) {
		SeriesDescription = seriesDescription;
	}
	public String getGameSpelling() {
		return GameSpelling;
	}
	public void setGameSpelling(String gameSpelling) {
		GameSpelling = gameSpelling;
	}
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	public String getActiveType() {
		return ActiveType;
	}
	public void setActiveType(String activeType) {
		ActiveType = activeType;
	}
	public String getGameType() {
		return GameType;
	}
	public void setGameType(String gameType) {
		GameType = gameType;
	}
	public String getGameName() {
		return GameName;
	}
	public void setGameName(String gameName) {
		GameName = gameName;
	}
	public String getClientSecret() {
		return ClientSecret;
	}
	public void setClientSecret(String clientSecret) {
		ClientSecret = clientSecret;
	}
	public boolean isIsShow() {
		return IsShow;
	}
	public void setIsShow(boolean isShow) {
		IsShow = isShow;
	}
}
