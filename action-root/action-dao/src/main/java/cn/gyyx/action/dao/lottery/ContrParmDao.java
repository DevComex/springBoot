/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午1:25:31
 * 版本号：v1.0
 * 本类主要用途叙述：活动参数的Dao
 */



package cn.gyyx.action.dao.lottery;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ContrParmDao implements IConPram {
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

	@Override
	/**
	 * 得到活动信息
	 */
	public ContrParmBean getConPram(
			int actionCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IConPram iConPram =sqlSession.getMapper(IConPram.class);
			return  iConPram.getConPram(actionCode);
			
		}
	}
	

}
