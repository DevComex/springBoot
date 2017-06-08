package cn.gyyx.action.dao.backstage;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.backstage.LimitPeopleBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LimitPeopleDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LimitPeopleDAO.class);

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
	 * 添加某活动下的人员
	 * @param LimitPeopleBean
	 * @return
	 */
	public void insertByActionCode(LimitPeopleBean limitPeopleBean){
		SqlSession session = getSession();
		try{
			ILimitPeopleMapper mapper = session.getMapper(ILimitPeopleMapper.class);
			mapper.insertByActionCode(limitPeopleBean);
			session.commit();
		}catch(Exception e){
			logger.warn("insertLimitBean"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * 删除某活动下的人员
	 * @param LimitPeopleBean
	 * @return
	 */
	public void deleteLimitPeopleBean(@Param("limitCode") int limitCode){
		SqlSession session = getSession();
		try{
			ILimitPeopleMapper mapper = session.getMapper(ILimitPeopleMapper.class);
			mapper.deleteLimitPeopleBean(limitCode);
			session.commit();
		}catch(Exception e){
			logger.warn("insertLimitBean"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * 查询某活动下的人员
	 * @param LimitPeopleBean
	 * @return
	 */
	public List<LimitPeopleBean> selectByActionCode(int limitCode){
		SqlSession session = getSession();
		List<LimitPeopleBean> result = null;
		try{
			ILimitPeopleMapper mapper = session.getMapper(ILimitPeopleMapper.class);
			result = mapper.selectByActionCode(limitCode);
		}catch(Exception e){
			logger.warn("insertLimitBean"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
}
