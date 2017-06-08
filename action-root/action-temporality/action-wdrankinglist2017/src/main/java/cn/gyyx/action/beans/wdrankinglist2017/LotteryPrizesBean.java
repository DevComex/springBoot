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
package cn.gyyx.action.beans.wdrankinglist2017;

import java.util.Date;

/**
 * <p>
 * 奖品信息
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
public class LotteryPrizesBean {

    private int code;

    private String prizeName;

    private String roleName;

    private Date winningAt;

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the winningAt
     */
    public Date getWinningAt() {
        return winningAt;
    }

    /**
     * @param winningAt
     *            the winningAt to set
     */
    public void setWinningAt(Date winningAt) {
        this.winningAt = winningAt;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LotteryPrizesBean [code=" + code + ", prizeName=" + prizeName
                + ", roleName=" + roleName + ", winningAt=" + winningAt + "]";
    }

}
