/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午6:04:33
 * 版本号：v1.0
 * 本类主要用途叙述：挑战关系表
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.challenger.ChallengeRelationshipBean;

public interface IChallengeRelationship {

	/****
	 * 根据挑战人获取一天内挑战人的数量
	 * @param begin
	 * @param end
	 * @param dareId
	 * @return
	 */
	public Integer getCountDareIdChallengeRelationshipBeanInTimeo(
			@Param("begin") String begin, @Param("end") String end,
			@Param("dareId") int dareId);
	
	/****
	 * 挑战人某天挑战某人的信息
	 * @param begin
	 * @param end
	 * @param dareId
	 * @return
	 */
	public ChallengeRelationshipBean getOneTodayChallengeSomeOne(
			@Param("begin") String begin, @Param("end") String end,
			@Param("dareId") int dareId,@Param("userId") int userId);
	
	/**
	 * 增加一条挑战关系记录
	 * @param challengeRelationshipBean
	 */
	public void addChallengeRelationshipBean(ChallengeRelationshipBean challengeRelationshipBean);
	
	/***
	 * 得到我挑战的角色
	 * @param userId
	 * @return
	 */
	public List<String> getRoleNameIChallenge(@Param("userId") int userId);
	
	/***
	 * 得到挑战我的角色
	 * @param userId
	 * @return
	 */
	public List<String> getRoleNameChallengeMe(@Param("userId") int userId);
	
	/****
	 * 根据挑战别人的数量
	 * @param begin
	 * @param end
	 * @param dareId
	 * @return
	 */
	public Integer getCountDareIdChallengeRelation(
			@Param("dareId") int dareId);

}
