/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：问道11周年留言墙活动
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月8日 下午5:35:52
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wd11thyearscommentwall;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * 留言墙活动留言实体类
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */

public class CommentWallBean {
    private Integer code;
    @JsonIgnore
    private Integer actionCode;

    private String nickName;

    private String message;
    @JsonIgnore
    private String sourceTag;
    @JsonIgnore
    private Integer isAudit;
    @JsonIgnore
    private String auditor;

    private Date createTime;
    @JsonIgnore
    private Date auditTime;

    /**
     * 
     */
    public CommentWallBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param code
     * @param actionCode
     * @param nickName
     * @param message
     * @param sourceTag
     * @param isAudit
     * @param auditor
     * @param createTime
     * @param auditTime
     */
    public CommentWallBean(Integer code, Integer actionCode, String nickName,
            String message, String sourceTag, Integer isAudit, String auditor,
            Date createTime, Date auditTime) {
        super();
        this.code = code;
        this.actionCode = actionCode;
        this.nickName = nickName;
        this.message = message;
        this.sourceTag = sourceTag;
        this.isAudit = isAudit;
        this.auditor = auditor;
        this.createTime = createTime;
        this.auditTime = auditTime;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return the actionCode
     */
    public Integer getActionCode() {
        return actionCode;
    }

    /**
     * @param actionCode
     *            the actionCode to set
     */
    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

    /**
     * @return the nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     *            the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    /**
     * @return the sourceTag
     */
    public String getSourceTag() {
        return sourceTag;
    }

    /**
     * @param sourceTag
     *            the sourceTag to set
     */
    public void setSourceTag(String sourceTag) {
        this.sourceTag = sourceTag == null ? null : sourceTag.trim();
    }

    /**
     * @return the isAudit
     */
    public Integer getIsAudit() {
        return isAudit;
    }

    /**
     * @param isAudit
     *            the isAudit to set
     */
    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    /**
     * @return the auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * @param auditor
     *            the auditor to set
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the auditTime
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * @param auditTime
     *            the auditTime to set
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CommentWallBean [code=" + code + ", actionCode=" + actionCode
                + ", nickName=" + nickName + ", message=" + message
                + ", sourceTag=" + sourceTag + ", isAudit=" + isAudit
                + ", auditor=" + auditor + ", createTime=" + createTime
                + ", auditTime=" + auditTime + "]";
    }
}