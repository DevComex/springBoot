package cn.gyyx.action.beans.noviceoa;

import java.util.Date;

public class NoviceGiftBean {
    private Integer code;

    private Integer batchId;

    private String giftName;

    private String giftCode;

    private Boolean isDefault;

    private Boolean isDelete;

    private Date createTime;

    public NoviceGiftBean(Integer code, Integer batchId, String giftName, String giftCode, Boolean isDefault, Boolean isDelete, Date createTime) {
        this.code = code;
        this.batchId = batchId;
        this.giftName = giftName;
        this.giftCode = giftCode;
        this.isDefault = isDefault;
        this.isDelete = isDelete;
        this.createTime = createTime;
    }

    public NoviceGiftBean() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName == null ? null : giftName.trim();
    }

    public String getGiftCode() {
        return giftCode;
    }

    public void setgiftCode(String giftCode) {
        this.giftCode = giftCode == null ? null : giftCode.trim();
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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

    @Override
    public String toString() {
        return "NoviceGiftBean{" +
                "code=" + code +
                ", batchId=" + batchId +
                ", giftName='" + giftName + '\'' +
                ", giftCode='" + giftCode + '\'' +
                ", isDefault=" + isDefault +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                '}';
    }
}