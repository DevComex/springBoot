package cn.gyyx.action.beans.wdrankinglist2017;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 
  * <p>
  *   玩家账号绑定类
  * </p>
  *  
  * @author laixiancai
  * @since 0.0.1
 */
public class RoleBindBean {
    @Null(message = "您不能指定编号！")
    private Integer code;
    // @NotNull(message = "微信token不可为空！")
    // private String wxToken;
    private Integer userId;
    @Null(message = "您不可指定账号！")
    private String account;

    private Date createTime;
    @NotNull(message = "openId不能为空！")
    private String openId;
    @Null(message = "您不可指定微信昵称")
    private String wxNick;

    private Date lotteryTime;

    private int refusedCount;

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
        this.account = account;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxNick() {
        return wxNick;
    }

    public void setWxNick(String wxNick) {
        this.wxNick = wxNick;
    }

    public Date getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(Date lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    /**
     * @return the refusedCount
     */
    public int getRefusedCount() {
        return refusedCount;
    }

    /**
     * @param refusedCount
     *            the refusedCount to set
     */
    public void setRefusedCount(int refusedCount) {
        this.refusedCount = refusedCount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RoleBindBean [code=" + code + ", userId=" + userId
                + ", account=" + account + ", createTime=" + createTime
                + ", openId=" + openId + ", wxNick=" + wxNick + ", lotteryTime="
                + lotteryTime + ", refusedCount=" + refusedCount + "]";
    }
}