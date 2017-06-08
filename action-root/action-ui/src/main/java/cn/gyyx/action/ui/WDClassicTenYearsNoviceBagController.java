/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年4月5日
       Note：
************************************************/
package cn.gyyx.action.ui;

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
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

/**
 * @ClassName: WDClassicTenYearsNoviceBagController
 * @Description: TODO 2699经典十年礼包控制器
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年4月5日 下午8:38:55
 */
@Controller
@RequestMapping("/WD2699Bag")
public class WDClassicTenYearsNoviceBagController {
	private static final Integer gameId = 2;
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDSevenActivation.class);
	private ClassicTenYearsNoviceBagService noviceBagService = 
									new ClassicTenYearsNoviceBagService();
	private BagActivityConfigBll bagActivityConfigBll = new BagActivityConfigBll();
	private ServerConfigBLL serverConfigBLL = new ServerConfigBLL();
	
	@RequestMapping("index")
	public String index(){
		return "wdClassicTenYears/bagIndex";
	}
	
	/**
	 * 
	* @Title: getServers1
	* @Description: TODO 获得服务器列表
	* @date 2016年4月5日 下午8:44:09
	* @param hdName
	* @param model
	* @return    
	* @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping("/getServers")
	@ResponseBody
	public  ResultBean<List<ServerBean>> getServers1(
			String hdName, Model model) {
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
	* @Title: receive
	* @Description: TODO 领取礼包
	* @date 2016年4月5日 下午8:44:37
	* @param request
	* @param parameter
	* @param response
	* @return    
	* @return ResultBean<String>
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
		result = noviceBagService.sendNoviceBag(parameter,request);
		long t2 = System.currentTimeMillis();
		logger.debug("Digester for receive function" + (t2 - t1));
		logger.info("result", result);
		return result;
	}
}
