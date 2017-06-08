/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月16日下午3:55:21
 * 版本号：v1.0
 * 本类主要用途叙述：用户中奖信息的数据库交互层的接口
 */

package cn.gyyx.action.dao.lottery;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.UserRecordBean;

public interface IUserRecord {
	/**
	 * 增加一条抽奖记录
	 * 
	 * @param userbean
	 */
	public void addUserRecordBean(UserRecordBean userbean);

	/**
	 * 得到用户抽奖记录信息
	 * 
	 * @param userCode
	 * @return
	 */
	public UserRecordBean getUserRecordBeanByTime(
			@Param("userCode") int userCode,
			@Param("lotteryTime") Date lotteryTime,
			@Param("actionCode") int actionCode,
			@Param("type") String type);
	
	/**
	 * 根据ip查询用户记录
	 * @param ip
	 * @param actionCode
	 * @param type
	 * @return
	 */
	 public UserRecordBean getUserRecordBeanByIp(
	 			@Param("ip") String ip,
	 			@Param("actionCode") int actionCode,
	 			@Param("type") String type);

}
    
