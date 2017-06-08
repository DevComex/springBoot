/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间：2014年12月10日 
 * @版本号：v1.0
 * @本类主要用途描述：获取服务器信息的实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.wd.wanjia;

import java.util.Map;

public class ServerInfoBean {
	
	private long TimeStamp;
	private Value Value;
	private String ErrorMessage;
	private String SignType;
	private String Sign;
	private String Error;

	public long getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		TimeStamp = timeStamp;
	}

	public Value getValue() {
		return Value;
	}

	public void setValue(Value value) {
		Value = value;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}

	public String getSignType() {
		return SignType;
	}

	public void setSignType(String signType) {
		SignType = signType;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}

	public static class Value {
		private boolean IsCash;
		private String ServerMark;
		private boolean IsClientOpen;
		private String ServerUrl;
		private String ServerName;
		private Integer Code;
		private String IdcIp;
		private Integer PlatFormTypeCode;
		private String ServerStatus;
		private String ServerIp;
		private Boolean IsValid;
		private String AreaName;
		private Boolean IsNeedRole;
		private String UnionServerName;
		private String OpenServerTime;
		private String ServerMonitorStatus;
		private Integer ServerPort;
		private Map<String,String> Game;
		private String UpdateTime;
		private Integer ServerOrder;
		private Integer NetTypeCode;
		private boolean IsHasNewPlayerCard;
		private boolean IsMaintain;
		private boolean IsOpen;
		private boolean IsNewServer;
		private boolean IsActivation;
		private boolean IsHot;
		
		@Override
		public String toString() {
			return "Value [IsCash=" + IsCash + ", ServerMark=" + ServerMark
					+ ", IsClientOpen=" + IsClientOpen + ", ServerUrl="
					+ ServerUrl + ", ServerName=" + ServerName + ", Code="
					+ Code + ", IdcIp=" + IdcIp + ", PlatFormTypeCode="
					+ PlatFormTypeCode + ", ServerStatus=" + ServerStatus
					+ ", ServerIp=" + ServerIp + ", IsValid=" + IsValid
					+ ", AreaName=" + AreaName + ", IsNeedRole=" + IsNeedRole
					+ ", UnionServerName=" + UnionServerName
					+ ", OpenServerTime=" + OpenServerTime
					+ ", ServerMonitorStatus=" + ServerMonitorStatus
					+ ", ServerPort=" + ServerPort + ", Game=" + Game
					+ ", UpdateTime=" + UpdateTime + ", ServerOrder="
					+ ServerOrder + ", NetTypeCode=" + NetTypeCode
					+ ", IsHasNewPlayerCard=" + IsHasNewPlayerCard
					+ ", IsMaintain=" + IsMaintain + ", IsOpen=" + IsOpen
					+ ", IsNewServer=" + IsNewServer + ", IsActivation="
					+ IsActivation + ", IsHot=" + IsHot + "]";
		}

		public boolean isIsCash() {
			return IsCash;
		}

		public void setIsCash(boolean isCash) {
			IsCash = isCash;
		}

		public String getServerMark() {
			return ServerMark;
		}

		public void setServerMark(String serverMark) {
			ServerMark = serverMark;
		}

		public boolean isIsClientOpen() {
			return IsClientOpen;
		}

		public void setIsClientOpen(boolean isClientOpen) {
			IsClientOpen = isClientOpen;
		}

		public String getServerUrl() {
			return ServerUrl;
		}

		public void setServerUrl(String serverUrl) {
			ServerUrl = serverUrl;
		}

		public String getServerName() {
			return ServerName;
		}

		public void setServerName(String serverName) {
			ServerName = serverName;
		}

		public Integer getCode() {
			return Code;
		}

		public void setCode(Integer code) {
			Code = code;
		}

		public String getIdcIp() {
			return IdcIp;
		}

		public void setIdcIp(String idcIp) {
			IdcIp = idcIp;
		}

		public Integer getPlatFormTypeCode() {
			return PlatFormTypeCode;
		}

		public void setPlatFormTypeCode(Integer platFormTypeCode) {
			PlatFormTypeCode = platFormTypeCode;
		}

		public String getServerStatus() {
			return ServerStatus;
		}

		public void setServerStatus(String serverStatus) {
			ServerStatus = serverStatus;
		}

		public String getServerIp() {
			return ServerIp;
		}

		public void setServerIp(String serverIp) {
			ServerIp = serverIp;
		}

		public Boolean getIsValid() {
			return IsValid;
		}

		public void setIsValid(Boolean isValid) {
			IsValid = isValid;
		}

		public String getAreaName() {
			return AreaName;
		}

		public void setAreaName(String areaName) {
			AreaName = areaName;
		}

		public Boolean getIsNeedRole() {
			return IsNeedRole;
		}

		public void setIsNeedRole(Boolean isNeedRole) {
			IsNeedRole = isNeedRole;
		}

		public String getUnionServerName() {
			return UnionServerName;
		}

		public void setUnionServerName(String unionServerName) {
			UnionServerName = unionServerName;
		}

		public String getOpenServerTime() {
			return OpenServerTime;
		}

		public void setOpenServerTime(String openServerTime) {
			OpenServerTime = openServerTime;
		}

		public String getServerMonitorStatus() {
			return ServerMonitorStatus;
		}

		public void setServerMonitorStatus(String serverMonitorStatus) {
			ServerMonitorStatus = serverMonitorStatus;
		}

		public Integer getServerPort() {
			return ServerPort;
		}

		public void setServerPort(Integer serverPort) {
			ServerPort = serverPort;
		}

		public Map<String,String> getGame() {
			//return Game;
			return Game;
		}

		public void setGame(Map<String,String> game) {
			this.Game = game;
		}

		public String getUpdateTime() {
			return UpdateTime;
		}

		public void setUpdateTime(String updateTime) {
			UpdateTime = updateTime;
		}

		public Integer getServerOrder() {
			return ServerOrder;
		}

		public void setServerOrder(Integer serverOrder) {
			ServerOrder = serverOrder;
		}

		public Integer getNetTypeCode() {
			return NetTypeCode;
		}

		public void setNetTypeCode(Integer netTypeCode) {
			NetTypeCode = netTypeCode;
		}

		public boolean isIsHasNewPlayerCard() {
			return IsHasNewPlayerCard;
		}

		public void setIsHasNewPlayerCard(boolean isHasNewPlayerCard) {
			IsHasNewPlayerCard = isHasNewPlayerCard;
		}

		public boolean isIsMaintain() {
			return IsMaintain;
		}

		public void setIsMaintain(boolean isMaintain) {
			IsMaintain = isMaintain;
		}

		public boolean isIsOpen() {
			return IsOpen;
		}

		public void setIsOpen(boolean isOpen) {
			IsOpen = isOpen;
		}

		public boolean isIsNewServer() {
			return IsNewServer;
		}

		public void setIsNewServer(boolean isNewServer) {
			IsNewServer = isNewServer;
		}

		public boolean isIsActivation() {
			return IsActivation;
		}

		public void setIsActivation(boolean isActivation) {
			IsActivation = isActivation;
		}

		public boolean isIsHot() {
			return IsHot;
		}

		public void setIsHot(boolean isHot) {
			IsHot = isHot;
		}

		public static class Game {
			private String GameWebSite;
			private String OpenServer;
			private String Introduce;
			private String GameSeriesInfo;
			private String LoginDomain;
			private String GameStatus;
			private String NewUser;
			private String SeriesDescription;
			private String GameSpelling;
			private Integer Code;
			private String ActiveType;
			private String GameType;
			private String GameName;

			
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

			public Integer getCode() {
				return Code;
			}

			public void setCode(Integer code) {
				Code = code;
			}

			public String getGameName() {
				return GameName;
			}

			public void setGameName(String gameName) {
				GameName = gameName;
			}
		}
	}

	@Override
	public String toString() {
		return "ServerInfoBean [TimeStamp=" + TimeStamp + ", Value=" + Value
				+ ", ErrorMessage=" + ErrorMessage + ", SignType=" + SignType
				+ ", Sign=" + Sign + ", Error=" + Error + "]";
	}

}
