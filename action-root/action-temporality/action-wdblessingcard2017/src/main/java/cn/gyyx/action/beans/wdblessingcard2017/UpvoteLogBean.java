/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdblessingcard2017
 * @作者：laixiancai
 * @联系方式：laixiancai@gyyx.cn
 * @创建时间：2017年3月9日 上午10:50:04
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.wdblessingcard2017;

import java.util.Date;

/**
 * <p>
 * 投票记录
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class UpvoteLogBean {
    private Integer code;

    private String blessingCardCode;

    private String account;

    private String ip;

    private Date voteDate;

    private int voteSource;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBlessingCardCode() {
        return blessingCardCode;
    }

    public void setBlessingCardCode(String blessingCardCode) {
        this.blessingCardCode = blessingCardCode == null ? null
                : blessingCardCode.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }

    public int getVoteSource() {
        return voteSource;
    }

    public void setVoteSource(int voteSource) {
        this.voteSource = voteSource;
    }

    @Override
    public String toString() {
        return "UpvoteLogBean [code=" + code + ", blessingCardCode="
                + blessingCardCode + ", account=" + account + ", ip=" + ip
                + ", voteDate=" + voteDate + ", voteSource=" + voteSource + "]";
    }

}