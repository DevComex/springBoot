/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月9日 上午10:50:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdblessingcard2017;

import java.util.Date;

/**
 * <p>
 * 用户绑定角色信息Bean
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class RoleBindBean {
    private Integer code;

    private Integer userId; // 用户id

    private String account; // 用户账号

    private Integer serverCode; // 服务器编号

    private String serverName; // 服务器名

    private String roleId; // 角色id
    
    private String roleName; // 角色名称

    private Integer lotteryTimes; // 总抽奖数（活动内获取到的总数）

    private Integer remainingTimes; // 可用抽奖数

    private String ip; // 登录ip(绑定角色时的ip)

    private Integer registerYear; // 注册年份

    private Integer upvoteTimes; // 可点赞数

    private Boolean enableGetLotteryTimes;

    private Boolean isReceivedTitle; // 是否领取称号

    private Date createTime; // 绑定时间

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getRegisterYear() {
        return registerYear;
    }

    public void setRegisterYear(Integer registerYear) {
        this.registerYear = registerYear;
    }

    public Integer getUpvoteTimes() {
        return upvoteTimes;
    }

    public void setUpvoteTimes(Integer upvoteTimes) {
        this.upvoteTimes = upvoteTimes;
    }

    public Boolean getEnableGetLotteryTimes() {
        return enableGetLotteryTimes;
    }

    public void setEnableGetLotteryTimes(Boolean enableGetLotteryTimes) {
        this.enableGetLotteryTimes = enableGetLotteryTimes;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsReceivedTitle() {
        return isReceivedTitle;
    }

    public void setIsReceivedTitle(Boolean isReceivedTitle) {
        this.isReceivedTitle = isReceivedTitle;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RoleBindBean [code=" + code + ", userId=" + userId
                + ", account=" + account + ", serverCode=" + serverCode
                + ", serverName=" + serverName + ", roleId=" + roleId
                + ", roleName=" + roleName + ", lotteryTimes=" + lotteryTimes
                + ", remainingTimes=" + remainingTimes + ", ip=" + ip
                + ", registerYear=" + registerYear + ", upvoteTimes="
                + upvoteTimes + ", enableGetLotteryTimes="
                + enableGetLotteryTimes + ", isReceivedTitle=" + isReceivedTitle
                + ", createTime=" + createTime + "]";
    }

}