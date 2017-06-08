/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/3/12 9:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wechatvideo;

/**
 * <p>
 * 角色绑定实体类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoRoleBindBean {

    /**
     * 主键
     */
    private Integer code;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 微信openID
     */
    private String openId;

    /**
     * 微信名
     */
    private String wechatAccount;

    /**
     * 区服Code
     */
    private Integer serverCode;

    /**
     * 区服名称
     */
    private String serverName;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 抽奖次数
     */
    private Integer lotteryTimes;

    /**
     * 剩余抽奖次数
     */
    private Integer remainingTimes;

    @Override
    public String toString() {
        return "WeChatVideoRoleBindBean [code=" + code + ", userId=" + userId
                + ", account=" + account + ", openId=" + openId
                + ", wechatAccount=" + wechatAccount + ", serverCode="
                + serverCode + ", serverName=" + serverName + ", roleId="
                + roleId + ", roleName=" + roleName + ", lotteryTimes="
                + lotteryTimes + ", remainingTimes=" + remainingTimes + "]";
    }

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount == null ? null
                : wechatAccount.trim();
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
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Integer getLotteryTimes() {
        return lotteryTimes;
    }

    public void setLotteryTimes(Integer lotteryTimes) {
        this.lotteryTimes = lotteryTimes;
    }

    public Integer getRemainingTimes() {
        return remainingTimes;
    }

    public void setRemainingTimes(Integer remainingTimes) {
        this.remainingTimes = remainingTimes;
    }
}
