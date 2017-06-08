/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/17 17:02
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wechatvideo;


/**
  * <p>
  *   抽奖记录实体
  * </p>
  *  
  * @author tanjunkai
  * @since 0.0.1
  */
public class WeChatVideoLotteryPrizesBean {
    
    private String openId;
    private String prizeType;
    private Integer prizeCode;
    private String prizeName;
    private Integer prizeNum;
    private String cardCode;
    private Integer isAvailable;
    private Integer serverCode;
    private String serverName;
    private String roleName;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getServerCode() {
        return serverCode;
    }

    public void setServerCode(Integer serverCode) {
        this.serverCode = serverCode;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "LotteryPrizesBean [openId=" + openId + ", prizeType="
                + prizeType + ", prizeCode=" + prizeCode + ", prizeName="
                + prizeName + ", prizeNum=" + prizeNum + ", cardCode="
                + cardCode + ", isAvailable=" + isAvailable + ", serverCode="
                + serverCode + ", serverName=" + serverName + ", roleName="
                + roleName + "]";
    }
}
