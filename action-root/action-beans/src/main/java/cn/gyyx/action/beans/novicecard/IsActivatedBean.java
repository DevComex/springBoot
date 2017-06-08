package cn.gyyx.action.beans.novicecard;

/**
 * 判断是否激活接口
 * interface.account.gyyx.cn/game/activelog/{gameId}/{account}/{servercode}/{serverhost}/{serverport}?timestamp=1459254403&sign=551b61eaa09043c225d14b8a8fee25c2&sign_type=MD5
 * @author Administrator
 *
 */
public class IsActivatedBean {
	private String account;
	private String gameid;
	private String servercode;
	private String serverhost;
	private String serverport;
	
	
	
	public IsActivatedBean() {
	}
	
	public IsActivatedBean(String account, String gameid, String servercode,
			String serverhost, String serverport) {
		this.account = account;
		this.gameid = gameid;
		this.servercode = servercode;
		this.serverhost = serverhost;
		this.serverport = serverport;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getGameid() {
		return gameid;
	}
	public void setGameid(String gameid) {
		this.gameid = gameid;
	}
	public String getServercode() {
		return servercode;
	}
	public void setServercode(String servercode) {
		this.servercode = servercode;
	}
	public String getServerhost() {
		return serverhost;
	}
	public void setServerhost(String serverhost) {
		this.serverhost = serverhost;
	}
	public String getServerport() {
		return serverport;
	}
	public void setServerport(String serverport) {
		this.serverport = serverport;
	}
	
}
