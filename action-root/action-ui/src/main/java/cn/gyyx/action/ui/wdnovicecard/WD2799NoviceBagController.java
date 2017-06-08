package cn.gyyx.action.ui.wdnovicecard;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.wd9yearnovicebag.BagActivityConfigBean;
import cn.gyyx.action.beans.wd9yearnovicebag.ServerBean;
import cn.gyyx.action.bll.wd9yearnovicebag.BagActivityConfigBll;
import cn.gyyx.action.bll.wd9yearnovicebag.ServerConfigBLL;
import cn.gyyx.action.service.wdClassicTenYears.ClassicTenYearsNoviceBagService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 版 权：光宇游戏
 * 作 者：ChengLong 
 * 创建时间：2016年8月17日 下午12:49:24 
 * 描 述：问道新手礼包2799-controller
 */
@Controller
@RequestMapping("/wd2799bag")
public class WD2799NoviceBagController {
	private static final Integer gameId = 2;
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WD2799NoviceBagController.class);
	private ClassicTenYearsNoviceBagService noviceBagService = new ClassicTenYearsNoviceBagService();
	private BagActivityConfigBll bagActivityConfigBll = new BagActivityConfigBll();
	private ServerConfigBLL serverConfigBLL = new ServerConfigBLL();

	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		return "wd2799/bag/bagPcIndex";
	}

	/**
	 * 获得服务器列表
	 */
	@RequestMapping("/getServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(String hdName, Model model) {
		logger.info("hdName", hdName);
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>();
		// Map<ServerBean, List<ActionServerConfigBean>> serverMap = null;
		List<ServerBean> server = null;
		try {
			ResultBean<BagActivityConfigBean> resultParameter = bagActivityConfigBll
					.getConfigMessage(hdName);

			if (resultParameter.getIsSuccess() == false) {
				result.setMessage(resultParameter.getMessage());
			} else {
				// 通过配置表读取活动Id
				Integer activityId = resultParameter.getData().getCode();

				server = serverConfigBLL.getServerConfig(activityId);
				result.setData(server);
				result.setIsSuccess(true);
				result.setMessage("获取服务器列表成功");
			}
		} catch (Exception e) {
			result.setMessage("系统维护，请稍后重试。");
			logger.error("获取服务器列表失败");
		}
		return result;

	}

	/**
	 * 领取礼包
	 */
	@RequestMapping("/receive")
	@ResponseBody
	public ResultBean<String> receive(HttpServletRequest request,
			NoviceParameter parameter, HttpServletResponse response) {
		long t1 = System.currentTimeMillis();
		Cookie[] cookies = request.getCookies();
		logger.info(String.valueOf(cookies));
		parameter.setGameId(gameId);
		logger.info(String.format(
				"receive方法输入参数为hdName:{},server:{},batchNo{}",
				parameter.getHdName(), parameter.getServerId(),
				parameter.getBatchNo()));
		ResultBean<String> result = new ResultBean<String>();
		// 接收Cookie中的信息
		UserInfo userInfo = SignedUser.getUserInfo(request);
		parameter.setUserInfo(userInfo);
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", "");
			logger.info("message" + result.getMessage());
			return result;
		}
		parameter.setUserInfo(userInfo);
		parameter.setAccount(userInfo.getAccount());
		result = noviceBagService.sendNoviceBag(parameter, request);
		long t2 = System.currentTimeMillis();
		logger.debug("Digester for receive function" + (t2 - t1));
		logger.info("result", result);
		return result;
	}
}
