/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——保单数据处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.insurance;


import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.MyOrderParameterBean;
import cn.gyyx.action.beans.insurance.OrderBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wd9yearnovicebag.ServerConfigDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class OrderDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerConfigDAO.class);
	private IOrderMapper iOrderMapper ;
	/**
	 * 根据用户名、区组、角色Id查询信息
	 * @param orderBean
	 * @return
	 */
	public Integer selectByNameGroupRole(OrderBean orderBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Integer result = null;
		try {
			iOrderMapper = session.getMapper(IOrderMapper.class);
			result = iOrderMapper.selectByNameGroupRole(orderBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 计算保费总额
	 * @return
	 */
	public Float selectProtectionSUM(MyOrderParameterBean myOrderParameterBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Float result = null;
		try {
			iOrderMapper = session.getMapper(IOrderMapper.class);
			result = iOrderMapper.selectProtectionSUM(myOrderParameterBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 根据订单号更改订单状态
	 * @param orderNum
	 * @param status
	 */
	public void updateOrderStatus(String orderNum ,String status){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iOrderMapper = session.getMapper(IOrderMapper.class);
			iOrderMapper.updateOrderStatus(status,orderNum);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 按照订单号查询订单信息
	 * @param orderNum
	 * @return
	 */
	public OrderBean selectByOrderNum(String orderNum){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		OrderBean orderBean = null;
		try {
			iOrderMapper = session.getMapper(IOrderMapper.class);
			orderBean = iOrderMapper.selectByOrderNum(orderNum);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return orderBean;
	}
	/**
	 * @Title: selectOrderByCondition
	 * @Author:wanglei
	 * @date 2015年07月14日 
	 * @Description: TODO 条件查询保单信息
	 * @param MyOrderParameterBean myOrderParameterBean
	 * 
	 */
	public List<OrderBean> selectOrderByCondition(MyOrderParameterBean myOrderParameterBean) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<OrderBean> resultList = null;
		try {
			iOrderMapper = session.getMapper(IOrderMapper.class);
			//
			resultList = iOrderMapper.selectOrderByCondition(myOrderParameterBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return resultList;

	}
	/**
	 * @Title: selectOrderByCondition
	 * @Author:wanglei
	 * @date 2015年07月14日 
	 * @Description: TODO 条件查询保单信息为Excel
	 * @param MyOrderParameterBean myOrderParameterBean
	 * 
	 */
	public List<OrderBean> selectOrderByConditionExcel(MyOrderParameterBean myOrderParameterBean) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<OrderBean> resultList = null;
		try {
			iOrderMapper = session.getMapper(IOrderMapper.class);
			//
			resultList = iOrderMapper.selectOrderByConditionExcel(myOrderParameterBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return resultList;

	}
	/**
	 * @Title: selectCountByCondition
	 * @Author:wanglei
	 * @date 2015年07月15日 
	 * @Description: TODO 条件查询保单信息条数
	 * @param MyOrderParameterBean myOrderParameterBean
	 * 
	 */
	public Integer selectCountByCondition(MyOrderParameterBean myOrderParameterBean) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Integer count = null;
		try {
			iOrderMapper = session.getMapper(IOrderMapper.class);
			
			count = iOrderMapper.selcetCountByCondition(myOrderParameterBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return count;
	}

	/**
	 * 增加保单
	 * @param orderBean
	 */
	public void addOrder(OrderBean orderBean){
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IOrderMapper iOrderMapper = sqlSession
					.getMapper(IOrderMapper.class);
			iOrderMapper.addOrder(orderBean);
			sqlSession.commit();
	
		}
	}

}
