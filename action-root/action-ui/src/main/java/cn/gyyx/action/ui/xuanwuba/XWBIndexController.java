/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：V1.214
 * @本类主要用途描述:首页显示控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.xuanwuba;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ResultIndexShowBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ResultSignBasicInfo;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PraiseBLL;
import cn.gyyx.action.service.common.OaCommonService;
import cn.gyyx.action.service.xuanwuba.XWBService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping(value = "/xuanwuba")
public class XWBIndexController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XWBIndexController.class);
	private XWBService XWBService = new XWBService();
	private OaCommonService oaCommonService = new OaCommonService();
	private int actionCode = 288;

	/**
	 * 进入首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String index() {
		return "xuanwuba/index";
	}
	

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: index
	 * @Description: TODO 首页
	 * @param model
	 * @return String
	 */
	@RequestMapping("/lotteryIndex")
	public String index(Model model) {
		return "xuanwuba/wb_lottery";
	}
	
	@RequestMapping("/exchangeIndex")
	public String exchange(Model model) {
		return "xuanwuba/wb_exchange";
	}

	/**
	 * 素材首页显示控制器
	 * 
	 * @param materialType
	 *            ：素材类别
	 * @return
	 */
	@RequestMapping(value = "/materialShow", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String materialShow(String materialType,HttpServletRequest request) {
		ResultBean<ResultSignBasicInfo> result = new ResultBean<ResultSignBasicInfo>();
		logger.info("炫舞吧积分活动————素材首页显示控制器，参数：" + materialType);
		if ("video".equals(materialType)) {
			materialType = "视频";
		}
		if ("close".equals(materialType)) {
			materialType = "服装";
		}
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		String userAccount = "";
		// 获取失败，返回为空
		if (userInfo == null) {
			userAccount = "";
		}else{
			// 获得该用户的信息
			userAccount = userInfo.getAccount();
		}
		
		ResultIndexShowBean resultIndexShowBean = XWBService
				.getmaterialShow(materialType,userAccount);
		return DataFormat.objToJson(resultIndexShowBean);
	}
	
	/**
	 * 返回账号信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserLoginAccount", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getUserLoginAccount(HttpServletRequest request) {
		ResultBean<String> result = new ResultBean<String>();
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		String userAccount = "";
		// 获取失败，返回为空
		if (userInfo == null) {
			userAccount = "登录超时";
		}else{
			// 获得该用户的信息
			userAccount = userInfo.getAccount();
			if(0 < userAccount.length() && userAccount.length() <= 3){
				userAccount = String.valueOf(userAccount.charAt(0))+matchStr(userAccount.length()-1);
			}else if(userAccount.length() == 4){
				userAccount = String.valueOf(userAccount.charAt(0))+matchStr(userAccount.length()-2)+String.valueOf(userAccount.charAt(userAccount.length()-1 ));
			}else if(userAccount.length() >=5){
				userAccount = userAccount.substring(0, 2)+matchStr(userAccount.length()-4)+userAccount.substring(userAccount.length()-2, userAccount.length());
			}else{
				
			}
		}
		result.setProperties(true, "成功！", userAccount);
		logger.info("炫舞吧XWBIndexController-getUserLoginAccount-获取账号信息：{}"+userAccount);
		return DataFormat.objToJson(result);
	}
	public static String matchStr(int num){
		String str = "";
		for(int i = 0;i < num;i++){
			str += "*";
		}
		return str;
	}
}
