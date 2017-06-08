/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午6:19:51
 * 版本号：v1.0
 * 本类主要用途叙述：挑战关系Bll
 */

package cn.gyyx.action.bll.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.ChallenterLiveRadioBean;
import cn.gyyx.action.dao.challenger.ChallenterLiveRadioDao;

public class ChallenterLiveRadioBll {

	private ChallenterLiveRadioDao challenterLiveRadioDao = new ChallenterLiveRadioDao();

	public int delete(ChallenterLiveRadioBean challenterLiveRadioBean,SqlSession session) {
		return challenterLiveRadioDao.delete(challenterLiveRadioBean, session);
	}
	
	public int update(ChallenterLiveRadioBean challenterLiveRadioBean,SqlSession session) {
		return challenterLiveRadioDao.update(challenterLiveRadioBean, session);
	}
	
	public int insert(ChallenterLiveRadioBean challenterLiveRadioBean,SqlSession session) {
		return challenterLiveRadioDao.insert(challenterLiveRadioBean, session);
	}
	
	public int getChallenterLiveRadioCount(ChallenterLiveRadioBean challenterLiveRadioBean) {
		return challenterLiveRadioDao.getChallenterLiveRadioCount(challenterLiveRadioBean);
	}
	
	public ChallenterLiveRadioBean getChallenterLiveRadioBean(int code) {
		return challenterLiveRadioDao.getChallenterLiveRadioBean(code);
	}
	
	public List<ChallenterLiveRadioBean> getChallenterLiveRadioListPaging(ChallenterLiveRadioBean challenterLiveRadioBean) {
		return challenterLiveRadioDao.getChallenterLiveRadioListPaging(challenterLiveRadioBean);
	}

	public List<ChallenterLiveRadioBean> getWebFrontChallenterLiveRadioListPaging(
			ChallenterLiveRadioBean bean) {
		return challenterLiveRadioDao.getWebFrontChallenterLiveRadioListPaging(bean);
	}

	public int getWebFrontChallenterLiveRadioCount(ChallenterLiveRadioBean bean) {
		return challenterLiveRadioDao.getWebFrontChallenterLiveRadioCount(bean);
	}
}
