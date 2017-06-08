/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午1:26:31
 * 版本号：v1.0
 * 本类主要用途叙述：挑战的接口
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.challenger.ChallenterInfoBean;

public interface IChallenterInfo {
	/***
	 * 增加一条挑战信息
	 * 
	 * @param challenterInfoBean
	 */
	public void addChallenterInfo(ChallenterInfoBean challenterInfoBean);

	/***
	 * 获取某个人合法的挑战信息
	 * 
	 * @param userCode
	 * @return
	 */
	public ChallenterInfoBean getOneChallenterInfoWithoutUnPass(
			@Param("userCode") int userCode);

	/***
	 * 页码状态获取挑战信息
	 * 
	 * @param pageNo
	 * @param size
	 * @param state
	 * @return
	 */
	public List<ChallenterInfoBean> getStateChallenterInfo(
			@Param("pageNo") int pageNo, @Param("size") int size,
			@Param("status") String state);

	/***
	 * 获取某状态下挑战的总数
	 * 
	 * @param state
	 * @return
	 */
	public Integer getCountStateChallenterInfo(@Param("status") String state);

	/***
	 * 增加挑战次数
	 */
	public void addChallengeTimes(@Param("userCode") int userCode);

	/**
	 * 获取某状态某人的挑战信息
	 */
	public ChallenterInfoBean getOneStatusChallenterInfo(
			@Param("userCode") int userCode, @Param("status") String status);

	/***
	 * 找到前五个被人揍得最多的玩家
	 * 
	 * @return
	 */
	public List<ChallenterInfoBean> getTopFiveChallenterInfo();

	/**
	 * 根据主键获取一条挑战信息
	 * 
	 * @param code
	 * @return
	 */
	public ChallenterInfoBean getOneChallenterInfo(@Param("code") int code);
	
	/***
	 * 设定一个挑战的状态和时间
	 * @param code
	 * @return
	 */
	public Integer setOneStatusAndTime(@Param("code") int code,@Param("status") String status);
}
