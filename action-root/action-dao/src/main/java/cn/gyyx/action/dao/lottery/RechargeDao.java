/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:06:06
 * 版本号：v1.0
 * 本类主要用途叙述：充值卡
 */



package cn.gyyx.action.dao.lottery;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.RechargeBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class RechargeDao implements IRecharge {
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	/**
	 * 获取用户中奖的礼品密码和卡号
	 * @param code
	 * @param actionCode
	 * @return
	 */
	@Override
		public String seltectCardCode(@Param("userCode") int code,@Param("actionCode") int actionCode){
			try(SqlSession sqlSession = getSession()) {
				IRecharge iRecharge = sqlSession.getMapper(IRecharge.class);
				return  iRecharge.seltectCardCode(code,actionCode);
				 
			}
		}
	@Override
	public void updateRecharge(int actionCode, String type, int code) {
		// TODO Auto-generated method stub
		try(SqlSession sqlSession = getSession()) {
			IRecharge iRecharge = sqlSession.getMapper(IRecharge.class);
			 iRecharge.updateRecharge(actionCode, type, code);
			 sqlSession.commit();
		}
	}

	@Override
	public String getCardCode(int code,int actionCode,String type) {
		try(SqlSession sqlSession = getSession()) {
			IRecharge iRecharge = sqlSession.getMapper(IRecharge.class);
			return  iRecharge.getCardCode(code,actionCode,type);
			 
		}
	}
	@Override
	public void addRechargeInfo(RechargeBean rechargeBean){
		try(SqlSession sqlSession = getSession()) {
			IRecharge iRecharge = sqlSession.getMapper(IRecharge.class);
			iRecharge.addRechargeInfo(rechargeBean);
			sqlSession.commit(); 
		}
	}
	@Override
	public int getRechargeCount(RechargeBean rechargeBean){
		try(SqlSession sqlSession = getSession()) {
			IRecharge iRecharge = sqlSession.getMapper(IRecharge.class);
			return iRecharge.getRechargeCount(rechargeBean);
			 
		}
	}
	@Override
	public int getRechargeCountNoUse(RechargeBean rechargeBean){
		try(SqlSession sqlSession = getSession()) {
			IRecharge iRecharge = sqlSession.getMapper(IRecharge.class);
			return iRecharge.getRechargeCountNoUse(rechargeBean);
			 
		}
	}
}
