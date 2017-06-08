package beans;

import java.io.Serializable;
import java.util.Date;

public class ReportBean implements Serializable {
    private Integer code;

    private Integer comentId;

    private Integer commentUserId;

    private String commentUserAccount;

    private Integer reportUserId;

    private String reportUserAccount;

    private String reason;

    private String status;

    private Date createTime;

    private Date processTime;

    private String operatorAccount;

    private static final long serialVersionUID = 1L;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getComentId() {
        return comentId;
    }

    public void setComentId(Integer comentId) {
        this.comentId = comentId;
    }

    public Integer getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Integer commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentUserAccount() {
        return commentUserAccount;
    }

    public void setCommentUserAccount(String commentUserAccount) {
        this.commentUserAccount = commentUserAccount == null ? null : commentUserAccount.trim();
    }

    public Integer getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Integer reportUserId) {
        this.reportUserId = reportUserId;
    }

    public String getReportUserAccount() {
        return reportUserAccount;
    }

    public void setReportUserAccount(String reportUserAccount) {
        this.reportUserAccount = reportUserAccount == null ? null : reportUserAccount.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public String getOperatorAccount() {
        return operatorAccount;
    }

    public void setOperatorAccount(String operatorAccount) {
        this.operatorAccount = operatorAccount == null ? null : operatorAccount.trim();
    }
}