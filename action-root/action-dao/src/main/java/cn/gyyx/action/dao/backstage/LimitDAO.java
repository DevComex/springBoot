package cn.gyyx.action.dao.backstage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.backstage.LimitBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LimitDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LimitDAO.class);

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
	public Integer insertLimitBean(LimitBean limitBean){
		SqlSession session = getSession();
		Integer result = 0;
		try{
			ILimitMapper mapper = session.getMapper(ILimitMapper.class);
			result = mapper.insertLimitBean(limitBean);
			session.commit();
		}catch(Exception e){
			logger.warn("insertLimitBean"+e.toString());
		}finally{
			session.close();
		}
		return result; 
	}
	/**
	 * 编辑权限
	 * @param limitBean
	 * @return
	 */
	public void updateLimitBean(LimitBean limitBean){
		SqlSession session = getSession();
		try{
			ILimitMapper mapper = session.getMapper(ILimitMapper.class);
			mapper.updateLimitBean(limitBean);
			session.commit();
		}catch(Exception e){
			logger.warn("updateLimitBean"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * 删除权限
	 * @param limitBean
	 * @return
	 */
	public void deleteLimitBean(int code){
		SqlSession session = getSession();
		try{
			ILimitMapper mapper = session.getMapper(ILimitMapper.class);
			mapper.deleteLimitBean(code);
			session.commit();
		}catch(Exception e){
			logger.warn("deleteLimitBean"+e.toString());
		}finally{
			session.close();
		}
	}
	/**
	 * 权限列表
	 * @param limitBean
	 * @return
	 */
	public List<LimitBean> selectLimitBeanAll(NewPageBean newPageBean){
		SqlSession session = getSession();
		List<LimitBean> result = null;
		try{
			ILimitMapper mapper = session.getMapper(ILimitMapper.class);
			result = mapper.selectLimitBeanAll(newPageBean);
		}catch(Exception e){
			logger.warn("selectLimitBeanAll"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
	public Integer selectLimitBeanAllCount(){
		SqlSession session = getSession();
		Integer result = null;
		try{
			ILimitMapper mapper = session.getMapper(ILimitMapper.class);
			result = mapper.selectLimitBeanAllCount();
		}catch(Exception e){
			logger.warn("selectLimitBeanAll"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
	/**
	 * 查询用户具有权限的活动
	 * @param limitBean
	 * @return
	 */
	public List<LimitBean> selectLimitBeanByUser(NewPageBean newPageBean){
		SqlSession session = getSession();
		List<LimitBean> result = null;
		try{
			ILimitMapper mapper = session.getMapper(ILimitMapper.class);
			result = mapper.selectLimitBeanByUser(newPageBean);
		}catch(Exception e){
			logger.warn("selectLimitBeanByUser"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
	public Integer selectLimitBeanByUserCount(int personId){
		SqlSession session = getSession();
		Integer result = null;
		try{
			ILimitMapper mapper = session.getMapper(ILimitMapper.class);
			result = mapper.selectLimitBeanByUserCount(personId);
		}catch(Exception e){
			logger.warn("selectLimitBeanByUserCount"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
	public LimitBean selectLimitBeanByCode(int code){
		SqlSession session = getSession();
		LimitBean result = null;
		try{
			ILimitMapper mapper = session.getMapper(ILimitMapper.class);
			result = mapper.selectLimitBeanByCode(code);
		}catch(Exception e){
			logger.warn("selectLimitBeanAll"+e.toString());
		}finally{
			session.close();
		}
		return result;
	}
	
}
