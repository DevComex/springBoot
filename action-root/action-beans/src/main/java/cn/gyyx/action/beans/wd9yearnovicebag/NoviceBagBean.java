/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei && zhouzhongkai
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2014年12月11日 下午2:53:48
 * @版本号：
 * @本类主要用途描述：新手卡；领取物品信息实体类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wd9yearnovicebag;

import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.core.auth.UserInfo;

public class NoviceBagBean {

	// 用户信息类
	private UserInfo userInfo;
	// 服务器信息类
	private ServerInfoBean serverInfo;
	// 手机号码
	private String cellPhone;
	// 用户账号
	private String account;
	// 游戏
	private int gameId;
	// 服务器ID
	private int serverId;
	// 批次号
	private int batchNo;
	// 只允许发放的物品编号，如果不限制的话不赋值
	private Integer limitItemId;
	// 密码(自动激活服务器所添加)
	private String PassWord;
	// 角色名字
	private String roleName;
	// 配置ActivityConfigBean的paras
	private String virtualItemCode;
	// 类型Id
	private Integer typeId;
	// 用来显示的虚拟物品名
	private String virtualItemName;
	// 验证方式：注册时间 / 激活时间
	private String checkType;
	// 活动名称
	private String hdName;
	// 验证码
	private String code;

	public NoviceBagBean() {
		this.cellPhone = "无";

		this.checkType = "ActivationCheck";
	}

	public NoviceBagBean(String account, String password, int gameId,
			int serverId, String card, int batchNo, String cellPhone,
			String checkType, UserInfo userInfo) {
		this.account = account;
		this.PassWord = password;
		this.gameId = gameId;
		this.serverId = serverId;
		this.batchNo = batchNo;
		this.cellPhone = cellPhone;
		this.checkType = checkType;
		this.userInfo = userInfo;
	}

	public String getHdName() {
		return hdName;
	}

	public void setHdName(String hdName) {
		this.hdName = hdName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public ServerInfoBean getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfoBean serverInfo) {
		this.serverInfo = serverInfo;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getVirtualItemName() {
		return virtualItemName;
	}

	public void setVirtualItemName(String virtualItemName) {
		this.virtualItemName = virtualItemName;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getLimitItemId() {
		return limitItemId;
	}

	public void setLimitItemId(Integer limitItemId) {
		this.limitItemId = limitItemId;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getVirtualItemCode() {
		return virtualItemCode;
	}

	public void setVirtualItemCode(String virtualItem) {
		this.virtualItemCode = virtualItem;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}


}
