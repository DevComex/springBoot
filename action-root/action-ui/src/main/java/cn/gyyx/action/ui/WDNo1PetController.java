/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Chen 
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年12月15日 上午11:43:59
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 问道 “天下第一宠活动” 控制器
-------------------------------------------------------------------------*/
package cn.gyyx.action.ui;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wdno1pet.Address;
import cn.gyyx.action.beans.wdno1pet.CommentBean;
import cn.gyyx.action.beans.wdno1pet.CrimeReporterBean;
import cn.gyyx.action.beans.wdno1pet.ImageBean;
import cn.gyyx.action.beans.wdno1pet.Pagination;
import cn.gyyx.action.beans.wdno1pet.PetsQuery;
import cn.gyyx.action.beans.wdno1pet.UserLotteryBean;
import cn.gyyx.action.beans.wdno1pet.VoteBean;
import cn.gyyx.action.beans.wdno1pet.WDNo1PetInfoBean;
import cn.gyyx.action.bll.wdno1pet.CommentBLL;
import cn.gyyx.action.bll.wdno1pet.CrimeReporterBLL;
import cn.gyyx.action.bll.wdno1pet.PetInfoBLL;
import cn.gyyx.action.bll.wdno1pet.UserLotteryBLL;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.core.Ip;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.service.wdno1pet.ImageService;
import cn.gyyx.service.wdno1pet.UserLotteryService;
import cn.gyyx.service.wdno1pet.VoteService;
import cn.gyyx.service.wdno1pet.WDNo1PetService;

@Controller
@RequestMapping(value = "wdno1pet")
@Scope("prototype")
public class WDNo1PetController {

	private static final PetInfoBLL petInfoBLL = new PetInfoBLL();
	private WDNo1PetService wdno1petService = new WDNo1PetService();
	private static final ImageService imageService = new ImageService();
	private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDNo1PetController.class);

	@RequestMapping(value = "/")
	public String showIndex(Model model) {
		logger.info("Inside index");
		model.addAttribute("codeServer1", "s.gyyx.cn");
		model.addAttribute("codeServer2", "static.cn114.cn");
		return "wdno1pet/index";
	}

	/**
	 * 获取宠物详细信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "pet/{pCode}", method = RequestMethod.GET)
	public String getPetInfo(@PathVariable("pCode") String pCode, Model model,
			HttpServletRequest request) {
		logger.info(" 展示作品详细信息和评论+投票后几名,宠物编号" + pCode);
		WDNo1PetInfoBean wdNo1PetInfoBean = wdno1petService
				.getPetInfoByPetCode(pCode);
		Pagination<CommentBean> pagination = wdno1petService.getComments(pCode,
				1 + "");
		List<String> accounts = wdno1petService.getVoteAccountsByPetId(pCode);
		// 接收Cookie中的信息
		UserInfo userInfo;
		try {
			userInfo = SignedUser.getUserInfo(request);
			logger.info("" + userInfo.getUserId());
		} catch (NullPointerException e) {
			logger.warn(e.getMessage());
			userInfo = new UserInfo();
			userInfo.setUserId(0);
		}
		ServerInfoBean serverInfoBean = null;
		try {
			serverInfoBean = callWebApiAgent
					.getServerStatusFromWebApi(wdNo1PetInfoBean.getServerID());
			model.addAttribute("serverName", serverInfoBean.getValue()

			.getServerName());
			logger.info("宠物详细信息", serverInfoBean);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("accounts", accounts);
		model.addAttribute("pagination", pagination);
		model.addAttribute("petInfo", wdNo1PetInfoBean);
		model.addAttribute("codeServer1", "s.gyyx.cn");
		model.addAttribute("codeServer2", "static.cn114.cn");
		return "wdno1pet/pet_deatail";
	}

	/**
	 * 最热宠物排行
	 * 
	 * @param petType
	 *            宠物类型:普通，变异，神兽，元灵，坐骑
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "hottestPets/{petType}", method = RequestMethod.GET)
	public String getHottestPetsByType(@PathVariable("petType") String petType,
			Model model) {
		logger.info("petType(宠物种类)", petType);
		try {
			petType = new String(petType.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.debug("宠物种类格式转化错误", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("---宠物类型-->" + petType);
		List<WDNo1PetInfoBean> hotPets = petInfoBLL.getHottestPetsByType(
				petType, 15);
		logger.info("hotPets宠物的信息", hotPets);
		model.addAttribute("hotPets", hotPets);
		return "wdno1pet/ajax/hotPets";
	}

	/**
	 * 最强宠物列表，分类依据属性值
	 * 
	 * @param qualityName
	 *            宠物属性名称：pet_growth,pet_blood,pet_speed,pet_magic,pet_attack
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "strongestPets/{qualityName}", method = RequestMethod.GET)
	public String getStrongestPetsByQuality(
			@PathVariable("qualityName") String qualityName, Model model) {
		logger.info("---宠物属性名称-->" + qualityName);
		List<WDNo1PetInfoBean> strongPets = petInfoBLL
				.getStrongestPetsByQuality(qualityName, 15);
		model.addAttribute("strongPets", strongPets);
		return "wdno1pet/ajax/strongPets";
	}

	/**
	 * 插入举报记录
	 * 
	 * @param crime
	 */
	@RequestMapping(value = " pet/{pCode}/addTipster", method = RequestMethod.POST)
	public void addTipster(@ModelAttribute CrimeReporterBean crime,
			PrintWriter printWriter) {

		logger.info("crime", crime);
		// TODO CHECK BEAN
		if (crime.getUserCode() == 0 || crime.getUserCode() == null
				|| crime.getPetCode() == 0) {
			logger.info("getPetCode getUserCode warn");
		} else {
			CrimeReporterBLL cReporterBLL = new CrimeReporterBLL();
			cReporterBLL.addTipster(crime);
		}
		printWriter.flush();
		printWriter.close();

	}

	/**
	 * ajax获取评论分页数据
	 * 
	 * @param pCode
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pet/{pCode}/page/{page}", method = RequestMethod.GET)
	public String getPetInfoComment(@PathVariable("pCode") String pCode,
			@PathVariable("page") String page, Model model) {
		// ajax显示该宠物下第N页的评论显示
		logger.info(pCode);
		Pagination<CommentBean> pagination = wdno1petService.getComments(pCode,
				page);

		model.addAttribute("pagination", pagination);
		return "wdno1pet/ajax/pagination";
	}

	/**
	 * 插入评论并将值传递给评论页面
	 * 
	 * @param commentBean
	 * @param pw
	 */

	@RequestMapping(value = "pet/{pCode}/addComment", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> addComment(
			@ModelAttribute CommentBean commentBean, HttpServletRequest request) {
		// TODO CHECK BEAN
		// 获取Ip
		ResultBean<Integer> resultBean = new ResultBean<Integer>();
		if (commentBean.getPetCode() != 0) {
			int ipInt;
			try {
				logger.info("IP is :" + Ip.getCurrent(request));
				ipInt = Ip.getCurrent(request).asNumber();
				logger.info("Ip", ipInt);
			} catch (Exception e) {
				// TODO: handle exception
				logger.warn(e.getMessage());
				logger.warn("Ip获取出错");
				ipInt = 111111111;// 需要修改
			}

			commentBean.setCommentIp(ipInt);// 需要修改
			CommentBLL cBll = new CommentBLL();

			cBll.addComment(commentBean);
			resultBean.setIsSuccess(true);
		}
		logger.info("addComment result", resultBean);
		return resultBean;
	}

	@RequestMapping(value = "uploadInfo")
	@ResponseBody
	public ResultBean<Integer> uploadPetInfo(HttpServletRequest request,
			WDNo1PetInfoBean petInfo) {
		return wdno1petService.uploadPetInfo(request, petInfo);
	}

	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> uploadImg(HttpServletRequest request,
			ImageBean img) {
		return imageService.uploadImg(request, img);
	}

	/**
	 * 显示参赛页面
	 * 
	 * @param request
	 *            请求
	 * @return 页面模板
	 */
	@RequestMapping(value = "showUpload")
	public String showUpload(HttpServletRequest request, Model model) {
		// Check Auth
		model.addAttribute("defaultServers",
				callWebApiAgent.getAllServerByNetTypeCode("3"));// 前端默认双线
		model.addAttribute("codeServer2", "static.cn114.cn");
		UserInfo userInfo;
		try {
			userInfo = SignedUser.getUserInfo(request);
			logger.info("" + userInfo.getUserId());
			model.addAttribute("isLogin", "true");
			model.addAttribute("userName", userInfo.getAccount());
		} catch (NullPointerException e) {
			logger.warn("[showUpload]NullPointerException in geting userInfo");
			userInfo = new UserInfo();
			userInfo.setUserId(0);
			model.addAttribute("isLogin", "false");
		}
		return "wdno1pet/upload";
	}

	// 通过typeCode 找到server
	@RequestMapping(value = "getServers/{typeCode}")
	public String getServers(@PathVariable("typeCode") String typeCode,
			Model model) {
		logger.info("typeCode", typeCode);
		// Check Auth
		model.addAttribute("crtServers",
				callWebApiAgent.getAllServerByNetTypeCode(typeCode));
		model.addAttribute("codeServer2", "static.cn114.cn");
		return "wdno1pet/ajax/serverList";
	}

	/**
	 * 用户给指定的作品投票
	 */
	@RequestMapping(value = "pet/{pCode}/voteForPet", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> voteForPet(@ModelAttribute VoteBean voteBean,
			HttpServletRequest request) {
		// TODO CHECK BEAN
		ResultBean<Integer> msg = new ResultBean<Integer>();
		java.util.Date nowdate = new java.util.Date();
		String myDateString = "2015-1-13 12:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			java.util.Date limitDate = sdf.parse(myDateString);
			if (nowdate.after(limitDate)) {
				msg.setIsSuccess(false);
				msg.setMessage("投票已经结束");
				return msg;
			}
		} catch (ParseException e1) {
			logger.warn("Date covert error!");
		}
		myDateString = "2015-1-13 12:00";
		try {
			java.util.Date limitDate = sdf.parse(myDateString);
			if (nowdate.after(limitDate)) {
				msg.setIsSuccess(false);
				msg.setMessage("投票结束时间为：2015年1月12日24:00");
				return msg;
			}
		} catch (ParseException e1) {
			logger.warn("Date covert error!");
		}

		// 获取Ip
		int ipInt;
		try {
			ipInt = Ip.getCurrent(request).asNumber();
		} catch (Exception e) {
			logger.warn(e.getMessage());
			// TODO: handle exception
			logger.warn("IP获取出错");
			ipInt = 111111111;// 需要修改
		}
		voteBean.setVoteIp(ipInt);// 需要修改
		if (voteBean.getUserCode() == 0 || voteBean.getUserCode() == null
				|| voteBean.getPetCode() == 0) {
			msg.setIsSuccess(false);
			msg.setMessage("fail");
		} else {
			VoteService vService = new VoteService();
			msg = vService.addVoteService(voteBean);
		}

		return msg;
	}

	@RequestMapping(value = "showPets")
	public String showPets(HttpServletRequest request, Model model) {
		// Check Auth,显示排行模版
		List<WDNo1PetInfoBean> hotPets = petInfoBLL.getHottestPetsByType("0",
				15);
		List<WDNo1PetInfoBean> strongestPets = petInfoBLL
				.getStrongestPetsByQuality("pet_growth", 15);
		model.addAttribute("defaultServers", callWebApiAgent.getAllServer());
		model.addAttribute("hotPets", hotPets);
		model.addAttribute("strongPets", strongestPets);
		model.addAttribute("codeServer2", "static.cn114.cn");

		UserInfo userInfo;
		try {
			userInfo = SignedUser.getUserInfo(request);
			logger.info("userInfo", userInfo);
			model.addAttribute("isLogin", "true");
		} catch (NullPointerException e) {
			logger.warn(e.getMessage());
			userInfo = new UserInfo();
			userInfo.setUserId(0);
			model.addAttribute("isLogin", "false");
		}

		return "wdno1pet/pets";
	}

	@RequestMapping(value = "getPets")
	public String getPets(Model model, PetsQuery query) {
		// TODO CHECK BEAN
		logger.info("PetsQuery", query);
		if (query.getPageIndex() >= 0) {
			if ("请输入关键词搜索".equals(query.getCharacterName())) {
				query.setCharacterName(null);
			}
			if ("请输入关键词搜索".equals(query.getPetName())) {
				query.setPetName(null);
			}
			int num = petInfoBLL.getPetCountByStrategyAndKeys(query);
			if (num != 0) {
				List<WDNo1PetInfoBean> list = petInfoBLL
						.getBeanByStrategyAndKeys(query);

				try {
					for (WDNo1PetInfoBean wb : list) {
						ServerInfoBean sib = callWebApiAgent
								.getServerStatusFromWebApi(wb.getServerID());
						wb.setServerName((sib.getValue().getServerName()));
						logger.info("ServerInfoBean", sib);
					}
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
				model.addAttribute("pets", list);
				model.addAttribute("pageNum", Math.ceil((float) num / 12f));
				model.addAttribute("crtPage", query.getPageIndex() + 1);
			}
		} else {
			model.addAttribute("pageNum", 0);
			model.addAttribute("crtPage", 1);
		}
		return "wdno1pet/ajax/petPages";
	}

	@RequestMapping(value = "showLottery")
	public String showLottery(Model model) {
		model.addAttribute("codeServer2", "static.cn114.cn");
		return "wdno1pet/lottery";
	}

	/**
	 * 处理数据绑定异常
	 * 
	 * @param printWriter
	 *            ajax返回
	 * @param e
	 *            绑定异常
	 */
	@ExceptionHandler({ BindException.class })
	@ResponseBody
	public ResultBean<Integer> exception(BindException e) {
		logger.warn(e.getMessage());
		ResultBean<Integer> bandResultBean = new ResultBean<Integer>();
		bandResultBean.setIsSuccess(false);
		bandResultBean.setMessage("错误的数据类型");
		logger.info("exception", bandResultBean);
		return bandResultBean;
	}

	/**
	 * 配置中奖信息，
	 * 
	 * @return 消息
	 */
	/*
	 * @RequestMapping(value = "LotteryConfigController" , method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public ResultBean<Integer> LotteryConfiging(){
	 * LotteryConfigService lService=new LotteryConfigService();
	 * ResultBean<Integer> resultBean=new ResultBean<Integer>(); try {
	 * lService.LotteryConfig(); resultBean.setIsSuccess(true);
	 * resultBean.setMessage("注入数据成功");
	 * 
	 * } catch (Exception e) { resultBean.setIsSuccess(false);
	 * resultBean.setMessage("注入数据失败"); } return resultBean; }
	 * 
	 * @RequestMapping(value = "showLotteryConfig") public String
	 * showConfig(Model model) {
	 * 
	 * return "wdno1pet/ajax/LotteryConfig"; }
	 */
	/**
	 * 用户进行抽奖
	 * 
	 * @return resultBean 抽奖信息
	 */

	@RequestMapping(value = "lottery", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<UserLotteryBean> Userlottery(HttpServletRequest request) {
		ResultBean<UserLotteryBean> resultBean = new ResultBean<UserLotteryBean>();
		UserInfo userInfo;
		// 抽奖开始时间
		java.util.Date nowdate = new java.util.Date();
		String myDateString1 = "2015-1-15 12:00";
		String myDateString2 = "2015-1-25 23:59";
		logger.debug("myDateString1", myDateString1);
		logger.debug("myDateString2", myDateString2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			java.util.Date limitDate1 = sdf.parse(myDateString1);
			java.util.Date limitDate2 = sdf.parse(myDateString2);
			// 抽奖开始
			if (nowdate.before(limitDate1)) {
				resultBean.setIsSuccess(false);
				resultBean.setMessage("抽奖开始时间为：2015年1月15日12:00，敬请期待");
			}
			// 抽奖结束
			else if (nowdate.after(limitDate2)) {
				resultBean.setIsSuccess(false);
				resultBean.setMessage("抽奖活动已结束");

			}
			// 正常
			else {
				// 验证登录并获取Id
				try {
					userInfo = SignedUser.getUserInfo(request);
					logger.info("" + userInfo.getUserId());
				} catch (NullPointerException e) {
					logger.warn("空");
					userInfo = new UserInfo();
					userInfo.setUserId(0);
					resultBean.setIsSuccess(false);
					resultBean.setMessage("userCodeNull");
				}
				// 在存在用户的情况下
				if (userInfo.getUserId() != 0) {
					logger.info("" + userInfo.getUserId());
					UserLotteryService uService = new UserLotteryService();
					resultBean = uService.userLottery(userInfo.getUserId());

				}

			}

		} catch (ParseException e1) {
			logger.warn(e1.getMessage());
			resultBean.setIsSuccess(false);
			resultBean.setMessage("Date covert error!");
		}
		logger.info("Userlottery", resultBean);
		return resultBean;

	}

	/*
	 * public static void main(String[] args) { final UserLotteryDAO uLotteryDAO
	 * = new UserLotteryDAO(); Runnable r1 = new Runnable(){
	 * 
	 * @Override public void run(){ System.out.println("in r1");
	 * uLotteryDAO.updateUserLotteryStatusAndType(1000); } };
	 * 
	 * Runnable r2 = new Runnable(){
	 * 
	 * @Override public void run(){ System.out.println("in r2");
	 * uLotteryDAO.updateUserLotteryStatusAndType(1001); } }; new
	 * Thread(r1).start(); new Thread(r2).start(); }
	 */
	/**
	 * 储存用户的地址信息
	 * 
	 * @param request
	 * @param userAdress
	 * @param userPhone
	 * @param userName
	 * @return 消息
	 */
	@RequestMapping(value = "lotteryAdress")
	@ResponseBody
	public ResultBean<String> userAddress(@ModelAttribute Address address,
			HttpServletRequest request) {
		logger.info("Address", address);
		String txtRealName = address.getTxtRealName();
		String txtAddress = address.getTxtAddress();
		String txtPhone = address.getTxtPhone();
		ResultBean<String> resultBean = new ResultBean<String>();
		String information = "姓名: " + txtRealName + " 电话:" + txtPhone
				+ " 收件地址:" + txtAddress;
		if (txtAddress == null || txtAddress.equals("") || txtPhone == null
				|| txtPhone.equals("") || txtRealName == null
				|| txtRealName.equals("")) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("信息填入失败");
		} else {

			UserLotteryBLL uLotteryBLL = new UserLotteryBLL();
			// 验证登录并获取Id
			UserInfo userInfo;
			try {
				userInfo = SignedUser.getUserInfo(request);
				logger.info("userInfo", userInfo);
			} catch (NullPointerException e) {
				logger.warn(e.getMessage());
				userInfo = new UserInfo();
				userInfo.setUserId(0);
				resultBean.setIsSuccess(false);
				resultBean.setMessage("userCodeNull");
				return resultBean;
			}
			uLotteryBLL.setUserAdress(information, userInfo.getUserId());
			resultBean.setIsSuccess(true);
			resultBean.setMessage("信息填入成功，您的奖品即将踏上旅途");
		}
		logger.info("resultBean", resultBean);
		return resultBean;
	}

	/**
	 * 中得是银元宝
	 * 
	 * @param request
	 * @param channel
	 * @param server
	 * @return result
	 */

	@RequestMapping(value = "lotteryServer", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> userAddress(HttpServletRequest request,
			@ModelAttribute Address address) {
		ResultBean<String> resultBean = new ResultBean<String>();
		String channel = address.getChannel();
		String server = address.getServer();
		logger.info("Address", address);
		// TODO
		ServerInfoBean serverInfoBean = null;
		try {
			serverInfoBean = callWebApiAgent.getServerStatusFromWebApi(Integer
					.parseInt(server));
			logger.info("serverInfoBean", serverInfoBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		String information = "大区："
				+ channel
				+ "服务器："
				+ (serverInfoBean == null ? "-" : serverInfoBean.getValue()
						.getServerName());
		if (channel == null || channel.equals("") || server == null
				|| server.equals("")) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("信息填入失败");

		} else {
			UserLotteryBLL uLotteryBLL = new UserLotteryBLL();
			// 验证登录并获取Id
			UserInfo userInfo;
			try {
				userInfo = SignedUser.getUserInfo(request);
				logger.info("userInfo", userInfo);
			} catch (NullPointerException e) {
				logger.warn(e.getMessage());
				userInfo = new UserInfo();
				userInfo.setUserId(0);
				resultBean.setIsSuccess(false);
				resultBean.setMessage("userCodeNull");
				return resultBean;
			}
			uLotteryBLL.setUserAdress(information, userInfo.getUserId());
			resultBean.setIsSuccess(true);
			resultBean.setMessage("元宝即将送到");

		}
		logger.info("userAddress resultBean", resultBean);

		return resultBean;
	}

}
