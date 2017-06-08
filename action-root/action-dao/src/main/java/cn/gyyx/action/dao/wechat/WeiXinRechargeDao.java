/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:06:06
 * 版本号：v1.0
 * 本类主要用途叙述：充值卡
 */



package cn.gyyx.action.dao.wechat;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class WeiXinRechargeDao implements IWeiXinRecharge {
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
	public String seltectCardCode(@Param("openId") String openId,@Param("actionCode") int actionCode){
		try(SqlSession sqlSession = getSession()) {
			IWeiXinRecharge iRecharge = sqlSession.getMapper(IWeiXinRecharge.class);
			return  iRecharge.seltectCardCode(openId,actionCode);
			 
		}
	}
	@Override
	public void updateRecharge(int actionCode, String type, String openId) {
		// TODO Auto-generated method stub
		try(SqlSession sqlSession = getSession()) {
			IWeiXinRecharge iRecharge = sqlSession.getMapper(IWeiXinRecharge.class);
			 iRecharge.updateRecharge(actionCode, type, openId);
			 sqlSession.commit();
		}
	}

	@Override
	public String getCardCode(String openId,int actionCode,String type) {
		try(SqlSession sqlSession = getSession()) {
			IWeiXinRecharge iRecharge = sqlSession.getMapper(IWeiXinRecharge.class);
			return  iRecharge.getCardCode(openId,actionCode,type);
			 
		}
	}
	
	/**
	 * 
	* @Title: updateRecharge
	* @Description: TODO 获取1.58新手礼包卡号
	* @param @param actionCode
	* @param @param type
	* @param @param openId    
	* @return void    
	* @throws
	 */
	public String  getLibaoCode(@Param("openId") String openId,@Param("actionCode") int actionCode,@Param("type") String type){
		try(SqlSession sqlSession = getSession()) {
			IWeiXinRecharge iRecharge = sqlSession.getMapper(IWeiXinRecharge.class);
			return  iRecharge.getLibaoCode(openId, actionCode, type);
		}
	}
	
	public String getCardCodeSession(String openId,int actionCode,String type,SqlSession sqlSession) {
	
		 IWeiXinRecharge iRecharge = sqlSession.getMapper(IWeiXinRecharge.class);
			return  iRecharge.getCardCode(openId,actionCode,type);
			 
	}
	public void updateRecharge(int actionCode, String type, String openId,SqlSession sqlSession) {
		
			IWeiXinRecharge iRecharge = sqlSession.getMapper(IWeiXinRecharge.class);
			 iRecharge.updateRecharge(actionCode, type, openId);			 
		
	}
	
	
}
