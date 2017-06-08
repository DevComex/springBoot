package cn.gyyx.action.dao.wechat;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.wechat.ConfigBean;
import cn.gyyx.action.beans.wechat.CustomBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class CustomDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomDao.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 获取操作的所有自定义参数
	 * @return
	 */
	public List<CustomBean> getAllCustomInfoByOperate(Integer operateCode) {
		SqlSession session = getSession();
		List<CustomBean> customBeanList = null;
		try {
			ICustomMapper mapper = session
					.getMapper(ICustomMapper.class);
			customBeanList = mapper.getAllCustomInfoByOperate(operateCode);
		} catch (Exception e) {
			logger.warn("getAllCustomInfoByOperate" + e.toString());
		} finally {
			session.close();
		}
		return customBeanList;
	}
	
	/**
	 * 获取统计的所有自定义参数
	 * @return
	 */
	public List<CustomBean> getAllCustomInfoByConfigCode(Integer configCode) {
		SqlSession session = getSession();
		List<CustomBean> customBeanList = null;
		try {
			ICustomMapper mapper = session
					.getMapper(ICustomMapper.class);
			customBeanList = mapper.getAllCustomInfoByConfigCode(configCode);
		} catch (Exception e) {
			logger.warn("getAllCustomInfoByConfigCode" + e.toString());
		} finally {
			session.close();
		}
		return customBeanList;
	}
	
	/**
	 * 获取自定义参数
	 * @return
	 */
	public CustomBean getCustomInfoByCode(Integer code){
		SqlSession session = getSession();
		CustomBean customBean = new CustomBean();
		try {
			ICustomMapper mapper = session
					.getMapper(ICustomMapper.class);
			customBean = mapper.getCustomInfoByCode(code);
		} catch (Exception e) {
			logger.warn("getCustomInfoByCode" + e.toString());
		} finally {
			session.close();
		}
		return customBean;
	}
	
	/**
	 * 根据自定义参数获取数量
	 * @param customType
	 * @return
	 */
	public Integer getCustomCountByCustomType(String customType){
		SqlSession session = getSession();
		Integer count = 0;
		try {
			ICustomMapper mapper = session
					.getMapper(ICustomMapper.class);
			count = mapper.getCustomCountByCustomType(customType);
		} catch (Exception e) {
			logger.warn("getCustomCountByCustomType" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	
	/**
	 * 增加参数
	 * @param customBean
	 */
	public void addCustomInfo(CustomBean customBean){
		SqlSession session = getSession();
		try {
			ICustomMapper mapper = session
					.getMapper(ICustomMapper.class);
			mapper.addCustomInfo(customBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("addCustomInfo" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 修改参数
	 * @param customBean
	 */
	public void updateCustomInfo(CustomBean customBean){
		SqlSession session = getSession();
		try {
			ICustomMapper mapper = session
					.getMapper(ICustomMapper.class);
			mapper.updateCustomInfo(customBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateCustomInfo" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 删除参数
	 * @param code
	 */
	public void deleteCustomInfo(Integer code){
		SqlSession session = getSession();
		try {
			ICustomMapper mapper = session
					.getMapper(ICustomMapper.class);
			mapper.deleteCustomInfo(code);
			session.commit();
		} catch (Exception e) {
			logger.warn("deleteCustomInfo" + e.toString());
		} finally {
			session.close();
		}
	}
}
