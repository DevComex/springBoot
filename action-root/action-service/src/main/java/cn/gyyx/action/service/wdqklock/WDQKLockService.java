/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：chenpeilan zhouzhongkai
 * @联系方式：chenpeilan@gyyx.cn zhouzhongkai@gyyx.cn
 * @创建时间： 2016年1月20日
 * @版本号：
 * @本类主要用途描述：问道乾坤锁领取礼包活动Service
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.wdqklock;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.QianKunLock.QianKunLockLimitBean;
import cn.gyyx.action.beans.QianKunLock.QianKunLockModel;
import cn.gyyx.action.beans.QianKunLock.QianKunLockModels;
import cn.gyyx.action.beans.novicecard.ProcessResult;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.bll.qiankunlock.QianKunLockBLL;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: WDQKLockService
 * @Description: TODO 问道乾坤锁领取礼包活动Service
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年1月20日 下午3:48:47
 *
 */
public class WDQKLockService {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CallWebApiAgent.class);
	private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	private QianKunLockBLL qianKunLockBLL = new QianKunLockBLL();

	/**
	 * 
	 * @Title: getMD5ByAccountServerInfoAndTime
	 * @Description: TODO 账号、游戏ID、时间戳加密
	 * @param @param account
	 * @param @param gameId
	 * @param @param timestamp
	 * @param @return
	 * @return String
	 * @throws
	 */
	private String getMD5ByAccountServerInfoAndTime(String account,
			Integer gameId, long timestamp) {
		String integtationURL = "/user/" + account + "/activatelog/" + gameId
				+ "?timestamp=" + timestamp + "123456";
		return MD5.encode(integtationURL);
	}

	private String getMD5ByUserInfoANDEkey(UserInfo userInfo,
			String ekeyCompany, String ekeySn, String password, long timestamp) {
		String integtationURL1 = "/" + userInfo.getUserId()
				+ "/unlock?account=" + userInfo.getAccount() + "&ekeyCompany="
				+ ekeyCompany + "&eKeySn=" + ekeySn + "&password=" + password
				+ "&timestamp=" + timestamp + "123456";
		return MD5.encode(integtationURL1);
	}

	/**
	 * 
	 * @Title: getIsActivityFromWebApi
	 * @Description: TODO 通过接口判断用户是否激活该服务器（问道）
	 * @param @param account
	 * @param @param serverId
	 * @param @return
	 * @param @throws Exception
	 * @return boolean
	 * @throws
	 */
	public boolean getIsActivityFromWebApi(String account, Integer serverId) {
		Integer gameId = 2;
		logger.info("account", account);
		logger.info("serverId", serverId);
		long timestamp = System.currentTimeMillis() / 1000;
		logger.info("account", account);
		logger.info("serverId", serverId);
		String sign_type = "MD5";
		String sign = getMD5ByAccountServerInfoAndTime(account, gameId,
				timestamp);
		logger.info("sign", sign);
		String url = "http://account.module.gyyx.cn/user/" + account
				+ "/activatelog/" + gameId + "?timestamp=" + timestamp
				+ "&sign=" + sign + "&sign_type=" + sign_type;
		logger.info("url", url);
		boolean flag = false;
		RestClient client = new RestClient();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			Integer data = response.getStatusCode();
			logger.info("data", data);
			if (data.equals(200)) {
				// 接收返回响应体
				String result = response.getEntity(String.class);
				logger.info("getIsActivityFromWebApi接受返回响应体result" + result);
				// TODO 看下核心类库有JSON向对象转换的封装
				JSONArray jsonarray = JSONArray.fromObject(result);
				logger.info("jsonarray----size" + jsonarray.size());
				if (jsonarray.size() > 0) {
					for (int i = 0; i < jsonarray.size(); i++) {
						Map map = (Map) jsonarray.get(i);
						if (map.get("ServerCode").equals(serverId)) {
							flag = true;
							logger.info("获取激活区服列表成功并激活----flag" + flag);
							return flag;
						} else {
							logger.info("获取激活区服列表成功但未激活----flag" + flag);
							flag = false;
						}
					}
				} else {
					flag = false;
					logger.info("获取激活区服列表为空----flag" + flag);
					return flag;
				}
				logger.info("data为200成功的时候返回的flag：" + flag);
				return flag;
			} else {
				logger.info("data不为200未成功的时候返回的flag：" + flag);
				return flag;
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * @Title: getIsActivityFromWebApi
	 * @Description: TODO 通过接口判断用户是否激活该服务器（问道）
	 * @param @param account
	 * @param @param serverId
	 * @param @return
	 * @param @throws Exception
	 * @return boolean
	 * @throws
	 */
	public boolean getIsActivityFromAction(String account, String serverId) {
		logger.info("account" + account);
		logger.info("serverId" + serverId);
		boolean flag = false;
		cn.gyyx.action.beans.ResultBean<String> activatedResult = new CallWebApiAgent()
				.accountIsActivated(account, serverId);
		logger.info("getIsActivityFromAction" + activatedResult.toString());
		if (activatedResult.getIsSuccess()) {
			if (activatedResult.getMessage().equals("already")) {
				flag = true;
			}
		}
		return flag;

	}

	/**
	 * 
	 * @Title: unChain
	 * @Description: TODO 验证乾坤锁
	 * @param @param userInfo
	 * @param @param ekeyCompany 乾坤锁厂商（3代表优逸实体乾坤锁，4代表手机乾坤锁）
	 * @param @param ekeySn 乾坤锁序列号
	 * @param @param password 乾坤锁动态码
	 * @param @return
	 * @return ResultBean<String>
	 * @throws
	 */
	public ResultBean<String> unChain(UserInfo userInfo, String ekeyCompany,
			String ekeySn, String password) {
		ResultBean<String> resultBean = new ResultBean<String>(false, "未知错误",
				"");
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String sign = getMD5ByUserInfoANDEkey(userInfo, ekeyCompany, ekeySn,
				password, timestamp);
		logger.info("unChain--------sign" + sign);
		// 请求地址
		String url = "http://ekey.module.gyyx.cn/" + userInfo.getUserId()
				+ "/unlock?account=" + userInfo.getAccount() + "&ekeyCompany="
				+ ekeyCompany + "&eKeySn=" + ekeySn + "&password=" + password
				+ "&timestamp=" + timestamp + "&sign=" + sign + "&sign_type="
				+ sign_type;
		logger.info("unChain--------url" + url);
		RestClient client = new RestClient();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource)
					.post("http://ekey.module.gyyx.cn/" + userInfo.getUserId()
							+ "/unlock");
			// 接收返回响应体
			String result = response.getEntity(String.class);
			JSONObject jsonobject = JSONObject.fromObject(result);
			String data = jsonobject.getString("Message");
			logger.info("unChain--------data" + data);
			if (data.equals("解锁成功！")) {
				resultBean.setProperties(true, data, "");
				return resultBean;
			} else {
				resultBean.setMessage(data);
				return resultBean;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("unChain error" + e.getMessage());
			return resultBean;
		}
	}

	public QianKunLockModels getQianKunLock(int userCode, String sn) {

		int sign1 = 123456;
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String preSign = "/" + userCode + "?timestamp=" + timestamp + ""
				+ sign1;
		String sign = MD5.encode(preSign);
		// 请求地址
		String url = "http://ekey.module.gyyx.cn/" + userCode + "?timestamp="
				+ timestamp + "&sign_type=" + sign_type + "&sign=" + sign;
		RestClient client = new RestClient();
		String result = null;
		QianKunLockModels qianKunLockModels = new QianKunLockModels();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			result = response.getEntity(String.class);
			JSONArray jsonArray = JSONArray.fromObject(result);
			List<Map<String, Object>> mapListJson = (List) jsonArray;
			for (int i = 0; i < mapListJson.size(); i++) {
				Map<String, Object> obj = mapListJson.get(i);
				if (obj.get("EkeySn").equals(sn)) {
					qianKunLockModels.setAccount(obj.get("Account").toString());
					qianKunLockModels.setEkeyType(Integer.parseInt(obj.get(
							"EKeyType").toString()));
					qianKunLockModels.setEKeyCompany(obj.get("Company")
							.toString());
					qianKunLockModels.setEKeySN(obj.get("EkeySn").toString());

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("getEkeyInformationBean web service error"
					+ e.getMessage());
			return qianKunLockModels;
		}
		return qianKunLockModels;
	}

	/**
	 * 
	 * @Title: SendItem
	 * @Description: TODO 发送物品方法
	 * @param @param parameter
	 * @param @return
	 * @return ResultBean<Integer>
	 * @throws
	 */
	public ResultBean<String> sendItem(String account, int serverId, String sn,
			int code) {
		ResultBean<String> result = new ResultBean<String>(false, "发送物品未知错误",
				"");
		ServerInfoBean serverInfo = callWebApiAgent
				.getServerStatusFromWebApi(serverId);
		Calendar c = java.util.Calendar.getInstance(TimeZone.getDefault(),
				Locale.CHINA);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		c.add(Calendar.YEAR, 1);
		java.util.Date newdate = c.getTime();
		String expiredTime = formatter.format(newdate);
		QianKunLockLimitBean qianKunLockLimitBean = qianKunLockBLL
				.getLockLimit();
		String EkeyStart = null;
		String EkeyEnd = null;
		if (qianKunLockLimitBean == null) {
			result.setProperties(false, "不在乾坤锁领取礼包号段范围内", null);
			return result;
		} else {
			EkeyStart = qianKunLockLimitBean.getBegin();
			EkeyEnd = qianKunLockLimitBean.getEnd();
		}
		String prize = null;
		if (Double.parseDouble(sn) >= Double.parseDouble(EkeyStart)
				&& Double.parseDouble(sn) <= Double.parseDouble(EkeyEnd)) {
			prize = "乾坤葫芦活动奖励乾坤葫芦豪华礼包(140402)";

		} else {
			prize = "绑定移动密保送奖励活动奖品(100601)";
		}
		ProcessResult processResult = CallWebServiceAgent.givePresents(2,
				account, prize, expiredTime, "", serverInfo);
		if (processResult != null) {
			QianKunLockModel qianKunLockModel = new QianKunLockModel();
			qianKunLockModel.setCode(code);
			qianKunLockModel.setErrCode(processResult.getErrorCode());
			qianKunLockModel.setDescription(processResult.getDescription());
			qianKunLockBLL.updateLog(qianKunLockModel);
			result.setProperties(true, processResult.getDescription(), "");
		}
		logger.debug("result", result);
		return result;
	}
}
