/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月16日下午3:06:01
 * 版本号：v1.0
 * 本类主要用途叙述：直播的数据库接口
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.challenger.ChallenterLiveBean;

public interface IChallenterLive {

	/**
	 * 增加直播信息
	 * 
	 * @param challenterLiveBean
	 */
	public void addChallenterLive(ChallenterLiveBean challenterLiveBean);

	/***
	 * 得到一个人发布直播的次数
	 * 
	 * @param userCode
	 * @param begin
	 * @param end
	 * @return
	 */
	public Integer getOneCountLiveInday(@Param("userCode") int userCode,
			@Param("begin") String begin, @Param("end") String end);

	/**
	 * 根据状态获取前多少条信息
	 * 
	 * @param size
	 * @param status
	 * @return
	 */
	public List<ChallenterLiveBean> getTopNumChallenterLiveBean(
			@Param("size") int size, @Param("status") String status);

	/***
	 * 根据主键获取一条直播信息
	 * 
	 * @param code
	 * @return
	 */
	public ChallenterLiveBean getOneCodeChallenterLiveBean(
			@Param("code") int code);

	/**
	 * 更新状态与时间
	 * 
	 * @param code
	 * @return
	 */
	public Integer setStateAndTime(@Param("code") int code,
			@Param("status") String status);

	/**
	 * 分页获取待审核直播信息
	 * 
	 * @param size
	 * @param status
	 * @return
	 */
	public List<ChallenterLiveBean> getPageOncheckChallenterLiveBean(
			@Param("size") int size, @Param("pageNo") int pageNo,
			@Param("status") String status);

	/**
	 * 更新状态与时间
	 * 
	 * @param code
	 * @return
	 */
	public Integer getAllCountOncheckChallenterLiveBean();

	/***
	 * 得到一个人发布直播的次数----不带状态
	 * 
	 * @param userCode
	 * @param begin
	 * @param end
	 * @return
	 */
	public Integer getOneCountLiveIndayNoState(@Param("userCode") int userCode,
			@Param("begin") String begin, @Param("end") String end);



}
