/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月16日下午7:25:32
 * 版本号：v1.0
 * 本类主要用途叙述：简单数据Bll
 */

package cn.gyyx.action.bll.challenger;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.challenger.SameDataBean;
import cn.gyyx.action.dao.challenger.SameDataDao;

public class SameDataBll {
	private SameDataDao sameDataDao = new SameDataDao();

	/**
	 * 设定简单数据，没有加入，有则更新
	 * 
	 * @param type
	 * @param content
	 * @param actionCode
	 */
	public void resetSameDate(String type, String content, int actionCode) {
		sameDataDao.resetSameDate(type, content, actionCode);
	}

	/***
	 * 获取这个类型的簡單数据
	 * 
	 * @param type
	 * @return
	 */
	public SameDataBean getSameDataBean(String type, int actionCode) {
		return sameDataDao.getSameDataBean(type, actionCode);
	}
	
	//content 是int 类型，一个事务增加
	public void increaseSameDate(String type, int activityCode,
			double increaseScore, SqlSession session) {
		sameDataDao.increaseSameDate(type, activityCode, increaseScore, session);
	}
}
