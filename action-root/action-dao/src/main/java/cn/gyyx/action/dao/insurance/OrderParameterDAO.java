/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：王雷
 * 联系方式：wanglei@gyyx.cn 
 * 创建时间：2015年07月14日 
 * 版本号：v1.0 
 * 本类主要用途描述： 虚拟保险项目——保单参数数据处理类
 * 
-------------------------------------------------------------------------*/

package cn.gyyx.action.dao.insurance;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.OrderParameterBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wd9yearnovicebag.ServerConfigDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class OrderParameterDAO implements IOrderParameterMapper {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerConfigDAO.class);
	private IOrderParameterMapper iOrderParameterMapper;
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
	@Override
	public List<OrderParameterBean> getAllOrderParameterBeans() {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IOrderParameterMapper iOrderParameterMapper = sqlSession
					.getMapper(IOrderParameterMapper.class);
			return iOrderParameterMapper.getAllOrderParameterBeans();
	
		}
	}
	/**
	 * 按照code查询信息
	 * @param code
	 * @return
	 */
	public OrderParameterBean selectBycode(Integer code){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		OrderParameterBean orderParameterBean = null;
		try {
			iOrderParameterMapper = session.getMapper(IOrderParameterMapper.class);
			orderParameterBean = iOrderParameterMapper.selectBycode(code);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return orderParameterBean;
	}
	/**
	 * 增加参数表数据
	 * @param orderParameterBean
	 */
	public void addOrderParameter(OrderParameterBean orderParameterBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iOrderParameterMapper = session.getMapper(IOrderParameterMapper.class);
			iOrderParameterMapper.addOrderParameter(orderParameterBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();			
		}	
	}
	/**
	 * 更新表中IsDelete字段
	 * @param code
	 */
	public void updateIsDelete(Integer code){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iOrderParameterMapper = session.getMapper(IOrderParameterMapper.class);
			iOrderParameterMapper.updateIsDelete(code);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 按照Code更改数据表信息
	 * @param orderParameterBean
	 */
	public void updateByCode(OrderParameterBean orderParameterBean){
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		try {
			iOrderParameterMapper = session.getMapper(IOrderParameterMapper.class);
			iOrderParameterMapper.updateByCode(orderParameterBean);
			session.commit();
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close(); 
		}
	}

}
