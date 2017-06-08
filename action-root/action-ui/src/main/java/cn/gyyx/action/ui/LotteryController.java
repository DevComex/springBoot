/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：H!Guo
 * 联系方式：guohai@gyyx.cn 
 * 创建时间： 2014年12月30日下午8:01:48
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 抽奖Controller
-------------------------------------------------------------------------*/
package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping(value = "/lottery")
public class LotteryController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LotteryController.class);
	/**
	 * 默认活动页面
	 * @return
	 */
	@RequestMapping("/")
	public String home(){
		return "";
	}
	
	/**
	 * 抽奖开始
	 * @param parm
	 * @return
	 */
	@RequestMapping("/start")
	@ResponseBody
	public ResultBean<String> start(ContrParmBean parm,HttpServletRequest request){
		logger.info("活动的信息", parm);
		ResultBean<String> result =new ResultBean<String>(false,"none",null);
		//获取用户登录情况
		
		UserInfo userInfo = SignedUser.getUserInfo(request);
		logger.info("从request中获取的用户信息", userInfo);
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", "");

			return result;
		}
		logger.info("返回的结果集", result);
		
		return result;
	}
}
