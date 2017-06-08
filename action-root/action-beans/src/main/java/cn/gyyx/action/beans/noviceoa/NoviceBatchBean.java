package cn.gyyx.action.beans.noviceoa;

import java.util.Date;

public class NoviceBatchBean {
    private Integer code;

    private String name;

    private Integer gameId;

    private String batchType;

    private String gameName;

    private Boolean isOpen;

    private Date createTime;

    public NoviceBatchBean(Integer code, String name, Integer gameId, String batchType, Boolean isOpen, Date createTime) {
        this.code = code;
        this.name = name;
        this.gameId = gameId;
        this.batchType = batchType;
        this.isOpen = isOpen;
        this.createTime = createTime;
    }

    public NoviceBatchBean() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    @Override
    public String toString() {
        return "NoviceBatchBean{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", gameId=" + gameId +
                ", batchType='" + batchType + '\'' +
                ", gameName='" + gameName + '\'' +
                ", isOpen=" + isOpen +
                ", createTime=" + createTime +
                '}';
    }
}