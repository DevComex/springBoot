/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午5:42:17
 * 版本号：v1.0
 * 本类主要用途叙述：用户报名信息的数据库接口
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;

public interface IChallenterUserInfo {

	/***
	 * 更改报名状态
	 * 
	 * @param userCode
	 * @param status
	 */
	public Integer setUserInfoState(@Param("code") int code,
			@Param("status") String status);

	/***
	 * 根据状态获取报名信息
	 * 
	 * @param status
	 * @return
	 */
	public List<ChallenterUserInfoBean> getStatusChallenterUserInfo(
			@Param("pageNo") int pageNo, @Param("size") int pageSize,
			@Param("status") String status);

	/***
	 * 根据状态查询数量
	 */
	public Integer getCountStatusChallenterUserInfo(
			@Param("status") String status);

	/***
	 * 根据状态获取某人的报名信息
	 * 
	 * @param userCode
	 * @param status
	 * @return
	 */
	public ChallenterUserInfoBean getOneChallenterUserInfo(
			@Param("userCode") int userCode, @Param("status") String status);

	/***
	 * 根据状态获取某人的报名信息
	 * 
	 * @param userCode
	 * @param status
	 * @return
	 */
	public ChallenterUserInfoBean getCodeChallenterUserInfo(
			@Param("code") int code, @Param("status") String status);

	public ChallenterUserInfoBean getUserInfoBean(
			ChallenterUserInfoBean userInfoBean);

	public int getUserInfoCount(ChallenterUserInfoBean userInfoBean);

	public int insert(ChallenterUserInfoBean userInfoBean);
    /**
     * 得到用户最后一条中奖信息
     * @param userCode
     * @return
     */
	public ChallenterUserInfoBean getLastOneChallenterUserInfo(
			@Param("userCode") int userCode);

	public List<ChallenterUserInfoBean> getUserInfoListByIds(
			@Param("userIds") List<Integer> userIds);
	
	List<ChallenterUserInfoBean> getTeamMemberUserInfoListByTeamIds(
			@Param("teamIds") List<Integer> teamIds);


}
