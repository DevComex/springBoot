/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——保单参数业务处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.bll.insurance;

import java.util.List;

import cn.gyyx.action.beans.insurance.OrderBean;
import cn.gyyx.action.beans.insurance.OrderParameterBean;
import cn.gyyx.action.dao.insurance.IOrderParameterMapper;
import cn.gyyx.action.dao.insurance.OrderParameterDAO;

public class OrderParameterBLL {
	private IOrderParameterMapper iOrderParameterMapper =new OrderParameterDAO();
	private OrderParameterDAO orderParameterDAO = new OrderParameterDAO();
	/**
	 * 按照code查询信息
	 * @param code
	 * @return
	 */
	public OrderParameterBean selectBycode(Integer code){
		return orderParameterDAO.selectBycode(code);
	}
	/**
	 * 得到所有的保险参数信息
	 * @return
	 */
	public List<OrderParameterBean> getAllOrderParameterBeans(){
		return iOrderParameterMapper.getAllOrderParameterBeans();
		
	}
	/**
	 * 增加参数表数据
	 * @param orderParameterBean
	 */
	public void addOrderParameter(OrderParameterBean orderParameterBean){
		orderParameterDAO.addOrderParameter(orderParameterBean);
	}
	/**
	 * 更新表中IsDelete字段
	 * @param code
	 */
	public void updateIsDelete(Integer code){
		orderParameterDAO.updateIsDelete(code);
	}
	/**
	 * 按照Code更改数据表信息
	 * @param orderParameterBean
	 */
	public void updateByCode(OrderParameterBean orderParameterBean){
		orderParameterDAO.updateByCode(orderParameterBean);
	}
}
