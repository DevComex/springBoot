/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2016年1月20日下午13:35:23
 * 版本号：
 * 本类主要用途叙述：绝世武神预约分享的数据库交互接口
 */
package cn.gyyx.action.dao.jswsReserve;

import cn.gyyx.action.beans.jswsReserve.ReserveBean;

public interface IReserveMapper {

	/**
	 * 
	 * @Title: insertReserveInfo
	 * @Description: TODO 添加预约信息
	 * @param @param reserveBean
	 * @param @return    
	 * @return int    
	 * @throws
	 */
	public int insertReserveInfo(ReserveBean reserveBean);
	
	/**
	 * 
 	 * @Title: getReserveByPhoneNum
	 * @Description: TODO 查询该手机号码是否已经预约过
	 * @param @param phoneNum
	 * @param @return    
	 * @return ReserveBean    
	 * @throws
	 */
	public ReserveBean getReserveByPhoneNum(String phoneNum);
}
