package cn.gyyx.action.beans.wdlight2017;

import java.util.Date;

public class WishBean {
    private Integer code;

    private Integer prizeCode;

    private Integer userId;

    private Integer level;

    private String roleName;

    private String serverName;

    private Date createTime;

    private String prizeName;

    private Integer serverId;

    private String content;

    private Integer status;

    private String presenttype;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getPrizeCode() {
        return prizeCode;
    }

    public void setPrizeCode(Integer prizeCode) {
        this.prizeCode = prizeCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPresenttype() {
        return presenttype;
    }

    public void setPresenttype(String presenttype) {
        this.presenttype = presenttype == null ? null : presenttype.trim();
    }
}