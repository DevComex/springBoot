/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午1:25:31
 * 版本号：v1.0
 * 本类主要用途叙述：活动参数的Dao
 */

package cn.gyyx.action.dao.newlottery;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.ContrParmBean;

public class NewContrParmDao {

	/***
	 * 获取活动信息
	 * 
	 * @param actionCode
	 * @param sqlSession
	 * @return
	 */
	public ContrParmBean getConPram(int actionCode, SqlSession sqlSession) {
		// TODO Auto-generated method stub
		IConPramNew iConPram = sqlSession.getMapper(IConPramNew.class);
		return iConPram.getConPram(actionCode);
	}

}
