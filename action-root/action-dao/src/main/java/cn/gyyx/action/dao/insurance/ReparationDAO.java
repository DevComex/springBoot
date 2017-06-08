/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——理赔数据处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.insurance;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.MyOrderParameterBean;
import cn.gyyx.action.beans.insurance.ReparationBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wd9yearnovicebag.ServerConfigDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ReparationDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerConfigDAO.class);
	private IReparationMapper iReparationMapper;
	/**
	 * 查询分页总条数
	 * @param myOrderParameterBean
	 * @return
	 */
	public Integer selectPageCount(MyOrderParameterBean myOrderParameterBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Integer count  = null;
		try {
			iReparationMapper = session.getMapper(IReparationMapper.class);
			count = iReparationMapper.selectPageCount(myOrderParameterBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	/**
	 * 计算理赔金额总额
	 * @return
	 */
	public Float selectReparationSUM(MyOrderParameterBean myOrderParameterBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		Float result = null;
		try {
			iReparationMapper = session.getMapper(IReparationMapper.class);
			result = iReparationMapper.selectReparationSUM(myOrderParameterBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return result;
	}
	/**
	 * 分页查询
	 * @param myOrderParameterBean
	 * @return
	 */
	public List<ReparationBean> selectPage(MyOrderParameterBean myOrderParameterBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<ReparationBean> reparationList = new ArrayList<ReparationBean>();
		try {
			iReparationMapper = session.getMapper(IReparationMapper.class);
			reparationList = iReparationMapper.selectPage(myOrderParameterBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return reparationList;
	}
	/**
	 * 为导出Excel条件查询
	 * @param myOrderParameterBean
	 * @return
	 */
	public List<ReparationBean> selectExcel(
			MyOrderParameterBean myOrderParameterBean) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<ReparationBean> reparationList = new ArrayList<ReparationBean>();
		try {
			iReparationMapper = session.getMapper(IReparationMapper.class);
			reparationList = iReparationMapper.selectExcel(myOrderParameterBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return reparationList;
	}
	/**
	 * 插入理赔表
	 * @param reparationBean
	 */
	public void insertReparation(ReparationBean reparationBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iReparationMapper = session.getMapper(IReparationMapper.class);
			iReparationMapper.insertReparation(reparationBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 根据订单号更改理赔说明
	 * @param orderNum
	 */
	public void updateReparationOrderNum(String orderNum, String reparatioResult){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iReparationMapper = session.getMapper(IReparationMapper.class);
			iReparationMapper.updateReparationOrderNum(orderNum, reparatioResult);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 根据订单号更改理赔金额
	 * @param orderNum
	 * @param reparationNum
	 */
	public void setReparation(String orderNum, Float reparationNum) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iReparationMapper = session.getMapper(IReparationMapper.class);
			iReparationMapper.setReparation(orderNum, reparationNum);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		
	}
	/**
	 * 根据订单号查询理赔信息
	 * @param OrderNum
	 * @return
	 */
	public ReparationBean selectReparationBeanByOrderNum(String OrderNum){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		ReparationBean reparationBean = null;
		try {
			iReparationMapper = session.getMapper(IReparationMapper.class);
			reparationBean = iReparationMapper.selectReparationBeanByOrderNum(OrderNum);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return reparationBean;
	}
	

}
