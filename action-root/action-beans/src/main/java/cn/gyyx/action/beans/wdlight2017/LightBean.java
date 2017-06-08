package cn.gyyx.action.beans.wdlight2017;

import java.util.Date;

public class LightBean {
    private Integer code;

    private Integer actionCode;

    private Integer level;

    private Integer limitNum;

    private Integer lightType;

    private Date updateTime;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getActionCode() {
        return actionCode;
    }

    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Integer getLightType() {
        return lightType;
    }

    public void setLightType(Integer lightType) {
        this.lightType = lightType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}