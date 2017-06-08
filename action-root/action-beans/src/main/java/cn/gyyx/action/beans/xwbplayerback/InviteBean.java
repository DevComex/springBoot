/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2015年12月15日
 * @版本号：
 * @本类主要用途描述：老玩家回归活动实体Bean
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbplayerback;

/**
 * @ClassName: InviteBean
 * @Description: TODO 老玩家回归活动
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2015年12月15日 下午1:20:27
 *
 */
public class InviteBean {

	//主键
	private int code;
	//邀请人账号
	private String inviteAccount;
	//老朋友（被邀请人）账号
	private String inviteeAccount;
	//服务器名称
	private String serverName;
	//登录账号
	private String loginAccount;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInviteAccount() {
		return inviteAccount;
	}

	public void setInviteAccount(String inviteAccount) {
		this.inviteAccount = inviteAccount;
	}

	public String getInviteeAccount() {
		return inviteeAccount;
	}

	public void setInviteeAccount(String inviteeAccount) {
		this.inviteeAccount = inviteeAccount;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	@Override
	public String toString() {
		return "InviteBean [code=" + code + ", inviteAccount=" + inviteAccount
				+ ", inviteeAccount=" + inviteeAccount + ", serverName="
				+ serverName + ", loginAccount=" + loginAccount + "]";
	}

	
	
	
}
