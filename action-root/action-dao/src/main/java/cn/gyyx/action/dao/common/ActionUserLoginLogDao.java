package cn.gyyx.action.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.common.ActionUserLoginLog;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年11月28日 下午7:10:29
 * 描        述：
 */
public class ActionUserLoginLogDao {

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public void insert(ActionUserLoginLog bean) {
		try (SqlSession sqlSession = getSession()) {
			IActionUserLoginLogMapper mapper = sqlSession.getMapper(IActionUserLoginLogMapper.class);
			mapper.insert(bean);
			sqlSession.commit();
		}
	}

	public ActionUserLoginLog getBeanByLoginId(String loginId) {
		try (SqlSession sqlSession = getSession()) {
			IActionUserLoginLogMapper mapper = sqlSession.getMapper(IActionUserLoginLogMapper.class);
			return mapper.getBeanByLoginId(loginId);
		}
	}

	public List<ActionUserLoginLog> getListPaging(ActionUserLoginLog bean) {
		try (SqlSession sqlSession = getSession()) {
			IActionUserLoginLogMapper mapper = sqlSession.getMapper(IActionUserLoginLogMapper.class);
			return mapper.getListPaging(bean);
		}
	}

	public int getListPagingCount(ActionUserLoginLog bean) {
		try (SqlSession sqlSession = getSession()) {
			IActionUserLoginLogMapper mapper = sqlSession.getMapper(IActionUserLoginLogMapper.class);
			return mapper.getListPagingCount(bean);
		}
	}

	public List<Map<String, String>> getLoginCountGroupByDate(int activityCode) {
		try (SqlSession sqlSession = getSession()) {
			IActionUserLoginLogMapper mapper = sqlSession.getMapper(IActionUserLoginLogMapper.class);
			return mapper.getLoginCountGroupByDate(activityCode);
		}
	}

}
