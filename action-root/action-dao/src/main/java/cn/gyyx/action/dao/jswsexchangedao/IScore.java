/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年1月25日下午4:03:19
 * 版本号：v1.0
 * 本类主要用途叙述：绝世武神积分兑换的分数处理接口
 */

package cn.gyyx.action.dao.jswsexchangedao;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.jswsexchange.JSWSScoreBean;

public interface IScore {
	/***
	 * 根据id获取绝世武神的分数
	 * 
	 * @param openId
	 * @return
	 */
	public JSWSScoreBean getJswsScoreBeanByOpenId(
			@Param("openId") String openId, @Param("type") String type);

	/***
	 * 更新积分
	 * 
	 * @param code
	 */
	public void reduceJswsScoreByCode(@Param("code") int code,
			@Param("score") int score);

	public void reduceJswsScoreByOpenId(@Param("OpenId") String OpenId,
			@Param("score") int score);

}
