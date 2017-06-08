package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.Date;

public class ReviewLogBean implements Serializable {
    private Integer code;

    private String contentType;//资源类型

    private Integer contentId;//资源ID

    private String operator;//操作者

    private String fromStatus;//修改前状态

    private String toStatus;//修改后状态

    private Date createTime;//创建时间

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus == null ? null : fromStatus.trim();
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus == null ? null : toStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}