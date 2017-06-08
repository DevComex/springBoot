/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——理赔数据处理接口
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.insurance;

import java.util.List;

import cn.gyyx.action.beans.insurance.MyOrderParameterBean;
import cn.gyyx.action.beans.insurance.ReparationBean;

public interface IReparationMapper {
	/**
	 * 计算理赔金额总额
	 * @return
	 */
	public Float selectReparationSUM(MyOrderParameterBean myOrderParameterBean);
	/**
	 * 查询分页总条数
	 * @param myOrderParameterBean
	 * @return
	 */
	public Integer selectPageCount(MyOrderParameterBean myOrderParameterBean);
	/**
	 * 分页查询
	 * @param myOrderParameterBean
	 * @return
	 */
	public List<ReparationBean> selectPage(MyOrderParameterBean myOrderParameterBean);
	/**
	 * 插入理赔表
	 * @param reparationBean
	 */
	public void insertReparation(ReparationBean reparationBean);
	/**
	 * 根据订单号更改理赔说明
	 * @param orderNum
	 */
	public void updateReparationOrderNum(String orderNum, String reparationResult);
	/**
	 * 根据订单号更改理赔金额
	 * @param orderNum
	 * @param reparationNum
	 */
	public void setReparation(String orderNum, Float reparationNum);
	/**
	 * 根据订单号查询理赔信息
	 * @param OrderNum
	 * @return
	 */
	public ReparationBean selectReparationBeanByOrderNum(String OrderNum);
	/**
	 * 为导出Excel条件查询
	 * @param myOrderParameterBean
	 * @return
	 */
	public List<ReparationBean> selectExcel(
			MyOrderParameterBean myOrderParameterBean);
	
}
