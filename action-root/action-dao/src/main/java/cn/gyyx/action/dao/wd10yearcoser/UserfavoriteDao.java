package cn.gyyx.action.dao.wd10yearcoser;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wd10yearcoser.PageHelper;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBean;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBeanExample;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBeanExample.Criteria;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class UserfavoriteDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	//添加点赞记录
	public void addFavorite(UserfavoriteBean bean) {
		try (SqlSession sqlSession = getSession()) {
			UserfavoriteBeanMapper mapper = sqlSession.getMapper(UserfavoriteBeanMapper.class);
			mapper.insertSelective(bean);
			sqlSession.commit();
		}
	}
	
	//添加点赞记录
	public void addFavorite(UserfavoriteBean bean, SqlSession session) {
		UserfavoriteBeanMapper mapper = session.getMapper(UserfavoriteBeanMapper.class);
		mapper.insertSelective(bean);
	}

	//根据资源code获取点赞数量
	public int countFavoriteNumBySourceCode(int sourceCode, SqlSession session) {
		UserfavoriteBeanMapper mapper = session.getMapper(UserfavoriteBeanMapper.class);
		UserfavoriteBeanExample example = new UserfavoriteBeanExample();
		//添加查询条件
		Criteria criteria = example.createCriteria();
		criteria.andSourceCodeEqualTo(sourceCode);
		return mapper.countByExample(example);
	}
	
	//根据资源code获取点赞数量
	public int countFavoriteNumByResourceCode(int resourceCode, SqlSession session) {
		UserfavoriteBeanMapper mapper = session.getMapper(UserfavoriteBeanMapper.class);
		UserfavoriteBeanExample example = new UserfavoriteBeanExample();
		//添加查询条件
		Criteria criteria = example.createCriteria();
		criteria.andResourceCodeEqualTo(resourceCode);
		return mapper.countByExample(example);
	}
	
	//根据查找条件查找用户点赞记录
	public List<UserfavoriteBean> findFavoriteByUser(int userCode) {
		try (SqlSession sqlSession = getSession()) {
			UserfavoriteBeanMapper mapper = sqlSession.getMapper(UserfavoriteBeanMapper.class);
			UserfavoriteBeanExample example = new UserfavoriteBeanExample();
			//添加查询条件
			Criteria criteria = example.createCriteria();
			criteria.andUserCodeEqualTo(userCode);
			criteria.andTypeEqualTo(false);
			return mapper.selectByExample(example);
		}
	}
	
	//根据查找条件查找用户点赞记录
	public List<UserfavoriteBean> findOfficialFavoriteByUser(int userCode) {
		try (SqlSession sqlSession = getSession()) {
			UserfavoriteBeanMapper mapper = sqlSession.getMapper(UserfavoriteBeanMapper.class);
			UserfavoriteBeanExample example = new UserfavoriteBeanExample();
			//添加查询条件
			Criteria criteria = example.createCriteria();
			criteria.andUserCodeEqualTo(userCode);
			criteria.andTypeEqualTo(true);
			return mapper.selectByExample(example);
		}
	}
	
	//根据查找条件查找用户点赞记录
	public List<UserfavoriteBean> findFavoriteByResource(int resourceCode,int userId,boolean flag) {
		try (SqlSession sqlSession = getSession()) {
			UserfavoriteBeanMapper mapper = sqlSession.getMapper(UserfavoriteBeanMapper.class);
			UserfavoriteBeanExample example = new UserfavoriteBeanExample();
			//添加查询条件
			Criteria criteria = example.createCriteria();
			criteria.andResourceCodeEqualTo(resourceCode);
			criteria.andUserCodeEqualTo(userId);
			criteria.andTypeEqualTo(flag);
			return mapper.selectByExample(example);
		}
	}
}
