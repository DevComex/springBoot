/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——订单数据处理接口
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.insurance;


import java.util.List;

import cn.gyyx.action.beans.insurance.MyOrderParameterBean;
import cn.gyyx.action.beans.insurance.OrderBean;





public interface IOrderMapper {
	/**
	 * 根据用户名、区组、角色Id查询信息
	 * @param orderBean
	 * @return
	 */
	public Integer selectByNameGroupRole(OrderBean orderBean);
	/**
	 * 计算保费总额
	 * @return
	 */
	public Float selectProtectionSUM(MyOrderParameterBean myOrderParameterBean);

	/**
	 * 增加保单
	 */
	public void addOrder(OrderBean orderBean);
	/**
	 * 根据订单号更改订单状态和理赔金额
	 * @param orderNum
	 * @param status
	 */
	public void updateOrderStatus(String status, String orderNum);

	/**
	 * 按照订单号查询订单信息
	 * @param orderNum
	 * @return
	 */
	public OrderBean selectByOrderNum(String orderNum);
	/**
	 * @Title: selectOrderByCondition
	 * @Author:wanglei
	 * @date 2015年07月14日 
	 * @Description: TODO 条件查询保单信息
	 * @param MyOrderParameterBean myOrderParameterBean
	 * 
	 */
	public List<OrderBean> selectOrderByCondition(MyOrderParameterBean myOrderParameterBean) ;
	/**
	 * @Title: selectOrderByCondition
	 * @Author:wanglei
	 * @date 2015年07月14日 
	 * @Description: TODO 条件查询保单信息为Excel
	 * @param MyOrderParameterBean myOrderParameterBean
	 * 
	 */
	public List<OrderBean> selectOrderByConditionExcel(MyOrderParameterBean myOrderParameterBean) ;
	/**
	 * 根据时间和状态查询符合的数据条数
	 * @param myOrderParameterBean
	 * @return Integer：数据条数
	 */
	public Integer selcetCountByCondition(MyOrderParameterBean myOrderParameterBean) ;
}
