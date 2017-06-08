/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月17日 下午3:54:24 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 激活日志实体类
-------------------------------------------------------------------------*/
package cn.gyyx.action.beans.novicecard;

/** 
* @ClassName: ActiveLog 
* @Description: TODO 查询激活日志实体类
* @author jh 
* @date 2014年12月17日 下午3:57:52 
*  
*/
public class ActiveLog {
	private int Code;
	private int UserId;
	private String Account;
	private int GameId;
	private int ServerCode;
	private String ActiveTime;

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
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

	public int getServerCode() {
		return ServerCode;
	}

	public void setServerCode(int serverCode) {
		ServerCode = serverCode;
	}

	public String getActiveTime() {
		return ActiveTime;
	}

	public void setActiveTime(String activeTime) {
		ActiveTime = activeTime;
	}

}
