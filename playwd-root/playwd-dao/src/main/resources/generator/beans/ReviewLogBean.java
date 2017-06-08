package beans;

import java.io.Serializable;
import java.util.Date;

public class ReviewLogBean implements Serializable {
    private Integer code;

    private String contentType;

    private Integer contentId;

    private String operator;

    private String fromStatus;

    private String toStatus;

    private Date createTime;

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