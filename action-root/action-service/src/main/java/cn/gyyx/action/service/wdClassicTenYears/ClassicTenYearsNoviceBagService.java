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

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.novicecard.NovicCardConfig.NoviceActionType;
import cn.gyyx.action.beans.wd9yearnovicebag.BagActivityConfigBean;
import cn.gyyx.action.bll.novicecard.GetMD5PasswordBLL;
import cn.gyyx.action.bll.novicecard.ServerBLL;
import cn.gyyx.action.bll.wd9yearnovicebag.BagActivityConfigBll;
import cn.gyyx.action.bll.wd9yearnovicebag.BagReceiveLogBll;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.seven.SevenNoviceBagService;
import cn.gyyx.core.Ip;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.service.novicecard.NoviceCardMutex;

/**
 * @ClassName: ClassicTenYearsNoviceBagService
 * @Description: TODO 2699经典十年礼包业务逻辑
 * @author 柳佳琦 liujiaqi@gyyx.cn
 * @date 2016年4月5日 下午8:36:15
 */
public class ClassicTenYearsNoviceBagService {
	private GetMD5PasswordBLL getMD5PasswordBll = new GetMD5PasswordBLL();
	private BagActivityConfigBll bagActivityConfigBll = new BagActivityConfigBll();
	private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private BagReceiveLogBll bagReceiveLogBll = new BagReceiveLogBll();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(SevenNoviceBagService.class);
	/**
	 * 
	 * @日期：2014年12月12日
	 * @Title: sendNoviceCard
	 * @Description: TODO 发送新手卡主方法
	 * @param hdName
	 * @param serverId
	 * @param card
	 * @param batchNo
	 * @param passWord
	 * @return ResultBean<String>
	 */

	public ResultBean<String> sendNoviceBag(NoviceParameter noviceParameter,HttpServletRequest request) {
		logger.debug("noviceParameter", noviceParameter);
		ResultBean<String> resultOfFunction = new ResultBean<String>();
		resultOfFunction.setIsSuccess(false);
		// 取活动配置信息，同时 检查了活动的开户和关闭时间
		ResultBean<BagActivityConfigBean> resultParameter = bagActivityConfigBll
				.getConfigMessage(noviceParameter.getHdName());
		if (!resultParameter.getIsSuccess()) {
			resultOfFunction.setProperties(false, resultParameter.getMessage(),
					"");
			return resultOfFunction;
		}
		noviceParameter.setVirtualItemType("VirtualPresent");
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
				
		//----------------------------------------------------------
		// 判断互斥
		// 判断用户是否在此服务器上领取过礼包
		ResultBean<String> mutexResult = NoviceCardMutex.judgeMutex(noviceParameter, NoviceActionType.bag);
		if(!mutexResult.getIsSuccess()){
			return mutexResult;
		}
		//----------------------------------------------------------
		logger.debug(" ReceiveV3 Account:{0}", noviceParameter.getAccount());

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
							resultOfFunction.setProperties(false, "只有激活时间小于30天才可以领取礼包!", "只有激活时间小于30天才可以领取礼包!");
							return resultOfFunction;
						}
					} catch (ParseException e) {
						resultOfFunction.setProperties(false, "激活失败了!", "激活失败了!");
						return resultOfFunction;
					}
				}else{
					// TODO:获取用户密码MD5版本
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
					resultOfFunction = CallWebServiceAgent
							.wenDaoManualActivate(noviceParameter,Ip.getCurrent(request).asString());
					if (!resultOfFunction.getIsSuccess()) {
						logger.debug("resultOfFunction", resultOfFunction);
						return resultOfFunction;
					}
				}
			}else{
				resultOfFunction.setProperties(false, "激活失败!!", "激活失败!!");
				return resultOfFunction;
			}
		} else {
			// TODO:如果不是通过检查激活时间代码还未实现
			resultOfFunction.setMessage("pzcw激活失败");
			logger.debug("resultOfFunction", resultOfFunction);
			return resultOfFunction;
		}

		noviceParameter
				.setVirtualItemCode(resultParameter.getData().getPrize());
		resultOfFunction = this.ReceiveOperate(noviceParameter);
		logger.debug("resultOfFunction", resultOfFunction);
		return resultOfFunction;

	}
	
	/**
	 * @Title: getServerStatus
	 * @Author: jianghan
	 * @Description: TODO 向已有接口发送参数， 返回激活服务器状态 -2代表服务器不存在， -1代表用户不存在， 0代表未激活,
	 *               1代表激活, 2代表激活，并激活天数大于30天,
	 * @param account
	 * @param response
	 * @return ResultBean<Integer>
	 * 
	 */
	public ResultBean<Integer> getServerStatus(String account, int game,
			int serverCode) {
		logger.debug("account", account);
		logger.debug("game", game);
		logger.debug("serverCode", serverCode);
		ResultBean<Integer> resultBean = new ResultBean<Integer>();
		resultBean.setData(0);
		resultBean.setMessage(null);
		resultBean.setIsSuccess(true);
		// 调用webapi
		CallWebApiAgent cwaa = new CallWebApiAgent();
		ClientResponse response = cwaa.getActivatelogFromWebApi(account, game);
		String StatusType = response.getStatusType().toString();
		// 接收返回响应体
		ServerBLL serverBLL = new ServerBLL();
		String result = response.getEntity(String.class);
		int serverStatus = serverBLL.getServerStatus(StatusType, result,
				account, game, serverCode);
		resultBean.setData(serverStatus);
		logger.debug("resultBean", resultBean);
		return resultBean;
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
			try(DistributedLock lock = new DistributedLock("wd_seven_noviceCard")) {
				lock.weakLock(30,10);
				result = bagReceiveLogBll.setReceiveLog(parameter);
			}catch(DLockException e){
				logger.error("recard novic card log eror!",e);
				logger.error("DLockException type:" + e.getType() + ",msg:" + e.getMessage());
				result.setProperties(false,"发送礼包失败,请稍后重试!" , "发送礼包失败,请稍后重试!");
				return result;
			}
			if (!result.getIsSuccess()) {
				logger.debug("result"+result);
				return result;
			}
			ResultBean<Integer> sendResult = this.SendItem(parameter);
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
		logger.info("发送物品方法parameter："+ parameter);
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
