/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:02:41
 * 版本号：v1.0
 * 本类主要用途叙述：获取充值卡信息信息
 */



package cn.gyyx.action.dao.wechat;

import org.apache.ibatis.annotations.Param;

public interface IWeiXinRecharge {
	/**
	 * 获取充值卡卡密
	 * @param map
	 */
	public void updateRecharge(@Param("actionCode") int actionCode,@Param("type") String type,@Param("openId") String openId);

		public String  getCardCode(@Param("openId") String openId,@Param("actionCode") int actionCode,@Param("type") String type);
	/**
	 * 获取用户中奖的礼品密码和卡号
	 * @param code
	 * @param actionCode
	 * @return
	 */
		public String seltectCardCode(@Param("openId") String openId,@Param("actionCode") int actionCode);
		
		/**
		 * 
		* @Title: getLibaoCode
		* @Description: TODO 获取新手礼包卡卡号
		* @param @param openId
		* @param @param actionCode
		* @param @param type
		* @param @return    
		* @return String    
		* @throws
		 */
		public String  getLibaoCode(@Param("openId") String openId,@Param("actionCode") int actionCode,@Param("type") String type);
}
