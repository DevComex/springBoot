package cn.gyyx.action.ui.xuanwuba;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.wdninestory.AddressBean;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.Data;
import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsGetBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PictureUrlBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SumCreditBean;
import cn.gyyx.action.bll.lottery.LotteryMemcacheBll;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.wdninestory.AddressBLL;
import cn.gyyx.action.bll.wdninestory.QualificationBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CreditBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsGetBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PictureUrlBLL;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.xuanwuba.XWBLotteryService;
import cn.gyyx.action.service.xuanwuba.XWBService;
import cn.gyyx.action.ui.WDNineLotteryController;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/xuanwubaLottery")
public class XWBLotteryController {
	private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private AddressBLL addressBll = new AddressBLL();
	private UserLotteryBll userLotteryBLL = new UserLotteryBll();
	private XWBLotteryService lotteryService = new XWBLotteryService();
	private XWBService xWBService = new XWBService();
	private int actionCode = 288;
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDNineLotteryController.class);
	private QualificationBLL qualificationBLL = new QualificationBLL();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private UserLotteryBll userLotteryBll = new UserLotteryBll();
	private CreditBLL creditBLL = new CreditBLL();
	private PictureUrlBLL pictureUrlBLL = new PictureUrlBLL();
	private GoodsBLL goodsBLL = new GoodsBLL();
	private GoodsGetBll goodsGetBll = new GoodsGetBll();

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getServers
	 * @Description: TODO 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(
			@RequestParam(value = "netType") String typeCode) {
		logger.info("typeCode", typeCode);
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
				true, "查询服务器失败", null);
		result.setProperties(activityConfigBll.getConfigMessage("炫舞吧抽奖")
				.getIsSuccess(), activityConfigBll.getConfigMessage("炫舞吧抽奖")
				.getMessage(), null);
		if (!activityConfigBll.getConfigMessage("炫舞吧抽奖").getIsSuccess()) {
			logger.info("result", result);
			return result;
		}
		result.setProperties(true, "查询服务器失败", null);
		List<Data> serversList = callWebApiAgent
				.getServersXuanWuBaByNetType(Integer.parseInt(typeCode));
		List<ServerBean> serverList = new ArrayList<ServerBean>();
		for (Data data : serversList) {
			ServerBean server = new ServerBean(data.getCode(),
					data.getServerName());
			serverList.add(server);
		}
		result.setProperties(true, "查询服务器成功", serverList);
		logger.info("result", result);
		return result;
	}
	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getPrizeInfoURL
	 * @Description: TODO 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getPrizeInfoURL")
	@ResponseBody
	public ResultBean<List<PrizeBean>> getPrizeInfoURL() {
		ResultBean<List<PrizeBean>> result = new ResultBean<List<PrizeBean>>(
				true, "显示奖品失败", null);
		
		result.setProperties(true, "显示奖品失败", null);
		List<PrizeBean> listPrize = userLotteryBll.getPrize(actionCode);
		if(listPrize!=null){
			for(PrizeBean prizeBean : listPrize){
				PictureUrlBean pictureUrlBean = pictureUrlBLL.getPictureUrlByPrizeCode(prizeBean.getCode());
				if(pictureUrlBean!=null){
					prizeBean.setPictureUrl(pictureUrlBean.getPictureUrl());
				}
			}
		}
		result.setProperties(true, "查询奖品成功", listPrize);
		logger.info("炫舞吧XWBLotteryController-奖品列表：{}", listPrize);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getAddress
	 * @Description: TODO 传递用户地址信息
	 * @param request
	 * @return ResultBean<AddressBean>
	 */
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean<AddressBean> getAddress(HttpServletRequest request) {
		ResultBean<AddressBean> result = new ResultBean<AddressBean>(false,
				"登录失效，请重新登录", null);
		try {

			UserInfo userInfo = SignedUser.getUserInfo(request);
			logger.info("userInfo", userInfo);
			result = addressBll.getAddress(userInfo.getUserId(), actionCode);
			if (!result.getIsSuccess()) {
				// 得到该用户获得的奖品
				List<UserInfoBean> list = userLotteryBll
						.getUserLotteryByUserCode(actionCode,
								userInfo.getAccount());
				if (list.size() > 0) {
					result.setProperties(true, "有奖品", null);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		logger.info("getAddress result ", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: resetAddress
	 * @Description: TODO 重置或者插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/resetAddress")
	@ResponseBody
	public ResultBean<Integer> resetAddress(
			@ModelAttribute AddressBean address, HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "添加地址信息失败",
				0);
		logger.info("AddressBean", address);
		// try {
		UserInfo userInfo = SignedUser.getUserInfo(request);
		logger.info("userInfo", userInfo);
		address.setUserCode(userInfo.getUserId());
		address.setActionCode(actionCode);

		result = addressBll.addAddress(address);
		// } catch (Exception e) {
		// logger.info(e.getMessage());
		// }
		logger.info("resetAddress result", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月25日
	 * @Title: setAddress
	 * @Description: TODO 修改地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/setAddress")
	@ResponseBody
	public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address,
			HttpServletRequest request) {
		logger.info("AddressBean", address);

		ResultBean<Integer> result = new ResultBean<Integer>(false,
				"登录失效，请重新登录", 0);
		logger.info("result", result);
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			logger.info("userInfo", userInfo);
			address.setUserCode(userInfo.getUserId());
			address.setActionCode(actionCode);
			result = addressBll.setAddress(address);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		logger.info("setAddress result ", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: resetAddress
	 * @Description: TODO 查询用户是否中奖
	 * @param address
	 * @param request
	 * @return ResultBean<UserLotteryBean>
	 */
	@RequestMapping(value = "/getPrize")
	@ResponseBody
	public ResultBean<UserLotteryBean> getPrize(
			@ModelAttribute ServerBean address, HttpServletRequest request) {
		ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>(
				false, "您未满足抽奖资格", null);
		result.setProperties(activityConfigBll.getConfigMessage("炫舞吧抽奖")
				.getIsSuccess(), activityConfigBll.getConfigMessage("炫舞吧抽奖")
				.getMessage(), null);
		if (!activityConfigBll.getConfigMessage("炫舞吧抽奖").getIsSuccess()) {
			logger.info("炫舞吧XWBLotteryController-抽奖配置检查结果：{}", result.getMessage());
			return result;
		}
//		try {
//			address.setServerName(new String(address.getServerName().getBytes("ISO-8859-1"),"UTF-8"));
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		PictureUrlBean pictureUrl = pictureUrlBLL.getPictureUrlByPrizeFlag(1);
		if(pictureUrl!=null){
			PrizeBean prizeBean = userLotteryBll.getPrizeByCode(pictureUrl.getPrizeCode());
			PrizeBean noprizeBean = new PrizeBean(prizeBean.getCode(), prizeBean.getChinese(), prizeBean.getEnglish(),
					actionCode,prizeBean.getIsReal(), prizeBean.getNum());
			logger.info("炫舞吧XWBLotteryController-默认奖品：{}", noprizeBean);
			logger.info("炫舞吧XWBLotteryController-抽奖服务器信息", address);
			try (DistributedLock lock = new DistributedLock(actionCode + "xwblottery")){
				// 获取用户cookie
				UserInfo userInfo = SignedUser.getUserInfo(request);
				if (userInfo == null) {
					result = new ResultBean<UserLotteryBean>(false, "请您先登录", null);
					return result;
				}			
				lock.weakLock(30, 2);			
				// 获取用户抽奖资格
				SumCreditBean sumCreditBean = creditBLL.getCreditByAccount(userInfo.getAccount());
				logger.info("炫舞吧XWBLotteryController-抽奖-抽奖资格：{}", sumCreditBean+",账号：{}"+userInfo.getAccount());
				if (sumCreditBean == null) {
					logger.info("炫舞吧XWBLotteryController-抽奖-抽奖资格检查为：{null},账号：{}"+userInfo.getAccount());
					return new ResultBean<UserLotteryBean>(false, "积分不足", null);
				}
				if (sumCreditBean.getSumCredit()>=30) {
					result = lotteryService.lotteryByDataBase(actionCode,
							userInfo.getUserId(), "byChance", userInfo.getAccount(),
							address.getServerName(), address.getServerId(),
							userInfo.getLoginIP(), noprizeBean);
					int i = sumCreditBean.getSumCredit();
					sumCreditBean.setSumCredit(i-30);
					creditBLL.setSumCredit(sumCreditBean);
					logger.info("炫舞吧XWBLotteryController-抽奖-扣除积分后：{}", sumCreditBean+",账号：{}"+userInfo.getAccount());
				} else {
					logger.info("炫舞吧XWBLotteryController-抽奖-抽奖资格检查为：{}不够30,账号：{}"+userInfo.getAccount());
					return new ResultBean<UserLotteryBean>(false, "积分不足",null);
				}
			} catch (DLockException e) {
				logger.error("DLockException type:" + e.getType() + ",msg:" + e.getMessage());
				return new ResultBean<UserLotteryBean>(false, "网络超时", null);	
			}
		}
		logger.info("getPrize result"+result);
		PictureUrlBean pictureUrlBean = pictureUrlBLL.getPictureUrlByPrizeCode(result.getData().getPrizeCode());
		UserLotteryBean userLotteryBeanNew = result.getData();
		userLotteryBeanNew.setUrl(pictureUrlBean.getPictureUrl());
		result.setData(userLotteryBeanNew);
		logger.info("炫舞吧XWBLotteryController-抽奖-抽奖结果：{}"+userLotteryBeanNew);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月24日
	 * @Title: getLotteryTime
	 * @Description: TODO 获取用户抽奖次数
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/getLotteryTime")
	@ResponseBody
	public ResultBean<Integer> getLotteryTime(HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<Integer>(false,
				"获取用户抽奖次数失败！", 0);
		try {
			// 获取用户cookie
			UserInfo userInfo = SignedUser.getUserInfo(request);
			logger.info("UserInfo", userInfo);
			// 获取用户抽奖资格
			QualificationBean qualification = qualificationBLL.selectByUserAndAction(""+userInfo.getUserId(),""+actionCode);
			if (qualification.getLotteryTime() > 0) {
				result.setProperties(true, "获取用户抽奖次数成功",
						qualification.getLotteryTime());
			}
			else{
				
			}
		} catch (Exception e) {
			logger.error("获取用户cookie失败");
			logger.warn(e.getMessage());
		}
		logger.info("result getLotteryTime", result);
		return result;
	}

	/**
	 * 得到当前活动所有中奖的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfo")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfo() {
		LotteryMemcacheBll lotteryMemcacheBll = new LotteryMemcacheBll();
		ResultBean<List<UserInfoBean>> resultBean = new ResultBean<List<UserInfoBean>>();
		List<UserInfoBean> list = new ArrayList<UserInfoBean>();
		list = userLotteryBLL.getUserLottery(actionCode);
		logger.info(list.size() + ":所有中奖记录数量");
		lotteryMemcacheBll.setUserInfoBeanMem(list, actionCode);
		for (UserInfoBean user : list) {
			String str = user.getAccount();
			if(0 < str.length() && str.length() <= 3){
				str = String.valueOf(str.charAt(0))+matchStr(str.length()-1);
			}else if(str.length() == 4){
				str = String.valueOf(str.charAt(0))+matchStr(str.length()-2)+String.valueOf(str.charAt(str.length()-1 ));
			}else if(str.length() >=5){
				str = str.substring(0, 2)+matchStr(str.length()-4)+str.substring(str.length()-2, str.length());
			}else{
				
			}
			user.setAccount(str);
		}
		resultBean.setProperties(true, "成功", list);
		logger.info("getAllLotteryInfo", resultBean);
		return resultBean;
	}
	public static String matchStr(int num){
		String str = "";
		for(int i = 0;i < num;i++){
			str += "*";
		}
		return str;
	}
	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfoByUser(
			HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ResultBean<List<UserInfoBean>> resultBean = new ResultBean<List<UserInfoBean>>();
		// 获得用户信息
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			logger.info("UserInfo", userInfo);
			// 得到该用户获得的奖品
			List<UserInfoBean> list = userLotteryBll
					.wishGetUserLotteryByUserCode(actionCode,
							userInfo.getAccount());
			for (UserInfoBean userInfoBean : list) {
				userInfoBean.setTimeStr(sdf.format(userInfoBean.getTime()));
			}
			resultBean.setProperties(true, "成功", list);

		} catch (Exception e) {
			logger.warn("取不到账户信息");
			logger.warn(e.getMessage());
			resultBean.setProperties(false, "取不到账户信息", null);
		}
		logger.info(" getAllLotteryInfoByUser resultBean", resultBean);
		return resultBean;
	}
	@RequestMapping(value = "/getExchange")
	@ResponseBody
	public ResultBean<GoodsInfoBean> getExchange(
			@ModelAttribute ServerBean address,String goodCode, HttpServletRequest request) {
//		try {
//			address.setServerName(new String(address.getServerName().getBytes("ISO-8859-1"),"UTF-8"));
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		GoodsInfoBean GoodsInfoBean = goodsBLL.getGoodsByCode(Integer.parseInt(goodCode));
		logger.info("炫舞吧XWBLotteryController-兑换ServerBean", address);
		ResultBean<GoodsInfoBean> result = new ResultBean<GoodsInfoBean>(
				false, "您未满足兑换资格资格", null);
		result.setProperties(activityConfigBll.getConfigMessage("炫舞吧抽奖")
				.getIsSuccess(), activityConfigBll.getConfigMessage("炫舞吧抽奖")
				.getMessage(), null);
		if (!activityConfigBll.getConfigMessage("炫舞吧抽奖").getIsSuccess()) {
			logger.info("炫舞吧XWBLotteryController-兑换配置检查结果：{}", result.getMessage());
			return result;
		}
		logger.info("result", result);
		
			// 获取用户cookie
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				result.setProperties(false, "请您先登录", null);
				return result;
			}
			logger.info("userInfo", userInfo);
			try(DistributedLock lock = new DistributedLock(actionCode + "xwbexchange")) {
				lock.weakLock(30,10);
				// 获取用户抽奖资格
				SumCreditBean sumCreditBean = creditBLL.getCreditByAccount(userInfo.getAccount());
				logger.info("炫舞吧XWBLotteryController-兑换-兑换资格：{}", sumCreditBean+",账号：{}"+userInfo.getAccount());
				if (sumCreditBean == null) {
					logger.info("炫舞吧XWBLotteryController-兑换-兑换资格检查为：{null},账号：{}"+userInfo.getAccount());
					return new ResultBean<GoodsInfoBean>(false, "您没有可兑换使用的积分！", null);
				}
				if (GoodsInfoBean.getPrice()<=0) {
					logger.info("炫舞吧XWBLotteryController-兑换-道具数量检查：{道具数量不足！}"+GoodsInfoBean.getPrice()+",账号：{}"+userInfo.getAccount());
					return new ResultBean<GoodsInfoBean>(false, "道具数量不足！", null);
				}
				GoodsGetBean goodsGetBean = new GoodsGetBean();
				if (sumCreditBean.getSumCredit()>=GoodsInfoBean.getCreditsCost()) {
					goodsGetBean.setAccount(userInfo.getAccount());
					goodsGetBean.setServer(address.getServerName());
					goodsGetBean.setGoodsCode(GoodsInfoBean.getCode());
					goodsGetBean.setGetWay("兑换");
					goodsGetBean.setExchangeDate(new Date());
					goodsGetBean.setRoleName("无");
					goodsGetBean.setDomain("1");
					goodsGetBll.addGoodsGet(goodsGetBean);
					int i = sumCreditBean.getSumCredit();
					sumCreditBean.setSumCredit(i-GoodsInfoBean.getCreditsCost());
					creditBLL.setSumCredit(sumCreditBean);
					logger.info("炫舞吧XWBLotteryController-兑换-扣除积分后：{}", sumCreditBean+",账号：{}"+userInfo.getAccount());
					cn.gyyx.action.beans.novicecard.ResultBean<String> resultBean = xWBService.sendRewardForLotteryAndExchange(address.getCode(), address.getServerName(), userInfo, GoodsInfoBean.getGoodsName()+"#"+GoodsInfoBean.getGoodsCode(),GoodsInfoBean.getMailContent());
					//道具数量减一
					goodsBLL.updatePriceNum(Integer.parseInt(goodCode));
					result.setProperties(true, resultBean.getMessage(), null);
				}else{
					logger.info("炫舞吧XWBLotteryController-兑换-兑换资格检查为：{}不够道具积分"+GoodsInfoBean.getCreditsCost()+",账号：{}"+userInfo.getAccount());
					result.setProperties(false, "积分不足", null);
				}
			} catch (DLockException e) {
				logger.error("DLockException type:" + e.getType() + ",msg:" + e.getMessage());
				result.setProperties(false, "网络超时", null);
			}
		logger.info("炫舞吧XWBLotteryController-兑换-结果：" + String.valueOf(result));
		return result;
	}
	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getServers
	 * @Description: TODO 查讯兑换物品
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getExchangeInfo")
	@ResponseBody
	public ResultBean<List<GoodsInfoBean>> getExchangeInfo() {
		ResultBean<List<GoodsInfoBean>> result = new ResultBean<List<GoodsInfoBean>>(
				true, "显示兑换物品失败", null);
		List<GoodsInfoBean> goodsInfoBeanList = goodsBLL.getGoodsAll();
		result.setProperties(true, "查询成功", goodsInfoBeanList);
		logger.info("result", result);
		return result;
	}

   	@RequestMapping("/getGoodsAllRecord")
   	@ResponseBody
   	public ResultBean<List<GoodsGetBean>> getGoodsRecord(Model model) {
   		
   		ResultBean<List<GoodsGetBean>> resultBean = new ResultBean<List<GoodsGetBean>>();
		// 获得用户信息
		try {
			
			List<GoodsGetBean> list = goodsGetBll.getGoodsRecordTop();
			for (GoodsGetBean user : list) {
				String str = user.getAccount();
				if(0 < str.length() && str.length() <= 3){
					str = String.valueOf(str.charAt(0))+matchStr(str.length()-1);
				}else if(str.length() == 4){
					str = String.valueOf(str.charAt(0))+matchStr(str.length()-2)+String.valueOf(str.charAt(str.length()-1 ));
				}else if(str.length() >=5){
					str = str.substring(0, 2)+matchStr(str.length()-4)+str.substring(str.length()-2, str.length());
				}else{
					
				}
				user.setAccount(str);
			}
			resultBean.setProperties(true, "成功", list);

		} catch (Exception e) {
			logger.warn(e.getMessage());
			resultBean.setProperties(false, "取不到账户信息", null);
		}
		logger.info(" getAllLotteryInfoByUser resultBean", resultBean);
		return resultBean;
   	}
	@RequestMapping("/getGoodsUserRecord")
   	@ResponseBody
   	public ResultBean<List<GoodsGetBean>> getGoodsUserRecord(Model model, HttpServletRequest request) {
   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   		ResultBean<List<GoodsGetBean>> resultBean = new ResultBean<List<GoodsGetBean>>();
		// 获得用户信息
		
		// 获得用户信息
				try {
					UserInfo userInfo = SignedUser.getUserInfo(request);
					logger.info("UserInfo", userInfo);
					// 得到该用户获得的奖品
					List<GoodsGetBean> list = goodsGetBll.getGoodsUserRecord(userInfo.getAccount());
					for (GoodsGetBean goodsGetBean : list) {
						goodsGetBean.setStrExchangeDate(sdf.format(goodsGetBean.getExchangeDate()));
					}
					resultBean.setProperties(true, "成功", list);

				} catch (Exception e) {
					logger.warn("取不到账户信息");
					logger.warn(e.getMessage());
					resultBean.setProperties(false, "取不到账户信息", null);
				}
				logger.info(" getGoodsUserRecord resultBean", resultBean);
				return resultBean;
   	}
	
}
