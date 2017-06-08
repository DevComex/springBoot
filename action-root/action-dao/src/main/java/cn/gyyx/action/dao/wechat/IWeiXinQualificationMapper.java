/*************************************************
       Copyright ©, 2015, GY Game
       Author: 王雷
       Created: 2015年-12月-21日
       Note: 微信端用户资格接口
************************************************/
package cn.gyyx.action.dao.wechat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wechat.WeiXinQualificationBean;

public interface IWeiXinQualificationMapper {

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: selectQualification
	 * @Description: TODO 根据用户code查询用户资格信息
	 * @param userCode
	 * @return WeiXinQualificationBean
	 */
	public WeiXinQualificationBean selectQualification(String openId);

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: updateLotteryTime
	 * @Description: TODO 根据用户code更新用户抽奖次数
	 * @param qualification
	 */
	public void updateLotteryTime(WeiXinQualificationBean qualification);
	
	/**
	 * 根据用户编号和活动编号获取抽奖资格信息
	 * @日期：2015年4月9日
	 * @Title: selectByUserAndAction 
	 * @param map 条件集合，包括用户Code和活动Code
	 * @return 
	 * WeiXinQualificationBean 用户抽奖资格信息
	 */
	public WeiXinQualificationBean selectByUserAndAction(Map<String, String> map);
	/**
	 * 插入用户资格 ——问道康师傅V1.211活动需要
	 * @param qualification
	 */
	public void insertQualification(WeiXinQualificationBean qualification);
	/**
	 * 减少抽奖次数
	 * @param userCode
	 * @param actionCode
	 */
	public void reduceTime(@Param("openId") String openId,
	@Param("actionCode") int actionCode);
	public List<WeiXinQualificationBean> selectByAction(@Param("actionCode") int actionCode);

}
	
