package cn.gyyx.action.dao.mobile.website;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.mobile.website.MobileRegisteBean;
import cn.gyyx.action.beans.mobile.website.MobileWebsiteRegisteLog;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class RegisteDAO {
	private static final Logger LOG = GYYXLoggerFactory
			.getLogger(RegisteDAO.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 插入注册信息
	 * @param para
	 * @return
	 */
	public Integer insertRegiste(MobileRegisteBean para){
		Integer result = -1;
		SqlSession session = getSession();
		try{
			IRegisteMapper registe = session.getMapper(IRegisteMapper.class);
			result = registe.insertRegiste(para);
		}catch(Exception e){
			e.printStackTrace();
			LOG.warn("insert mobile official website registe message erro!",e);
		}finally{
			session.commit();
			session.close();
		}
		return result;
	}
	public Integer isExist(String phone){
		Integer result = -1;
		SqlSession session = getSession();
		try{
			IRegisteMapper registe = session.getMapper(IRegisteMapper.class);
			result = registe.isExist(phone);
		}catch(Exception e){
			LOG.warn("isExist  erro!",e);
		}finally{
			session.close();
		}
		return result;
	}
	public Integer insertRegisteLog(MobileWebsiteRegisteLog para){
		Integer result = -1;
		SqlSession session = getSession();
		try{
			IRegisteMapper registe = session.getMapper(IRegisteMapper.class);
			result = registe.insertRegisteLog(para);
		}catch(Exception e){
			LOG.warn("insert mobile website registe log erro!",e);
		}finally{
			session.commit();
			session.close();
		}
		return result;
	}
}
