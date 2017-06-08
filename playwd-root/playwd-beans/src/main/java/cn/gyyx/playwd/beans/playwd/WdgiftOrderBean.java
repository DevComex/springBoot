 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月7日下午3:03:48
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.Date;

import cn.gyyx.playwd.beans.common.RetryTaskInfo;

/**
 * 游戏虚拟物品订单表
 * @author lidudi
 *
 */
public class WdgiftOrderBean extends RetryTaskInfo implements Serializable {
	
    private Integer code;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 内容ID
     */
    private Integer contentId;

    /**
     * 奖励ID
     */
    private Integer prizeId;

    /**
     * 游戏ID
     */
    private Integer gameId;

    private Integer serverId;

    private String serverName;

    /**
     * 虚拟物品（银元宝或者称号）
     */
    private String gift;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 订单状态
     */
    private String status;

    private Integer userId;

    private String account;

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
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

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift == null ? null : gift.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
    
    /**
     * 订单状态
     */
    public enum GiftOrderState {
        /**
         * 待发放
         */
    	waitgrant("waitgrant"), // 待发放
        /**
         * 成功
         */
        success("success"), // 成功
        /**
         * 失败
         */
        failed("failed"), //失败
    	 /**
         * 失败
         */
    	unactivated("unactivated"); //未激活服务器
        
        private String value;

        private GiftOrderState(String value) {
            this.value = value;
        }

        public String Value() {
            return value;
        }
    }

	@Override
	public String toString() {
		return "WdgiftOrderBean [code=" + code + ", orderId=" + orderId
				+ ", contentType=" + contentType + ", contentId=" + contentId
				+ ", prizeId=" + prizeId + ", gameId=" + gameId + ", serverId="
				+ serverId + ", serverName=" + serverName + ", gift=" + gift
				+ ", createTime=" + createTime + ", status=" + status
				+ ", userId=" + userId + ", account=" + account + "]";
	}

}
