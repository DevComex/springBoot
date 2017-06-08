/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午5:52:04
 * 版本号：v1.0
 * 本类主要用途叙述：用户报名信息的数据库接口
 */

package cn.gyyx.action.dao.wd10yearcoser;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wd10yearcoser.CoserNovice;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class CoserNoviceDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public List<CoserNovice> getNoviceListPaging(CoserNovice bean) {
		try (SqlSession session = getSession()) {
			ICoserNovice iCoserNovice = session
					.getMapper(ICoserNovice.class);
			return iCoserNovice.getNoviceListPaging(bean);
		} 
	}
	
	public int insert(CoserNovice bean, SqlSession session) {
		ICoserNovice iCoserNovice = session
				.getMapper(ICoserNovice.class);
		return iCoserNovice.insert(bean);
	}
	
	public int update(CoserNovice bean, SqlSession session) {
		ICoserNovice iCoserNovice = session
				.getMapper(ICoserNovice.class);
		return iCoserNovice.update(bean);
	}
	
	public int delete(CoserNovice bean, SqlSession session) {
		ICoserNovice iCoserNovice = session
				.getMapper(ICoserNovice.class);
		return iCoserNovice.delete(bean);
	}
	
	public int getNoviceCount(CoserNovice bean) {
		try (SqlSession session = getSession()) {
			ICoserNovice iCoserNovice = session
					.getMapper(ICoserNovice.class);
			return iCoserNovice.getNoviceCount(bean);
		} 
	}
	
	public CoserNovice getCoserNovice(int code) {
		try (SqlSession session = getSession()) {
			ICoserNovice iCoserNovice = session
					.getMapper(ICoserNovice.class);
			return iCoserNovice.getCoserNovice(code);
		} 
	}

	public List<CoserNovice> getNoviceFontListPaging(CoserNovice bean) {
		try (SqlSession session = getSession()) {
			ICoserNovice iCoserNovice = session
					.getMapper(ICoserNovice.class);
			return iCoserNovice.getNoviceFontListPaging(bean);
		} 
	}
	
	public CoserNovice getNoviceNew() {
		try (SqlSession session = getSession()) {
			ICoserNovice iCoserNovice = session
					.getMapper(ICoserNovice.class);
			return iCoserNovice.getNoviceNew();
		} 
	}
	

	

}
