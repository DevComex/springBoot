package cn.gyyx.action.beans.wechatvideo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>
 * 视频上传视图
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class WeChatVideoUpLoadLogViewBean {
    /**
     * 视频名称
     */
    @NotBlank(message = "视频名称不可为空")
    private String videoName;

    /**
     * 微信OpenId
     */
    @NotBlank(message = "openId不可为空")
    private String openId;

    /**
     * 视频地址
     */
    @NotBlank(message = "视频地址不可为空")
    private String videoAddress;

    /**
     * 视频封面图片地址
     */
    @NotBlank(message = "视频封面地址不可为空")
    private String videoCoverAddress;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
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

    @Override
    public String toString() {
        return "WeChatVideoUpLoadLogViewBean [videoName=" + videoName
                + ", openId=" + openId + ", videoAddress=" + videoAddress
                + ", videoCoverAddress=" + videoCoverAddress + "]";
    }

}
