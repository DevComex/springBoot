package cn.gyyx.playwd.beans.playwd;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 
  * <p>
  *   用户角色封装bean
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL) 
public class RoleBean {
    private Integer code;

    private Integer userId;

    private String account;

    private Integer netId;

    private Integer serverId;

    private String serverName;

    private String nickName;

    private Integer level;

    private String faction;

    private Boolean isDefault;

    private String picture;

    private String qq;

    private String gender;
    
    private Date updateTime;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getNetId() {
        return netId;
    }

    public void setNetId(Integer netId) {
        this.netId = netId;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getlevel() {
        return level;
    }

    public void setlevel(Integer level) {
        this.level = level;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction == null ? null : faction.trim();
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}