package cn.gyyx.action.ui.wdsigned;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.wdsigned.IWdAppSignLogService;
import cn.gyyx.action.service.wdsigned.WdAppSignLogServiceImpl;
import cn.gyyx.action.ui.wdnationalday.WdNationalSignController;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 问道app签到
 * 
 * @ClassName: WenDaoSignedController
 * @description WenDaoSignedController
 * @author luozhenyu
 * @date 2016年11月16日
 */
@Controller
@RequestMapping("/wdappsign")
public class WenDaoSignedController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdNationalSignController.class);
	private IWdAppSignLogService wdAppSignLogService = new WdAppSignLogServiceImpl();

	@RequestMapping("/index")
	public String showPhotoDetail(Model model, HttpServletRequest request, HttpServletResponse response) {

		return "wdappsign/check";
	}

	@RequestMapping(value = "/sign", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> sign(String qrCodeContent, String a, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("二维码信息：{}   appAccount", qrCodeContent, a);

		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "签到失败", null);
		if (StringUtils.isEmpty(qrCodeContent)) {
			resultBean.setProperties(false, "请传二维码", null);
			return resultBean;
		}
		try {
			return wdAppSignLogService.sign(qrCodeContent, a);
		} catch (Exception e) {
			logger.error("问道app签到[签到失败]异常", e);
		}
		return resultBean;
	}

	@RequestMapping(value = "/signlist", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> signList(String account, String serverName, HttpServletRequest request,
			HttpServletResponse response) {
	    
		logger.info("展示签到列表");
		
		Map<String, Object> result = new HashMap<>();

		if (StringUtils.isEmpty(account) || "null".equals(account) || StringUtils.isEmpty(serverName) || "null".equals(serverName)) {
			result.put("isSuccess", false);
			result.put("message", "account或serverName不能为空");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		try {
            int serverId = wdAppSignLogService.getRegion(serverName);
            
		    Date start = new Date();
		    int i=1;
		    while(true){
                
                try {
                     response.getOutputStream().flush();
                } finally {
                    // 用于当客户端断开连接时，抛出异常 break 循环
                }

	            result = wdAppSignLogService.signList(account, serverId);
	            
	            if(!(Boolean)result.get("isSuccess")){ // 获取失败，直接返回
	                return new ResponseEntity<>(result, HttpStatus.OK);
	            }
	            
	            if(!(Boolean) result.get("isConnect")){ // 今日已签到，无需等待结果
                    return new ResponseEntity<>(result, HttpStatus.OK);
	            }
	            
	            int sleepTime = i++*1000;
	            if(new Date().getTime() + sleepTime > start.getTime()){ // 由于 list 体验不好，不循环
	                break;
	            }
	            
		        Thread.sleep(sleepTime);
		    }
		} catch (Exception e) {
			logger.error("问道app签到[签到失败]异常", e);
			result.put("isSuccess", false);
			result.put("message", "获取签到列表失败");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/qrcode", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> getQrCode(String s, String account, String serverId, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("--------------获取二维码  account:{}  server:{}", account, s);
		ResultBean<String> resultBean = new ResultBean<>();
		String qrCode = wdAppSignLogService.getQrCode();
		if (StringUtils.isEmpty(s) || StringUtils.isEmpty(account) || StringUtils.isEmpty(serverId)) {
			resultBean.setProperties(false, "s，account，serverId不能为空", null);
			return resultBean;
		}
		if (StringUtils.isEmpty(qrCode)) {
			resultBean.setProperties(false, "获取二维码失败", null);
			return resultBean;
		}
		String result = String.format("%s&s=%s&a=%s&id=%s", qrCode, s, account, serverId);
		resultBean.setProperties(true, "获取二维码成功", result);
		return resultBean;
	}

	@RequestMapping(value = "/sendgift", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<List<String>> sendGift(String serverName, String account, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("发送礼物接口   serverName:{}   account:{}", serverName, account);
		ResultBean<List<String>> resultBean = new ResultBean<>();
		if ((StringUtils.isEmpty(account) || "null".equals(account))
				|| (StringUtils.isEmpty(serverName) || "null".equals(serverName))) {
			resultBean.setProperties(false, "account或serverName不能为空", null);
			return resultBean;
		}
		int serverId = wdAppSignLogService.getRegion(serverName);
		List<String> result = wdAppSignLogService.sendGift(serverId, account);
		if (result == null || result.size() == 0) {
			resultBean.setProperties(false, "领取奖品失败", null);
			return resultBean;
		}
		resultBean.setProperties(true, "领取奖品成功", result);
		return resultBean;
	}
}
