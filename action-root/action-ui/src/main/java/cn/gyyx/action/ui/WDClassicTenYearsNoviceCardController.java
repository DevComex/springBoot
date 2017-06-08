/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年4月5日
       Note：
************************************************/
package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerBean;
import cn.gyyx.action.bll.novicecard.ServerBLL;
import cn.gyyx.action.service.wdClassicTenYears.ClassicTenYearsNoviceCardService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;

/**
 * @ClassName: WDClassicTenYearsNoviceCardController
 * @Description: TODO 2699经典十年新手卡控制器
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年4月5日 下午8:39:37
 */
@Controller
@RequestMapping("/WD2699Card")
public class WDClassicTenYearsNoviceCardController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDClassicTenYearsNoviceCardController.class);
	private ClassicTenYearsNoviceCardService noviceCardService = 
							new ClassicTenYearsNoviceCardService();
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("codeServer1", "s.gyyx.cn");
		model.addAttribute("codeServer2", "static.cn114.cn");
		return "wdClassicTenYears/cardIndex";
	}
	
	/**
	* @Title: getServers
	* @Description: TODO 获得服务器列表
	* @date 2016年4月5日 下午8:41:33
	* @param gameCategory
	* @param netType
	* @param batchNo
	* @return    
	* @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping("/getServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(Integer gameCategory,
			Integer netType, Integer batchNo) {
		logger.info("getServers function{}", batchNo);
		logger.info("gameCategory:", gameCategory);
		logger.info("netType", netType);
		ResultBean<List<ServerBean>> result = new ServerBLL()
				.getServerByGameIdAndState(gameCategory, true, netType, batchNo);
		logger.info("result", result);
		return result;
	}

	/**
	* @Title: receive
	* @Description: TODO 提交领取新手卡奖励
	* @date 2016年4月5日 下午8:42:35
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
		parameter.setGameId(2);
		logger.info("receive方法输入参数为hdName:" + parameter.getHdName()
				+ ",server:{" + parameter.getServerId() + "},batchNo{"
				+ parameter.getBatchNo() + "}");
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
		// 验证验证码
		logger.info("用户输入的验证码为{}", parameter.getCode());
		if (!new Captcha(request, response).equals(parameter.getCode())) {
			result.setProperties(false, "很抱歉，您的验证码填写不正确", "");
			return result;
		}
		result = noviceCardService.sendNoviceCard(parameter,request);
		long t2 = System.currentTimeMillis();
		logger.debug("Digester for receive function" + (t2 - t1));
		logger.info("控制器提交领取新手卡奖励返回页面前result参数信息:isSuccess,message,data");
		logger.info("ResultBean<String>:" + result);
		logger.info("isSuccess:" + result.getIsSuccess());
		logger.info("message:" + result.getMessage());
		logger.info("data:" + result.getData());
		return result;
	}
}
