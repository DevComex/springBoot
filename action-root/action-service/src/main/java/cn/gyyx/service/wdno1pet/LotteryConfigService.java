/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月17日下午3:27:43
 * 版本号：v1.0
 * 本类主要用途叙述:中奖配置的service
 */

package cn.gyyx.service.wdno1pet;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wdno1pet.LotteryConfigBean;
import cn.gyyx.action.bll.wdno1pet.LotteryConfigBLL;
import cn.gyyx.action.bll.wdno1pet.UserLotteryBLL;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LotteryConfigService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LotteryConfigService.class);

	/**
	 * 中奖信息的配置
	 */
	public void LotteryConfig() {
		LotteryConfigBean lBean = new LotteryConfigBean();
		UserLotteryBLL uBll = new UserLotteryBLL();
		logger.debug("UserLotteryBLL",uBll);
		int num = uBll.getNumberAlllucky();
		if (num < 1000) {
			num = 1000;
		}
		LotteryConfigBLL lBll = new LotteryConfigBLL();
		// 如果表中有记录，先清空
		if (!lBll.isEmpty()) {
			lBll.cleanLottery();
		}
		String[] rule = lBll.RuleLotter(num);
		logger.debug("rule",rule);
		for (int i = 1; i <= num; i++) {
			// 位置有奖
			if (rule[i] != null) {
				lBean.setLotterOrder(i);
				lBean.setLotterType(rule[i]);
				lBll.setLotteryConfig(lBean);
			}

		}

	}

}
