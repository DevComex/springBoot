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

import java.util.Date;

/**
 * <p>
 * 森罗万象活动用户绑定信息bean
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
public class ShenLuoWangXiangBean {
    private Integer code;

    private Integer userId;//用户ID

    private String account;//账号

    private Integer maxLevel;//服务器角色最大等级

    private Integer serverId;//服务器ID

    private String serverName;//服务器内容

    private Integer luckyNum;
    private Integer totalNum;
    private Integer lastNum;
    private String source;//mobile代表手机端

    private Date thinkTime;//createTime的前10位时间格式

    private Date createTime;

    private Date updateTime;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
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

    public Integer getLuckyNum() {
        return luckyNum;
    }

    public void setLuckyNum(Integer luckyNum) {
        this.luckyNum = luckyNum;
    }

    public Date getThinkTime() {
        return thinkTime;
    }

    public void setThinkTime(Date thinkTime) {
        this.thinkTime = thinkTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getLastNum() {
		return lastNum;
	}

	public void setLastNum(Integer lastNum) {
		this.lastNum = lastNum;
	}
	
	public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
	public String toString() {
		return "ShenLuoWangXiangBean [code=" + code + ", userId=" + userId + ", account=" + account + ", maxLevel="
				+ maxLevel + ", serverId=" + serverId + ", serverName=" + serverName + ", luckyNum=" + luckyNum
				+ ", totalNum=" + totalNum + ", lastNum=" + lastNum + ", thinkTime=" + thinkTime + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
    
}