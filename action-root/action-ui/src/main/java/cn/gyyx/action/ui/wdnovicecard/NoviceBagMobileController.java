package cn.gyyx.action.ui.wdnovicecard;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.service.wdnovicecard.WDNoviceCommonService;
import cn.gyyx.action.service.wdnovicecard.WDNoviceCommonUtil;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Throwables;

@Controller
@RequestMapping("/MoLogin")
public class NoviceBagMobileController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoviceBagMobileController.class);
	private WDNoviceCommonService commonService = new WDNoviceCommonService();
	
	/**
	 * 注册
	 */
	@RequestMapping("/reg")
	public String index() {
		return "wd2999/bag/bagWapRegist";
	}

	/**
	 * 兑换礼包页面
	 */
	@RequestMapping("/pre")
	public String pre() {
		return "wd2999/bag/bagWapGift";
	}

	/**
	 * 注册
	 */
	@RequestMapping("/regist")
	@ResponseBody
	public ResultBean<String> regist(String phone, String password,String verifyCode, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			request.setAttribute("registSource", WDNoviceCommonUtil.REGIST_SOURCE_M_GW);
			return commonService.wapBagRegist(uuid,phone,password,verifyCode,request,response);
		} catch (Exception e) {
			logger.error("wap官网注册失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
			return new ResultBean<String>(false,"注册失败","");
		}
	}
	
	/**
	 * 首次注册成功获取奖品接口
	 */
	@RequestMapping("/obtain")
	@ResponseBody
	public ResultBean<String> obtainPresent(
			int serverCode, HttpServletRequest request) {
		NoviceParameter p = new NoviceParameter();
		p.setHdName("问道移动官网29992017");
        p.setVirtualItemName("2999 2017新手礼包");
        ResultBean<String> result = null;
        try {
        	String uuid = UUID.randomUUID().toString().replace("-", "");
        	result = commonService.obtainPresent(uuid,p,serverCode,request);
        	if(result != null && result.getIsSuccess()){
    			result.setMessage("恭喜您获得了" + result.getMessage()+"，请电脑登录问道官网wd.gyyx.cn ，下载问道PC客户端，安装后进入游戏内领取，价值2999元，保送140级！");
    		}
			return result;
		} catch (Exception e) {
			logger.error("wap官网领取礼包失败,错误堆栈:{}",Throwables.getStackTraceAsString(e));
			return new ResultBean<String>(false,"领取礼包失败","");
		}
	}

}
