package cn.gyyx.action.ui;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/GameServer")
public class WDGetServer {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDNineLotteryController.class);
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	/**
	 * 
	* @Title: getServers
	* @Description: TODO 获取服务器信息
	* @param @param typeCode
	* @param @return    
	* @return ResultBean<List<ServerBean>>    
	* @throws
	 */
	@RequestMapping(value = "/GetGameServer")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(
			@RequestParam(value = "netType") String netType) {
		logger.info("typeCode", netType);
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
				true, "查询服务器失败", null);
		result.setProperties(
				activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动")
						.getIsSuccess(),
				activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动")
						.getMessage(), null);
		if (!activityConfigBll.getConfigMessage("问道乾坤锁领取礼包活动")
				.getIsSuccess()) {
			logger.info("result", result);
			return result;
		}
		result.setProperties(true, "查询服务器失败", null);
		List<Value> serversList = callWebApiAgent
				.getAllServerByNetTypeCode(netType);
		List<ServerBean> serverList = new ArrayList<ServerBean>();
		for (Value value : serversList) {
			ServerBean server = new ServerBean(value.getCode(),
					value.getServerName());
			serverList.add(server);
		}
		result.setProperties(true, "查询服务器成功", serverList);
		logger.info("result", result);
		return result;
	}
	
}
