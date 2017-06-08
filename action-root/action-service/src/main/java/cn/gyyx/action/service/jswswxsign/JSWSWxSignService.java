/**------------------------------------------------------------------------- 
* 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/ 

package cn.gyyx.action.service.jswswxsign;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.jswswxsign.ExchangeLog;
import cn.gyyx.action.beans.jswswxsign.Gift;
import cn.gyyx.action.beans.jswswxsign.GiftExchangeCode;
import cn.gyyx.action.beans.jswswxsign.Sign;
import cn.gyyx.action.beans.jswswxsign.SignLog;
import cn.gyyx.action.beans.jswswxsign.UserScore;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.bll.jswswxsign.DateTools;
import cn.gyyx.action.bll.jswswxsign.ExchangeLogBLL;
import cn.gyyx.action.bll.jswswxsign.GiftBLL;
import cn.gyyx.action.bll.jswswxsign.GiftExchangeCodeBLL;
import cn.gyyx.action.bll.jswswxsign.SignBLL;
import cn.gyyx.action.bll.jswswxsign.SignLogBLL;
import cn.gyyx.action.bll.jswswxsign.UserScoreBLL;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/** 
 * 作        者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年5月13日 下午6:32:00
 * 描        述： 绝世武神微信签到功能的业务拼装层
 */
public class JSWSWxSignService {
	private UserScoreBLL userScoreBLL = new UserScoreBLL();
	private ExchangeLogBLL exchangeLogBLL = new ExchangeLogBLL();
	private GiftBLL giftBLL = new GiftBLL();
	private GiftExchangeCodeBLL giftExchangeCodeBLL = new GiftExchangeCodeBLL();
	private SignBLL signBLL = new SignBLL();
	private SignLogBLL signLogBLL = new SignLogBLL();
	NewUserLotteryBll newUserLotteryBll = new NewUserLotteryBll();
	
	private int actionCode = 366;//对应 hd_config_tb 表中的一条记录
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(JSWSWxSignService.class);
	
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/**
	 * 获取用户的积分
	 */
	public ResultBean<Integer> getUserScore(String openId) {
		ResultBean<Integer> res = new ResultBean<Integer>(false, "操作失败！", 0);
		if (StringUtils.isBlank(openId)) {
			res.setMessage("缺少必要参数！");
			return res;
		}
		UserScore us = null;
		try{
			us = userScoreBLL.getUserScore(openId);
			if (us == null) {
				us = new UserScore();
				us.setCurIntergral(0);
			}
			
			res.setIsSuccess(true);
			res.setMessage("请求成功！");
			res.setData(us.getCurIntergral());
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[获取用户积分Service]异常",e);
		}
		
		return res;
	}

	/**
	 * 获取用户已兑换过的礼包码列表
	 */
	public ResultBean<List<HashMap<String,String>>> getExchangeLogList(String openId) {
		ResultBean<List<HashMap<String,String>>> res = new ResultBean<List<HashMap<String,String>>>();
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		res.setProperties(false, "操作失败！", null);
		
		if (StringUtils.isBlank(openId)) {
			res.setMessage("缺少必要参数！");
			return res;
		}
		
		List<ExchangeLog> els = null;
		try{
			els = exchangeLogBLL.getExchangeLogList(openId);
			if(els != null && els.size() > 0){
				for(ExchangeLog log : els){
					HashMap<String,String> m = new HashMap<String,String>();
					
					m.put("exangeCode", log.getExangeCode());
					m.put("exangeTime", DateTools.formatDate(log.getExangeTime()));
					m.put("giftName", log.getGiftName());
					list.add(m);
				}
			}
			
			res.setIsSuccess(true);
			res.setMessage("请求成功！");
			res.setData(list);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[获取用户已兑换过的礼包码列表Service]异常",e);
		}
		
		return res;
	}

	/**
	 * 获取积分商品兑换礼包列表
	 */
	public ResultBean<List<Gift>> getGiftList() {
		ResultBean<List<Gift>> res = new ResultBean<List<Gift>>();
		res.setProperties(false, "操作失败！", null);
		
		List<Gift> els = null;
		try{
			els = giftBLL.getGiftList();
			
			res.setIsSuccess(true);
			res.setMessage("请求成功！");
			res.setData(els);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[获取积分商品兑换礼包列表Service]异常",e);
		}
		
		return res;
	}

	/**
	 * 签到
	 */
	public ResultBean<HashMap<String,String>> sign(String openId) {
		ResultBean<HashMap<String,String>> res = new ResultBean<HashMap<String,String>>();
		HashMap<String,String> signMap = new HashMap<String,String>();
		res.setProperties(false, "操作失败！", signMap);
		
		if (StringUtils.isBlank(openId)) {
			res.setMessage("缺少必要参数！");
			return res;
		}
		
		SqlSession session = null;
		try{
			//判断活动是否开始或者结束
			String mes = acitivityIsAvailable();
			if(StringUtils.isNotBlank(mes)){
				res.setMessage(mes);
				return res;	
			}
			
			session = getSession();//开启共用session，保障事务
			Date curTime = new Date();
			
			//1.获得用户最后签到的日期,判断用户是否签到过并且今日是否签到
			int userScoreNumber = 0;
			int continuousCount = 0;
			Sign recentSign = signBLL.getRecentSign(openId,session);
			if(recentSign == null){
				recentSign = new Sign();
				recentSign.setLastSignDate(curTime);
				recentSign.setOpenId(openId);
				recentSign.setSignNumber(1);
				userScoreNumber += getScoreByRule(1);   
				continuousCount = 1;
				signBLL.insert(recentSign,session);
			}else{
				Date recentSignTime = recentSign.getLastSignDate();
				if(DateTools.formatDate(recentSignTime).equals(DateTools.formatDate(curTime))){
					res.setMessage("今日您已签到，请明日再来哟！");
					return res;	
				}
				//是昨天的话
				if(DateTools.formatDate(recentSignTime).equals(DateTools.formatDate(DateTools.getPreviousDate(curTime)))){
					continuousCount = recentSign.getSignNumber() + 1;
				}else{
					continuousCount = 1;
				}
				recentSign.setSignNumber(continuousCount); 
				userScoreNumber += getScoreByRule(continuousCount);  
				recentSign.setLastSignDate(curTime);
				signBLL.update(recentSign,session);
			}
			
			//2.签到明细插入一条日志
			SignLog signLog = new SignLog();
			signLog.setOpenId(openId);
			signLog.setSid(recentSign.getCode());
			signLog.setSignTime(curTime);
			signLog.setObtainedIntegral(userScoreNumber);
			signLogBLL.insert(signLog,session);
			
			//3.积分增加
			UserScore userScore = userScoreBLL.getUserScore(openId);
			if(userScore != null){
				userScore.setCurIntergral(userScore.getCurIntergral() + userScoreNumber);
				userScoreBLL.update(userScore,session);
			}else{
				userScore = new UserScore();
				userScore.setOpenId(openId);
				userScore.setCurIntergral(userScoreNumber);
				userScoreBLL.insert(userScore,session);
			}
			
			res.setIsSuccess(true);
			res.setMessage("签到成功！");
			signMap.put("continuousCount", continuousCount + "");
			signMap.put("acquireScore", userScoreNumber + "");
			res.setData(signMap);
			
			session.commit(true);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[签到Service]异常",e);
			if(session != null){
				session.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return res;
	}
	
	/***
	 * 积分兑换礼包
	 */
	public ResultBean<String> exchange(String openId, String gid, String os) {
		ResultBean<String> res = new ResultBean<String>();
		res.setProperties(false, "操作失败！", "");
		
		if (StringUtils.isBlank(openId) || StringUtils.isBlank(gid) || StringUtils.isEmpty(os)) {
			res.setMessage("缺少必要参数！");
			return res;
		}
		
		SqlSession session = null;
		try(DistributedLock lock = new DistributedLock("JSWSExchange"+actionCode+"_"+gid)) {
			lock.weakLock(30, 11);
//		try {
			
			//判断活动是否开始或者结束
			String mes = acitivityIsAvailable();
			if(StringUtils.isNotBlank(mes)){
				res.setMessage(mes);
				return res;	
			}
			
			Gift gift = new Gift();
			gift.setCode(Integer.parseInt(gid));
			gift = giftBLL.getGift(gift);
			if(gift == null){
				res.setMessage("礼包已下架!");
				return res;
			}
			
			session = getSession();//共用session
			Date curTime = new Date();
			
			UserScore us = userScoreBLL.getUserScore(openId,session);
			if (us == null) {
				us = new UserScore();
				us.setCurIntergral(0);
			}
			if (us.getCurIntergral() < gift.getGiftIntergral()) {
				res.setMessage("对不起，您的当前积分不足！");
				return res;
			}
			GiftExchangeCode giftExchangeCode = giftExchangeCodeBLL.getGiftExchangeCodeOne(Integer.parseInt(gid), os, session);
			if(giftExchangeCode == null){
				res.setMessage("本期礼包已发放完毕<br/>敬请期待下期签到礼包哦！");
				return res;
			}
			giftExchangeCode.setIsUse(1);
			//修改兑换码为已使用
			giftExchangeCodeBLL.update(giftExchangeCode,session);
			us.setCurIntergral(us.getCurIntergral() - gift.getGiftIntergral());
			//修改用户积分
			userScoreBLL.update(us, session);
			ExchangeLog exchangeLog = new ExchangeLog();
			exchangeLog.setExangeCode(giftExchangeCode.getExchangeCode());
			exchangeLog.setExangeIntegral(gift.getGiftIntergral());
			exchangeLog.setExangeTime(curTime);
			exchangeLog.setOpenId(openId);
			exchangeLog.setGid(Integer.parseInt(gid));
			//增加一条兑换记录
			exchangeLogBLL.insert(exchangeLog,session);
			
			res.setIsSuccess(true);
			res.setMessage("兑换成功！");
			res.setData(giftExchangeCode.getExchangeCode());
			
			session.commit(true);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[积分兑换礼包Service]异常",e);
			if(session != null){
				session.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		return res;
	}
	
	/***
	 * 连续签到次数清零
	 */
	public void updateSignZero() {
		SqlSession session = null;
		try {
			session = getSession();
			signBLL.updateSignZero(session);
			session.commit(true);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[连续签到次数清零Service]异常",e);
			if(session != null){
				session.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	/***
	 * 积分清零
	 */
	public void updateUserScoreZero() {
		SqlSession session = null;
		try {
			session = getSession();
			userScoreBLL.updateUserScoreZero(session);
			session.commit(true);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[积分清零Service]异常",e);
			if(session != null){
				session.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	/**
	 * 积分清零 && 连续签到次数清零
	 */
	public void updateZeroInMonthStart() {
		SqlSession session = null;
		try {
			session = getSession();
			userScoreBLL.updateUserScoreZero(session);
			signBLL.updateSignZero(session);
			session.commit(true);
		}catch(Exception e){
			logger.error("绝世武神微信签到功能[积分清零Service]异常,错误堆栈:{}",Throwables.getStackTraceAsString(e));
			if(session != null){
				session.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	/**
	 * @param res
	 * @param session
	 */
	private String acitivityIsAvailable() {
		SqlSession session = getSession();
		try{
			ContrParmBean controParm = newUserLotteryBll.getContrParm(actionCode, session);
			if(controParm == null){
				return "活动已结束！";	
			}
			Date signStartTime = controParm.getActivityStart();
			Date signEndTime = controParm.getActivityEnd();
			Date curTime = new Date();
			if(curTime.before(signStartTime)){
				return "敬请期待！";	
			}
			if(curTime.after(signEndTime)){
				return "活动已结束";
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return "";
	}
	
	/**
	 * 设置用户签到积分累加规则
	 */
	public int getScoreByRule(int signCount){
		int score = 10;
		
		if(signCount == 5){
			score += 20;
        }else if(signCount == 10){
        	score += 40;
        }else if(signCount == 20){
        	score += 60;
        }else if(signCount == 30){
        	score += 100;
        }
		
		return score;
	}

	


}
