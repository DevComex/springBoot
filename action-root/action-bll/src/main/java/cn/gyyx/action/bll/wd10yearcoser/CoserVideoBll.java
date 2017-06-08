/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午6:19:51
 * 版本号：v1.0
 * 本类主要用途叙述：挑战关系Bll
 */

package cn.gyyx.action.bll.wd10yearcoser;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wd10yearcoser.CoserOfficialVideo;
import cn.gyyx.action.dao.wd10yearcoser.CoserVideoDao;

public class CoserVideoBll {

	private CoserVideoDao coserVideoDao = new CoserVideoDao();

	public int delete(CoserOfficialVideo CoserOfficialVideo,SqlSession session) {
		return coserVideoDao.delete(CoserOfficialVideo, session);
	}
	
	public int update(CoserOfficialVideo CoserOfficialVideo,SqlSession session) {
		return coserVideoDao.update(CoserOfficialVideo, session);
	}
	
	public int insert(CoserOfficialVideo CoserOfficialVideo,SqlSession session) {
		return coserVideoDao.insert(CoserOfficialVideo, session);
	}
	
	public int getVideoCount(CoserOfficialVideo CoserOfficialVideo) {
		return coserVideoDao.getVideoCount(CoserOfficialVideo);
	}
	
	public CoserOfficialVideo getCoserVideo(int code) {
		return coserVideoDao.getCoserVideo(code);
	}
	
	public CoserOfficialVideo getCoserVideo(int code, SqlSession session) {
		return coserVideoDao.getCoserVideo(code,session);
	}

	public List<CoserOfficialVideo> getVideoListPaging(CoserOfficialVideo CoserOfficialVideo) {
		return coserVideoDao.getVideoListPaging(CoserOfficialVideo);
	}

	public void cancleLastTop(SqlSession session) {
		coserVideoDao.cancleLastTop(session);
	}

	public List<CoserOfficialVideo> videoFrontTopList(CoserOfficialVideo bean) {
		return coserVideoDao.videoFrontTopList(bean);
	}

	public List<CoserOfficialVideo> getVideoFrontListPaging(
			CoserOfficialVideo bean) {
		return coserVideoDao.getVideoFrontListPaging(bean);
	}

	public int getVideoFrontCount(CoserOfficialVideo bean) {
		return coserVideoDao.getVideoFrontCount(bean);
	}


//	public List<CoserOfficialVideo> getWebFrontCoserOfficialVideoListPaging(
//			CoserOfficialVideo bean) {
//		return coserVideoDao.getWebFrontCoserOfficialVideoListPaging(bean);
//	}

}
