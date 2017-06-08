package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.service.lhzsgoldback.GoldBackService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 版 权：光宇游戏 
 * 作 者：ChengLong 
 * 创建时间：2016年12月15日 下午7:18:18 
 * 描 述：灵魂战神金币返回
 */
@Controller
@RequestMapping(value = "/lhzs/goldreturn")
public class LhzsGoldReturnController {
	private Logger logger = GYYXLoggerFactory.getLogger(getClass());
	private GoldBackService goldBackService = new GoldBackService();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	
	/**
	 * 活动首页
	 */
	@RequestMapping("")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo != null) {
				model.addAttribute("account", userInfo.getAccount());		
			}
		} catch (Exception e) {
			logger.error("灵魂战神金币返还活动:获取登录信息,错误堆栈:{}", Throwables.getStackTraceAsString(e));
		}
		return "lhzs_goldreturn/index";
	}

	/**
	 * 查询剩余金币数
	 */
	@RequestMapping(value = "/getremaingold", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> getgold(String account,String pwd,String captcha,
			HttpServletRequest request,HttpServletResponse response, Model model) {
		logger.info("灵魂战神金币返还活动:查询剩余金币数,account:{},pwd:{}",new Object[]{account,pwd});
		try {
			if (StringUtils.isBlank(account)) {
				return new ResultBean<>(false, "社区账号不能为空", "");
			}
			if (StringUtils.isBlank(pwd)) {
				return new ResultBean<>(false, "社区密码不能为空", "");
			}
			if (!new Captcha(request, response).equals(captcha)) {
				return new ResultBean<>(false, "验证码输入有误", "");
			}
			//判断活动是否开始或者结束
			ResultBean<ActivityConfigBean> configResult = activityConfigBll.getConfigMessage(GoldBackService.HD_NAME);
			if (!configResult.getIsSuccess()) {
				return new ResultBean<String>(false,configResult.getMessage(),"");
			}
			return goldBackService.getgold(account,pwd);
		} catch (Exception e) {
			logger.error("灵魂战神金币返还活动:查询剩余金币数失败,错误堆栈:{}", Throwables.getStackTraceAsString(e));
			return new ResultBean<>(false, "查询剩余金币数失败", "");
		}
	}

	/**
	 * 提交申请记录
	 */
	@RequestMapping(value = "/insertapply", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> insertapply(String account,String pwd,HttpServletRequest request, Model model) {
		logger.info("灵魂战神金币返还活动:提交申请记录");
		try {
			if (StringUtils.isBlank(account)) {
				return new ResultBean<>(false, "社区账号不能为空", "");
			}
			if (StringUtils.isBlank(pwd)) {
				return new ResultBean<>(false, "社区密码不能为空", "");
			}
			// 判断活动是否开始或者结束
			ResultBean<ActivityConfigBean> configResult = activityConfigBll.getConfigMessage(GoldBackService.HD_NAME);
			if (!configResult.getIsSuccess()) {
				return new ResultBean<>(false, configResult.getMessage(), "");
			}
			return goldBackService.insertapply(account,pwd,getIpAddress(request));
		} catch (Exception e) {
			logger.error("灵魂战神金币返还活动:提交申请失败,错误堆栈:{}", Throwables.getStackTraceAsString(e));
			return new ResultBean<>(false, "提交申请失败", "");
		}
	}
	

        public static String getIpAddress(HttpServletRequest request) {
                String ip = request.getHeader("x-forwarded-for");
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("X-Real-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getRemoteAddr();
                }
                if (ip != null && ip.contains(",")) {
                        ip = ip.split(",")[0];
                }
                return ip;
        }

}
