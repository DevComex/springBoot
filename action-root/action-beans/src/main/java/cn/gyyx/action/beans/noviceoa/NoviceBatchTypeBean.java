package cn.gyyx.action.beans.noviceoa;

import java.util.Date;

public class NoviceBatchTypeBean {
    private Integer code;

    private String batchType;

    private String typeName;

    private Boolean isDelete;

    private Date createTime;

    public NoviceBatchTypeBean(Integer code, String batchType, String typeName, Boolean isDelete, Date createTime) {
        this.code = code;
        this.batchType = batchType;
        this.typeName = typeName;
        this.isDelete = isDelete;
        this.createTime = createTime;
    }

    public NoviceBatchTypeBean() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType == null ? null : batchType.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
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