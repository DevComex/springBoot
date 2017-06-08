package cn.gyyx.action.beans.noviceoa;

import java.util.Date;

public class NoviceServerBean {
    private Integer code;

    private Integer batchId;

    private Integer gameId;

    private Integer serverId;

    private String serverName;

    private Integer netTypeCode;

    private Boolean isDelete;

    private Date createTime;

    public NoviceServerBean(Integer code, Integer batchId, Integer gameId, Integer serverId, String serverName, Integer netTypeCode, Boolean isDelete, Date createTime) {
        this.code = code;
        this.batchId = batchId;
        this.gameId = gameId;
        this.serverId = serverId;
        this.serverName = serverName;
        this.netTypeCode = netTypeCode;
        this.isDelete = isDelete;
        this.createTime = createTime;
    }

    public NoviceServerBean() {
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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getNetTypeCode() {
        return netTypeCode;
    }

    public void setNetTypeCode(Integer netTypeCode) {
        this.netTypeCode = netTypeCode;
    }

    @Override
    public String toString() {
        return "NoviceServerBean{" +
                "code=" + code +
                ", batchId=" + batchId +
                ", gameId=" + gameId +
                ", serverId=" + serverId +
                ", serverName='" + serverName + '\'' +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                '}';
    }
}