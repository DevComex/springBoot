/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午5:11:58
 * 版本号：v1.0
 * 本类主要用途叙述：抽奖的memcache处理
 */



package cn.gyyx.action.bll.lottery;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;

public class LotteryMemcacheBll {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LotteryMemcacheBll.class);
	MemcacheUtil memcacheUtil = new MemcacheUtil();
	XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil.getMemcache();

	/**
	 * 从memcache中获取活动信息
	 * @param actionCode
	 * @return
	 */
	public  ContrParmBean getControParaMem(int actionCode){
		return  memcachedClientAdapter.get(
				actionCode+"ContrParm",  ContrParmBean.class);
		
	}
	/**
	 * 从memcache中获取memcache中的奖品信息
	 * @param actionCode
	 * @return
	 */
	public List<PrizeBean> getPrizeMem(int actionCode){
		if(memcachedClientAdapter.get(
				actionCode+"PrizeBean", PrizeBean[].class)!=null){
			return Arrays.asList(memcachedClientAdapter.get(
					actionCode+"PrizeBean", PrizeBean[].class));
		}
		else{
			return null;
		}
	}
	/**
	 * 从memcache中获取奖品名次的对应值
	 * @param actionCode
	 * @return
	 */
	public List<OrderAndPrizeBean> getPrizeAndOrderInfoMem(int actionCode){
		if (memcachedClientAdapter.get(
				actionCode+"OrderPrizeBean", OrderAndPrizeBean[].class)!=null){
					
			return Arrays.asList(memcachedClientAdapter.get(
					actionCode+"OrderPrizeBean", OrderAndPrizeBean[].class));
		}
		else{
			return null;
		}

	}
	/**
	 * 从memcache中获取中奖信息的值
	 * @param actionCode
	 * @return
	 */
	public List<ChancePrizeBean> getChancePrizeBeanMem(int actionCode){

try {
	List<ChancePrizeBean> list=Arrays.asList(memcachedClientAdapter.get(
			actionCode+"ChancePrizeBean", ChancePrizeBean[].class));
	return list;
	
} catch (Exception e) {
	// TODO: handle exception
	logger.warn(e.getMessage());
	return null;
	
}
	
	}
	
	public List<UserInfoBean> getUserLotteryMem(int actionCode){

		try {
			List<UserInfoBean> list=Arrays.asList(memcachedClientAdapter.get(
					actionCode+"UserInfoBean", UserInfoBean[].class));
			return list;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
			return null;
			
		}
			
			}
	/**
	 * 设定memcache中用户中奖的信息
	 * @param list
	 * @param actionCode
	 * @return
	 */
	public boolean setUserInfoBeanMem(List<UserInfoBean> list,int actionCode) {

		UserInfoBean[] array = new UserInfoBean[0];
		array = list.toArray(array);
		return memcachedClientAdapter.set(actionCode+"UserInfoBean",3600,array);

	}
	/**
	 * 设定memcache中
	 * @param list
	 * @param actionCode
	 * @return
	 */
	public boolean setPrizeAndOrderInfonMem(List<OrderAndPrizeBean> list,int actionCode) {

		OrderAndPrizeBean[] array = new OrderAndPrizeBean[0];
		array = list.toArray(array);
		return memcachedClientAdapter.set(actionCode+"OrderPrizeBean", 3600,array);

	}
	/**
	 * 设定memchache中奖品的值
	 * @param list
	 * @param actionCode
	 * @return
	 */
	public boolean setPrizeBeanMem(List<PrizeBean> list,int actionCode) {

		PrizeBean[] array = new PrizeBean[0];
		array = list.toArray(array);
		return memcachedClientAdapter.set(actionCode+"PrizeBean",3600, array);

	}
/**
 * 设定活动信息的值
 * @param list
 * @param actionCode
 * @return
 */
	public boolean setContrParmMem(ContrParmBean list,int actionCode) {

		
		return memcachedClientAdapter.set(actionCode+"ContrParm",3600, list);

	}
	/**
	 * memcache插入
	 * @param list
	 * @param actionCode
	 * @return
	 */
	public boolean setPrizeChanceBeanMem(List<ChancePrizeBean> list,int actionCode) {

		ChancePrizeBean[] array = new ChancePrizeBean[0];
		array = list.toArray(array);
		return memcachedClientAdapter.set(actionCode+"ChancePrizeBean",3600, array);

	}

	
}
