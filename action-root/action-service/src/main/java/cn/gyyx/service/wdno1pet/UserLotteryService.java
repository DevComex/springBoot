/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月25日下午2:44:00
 * 版本号：v1.0
 * 本类主要用途叙述：用户抽奖的service层
 */

package cn.gyyx.service.wdno1pet;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdno1pet.UserLotteryBean;
import cn.gyyx.action.bll.wdno1pet.UserLotteryBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class UserLotteryService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(UserLotteryService.class);

	public ResultBean<UserLotteryBean> userLottery(int userCode) {
		logger.debug("userCode", userCode);
		ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();
		UserLotteryBean userLotteryBean = new UserLotteryBean();
		UserLotteryBLL userLotteryBLL = new UserLotteryBLL();

		int errorCode = userLotteryBLL.setUserLotteryType(userCode);
		switch (errorCode) {
		// 抽奖成功
		case 0:

			// 获得当前中奖的信息
			userLotteryBean = userLotteryBLL.getUserLotteryByUserCode(userCode);
			logger.debug("userLotteryBean", userLotteryBean);
			// 获得奖品的代号
			userLotteryBean.setUserLotteryTypeValues(userLotteryBLL
					.setLotteryValues(userLotteryBean));
			logger.debug("userLotteryBean", userLotteryBean);
			/**
			 * 设定中文字符
			 */
			userLotteryBean.setUserLotteryTypeChinese(userLotteryBLL
					.setLotteryChinese(userLotteryBean));
			logger.debug("userLotteryBean", userLotteryBean);
			String messageString = userLotteryBean.getUserLotteryTypeChinese();

			// 传值
			resultBean.setProperties(true, messageString, userLotteryBean);
			break;
		// 已经抽过奖
		case 1:
			messageString = "您已经抽过奖了";
			resultBean.setProperties(false, messageString, userLotteryBean);

			break;
		case 2:
			messageString = "您没有抽奖资格";
			resultBean.setProperties(false, messageString, userLotteryBean);
			break;
		case 3:
			messageString = "出现错误";
			resultBean.setProperties(false, messageString, userLotteryBean);
			break;

		default:
			messageString = "出现错误";
			resultBean.setProperties(false, messageString, userLotteryBean);
			break;
		}
		logger.debug("userLottery resultBean", resultBean);
		return resultBean;

	}

}
