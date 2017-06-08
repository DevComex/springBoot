package cn.gyyx.action.dao.wechat;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.wechat.ConfigBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ConfigDao {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfigDao.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 获取所有的后台统计配置信息
	 * @return
	 */
	public List<ConfigBean> getWechatConfig() {
		SqlSession session = getSession();
		List<ConfigBean> WeChatConfigList = null;
		try {
			IConfigMapper mapper = session
					.getMapper(IConfigMapper.class);
			WeChatConfigList = mapper.getWechatConfig();
		} catch (Exception e) {
			logger.warn("getWechatConfig" + e.toString());
		} finally {
			session.close();
		}
		return WeChatConfigList;
	}
	
	/**
	 * 获取最新的统计编号
	 */
	public Integer getMaxCodeFromConfig(){
		SqlSession session = getSession();
		Integer num = 0;
		try {
			IConfigMapper mapper = session
					.getMapper(IConfigMapper.class);
			num = mapper.getMaxCodeFromConfig();
		} catch (Exception e) {
			logger.warn("getMaxCodeFromConfig" + e.toString());
		} finally {
			session.close();
		}
		return num;
	}
	
	/**
	 * 根据配置名称查询统计配置
	 * @param configName
	 * @return
	 */
	public Integer getWechatConfigByConfigName(String configName){
		SqlSession session = getSession();
		Integer num = 0;
		try {
			IConfigMapper mapper = session
					.getMapper(IConfigMapper.class);
			num = mapper.getWechatConfigByConfigName(configName);
		} catch (Exception e) {
			logger.warn("getWechatConfigByConfigName" + e.toString());
		} finally {
			session.close();
		}
		return num;
	}
	/**
	 * 插入统计配置信息
	 * @param weChatConfigBean
	 */
	public void addWechatConfig(ConfigBean weChatConfigBean){
		SqlSession session = getSession();
		try {
			IConfigMapper mapper = session
					.getMapper(IConfigMapper.class);
			mapper.addWechatConfig(weChatConfigBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("addWechatConfig" + e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 根据code获取统计配置信息
	 * @param code
	 * @return
	 */
	public ConfigBean getWechatConfigInfoByCode(Integer code){
		SqlSession session = getSession();
		ConfigBean WeChatConfig = null;
		try {
			IConfigMapper mapper = session
					.getMapper(IConfigMapper.class);
			WeChatConfig = mapper.getWechatConfigInfoByCode(code);
		} catch (Exception e) {
			logger.warn("getWechatConfigInfoByCode" + e.toString());
		} finally {
			session.close();
		}
		return WeChatConfig;
	}
	/**
	 * 修改统计配置信息
	 * @param weChatConfigBean
	 */
	public void updateWechatConfig(ConfigBean weChatConfigBean){
		SqlSession session = getSession();
		try {
			IConfigMapper mapper = session
					.getMapper(IConfigMapper.class);
			mapper.updateWechatConfig(weChatConfigBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateWechatConfig" + e.toString());
		} finally {
			session.close();
		}
	}
	/**
	 * 修改统计开启状态
	 * @param isOpen
	 */
	public void updateConfigIsOpen(ConfigBean weChatConfigBean){
		SqlSession session = getSession();
		try {
			IConfigMapper mapper = session
					.getMapper(IConfigMapper.class);
			mapper.updateConfigIsOpen(weChatConfigBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateConfigIsOpen" + e.toString());
		} finally {
			session.close();
		}
	}
}
