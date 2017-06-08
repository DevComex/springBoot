package cn.gyyx.action.dao.activityCode;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.activityCode.PresentMesBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class HdActivityCodeDAO implements IHdActivityCodeMapper {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(HdActivityCodeDAO.class);
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
	public PresentMesBean getPresent(int activityCode) {
		SqlSession session = getSession();
		PresentMesBean result = new PresentMesBean();
		try{
			IHdActivityCodeMapper entity = session.getMapper(IHdActivityCodeMapper.class);
			result = entity.getPresent(activityCode);
		}catch(Exception e){
			LOG.error("获取中奖信息失败",e);
		}finally{
			session.close();
		}
		return result;
	}
	@Override
	public int deleteActivityCode(int code) {
		SqlSession session = getSession();
		int result = 0;
		try{
			IHdActivityCodeMapper entity = session.getMapper(IHdActivityCodeMapper.class);
			result = entity.deleteActivityCode(code);
		}catch(Exception e){
			LOG.error("删除奖品息失败",e);
		}finally{
			session.commit();
			session.close();
		}
		return result;
	}
	@Override
	public Integer getReceiveCount(String actionCode) {
		SqlSession session = getSession();
		Integer result = 0;
		try{
			IHdActivityCodeMapper entity = session.getMapper(IHdActivityCodeMapper.class);
			result = entity.getReceiveCount(actionCode);
		}catch(Exception e){
			LOG.error("获取剩余激活码失败",e);
		}finally{
			session.close();
		}
		return result;
	}
	
}
