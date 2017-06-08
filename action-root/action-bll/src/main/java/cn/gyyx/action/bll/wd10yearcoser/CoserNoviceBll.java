/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王安东
 * 创建时间：2016年7月14日下午6:19:51
 * 版本号：v1.0
 * 本类主要用途叙述：挑战关系Bll
 */

package cn.gyyx.action.bll.wd10yearcoser;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wd10yearcoser.CoserNovice;
import cn.gyyx.action.dao.wd10yearcoser.CoserNoviceDao;

public class CoserNoviceBll {

	private CoserNoviceDao coserNoviceDao = new CoserNoviceDao();

	public int delete(CoserNovice CoserNovice,SqlSession session) {
		return coserNoviceDao.delete(CoserNovice, session);
	}
	
	public int update(CoserNovice CoserNovice,SqlSession session) {
		return coserNoviceDao.update(CoserNovice, session);
	}
	
	public int insert(CoserNovice CoserNovice,SqlSession session) {
		return coserNoviceDao.insert(CoserNovice, session);
	}
	
	public int getNoviceCount(CoserNovice CoserNovice) {
		return coserNoviceDao.getNoviceCount(CoserNovice);
	}
	
	public CoserNovice getCoserNovice(int code) {
		return coserNoviceDao.getCoserNovice(code);
	}
	
	public List<CoserNovice> getNoviceListPaging(CoserNovice CoserNovice) {
		return coserNoviceDao.getNoviceListPaging(CoserNovice);
	}

	public List<CoserNovice> getNoviceFontListPaging(CoserNovice bean) {
		return coserNoviceDao.getNoviceFontListPaging(bean);
	}

	public CoserNovice getNoviceNew() {
		return coserNoviceDao.getNoviceNew();
	}

}
