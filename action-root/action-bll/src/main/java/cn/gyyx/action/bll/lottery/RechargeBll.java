/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月15日下午5:15:02
 * 版本号：v1.0
 * 本类主要用途叙述：充值卡Bll
 */



package cn.gyyx.action.bll.lottery;


import cn.gyyx.action.beans.lottery.RechargeBean;
import cn.gyyx.action.dao.lottery.IRecharge;
import cn.gyyx.action.dao.lottery.RechargeDao;

public class RechargeBll {
	private IRecharge iRecharge=new RechargeDao();
	/**
	 * 得到充值卡卡号
	 * @param actionCode
	 * @param type
	 * @param code
	 * @return
	 */
	public String getCardCode(int actionCode, String type, int code){
		iRecharge.updateRecharge(actionCode, type, code);
		return iRecharge.getCardCode(code, actionCode,type);
	}
	/**
	 * 得到中奖人的充值卡卡号
	 * @param actionCode
	 * @param type
	 * @param code
	 * @return
	 */
	public String getCardCodeByPageShow(int actionCode, String type, int code){
		return iRecharge.getCardCode(code, actionCode,type);
	}
	/**
	 * 得到用户已经中的卡号和密码
	 * @param actionCode
	 * @param code
	 * @return
	 */
	public String getCardCodeByUser(int actionCode, int code){
		return iRecharge.seltectCardCode(code, actionCode);
	}
	
	public void addRechargeInfo(RechargeBean rechargeBean){
		iRecharge.addRechargeInfo(rechargeBean);
	}
	
	public int getRechargeCount(RechargeBean rechargeBean){
		return iRecharge.getRechargeCount(rechargeBean);
	}
		
	public int getRechargeCountNoUse(RechargeBean rechargeBean){
		return iRecharge.getRechargeCountNoUse(rechargeBean);
	}
}
