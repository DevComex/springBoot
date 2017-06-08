/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：V1.214
 * @本类主要用途描述：首页素材展示记录数据访问层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.xwbcreditsluckydraw.ShowTopBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ShowTopDAO {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ShowTopDAO.class);

	/**
	 * 获取session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 增加一条记录
	 * @author fanyongliang
	 * @param showTopBean
	 */
	public void addOne(ShowTopBean ShowTopBean) {
		SqlSession session = getSession();
		try {
			IShowTopMapper mapper = session.getMapper(IShowTopMapper.class);
			mapper.addOne(ShowTopBean);
			session.commit();
		} catch (Exception e) {
			logger.warn("addOne" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 根据审核code删除记录
	 * @author fanyongliang
	 * @param auditCode
	 */
	public void deleteByAuditCode(Integer auditCode) {
		SqlSession session = getSession();
		try {
			IShowTopMapper mapper = session.getMapper(IShowTopMapper.class);
			mapper.deleteByAuditCode(auditCode);
			session.commit();
		} catch (Exception e) {
			logger.warn("deleteByAuditCode" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 清空记录的数据
	 * @author fanyongliang
	 */
	public void deleteAll() {
		SqlSession session = getSession();
		try {
			IShowTopMapper mapper = session.getMapper(IShowTopMapper.class);
			mapper.deleteAll();
			session.commit();
		} catch (Exception e) {
			logger.warn("deleteAll" + e.toString());
		} finally {
			session.close();
		}
	}

	/**
	 * 根据素材类型查询记录数量
	 * @author fanyongliang
	 */
	public Integer getCountByMaterialType(String materialType) {
		SqlSession session = getSession();
		Integer count = 0;
		try {
			IShowTopMapper mapper = session.getMapper(IShowTopMapper.class);
			count = mapper.getCountByMaterialType(materialType);
		} catch (Exception e) {
			logger.warn("getCountByMaterialType" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}

	/**
	 * 根据审核编号查询记录数量
	 * @author fanyongliang
	 */
	public Integer getCountByAuditCode(Integer auditCode) {
		SqlSession session = getSession();
		Integer count = 0;
		try {
			IShowTopMapper mapper = session.getMapper(IShowTopMapper.class);
			count = mapper.getCountByAuditCode(auditCode);
		} catch (Exception e) {
			logger.warn("getCountByAuditCode" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}
	
	/**
	 * 查询总记录数
	 * @author fanyongliang
	 */
	public Integer getCountAll() {
		SqlSession session = getSession();
		Integer count = 0;
		try {
			IShowTopMapper mapper = session.getMapper(IShowTopMapper.class);
			count = mapper.getCountAll();
		} catch (Exception e) {
			logger.warn("getCountAll" + e.toString());
		} finally {
			session.close();
		}
		return count;
	}

	/**
	 * 查询所有记录
	 * @author fanyongliang
	 */
	public List<ShowTopBean> getAllInfo() {
		SqlSession session = getSession();
		List<ShowTopBean> list = new ArrayList<ShowTopBean>();
		try {
			IShowTopMapper mapper = session.getMapper(IShowTopMapper.class);
			list = mapper.getAllInfo();
		} catch (Exception e) {
			logger.warn("getAllInfo" + e.toString());
		} finally {
			session.close();
		}
		return list;
	}
}
