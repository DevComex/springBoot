package cn.gyyx.action.dao.wdlight2017;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBean;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBeanExample;
import cn.gyyx.action.beans.wdlight2017.UserinfoBean;
import cn.gyyx.action.beans.wdlight2017.UserinfoBeanExample;
import cn.gyyx.action.beans.wdlight2017.UserinfoBeanExample.Criteria;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.wd10yearcoser.UserfavoriteBeanMapper;

public class UserinfoDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	// 获取用户信息通过userId
	public UserinfoBean getUserinfoBeanByUserId(int userId) {
		try (SqlSession sqlSession = getSession()) {
			UserinfoBeanMapper mapper = sqlSession.getMapper(UserinfoBeanMapper.class);
			UserinfoBeanExample example = new UserinfoBeanExample();
			Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(userId);
			List<UserinfoBean> list = mapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return  null;
		}
	}
	
	// 获取用户信息通过roleCode
	public UserinfoBean getUserinfoBeanByRoleCode(String roleCode) {
		try (SqlSession sqlSession = getSession()) {
			UserinfoBeanMapper mapper = sqlSession.getMapper(UserinfoBeanMapper.class);
			UserinfoBeanExample example = new UserinfoBeanExample();
			Criteria criteria = example.createCriteria();
			criteria.andRoleCodeEqualTo(roleCode);
			List<UserinfoBean> list = mapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return  null;
		}
	}
	
	// 获取用户信息通过userId
	public UserinfoBean getUserinfoBeanByUserId(int userId, SqlSession sqlSession) {
		UserinfoBeanMapper mapper = sqlSession.getMapper(UserinfoBeanMapper.class);
		UserinfoBeanExample example = new UserinfoBeanExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<UserinfoBean> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return  null;
	}
	
	// 增加一条用户信息
	public void addUserinfoBean(UserinfoBean userinfoBean) {
		try (SqlSession sqlSession = getSession()) {
			UserinfoBeanMapper mapper = sqlSession.getMapper(UserinfoBeanMapper.class);
			mapper.insertSelective(userinfoBean);
			sqlSession.commit();
		}
	}
	
	// 增加一条用户信息
	public void updateUserinfoBean(UserinfoBean userinfoBean, SqlSession sqlSession) {
		UserinfoBeanMapper mapper = sqlSession.getMapper(UserinfoBeanMapper.class);
		mapper.updateByPrimaryKeySelective(userinfoBean);
	}
}
