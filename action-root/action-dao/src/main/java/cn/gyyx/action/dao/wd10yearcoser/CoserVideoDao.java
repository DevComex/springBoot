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

import cn.gyyx.action.beans.wd10yearcoser.CoserOfficialVideo;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class CoserVideoDao {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	public List<CoserOfficialVideo> getVideoListPaging(CoserOfficialVideo bean) {
		try (SqlSession session = getSession()) {
			ICoserVideo iCoserVideo = session
					.getMapper(ICoserVideo.class);
			return iCoserVideo.getVideoListPaging(bean);
		} 
	}
	
	public int insert(CoserOfficialVideo bean, SqlSession session) {
		ICoserVideo iCoserVideo = session
				.getMapper(ICoserVideo.class);
		return iCoserVideo.insert(bean);
	}
	
	public int update(CoserOfficialVideo bean, SqlSession session) {
		ICoserVideo iCoserVideo = session
				.getMapper(ICoserVideo.class);
		return iCoserVideo.update(bean);
	}
	
	public int delete(CoserOfficialVideo bean, SqlSession session) {
		ICoserVideo iCoserVideo = session
				.getMapper(ICoserVideo.class);
		return iCoserVideo.delete(bean);
	}
	
	public int getVideoCount(CoserOfficialVideo bean) {
		try (SqlSession session = getSession()) {
			ICoserVideo iCoserVideo = session
					.getMapper(ICoserVideo.class);
			return iCoserVideo.getVideoCount(bean);
		} 
	}
	
	public CoserOfficialVideo getCoserVideo(int code) {
		try (SqlSession session = getSession()) {
			ICoserVideo iCoserVideo = session
					.getMapper(ICoserVideo.class);
			return iCoserVideo.getCoserOfficialVideo(code);
		} 
	}
	
	public CoserOfficialVideo getCoserVideo(int code, SqlSession session) {
		ICoserVideo iCoserVideo = session
				.getMapper(ICoserVideo.class);
		return iCoserVideo.getCoserOfficialVideo(code);
	}

	public void cancleLastTop(SqlSession session) {
		ICoserVideo iCoserVideo = session
				.getMapper(ICoserVideo.class);
		iCoserVideo.cancleLastTop();
	}
	
	public List<CoserOfficialVideo> videoFrontTopList(CoserOfficialVideo bean) {
		try (SqlSession session = getSession()) {
			ICoserVideo iCoserVideo = session
					.getMapper(ICoserVideo.class);
			return iCoserVideo.videoFrontTopList(bean);
		} 
	}
	
	public int getVideoFrontCount(CoserOfficialVideo bean) {
		try (SqlSession session = getSession()) {
			ICoserVideo iCoserVideo = session
					.getMapper(ICoserVideo.class);
			return iCoserVideo.getVideoFrontCount(bean);
		} 
	}
	
	public List<CoserOfficialVideo> getVideoFrontListPaging(
			CoserOfficialVideo bean) {
		try (SqlSession session = getSession()) {
			ICoserVideo iCoserVideo = session
					.getMapper(ICoserVideo.class);
			return iCoserVideo.getVideoFrontListPaging(bean);
		} 
	}

}
