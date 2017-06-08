/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月9日上午11:20:22
 * 版本号：v1.0
 * 本类主要用途叙述：奖品配置的数据库接口
 */

package cn.gyyx.action.dao.wdtenexchangescore;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.exchangescore.PrizeConfigBean;

public interface IPrizeConfig {

	// 获取奖品的主键
	public PrizeConfigBean getPrizeConfigBeanByPrizeCode(
			@Param("prizeCode") int prizeCode);

	// 获取奖品的主键
	public PrizeConfigBean getPrizeConfigBeanByPrizeCodeWithLock(
			@Param("prizeCode") int prizeCode);

	// 兑换奖品更改配置的信息
	public void exchangePrize(@Param("prizeCode") int prizeCode);

	// 获取所有配置信息
	public List<PrizeConfigBean> getAllExchangePrizes();

	// 更新中奖次数了
	public void setTimeByCode(@Param("code") int code,@Param("num") int num);

}
