/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午1:06:15
 * 版本号：v1.0
 * 本类主要用途叙述：奖品的数据库Dao
 * 
 */

package cn.gyyx.action.dao.newlottery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.lottery.PrizeBean;

public class NewPrizeDao {

	/**
	 * 获取奖品信息
	 * 
	 * @param actionCode
	 * @return
	 */
	public List<PrizeBean> getPrize(int actionCode, SqlSession sqlSession) {
		IPrizeNew prize = sqlSession.getMapper(IPrizeNew.class);
		return prize.getPrize(actionCode);
	}
     /**
      * 根据主键获取奖品信息
      * @param code
      * @param sqlSession
      * @return
      */
	public PrizeBean getPrizeByCode(int code, SqlSession sqlSession) {
		IPrizeNew prize = sqlSession.getMapper(IPrizeNew.class);
		return prize.getPrizeByCode(code);
	}
	/*
	 * public PrizeBean getPrizeByCode(@Param("code") int code){ // TODO
	 * Auto-generated method stub try (SqlSession sqlSession = getSession()) {
	 * IPrize prize =sqlSession.getMapper(IPrize.class); return
	 * prize.getPrizeByCode(code); } } public Integer updatePrizeBean(PrizeBean
	 * prizeBean){ Integer result = null; SqlSession session = getSession(); try
	 * { IPrize prize = session .getMapper(IPrize.class); result =
	 * prize.updatePrizeBean(prizeBean); session.commit(); } catch (Exception e)
	 * {
	 * 
	 * } finally { session.close(); } return result; }
	 * 
	 * @Override public List<PrizeBean> getPrizeAll(int actionCode) { // TODO
	 * Auto-generated method stub try (SqlSession sqlSession = getSession()) {
	 * IPrize prize =sqlSession.getMapper(IPrize.class); return
	 * prize.getPrizeAll(actionCode); } }
	 */
}
