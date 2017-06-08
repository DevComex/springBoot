/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月16日下午4:37:22
 * 版本号：v1.0
 * 本类主要用途叙述：抽奖的service
 */

package cn.gyyx.action.service.dnelottery;

import java.util.Date;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.UserRecordBean;
import cn.gyyx.action.bll.lottery.RechargeBll;
import cn.gyyx.action.bll.lottery.UserRecordBll;
import cn.gyyx.action.service.lottery.LotteryService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class DnelotteryService {
	private UserRecordBll dneLotteryBll = new UserRecordBll();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(DnelotteryService.class);
	private LotteryService lottery = new LotteryService();
	private RechargeBll rechargeBll = new RechargeBll();
	private UserRecordBll userRecordBll = new UserRecordBll();
	UserRecordBean   userRecordBean =new UserRecordBean();
	
	

	/**
	 * 根据时间判断用户是否有资格
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean isExitLotteryByTime(int userCode, int actionCode, String type) {

		logger.debug("userCode:" + userCode + "actionCode:" + actionCode
				+ "type:" + type);
		Date date = new Date();

		try {
			// 如果不存在纪录

			if (dneLotteryBll.getUserRecordBeanByTime(userCode,
					dneLotteryBll.getDateTurn(date), actionCode, type) == null) {
				return true;

			}
			// 如果存在记录
			else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("DneLotteryBll", e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 判断该ip是否存在记录信息
	 * 
	 * @param ip
	 * @param actionCode
	 * @param type
	 * @return
	 */
	public boolean isExitLotteryByIp(String ip, int actionCode, String type) {
		logger.debug("isExitLotteryByIp param", "userCode:" + ip
				+ "actionCode:" + actionCode + "type:" + type);
		if (dneLotteryBll.getUserRecordBeanByIp(ip, actionCode, type) == null) {
			return true;

		}
		// 如果存在记录
		else {
			return false;
		}
	}

	/**
	 * 中奖主函数
	 * 
	 * @param actionCode
	 * @param userCode
	 * @param type
	 * @param userName
	 * @param server
	 * @param serverCode
	 * @param ip
	 * @param noPrize
	 * @return
	 */
	public ResultBean<UserLotteryBean> userLottery(int actionCode,
			int userCode, String type, String userName, String server,
			int serverCode, String ip, PrizeBean noPrize) {
		logger.debug("actionCode:" + actionCode + " userCode" + userCode
				+ " type" + type + " userName" + userName + " server" + server
				+ " serverCode" + serverCode + " ip" + ip + " noPrize"
				+ noPrize);
		ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();
		if (isExitLotteryByIp(ip, actionCode, "lottery")) {
			// 中奖
			resultBean = lottery.lotteryByDataBase(actionCode, userCode, type,
					userName, server, serverCode, ip, noPrize);
			if(resultBean.getIsSuccess()){
			if(!resultBean.getData().getPrizeEnglish().equals("thank")){
			String cardCodeString = rechargeBll.getCardCode(actionCode,
					resultBean.getData().getPrizeEnglish(), userCode);
			
			logger.debug("cardCodeString", cardCodeString);
			resultBean.setMessage(cardCodeString);
			userRecordBean.setActionCode(actionCode);
			userRecordBean.setIp(ip);
			userRecordBean.setTime(new Date());
			userRecordBean.setType("lottery");
			userRecordBean.setUserAccount(userName);
			userRecordBean.setUserCode(userCode);
			if(resultBean.getIsSuccess()){
				try {
					userRecordBll.add(userRecordBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.warn("记录插入错误呀",e.getMessage());
					resultBean.setProperties(false, "系统错误", null);
				}
				
			}
			
		} else {
			resultBean.setProperties(false, "礼包已被领取", null);
			userRecordBean.setActionCode(actionCode);
			userRecordBean.setIp(ip);
			userRecordBean.setTime(new Date());
			userRecordBean.setType("lottery");
			userRecordBean.setUserAccount(userName);
			userRecordBean.setUserCode(userCode);
				try {
					userRecordBll.add(userRecordBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.warn("记录插入错误呀",e.getMessage());
					resultBean.setProperties(false, "系统错误", null);
				}
			
		}
		}
		else if(resultBean.getData().getPrizeEnglish().equals("thank")){
			userRecordBean.setActionCode(actionCode);
			userRecordBean.setIp(ip);
			userRecordBean.setTime(new Date());
			userRecordBean.setType("lottery");
			userRecordBean.setUserAccount(userName);
			userRecordBean.setUserCode(userCode);
			if(resultBean.getIsSuccess()){
				try {
					userRecordBll.add(userRecordBean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.warn("记录插入错误呀",e.getMessage());
					resultBean.setProperties(false, "系统错误", null);
				}
				
		}
		logger.debug(" userLottery resultBean", resultBean);
		

	}
		}
		return resultBean;
	}
}
