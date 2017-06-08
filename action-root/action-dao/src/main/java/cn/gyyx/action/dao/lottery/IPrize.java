/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午1:05:26
 * 版本号：v1.0
 * 本类主要用途叙述：奖品信息的数据库交互接口
 */



package cn.gyyx.action.dao.lottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.PrizeBean;

public interface IPrize {
	
	public List<PrizeBean> getPrize(@Param("actionCode") int actionCode);
	public PrizeBean getPrizeByCode(@Param("code") int code);
	public Integer updatePrizeBean(PrizeBean prizeBean);
	public Integer updatePrizeBeanAll(PrizeBean prizeBean);
	public List<PrizeBean> getPrizeAll(@Param("actionCode") int actionCode);
	public Integer insertPrize(PrizeBean prizeBean);
	public PrizeBean getPrizeByNum(PrizeBean prizeBean);

}
