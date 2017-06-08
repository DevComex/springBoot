/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：V1.214
 * @本类主要用途描述：业务拼接类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.xuanwuba;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wd9yearnovicebag.HdSendPresentLogBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CalendarInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CommentsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CreditsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsGetBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialShowBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionReceiveBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PlayerInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PraiseBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ResultIndexShowBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ResultSignBasicInfo;
import cn.gyyx.action.beans.xwbcreditsluckydraw.RewardReceiveLogBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SignInBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SignPrizeBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.SumCreditBean;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.wd9yearnovicebag.BagReceiveLogBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CommentsBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CreditBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsGetBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialAuditBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialInfoBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionReceiveBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PlayerinfoBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PraiseBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.RewardReceiveLogBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.SignInBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.SignPrizeBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.TimeBLL;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.service.newnovicecard.NoviceCardService;

public class XWBService {
	private static String activityName = "炫舞吧抽奖";
	private static int gameId = 11;
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XWBService.class);
	private MaterialAuditBll materialAuditBll = new MaterialAuditBll();
	private MaterialInfoBLL materialInfoBll = new MaterialInfoBLL();
	private PraiseBLL praiseBLL = new PraiseBLL();
	private CommentsBll commentsBll = new CommentsBll();
	private SignInBll signInBll = new SignInBll();
	private CreditBLL creditBLL = new CreditBLL();
	private RewardReceiveLogBLL rewardReceiveLogBLL = new RewardReceiveLogBLL();
	private TimeBLL timeBLL = new TimeBLL();
	private PlayerinfoBLL playerinfoBLL = new PlayerinfoBLL();
	private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private SignPrizeBll signPrizeBll = new SignPrizeBll();
	private NoviceCardService noviceCardService = new NoviceCardService();
	private BagReceiveLogBll bagReceiveLogBll = new BagReceiveLogBll();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private GoodsGetBll goodsGetBll = new GoodsGetBll();
	private MissionBLL missionBLL = new MissionBLL();
	private MissionReceiveBLL missionReceiveBLL = new MissionReceiveBLL();
	
	
	/**
	 * 签到发送奖励
	 * @param serverId
	 * @return
	 */
	public ResultBean<String> sendReward(Integer serverId, String serverName, UserInfo userInfo,String prizeType){
		ResultBean<String> result = new ResultBean<String>(false,"发送物品，未知错误","");
		//根据服务器Id获取服务器信息
		ServerInfoBean serverInfo = new ServerInfoBean();	
		try {
			logger.info("炫舞吧活动发送奖励sendReward方法——获取炫舞吧服务器信息，参数serverId："+serverId);
			serverInfo = callWebApiAgent
					.getXWBServerStatusFromWebApi(serverId);
			logger.info("炫舞吧活动发送奖励sendReward方法——获取炫舞吧服务器信息，结果：serverInfo："+serverInfo);
		} catch (Exception e) {
			// 记录WebAPI错误日志
			result.setProperties(false, "获取服务器信息出错!", "获取服务器信息出错getServerStatusFromWebApi");
			logger.error("炫舞吧活动发送奖励sendReward方法——获取炫舞吧服务器信息，异常结束，resultOfFunction", serverInfo);
			return result;
		}

		if (serverInfo == null) {
			result.setProperties(false, "服务器信息为空!", "服务器信息为空");
			return result;
		}
		//获取本次奖励的物品
		
		String sex = playerinfoBLL.getSexByAccount(userInfo.getAccount());
		logger.info("炫舞吧活动发送奖励sendReward方法——根据奖励类型查询签到奖励,参数prizeType："+prizeType+"; sex:"+sex);
		List<SignPrizeBean> list = signPrizeBll.getAccountPrize(prizeType, sex);
		//记录日志
		logger.info("炫舞吧活动发送奖励sendReward方法——记录rewardReceiveLog，参数，prizeType："+prizeType+"; userInfo.getAccount():"+userInfo.getAccount()
				+"; serverId:"+serverId+"; sex:"+sex);
		boolean setRewardLog = rewardReceiveLogBLL.addRewardReceiveLog(new RewardReceiveLogBean(prizeType, userInfo.getAccount(),
				serverId,  sex,new Date()));
		if(!setRewardLog){
			result.setProperties(false, "日志记录失败!", "rewardLog记录失败");
			return result;
		}
		//发送物品
		//记录是否有物品发送失败
		boolean flag = true;
		//记录有哪些物品发送失败
		String message = "";
		//为记录hd_send_present_log日志，获取活动Id
		Integer activityId = 0;
		logger.info("炫舞吧活动发送奖励sendReward方法——获取活动信息,参数:"+activityName);
		ResultBean<ActivityConfigBean> activityConfig = activityConfigBll.getConfigMessageByHdName(activityName);
		if(!activityConfig.getIsSuccess()){
			logger.debug("炫舞吧活动发送奖励sendReward方法——记录hd_send_present_log日志，获取活动信息,失败");
			result.setProperties(false, "获取活动信息失败!", activityConfig.getMessage());
			return result;
		}else{
			activityId = activityConfig.getData().getCode();
		}
		for(SignPrizeBean signPrizeBean : list){		
			//记录HdSendPresentLog
			HdSendPresentLogBean hdSendPresentLogBean = new HdSendPresentLogBean(activityId,
					activityName, 0, userInfo.getAccount(), gameId,
					serverId,serverName, "noRealPrize",
					signPrizeBean.getPrizeName(), new Date() , userInfo.getLoginIP());
			logger.info("炫舞吧活动发送奖励sendReward方法——记录hd_send_present_log日志，参数："+hdSendPresentLogBean);
			boolean setHdSendRewardLog = bagReceiveLogBll.setHdSendPresentLog(hdSendPresentLogBean);
			if(!setHdSendRewardLog){
				//如果发放失败，将标记为false，并将那个物品记录
				flag = false;
				message = message + signPrizeBean.getPrizeName()+"、";
				logger.debug("炫舞吧活动发送奖励sendReward方法——记录hd_send_present_log日志,失败");
				result.setProperties(false, "记录失败!", "hdSendLog记录失败");
				continue;
			}
			//发送
			//固定发送物品参数规则写法
			String prize = signPrizeBean.getPrizeName() + "#"+signPrizeBean.getPrizeCode();
			NoviceParameter parameter = new NoviceParameter(serverInfo, userInfo.getAccount(),
				gameId, prize);
			logger.info("炫舞吧活动发送奖励sendReward方法——发送物品！！，参数：parameter"+parameter);
			try{
				parameter.setMailContent(signPrizeBean.getMailContent());
				ResultBean<Integer> sendResult = noviceCardService.SendItemXWB(parameter);
				logger.info("炫舞吧活动发送奖励sendReward方法——发送物品！！，结果：sendResult:"+sendResult.getIsSuccess()+"  ;"+sendResult.getMessage()+"  ;"+sendResult.getData());
				if (!sendResult.getIsSuccess() || sendResult.getData() != 0) {
					//如果发放失败，将标记为false，并将那个物品记录
					flag = false;
					message = message + signPrizeBean.getPrizeName()+"、";
					result.setProperties(false, sendResult.getMessage(), "");
					logger.debug("炫舞吧活动发送奖励sendReward方法——发送物品失败！！参数：parameter"+parameter+"; result:"+result);
					continue;
				}
			}catch (Exception e) {
				//如果发放失败，将标记为false，并将那个物品记录
				flag = false;
				message = message + signPrizeBean.getPrizeName()+"、";
				result.setIsSuccess(false);
				logger.warn("炫舞吧活动发送奖励sendReward方法——发送物品异常！！catch："+e.toString());
				logger.debug("炫舞吧活动发送奖励sendReward方法——发送物品异常！！参数：parameter"+parameter+"; result:"+result);
				continue;
			}
			
			
		}
		//如果有记录为有物品领取失败
		if(!flag){
			result.setProperties(false,message+ "领取失败！", "");
			return result ;
		}
		result.setProperties(true, "领取成功！！", "");
		return result ;
	}
	
	
	/**
	 * 抽奖和兑换发送奖励
	 */
	public ResultBean<String> sendRewardForLotteryAndExchange(Integer serverId, String serverName, UserInfo userInfo,String prize,String mailContent){
		ResultBean<String> result = new ResultBean<String>(false,"发送物品，未知错误","");
		//根据服务器Id获取服务器信息
		ServerInfoBean serverInfo = new ServerInfoBean();	
		try {
			logger.info("炫舞吧活动抽奖和兑换发送奖励——获取炫舞吧服务器信息，参数serverId："+serverId);
			serverInfo = callWebApiAgent
					.getXWBServerStatusFromWebApi(serverId);
			logger.info("炫舞吧活动抽奖和兑换发送奖励——获取炫舞吧服务器信息，结果：serverInfo："+serverInfo);
		} catch (Exception e) {
			// 记录WebAPI错误日志
			result.setProperties(false, "获取服务器信息出错!", "获取服务器信息出错getServerStatusFromWebApi");
			logger.error("炫舞吧活动抽奖和兑换发送奖励——获取炫舞吧服务器信息，异常结束，resultOfFunction", serverInfo);
			return result;
		}

		if (serverInfo == null) {
			result.setProperties(false, "服务器信息为空!", "服务器信息为空");
			return result;
		}
		
		//发送物品
		//记录是否有物品发送失败
		boolean flag = true;
		//记录有哪些物品发送失败
		String message = "";
		//为记录hd_send_present_log日志，获取活动Id
		Integer activityId = 0;
		logger.info("炫舞吧活动抽奖和兑换发送奖励——获取活动信息,参数:"+activityName);
		ResultBean<ActivityConfigBean> activityConfig = activityConfigBll.getConfigMessageByHdName(activityName);
		if(!activityConfig.getIsSuccess()){
			logger.debug("炫舞吧活动抽奖和兑换发送奖励——记录hd_send_present_log日志，获取活动信息,失败");
			result.setProperties(false, "获取活动信息失败!", activityConfig.getMessage());
			return result;
		}else{
			activityId = activityConfig.getData().getCode();
		}
		
		//发送
		NoviceParameter parameter = new NoviceParameter(serverInfo, userInfo.getAccount(),
			gameId, prize);
		logger.info("炫舞吧活动抽奖和兑换发送奖励——发送物品！！，参数：parameter"+parameter);
		try{
			parameter.setMailContent(mailContent);
			
			ResultBean<Integer> sendResult = noviceCardService.SendItemXWB(parameter);
			logger.info("炫舞吧活动抽奖和兑换发送奖励——发送物品！！，结果：sendResult:"+sendResult.getIsSuccess()+"  ;"+sendResult.getMessage()+"  ;"+sendResult.getData());
			if (!sendResult.getIsSuccess() || sendResult.getData() != 0) {
				//如果发放失败，将标记为false，并将那个物品记录
				flag = false;
				message = message + prize;
				result.setProperties(false, sendResult.getMessage(), "");
				logger.debug("炫舞吧活动抽奖和兑换发送奖励——发送物品失败！！参数：parameter"+parameter+"; result:"+result);
			}
		}catch (Exception e) {
			//如果发放失败，将标记为false，并将那个物品记录
			flag = false;
			message = message + prize;
			result.setIsSuccess(false);
			logger.warn("炫舞吧活动抽奖和兑换发送奖励——发送物品异常！！catch："+e.toString());
			logger.debug("炫舞吧活动抽奖和兑换发送奖励——发送物品异常！！参数：parameter"+parameter+"; result:"+result);
		}

		//如果有记录为有物品领取失败
		if(!flag){
			return result ;
		}
		result.setProperties(true, "领取成功！", "");
		return result ;
	}
	
	
	
	
	/**
	 * 查询首页素材显示信息
	 * 
	 * @param materialType
	 *            素材类别
	 * @return List<MaterialShowBean>
	 */
	public ResultIndexShowBean getmaterialShow(String materialType,String userAccount) {
		logger.info("炫舞吧积分活动___查询首页素材显示信息,参数materialType：" + materialType);
		List<MaterialShowBean> materialShowList = new ArrayList<MaterialShowBean>();
		MaterialShowBean materialShowBean;
		// 获取审核表中首页显示数据
		List<MaterialAuditBean> materialAuditList = materialAuditBll
				.getMaterialShow(new MaterialAuditBean(materialType, 1));
		int length = 0;
		if (materialAuditList != null) {
			// 将相应的首页素材显示内容add到materialShowList中
			for (MaterialAuditBean materialAudit : materialAuditList) {
				String account = materialAudit.getAccount();
				String serverName = materialAudit.getServerName();
				Integer materialCode = materialAudit.getMaterialCode();
				// 获取素材表中素材图片的信息
				String materialPicture = null;
				MaterialInfoBean materialBean = materialInfoBll
						.getMaterialInfo(new MaterialInfoBean(materialCode));
				if (materialBean != null) {
					materialPicture = materialBean.getMaterialPicture();
					if(!materialBean.getIsShowIssuer()){
						account = "匿名玩家";
					}else{
						if(0 < account.length() && account.length() <= 3){
							account = String.valueOf(account.charAt(0))+matchStr(account.length()-1);
						}else if(account.length() == 4){
							account = String.valueOf(account.charAt(0))+matchStr(account.length()-2)+String.valueOf(account.charAt(account.length()-1 ));
						}else if(account.length() >=5){
							account = account.substring(0, 2)+matchStr(account.length()-4)+account.substring(account.length()-2, account.length());
						}else{
							
						}
					}
				}
				// 评论总数
				Integer commentsCount = commentsBll
						.getCommentsCount(new CommentsBean(materialCode));
				// 点赞总数
				Integer praiseCount = praiseBLL.getCountPraise(materialCode);
				
				//是否有点赞
				Integer praiseOwn = 0;
				//是否有评论
				Integer commentsOwn = 0;
				
				if(userAccount != ""){
					praiseOwn = praiseBLL.getPraiseCountByUser(userAccount,materialCode);
					commentsOwn = commentsBll.getCommentCountByUser(userAccount,materialCode);
				}
				
				materialShowBean = new MaterialShowBean(materialCode,
						materialPicture, account, serverName, commentsCount,
						praiseCount,praiseOwn,commentsOwn);
				logger.info("炫舞吧积分活动___查询首页素材显示信息,分条查询结果：" + materialShowBean);
				materialShowList.add(materialShowBean);
				length++;
			}
		}
		ResultIndexShowBean resultBean = new ResultIndexShowBean(
				materialShowList, length);
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
	 * 查询签到页登陆信息（登陆信息以及签到奖励信息）
	 * @param account
	 * @return
	 */
	public ResultBean<ResultSignBasicInfo> signPageAccountInfo(String account){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ResultBean<ResultSignBasicInfo> resultBean = new ResultBean<ResultSignBasicInfo>(false,"获取信息未知错误",null);
		logger.info("炫舞吧积分活动___查询签到页登陆信息,参数account："+account);
		ResultSignBasicInfo resultSignBasicInfo = new ResultSignBasicInfo();
		Integer credit = 0;
		Integer weekSignCount = 0;
		Integer allSignCount  = 0;
		String sex =null;
		Boolean isSign ;
		String serverName= null;
		Integer serverId = 0;
		//获取account的总积分
		try{
			credit = creditBLL.getSumCredit(account);
			if (credit == -1) {
				credit = 0;
			}
		} catch (Exception e) {
			resultBean.setMessage("获取account的总积分错误");
			return resultBean;
		}
		// 获得本周签到天数
		try {
			weekSignCount = signInBll.getWeekSignCount(account);
		} catch (Exception e) {
			resultBean.setMessage("获得本周签到天数错误");
			return resultBean;
		}
		// 获得总签到天数
		try {
			allSignCount = signInBll.getSignInCount(new SignInBean(account));
		} catch (Exception e) {
			resultBean.setMessage("获得总签到天数错误");
			return resultBean;
		}	
		//获得用户个人信息中的性别
		try{
			PlayerInfoBean playerInfoBean = playerinfoBLL.getInfoByAccount(account);
			if(playerInfoBean != null){
				try{
					sex = playerInfoBean.getRoleSex();
				}catch(Exception e){
					sex = null;
				}
				try{
					serverName = playerInfoBean.getServerName();
				}catch(Exception e){
					serverName = null;
				}
				try{
					serverId = playerInfoBean.getServerId();
				}catch(Exception e){
					serverName = null;
				}
			}		
		}catch(Exception e){
			resultBean.setMessage("获得用户个人信息中的性别错误");
			return resultBean;
		}	
		//获得用户当天签到信息
		try{
			//本人今天签到记录
			int count = signInBll.getSignInCount(new SignInBean(account ,sdf.parse(sdf.format(new Date()))));
			if(count >=1){
				isSign = true;
			}else{
				isSign = false;
			}
		}catch(Exception e){
			resultBean.setMessage("获得用户个人信息中的性别错误");
			return resultBean;
		}	
		resultSignBasicInfo.SetProperties(weekSignCount, allSignCount, credit, sex, isSign,serverName,serverId);
		resultBean.setProperties(true, "", resultSignBasicInfo);
		logger.info("炫舞吧积分活动___查询签到页登陆信息,结果credit："+credit+"; weekSignCount:"+weekSignCount+"; allSignCount:"+allSignCount
				+";  credit:"+credit+"; sex:"+sex+"; isSign:"+isSign+"; serverName:"+serverName);
		return resultBean;
	}
	/**
	 * 查询签到页签到奖励信息
	 * @param account
	 * @return
	 */
	public ResultBean<ResultSignBasicInfo> signPageRewardInfo(String account){
		ResultBean<ResultSignBasicInfo> resultBean = new ResultBean<ResultSignBasicInfo>(false,"获取信息未知错误",null);
		logger.info("炫舞吧积分活动___查询签到页签到奖励信息,参数account："+account);
		ResultSignBasicInfo resultSignBasicInfo = new ResultSignBasicInfo();
		//获取用户签到奖励信息
		Integer monthSignCount = 0;
		List<RewardReceiveLogBean> receiveList = new ArrayList<RewardReceiveLogBean>();
		//签到两天奖励
		Boolean twoDaysReward = true;
		//签到五天奖励
		Boolean fiveDaysReward = true;
		//签到十天奖励
		Boolean tenDaysReward = true;
		//签到十五天奖励
		Boolean fifteenDaysReward = true;
		//签到满勤奖励
		Boolean allDaysReward = true;
		//用户本月累计签到天数
		try{
			monthSignCount = signInBll.geMonthSignCount(account);
		}catch(Exception e){
			resultBean.setMessage("获得本月签到天数错误");
			return resultBean;
		}
		//用户本月已领了哪些奖励
		String startTime = timeBLL.getMinMonthDate();
		String endTime = timeBLL.getMaxMonthDate();
		logger.info("炫舞吧积分活动___查询签到页签到奖励信息,startTime"+startTime+"; endTime"+endTime);
		try{
			receiveList = rewardReceiveLogBLL.getRewardReceiveLog(new RewardReceiveLogBean(account ,startTime ,endTime ));
		}catch(Exception e){
			resultBean.setMessage("获得本月已领了哪些奖励错误");
			return resultBean;
		}
		//设置用户奖励领取信息
		//根据本月签到天数
		if(monthSignCount<2){
			twoDaysReward = false;
		}
		if(monthSignCount<5){
			fiveDaysReward = false;
		}
		if(monthSignCount<10){
			tenDaysReward = false;
		}
		if(monthSignCount<15){
			fifteenDaysReward = false;
		}
		if(monthSignCount<timeBLL.getMonthDays()){
			allDaysReward = false;
		}
		
		if(receiveList != null){
			for(RewardReceiveLogBean rewardReceiveLogBean:receiveList){
				switch(rewardReceiveLogBean.getRewardType()){
					case "twoDaysReward" : twoDaysReward = false;break;
					case "fiveDaysReward" : fiveDaysReward = false;break;
					case "tenDaysReward" : tenDaysReward = false;break;
					case "fifteenDaysReward" : fifteenDaysReward = false;break;
					case "allDaysReward" : allDaysReward = false;break;
				}
			}
		}
		logger.info("炫舞吧积分活动___查询签到页签到奖励信息,结果：twoDaysReward"+twoDaysReward+"; fiveDaysReward"+fiveDaysReward+"; tenDaysReward"+tenDaysReward
				+"; fifteenDaysReward"+fifteenDaysReward+"; allDaysReward"+allDaysReward);
		resultSignBasicInfo.setRewardProperties(twoDaysReward, fiveDaysReward, tenDaysReward, fifteenDaysReward, allDaysReward,receiveList);	
		resultBean.setProperties(true, "", resultSignBasicInfo);
		return resultBean;
	}

	/**
	 * 获取日历签到信息
	 * 
	 * @author fanyongliang
	 * @param year
	 * @param month
	 * @return
	 */
	public ResultBean<CalendarInfoBean> getCalendarInfo(String account,
			Integer year, Integer month) {
		ResultBean<CalendarInfoBean> resultBean = new ResultBean<CalendarInfoBean>(
				false, "获取信息未知错误", null);
		CalendarInfoBean calendarInfoBean = new CalendarInfoBean();
		logger.info("炫舞吧积分活动___XWBService获取日历签到信息,参数account:"+account+",year:" + year + ",month:"
				+ month);
		Calendar c1 = Calendar.getInstance();
		c1.set(year, month - 1, 1);
		Calendar c2 = Calendar.getInstance();
		c2.set(year, month, 1);
		long c2s = c2.getTimeInMillis();
		long c1s = c1.getTimeInMillis();
		long day = 1 * 24 * 60 * 60 * 1000;
		long days = (c2s - c1s) / day;
		logger.info("炫舞吧积分活动___XWBService获取日历签到信息,当月天数:" + days + "天");
		calendarInfoBean.setMonthDays((int) days);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int firstWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		logger.info("炫舞吧积分活动___XWBService获取日历签到信息,当月第一天星期:" + firstWeek);
		calendarInfoBean.setWeekOfFirstDay(firstWeek);
		String firstDay = year + "-" + month + "-1";
		logger.info("炫舞吧积分活动___XWBService获取日历签到信息,当月第一天:" + firstDay);
		String lastDay = year + "-" + month + "-" + days;
		logger.info("炫舞吧积分活动___XWBService获取日历签到信息,当月最后一天:" + lastDay);
		List<SignInBean> list = signInBll.getSignInInfoBetweenFLDay(account,
				firstDay, lastDay);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int dayArray[] = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			dayArray[i] = Integer.parseInt(sdf.format(
					list.get(i).getSignInDate())
					.substring(
							sdf.format(list.get(i).getSignInDate())
									.lastIndexOf("-") + 1));
		}
		Arrays.sort(dayArray);
		logger.info("炫舞吧积分活动___XWBService获取日历签到信息,当月签到天数:" + dayArray.length);
		calendarInfoBean.setDayArray(dayArray);
		calendarInfoBean.setNowDay(Integer.parseInt(sdf.format(new Date()).substring(sdf.format(new Date()).lastIndexOf("-") + 1)));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		calendarInfoBean.setNowdate(format.format(new Date()));
		resultBean.setProperties(true, "获取日历信息成功", calendarInfoBean);
		return resultBean;
	}
	/**
	 * 签到得积分
	 * @param account
	 * @return
	 */
	public ResultBean<String> signIn(String account){
		logger.info("炫舞吧积分活动___签到得积分参数：account："+account);
		ResultBean<String> resultBean = new ResultBean<String>(false,"签到得积分未知错误",null);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			
			//得到今天的开始时间和结束时间
			String startTime = sdf.format(date)+" 00:00:00";
			String endTime = sdf.format(date)+" 23:59:59";
			//查询此用户今天有无签到
			Integer num = creditBLL.getCreditsNum(new CreditsBean(account, "签到", startTime, endTime));
			//如果已经签到，则直接返回
			if(num>0){
				resultBean.setProperties(false,"今天您已经签到","今天您已经签到");
				return resultBean ;
			}
			//签到一次一积分
			creditBLL.judge(new CreditsBean(account, 1 , "签到", date));
			logger.info("炫舞吧XWBService-每日签到增加积分-:account{}"+account+"，增加积分{}"+1);
			
		}catch(Exception e){
			resultBean.setProperties(false,"签到得积分积分增加错误",null);
			return resultBean;
		}	
		try{
			signInBll.addSignIn(new SignInBean(sdf.format(date), account));
		}
		catch(Exception e){
			resultBean.setProperties(false,"签到得积分签到增加失败",null);
			return resultBean;
		}
		try{	
			//如果满勤，5积分
			if(signInBll.isAllMonthSign(account)){
				logger.info("炫舞吧XWBService-签到满勤增加积分-:account{}"+account+"，增加积分{}"+5);
				creditBLL.judge(new CreditsBean(account, 5 , "满勤", date));
			}	
		}catch(Exception e){
			resultBean.setProperties(false,"签到得满勤积分积分增加错误",null);
			return resultBean;
		}	
		
		resultBean.setProperties(true,"签到成功","");
		List<MissionReceiveBean> receiveBeanList = missionReceiveBLL.getMissionReceiveByAccountAndType(account, "签到");
		if(receiveBeanList!=null){
			for(MissionReceiveBean missionReceiveBean : receiveBeanList){
				MissionBean missionBean = missionBLL.getMissionInfoByCode(missionReceiveBean.getMissionCode());
			
					if(missionReceiveBean.getCount()+1>=missionBean.getSignNum()){
						missionReceiveBLL.updateCompleteStatus(missionReceiveBean.getCode(), "已完成",new Date());
						missionReceiveBLL.updateCount(missionReceiveBean.getCode(), missionReceiveBean.getCount()+1);
						CreditsBean credit = new CreditsBean();
						credit.setAccount(account);
						credit.setCredits(missionBean.getMissionCredits());
						credit.setEnterTime(new Date());
						credit.setType("签到任务");
						creditBLL.addCredits(credit);
						SumCreditBean sumCreditBean = creditBLL.getCreditByAccount(account);
						if(sumCreditBean!=null){
							int count = sumCreditBean.getSumCredit() + missionBean.getMissionCredits();
							sumCreditBean.setSumCredit(count);
							creditBLL.setSumCredit(sumCreditBean);
							logger.info("炫舞吧XWBService-签到任务增加积分-:account{}"+account+"，增加积分{}"+missionBean.getMissionCredits());
						}else{
							SumCreditBean sumCredit = new SumCreditBean();
							sumCredit.setAccount(account);
							sumCredit.setSumCredit(missionBean.getMissionCredits());
							creditBLL.addSumCredit(sumCredit);
							logger.info("炫舞吧XWBService-签到任务增加积分-:account{}"+account+"，增加积分{}"+missionBean.getMissionCredits());
						}
						
					}else{
						missionReceiveBLL.updateCount(missionReceiveBean.getCode(), missionReceiveBean.getCount()+1);
					}
			}
				
			}
		return resultBean ;
	}
	/**
	 * 征集列表
	 * @param account
	 * @return
	 */
	public  List<MaterialAuditBean>  getMaterialUserShow(String account,String materialType){
		 List<MaterialAuditBean> list = materialAuditBll.getMaterialUserShow(account);
		 List<MaterialAuditBean> listM=new ArrayList<MaterialAuditBean>();
		 if(list!=null&&list.size()!=0){
			 for(MaterialAuditBean materialAuditBean:list){
				 if(materialAuditBean!=null){
					 if(materialAuditBean.getMaterialType().equals(materialType)){
						 listM.add(materialAuditBean);
					 }
				 }
			 }
		 }
		return listM;
		
	}
	/**
	 * 中奖兑换
	 * @param account
	 * @return
	 */
	public List<GoodsGetBean> getGoodGetByUser(String account,String getWay){
		List<GoodsGetBean> list = goodsGetBll.getGoodsGetByUser(account);
		List<GoodsGetBean> listM = new ArrayList<GoodsGetBean>();
		 if(list!=null&&list.size()!=0){
			 for(GoodsGetBean goodsGetBean:list){
				 if(goodsGetBean!=null){
					 if(goodsGetBean.getGetWay().equals(getWay)){
						 listM.add(goodsGetBean);
					 }
				 }
				 
			 }
		 }
		return listM;
		
	}
	/**
	 * 修改个人信息
	 * @param account
	 * @return
	 */
	public Integer updateUserInfo(PlayerInfoBean playerInfoBean){
		PlayerInfoBean playerInfoBean1 = playerinfoBLL.getInfoByAccount(playerInfoBean.getAccount());
		Integer i = 0;
		if(playerInfoBean1==null){
			i = playerinfoBLL.addPlayer(playerInfoBean);
		}else{
			i = playerinfoBLL.updateplayerinfo(playerInfoBean);
		}
		return i;
		
	}
	/**
	 * 获取任务集合
	 * @param 
	 * @return
	 */
	public List<MissionBean> getMissionAll(String acount){
		MissionReceiveBean missionReceiveBean =null;
		List<MissionBean> list = missionBLL.getMissionAll();
		List<MissionBean> listNew = new ArrayList<MissionBean>();
		if(list!=null){
			for(MissionBean missionBean:list){
				missionReceiveBean = missionReceiveBLL.getMissionReceiveBycode(missionBean.getCode(), acount);
				if(missionReceiveBean==null){
					missionBean.setStatus("未领取");
				}
				else{
					missionBean.setStatus(missionReceiveBean.getCompleteStatus());
				}
				
			}
			for(MissionBean missionBean:list){
				if(missionBean.getStatus().equals("未完成")){
					listNew.add(missionBean);
				}
			}
			for(MissionBean missionBean:list){
				if(missionBean.getStatus().equals("未领取")){
					listNew.add(missionBean);
				}
			}
			for(MissionBean missionBean:list){
				if(missionBean.getStatus().equals("已完成")){
					listNew.add(missionBean);
				}
			}
		}
		
		return listNew;
		
	}
	public Integer getMissionCount(String acount){
		MissionReceiveBean missionReceiveBean =null;
		Integer i=0;
		List<MissionBean> list = missionBLL.getMissionAll();
		List<MissionBean> listNew = new ArrayList<MissionBean>();
		if(list!=null){
			for(MissionBean missionBean:list){
				missionReceiveBean = missionReceiveBLL.getMissionReceiveBycode(missionBean.getCode(), acount);
				if(missionReceiveBean==null){
					missionBean.setStatus("未领取");
				}
				else{
					missionBean.setStatus(missionReceiveBean.getCompleteStatus());
				}
				
			}
			for(MissionBean missionBean:list){
				if(missionBean.getStatus().equals("未完成")){
					listNew.add(missionBean);
				}
			}
			i = listNew.size();
		}
		
		return i;
		
	}
	
}
