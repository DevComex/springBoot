/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年7月15日 
 * @版本号：v1.0
 * @本类主要用途描述：问道虚拟物品保险项目 控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.activemq.filter.function.inListFunction;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.insurance.BlackListBean;
import cn.gyyx.action.beans.insurance.GoPayParamBean;
import cn.gyyx.action.beans.insurance.LastLoginBean;
import cn.gyyx.action.beans.insurance.MyOrderParameterBean;
import cn.gyyx.action.beans.insurance.OrderBean;
import cn.gyyx.action.beans.insurance.OrderParameterBean;
import cn.gyyx.action.beans.insurance.OrderResultBean;
import cn.gyyx.action.beans.insurance.ReceiveReparationResultBean;
import cn.gyyx.action.beans.insurance.ReparationBean;
import cn.gyyx.action.beans.insurance.TempOrderUrl;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.bll.insurance.BlackListBLL;
import cn.gyyx.action.bll.insurance.GyyxGoPayBLL;
import cn.gyyx.action.bll.insurance.OrderBLL;
import cn.gyyx.action.bll.insurance.OrderParameterBLL;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.action.service.insurance.FroGYYXGO;
import cn.gyyx.action.service.insurance.GYYXGopayIntService;
import cn.gyyx.action.service.insurance.InsuranceService;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;

import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping(value = "/insurance")
public class WDInsuranceController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDInsuranceController.class);
	private InsuranceService insuranceService = new InsuranceService();
	private GYYXGopayIntService goPayIntService = new GYYXGopayIntService();
	private OrderBLL orderBLL = new OrderBLL();
	private BlackListBLL blackListBLL = new BlackListBLL();
	private OrderParameterBLL orderParameterBLL = new OrderParameterBLL();
	private GyyxGoPayBLL goPayBLL = new GyyxGoPayBLL();
	private MemcacheUtil memcacheUtil=new MemcacheUtil();
	XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil.getMemcache();

	/**
	 * 虚拟财产保险接口
	 * 
	 * @param status
	 * @param orderNum
	 * @param protection
	 * @param circle
	 * @param reparation
	 * @param other
	 * @param reparation_order_num
	 * @param reparation_result
	 * @param timestamp
	 * @param sign_type
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/claims", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String receiveReparation(String status, String orderNum,
			Float protection, Integer circle, Float reparation, String other,
			String reparation_order_num, String reparation_result,
			String timestamp, String sign_type, String sign,
			HttpServletRequest request) {
		logger.info("理赔接口被调开始————");
		logger.info("理赔接口被调传来的参数：" + "status（" + status + "）orderNum（"
				+ orderNum + "）protection（" + protection + "）circle（" + circle
				+ "）reparation（" + reparation + "）other（" + other
				+ "）reparation_order_num（" + reparation_order_num
				+ "）reparation_result（" + reparation_result + "）timestamp（"
				+ timestamp + "）sign_type（" + sign_type + "）sign（" + sign + "）");
		ReceiveReparationResultBean receiveReparationResultBean = new ReceiveReparationResultBean(
				false, false, "未知错误 ");
		String signStr = request.getParameter("sign");
		// 检查是否为空
		boolean flagNull = orderBLL.checkIsNull(status, orderNum, protection,
				circle, reparation, other, reparation_order_num,
				reparation_result, timestamp, sign_type, signStr);
		logger.info("理赔被调接口参数验空结果:" + flagNull);
		if (flagNull) {
			receiveReparationResultBean.setAll(true, false, "有参数没传");
			logger.info("理赔被调接口参数验空结束，返回");
			return DataFormat.objToJson(receiveReparationResultBean);
		}
		try {
			status = new String(status.getBytes("iso8859-1"), "utf-8");
			orderNum = new String(orderNum.getBytes("iso8859-1"), "utf-8");
			other = new String(other.getBytes("iso8859-1"), "utf-8");
			reparation_order_num = new String(
					reparation_order_num.getBytes("iso8859-1"), "utf-8");
			if (reparation_result != null) {
				reparation_result = new String(
						reparation_result.getBytes("iso8859-1"), "utf-8");
			}
			sign_type = new String(sign_type.getBytes("iso8859-1"), "utf-8");
			sign = new String(sign.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.debug("保险公司调用理赔接口——属性修改编码错误");
			e.printStackTrace();
		}
		// 验签
		FroGYYXGO froGyyxGo = new FroGYYXGO();
		String configValue = "";
		configValue = goPayBLL.getInsureKey("reparation_key");
		logger.info("理赔接口验签秘钥为：" + configValue);
		if (configValue == "") {
			logger.info("从数据库中查出的秘钥为空...");
		}
		boolean result = froGyyxGo.inspectSignDomain(signStr, request,
				configValue, "actionv2.gyyx.cn");
		if (!result) {
			receiveReparationResultBean.setAll(true, false, "验签失败！");
			logger.info("理赔接口验签失败，返回");
			return DataFormat.objToJson(receiveReparationResultBean);
		}

		// 如果验证存在和失效，不通过，则返回
		receiveReparationResultBean = insuranceService
				.checkExistOrNoefforder(orderNum);
		if (!(receiveReparationResultBean.isError() && receiveReparationResultBean
				.isIsSuccess())) {
			return DataFormat.objToJson(receiveReparationResultBean);
		}
		// 验证订单号的信息是否与本数据库信息相符
		logger.info("理赔接口验证订单号为" + orderNum + "的信息是否与本数据库order表中信息相符");
		ResultBean<OrderBean> orderResult = orderBLL.selectByOrderNum(orderNum);
		if (!orderResult.getIsSuccess()) {
			receiveReparationResultBean.setAll(true, false, "获取订单信息出错");
			logger.error("理赔接口获取订单号为" + orderNum + "的信息出错 ，返回");
			return DataFormat.objToJson(receiveReparationResultBean);
		}
		ReparationBean reparationBean = new ReparationBean(orderNum, null,
				null, protection, circle, reparation, other, new Date());
		logger.info("理赔接口验证传输来的参数是否与本数据库中的信息相符，参数包括：Protection、Circle");
		if (!orderBLL.checkSame(orderResult.getData(), reparationBean)) {
			logger.info("理赔接口验证参数的信息与本数据库信息不符，返回");
			receiveReparationResultBean.setAll(true, false, "参数的信息与本数据库信息不符");
			return DataFormat.objToJson(receiveReparationResultBean);
		}
		// 将订单的数据补全理赔Bean
		reparationBean.setReparationOrderNum(reparation_order_num);
		reparationBean.setServerGroup(orderResult.getData().getServerGroup());
		reparationBean.setType(orderResult.getData().getOrderType());
		reparationBean.setReparationResult(reparation_result);
		// 按状态分别进行操作
		// 1.处理中
		if ("reparation".equals(status)) {
			logger.info("理赔接口处理理赔处理中状态，开始。。。。。。");
			receiveReparationResultBean = insuranceService.whenReparation(
					orderNum, reparationBean);
			logger.info("理赔接口处理理赔处理中状态，结束，返回。。。。。。");
			return DataFormat.objToJson(receiveReparationResultBean);
		} else {
			// 其他状态
			logger.info("理赔接口处理理赔其他状态，开始。。。。。。");
			receiveReparationResultBean = insuranceService.whenEndreparation(
					orderNum, reparationBean, status, reparation);
			if ("failreparation_scam".equals(status)) {
				// 加入黑名单
				BlackListBean blackListBean = new BlackListBean(orderNum,
						orderResult.getData().getName(), orderResult.getData()
								.getPhone(),
						orderResult.getData().getAccount(), orderResult
								.getData().getIp());
				logger.info("理赔接口处理理赔失败（黑名单）状态--向黑名单表中加入"
						+ blackListBean.toString());
				blackListBLL.addBlackBean(blackListBean);
			}
			logger.info("理赔接口--被调结束");
			return DataFormat.objToJson(receiveReparationResultBean);
		}

	}

	/**
	 * 进入主页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String toIndex(Model model, HttpServletRequest request) {
		Integer allOrder = 0;
		Integer yesOrder = 0;
		Integer noOrder = 0;
		String timeString = "未知时间";
		String ip = "未知IP";
		LastLoginBean lastLoginBean = new LastLoginBean(ip, timeString);
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			// 获取上次登录信息
			lastLoginBean = CallWebServiceInsuranceAgent
					.getLastLoginInfo(userInfo.getUserId());
			// 信息为空的情况

			// 获取总保单数
			timeString = userInfo.getLastTime();
			ip = userInfo.getLastLocation();
			MyOrderParameterBean myOrderParameterBean1 = new MyOrderParameterBean();
			myOrderParameterBean1.setAccount(userInfo.getAccount());
			myOrderParameterBean1.setGameName("问道");
			allOrder = orderBLL.getCountOrder(myOrderParameterBean1);

			if (allOrder == null) {
				allOrder = 0;
			}
			// 获取有效保单
			MyOrderParameterBean myOrderParameterBean2 = new MyOrderParameterBean();
			myOrderParameterBean2.setGameName("问道");
			myOrderParameterBean2.setAccount(userInfo.getAccount());
			myOrderParameterBean2.setStatus("efforder");
			yesOrder = orderBLL.getCountOrder(myOrderParameterBean2);
			if (yesOrder == null) {
				yesOrder = 0;
			}
			// 获取失效保单数
			MyOrderParameterBean myOrderParameterBean3 = new MyOrderParameterBean();
			myOrderParameterBean3.setAccount(userInfo.getAccount());
			myOrderParameterBean3.setGameName("问道");
			myOrderParameterBean3.setStatus("noefforder");
			noOrder = orderBLL.getCountOrder(myOrderParameterBean3);
			if (noOrder == null) {
				noOrder = 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("toIndex", e.getClass().getSimpleName());
		}

		model.addAttribute("timeString", lastLoginBean.getTime());
		model.addAttribute("ip", lastLoginBean.getIp());

		model.addAttribute("allOrder", allOrder);
		model.addAttribute("yesOrder", yesOrder);
		model.addAttribute("noOrder", noOrder);
		return "wdinsurance/index";
	}

	/**
	 * 跳转投保到第一步界面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/step1")
	public String tostep1(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);

			if (insuranceService.getStepCookies(request, response,
					userInfo.getAccount() + "doinsurance")) {

				return "wdinsurance/step1";
			} else {
				Integer allOrder = 0;
				Integer yesOrder = 0;
				Integer noOrder = 0;
				String timeString = "未知时间";
				String ip = "未知IP";
				LastLoginBean lastLoginBean = new LastLoginBean(ip, timeString);
				lastLoginBean = CallWebServiceInsuranceAgent
						.getLastLoginInfo(userInfo.getUserId());
				try {
					// 获取总保单数
					MyOrderParameterBean myOrderParameterBean1 = new MyOrderParameterBean();
					myOrderParameterBean1.setAccount(userInfo.getAccount());
					myOrderParameterBean1.setGameName("问道");
					allOrder = orderBLL.getCountOrder(myOrderParameterBean1);

					if (allOrder == null) {
						allOrder = 0;
					}
					// 获取有效保单
					MyOrderParameterBean myOrderParameterBean2 = new MyOrderParameterBean();
					myOrderParameterBean2.setGameName("问道");
					myOrderParameterBean2.setAccount(userInfo.getAccount());
					myOrderParameterBean2.setStatus("efforder");
					yesOrder = orderBLL.getCountOrder(myOrderParameterBean2);
					if (yesOrder == null) {
						yesOrder = 0;
					}
					// 获取失效保单数
					MyOrderParameterBean myOrderParameterBean3 = new MyOrderParameterBean();
					myOrderParameterBean3.setAccount(userInfo.getAccount());
					myOrderParameterBean3.setGameName("问道");
					myOrderParameterBean3.setStatus("noefforder");
					noOrder = orderBLL.getCountOrder(myOrderParameterBean3);
					if (noOrder == null) {
						noOrder = 0;
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.info("toIndex", e.getMessage());
				}
				model.addAttribute("timeString", lastLoginBean.getTime());
				model.addAttribute("ip", lastLoginBean.getIp());
				model.addAttribute("allOrder", allOrder);
				model.addAttribute("yesOrder", yesOrder);
				model.addAttribute("noOrder", noOrder);
				return "wdinsurance/index";
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("tostep1 warn", e.getMessage());
			Integer allOrder = 0;
			Integer yesOrder = 0;
			Integer noOrder = 0;
			String timeString = "未知时间";
			String ip = "未知IP";
			LastLoginBean lastLoginBean = new LastLoginBean(ip, timeString);
			try {
				UserInfo userInfo = SignedUser.getUserInfo(request);

				lastLoginBean = CallWebServiceInsuranceAgent
						.getLastLoginInfo(userInfo.getUserId());
				// 获取总保单数
				MyOrderParameterBean myOrderParameterBean1 = new MyOrderParameterBean();
				myOrderParameterBean1.setAccount(userInfo.getAccount());
				myOrderParameterBean1.setGameName("问道");
				allOrder = orderBLL.getCountOrder(myOrderParameterBean1);

				if (allOrder == null) {
					allOrder = 0;
				}
				// 获取有效保单
				MyOrderParameterBean myOrderParameterBean2 = new MyOrderParameterBean();
				myOrderParameterBean2.setGameName("问道");
				myOrderParameterBean2.setAccount(userInfo.getAccount());
				myOrderParameterBean2.setStatus("efforder");
				yesOrder = orderBLL.getCountOrder(myOrderParameterBean2);
				if (yesOrder == null) {
					yesOrder = 0;
				}
				// 获取失效保单数
				MyOrderParameterBean myOrderParameterBean3 = new MyOrderParameterBean();
				myOrderParameterBean3.setAccount(userInfo.getAccount());
				myOrderParameterBean3.setGameName("问道");
				myOrderParameterBean3.setStatus("noefforder");
				noOrder = orderBLL.getCountOrder(myOrderParameterBean3);
				if (noOrder == null) {
					noOrder = 0;
				}
			} catch (Exception e2) {
				// TODO: handle exception
				logger.info("toIndex", e.getMessage());
			}
			model.addAttribute("timeString", lastLoginBean.getTime());
			model.addAttribute("ip", lastLoginBean.getIp());
			model.addAttribute("allOrder", allOrder);
			model.addAttribute("yesOrder", yesOrder);
			model.addAttribute("noOrder", noOrder);
			return "wdinsurance/index";
		}

	}

	/**
	 * 跳转到投保第二步界面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/step2")
	public String tostep2(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String timeString = "未知时间";
		String ip = "未知IP";
		LastLoginBean lastLoginBean = new LastLoginBean(ip, timeString);
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);

			// 判断标志
			if (insuranceService.getStepCookies(request, response,
					userInfo.getAccount() + "checksuccess")) {
				lastLoginBean = CallWebServiceInsuranceAgent
						.getLastLoginInfo(userInfo.getUserId());
				// 掩饰账号
				String nameString = userInfo.getAccount().substring(0, 2)
						+ "******"
						+ userInfo.getAccount().substring(
								userInfo.getAccount().length() - 2,
								userInfo.getAccount().length());

				model.addAttribute("timeString", lastLoginBean.getTime());
				model.addAttribute("ip", lastLoginBean.getIp());
				model.addAttribute("nameString", nameString);
				return "wdinsurance/step2";
			} else {
				return "wdinsurance/step1";
			}

			// 当上次记录信息不为空

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("tostep2 errroe", e.getMessage());
			return "wdinsurance/step1";
		}
	}

	/**
	 * 跳转到投保第三步界面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/step3")
	public String tostep3(Model model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "serverGroup") String serverGroup,
			@RequestParam(value = "serverName") String serverName,
			@RequestParam(value = "roleName") String roleName,
			@RequestParam(value = "level") String level,
			@RequestParam(value = "pictureUrl") String pictureUrl,
			@RequestParam(value = "RoleId") String roleId
			) {

		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (insuranceService.getStepCookies(request, response,
					userInfo.getAccount() + "checksuccess")) {
				// 掩饰账号
				String nameString = userInfo.getAccount().substring(0, 2)
						+ "******"
						+ userInfo.getAccount().substring(
								userInfo.getAccount().length() - 2,
								userInfo.getAccount().length());
				// 用户手机号
				String phone = CallWebServiceInsuranceAgent
						.getUserPhone(userInfo.getUserId());
				phone = phone.substring(0, 3) + "******"
						+ phone.substring(phone.length() - 2, phone.length());
				model.addAttribute("serverGroup", serverGroup);
				model.addAttribute("serverName", serverName);
				model.addAttribute("nameString", nameString);
				model.addAttribute("roleName", roleName);
				model.addAttribute("phone", phone);
				model.addAttribute("level", level);
				model.addAttribute("pictureUrl", pictureUrl);
				model.addAttribute("roleId", roleId);
				return "wdinsurance/step3";
			} else {
				return "wdinsurance/step1";

			}

			// 判断标志

		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("tostep3 error", e.getMessage());
			return "wdinsurance/step1";
		}

	}

	/**
	 * 第四步页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/step4")
	public String tostep4(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return "wdinsurance/step4";
	}

	/**
	 * 我的保单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/myInsurance")
	public String tomyInsurance(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (insuranceService.getStepCookies(request, response,
					userInfo.getAccount() + "docompensation")) {

				return "wdinsurance/myInsurance";
			} else {
				Integer allOrder = 0;
				Integer yesOrder = 0;
				Integer noOrder = 0;
				String timeString = "未知时间";
				String ip = "未知IP";
				LastLoginBean lastLoginBean = new LastLoginBean(ip, timeString);
				try {
					lastLoginBean = CallWebServiceInsuranceAgent
							.getLastLoginInfo(userInfo.getUserId());
					// 获取总保单数
					MyOrderParameterBean myOrderParameterBean1 = new MyOrderParameterBean();
					myOrderParameterBean1.setAccount(userInfo.getAccount());
					myOrderParameterBean1.setGameName("问道");
					allOrder = orderBLL.getCountOrder(myOrderParameterBean1);

					if (allOrder == null) {
						allOrder = 0;
					}
					// 获取有效保单
					MyOrderParameterBean myOrderParameterBean2 = new MyOrderParameterBean();
					myOrderParameterBean2.setGameName("问道");
					myOrderParameterBean2.setAccount(userInfo.getAccount());
					myOrderParameterBean2.setStatus("efforder");
					yesOrder = orderBLL.getCountOrder(myOrderParameterBean2);
					if (yesOrder == null) {
						yesOrder = 0;
					}
					// 获取失效保单数
					MyOrderParameterBean myOrderParameterBean3 = new MyOrderParameterBean();
					myOrderParameterBean3.setAccount(userInfo.getAccount());
					myOrderParameterBean3.setGameName("问道");
					myOrderParameterBean3.setStatus("noefforder");
					noOrder = orderBLL.getCountOrder(myOrderParameterBean3);
					if (noOrder == null) {
						noOrder = 0;
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.info("toIndex", e.getMessage());
				}
				model.addAttribute("timeString", lastLoginBean.getTime());
				model.addAttribute("ip", lastLoginBean.getIp());
				model.addAttribute("allOrder", allOrder);
				model.addAttribute("yesOrder", yesOrder);
				model.addAttribute("noOrder", noOrder);
				return "wdinsurance/index";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("tostep1 warn", e.getMessage());
			Integer allOrder = 0;
			Integer yesOrder = 0;
			Integer noOrder = 0;
			String timeString = "未知时间";
			String ip = "未知IP";
			LastLoginBean lastLoginBean = new LastLoginBean(ip, timeString);
			try {
				UserInfo userInfo = SignedUser.getUserInfo(request);
				lastLoginBean = CallWebServiceInsuranceAgent
						.getLastLoginInfo(userInfo.getUserId());
				// 获取总保单数
				MyOrderParameterBean myOrderParameterBean1 = new MyOrderParameterBean();
				myOrderParameterBean1.setAccount(userInfo.getAccount());
				allOrder = orderBLL.getCountOrder(myOrderParameterBean1);
				if (allOrder == null) {
					allOrder = 0;
				}
				// 获取有效保单
				MyOrderParameterBean myOrderParameterBean2 = new MyOrderParameterBean();
				myOrderParameterBean2.setAccount(userInfo.getAccount());
				myOrderParameterBean2.setStatus("efforder");
				yesOrder = orderBLL.getCountOrder(myOrderParameterBean2);
				if (yesOrder == null) {
					yesOrder = 0;
				}
				// 获取失效保单数
				MyOrderParameterBean myOrderParameterBean3 = new MyOrderParameterBean();
				myOrderParameterBean3.setAccount(userInfo.getAccount());
				myOrderParameterBean3.setStatus("noefforder");
				noOrder = orderBLL.getCountOrder(myOrderParameterBean3);
				if (noOrder == null) {
					noOrder = 0;
				}
			} catch (Exception e2) {
				// TODO: handle exception
				logger.info("toIndex", e.getMessage());
			}
			model.addAttribute("timeString", lastLoginBean.getTime());
			model.addAttribute("ip", lastLoginBean.getIp());
			model.addAttribute("allOrder", allOrder);
			model.addAttribute("yesOrder", yesOrder);
			model.addAttribute("noOrder", noOrder);
			return "wdinsurance/index";
		}

	}

	/**
	 * 了解界面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/understanding")
	public String tounderstanding(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return "wdinsurance/understanding";
	}

	/**
	 * 我的订单条件查询控制器
	 * 
	 * @param request
	 * @param response
	 * @param myOrderParameterBean
	 * @return 查询信息
	 */
	@RequestMapping(value = "/myOrder", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String myOrder(HttpServletRequest request,
			HttpServletResponse response,
			MyOrderParameterBean myOrderParameterBean) {
		ResultBean<OrderResultBean> result = new ResultBean<OrderResultBean>(
				true, "获取成功", new OrderResultBean());
		// 验证登陆
		// 接收Cookie中的信息
		UserInfo userInfo = SignedUser.getUserInfo(request);

		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);

			return DataFormat.objToJson(result);
		}

		myOrderParameterBean.setAccount(userInfo.getAccount());
		myOrderParameterBean.setGameName("问道");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("null".equals(myOrderParameterBean.getStatus())) {
			myOrderParameterBean.setStatus(null);
		}
		try {
			Date dateEnd = null;
			Date dateStart = null;
			if (!"null".equals(myOrderParameterBean.getEndTimeStr())) {
				myOrderParameterBean.setEndTimeStr(myOrderParameterBean
						.getEndTimeStr() + " 23:59:59");
				dateEnd = sdf.parse(myOrderParameterBean.getEndTimeStr());
			}
			if (!"null".equals(myOrderParameterBean.getStartTimeStr())) {
				myOrderParameterBean.setStartTimeStr(myOrderParameterBean
						.getStartTimeStr() + " 00:00:00");
				dateStart = sdf.parse(myOrderParameterBean.getStartTimeStr());
			}
			myOrderParameterBean.setEndTime(dateEnd);
			myOrderParameterBean.setStartTime(dateStart);
		} catch (ParseException e) {
			result.setProperties(false, "很抱歉，内部错误，请重试", null);
			e.printStackTrace();
			return DataFormat.objToJson(result);
		}
		// 获取我的订单信息
		ResultBean<List<OrderBean>> resultOrderList = orderBLL
				.getOrderByCondition(myOrderParameterBean);
		if (!resultOrderList.getIsSuccess()) {
			result.setProperties(false, "很抱歉，保单信息获取失败，请重试", null);

			return DataFormat.objToJson(result);
		}
		// 将保单List存入result
		result.getData().setOrderList(resultOrderList.getData());
		// 获取保单数量信息及分页总页数
		ResultBean<Map<String, Integer>> resultCount = orderBLL
				.getPageCount(myOrderParameterBean);
		if (!resultCount.getIsSuccess()) {
			result.setProperties(false, "很抱歉，获取保单数量信息失败，请重试", null);

			return DataFormat.objToJson(result);
		}
		// 将保单数量信息及分页总页数加到result类中
		result.setData(orderBLL.setOrderNumToResult(result.getData(),
				resultCount.getData()));

		return DataFormat.objToJson(result);
	}

	/**
	 * 投保条件验证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doinsurance", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> doinsurance(HttpServletRequest request,
			HttpServletResponse response) {
		ResultBean<String> resultBean = new ResultBean<String>();

		try {

			// 获取用户信息
			UserInfo userInfo = SignedUser.getUserInfo(request);
			// 用户信息日志
			logger.debug("doinsurance userinfo" + userInfo);

			// 获取用户的认证手机
			String phoneString = CallWebServiceInsuranceAgent
					.getUserPhone(userInfo.getUserId());
			logger.info("doinsurance phone ", phoneString);
			// 判断是否存在黑名单
			resultBean = insuranceService.isCondition1(userInfo.getUserId(),
					phoneString, userInfo.getAccount());
			// 书写cookies
			if (resultBean.getIsSuccess()) {
				insuranceService.setStepCookies(request, response,
						userInfo.getAccount() + "doinsurance");

			}
			// 无黑名单
		} catch (Exception e) {
			// // TODO: handle exception
			logger.warn(" doinsurance user load warn " + e.getMessage());
			resultBean.setProperties(false, "登录失效，请重新登陆", "");
		}
		logger.info("doinsurance result", resultBean);
		return resultBean;
	}

	/**
	 * 赔偿条件判断
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/docompensation", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> docompensation(HttpServletRequest request,
			HttpServletResponse response) {
		ResultBean<String> resultBean = new ResultBean<String>();
		try {
			// 获取用户信息
			UserInfo userInfo = SignedUser.getUserInfo(request);
			// 获取用户的认证手机
			String phoneString = CallWebServiceInsuranceAgent
					.getUserPhone(userInfo.getUserId());
			// 判断是否存在黑名单
			resultBean = insuranceService.isCondition2(userInfo.getUserId(),

			phoneString, userInfo.getAccount());
			// 书写cookies
			if (resultBean.getIsSuccess()) {
				insuranceService.setStepCookies(request, response,
						userInfo.getAccount() + "docompensation");
			}
			// 无黑名单
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(" docompensation user load warn ", e.getMessage());
			resultBean.setProperties(false, "登录失效，请重新登陆", "");
		}
		logger.info("docompensation result", resultBean);
		return resultBean;
	}

	/**
	 * 驗證乾坤鎖
	 * 
	 * @param request
	 * @param response
	 * @param passpod
	 * @param passpodType
	 * @return
	 */
	/*@RequestMapping(value = "/getUserEkeyverify ", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> getUserEkeyverify(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "Passpod") String passpod,
			@RequestParam(value = "PasspodType") String passpodType) {
		ResultBean<String> resultBean = new ResultBean<String>();
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			// 乾坤锁验证

			String isSuccess = CallWebServiceInsuranceAgent
					.getUserEkeyverifyCode(passpod, userInfo.getAccount(),
							userInfo.getUserId(), 2, passpodType);
			logger.info("getUserEkeyverify1" + isSuccess);
			// 若果乾坤锁验证成功
			if (isSuccess.equals("AUTH_SUCCESS")) {

				resultBean.setProperties(true, "验证成功", "");

			} else {
				resultBean.setProperties(false, "乾坤锁信息验证失败", "");

			}

			if (resultBean.getIsSuccess()) {
				insuranceService.setStepCookies(request, response,
						userInfo.getAccount() + "docompensation");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getUserEkeyverifyCodeAndPhone error" + e.getMessage());
			resultBean.setProperties(false, "登录失效，请重新登陆", "");
			return resultBean;
		}
		return resultBean;

	}*/

	/**
	 * 验证手机号
	 * 
	 * @param request
	 * @param response
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value = "/getCodeAndPhone ", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> getCodeAndPhone(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "verifyCode") String verifyCode) {
		ResultBean<String> resultBean = new ResultBean<String>();

		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			String phone = CallWebServiceInsuranceAgent.getUserPhone(userInfo
					.getUserId());
			String status = CallWebServiceInsuranceAgent
					.getUserPhoneverifyCode(phone, verifyCode);
			if (status.equals("success")) {
				resultBean.setProperties(true, "手机验证码验证成功", "");
			} else {

				resultBean.setProperties(false, "手机验证码验证失败", "");
			}
			// 书写cookies
			if (resultBean.getIsSuccess()) {
				insuranceService.setStepCookies(request, response,
						userInfo.getAccount() + "docompensation");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getUserEkeyverifyCodeAndPhone error", e.getMessage());
			resultBean.setProperties(false, "登录失效，请重新登陆", "");
			return resultBean;
		}
		return resultBean;
	}

	/**
	 * 驗證手機驗證碼和乾坤鎖驗證碼
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getUserEkeyverifyCodeAndPhone ", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> getUserEkeyverifyCodeAndPhone(
			HttpServletRequest request, HttpServletResponse response,
		//	@RequestParam(value = "Passpod") String passpod,
			@RequestParam(value = "PasspodType") String passpodType,
			@RequestParam(value = "verifyCode") String verifyCode) {
		/*logger.info("getUserEkeyverifyCodeAndPhone input", "Passpod" + passpod
				+ "PasspodType" + passpodType);*/
		logger.info("getUserEkeyverifyCodeAndPhone input"
				+ "PasspodType" + passpodType);
		ResultBean<String> resultBean = new ResultBean<String>();
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			String phone = CallWebServiceInsuranceAgent.getUserPhone(userInfo
					.getUserId());
			/*logger.info("sendVerifyCode input", "Passpod" + passpod
					+ "PasspodType" + passpodType);*/
			logger.info("sendVerifyCode input"
					+ "PasspodType" + passpodType);
			/*if (passpod!= null && passpodType != null && !passpodType.equals("")) {
				String isSuccess = CallWebServiceInsuranceAgent
						.getUserEkeyverifyCode(passpod,
								userInfo.getAccount(),
								userInfo.getUserId(), 2, passpodType);

				// 若果乾坤锁验证成功
				if (isSuccess.equals("AUTH_SUCCESS")) {*/
			if (passpodType != null && !passpodType.equals("")) {
					String status = CallWebServiceInsuranceAgent
							.getUserPhoneverifyCode(phone, verifyCode);
	
					if (status.equals("success")) {
						resultBean.setProperties(true, "验证成功", "");
						
					} else {
						resultBean.setProperties(false, " 手机验证码不正确，请您重新输入", "");
					}
				/*} else {
					resultBean.setProperties(false, "乾坤锁信息验证失败", "");
				}	*/
			} else {
				resultBean.setProperties(false, "验证码不能为空", "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getUserEkeyverifyCodeAndPhone error", e.getMessage());
			resultBean.setProperties(false, "登录失效，请重新登陆", "");
			return resultBean;
		}

		// 验证成功cookies
		if (resultBean.getIsSuccess()) {
			try {
				UserInfo userInfo = SignedUser.getUserInfo(request);
				insuranceService.setStepCookies(request, response,
						userInfo.getAccount() + "checksuccess");

			} catch (Exception e) {
				// TODO: handle exception
				logger.warn("addCookies1", e.getMessage());
				return new ResultBean<String>(false, "验证异常，无法进入下一步", "");
			}
		}
		return resultBean;

	}

	/**
	 * 发送手机验证码
	 * 
	 * @param request
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/sendVerifyCode ", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendVerifyCode(HttpServletRequest request) {
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			String phone = CallWebServiceInsuranceAgent.getUserPhone(userInfo
					.getUserId());
			if (phone != null && !phone.equals("")) {
				// 掩饰账号
				String nameString = userInfo.getAccount().substring(0, 2)
						+ "******"
						+ userInfo.getAccount().substring(
								userInfo.getAccount().length() - 2,
								userInfo.getAccount().length());
				String resultString = CallWebServiceInsuranceAgent
						.sendUserPhoneverifyCode(phone, nameString);
				return resultString;
			} else {
				return "信息不能为空";
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "登录失效";
		}

	}

	/**
	 * 防止恶意刷新接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/changeRoleInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> changeRoleInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "serverId") int serverId,
			@RequestParam(value = "veCode") String veCode) {
		ResultBean<String> resultBean = new ResultBean<String>();
		int roleCount=0;
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			// 验证验证码
			if (!new Captcha(request, response).equals(veCode)) {
				resultBean.setProperties(false, "很抱歉，您的验证码填写不正确", "");
			} else {
				
				//获取缓存
				if(memcachedClientAdapter.get(
						userInfo.getUserId()+"insuranceChangeRoleInfo", int.class)!=null){
					roleCount=memcachedClientAdapter.get(
							userInfo.getUserId()+"insuranceChangeRoleInfo", int.class);
					memcachedClientAdapter.set(userInfo.getUserId()+"insuranceChangeRoleInfo",3600,roleCount+1+"");
				}
				else{
					//增加缓存
					memcachedClientAdapter.set(userInfo.getUserId()+"insuranceChangeRoleInfo",3600,0+"");
				}
			
		
				// 次数记录大于20次
				if (roleCount >= 20) {
				 	resultBean.setProperties(false, "禁止多次刷新", "");
				}
				else{
				resultBean.setProperties(true, "获取成功", "");
				// 重新载入角色信息
				if (resultBean.getIsSuccess()) {
					resultBean.setData(CallWebServiceInsuranceAgent
							.getRoleInfo(userInfo.getAccount(), serverId));
				}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			resultBean.setProperties(false, "登录失效，请重新登陆", "");
		}
		logger.info("setRoleCookies output", resultBean);
		return resultBean;
	}

	// TODO MT
	/**
	 * 光宇GO支付成功跳转链接
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goPaySuccess")
	public String returnGoPaySuccess(HttpServletRequest request, Model model) {
		String orderNo = request.getParameter("orderNum");
		if (orderNo == null || orderNo.equals("")) { // 同步处理
			String status = request.getParameter("trade_status"); // 状态
			logger.info("输出同步交易状态：" + status);
			String outTradeNo = (String) request.getParameter("out_trade_no"); // 订单号
			if (status != null) {
				if (status.toUpperCase().equals("TRADE_SUCCESS")
						|| status.toUpperCase().equals("TRADE_FINISHED")) {
					model.addAttribute("status", "success");
					model.addAttribute("outTradeNo", outTradeNo);
				} else {
					model.addAttribute("status", "fail");
					model.addAttribute("outTradeNo", outTradeNo);
				}
			} else {
				model.addAttribute("status", "fail");
				model.addAttribute("outTradeNo", outTradeNo);
			}
			return "/wdinsurance/step4";
		} else {
			String status = "";
			try {
				status = orderBLL.selectByOrderNum(orderNo).getData()
						.getStatus();
				logger.info("直接请求时订单状态：" + status);
			} catch (Exception e) {
				logger.error("调用成功页面时，查询订单状态参数为空....");
			}
			if (status.equals("succpay") || status.equals("efforder")) {
				model.addAttribute("status", "success");
				model.addAttribute("outTradeNo", orderNo);
			} else {
				model.addAttribute("status", "fail");
				model.addAttribute("outTradeNo", orderNo);
			}
		}
		return "/wdinsurance/step4";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "test")
	public String test(GoPayParamBean paraBean) {
		String url = goPayIntService.gumURL(paraBean);
		return url;
	}

	/**
	 * 异步回调光宇GO接口链接
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/froGoPay")
	public String froGoPay(HttpServletRequest request,
			HttpServletResponse response) {
		FroGYYXGO froGYYXGo = new FroGYYXGO();
		String result = null;
		try {
			result = froGYYXGo.froGOInterface(request);
		} catch (Exception e) {
			logger.error("GO购回调接口出错..." + e.toString());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/test2")
	public String test2() {
		return "success";
	}

	/**
	 * 获取大区对用的服务器信息
	 * 
	 * @Title: getServers
	 * @param typeCode
	 *            大区区号
	 * @return ResultBean<List<ServerBean>> 大区对应的服务器信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getServers", method = RequestMethod.POST)
	public ResultBean<List<ServerBean>> getServers(
			@RequestParam(value = "netType") String typeCode) {
		logger.info("typeCode", typeCode);
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
				true, "查询服务器失败", null);
		result.setProperties(true, "查询服务器失败", null);
		CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
		List<Value> serversList = callWebApiAgent
				.getAllServerByNetTypeCode(typeCode);
		List<ServerBean> serverList = new ArrayList<ServerBean>();
		for (Value value : serversList) {
			ServerBean server = new ServerBean(value.getCode(),
					value.getServerName());
			serverList.add(server);
		}
		result.setProperties(true, "查询服务器成功", serverList);
		logger.info("result", result);
		return result;
	}

	/**
	 * 获取保险参数信息
	 * 
	 * @return
	 */

	@RequestMapping(value = "/getAllOrderOrderParameterBean", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<List<OrderParameterBean>> getAllOrderOrderParameterBean() {
		return insuranceService.getAllOrderOrderParameterBean();
	}

	@RequestMapping(value = "/toPage1")
	public String toPage1(Model model, HttpServletRequest request) {
		return null;

	}
	/**
	 * 检查检查账号+区组+角色ID是否有重复的
	 * @param request
	 * @param orderBean
	 * @return
	 */
	@RequestMapping(value = "/checkOrder", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkOrder(
			HttpServletRequest request, OrderBean orderBean) {
		logger.info("虚拟财产保险活动，检查账号+区组+角色ID是否有重复的。1信息为："+orderBean);
		ResultBean<String> result = new ResultBean<String>(false,"检查订单信息重复未知错误","检查订单信息重复未知错误");
		//获取用户名
		UserInfo userInfo = SignedUser.getUserInfo(request);
		orderBean.setAccount(userInfo.getAccount());
		logger.info("虚拟财产保险活动，检查账号+区组+角色ID是否有重复的。2信息为："+orderBean);
		if(orderBLL.checkOrder(orderBean)){
			result.setProperties(false, "该角色已进行投保，且还在有效期，期间无法投保", "该角色已进行投保，且还在有效期，期间无法投保");
		}else{
			result.setProperties(true,"","");
		}	
		return DataFormat.objToJson(result);
	}
	/**
	 * 记录投保订单
	 * 
	 * @param request
	 * @param orderBean
	 * @return
	 */
	@RequestMapping(value = "/addNewOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<TempOrderUrl> getAllOrderOrderParameterBean(
			HttpServletRequest request, OrderBean orderBean) {
		String server = orderBean.getServerGroup();
		orderBean.setServerGroup(server.substring(0, 2));
		orderBean.setServerName(server.substring(3, server.length()));
		orderBean.setGameName("问道");
		logger.info("addNewOrder orderBean"+orderBean);
		String orderid = "";
		ResultBean<TempOrderUrl> resultBean = new ResultBean<TempOrderUrl>();
		List<OrderParameterBean> list = orderParameterBLL
				.getAllOrderParameterBeans();
		if (list != null) {
			// 计算保费
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (orderBean.getCircle() == list.get(i).getCircle()) {
						logger.info("增加投保订单：得到的投保参数信息为OrderParameterBean："+list.get(i));
						float oddestemp = (list.get(i).getOdds() / 100);
						
						//0908 添加验证 ：如果用户的保费>保率  提示：请输入5≤保费≤周期保额上限*周期费率的整数
						//周期保额上限*周期费率的整数
						 float checkup = oddestemp*list.get(i).getUpper();
						 logger.info("增加投保订单：0908 添加验证 ：如果用户的保费>保率  提示：请输入5≤保费≤周期保额上限*周期费率的整数 de 周期保额上限*周期费率的整数:"+checkup);
						 logger.info("增加投保订单：0908 添加验证 ：如果用户的保费>保率 用户保费为orderBean.getProtection()："+orderBean.getProtection());
						 if(orderBean.getProtection()>checkup || orderBean.getProtection()<5){
							resultBean.setProperties(false, "请输入5≤保费≤"+(int)checkup, null);
							return resultBean;
						}
						
						float protection = orderBean.getProtection();
						int money = (int) (protection / oddestemp);
						if (money >= 10000) {
							money = 10000;
						}
						orderBean.setReparation(money);
						orderBean.setOrderType(list.get(i).getType());
						break;
					}
				}
			}
		}
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			orderBean.setAccount(userInfo.getAccount());
			orderBean.setIp(userInfo.getLoginIP());
			//检查是否账号+服务器+角色重复
			logger.info("虚拟财产保险活动，检查账号+区组+角色ID是否有重复的。在addNewOrder信息为："+orderBean);
			if(orderBLL.checkOrder(orderBean)){
				resultBean.setIsSuccess(false);
				resultBean.setMessage("该角色已进行投保，且还在有效期，期间无法投保");
				return resultBean;
			}
			// 设定订单号
			orderid = insuranceService.creatOrderNum(userInfo.getUserId());
			orderBean.setOrderNum(orderid);
			// 用户手机号

			orderBean.setPhone(CallWebServiceInsuranceAgent
					.getUserPhone(userInfo.getUserId()));
			try {
				orderBLL.addOrder(orderBean);
				resultBean.setProperties(true, "提交保单成功", null);

			} catch (Exception e) {
				// TODO: handle exception
				orderBLL.addOrder(orderBean);
				resultBean.setProperties(false, "提交订单失败", null);
			}

			if (resultBean.getIsSuccess()) {

				GoPayParamBean paramBean = new GoPayParamBean();
				// 设定账号
				paramBean.setBuyerAccountValue(userInfo.getAccount());
				paramBean.setOutTradeNoValue(orderid); // *订单号
				paramBean.setTotalFeeValue("" + orderBean.getProtection());// *投保价格
				// 插入数据
				// 交易描述
				String storyString = orderBean.getOrderType() + "投保金额"
						+ orderBean.getProtection();
				paramBean.setBodyValue(storyString);
				String url = goPayIntService.gumURL(paramBean);
				TempOrderUrl tempOrderUrl = new TempOrderUrl(url, orderid);
				resultBean.setData(tempOrderUrl);

			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("addNewOrder error" + e.getMessage());
			resultBean.setProperties(false, "投保失败", null);
		}
		// 成功调用界面
		return resultBean;

	}

	/**
	 * 获取订单支付状态
	 * 
	 * @param orderNum
	 * @return
	 */
	@RequestMapping(value = "/getOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> getOrderStatus(
			@RequestParam(value = "orderNum") String orderNum) {
		logger.info("addNewOrder input", "orderNum" + orderNum);
		ResultBean<String> resultBean = new ResultBean<String>();
		// 根据订单号获取订单信息
		ResultBean<OrderBean> resultBean2 = orderBLL.selectByOrderNum(orderNum);
		if (resultBean2.getData().getStatus().equals("succpay")) {
			resultBean.setProperties(true, "支付成功", "");
		} else {
			resultBean.setProperties(false, "支付出现问题", "");
		}
		return resultBean;
	}

	/**
	 * 获取支付链接
	 * 
	 * @param request
	 * @param orderBean
	 * @return
	 */
	@RequestMapping(value = "/goPay", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> goPay(HttpServletRequest request,
			OrderBean orderBean) {
		logger.info("goPay input ", orderBean);
		ResultBean<String> resultBean = new ResultBean<String>();
		GoPayParamBean paramBean = new GoPayParamBean();
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			// 设定账号
			paramBean.setBuyerAccountValue(userInfo.getAccount());
			paramBean.setOutTradeNoValue(orderBean.getOrderNum()); // *订单号
			paramBean.setTotalFeeValue("" + orderBean.getProtection());// *投保价格
			// 插入数据
			// 交易描述
			String storyString = orderBean.getOrderType() + "投保金额"
					+ orderBean.getProtection();
			paramBean.setBodyValue(storyString);
			String url = goPayIntService.gumURL(paramBean);
			resultBean.setProperties(true, "获取成功", url);
		} catch (Exception e) {
			// TODO: handle exception
			resultBean.setProperties(false, "获取连接失败", "");
		}

		return resultBean;
	}

}
