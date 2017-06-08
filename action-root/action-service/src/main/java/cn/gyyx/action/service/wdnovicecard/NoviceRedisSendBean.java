/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：activity-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/3/2 9:29
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wdnovicecard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 描述:
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceRedisSendBean {
    /**
     * 游戏编号
     */
    private int gameId;

    /**
     * 服务器ip
     */
    private String serverIp;

    /**
     * 服务器端口
     */
    private int serverPort;

    /**
     * 服务器名
     */
    private String serverName;

    /**
     * 账号
     */
    private String account;

    /**
     * 礼包编号
     */
    private String giftPackage;

    /**
     * 失效时间 ex:20180227000051
     */
    private String expiredTime;

    /**
     * 发奖描述
     */
    private String description;

    /**
     * 发奖单号
     */
    private String orderId;

    /**
     * 发奖来源
     */
    private String sourceType;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGiftPackage() {
        return giftPackage;
    }

    public void setGiftPackage(String giftPackage) {
        this.giftPackage = giftPackage;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public String toString() {
        return "NoviceRedisSendBean{" +
                "gameId=" + gameId +
                ", serverIp='" + serverIp + '\'' +
                ", serverPort=" + serverPort +
                ", serverName='" + serverName + '\'' +
                ", account='" + account + '\'' +
                ", giftPackage='" + giftPackage + '\'' +
                ", expiredTime='" + expiredTime + '\'' +
                ", description='" + description + '\'' +
                ", orderId='" + orderId + '\'' +
                ", sourceType='" + sourceType + '\'' +
                '}';
    }

    /**
     * 对象转bite[]数组
     * @return
     */
    public byte[] toByteArray(){
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
