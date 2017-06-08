/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年4月17日上午10:43:17
 * 版本号：v1.0
 * 本类主要用途叙述：抽奖的controller
 */

package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.service.dnelottery.DnelotteryService;
import cn.gyyx.core.Ip;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/dnelottery")
public class DNELotteryController {
	private DnelotteryService lottery = new DnelotteryService();
	private PrizeBean noprizeBean = new PrizeBean(1234, "谢谢参与", "thank", 184,
			"noRealPrize", 123);
	private int actionCode =184;
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(DNELotteryController.class);
	/**
	 * 跳转到中奖界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/lottery")
	public String toLottery(Model model) {
		return "dnelottery/dnelottery";
	}
	/**
	 * 抽奖
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPrize", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<UserLotteryBean> getPrize(HttpServletRequest request) {
		ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();
		//判断时间
		resultBean.setProperties(
				activityConfigBll.getConfigMessage("龙云精灵礼包抽奖20150416")
						.getIsSuccess(),
				activityConfigBll.getConfigMessage("龙云精灵礼包抽奖20150416")
						.getMessage(), null);
		if (!activityConfigBll.getConfigMessage("龙云精灵礼包抽奖20150416")
				.getIsSuccess()) {
			logger.info("result", resultBean);
			return resultBean;
		}
		try {
			resultBean = lottery.userLottery(actionCode,123,"byChance",
					Ip.getCurrent(request).asString(), "未知服务器", 0,
					Ip.getCurrent(request).asString(), noprizeBean);
			if(resultBean.getIsSuccess()){
	        if(resultBean.getData().getPrizeEnglish().equals("thank")){
	        
	        }
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
			resultBean.setProperties(false, "未中奖", null);
		}
			
		logger.info("getPrize", resultBean);
		return resultBean;

	}

}
