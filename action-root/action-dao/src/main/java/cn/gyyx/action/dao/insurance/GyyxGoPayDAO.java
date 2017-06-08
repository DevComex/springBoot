package cn.gyyx.action.dao.insurance;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.insurance.InsuranceConfigBean;
import cn.gyyx.action.beans.insurance.TranferInsurerParaBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class GyyxGoPayDAO implements IGyyxGoPay {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(GyyxGoPayDAO.class);
	/**
	 * 获取工厂类
	 * @return
	 */
	private SqlSessionFactory getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory;
	}
	/**
	 * 异步支付修改成功修改状态
	 */
	@Override
	public void paySuccess(String tradeNo,String outTradeNo) {
		SqlSession sqlSession = getSession().openSession();
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			GyyxGoPayDAO.paySuccess(tradeNo,outTradeNo);
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.commit();
			sqlSession.close();
		}
	}
	/**
	 * 查看订单状态
	 */
	@Override
	public String isSuccessPay(String outTradeNo) {
		SqlSession sqlSession = getSession().openSession();
		String condition = "";
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			condition = GyyxGoPayDAO.isSuccessPay(outTradeNo);
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.close();
		}
		return condition;
	}
	/**
	 * PICC投保成功订单号
	 */
	@Override
	public void addPICCOrder(String piccnum,String outTradeNo) {
		SqlSession sqlSession = getSession().openSession();
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			GyyxGoPayDAO.addPICCOrder(piccnum,outTradeNo);
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.commit();
			sqlSession.close();
		}
	}
	/**
	 * 修改支付时间，过期时间，订单状态
	 */
	@Override
	public void alterPaySuccOrderInfo(String outTradeNo, String payTime,
			String overTime) {
		SqlSession sqlSession = getSession().openSession();
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			GyyxGoPayDAO.alterPaySuccOrderInfo(outTradeNo,payTime,overTime);
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.commit();
			sqlSession.close();
		}
	}
	/**
	 * 修改订单状态
	 */
	@Override
	public void alterOrder(String outTradeNo, String status) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = getSession().openSession();
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			GyyxGoPayDAO.alterOrder(outTradeNo,status);
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.commit();
			sqlSession.close();
		}
	}
	/**
	 * 查询投保接口参数
	 */
	@Override
	public TranferInsurerParaBean selectInsurePara(String outTradeNo) {
		SqlSession sqlSession = getSession().openSession();
		TranferInsurerParaBean paraBean = null ;
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			paraBean = GyyxGoPayDAO.selectInsurePara(outTradeNo);
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.close();
		}
		return paraBean;
	}
	@Override
	public int getCircle(String outTradeNo) {
		SqlSession sqlSession = getSession().openSession();
		int result = 0;
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			result = GyyxGoPayDAO.getCircle(outTradeNo);
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.close();
		}
		return result;
	}
	/**
	 * 查询所有配置信息
	 */
	@Override
	public List<InsuranceConfigBean> getAllConfig() {
		SqlSession sqlSession = getSession().openSession();
		List<InsuranceConfigBean> result = new ArrayList<InsuranceConfigBean>();
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			result = GyyxGoPayDAO.getAllConfig();
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.close();
		}
		return result;
	}
	@Override
	public String getInsureKey(String para) {
		SqlSession sqlSession = getSession().openSession();
		String result = "";
		try{
			IGyyxGoPay GyyxGoPayDAO = sqlSession.getMapper(IGyyxGoPay.class);
			result = GyyxGoPayDAO.getInsureKey(para);
		}catch(Exception e){
			LOG.error(e.toString());
		}finally{
			sqlSession.close();
		}
		return result;
	}
	
}
