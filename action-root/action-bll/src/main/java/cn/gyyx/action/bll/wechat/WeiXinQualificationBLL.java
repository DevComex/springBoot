/*************************************************
       Copyright ©, 2015, GY Game
       Author: 王雷
       Created: 2015年-12月-21日
       Note: 微信端用户资格逻辑类
************************************************/
package cn.gyyx.action.bll.wechat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wechat.WeiXinQualificationBean;
import cn.gyyx.action.dao.wechat.WeiXinQualificationDAO;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WeiXinQualificationBLL {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WeiXinQualificationBLL.class);
	private WeiXinQualificationDAO qualificationDao = new WeiXinQualificationDAO();

	/**
	 * 根据用户编号获取用户抽奖资格信息
	 * @param openId
	 * @return
	 */
	public WeiXinQualificationBean getQualification(String openId) {
		return qualificationDao.getQualification(openId);
	}

	/**
	 * 根据用户code更新用户抽奖次数
	 * @param qualification
	 */
	public void setLotteryTime(WeiXinQualificationBean qualification) {
		logger.debug("WeiXinQualificationBean", qualification);

		qualification.setLotteryTime(qualification.getLotteryTime() - 1);
		logger.debug("WeiXinQualificationBean", qualification);
		qualificationDao.setLotteryTime(qualification);
	}
	
	public void addLotteryTime(WeiXinQualificationBean qualification) {
		logger.debug("WeiXinQualificationBean", qualification);

		qualification.setLotteryTime(qualification.getLotteryTime() + 1);
		logger.debug("WeiXinQualificationBean", qualification);
		qualificationDao.setLotteryTime(qualification);
	}
	/**
	 * 根据用户code更新用户抽奖次数
	 * @param qualification
	 */
	public void setTimeLottery(WeiXinQualificationBean qualification) {
		logger.debug("WeiXinQualificationBean", qualification);
		qualificationDao.setLotteryTime(qualification);
	}

	/**
	 * 根据用户编号和活动编号获取抽奖资格信息
	 * @param openId
	 * @param actionCode
	 * @return
	 */
	public WeiXinQualificationBean selectByUserAndAction(String openId, String actionCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("openId", openId);
		map.put("actionCode", actionCode);
		return qualificationDao.selectByUserAndAction(map);
	}
	/**
	 * 增加用户资格 ——问道康师傅V1.211活动需要
	 * @param qualification
	 */
	public void addQualification(WeiXinQualificationBean qualification){
		qualificationDao.insertQualification(qualification);
	}
	public void reduceTime( String openId,
			 int actionCode){
		qualificationDao.reduceTime(openId, actionCode);
	}
	public List<WeiXinQualificationBean> selectByAction(int actionCode){
		return qualificationDao.selectByAction(actionCode);
		
	}
}
