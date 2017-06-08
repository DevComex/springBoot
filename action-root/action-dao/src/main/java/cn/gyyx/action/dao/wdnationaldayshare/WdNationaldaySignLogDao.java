package cn.gyyx.action.dao.wdnationaldayshare;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdnationalday.WdNationaldaySignLogBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月25日 下午3:40:28
 * 描        述：
 */
public class WdNationaldaySignLogDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public WdNationaldaySignLogBean getTodaySignLog(String account,String today) {
		try (SqlSession sqlSession = getSession()) {
			IWdNationaldaySignLogMapper mapper = sqlSession
					.getMapper(IWdNationaldaySignLogMapper.class);
			return mapper.getTodaySignLog(account,today);
		}
	}
	
	public int insert(WdNationaldaySignLogBean bean,SqlSession session) {
		int res = 0;
		IWdNationaldaySignLogMapper mapper = session
				.getMapper(IWdNationaldaySignLogMapper.class);
		res = mapper.insert(bean);
		return res;
	}
	
	public List<String> getSignHistoryDate(String account) {
		try (SqlSession sqlSession = getSession()) {
			IWdNationaldaySignLogMapper mapper = sqlSession
					.getMapper(IWdNationaldaySignLogMapper.class);
			return mapper.getSignHistoryDate(account);
		}
	}

	public int getSignCountByAccount(String account, SqlSession session) {
		int res = 0;
		IWdNationaldaySignLogMapper mapper = session
				.getMapper(IWdNationaldaySignLogMapper.class);
		res = mapper.getSignCountByAccount(account);
		return res;
	}
	
	public int getSignCountByAccount(String account) {
		try (SqlSession sqlSession = getSession()) {
			IWdNationaldaySignLogMapper mapper = sqlSession
					.getMapper(IWdNationaldaySignLogMapper.class);
			return mapper.getSignCountByAccount(account);
		}
	}
	

}
