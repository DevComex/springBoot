/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:15:02
 * 版本号：v1.0
 * 本类主要用途叙述：充值卡Bll
 */



package cn.gyyx.action.bll.wechat;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.dao.wechat.IWeiXinRecharge;
import cn.gyyx.action.dao.wechat.WeiXinRechargeDao;

public class WeiXinRechargeBll {
	private WeiXinRechargeDao weiXinRechargeDao=new WeiXinRechargeDao();
	private IWeiXinRecharge iRecharge=new WeiXinRechargeDao();
	/**
	 * 得到充值卡卡号
	 * @param actionCode
	 * @param type
	 * @param code
	 * @return
	 */
	public String getCardCode(int actionCode, String type, String openId){
		iRecharge.updateRecharge(actionCode, type, openId);
		return iRecharge.getCardCode(openId, actionCode,type);
	}
	/**
	 * 得到用户已经中的卡号和密码
	 * @param actionCode
	 * @param code
	 * @return
	 */
	public String getCardCodeByUser(int actionCode, String openId){
		return iRecharge.seltectCardCode(openId, actionCode);
	}
	
	/**
	 * 
	* @Title: getLibaoCode
	* @Description: TODO 获取1.58新手礼包的卡号
	* @param @param actionCode
	* @param @param type
	* @param @param openId
	* @param @return    
	* @return String    
	* @throws
	 */
	public String getLibaoCode(int actionCode, String type, String openId){
		iRecharge.updateRecharge(actionCode, type, openId);
		return iRecharge.getLibaoCode(openId, actionCode, type);
	}
	
	/**
	 * 得到充值卡卡号
	 * @param actionCode
	 * @param type
	 * @param code
	 * @return
	 */
	public String getCardCode(int actionCode, String type, String openId,SqlSession sqlSession){
		weiXinRechargeDao.updateRecharge(actionCode, type, openId,sqlSession);
		return weiXinRechargeDao.getCardCodeSession(openId, actionCode, type, sqlSession);
	}
	
	
}
