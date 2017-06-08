/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/6 15:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wish11th;

import java.util.Date;

/**
  * <p>
  *   问道许愿活动抽奖日志类
  * </p>
  *  
  * @author tanjunkai
  * @since 0.0.1
  */
public class Wish11thLotteryPrizesBean {
    private Long code;
    
    private String activityType;
    
    private Integer activityId;
    
    private String userId;
    
    private String gameId;
    
    private String serverId;
    
    private String prizeType;
    
    private Integer prizeCode;
    
    private String prizeName;
    
    private Integer prizeNum;
    
    private String cardCode;
    
    private Date winningAt;
    
    private String winningIp;
    
    private Integer isAvailable;
    
    private String remark;

    public Long getCode() {
            return code;
    }

    public void setCode(Long code) {
            this.code = code;
    }

    public String getActivityType() {
            return activityType;
    }

    public void setActivityType(String activityType) {
            this.activityType = activityType;
    }

    public Integer getActivityId() {
            return activityId;
    }

    public void setActivityId(Integer activityId) {
            this.activityId = activityId;
    }

    public String getUserId() {
            return userId;
    }

    public void setUserId(String userId) {
            this.userId = userId;
    }

    public String getGameId() {
            return gameId;
    }

    public void setGameId(String gameId) {
            this.gameId = gameId;
    }

    public String getServerId() {
            return serverId;
    }

    public void setServerId(String serverId) {
            this.serverId = serverId;
    }

    public String getPrizeType() {
            return prizeType;
    }

    public void setPrizeType(String prizeType) {
            this.prizeType = prizeType;
    }

    public Integer getPrizeCode() {
            return prizeCode;
    }

    public void setPrizeCode(Integer prizeCode) {
            this.prizeCode = prizeCode;
    }

    public String getPrizeName() {
            return prizeName;
    }

    public void setPrizeName(String prizeName) {
            this.prizeName = prizeName;
    }

    public Integer getPrizeNum() {
            return prizeNum;
    }

    public void setPrizeNum(Integer prizeNum) {
            this.prizeNum = prizeNum;
    }

    public String getCardCode() {
            return cardCode;
    }

    public void setCardCode(String cardCode) {
            this.cardCode = cardCode;
    }

    public Date getWinningAt() {
            return winningAt;
    }

    public void setWinningAt(Date winningAt) {
            this.winningAt = winningAt;
    }

    public String getWinningIp() {
            return winningIp;
    }

    public void setWinningIp(String winningIp) {
            this.winningIp = winningIp;
    }

    public Integer getIsAvailable() {
            return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
            this.isAvailable = isAvailable;
    }

    public String getRemark() {
            return remark;
    }

    public void setRemark(String remark) {
            this.remark = remark;
    }
}
