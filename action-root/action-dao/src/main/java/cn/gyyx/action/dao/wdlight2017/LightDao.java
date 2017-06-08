package cn.gyyx.action.dao.wdlight2017;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdlight2017.LightBean;
import cn.gyyx.action.beans.wdlight2017.LightBeanExample;
import cn.gyyx.action.beans.wdlight2017.LightBeanExample.Criteria;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class LightDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * 获取所有灯的信息
	 *
	 */
	public List<LightBean> getLightALl() {
		try (SqlSession sqlSession = getSession()) {
			LightBeanMapper mapper = sqlSession.getMapper(LightBeanMapper.class);
			LightBeanExample example = new LightBeanExample();
			List<LightBean> list = mapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				return list;
			}
			return  null;
		}
	}
	
	/**
	 * 获取所有灯的信息
	 *
	 */
	public List<LightBean> getLightALl(SqlSession sqlSession) {
		LightBeanMapper mapper = sqlSession.getMapper(LightBeanMapper.class);
		LightBeanExample example = new LightBeanExample();
		List<LightBean> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list;
		}
		return  null;
	}
	
	/**
	 * 获取所有灯的信息
	 *
	 */
	public LightBean getLightByLevel(int level, SqlSession sqlSession) {
		LightBeanMapper mapper = sqlSession.getMapper(LightBeanMapper.class);
		LightBeanExample example = new LightBeanExample();
		Criteria criteria = example.createCriteria();
		criteria.andLevelEqualTo(level);
		List<LightBean> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 修改灯的数据
	 *
	 */
	public void updateLight(LightBean lightBean, SqlSession sqlSession) {
		LightBeanMapper mapper = sqlSession.getMapper(LightBeanMapper.class);
		mapper.updateByPrimaryKeySelective(lightBean);
	}
	
	/**
	 * 修改灯的数据
	 *
	 */
	public void updateLight(LightBean lightBean) {
		try (SqlSession sqlSession = getSession()) {
			LightBeanMapper mapper = sqlSession.getMapper(LightBeanMapper.class);
			mapper.updateByPrimaryKeySelective(lightBean);
			sqlSession.commit();
		}
	}
	
}
