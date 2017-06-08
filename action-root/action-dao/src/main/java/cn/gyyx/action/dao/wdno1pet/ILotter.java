/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日上午11:25:26
 * 版本号：v1.0
 * 本类主要用途叙述：中奖配置信息的DAO层接口
 */



package cn.gyyx.action.dao.wdno1pet;


import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdno1pet.LotteryConfigBean;

public interface ILotter {
	/**
	 * 向中奖信息表中插入信息
	 * @param lConfig
	 */
	public void insertLotter(LotteryConfigBean lConfig);
	/**
	 * 查询中奖配置表中的数量
	 */
    public int  selectConutLottery();
    /**
     * 删除配置表
     */
    public void deleteLotteryConfig();
    /**
     * 根据名词查询奖品
     * @param lcOrder
     * @return LotteryConfigBean 获奖信息
     */
    public LotteryConfigBean selectLotteryConfigByOrder(@Param("LotterOrder") int lcOrder);
    

}

