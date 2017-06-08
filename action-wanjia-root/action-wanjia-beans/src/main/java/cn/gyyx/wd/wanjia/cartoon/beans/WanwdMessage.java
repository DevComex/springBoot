package cn.gyyx.wd.wanjia.cartoon.beans;

import java.util.Date;

public class WanwdMessage {
    private Integer code;

    private Integer userId;

    private String userName;

    private Integer sourceType;

    private Integer sourceCode;

    private String sourceTitle;

    private Integer messageType;

    private Boolean isDelete;

    private Date createTime;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(Integer sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle == null ? null : sourceTitle.trim();
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}