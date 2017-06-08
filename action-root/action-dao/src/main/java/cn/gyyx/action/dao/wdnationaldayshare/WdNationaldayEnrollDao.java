package cn.gyyx.action.dao.wdnationaldayshare;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdnationalday.WdNationaldayEnrollBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年9月25日 下午3:40:28
 * 描        述：
 */
public class WdNationaldayEnrollDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	public WdNationaldayEnrollBean getEnrollInfoByAccount(String account) {
		try (SqlSession sqlSession = getSession()) {
			IWdNationaldayEnrollMapper mapper = sqlSession
					.getMapper(IWdNationaldayEnrollMapper.class);
			return mapper.getEnrollInfoByAccount(account);
		}
	}
	
	public int insert(WdNationaldayEnrollBean bean) {
		int res = 0;
		try (SqlSession sqlSession = getSession()) {
			IWdNationaldayEnrollMapper mapper = sqlSession
					.getMapper(IWdNationaldayEnrollMapper.class);
			
			res = mapper.insert(bean);
			sqlSession.commit();
		}
		return res;
	}
	
	/**
	 * 为指定的账号，增加相应积分
	 */
	public int increaseScore(String account,int increaseScore,SqlSession session) {
		IWdNationaldayEnrollMapper mapper = session
					.getMapper(IWdNationaldayEnrollMapper.class);
		int res =  mapper.increaseScore(account,increaseScore);
		return res;
	}

	/**
	 * 根据opendId查询数据库
	 */
	public WdNationaldayEnrollBean getEnrollInfoFromDatabaseByOpenId(
			String openId) {
		WdNationaldayEnrollBean res = null;
		try (SqlSession sqlSession = getSession()) {
			IWdNationaldayEnrollMapper mapper = sqlSession
					.getMapper(IWdNationaldayEnrollMapper.class);
			
			res = mapper.getEnrollInfoFromDatabaseByOpenId(openId);
		}
		return res;
	}
	

}
