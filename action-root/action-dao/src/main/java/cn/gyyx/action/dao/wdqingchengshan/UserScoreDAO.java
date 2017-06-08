/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.dao.wdqingchengshan;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.jswswxsign.UserScore;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/** 
 * 用户积分
 */
public class UserScoreDAO {
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public UserScore getUserScore(UserScore userScore) {
		UserScore result = null;
		SqlSession session = getSession();
		try {
			result = getUserScore(userScore,session);
		}finally{
			session.close();
		}
		return result;
	}

	public int insertUserScore(UserScore userScore) {
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
	
	public int insertUserScore(UserScore userScore,SqlSession session) {
		int result = 0;
		IUserScoreMapper mapper = session.getMapper(IUserScoreMapper.class);
		result = mapper.insertUserScore(userScore);
		return result;
	}

	public int updateUserScore(UserScore userScore) {
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
	
	public int updateUserScore(UserScore userScore,SqlSession session) {
		int result = 0;
		IUserScoreMapper mapper = session.getMapper(IUserScoreMapper.class);
		result = mapper.updateUserScore(userScore);
		return result;
	}

	public UserScore getUserScore(UserScore userScore, SqlSession session) {
		UserScore result = null;
		IUserScoreMapper mapper = session.getMapper(IUserScoreMapper.class);
		result = mapper.getUserScore(userScore);
		return result;
	}

	
	

}
