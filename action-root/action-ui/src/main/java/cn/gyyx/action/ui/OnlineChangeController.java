/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：online-change
 * @作者：fanyongliang
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年9月28日
 * @版本号：
 * @本类主要用途描述：创世online光宇币兑换控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.online.OnlineChangeBean;
import cn.gyyx.action.beans.online.OnlineChangeLogBean;
import cn.gyyx.action.bll.online.OnlineChangeBll;
import cn.gyyx.action.bll.online.OnlineChangeLogBll;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.captcha.Captcha;

@Controller
@RequestMapping(value = "/online")
public class OnlineChangeController {
	private OnlineChangeBll onlineChangeBll = new OnlineChangeBll();
	private OnlineChangeLogBll onlineChangeLogBll = new OnlineChangeLogBll();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(OnlineChangeController.class);

	/**
	 * 默认活动页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "online/index";
	}
	/**
	 * 检查是否可以申请
	 * @param account
	 * @param code
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String check(String account,String code,HttpServletRequest request,HttpServletResponse response,Model model) {
		ResultBean<String> result = new ResultBean<String>(false, "活动已经结束了！", "活动已经结束了！");
		Calendar overTime = Calendar.getInstance(); overTime.set(2016, 0, 25, 23, 59);
		if(new Date().getTime()<overTime.getTime().getTime()){
			logger.info("创世online光宇币兑换,account" + account);
			//判断验证码正确
			if (!new Captcha(request, response).equals(code)) {
				result.setProperties(false, "很抱歉，您的验证码填写不正确!", "");
				return DataFormat.objToJson(result);
			}
			// 用户资格查询
			OnlineChangeBean onlineBean = onlineChangeBll.getAccount(account);
			logger.info("创世online光宇币兑换,用户资格查询onlineBean:" + onlineBean);
			if (onlineBean == null) {
				result.setMessage("您输入的账号没有资格申请返还");
				return DataFormat.objToJson(result);
			}
			// 用户是否已将申请过
			OnlineChangeLogBean onlineChangeLogBean = onlineChangeLogBll
					.getonlineChangeLog(account);
			logger.info("创世online光宇币兑换,用户是否已将申请过onlineChangeLogBean:"
					+ onlineChangeLogBean);
			if (onlineChangeLogBean != null) {
				result.setMessage("您输入的账号已经申请过返还");
				return DataFormat.objToJson(result);
			}
			// 计算返还光宇币数量
			//用户元宝数
			int yuanbao = onlineBean.getYuanbao();
			if (yuanbao == 0) {
				result.setMessage("没有可返还的金元宝");
				return DataFormat.objToJson(result);
			}
			//可换光宇币数
			int gyyxCurrency = yuanbao % 100 == 0 ? yuanbao / 100
					: yuanbao / 100 + 1;
			logger.info("创世online光宇币兑换,计算返还光宇币数量:yuanbao:" + yuanbao
					+ "; gyyxCurrency:" + gyyxCurrency);
			result.setIsSuccess(true);
			result.setMessage("您拥有金元宝"+yuanbao+"，可返还"+gyyxCurrency+"光宇币");
		}
		return DataFormat.objToJson(result);
	}
	/**
	 * 提交兑换申请
	 * @param account
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String change(String account,HttpServletRequest request,HttpServletResponse response,Model model) {
		ResultBean<String> result = new ResultBean<String>(false, "活动已经结束了！", "活动已经结束了！");
		Calendar overTime = Calendar.getInstance(); overTime.set(2016, 0, 25, 23, 59);
		if(new Date().getTime()<overTime.getTime().getTime()){
			logger.info("创世online光宇币兑换,account" + account);
			// 用户资格查询
			OnlineChangeBean onlineBean = onlineChangeBll.getAccount(account);
			logger.info("创世online光宇币兑换,用户资格查询onlineBean:" + onlineBean);
			if (onlineBean == null) {
				result.setMessage("您输入的账号没有资格申请返还");
				return DataFormat.objToJson(result);
			}
			// 用户是否已将申请过
			OnlineChangeLogBean onlineChangeLogBean = onlineChangeLogBll
					.getonlineChangeLog(account);
			logger.info("创世online光宇币兑换,用户是否已将申请过onlineChangeLogBean:"
					+ onlineChangeLogBean);
			if (onlineChangeLogBean != null) {
				result.setMessage("您输入的账号已经申请过返还");
				return DataFormat.objToJson(result);
			}
			// 计算返还光宇币数量
			//用户元宝数
			int yuanbao = onlineBean.getYuanbao();
			if (yuanbao == 0) {
				result.setMessage("没有可返还的金元宝");
				return DataFormat.objToJson(result);
			}
			//可换光宇币数
			int gyyxCurrency = yuanbao % 100 == 0 ? yuanbao / 100
					: yuanbao / 100 + 1;
			logger.info("创世online光宇币兑换,计算返还光宇币数量:yuanbao:" + yuanbao
					+ "; gyyxCurrency:" + gyyxCurrency);
			onlineChangeLogBean = new OnlineChangeLogBean(account, yuanbao,
					gyyxCurrency, new Date());
			logger.info("创世online光宇币兑换,增加用户申请记录:onlineChangeLogBean:"
					+ onlineChangeLogBean);
			// 增加用户申请记录
			int falg = onlineChangeLogBll.addOnlineChangeLog(onlineChangeLogBean);
			logger.info("创世online光宇币兑换,增加用户申请记录:falg:" + falg);
			if (falg == 0) {
				result.setMessage("申请失败，请重试！！");
				return DataFormat.objToJson(result);
			}
			result.setIsSuccess(true);
			result.setMessage("申请已成功提交");
		}
		return DataFormat.objToJson(result);
	}

}
