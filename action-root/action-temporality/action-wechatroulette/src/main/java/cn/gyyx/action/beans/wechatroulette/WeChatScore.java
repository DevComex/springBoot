/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月16日 下午3:57:36
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.beans.wechatroulette;

import java.util.Date;

/**
 * <p>
 * 微信积分表实体
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class WeChatScore {
    private Integer code;

    private String openId;

    private Integer score;

    private Date createTime;

    private Date updateTime;

    private String type;

    private String nickName;

    /**
     * 
     */
    public WeChatScore() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param code
     * @param openId
     * @param score
     * @param createTime
     * @param updateTime
     * @param type
     * @param nickName
     */
    public WeChatScore(Integer code, String openId, Integer score,
            Date createTime, Date updateTime, String type, String nickName) {
        super();
        this.code = code;
        this.openId = openId;
        this.score = score;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.type = type;
        this.nickName = nickName;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return the openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId
     *            the openId to set
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * @return the score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @param score
     *            the score to set
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return the nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     *            the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WeChatScore [code=" + code + ", openId=" + openId + ", score="
                + score + ", createTime=" + createTime + ", updateTime="
                + updateTime + ", type=" + type + ", nickName=" + nickName
                + "]";
    }

}
