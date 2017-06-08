package cn.gyyx.action.ui.lottery;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.po.ActionLotteryLogPO;
import cn.gyyx.action.ui.BaseController;

@Controller
@RequestMapping("/lottery")
public class PrizesInfoController extends BaseController {

	@ResponseBody
	@RequestMapping(value = "/y/prizesinfo/get", method = RequestMethod.GET)
	public ResultBean<ActionLotteryLogPO> get(int activityId, 
			HttpServletRequest request) {
		ResultBean<ActionLotteryLogPO> result = new ResultBean<ActionLotteryLogPO>();
		result.setIsSuccess(false);
		result.setMessage("活动已结束！");
		return result;
	}
}
