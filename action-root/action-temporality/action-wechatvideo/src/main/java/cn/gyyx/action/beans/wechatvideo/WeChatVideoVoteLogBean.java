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
 * 视频点赞实体类
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoVoteLogBean {

    /**
     * 主键code
     */
    private Integer code;

    /**
     * 视频ID
     */
    private Integer videoId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 点赞日期
     */
    private Date voteDate;

    /**
     * 来源IP
     */
    private String ip;

    /**
     * 浏览器版本
     */
    private String voteSource;

    @Override
    public String toString() {
        return "WeChatVideoVoteLogBean [code=" + code + ", videoId=" + videoId
                + ", userId=" + userId + ", voteDate=" + voteDate + ", ip=" + ip
                + ", voteSource=" + voteSource + "]";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setvideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getVoteSource() {
        return voteSource;
    }

    public void setVoteSource(String voteSource) {
        this.voteSource = voteSource == null ? null : voteSource.trim();
    }
}