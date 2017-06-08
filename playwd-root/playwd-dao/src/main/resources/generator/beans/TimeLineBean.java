package beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户对资源的操作表 比如：点赞、分享、评论等
 * @author chenglong
 *
 */
public class TimeLineBean implements Serializable {
    private Integer code;

    private Integer operateId;//操作用户ID

    private Integer toUserId;//被操作用户ID

    private String contentType;//内容类型 文章或者视频等

    private Integer contentId;//内容ID

    private String operateType;//操作类型 点赞等

    private Date createTime;//时间

    private String description;//描述

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
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

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}