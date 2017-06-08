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

import cn.gyyx.action.beans.challenger.ChallenterLiveRadioBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ChallenterLiveRadioDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public List<ChallenterLiveRadioBean> getChallenterLiveRadioListPaging(ChallenterLiveRadioBean bean) {
		try (SqlSession session = getSession()) {
			IChallenterLiveRadio iChallenterLiveRadio = session
					.getMapper(IChallenterLiveRadio.class);
			return iChallenterLiveRadio.getChallenterLiveRadioListPaging(bean);
		} 
	}
	
	public int insert(ChallenterLiveRadioBean bean, SqlSession session) {
		IChallenterLiveRadio iChallenterLiveRadio = session
				.getMapper(IChallenterLiveRadio.class);
		return iChallenterLiveRadio.insert(bean);
	}
	
	public int update(ChallenterLiveRadioBean bean, SqlSession session) {
		IChallenterLiveRadio iChallenterLiveRadio = session
				.getMapper(IChallenterLiveRadio.class);
		return iChallenterLiveRadio.update(bean);
	}
	
	public int delete(ChallenterLiveRadioBean bean, SqlSession session) {
		IChallenterLiveRadio iChallenterLiveRadio = session
				.getMapper(IChallenterLiveRadio.class);
		return iChallenterLiveRadio.delete(bean);
	}
	
	public int getChallenterLiveRadioCount(ChallenterLiveRadioBean bean) {
		try (SqlSession session = getSession()) {
			IChallenterLiveRadio iChallenterLiveRadio = session
					.getMapper(IChallenterLiveRadio.class);
			return iChallenterLiveRadio.getChallenterLiveRadioCount(bean);
		} 
	}
	
	public ChallenterLiveRadioBean getChallenterLiveRadioBean(int code) {
		try (SqlSession session = getSession()) {
			IChallenterLiveRadio iChallenterLiveRadio = session
					.getMapper(IChallenterLiveRadio.class);
			return iChallenterLiveRadio.getChallenterLiveRadioBean(code);
		} 
	}
	
	public List<ChallenterLiveRadioBean> getWebFrontChallenterLiveRadioListPaging(
			ChallenterLiveRadioBean bean) {
		try (SqlSession session = getSession()) {
			IChallenterLiveRadio iChallenterLiveRadio = session
					.getMapper(IChallenterLiveRadio.class);
			return iChallenterLiveRadio.getWebFrontChallenterLiveRadioListPaging(bean);
		} 
	}
	
	public int getWebFrontChallenterLiveRadioCount(ChallenterLiveRadioBean bean) {
		try (SqlSession session = getSession()) {
			IChallenterLiveRadio iChallenterLiveRadio = session
					.getMapper(IChallenterLiveRadio.class);
			return iChallenterLiveRadio.getWebFrontChallenterLiveRadioCount(bean);
		} 
	}

	

}
