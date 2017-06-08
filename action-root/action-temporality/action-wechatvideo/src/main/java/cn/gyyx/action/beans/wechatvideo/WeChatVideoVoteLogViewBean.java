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
 * 视频点赞视图
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoVoteLogViewBean {
    /**
     * 视频ID
     */
    private Integer videoId;
    /**
     * 浏览器来源
     */
    private String voteSource;
    /**
     * 微信openId
     */
    private String openId;

    public Integer getVideoId() {
        return videoId;
    }

    public void setvideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVoteSource() {
        return voteSource;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }
    public String getOpenId() {
        return openId;
    }

    public void setVoteSource(String voteSource) {
        this.voteSource = voteSource == null ? null : voteSource.trim();
    }
    
    @Override
    public String toString() {
        return "WeChatVideoVoteLogViewBean [videoId=" + videoId
                + ", voteSource=" + voteSource + ", openId=" + openId + "]";
    }
}
