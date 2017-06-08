/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:02:41
 * 版本号：v1.0
 * 本类主要用途叙述：获取充值卡信息信息
 */

package cn.gyyx.action.dao.newlottery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lottery.RechargeBean;

public interface IRechargeNew {
	/**
	 * 获取充值卡卡密
	 * 
	 * @param map
	 */
	public void updateRecharge(@Param("actionCode") int actionCode,
			@Param("type") String type, @Param("userCode") int code);

	public String getCardCode(@Param("userCode") int code,
			@Param("actionCode") int actionCode, @Param("type") String type);

	/**
	 * 获取用户中奖的礼品密码和卡号
	 * 
	 * @param code
	 * @param actionCode
	 * @return
	 */
	public String selectCardCode(@Param("userCode") int code,
			@Param("actionCode") int actionCode);
	
	
	public List<String> selectAllCardCode(@Param("userCode") int code,
			@Param("actionCode") int actionCode);
	
	public RechargeBean getCardCodeByCardCode(@Param("actionCode") int actionCode, @Param("type") String type,
			@Param("cardCode") String cardCode);
}
