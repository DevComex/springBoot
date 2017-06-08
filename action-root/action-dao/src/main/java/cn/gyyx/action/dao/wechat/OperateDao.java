package cn.gyyx.action.dao.wechat;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.beans.wechat.OperateBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class OperateDao {

	private static final Logger logger = LoggerFactory
			.getLogger(OperateDao.class);

	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 获取所有的操作信息
	 * @return
	 */
	public List<OperateBean> getOperateInfoByConfigCode(Integer configCode) {
		SqlSession session = getSession();
		List<OperateBean> WeChatConfigList = null;
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			WeChatConfigList = mapper.getOperateInfoByConfigCode(configCode);
		} catch (Exception e) {
			logger.warn("getOperateInfoByConfigCode" + e.toString());
		} finally {
			session.close();
		}
		return WeChatConfigList;
	}
	
	/**
	 * 获取所有参与统计的操作信息
	 * @return
	 */
	public List<OperateBean> getOperateInfoByConfigCodeNoCount(Integer configCode) {
		SqlSession session = getSession();
		List<OperateBean> WeChatConfigList = null;
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			WeChatConfigList = mapper.getOperateInfoByConfigCodeNoCount(configCode);
		} catch (Exception e) {
			logger.warn("getOperateInfoByConfigCodeNoCount" + e.toString());
		} finally {
			session.close();
		}
		return WeChatConfigList;
	}
	
	/**
	 * 根据操作类型查询数量
	 * @param operateType
	 * @return
	 */
	public Integer getCountByOperateType(OperateBean operateBean){
		SqlSession session = getSession();
		Integer count = 0;
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			count = mapper.getCountByOperateType(operateBean);
		} catch (Exception e) {
			logger.warn("getCountByOperateType" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	
	/**
	 * 插入新操作
	 * @param operateBean
	 */
	public void addOperateInfo(OperateBean operateBean){
		SqlSession session = getSession();
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			mapper.addOperateInfo(operateBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("addOperateInfo" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 根据code查询操作
	 * @param code
	 * @return
	 */
	public OperateBean getOperateInfoByCode(Integer code){
		SqlSession session = getSession();
		OperateBean operateBean = null;
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			operateBean = mapper.getOperateInfoByCode(code);
		} catch (Exception e) {
			logger.warn("getOperateInfoByCode" + e.toString());
		} finally {
			session.close();
		}
		return operateBean;
	}
	
	/**
	 * 修改操作
	 * @param operateBean
	 */
	public void updateOperateInfo(OperateBean operateBean){
		SqlSession session = getSession();
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			mapper.updateOperateInfo(operateBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateOperateInfo" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 删除操作
	 * @param code
	 */
	public void updateDeleteFlag(Integer code){
		SqlSession session = getSession();
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			mapper.updateDeleteFlag(code);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateDeleteFlag" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 编辑参数
	 * @param operateBean
	 */
	public void updateWechatCustomPara(OperateBean operateBean){
		SqlSession session = getSession();
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			mapper.updateWechatCustomPara(operateBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateWechatCustomPara" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 修改统计状态
	 * @param operateBean
	 */
	public void updateOperateInCount(OperateBean operateBean){
		SqlSession session = getSession();
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			mapper.updateOperateInCount(operateBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateOperateInCount" + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * 修改详细统计状态
	 * @param operateBean
	 */
	public void updateOperateInDetail(OperateBean operateBean){
		SqlSession session = getSession();
		try {
			IOperateMapper mapper = session
					.getMapper(IOperateMapper.class);
			mapper.updateOperateInDetail(operateBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("updateOperateInDetail" + e.toString());
		} finally {
			session.close();
		}
	}
}
