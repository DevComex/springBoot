/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.xlsgwxsign;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.xlsgwxsign.XlsgUserScore;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午8:08:14
 */
public class XlsgUserScoreDAO {
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public XlsgUserScore getUserScore(XlsgUserScore userScore) {
		XlsgUserScore result = null;
		SqlSession session = getSession();
		try {
			result = getUserScore(userScore,session);
		}finally{
			session.close();
		}
		return result;
	}

	public int insertUserScore(XlsgUserScore userScore) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = insertUserScore(userScore,session);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}
	
	public int insertUserScore(XlsgUserScore userScore,SqlSession session) {
		int result = 0;
		IXlsgUserScoreMapper mapper = session.getMapper(IXlsgUserScoreMapper.class);
		result = mapper.insertUserScore(userScore);
		return result;
	}

	public int updateUserScore(XlsgUserScore userScore) {
		int result = 0;
		SqlSession session = getSession();
		try {
			result = updateUserScore(userScore,session);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}
	
	public int updateUserScore(XlsgUserScore userScore,SqlSession session) {
		int result = 0;
		IXlsgUserScoreMapper mapper = session.getMapper(IXlsgUserScoreMapper.class);
		result = mapper.updateUserScore(userScore);
		return result;
	}

	public XlsgUserScore getUserScore(XlsgUserScore userScore, SqlSession session) {
		XlsgUserScore result = null;
		IXlsgUserScoreMapper mapper = session.getMapper(IXlsgUserScoreMapper.class);
		result = mapper.getUserScore(userScore);
		return result;
	}

	public void updateUserScoreZero() {
		SqlSession session = getSession();
		try {
			updateUserScoreZero(session);
			session.commit();
		} finally {
			session.close();
		}
	}
	
	public void updateUserScoreZero(SqlSession session) {
		IXlsgUserScoreMapper mapper = session.getMapper(IXlsgUserScoreMapper.class);
		mapper.updateUserScoreZero();
	}

}
