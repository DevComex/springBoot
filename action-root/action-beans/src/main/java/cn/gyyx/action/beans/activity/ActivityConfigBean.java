package cn.gyyx.action.beans.activity;

import java.util.Date;

public class ActivityConfigBean {
    private Integer code;

    private String activityCode;

    private String note;

    private Date beginTime;

    private Date endTime;

    private Boolean isOpen;

    public ActivityConfigBean(Integer code, String activityCode, String note, Date beginTime, Date endTime, Boolean isOpen) {
        this.code = code;
        this.activityCode = activityCode;
        this.note = note;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.isOpen = isOpen;
    }

    public ActivityConfigBean() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode == null ? null : activityCode.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public String toString() {
        return "ActivityConfigBean{" +
                "code=" + code +
                ", activityCode='" + activityCode + '\'' +
                ", note='" + note + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", isOpen=" + isOpen +
                '}';
    }
}