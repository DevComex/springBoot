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
 * 许愿实体
 * </p>
 * 
 * @author tanjunkai
 * @since 0.0.1
 */
public class Wish11thLightInfoBean extends Wish11thLightBean {
    /**
     * 该层许愿人数
     */
    private Integer wishNum;

    /**
     * 抽奖次数
     */
    private Integer lotteryNum;

    public Integer getWishNum() {
        return wishNum;
    }

    public void setWishNum(Integer wishNum) {
        this.wishNum = wishNum;
    }

    public Integer getLotteryNum() {
        return lotteryNum;
    }

    public void setLotteryNum(Integer lotteryNum) {
        this.lotteryNum = lotteryNum;
    }
}
