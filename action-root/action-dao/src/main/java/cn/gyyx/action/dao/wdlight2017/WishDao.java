package cn.gyyx.action.dao.wdlight2017;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdlight2017.LightOaBean;
import cn.gyyx.action.beans.wdlight2017.ValidWishBean;
import cn.gyyx.action.beans.wdlight2017.WishBean;
import cn.gyyx.action.beans.wdlight2017.WishBeanExample;
import cn.gyyx.action.beans.wdlight2017.WishBeanExample.Criteria;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WishDao {

	private static final GYYXLogger LOG = GYYXLoggerFactory.getLogger(WishDao.class);

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 通过层级获取许愿内容
	 *
	 */
	public List<WishBean> getWishsBylevel(int level) {
		try (SqlSession sqlSession = getSession()) {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			// 20-前多少条 2-status
			List<WishBean> list = mapper.findWishByLevel(20, 2, level);
			if (list != null && list.size() > 0) {
				return list;
			}
			return null;
		}
	}

	/**
	 * 根据层级获取用户在该层第几次抽奖
	 *
	 */
	public int getUserWishNumByLevel(int userId, int level) {
		try (SqlSession sqlSession = getSession()) {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			WishBeanExample example = new WishBeanExample();
			// 查询筛选
			Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(userId);
			criteria.andLevelEqualTo(level);

			int result = mapper.countByExample(example);
			return result;
		}
	}

	/**
	 * 添加一条用户愿望
	 *
	 */
	public void addWishBean(WishBean wishBean, SqlSession sqlSession) {
		WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
		mapper.insertSelective(wishBean);
	}

	/**
	 * 获取当前用户的所有愿望
	 *
	 */
	public List<WishBean> getMyWishAll(int userId) {
		try (SqlSession sqlSession = getSession()) {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			WishBeanExample example = new WishBeanExample();
			Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(userId);
			List<WishBean> list = mapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				return list;
			}
			return null;
		}
	}

	/**
	 * 获取不同状态的许愿记录
	 * 
	 * @param pageNum
	 * @param page
	 * @param status
	 * @return
	 */
	public List<ValidWishBean> getValidWishBean(int pageNum, int page, int status) {
		List<ValidWishBean> result = null;
		SqlSession session = getSession();
		try {
			WishBeanMapper mapper = session.getMapper(WishBeanMapper.class);
			result = mapper.getValidWishBean(pageNum, page, status);
			session.commit();
		} catch (Exception e) {
			LOG.error("获取分页异常:{},{},{}", new Object[] { e.getMessage(), e.getCause(), e.getStackTrace() });
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 修改许愿状态
	 * 
	 * @param status
	 * @param code
	 * @return
	 */
	public int modifyWishStatusByCode(int status, int code) {
		int result = 0;
		SqlSession session = getSession();
		try {
			WishBeanMapper mapper = session.getMapper(WishBeanMapper.class);
			result = mapper.modifyWishStatusByCode(status, code);
			session.commit();
		} catch (Exception e) {
			LOG.error("获取分页异常:{},{},{}", new Object[] { e.getMessage(), e.getCause(), e.getStackTrace() });
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 获取许愿总数
	 * 
	 * @param status
	 * @return
	 */
	public int getWishCount(int status) {
		int result = 0;
		try (SqlSession sqlSession = getSession()) {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			result = mapper.getWishCount(status);
		} catch (Exception e) {
			LOG.error("获取许愿总数异常:{},{},{}", new Object[] { e.getMessage(), e.getCause(), e.getStackTrace() });
		}
		return result;

	}

	/**
	 * 获取用户的所有愿望
	 *
	 */
	public List<WishBean> findWishAll(int num) {
		try (SqlSession sqlSession = getSession()) {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			List<WishBean> list = mapper.findWishAll(num, 2);
			if (list != null && list.size() > 0) {
				return list;
			}
			return null;
		}
	}

	/**
	 * 修改点亮许愿人数
	 * 
	 * @param status
	 * @param code
	 * @return
	 */
	public int modifyLightLimitNum(int limitNum, int level) {
		int result = 0;
		SqlSession session = getSession();
		try {
			WishBeanMapper mapper = session.getMapper(WishBeanMapper.class);
			result = mapper.modifyLightLimitNum(limitNum, level);
			session.commit();
		} catch (Exception e) {
			LOG.error("修改点亮许愿人数异常:{},{},{}", new Object[] { e.getMessage(), e.getCause(), e.getStackTrace() });
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 获取所有层数
	 *
	 */
	public List<LightOaBean> getLightLevel() {
		List<LightOaBean> list = null;
		SqlSession sqlSession = getSession();
		try {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			list = mapper.getLightLevel();
		} catch (Exception e) {
			LOG.error("获取所有层数异常:{},{},{}", new Object[] { e.getMessage(), e.getCause(), e.getStackTrace() });
		} finally {
			sqlSession.close();
		}
		return list;
	}
	
	/**
	 * 获取每层人数异常
	 * 
	 * @param status
	 * @param code
	 * @return
	 */
	public Map<Integer, Integer> getLightPeople() {
		Map<Integer, Integer> result=null ;
		SqlSession session = getSession();
		try {
			WishBeanMapper mapper = session.getMapper(WishBeanMapper.class);
			result = mapper.getLightPeople();
			session.commit();
		} catch (Exception e) {
			LOG.error("获取每层许愿人数人数异常:{},{},{}", new Object[] { e.getMessage(), e.getCause(), e.getStackTrace() });
		} finally {
			session.close();
		}
		return result;
	}
	/**
	 * 获取许愿总数
	 * @param status
	 * @return
	 */
	public int getWishNumByLevel(int level) {
		try (SqlSession sqlSession = getSession()) {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			WishBeanExample example = new WishBeanExample();
			Criteria criteria = example.createCriteria();
			criteria.andLevelEqualTo(level);
			criteria.andStatusEqualTo(2);
			int result = mapper.countByExample(example);
			return result;
		}
	}
	
	/**
	 * 获取许愿总数
	 * @param status
	 * @return
	 */
	public int getWishCountByStatus(int status){
		try (SqlSession sqlSession = getSession()) {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			WishBeanExample example = new WishBeanExample();
			int result = mapper.countByExample(example);
			return result;
		}
		
	}
	
	/**
	 * 获取许愿人数 通过层级
	 * @param status
	 * @return
	 */
	public int getWishUserCountByLevel(int level) {
		try (SqlSession sqlSession = getSession()) {
			WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
			int result = mapper.getWishUserCountByLevel(level);
			return result;
		}
	}
	
	/**
	 * 获取许愿人数 通过层级
	 * @param status
	 * @return
	 */
	public int getWishUserCountByLevel(int level, SqlSession sqlSession) {
		WishBeanMapper mapper = sqlSession.getMapper(WishBeanMapper.class);
		int result = mapper.getWishUserCountByLevel(level);
		return result;
	}
	
}
