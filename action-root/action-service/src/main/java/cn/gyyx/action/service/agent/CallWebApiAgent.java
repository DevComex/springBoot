/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：姜晗
 * 联系方式：jianghan@gyyx.cn 
 * 创建时间：2014年12月10日 下午3:16:48 
 * 版本号：v1.0 
 * 本类主要用途描述： 调用web接口的类
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.service.agent;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.gyyx.action.beans.game.GameInfoBean;
import com.google.common.base.Throwables;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.util.EntityUtils;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.xwbcreditsluckydraw.Data;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ServerBean;
import cn.gyyx.action.dao.wdno1pet.PetInfoDAO;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

/**
 * @ClassName: CallWebApiAgent
 * @Description: TODO
 * @author jh 调用web接口的类
 * @date 2014年12月10日 下午3:17:34
 *
 */
public class CallWebApiAgent {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CallWebApiAgent.class);

	/**
	 * @Title: getActivatelogFromWebApi
	 * @Author: jianghan
	 * @Description: TODO 通过webapi， 获取激活日志
	 * @param account
	 * @param game
	 * @return ClientResponse
	 *
	 */
	public ClientResponse getActivatelogFromWebApi(String account, int game) {
		long timestamp = System.currentTimeMillis() / 1000;
		logger.debug("account", account);
		logger.debug("game", game);
		String sign_type = "MD5";
		String sign = getMD5ByAccountAndGameAndTimestamp(account, game,
				timestamp);
		String url = "http://account.module.gyyx.cn/user/" + account
				+ "/activatelog/" + game + "/?timestamp=" + timestamp
				+ "&sign_type=" + sign_type + "&sign=" + sign;
		logger.debug("sign", sign);
		logger.debug("url", url);
		RestClient client = new RestClient();
		org.apache.wink.client.Resource resource = client.resource(url);
		ClientResponse response = (resource).get();
		logger.info("response", response);
		return response;
	}

	/**
	 * @Title: getMD5ByAccountAndTime
	 * @Author: jianghan
	 * @Description: TODO md5加密 获取sign （用于验证） 私有
	 * @param account
	 * @param game
	 * @param timestamp
	 * @return
	 *
	 */
	private String getMD5ByAccountAndGameAndTimestamp(String account, int game,
			long timestamp) {
		logger.debug("account", account);
		logger.debug("game", game);
		logger.debug("timestamp", timestamp);
		String integtationURL = "/user/" + account + "/activatelog/" + game
				+ "/?timestamp=" + timestamp + "123456";
		logger.debug("integtationURL", integtationURL);
		return MD5.encode(integtationURL);
	}

	/**
	 * 取问道
	 * 日期：2014年12月12日 作者：王雷 方法名：getServerStatusFromWebApi 参 数：int serverId
	 * 返回值：ServerInfoBean 描述：返回服务器相关信息
	 */

	public ServerInfoBean getServerStatusFromWebApi(int serverId) {
		logger.debug("serverId", serverId);
		long timestamp = System.currentTimeMillis() / 1000;
		logger.debug("serverId", serverId);
		String sign_type = "MD5";
		String sign = getMD5ByAccountAndTime(String.valueOf(serverId),
				timestamp);
		logger.debug("sign", sign);
		String url = "http://game.module.gyyx.cn/game/wendao/server/"
				+ serverId + "?timestamp=" + timestamp + "&sign_type="
				+ sign_type + "&sign=" + sign;
		logger.debug("url", url);
		RestClient client = new RestClient();
		org.apache.wink.client.Resource resource = client.resource(url);
		ClientResponse response = resource.get();
		// 接收返回响应体
		String result = response.getEntity(String.class);
		ServerInfoBean serverInfo = DataFormat.jsonToObj(result,ServerInfoBean.class);
		// 看下核心类库有JSON向对象转换的封装
		logger.debug("serverInfo", serverInfo);
		return serverInfo;
	}

	/**
	 * 判断是否激活
	 * @param
	 * @return
	 */
	public ResultBean<String> accountIsActivated(String account,String serverCode){
		logger.debug("start judge activated!");
		ResultBean<String> result = new ResultBean<String>();
		try {
			ServerInfoBean serverInfo = getServerStatusFromWebApi(Integer.parseInt(serverCode));
			if(null != serverInfo && null != serverInfo.getValue() && null != serverInfo.getValue().getServerIp()){
				StringBuilder realUrl = new StringBuilder("http://interface.account.gyyx.cn");
				StringBuilder url = new StringBuilder("/game/activelog/");
				url.append("2").append("/").append(account).append("/").append(serverCode)
				.append("/").append(serverInfo.getValue().getServerIp()).append("/").append(serverInfo.getValue().getServerPort()).append("?timestamp=").append(System.currentTimeMillis()/1000);
				String sign = MD5.encode(url.toString()+"123456");
				realUrl.append(url.toString()).append("&sign=").append(sign).append("&sign_type=MD5");
				logger.debug("request judge activated interface:"+realUrl.toString());
				RestClient client = new RestClient();
				String requestResult = "";
				try{
					Resource resource = client.resource(realUrl.toString()).accept("application/json");
					ClientResponse response = resource.get();
					requestResult = response.getEntity(String.class);
					JSONObject json = JSONObject.fromObject(requestResult);
					if("success".equals(json.getString("Status"))){
						if(requestResult.contains("Code")){
							String time = requestResult.split("ActiveTime")[1].substring(3, 22).replace("T", " ");
							result.setProperties(true, "already", time);
							logger.debug("activated return time :"+time);
						}else{
							result.setProperties(true, "not", "还没激活!");
						}
					}else{
						result.setProperties(false, "查询是否激活,调用失败!", "查询是否激活,调用失败!");
					}
				}catch(Exception e){
					logger.warn("get request error! bad connect",e);
				}
			}else{
				result.setProperties(false, "获取serverIP失败!", "获取serverIP失败!");
			}
		} catch (Exception e) {
			result.setProperties(false, "激活失败了!","激活失败了!");
		}
		logger.debug("judge activated message --isSuccess:"+result.getIsSuccess()+"--message:"+result.getMessage()+"--data:"+result.getData());
		logger.debug("finished judge activated!");
		return result;
	}
	/**
	 * 获取炫舞吧服务器信息
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public ServerInfoBean getXWBServerStatusFromWebApi(int serverId)
			throws Exception {
		logger.info(" 获取炫舞吧服务器信息serverId："+ serverId);
		long timestamp = System.currentTimeMillis() / 1000;
		logger.info(" 获取炫舞吧服务器信息serverId："+ serverId);
		String sign_type = "MD5";
		String sign = getXWBMD5ByAccountAndTime(String.valueOf(serverId),
				timestamp);
		logger.info("sign", sign);
		String url = "http://game.module.gyyx.cn/game/xuanwuba/server/"
				+ serverId + "?timestamp=" + timestamp + "&sign_type="
				+ sign_type + "&sign=" + sign;
		logger.info(" 获取炫舞吧服务器信息url："+ url);
		RestClient client = new RestClient();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			ServerInfoBean serverInfo = DataFormat.jsonToObj(result,
					ServerInfoBean.class);
			// TODO 看下核心类库有JSON向对象转换的封装
			logger.info(" 获取炫舞吧服务器信息serverInfo："+ serverInfo);
			return serverInfo;

		} catch (Exception e) {
			logger.warn(e.getMessage());

			throw e;
		}
	}
	/**
	 * 生成炫舞吧sign
	 * @param serviceId
	 * @param timestamp
	 * @return
	 */
	private String getXWBMD5ByAccountAndTime(String serviceId, long timestamp) {
		String integtationURL = "/game/xuanwuba/server/" + serviceId
				+ "?timestamp=" + timestamp + "123456";
		return MD5.encode(integtationURL);
	}

	/**
	 * 日期：2014年12月8日 作者：王雷 方法名：getMD5ByAccountAndTime 参 数： userId:用户Id
	 * timestamp：时间戳 返回值：String ：sign 描述：生成sign
	 */
	private String getMD5ByAccountAndTime(String serviceId, long timestamp) {
		String integtationURL = "/game/wendao/server/" + serviceId
				+ "?timestamp=" + timestamp + "123456";
		return MD5.encode(integtationURL);
	}

	/**
	 * 日期：2014年12月22日 作者：李泽溥 方法名：getAllServer 描述：获得全部开放的服务器
	 */
	public List<Value> getAllServer() {
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String sign = getMD5ByTime(timestamp);
		String url = "http://game.module.gyyx.cn/game/wendao/server/?timestamp="
				+ timestamp + "&sign_type=" + sign_type + "&sign=" + sign;
		RestClient client = new RestClient();
		List<Value> res = new LinkedList<Value>();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			cn.gyyx.action.beans.wdno1pet.ServerInfoBean serverInfo = DataFormat
					.jsonToObj(result,
							cn.gyyx.action.beans.wdno1pet.ServerInfoBean.class);
			List<Integer> exists = new PetInfoDAO().getServerIdListByExist();
			Value[] val = serverInfo.getValue();
			for (Value v : val) {
				if (v.isIsOpen() && exists.contains(v.getCode())) {
					res.add(v);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("res", res);
		return res;
	}

	private String getMD5ByTime(long timestamp) {
		String integtationURL = "/game/wendao/server/?timestamp=" + timestamp
				+ "123456";
		return MD5.encode(integtationURL);
	}

	/**
	 * 根据网络类型获得全部服务器列表
	 *
	 * @return 服务器列表
	 */
	public List<Value> getAllServerByNetTypeCode(String netTypeCode) {
		long timestamp = System.currentTimeMillis() / 1000;
		logger.debug("netTypeCode", netTypeCode);
		String sign_type = "MD5";
		String sign = getMD5ByNetTypeAndTime(netTypeCode, timestamp);
		String url = "http://game.module.gyyx.cn/game/wendao/server/?"
				+ "nettype=" + netTypeCode + "&timestamp=" + timestamp
				+ "&sign_type=" + sign_type + "&sign=" + sign;
		logger.debug("url", url);
		RestClient client = new RestClient();
		List<Value> res = new LinkedList<Value>();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			cn.gyyx.action.beans.wdno1pet.ServerInfoBean serverInfo = DataFormat
					.jsonToObj(result,
							cn.gyyx.action.beans.wdno1pet.ServerInfoBean.class);
			Value[] val = serverInfo.getValue();
			for (Value v : val) {
				if (v.isIsOpen()) {
					res.add(v);
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}



	private String getMD5ByNetTypeAndTime(String netType, long timestamp) {
		String integtationURL = "/game/wendao/server/?nettype=" + netType
				+ "&timestamp=" + timestamp + "123456";
		return MD5.encode(integtationURL);
	}

	/**
	 *
	 * @日期：2015年7月8日
	 * @Title: getAllServerXuanWuBa
	 * @Description: TODO 获取炫舞吧所有服务器列表
	 * @return List<Data>
	 */
	public List<Data> getAllServerXuanWuBa() {
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String sign = getMD5ByXuanWuBaTime(timestamp);
		String url = "http://game.module.gyyx.cn/v2/game/11/server?"
				+ "timestamp=" + timestamp + "&sign_type=" + sign_type
				+ "&sign=" + sign;
		logger.debug("url", url);
		RestClient client = new RestClient();
		List<Data> res = new LinkedList<Data>();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			ServerBean serverInfo = DataFormat.jsonToObj(result,
					ServerBean.class);
			Data[] val = serverInfo.getData();
			for (Data v : val) {
				if (v.isOpen()) {
					res.add(v);
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 获取游戏列表
	 * @return
	 */
	public static ResultBean<List<GameInfoBean>> getClientGameList(){
        ResultBean<List<GameInfoBean>> resultBean = new ResultBean<>();
		long timestamp = System.currentTimeMillis() / 1000;

		String sign_uri = "/v2/gamelist/client?timestamp=" + timestamp
				+ "123456";
		String sign = MD5.encode(sign_uri);

		String uri = "http://game.module.gyyx.cn/v2/gamelist/client?timestamp="+timestamp+"&sign="+sign+"&sign_type=MD5";
        try {
            Response response = Request.Get(uri).execute();
            HttpResponse httpResponse = response.returnResponse();
            String gameResultStr = EntityUtils.toString(httpResponse.getEntity());
			com.alibaba.fastjson.JSONObject responseObject = com.alibaba.fastjson.JSONObject.parseObject(gameResultStr);
            if(httpResponse.getStatusLine().getStatusCode()==200 && responseObject.getString("status").equals("success")){

				List<GameInfoBean> list = com.alibaba.fastjson.JSON.parseArray(responseObject.getString("data"),GameInfoBean.class);

				resultBean.setIsSuccess(true);
				resultBean.setData(list);

            }else{
                resultBean.setIsSuccess(false);
                resultBean.setMessage("获取游戏列表失败");
            }
        } catch (ClientProtocolException e){
            logger.error(Throwables.getStackTraceAsString(e));
        } catch (IOException e){
            logger.error(Throwables.getStackTraceAsString(e));
        }
        return resultBean;
	}

	/**
	 * 获取游戏信息
	 * @return
	 */
	public static ResultBean<GameInfoBean> getGameInfo(int gameId){
		ResultBean<GameInfoBean> resultBean = new ResultBean<>();
		long timestamp = System.currentTimeMillis() / 1000;

		String sign_uri = "/v2/game/"+gameId+"?timestamp=" + timestamp
				+ "123456";
		String sign = MD5.encode(sign_uri);

		String uri = "http://game.module.gyyx.cn/v2/game/"+gameId+"?timestamp="+timestamp+"&sign="+sign+"&sign_type=MD5";
		try {
			Response response = Request.Get(uri).execute();
			HttpResponse httpResponse = response.returnResponse();
			com.alibaba.fastjson.JSONObject responseObject = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(httpResponse.getEntity()));
			if(httpResponse.getStatusLine().getStatusCode()==200 && responseObject.getString("status").equals("success")){

				GameInfoBean gameInfo = com.alibaba.fastjson.JSON.parseObject(responseObject.getString("data"),GameInfoBean.class);

				resultBean.setIsSuccess(true);
				resultBean.setData(gameInfo);

			}else{
				resultBean.setIsSuccess(false);
				resultBean.setMessage("获取游戏信息失败");
			}
		} catch (ClientProtocolException e){
			logger.error(Throwables.getStackTraceAsString(e));
		} catch (IOException e){
			logger.error(Throwables.getStackTraceAsString(e));
		}
		return resultBean;
	}

	public static ResultBean<List<ServerInfoBean.Value>> getWdServers(int gameId){
		ResultBean<List<ServerInfoBean.Value>> resultBean = new ResultBean<>();
		List<ServerInfoBean.Value> serverList = new LinkedList<>();
		long timestamp = System.currentTimeMillis() / 1000;

		String sign_uri = "/game/"+gameId+"/server?timestamp=" + timestamp
				+ "123456";
		String sign = MD5.encode(sign_uri);

		String uri = "http://game.module.gyyx.cn/game/"+gameId+"/server?timestamp="+timestamp+"&sign="+sign+"&sign_type=MD5";

		try {
			Response response = Request.Get(uri).execute();
			HttpResponse httpResponse = response.returnResponse();
			String result = EntityUtils.toString(httpResponse.getEntity());
			if(httpResponse.getStatusLine().getStatusCode()==200){
				cn.gyyx.action.beans.wdno1pet.ServerInfoBean serverInfo = DataFormat
						.jsonToObj(result,
								cn.gyyx.action.beans.wdno1pet.ServerInfoBean.class);
				Value[] val = serverInfo.getValue();
				for (Value v : val) {
					if (v.isIsOpen()) {
						serverList.add(v);
					}
				}

			}else{
				resultBean.setIsSuccess(false);
				resultBean.setMessage("获取服务器信息失败");
			}
		} catch (ClientProtocolException e){
			logger.error(Throwables.getStackTraceAsString(e));
		} catch (IOException e){
			logger.error(Throwables.getStackTraceAsString(e));
		}
		resultBean.setIsSuccess(true);
		resultBean.setData(serverList);
		return resultBean;
	}

    /**
	 *
	 * @日期：2015年7月8日
	 * @Title: getMD5ByXuanWuBaTime
	 * @Description: TODO 炫舞吧时间戳加密
	 * @param timestamp
	 * @return String
	 */
	private String getMD5ByXuanWuBaTime(long timestamp) {
		String integtationURL = "/v2/game/11/server?timestamp=" + timestamp
				+ "123456";
		return MD5.encode(integtationURL);
	}

	/**
	 *
	 * @日期：2015年7月9日
	 * @Title: getServersXuanWuBaByNetType
	 * @Description: TODO 根据大区获取各个大区服务器
	 * @param netType
	 * @return List<Data>
	 */
	public List<Data> getServersXuanWuBaByNetType(int netType) {
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String sign = getMD5ByXuanWuBaTimeAndNetType(timestamp, netType);
		String url = "http://game.module.gyyx.cn/v2/game/11/server?"
				+ "nettype=" + netType + "&timestamp=" + timestamp
				+ "&sign_type=" + sign_type + "&sign=" + sign;
		logger.debug("url", url);
		RestClient client = new RestClient();
		List<Data> res = new LinkedList<Data>();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			ServerBean serverInfo = DataFormat.jsonToObj(result,
					ServerBean.class);
			Data[] val = serverInfo.getData();
			for (Data v : val) {
				if (v.isOpen()) {
					res.add(v);
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 开服时间比规定时间大的所有区服
	 * @param
	 * @param gameType
	 * @return
	 */
	public List<Data> getServerMsg(int netType,String gameType,Date limitDate) {
		long timestamp = System.currentTimeMillis() / 1000;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sign_type = "MD5";
		String sign = getMD5ServerURL(timestamp, netType,gameType);
		String url = "http://game.module.gyyx.cn/v2/game/"+gameType+"/server?"
				+ "nettype=" + netType + "&timestamp=" + timestamp
				+ "&sign_type=" + sign_type + "&sign=" + sign;
		logger.debug("url", url);
		RestClient client = new RestClient();
		List<Data> res = new LinkedList<Data>();
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			ServerBean serverInfo = DataFormat.jsonToObj(result,
					ServerBean.class);
			Data[] val = serverInfo.getData();
			logger.debug("servers :"+result);
			StringBuilder dataLog = new StringBuilder("");
			for (Data v : val) {
				try{
					dataLog.append(v.getServerName()).append(v.getOpenServerTime()).append(";");
					//待添加开服时间判断
					if (v.isOpen()&&(format.parse(v.getOpenServerTime().substring(0,10)).getTime()-limitDate.getTime())>0) {
						res.add(v);
					}
				}catch(Exception e){
					logger.debug("get server error！",e);
				}
			}
			logger.debug("servers log:"+dataLog.toString());
		} catch (Exception e) {
			logger.debug("get message error！",e);
		}
		return res;
	}
	/**
	 *
	 * @日期：2015年7月9日
	 * @Title: getMD5ByXuanWuBaTimeAndNetType
	 * @Description: TODO 根据网络类型获取sign参数
	 * @param timestamp
	 * @param netType
	 * @return String
	 */
	private String getMD5ByXuanWuBaTimeAndNetType(long timestamp, int netType) {
		String integtationURL = "/v2/game/11/server?nettype=" + netType
				+ "&timestamp=" + timestamp + "123456";
		return MD5.encode(integtationURL);
	}
	private String getMD5ServerURL(long timestamp, int netType,String gameType) {
		String integtationURL = "/v2/game/"+gameType+"/server?nettype=" + netType
				+ "&timestamp=" + timestamp + "123456";
		return MD5.encode(integtationURL);
	}
	/**
	* 叙述:发送短信验证码<br />
	* @param phoneNumber 电话号码
	* @param type 类型 - 请找API负责人申请 （上次申请负责人为 康佳微）
	* @param msgParam 短信参数 一般是带星号的账号名 如 te***a1
	* @return boolean 代表是否发送成功的布尔值
	*/
	public String sendMessage(String phoneNumber,String type,String msgParam) {
		long timestamp = System.currentTimeMillis() / 1000;
		String domain = "http://interface.message.gyyx.cn";
		String url = "/v1/Send/VerifyCode/Phone"
		+"/?phone="+ phoneNumber
		+"&messageParam=" + msgParam
		+"&sourceType=" + type
		+"&timestamp=" + timestamp;
		String signUrl = "&sign=" + MD5.encode(url + "123456") + "&sign_type=MD5";
		RestClient client = new RestClient();
		String postUrl = domain+url+signUrl;
		Resource resource = client.resource(postUrl);
		ClientResponse response = resource.post(postUrl);
		String rString = response.getEntity(String.class);
		logger.info("sendMessage response:" + rString);
		if(rString != null) {
			if(rString.indexOf("短信发送成功") != -1){
				return "SUCCESS";
			} else if(rString.indexOf("未达到下次发送时间") != -1){
				return "未达到下次发送时间,请等待1分钟后再试";
			} else if(rString.indexOf("本日发送短信次数过多") != -1) {
				return "本日发送短信次数过多";
			} else if(rString.indexOf("该业务发送短信次数过多") != -1){
				return "该业务发送短信次数过多,请明天再试";
			} else {
				return "未知错误";
			}
		} else {
			return "未知错误";
		}
	}


	/**
	* 叙述:验证短信验证码<br />
	* @param captcha 验证码
	* @param phoneNumber 手机号
	* @param type 类型
	* @return boolean 代表验证是否通过的布尔值
	*/
	public boolean validateMessageCaptcha(String captcha,String phoneNumber,String type) {
		boolean result = false;
		long timestamp = System.currentTimeMillis() / 1000;
		String domain = "http://interface.message.gyyx.cn";
		String url = "/v1/Valid/VerifyCode/Phone"
		+"?phone="+ phoneNumber
		+"&sourceType=" + type
		+"&timestamp=" + timestamp
		+"&verifyCode=" + captcha;
		String signUrl = "&sign=" + MD5.encode(url + "123456") + "&sign_type=MD5";
		RestClient client = new RestClient();
		String postUrl = domain+url+signUrl;
		Resource resource = client.resource(postUrl);
		ClientResponse response = resource.post(postUrl);
		String rString = response.getEntity(String.class);
		logger.info("validateMessage response:" + rString);
		if(rString != null) {
			result = rString.indexOf("验证成功") != -1;
		}
		return result;
	}
	/**
	  * <p>
	  * 根据open查询绑定的社区账号
	  * </p>
	  *
	  * @action
	  *    niushuai 2017年4月10日 上午11:12:01 描述
	  *
	  * @param openId
	  * @return String
	 */
	public ResultBean<String> getAccountByOpenId(String openId) {
	    ResultBean<String> result = new ResultBean<String>();
            result.setIsSuccess(false);
            result.setMessage("绑定失败！");
            try {
                long timestamp = System.currentTimeMillis() / 1000;
                String domain = "http://api.weixin.gyyx.cn/account/GetBindServer.ashx?";
                String url = "flag=bindInfo&OpenId="+openId+"&time="+timestamp+"&weixinType=Wd";
                String signUrl = "&sign=" + MD5.encode(url+"Dfad124FAC518DF3");
                RestClient client = new RestClient();
                String postUrl = domain+url+signUrl;
                Resource resource = client.resource(postUrl);
                ClientResponse response = resource.get();
                String rString = response.getEntity(String.class);
                logger.info("sendMessage response:" + rString);
                JSONObject json = JSONObject.fromObject(rString);
                result.setProperties(json.getBoolean("Issuccess"),json.getString("errormsg"), null);
                if(json.getBoolean("Issuccess")) {
                    result.setData(json.getJSONObject("Data").getString("Account"));
                }
                result.setStateCode(1); // 正常
            } catch(Exception e) {
                e.printStackTrace();
                logger.error("查询微信绑定社区账号异常！{}",Throwables.getStackTraceAsString(e));
                result.setIsSuccess(false);
                result.setMessage("查询微信绑定社区账号时异常！");
                result.setStateCode(0); // 异常时
            }
            return result;
	}
	
	/**
	  * <p>
	  * 微信号绑定社区账号
	  * </p>
	  *
	  * @action
	  *    niushuai 2017年4月10日 上午11:58:55 描述
	  *
	  * @param account
	  * @param userId
	  * @param openId
	  * @return int
	 */
	public ResultBean<Integer> roleBindWechatAcount(String openId, String account, int userId) {
	    ResultBean<Integer> result = new ResultBean<Integer>();
	    result.setIsSuccess(false);
	    result.setMessage("绑定失败！");
	    try {
	        long timestamp = System.currentTimeMillis() / 1000;
	        String domain = "http://api.weixin.gyyx.cn/account/GetBindServer.ashx?";
	        String url = "account="+account
	                +"&flag=bind&OpenId="+openId+"&time="+timestamp
	                +"&userId="+userId+"&weixinType=Wd";
	        String signUrl = "&sign=" + MD5.encode(url+"Dfad124FAC518DF3");
	        RestClient client = new RestClient();
	        String postUrl = domain+url+signUrl;
	        Resource resource = client.resource(postUrl);
	        ClientResponse response = resource.get();
	        String rString = response.getEntity(String.class);
	        System.out.println(rString);
	        JSONObject json = JSONObject.fromObject(rString);
	        result.setIsSuccess(json.getBoolean("Issuccess"));
	        result.setMessage(json.getString("errormsg"));
	        logger.info("sendMessage response:" + rString);
	    } catch(Exception e) {
	        logger.error("微信号绑定社区账号异常！{}",Throwables.getStackTraceAsString(e));
	        result.setIsSuccess(false);
	        result.setMessage("绑定时异常！");
	    }
	    return result;
	}
	
}
