/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:06:06
 * 版本号：v1.0
 * 本类主要用途叙述：充值卡
 */

package cn.gyyx.action.dao.newlottery;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.RechargeBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class NewRechargeDao {
	
	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 获取用户中奖的礼品密码和卡号
	 * 
	 * @param code
	 * @param actionCode
	 * @return
	 */
	
	
	public String selectCardCode(int code, int actionCode) {
		try (SqlSession sqlSession = getSession()){
			IRechargeNew iRecharge = sqlSession.getMapper(IRechargeNew.class);
			return iRecharge.selectCardCode(code, actionCode);
	}
		}
	
	public List<String> selectAllCardCode(int code, int actionCode) {
		try (SqlSession sqlSession = getSession()){
			IRechargeNew iRecharge = sqlSession.getMapper(IRechargeNew.class);
			return iRecharge.selectAllCardCode(code, actionCode);
	}
		}
	 

	/**
	 * 更新卡密信息
	 * @param actionCode
	 * @param type
	 * @param code
	 * @param sqlSession
	 */
	public void updateRecharge(int actionCode, String type, int code,
			SqlSession sqlSession) {
		IRechargeNew iRecharge = sqlSession.getMapper(IRechargeNew.class);
		iRecharge.updateRecharge(actionCode, type, code);
	}

	/***
	 * 获取充值卡卡密
	 * 
	 * @param code
	 * @param actionCode
	 * @param type
	 * @param sqlSession
	 * @return
	 */
	public String getCardCode(int code, int actionCode, String type,
			SqlSession sqlSession) {

		IRechargeNew iRecharge = sqlSession.getMapper(IRechargeNew.class);
		return iRecharge.getCardCode(code, actionCode, type);

	}
	public RechargeBean getCardCodeByCardCode( int actionCode, String type,
			 String cardCode,SqlSession sqlSession){
		IRechargeNew iRecharge = sqlSession.getMapper(IRechargeNew.class);
		return iRecharge.getCardCodeByCardCode(actionCode, type, cardCode);
	}
}
