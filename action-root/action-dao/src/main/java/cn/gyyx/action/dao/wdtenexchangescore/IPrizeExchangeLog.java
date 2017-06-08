/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年4月9日下午12:13:51
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.dao.wdtenexchangescore;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.exchangescore.PrizeExchangeLogBean;

public interface IPrizeExchangeLog {
	/**
	 * 根据奖品主键和活动主键
	 * 
	 * @param prizeCode
	 * @return
	 */
	public PrizeExchangeLogBean getTopOneByPrizeAndUserOrderByTime(
			@Param("prizeCode") int prizeCode,
			@Param("actionCode") int actionCode, @Param("userCode") int userCode);

	/***
	 * 增加一条日志信息
	 * 
	 */
	public void addPrizeExchangeLog(PrizeExchangeLogBean prizeExchangeLogBean);

	/***
	 * 
	 * 得到所有中奖信息
	 * 
	 * @return
	 */
	public List<PrizeExchangeLogBean> getAllPrizeExchangeLogBeans();

	/**
	 * 得到用户的兑换信息
	 * 
	 * @param actionCode
	 * @param userCode
	 * @return
	 */
	public List<PrizeExchangeLogBean> getExchangeLogBeansByUserCode(
			@Param("actionCode") int actionCode, @Param("userCode") int userCode);

}
