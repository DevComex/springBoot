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

import java.util.Date;

/**
 * <p>
 * 视频上传实体类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoUpLoadLogBean {
    /**
     * 视频ID
     */
    private Integer code;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 微信名
     */
    private String wechatAccount;

    /**
     * 服务器名
     */
    private String serverName;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频地址
     */
    private String videoAddress;

    /**
     * 视频封面图片地址
     */
    private String videoCoverAddress;

    /**
     * 视频状态(0:未审核；1:已审核)
     */
    private Integer videoStatus;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 点赞次数
     */
    private Integer voteTimes;

    /**
     * 共享次数
     */
    private Integer shareTimes;

    @Override
    public String toString() {
        return "WeChatVideoUpLoadLogBean [code=" + code + ", userId=" + userId
                + ", account=" + account + ", roleName=" + roleName
                + ", wechatAccount=" + wechatAccount + ", serverName="
                + serverName + ", uploadTime=" + uploadTime + ", videoName="
                + videoName + ", videoAddress=" + videoAddress
                + ", videoCoverAddress=" + videoCoverAddress + ", videoStatus="
                + videoStatus + ", auditor=" + auditor + ", auditTime="
                + auditTime + ", voteTimes=" + voteTimes + ", shareTimes="
                + shareTimes + "]";
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount == null ? null
                : wechatAccount.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public String getVideoAddress() {
        return videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress == null ? null : videoAddress.trim();
    }

    public String getVideoCoverAddress() {
        return videoCoverAddress;
    }

    public void setVideoCoverAddress(String videoCoverAddress) {
        this.videoCoverAddress = videoCoverAddress == null ? null
                : videoCoverAddress.trim();
    }

    public Integer getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(Integer videoStatus) {
        this.videoStatus = videoStatus;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getVoteTimes() {
        return voteTimes;
    }

    public void setVoteTimes(Integer voteTimes) {
        this.voteTimes = voteTimes;
    }

    public Integer getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }
}