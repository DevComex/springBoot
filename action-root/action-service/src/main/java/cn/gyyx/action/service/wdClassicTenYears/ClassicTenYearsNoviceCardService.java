/*************************************************
       Copyright ©, 2015, GY Game
       Author: 柳佳琦
       Created: 2016年4月5日
       Note：
************************************************/
package cn.gyyx.action.service.wdClassicTenYears;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.novicecard.ErWeiMaBean;
import cn.gyyx.action.beans.novicecard.NoviceCardBean;
import cn.gyyx.action.beans.novicecard.NoviceCardMark;
import cn.gyyx.action.beans.novicecard.NoviceCardReceivGiftLog;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.novicecard.VirtualItemBean;
import cn.gyyx.action.beans.novicecard.NovicCardConfig.NoviceActionType;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.novicecard.ErWeiMaBLL;
import cn.gyyx.action.bll.novicecard.GetMD5PasswordBLL;
import cn.gyyx.action.bll.novicecard.NoviceCardBLL;
import cn.gyyx.action.bll.novicecard.NoviceCardReceivGiftLogNewBLL;
import cn.gyyx.action.bll.novicecard.ReceiveLogBll;
import cn.gyyx.action.bll.novicecard.ServerBLL;
import cn.gyyx.action.bll.novicecard.VirtualItemBll;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.core.Ip;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.service.novicecard.NoviceCardMutex;
import cn.gyyx.service.novicecard.NoviceCardService;

/**
 * @ClassName: ClassicTenYearsNoviceCardService
 * @Description: TODO 2699经典十年新手卡业务逻辑
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年4月5日 下午8:36:45
 */
public class ClassicTenYearsNoviceCardService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoviceCardService.class);
	private GetMD5PasswordBLL getMD5PasswordBll = new GetMD5PasswordBLL();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private NoviceCardBLL noviceCardBll = new NoviceCardBLL();
	private NoviceCardReceivGiftLogNewBLL noviceCardReceivGiftLogNewBLL = new NoviceCardReceivGiftLogNewBLL();
	private ReceiveLogBll receiveLogBll = new ReceiveLogBll();
	private ErWeiMaBLL erWeiMaBll = new ErWeiMaBLL();

	public ResultBean<String> sendNoviceCard(NoviceParameter noviceParameter,HttpServletRequest request) {
		logger.debug("noviceParameter", noviceParameter);
		ResultBean<String> resultOfFunction = new ResultBean<String>();
		resultOfFunction.setIsSuccess(false);
		int flag = 0;//0表示来源于pc端新手卡激活 1表示移动端
		
		// 取活动配置信息，同时 检查了活动的开户和关闭时间
		ResultBean<ActivityConfigBean> resultParameter = activityConfigBll
				.getConfigMessage(noviceParameter.getHdName());
		if (!resultParameter.getIsSuccess()) {
			resultOfFunction.setProperties(false, resultParameter.getMessage(),
					"");
			logger.debug("resultOfFunction", resultOfFunction);
			return resultOfFunction;
		}
		
		//获取服务器信息
		ServerInfoBean serverInfo = new ServerInfoBean();
		try {
			serverInfo = callWebApiAgent
					.getServerStatusFromWebApi(noviceParameter.getServerId());
			if (serverInfo == null) {
				resultOfFunction.setMessage("服务器无效！");
				return resultOfFunction;
			}else{
				if(serverInfo.getErrorMessage() != null && !"".equals(serverInfo.getErrorMessage())){
					resultOfFunction.setMessage(serverInfo.getErrorMessage());
					return resultOfFunction;
				}
			}
		} catch (Exception e) {
			resultOfFunction.setProperties(false, "校验服务器信息失败！", "");
			logger.warn("校验服务器信息失败！",e);
			return resultOfFunction;
		}
		noviceParameter.setServerInfo(serverInfo);
				
		//判断新手卡存在与卡号是否有效
		ResultBean<NoviceCardBean> resultNovice = new ResultBean<NoviceCardBean>();
		resultNovice.setProperties(false, "判断新手卡条件未知错误", null);
		ResultBean<ErWeiMaBean> resultErWeiMaNovice = new ResultBean<ErWeiMaBean>();
		resultErWeiMaNovice.setProperties(false, "判断二维码新手卡条件未知错误", null);
		// 判断二维码与新手卡
		if (noviceParameter.getCard().toCharArray().length == 14) {
			noviceParameter.setMark(NoviceCardMark.erWeiMa);
			ErWeiMaBean erWeiMa = new ErWeiMaBean(noviceParameter.getCard(),
					noviceParameter.getBatchNo(), noviceParameter.getGameId());
			resultErWeiMaNovice = erWeiMaBll
					.CheckErWeiCardNoInfoIsValid(erWeiMa);
			if (!resultErWeiMaNovice.getIsSuccess()) {
				resultOfFunction.setMessage(resultErWeiMaNovice.getMessage());
				logger.debug("resultOfFunction", resultOfFunction);
				return resultOfFunction;
			}
			// 设置给玩家发放的物品编号
			noviceParameter.setLimitItemId(resultErWeiMaNovice.getData()
					.getItemId());
			noviceParameter
					.setTypeId(resultErWeiMaNovice.getData().getTypeId());
		} else if (noviceParameter.getCard().toCharArray().length == 12) {
			noviceParameter.setMark(NoviceCardMark.noviceCard);
			resultNovice = noviceCardBll.CheckCardNoInfoIsValid(
					noviceParameter.getCard(), noviceParameter.getGameId(),
					noviceParameter.getBatchNo());
			if (!resultNovice.getIsSuccess()) {
				resultOfFunction.setMessage(resultNovice.getMessage());
				logger.debug("resultOfFunction", resultOfFunction);
				return resultOfFunction;
			}
			// 设置给玩家发放的物品编号
			noviceParameter.setLimitItemId(resultNovice.getData().getItemId());
			noviceParameter.setTypeId(resultNovice.getData().getTypeId());
		} else {
			resultOfFunction.setMessage("卡号格式不正确");
			logger.debug("resultOfFunction", resultOfFunction);
			return resultOfFunction;
		}
		//----------------------------------------------------------
		// 与新手礼包活动互斥  （相同活动礼包互斥）
		// 如果checkGet查询结果：用户已经领过、IsSuccess()：true
		// 判断互斥
		// 判断用户是否在此服务器上领取过礼包
		//判断移动端是否有
		// 判断用户是否在此服务器上领取过新手卡
		ResultBean<String> mutexResult = NoviceCardMutex.judgeMutex(noviceParameter, NoviceActionType.card);
		if(!mutexResult.getIsSuccess()){
			return mutexResult;
		}
		logger.info(" ReceiveV3 Account:{0}", noviceParameter.getAccount());
		
		// 设置激活检查方法
		noviceParameter.setCheckType(resultParameter.getData().getCheckType());
		// 检查激活情况 并帮手动激活
		if (noviceParameter.getCheckType().equals("ActivationCheck")) {
			//判断是否激活
			cn.gyyx.action.beans.ResultBean<String> activatedResult = new CallWebApiAgent().accountIsActivated(noviceParameter.getUserInfo().getAccount()
					,String.valueOf(noviceParameter.getServerId()));
			if(activatedResult.getIsSuccess()){
				if(activatedResult.getMessage().equals("already")){
					try {
						Date activateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(activatedResult.getData());
						Date nowDate = new Date();
						long tempTime = (nowDate.getTime()-activateDate.getTime())/1000;
						if(tempTime>30*24*60*60){  //激活超过30天
							resultOfFunction.setProperties(false, "您激活该区组已超过30天，无法领取新手卡。", "您激活该区组已超过30天，无法领取新手卡。");
							return resultOfFunction;
						}
					} catch (ParseException e) {
						resultOfFunction.setProperties(false, "激活失败了!", "激活失败了!");
						return resultOfFunction;
					}
				}else{
					// TODO:获取用户密码MD5版本
					if(StringUtils.isBlank(noviceParameter.getPassWord())){
						ResultBean<String> resultpass = getMD5PasswordBll
								.getPassword(noviceParameter.getUserInfo().getLoginID());
						if (resultpass.getIsSuccess()
								&& "".equals(resultpass.getMessage())) {
							noviceParameter.setPassWord(resultpass.getData());
						} else {
							resultOfFunction.setProperties(false, "激活服务器失败，请到社区激活服务器",
									"");
							logger.debug("resultOfFunction", resultOfFunction);
							return resultOfFunction;
						}
					}else{
						flag = 1;
					}
					resultOfFunction = CallWebServiceAgent
							.wenDaoManualActivate(noviceParameter,Ip.getCurrent(request).asString());
					if (!resultOfFunction.getIsSuccess()) {
						logger.debug("resultOfFunction", resultOfFunction);
						return resultOfFunction;
					}
				}
			}else{
				resultOfFunction.setProperties(false, "激活失败!", "激活失败!");
				return resultOfFunction;
			}
		} 
		noviceParameter.setFlag(flag);
		
		// 检查领取新手卡相关判断条件的正确性（帐号信息，卡号信息和服务器信息）
		ResultBean<VirtualItemBean> reusltVI = new VirtualItemBll()
				.getVirtualItemByCode(noviceParameter.getLimitItemId());
		if (!reusltVI.getIsSuccess()) {
			resultOfFunction.setMessage("检查领取新手卡关联物品失败");
			resultOfFunction.setIsSuccess(false);
			logger.debug("resultOfFunction", resultOfFunction);
			return resultOfFunction;
		}
		noviceParameter.setVirtualItemCode(reusltVI.getData().getItemName());
		noviceParameter.setVirtualItemName(reusltVI.getData().getItemNote());
		resultOfFunction = this.ReceiveOperate(noviceParameter);
		logger.debug("resultOfFunction", resultOfFunction);
		return resultOfFunction;
	}
	/**
	 * 
	 * @日期：2014年12月16日
	 * @Title: ReceiveOperate
	 * @Description: TODO
	 * @param parameter
	 * @return ResultBean<String>
	 */
	public ResultBean<String> ReceiveOperate(NoviceParameter parameter) {
		logger.debug("parameter", parameter);
		ResultBean<String> result = new ResultBean<String>();
		try {
			try(DistributedLock lock = new DistributedLock("wd_seven_noviceCard")){
				lock.weakLock(30,10);
				result = receiveLogBll.setReceiveLog(parameter,
					parameter.getVirtualItemCode(), parameter.getTypeId());
			}catch(DLockException e){
				logger.error("recard novic card log eror!",e);
				logger.error("DLockException type:" + e.getType() + ",msg:" + e.getMessage());
				result.setProperties(false, "发送新手卡礼包失败,请稍后重试!", "发送新手卡礼包失败,请稍后重试!");
				return result;
			}
			if (!result.getIsSuccess()) {
				logger.debug("result", result);
				return result;
			}
			ResultBean<Integer> sendResult = this.SendItem(parameter);
			//需求 2699二零一六 移动端需要记录 新注册用户和老用户每日激活新手卡量   由于novice_card_receive_log日志未记录详细信息且不想修改存储过程 所以单建一张表统计
			try{
				NoviceCardReceivGiftLog log = new NoviceCardReceivGiftLog();
				log.setAccountName(parameter.getAccount());
				log.setUserId(parameter.getUserInfo().getUserId());
				log.setGameId(parameter.getGameId());
				log.setServerId(parameter.getServerId());
				log.setServerName(parameter.getServerInfo().getValue().getServerName());
				log.setActivityId(parameter.getActivityId());
				log.setActivityName(parameter.getHdName());
				log.setBatchNo(parameter.getBatchNo());
				log.setCreateDate(new Date());
				log.setFlag(parameter.getFlag());
				log.setCardNo(parameter.getCard());
				log.setResponseResult(sendResult.getData()+"");
				noviceCardReceivGiftLogNewBLL.insert(log);
			}catch(Exception e){
				logger.error("记录新手卡新老用户每日激活新手卡日志失败！", e);
			}
			if (!sendResult.getIsSuccess() || sendResult.getData() != 0) {
				result.setProperties(false, sendResult.getMessage(), "");
				logger.debug("result", result);
				return result;
			}
		} catch (Exception e) {
			result.setIsSuccess(false);
			logger.warn(e.toString());
			logger.debug("result", result);
			return result;
		}
		// 更新日志
		if (!result.getIsSuccess()) {
			result.setProperties(false, result.getMessage(), "");
			logger.debug("result", result);
			return result;
		}
		result.setProperties(true, "恭喜您获得了" + parameter.getVirtualItemName(),
				"恭喜您获得了" + parameter.getVirtualItemName());
		logger.debug("result", result);
		return result;
	}
	/**
	 * 
	 * @日期：2014年12月15日
	 * @Title: SendItem
	 * @Description: TODO 发送物品方法
	 * @param parameter
	 * @return ResultBean<Integer>
	 */

	public ResultBean<Integer> SendItem(NoviceParameter parameter) {
		logger.debug("发送物品方法,parameter:"+ parameter);
		ResultBean<Integer> result = new ResultBean<Integer>(false, "发送物品未知错误",
				-2147483648);
		try {
			ProcessResult processResult = new ProcessResult();
			Calendar c = java.util.Calendar.getInstance(TimeZone.getDefault(),
					Locale.CHINA);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			c.add(Calendar.YEAR, 1);
			java.util.Date newdate = c.getTime();
			String expiredTime = formatter.format(newdate);
			processResult = CallWebServiceAgent.givePresents(
					parameter.getGameId(), parameter.getAccount(),
					parameter.getVirtualItemCode(), expiredTime, "",
					parameter.getServerInfo());
			if (processResult != null) {
				result.setProperties(true, processResult.getDescription(),
						processResult.getErrorCode());
			}
		} catch (Exception e) {
			logger.warn(e.toString());
			result.setProperties(false, "网络异常，领取失败", -1);
		}
		logger.debug("result", result);
		return result;
	}
}
