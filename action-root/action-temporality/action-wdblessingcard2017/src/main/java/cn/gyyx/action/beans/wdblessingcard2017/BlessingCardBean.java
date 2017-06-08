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
 * 祝福卡Bean
 * </p>
 * 
 * @author laixiancai
 * @since 0.0.1
 */
public class BlessingCardBean {
    private Integer code;

    private Integer userId;

    private String account;

    private String content;

    private String roleName;

    private String serverName;

    private String title; // 玩家称号

    private Integer verifyStatus;

    private Date verifyTime;

    private String verifyAdmin;

    private Date createTime;

    private Integer upvoteNum; // 点赞次数

    private String avatar;

    private Integer registerYear; // 注册年份

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifyAdmin() {
        return verifyAdmin;
    }

    public void setVerifyAdmin(String verifyAdmin) {
        this.verifyAdmin = verifyAdmin == null ? null : verifyAdmin.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpvoteNum() {
        return upvoteNum;
    }

    public void setUpvoteNum(Integer upvoteNum) {
        this.upvoteNum = upvoteNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRegisterYear() {
        return registerYear;
    }

    public void setRegisterYear(Integer registerYear) {
        this.registerYear = registerYear;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BlessingCardBean [code=" + code + ", userId=" + userId
                + ", account=" + account + ", content=" + content
                + ", roleName=" + roleName + ", serverName=" + serverName
                + ", title=" + title + ", verifyStatus=" + verifyStatus
                + ", verifyTime=" + verifyTime + ", verifyAdmin=" + verifyAdmin
                + ", createTime=" + createTime + ", upvoteNum=" + upvoteNum
                + ", avatar=" + avatar + ", registerYear=" + registerYear + "]";
    }

}