/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：xuanwuba-creditsLuckyDraw-backstage
 * @作者：mawenbin
 * @联系方式：mawenbin@gyyx.cn
 * @创建时间： 2015年7月7日 下午3:16:53
 * @版本号：v1.204
 * @本类主要用途描述：玩家信息表
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

/**
 * 玩家信息表
 * 
 **/
public class PlayerInfoBean {
	// 主键编号
	private int code;
	// 账号名称
	private String account;
	// 用户id
	private int userId;
	// 所在服务器
	private String serverName;
	// 所在服务器ID
	private Integer serverId;
	// 角色性别
	private String roleSex;
	// 区组
	private Integer netType;
	// 分页用的page
	private Integer page;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getRoleSex() {
		return roleSex;
	}

	public void setRoleSex(String roleSex) {
		this.roleSex = roleSex;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getNetType() {
		return netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
