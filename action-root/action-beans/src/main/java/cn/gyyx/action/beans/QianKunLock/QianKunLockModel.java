package cn.gyyx.action.beans.QianKunLock;

public class QianKunLockModel {
	 //玩家ID
    private int UserId;

    //玩家账号
    private String Account;

    //游戏ID
    private int GameId;

    //游戏服务器ID
    private int ServerID;

    //游戏服务器名
    private String ServerName;

    //密保类型
    private int EkeyType;

    //移动密保sn号
    private String EkeySn;

    //标识
    private int Code;

    //错误标识
    private int ErrCode;
    private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public int getGameId() {
		return GameId;
	}

	public void setGameId(int gameId) {
		GameId = gameId;
	}

	public int getServerID() {
		return ServerID;
	}

	public void setServerID(int serverID) {
		ServerID = serverID;
	}

	public String getServerName() {
		return ServerName;
	}

	public void setServerName(String serverName) {
		ServerName = serverName;
	}

	public int getEkeyType() {
		return EkeyType;
	}

	public void setEkeyType(int ekeyType) {
		EkeyType = ekeyType;
	}

	public String getEkeySn() {
		return EkeySn;
	}

	public void setEkeySn(String ekeySn) {
		EkeySn = ekeySn;
	}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public int getErrCode() {
		return ErrCode;
	}

	public void setErrCode(int errCode) {
		ErrCode = errCode;
	}
    
}
