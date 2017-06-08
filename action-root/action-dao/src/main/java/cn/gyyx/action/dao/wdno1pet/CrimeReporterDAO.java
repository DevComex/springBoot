/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午2:11:07
 * 版本号：v1.0
 * 本类主要用途叙述：关于举报DAO层对数据库的操作
 */

package cn.gyyx.action.dao.wdno1pet;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdno1pet.CrimeReporterBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class CrimeReporterDAO implements ICrimeReporter {

	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 插入举报信息的DAO实现
	 */
	public void insertTipster(CrimeReporterBean crime) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			ICrimeReporter iCrimeReporter = sqlSession
					.getMapper(ICrimeReporter.class);
			iCrimeReporter.insertTipster(crime);
			sqlSession.commit();
		}

	}

}
