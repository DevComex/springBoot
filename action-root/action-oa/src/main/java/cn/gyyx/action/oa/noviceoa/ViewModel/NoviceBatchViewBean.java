/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/24 16:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.noviceoa.ViewModel;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * <p>
 * 描述:批次管理视图模型
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceBatchViewBean {
    private int batchCode;

    @Min(value = 1, message = "游戏编号无效")
    private int gameId;

    @NotBlank(message = "批次名不可为空")
    private String batchName;

    @NotBlank(message = "批次类型不可为空")
    private String batchType;

    private String serverName;

    private String serverId;

    private Long startTime;

    private Long endTime;

    private boolean isOpen;

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean open) {
        isOpen = open;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(int batchCode) {
        this.batchCode = batchCode;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    @Override
    public String toString() {
        return "NoviceBatchViewBean{" +
                "batchCode=" + batchCode +
                ", gameId=" + gameId +
                ", batchName='" + batchName + '\'' +
                ", batchType='" + batchType + '\'' +
                ", serverName='" + serverName + '\'' +
                ", serverId='" + serverId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isOpen=" + isOpen +
                '}';
    }
}
