/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：yangyusheng
 * @联系方式：yangyusheng@gyyx.cn
 * @创建时间： 2014年12月10日 下午3:03:43
 * @版本号：
 * @本类主要用途描述：新手卡service层
 *-------------------------------------------------------------------------
 */
package cn.gyyx.service.novicecard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.wink.client.ClientResponse;
import org.slf4j.Logger;

import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.novicecard.ErWeiMaBean;
import cn.gyyx.action.beans.novicecard.NoviceCardBean;
import cn.gyyx.action.beans.novicecard.NoviceCardMark;
import cn.gyyx.action.beans.novicecard.NoviceParameter;
import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.novicecard.VirtualItemBean;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.novicecard.CheckAlreadyGetBLL;
import cn.gyyx.action.bll.novicecard.ErWeiMaBLL;
import cn.gyyx.action.bll.novicecard.GetMD5PasswordBLL;
import cn.gyyx.action.bll.novicecard.NoviceCardBLL;
import cn.gyyx.action.bll.novicecard.ReceiveLogBll;
import cn.gyyx.action.bll.novicecard.ServerBLL;
import cn.gyyx.action.bll.novicecard.VirtualItemBll;
import cn.gyyx.action.bll.wd9yearnovicebag.CheckAlreadyBagGetBLL;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.core.Ip;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class NoviceCardService {
	//新手礼包活动Id
	private static final Integer activityId = 177;
	private CheckAlreadyBagGetBLL checkAlreadyBagGetBLL = new CheckAlreadyBagGetBLL();
	private CheckAlreadyGetBLL checkAlreadyGetBLL = new CheckAlreadyGetBLL();
	private GetMD5PasswordBLL getMD5PasswordBll = new GetMD5PasswordBLL();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private NoviceCardBLL noviceCardBll = new NoviceCardBLL();
	private ReceiveLogBll receiveLogBll = new ReceiveLogBll();
	private ErWeiMaBLL erWeiMaBll = new ErWeiMaBLL();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(NoviceCardService.class);

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
	 * 日期：2014年12月12日 作者：王雷 方法名：checkUserCanGet 参 数：NoviceParameter parameter
	 * 返回值：ResultBean<Boolean> result.Data true :可以领取 false：不可以领取
	 * 描述：检查用户是否可以领取新手卡
	 */

	public ResultBean<Boolean> checkUserCanGet(Integer flag) {
		logger.debug("flag", flag);
		ResultBean<Boolean> result = null;
		if (flag == 1) {
			result = new ResultBean<Boolean>(true, null, true);
		}
		if (flag == 2) {
			result = new ResultBean<Boolean>(false, "激活天数大于30天", false);
		}
		logger.debug("result", result);
		return result;
	}

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

	public ResultBean<String> sendNoviceCard(NoviceParameter noviceParameter,HttpServletRequest request) {
		logger.debug("noviceParameter", noviceParameter);
		ResultBean<String> resultOfFunction = new ResultBean<String>();
		resultOfFunction.setIsSuccess(false);
		// 判断新手卡存在与卡号是否有效
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
		//与新手礼包活动互斥
		NoviceParameter bagNoviceParameter = new NoviceParameter();
		bagNoviceParameter.setUserInfo(noviceParameter.getUserInfo());
		bagNoviceParameter.setServerId(noviceParameter.getServerId());
		bagNoviceParameter.setGameId(noviceParameter.getGameId());
		bagNoviceParameter.setActivityId(activityId);
		ResultBean<String> resultGet = checkAlreadyBagGetBLL
				.checkBagGet(bagNoviceParameter);
		// 如果checkGet查询结果：用户已经领过、IsSuccess()：true
		if (resultGet.getIsSuccess()) {
			resultOfFunction = resultGet;
			resultOfFunction.setIsSuccess(false);
			logger.debug("resultOfFunction", resultOfFunction);
			return resultOfFunction;
		}
		// 判断用户是否在此服务器上领取过新手卡
		ResultBean<String> resultBagGet = checkAlreadyGetBLL
				.checkGet(noviceParameter);
		// 如果checkGet查询结果：用户已经领过、IsSuccess()：true
		if (resultBagGet.getIsSuccess()) {
			resultOfFunction = resultBagGet;
			resultOfFunction.setIsSuccess(false);
			return resultOfFunction;
		}
		logger.info(" ReceiveV3 Account:{0}", noviceParameter.getAccount());
		// 取活动配置信息，同时 检查了活动的开户和关闭时间
		ResultBean<ActivityConfigBean> resultParameter = activityConfigBll
				.getConfigMessage(noviceParameter.getHdName());
		if (!resultParameter.getIsSuccess()) {
			resultOfFunction.setProperties(false, resultParameter.getMessage(),
					"");
			logger.debug("resultOfFunction", resultOfFunction);
			return resultOfFunction;
		}
		//TODO ...检查激活方法..
		// 设置激活检查方法
		noviceParameter.setCheckType(resultParameter.getData().getCheckType());
		// 检查激活情况 并帮手动激活
		if (noviceParameter.getCheckType().equals("ActivationCheck")) {
			// 用户激活服务器日志查询返回结果
			int flag = this.getServerStatus(noviceParameter.getAccount(),
					noviceParameter.getGameId(), noviceParameter.getServerId())
					.getData();
			// 判断是否激活小于30天
			if (flag == 2) {
				resultOfFunction.setProperties(false, "激活时间大于30天", "激活时间大于30天");
				logger.debug("resultOfFunction", resultOfFunction);
				return resultOfFunction;
			}
			// 根据用户激活服务器时间是否发放新手卡物品给用户的提示信息、需要手动激活
			if (flag == -2 || flag == 0 || flag == -1) {
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

		} else {
			// TODO:如果不是通过检查激活时间代码还未实现
			resultOfFunction.setMessage("pzcw激活失败");
			logger.debug("resultOfFunction", resultOfFunction);
			return resultOfFunction;
		}

		ServerInfoBean serverInfo = new ServerInfoBean();
		try {
			serverInfo = callWebApiAgent
					.getServerStatusFromWebApi(noviceParameter.getServerId());
		} catch (Exception e) {
			// 记录WebAPI错误日志
			resultOfFunction.setProperties(false, "很抱歉，领取失败！！", "");
			logger.debug("resultOfFunction", resultOfFunction);
			return resultOfFunction;
		}

		if (serverInfo == null) {

			return resultOfFunction;
		}
		noviceParameter.setServerInfo(serverInfo);
		// 检查领取新手卡相关判断条件的正确性（帐号信息，卡号信息和服务器信息）
		ResultBean<VirtualItemBean> reusltVI = new VirtualItemBll()
				.getVirtualItemByCode(noviceParameter.getLimitItemId());
		if (!reusltVI.getIsSuccess()) {
			resultOfFunction.setMessage("发放失败");
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
			result = receiveLogBll.setReceiveLog(parameter,
					parameter.getVirtualItemCode(), parameter.getTypeId());
			if (!result.getIsSuccess()) {
				logger.debug("result", result);
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
		logger.debug("parameter", parameter);
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
