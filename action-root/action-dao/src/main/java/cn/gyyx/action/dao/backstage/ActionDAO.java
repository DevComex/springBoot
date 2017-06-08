package cn.gyyx.action.dao.backstage;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.backstage.ActionBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ActionDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ActionDAO.class);

	/**
	 * 
	 * @日期：2015年7月10日
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
	 * 增加权限
	 * @param limitBean
	 * @return
	 */
	public void inserActionLog(ActionBean actionBean){
		SqlSession session = getSession();
		
		try{
			IActionMapper mapper = session.getMapper(IActionMapper.class);
			mapper.inserActionLog(actionBean);
			session.commit();
		}catch(Exception e){
			logger.warn("insertLimitBean"+e.toString());
		}finally{
			session.close();
		}
	
	}
}
