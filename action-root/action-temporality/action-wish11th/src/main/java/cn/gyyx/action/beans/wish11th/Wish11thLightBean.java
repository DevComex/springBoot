/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：tanjunkai        
 * @联系方式：tanjunkai@gyyx.cn
 * @创建时间：2017/4/1 13:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wish11th;

import java.util.Date;


/**
  * <p>
  *   许愿层实体
  * </p>
  *  
  * @author tanjunkai
  * @since 0.0.1
  */
public class Wish11thLightBean {
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