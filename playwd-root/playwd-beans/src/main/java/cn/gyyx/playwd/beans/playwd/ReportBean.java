package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.Date;

/**
 * 举报实体
 * @author chenglong
 *
 */
public class ReportBean implements Serializable {
    private Integer code;

    private Integer commentId;//评论ID

    private Integer commentUserId;//评论用户ID

    private String commentUserAccount;//评论用户账号

    private Integer reportUserId;//举报用户ID

    private String reportUserAccount;//评论用户账号

    private String reason;//举报原因

    private String status;//状态

    private Date createTime;//创建时间

    private Date processTime;//举报处理时间

    private String operatorAccount;//处理人账号
    
    private CommentBean commentBean;//评论信息
    
	private static final long serialVersionUID = 1L;
    
	public enum ReportStatus {
       
        /**
         * 已处理
         */
		processed("processed"), 
        /**
         * 未处理
         */
		processing("processing");
        
        private String value;

        private ReportStatus(String value) {
            this.value = value;
        }

        public String Value() {
            return value;
        }
    }

    public CommentBean getCommentBean() {
		return commentBean;
	}

	public void setCommentBean(CommentBean commentBean) {
		this.commentBean = commentBean;
	}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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