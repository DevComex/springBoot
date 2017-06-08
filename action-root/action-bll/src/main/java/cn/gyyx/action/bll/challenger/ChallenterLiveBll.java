/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月16日下午3:15:08
 * 版本号：v1.0
 * 本类主要用途叙述：直播的Bll
 */

package cn.gyyx.action.bll.challenger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.ChallenterLiveBean;
import cn.gyyx.action.dao.challenger.ChallenterLiveDao;

public class ChallenterLiveBll {
	/**
	 * 增加直播信息
	 * 
	 * @param challenterLiveBean
	 */
	private ChallenterLiveDao challenterLiveDao = new ChallenterLiveDao();

	/***
	 * 增加直播
	 * 
	 * @param challenterLiveBean
	 */
	public void addChallenterLive(ChallenterLiveBean challenterLiveBean) {
		challenterLiveDao.addChallenterLive(challenterLiveBean);

	}

	/***
	 * 得到一个人发布直播的次数
	 * 
	 * @param userCode
	 * @param begin
	 * @param end
	 * @return
	 */
	public Integer getOneCountLiveInday(int userCode, String begin, String end) {
		return challenterLiveDao.getOneCountLiveIndayNoState(userCode, begin, end);
	}
	public Integer getOneCountLiveInday(int userCode, String begin, String end,SqlSession sqlSession) {
		return challenterLiveDao.getOneCountLiveInday(userCode, begin, end,sqlSession);
	}

	/**
	 * 根据状态获取前多少条信息
	 * 
	 * @param size
	 * @param status
	 * @return
	 */
	public List<ChallenterLiveBean> getTopNumChallenterLiveBean(int size,
			String status) {
		return challenterLiveDao.getTopNumChallenterLiveBean(size, status);
	}

	/***
	 * 根据主键获取一条直播信息
	 * 
	 * @param code
	 * @return
	 */
	public ChallenterLiveBean getOneCodeChallenterLiveBean(int code,
			SqlSession sqlSession) {
		return challenterLiveDao.getOneCodeChallenterLiveBean(code, sqlSession);
	}

	/**
	 * 更新状态与时间
	 * 
	 * @param code
	 * @return
	 */
	public Integer setStateAndTime(int code, String status,
			SqlSession sqlSession) {
		return challenterLiveDao.setStateAndTime(code, status, sqlSession);
	}

	/**
	 * 分页获取待审核直播信息
	 * 
	 * @param size
	 * @param status
	 * @return
	 */
	public List<ChallenterLiveBean> getPageOncheckChallenterLiveBean(int size,
			int pageNo, String status) {
		return challenterLiveDao.getPageOncheckChallenterLiveBean(size, pageNo,
				status);
	}

	/**
	 * 更新状态与时间
	 * 
	 * @param code
	 * @return
	 */
	public Integer getAllCountOncheckChallenterLiveBean() {
		return challenterLiveDao.getAllCountOncheckChallenterLiveBean();

	}
}
