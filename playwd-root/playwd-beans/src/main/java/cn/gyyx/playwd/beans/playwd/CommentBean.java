package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论表
 * @author chenglong
 *
 */
public class CommentBean implements Serializable {
    private Integer code;

    private Integer parentCode;//回复的评论ID

    private String contentType;//评论类型 article等

    private Integer contentId;//评论内容ID

    private Integer fromUserId;//评论用户ID

    private String fromUserAccount;//评论用户账号

    private Integer toUserId;//被评论用户ID

    private String toUserAccount;//被评论用户账号

    private String content;//评论内容

    private Date createTime;//创建时间

    private boolean isShow;//是否显示

    private String fromIp;//评论用户IP
    
    private Integer replyCount;//评论回复数目

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserAccount() {
        return fromUserAccount;
    }

    public void setFromUserAccount(String fromUserAccount) {
        this.fromUserAccount = fromUserAccount == null ? null : fromUserAccount.trim();
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserAccount() {
        return toUserAccount;
    }

    public void setToUserAccount(String toUserAccount) {
        this.toUserAccount = toUserAccount == null ? null : toUserAccount.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp == null ? null : fromIp.trim();
    }

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

}