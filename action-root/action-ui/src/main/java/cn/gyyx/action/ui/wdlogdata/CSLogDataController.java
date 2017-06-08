/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @作者：liqiang
 * @联系方式：liqiang@gyyx.cn
 * @创建时间： 2015年11月11日
 * @版本号：v1.0
 * @本类主要用途描述：创世公用安装卸载用户信息统计
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wdlogdata;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.wdlogdata.WdLogDataBean;
import cn.gyyx.action.service.wdlogdata.WdLogDataService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 安装与卸载创世LOG数据的Controller
 */
@Controller
@RequestMapping(value = "/datacontroller")
public class CSLogDataController {
	private WdLogDataService wdLogDataService = new WdLogDataService();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CSLogDataController.class);

	/**
	 * 保存用户安装问道程序信息，acc为玩家账号，ip为玩家ip地址，mac为玩家mac地址，ver为程序版本号,
	 * timestamp为unix时间戳,sign为MD5加密的验证标签
	 * (加密部分:acc=x&gameid=x&ip=x&mac=x&timestampver=x&ver=x123456)
	 * */
	@RequestMapping(value = "/install", method = RequestMethod.GET)
	@ResponseBody
	public String saveWdInstallLogData(
			WdLogDataBean log,
			@RequestParam(value="timestamp",required=false) String timestamp,
			@RequestParam(value="sign",required=false) String sign,
			@RequestParam(value="sign_type",required=false) String sign_type,HttpServletRequest  request) {		
		logger.info("ip:" + log.getIp()+ ",mac:" + log.getMac() + ",ver:" + log.getVer() + ",acc:" + log.getAcc() + ",gameid:" + log.getGameid() + ",timestamp:"
				+ timestamp + ",sign:" + sign+",sign_type:"+sign_type);
		/* int wd_log_type 区分安装与卸载1代表此条log记录的是安装问道的相关数据， 卸载则为2 */
		log.setTime(new Date());
		log.setType(1);
		wdLogDataService.saveLogData(log, timestamp, sign,sign_type,request);
		return "success";
	}

	/**
	 * @param wdLog
	 * @return
	 */
	@RequestMapping(value = "/uninstall", method = RequestMethod.GET)
	@ResponseBody
	public String recordUninstall(
			WdLogDataBean log,
			@RequestParam(value="timestamp",required=false) String timestamp,
			@RequestParam(value="sign",required=false) String sign,
			@RequestParam(value="sign_type",required=false) String sign_type,HttpServletRequest  request) {
		
		logger.info("ip:" + log.getIp()+ ",mac:" + log.getMac() + ",ver:" + log.getVer() + ",acc:" + log.getAcc() + ",gameid:" + log.getGameid() + ",timestamp:"
				+ timestamp + ",sign:" + sign+",sign_type:"+sign_type);
		log.setTime(new Date());
		log.setType(2);
		wdLogDataService.saveLogData(log, timestamp, sign,sign_type,request);
		return "success";
	}
}
