/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月5日下午1:56:32
 * 版本号：v1.0
 * 本类主要用途叙述：中奖
 */



package cn.gyyx.action.bll.lottery;

import java.util.HashMap;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.dao.lottery.IUserLotteryByOrder;
import cn.gyyx.action.dao.lottery.UserLotteryDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;


public class LotteryByOrderBll {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LotteryByOrderBll.class);
	/**
	 * 获得用户抽奖的名次
	 * @param userCode
	 * @param actionCode
	 * @return
	 */
	public ResultBean<Integer> getOrderByUserCode(int userCode,int actionCode){
		logger.debug("userCode",userCode);
		logger.debug("actionCode",actionCode);
		ResultBean<Integer> resultBean=new ResultBean<Integer>();
		IUserLotteryByOrder userLotteryByOrder=new UserLotteryDao();
		//设定参数
		HashMap<String, Integer> map=new HashMap<String, Integer>();
		map.put("userCode", userCode);
		map.put("errorCode",0);
		map.put("actionCode", actionCode);
		map.put("lotteryOrder",0);
		logger.debug("map",map);
		userLotteryByOrder.getOrderByUserCode(map);
		logger.debug("map",map);
		//存储过程没有出现错误
		if(map.get("errorCode")==0){
			resultBean.setData(map.get("lotteryOrder"));
			resultBean.setIsSuccess(true);
		}
		else{
			resultBean.setIsSuccess(false);
			resultBean.setMessage("抽奖错误");
		}
		logger.debug("getOrderByUserCode",resultBean);
		return resultBean;
	}
	
	
}
