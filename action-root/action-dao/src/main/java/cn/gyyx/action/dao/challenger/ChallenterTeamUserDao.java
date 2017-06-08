/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午5:52:04
 * 版本号：v1.0
 * 本类主要用途叙述：用户报名信息的数据库接口
 */

package cn.gyyx.action.dao.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.challenger.ChallenterTeamUserBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ChallenterTeamUserDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public void insertTeamAndUser(SqlSession session, ChallenterTeamUserBean ub) {
		IChallenterTeamUser iChallenterTeamUser = session
				.getMapper(IChallenterTeamUser.class);
		iChallenterTeamUser.insert(ub);
	}

	public List<ChallenterTeamUserBean> getTeamUserBearListByTeamIds(
			List<Integer> teamIds) {
		try (SqlSession session = getSession()) {
			IChallenterTeamUser iChallenterTeamUser = session
					.getMapper(IChallenterTeamUser.class);
			return iChallenterTeamUser.getTeamUserBearListByTeamIds(teamIds);
		}
	}

	
}
