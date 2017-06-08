package cn.gyyx.action.ui.wdnovicecard;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.service.wdnovicecard.WDNoviceCommonService;
import cn.gyyx.action.service.wdnovicecard.WDNoviceCommonUtil;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Throwables;

@Controller
@RequestMapping("/noviceCardMobile")
public class NoviceCardMobileController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(NoviceCardMobileController.class);
	
	private WDNoviceCommonService commonService = new WDNoviceCommonService();
	WDNoviceCommonUtil noviceCommonUtil = new WDNoviceCommonUtil();
	
	@RequestMapping("/index")
	public String index() {
		return "wd2999/card/cardWapIndex";
	}
	
	@RequestMapping("/loginIndex")
	public String loginIndex() {
		return "wd2999/card/loginIndex";
	}
	
	@RequestMapping("/receiveGiftIndex")
	public String receiveGiftIndex(String userType,Model model) {
		model.addAttribute("userType", userType);
		return "wd2999/card/receiveGiftIndex";
	}
	
	@RequestMapping("/registIndex")
	public String registIndex() {
		return "wd2999/card/regist";
	}
	
	/**
	 * 注册
	 */
	@RequestMapping("/regist")
	@ResponseBody
	public ResultBean<String> regist(String userType, String accountName,String password,
			String smsCode,String imgCode, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			request.setAttribute("registSource", WDNoviceCommonUtil.REGIST_SOURCE_M_MT);
			return commonService.wapCardRegist(uuid,userType, accountName,password,
					smsCode,imgCode, request, response);
		} catch (Exception e) {
			logger.error("wap官网注册失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
			return new ResultBean<String>(false,"注册失败","");
		}
	}
	
	/**
	 * 移动端-领取新手卡
	 */
	@RequestMapping(value="/receive",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> wapreceive(NoviceParameter parameter, String source,HttpServletRequest request,HttpServletResponse response) {
		try{
			if (StringUtils.isBlank(source)) {
				return new ResultBean<>(false,"领取来源不能为空","");
			}
			if (!source.equals("login") && !source.equals("regist")) {
				return new ResultBean<>(false,"领取来源不正确","");//注册或者是登录领取 
			}
			if (parameter.getServerId() == 0) {
				return new ResultBean<>(false,"服务器不能为空","");
			}
			if (StringUtils.isBlank(parameter.getCard())) {
				return new ResultBean<>(false,"新手卡卡号不能为空","");
			}
			if (StringUtils.isBlank(parameter.getCode())) {
				return new ResultBean<>(false,"验证码不能为空","");
			}
			if (StringUtils.isBlank(parameter.getHdName())) {
				return new ResultBean<>(false,"活动名称不能为空","");
			}
			if (parameter.getBatchNo() == 0 ) {
				return new ResultBean<>(false,"批次号不能为空","");
			}
			
			if (WDNoviceCommonUtil.isRelease && !new Captcha(request, response).equals(parameter.getCode())) {
				return new ResultBean<>(false,"很抱歉，您的验证码填写不正确","");
			}
			
			//获取用户信息
			UserInfo userInfo = null;
			if (source.equals("login")) {
				userInfo = SignedUser.getUserInfo(request);
				if (userInfo == null) {
					return new ResultBean<>(false,"很抱歉，您的登陆超时","");
				}
				parameter.setUserInfo(userInfo);
				parameter.setAccount(userInfo.getAccount());
			}else{
				noviceCommonUtil.setUserInfoFromWapNoviceCard(request, parameter);
				if (parameter.getUserInfo() == null) {
					return new ResultBean<>(false,"注册后请立即领取，您已经错过领取时间","");
				}
			}
			parameter.setGameId(2);
			String uuid = UUID.randomUUID().toString().replace("-", "");
			ResultBean<String> result = commonService.receiveInPcOfCard(parameter, request,uuid);
			if(result != null && result.getIsSuccess()){
				result.setMessage("新手卡领取成功，请进入游戏，10级后到天墉城南极仙翁处领取。");
			}
			return result;
		}catch(Exception e){
			logger.error("wap官网注册失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
			return new ResultBean<String>(false,"领取新手卡卡失败","");
		}
	}
}
