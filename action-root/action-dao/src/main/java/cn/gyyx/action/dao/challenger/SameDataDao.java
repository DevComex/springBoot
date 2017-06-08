/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月16日下午7:16:32
 * 版本号：v1.0
 * 本类主要用途叙述：简单数据数据库
 */

package cn.gyyx.action.dao.challenger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.challenger.SameDataBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class SameDataDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 根据类型更改数据
	 * 
	 * @param type
	 * @param content
	 */
	public void resetSameDate(String type, String content, int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			ISameData iSameData = sqlSession.getMapper(ISameData.class);
			SameDataBean sameDataBean = iSameData.getSameDataBean(type,
					actionCode);
			if (sameDataBean == null) {
				iSameData.addSameData(type, content, actionCode);
			} else {
				iSameData.setSameDate(type, content, actionCode);
			}
			sqlSession.commit(true);
		}
	}

	/***
	 * 获取这个类型的簡單数据
	 * 
	 * @param type
	 * @return
	 */
	public SameDataBean getSameDataBean(String type, int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			ISameData iSameData = sqlSession.getMapper(ISameData.class);
			return iSameData.getSameDataBean(type, actionCode);

		}
	}
	
	public void increaseSameDate(String type, int activityCode,
			double increaseScore, SqlSession session) {
		ISameData iSameData = session.getMapper(ISameData.class);
		iSameData.increaseSameDate(type, activityCode, increaseScore);
	}
}
