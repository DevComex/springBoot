package cn.gyyx.action.dao.lhzs.oa.activityCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.oa.lhzs.activityCode.ActivityCodeChannelBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.UserMesBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LHZSendPresentLogDAO implements ILHZSendPresentLogMapper {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(LHZSendPresentLogDAO.class);
	/**
	 * 
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 激活-产品
	 */
	@Override
	public Integer getActivationProduct(String startTime,String endTime) {
		SqlSession session = getSession();
		Integer result = -1;
		try{
			ILHZSendPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSendPresentLogMapper.class);
			result = iPresentLogMapper.getActivationProduct(startTime, endTime);
		}catch(Exception e){
			LOG.error("查找激活-产品个数出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	/**
	 * 激活-市场
	 */
	@Override
	public Integer getActivationMarket(String startTime,String endTime) {
		SqlSession session = getSession();
		Integer result = -1;
		try{
			ILHZSendPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSendPresentLogMapper.class);
			result = iPresentLogMapper.getActivationMarket(startTime, endTime);
		}catch(Exception e){
			LOG.error("查找激活-市场个数出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	/**
	 * 发放-产品 （发放未激活）
	 */
	@Override
	public Integer getSendMarket(String startTime,String endTime) {
		SqlSession session = getSession();
		Integer result = -1;
		try{
			ILHZSendPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSendPresentLogMapper.class);
			result = iPresentLogMapper.getSendMarket(startTime, endTime);
		}catch(Exception e){
			LOG.error("查找发放-产品（发放未激活）个数出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	
	/**
	 * 发放-产品 （当天发放激活）
	 */
	@Override
	public Integer getSendMarket2(String startTime, String endTime) {
		SqlSession session = getSession();
		Integer result = -1;
		try{
			ILHZSendPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSendPresentLogMapper.class);
			result = iPresentLogMapper.getSendMarket2(startTime, endTime);
		}catch(Exception e){
			LOG.error("查找发放-产品 （当天发放激活）个数出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	
	@Override
	public void insertActivationCode(Map<String, Object> para) {
		SqlSession session = getSession();
		try{
			ILHZSendPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSendPresentLogMapper.class);
			iPresentLogMapper.insertActivationCode(para);
		}catch(Exception e){
			LOG.error("添加激活码出错！！",e);
		}finally{
			session.commit();
			session.close();
		}
	}
	@Override
	public List<ActivityCodeChannelBean> searchActivityChannel() {
		SqlSession session = getSession();
		List<ActivityCodeChannelBean> result = new ArrayList<ActivityCodeChannelBean>();
		try{
			ILHZSendPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSendPresentLogMapper.class);
			result = iPresentLogMapper.searchActivityChannel();
		}catch(Exception e){
			LOG.error("查找所有渠道出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	
	public List<UserMesBean> getActivityMarketDetailed(String startTime,String endTime){
		SqlSession session = getSession();
		List<UserMesBean> result = new ArrayList<UserMesBean>();
		try{
			ILHZSendPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSendPresentLogMapper.class);
			result = iPresentLogMapper.getActivityMarketDetailed(startTime,endTime);
		}catch(Exception e){
			LOG.error("查询激活市场详细信息出错！！",e);
		}finally{
			session.close();
		}
		return result;
		
	}
	/**
	 * 查询激活市场详细信息
	 */
	@Override
	public List<UserMesBean> getActivityMarketDetailedToCode(String startTime,
			String endTime, int startCode, int endCode) {
		SqlSession session = getSession();
		List<UserMesBean> result = new ArrayList<UserMesBean>();
		try{
			ILHZSendPresentLogMapper iPresentLogMapper = session.getMapper(ILHZSendPresentLogMapper.class);
			result = iPresentLogMapper.getActivityMarketDetailedToCode(startTime,endTime,startCode,endCode);
		}catch(Exception e){
			LOG.error("查询激活市场详细信息出错！！",e);
		}finally{
			session.close();
		}
		return result;
	}
	
}
