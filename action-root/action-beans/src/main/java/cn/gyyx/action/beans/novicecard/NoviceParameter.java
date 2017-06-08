/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月11日 下午2:53:48
 * @版本号：
 * @本类主要用途描述：新手卡；领取物品信息实体类
 *-------------------------------------------------------------------------
 */

package cn.gyyx.action.beans.novicecard;

import cn.gyyx.core.auth.UserInfo;

public class NoviceParameter implements Cloneable{

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
	// 新手卡号
	private String card;
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
	// 卡类型
	private NoviceCardMark mark;
	//九周年活动————新手礼包项目 （物品类别）
	private String virtualItemType;
	//九周年活动————新手礼包项目 （活动Id）
	private Integer activityId;
	//IP用户IP地址
	private String ip;
	//激活码
	private String activeCode;
	private String mailContent;//20160623新增字段，用于发送奖品时标示邮件内容 
	private int flag;//0 pc端 1移动端 
	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getVirtualItemType() {
		return virtualItemType;
	}

	public void setVirtualItemType(String virtualItemType) {
		this.virtualItemType = virtualItemType;
	}

	public NoviceParameter() {
		this.cellPhone = "无";

		this.checkType = "ActivationCheck";
	}

	public NoviceParameter(String account, String password, int gameId,
			int serverId, String card, int batchNo, String cellPhone,
			String checkType, UserInfo userInfo) {
		this.account = account;
		this.PassWord = password;
		this.gameId = gameId;
		this.serverId = serverId;
		this.card = card;
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

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
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

	public NoviceCardMark getMark() {
		return mark;
	}

	public void setMark(NoviceCardMark mark) {
		this.mark = mark;
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public NoviceParameter(ServerInfoBean serverInfo, String account,
			int gameId, String virtualItemCode) {
		super();
		this.serverInfo = serverInfo;
		this.account = account;
		this.gameId = gameId;
		this.virtualItemCode = virtualItemCode;
	}

	
	@Override
	public NoviceParameter clone() throws CloneNotSupportedException {
		return (NoviceParameter)super.clone();
	}

	@Override
	public String toString() {
		return "NoviceParameter [userInfo=" + userInfo + ", serverInfo="
				+ serverInfo + ", cellPhone=" + cellPhone + ", account="
				+ account + ", gameId=" + gameId + ", serverId=" + serverId
				+ ", card=" + card + ", batchNo=" + batchNo + ", limitItemId="
				+ limitItemId + ", PassWord=" + PassWord + ", roleName="
				+ roleName + ", virtualItemCode=" + virtualItemCode
				+ ", typeId=" + typeId + ", virtualItemName=" + virtualItemName
				+ ", checkType=" + checkType + ", hdName=" + hdName + ", code="
				+ code + ", mark=" + mark + ", virtualItemType="
				+ virtualItemType + ", activityId=" + activityId + ", ip=" + ip
				+ ", activeCode=" + activeCode + "]";
	}
	
}
