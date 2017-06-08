/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdshenluowangxiang
 * @作者：lihu
 * @联系方式：lihu@gyyx.cn
 * @创建时间：2017年4月8日 下午4:23:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdshenluowangxiang;

/**
 * <p>
 * 森罗万象活动页面 返回封装bean
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
public class ShenLuoWangXiangReturnBean {
	//主键
	private Integer code;
	//是否绑定
	private boolean isBind;
	//用户ID
	private Integer userId;
	//用户名
	private String account;
	//服务器ID
	private Integer serverId;
	//服务器名称
	private String serverName;
	//剩余抽奖次数
	private Integer lastNum;
	//是否中过实物奖品
	private boolean realPrizeFlag;
	//是否中过邀请函
        private boolean inviteFlag;

	public ShenLuoWangXiangReturnBean() {
	    super();
	}

	public ShenLuoWangXiangReturnBean(boolean isBind) {
		this.isBind = isBind;
	}

	public boolean isBind() {
		return isBind;
	}

	public void setBind(boolean isBind) {
		this.isBind = isBind;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName == null ? null : serverName.trim();
	}

	public Integer getLastNum() {
		return lastNum;
	}

	public void setLastNum(Integer lastNum) {
		this.lastNum = lastNum;
	}
	

        public Integer getUserId() {
                return userId;
        }

        public void setUserId(Integer userId) {
                this.userId = userId;
        }

        public boolean isRealPrizeFlag() {
            return realPrizeFlag;
        }

        public void setRealPrizeFlag(boolean realPrizeFlag) {
            this.realPrizeFlag = realPrizeFlag;
        }

        public boolean isInviteFlag() {
            return inviteFlag;
        }

        public void setInviteFlag(boolean inviteFlag) {
            this.inviteFlag = inviteFlag;
        }

}