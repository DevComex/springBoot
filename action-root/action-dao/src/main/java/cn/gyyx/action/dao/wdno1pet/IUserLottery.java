/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日下午1:58:11
 * 版本号：v1.0
 * 本类主要用途叙述：用户中奖资格的DAO层接口
 */



package cn.gyyx.action.dao.wdno1pet;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.wdno1pet.UserLotteryBean;

public interface IUserLottery {
	
	/**
	 * 查询可以抽奖的总人数
	 * @return int 中奖总人数
	 */
	public int  selectNumLucky(); 
	/**
	 * 向用户中奖信息表中插入中奖信息
	 * @param lBean 中奖信息
	 */
	public void insertUserLottery(UserLotteryBean userLotteryBean);
	/**
	 * 将用户的信息插入到中奖信息表中
	 * @param info
	 */
	public void updateUserAdress(@Param("lotteryAdress")String info,@Param("userCode")int userCode);
	/**
	 * 通过用户ID来查找用户中奖的资格
	 * @param userCode
	 * @return userLotteryBean 用户中奖信息
	 */
	public UserLotteryBean selectUserLotteryByUserCode(@Param("userCode") int userCode);
	/**
	 * 更新用户的中奖信息
	 * @param userCode
	 */
	public void  updateUserLotteryStatusAndType(@Param("userCode")int userCode);
	/**
	 * 更该用户的中奖状态
	 * @param lotteryStatus
	 */
	public void updateUserLotteryStatus(@Param("lotteryStatus") String lotteryStatus,@Param("userCode") int userCode);
	/**
	 * 更新账户的中奖信息
	 * @param userCode
	 * @return errorCode
	 */
	public void updateLotteryType(HashMap<String, Integer> map);
	/**
	 * 想中奖信息表中插入记录
	 * @param userCode
	 */
	public void insertUser(UserLotteryBean uLotteryBean);
    

}
