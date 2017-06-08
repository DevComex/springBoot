/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：
 * @作者：bozhencheng
 * @联系方式：bozhencheng@gyyx.cn
 * @创建时间：2016年5月24日 
 * @版本号：
 * @本类主要用途描述： 问道十年缤纷活动
 *-------------------------------------------------------------------------
 */
package cn.gyyx.wd.wanjia.cartoon.service.upload;

import java.io.IOException;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;



import cn.gyyx.action.common.beans.ResultBean;
import cn.gyyx.core.DataFormat;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.ServerBean;
import cn.gyyx.wd.wanjia.ServerInfoBean;
import net.sf.json.JSONObject;

/**
 * @ClassName: WDGameAgent
 * @description 问道信息获取工具类
 * @author bozhencheng
 * @date 2016年5月25日
 */
public class WDGameAgent {
	
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(WDGameAgent.class);
    
    /**
     * @throws IOException 
     * 
     * @Title: getServers
     * @Description: TODO 获取游戏区服
     * @param netType 大区编码  网通1 电信2  双线3
     * @return ServerBean
     * @throws
     */
    public ServerBean getServers(int gameId,int netType) throws IOException{
        StringBuffer md5Para = new StringBuffer("/v2/game/"+gameId+"/server?");
        md5Para.append("nettype=").append(netType).append("&timestamp=");
        
        StringBuffer urlStr = new StringBuffer("http://game.module.gyyx.cn/v2/game/"+gameId+"/server");
        StringBuffer paraStr = new StringBuffer("timestamp=");
        
        long nowDate = System.currentTimeMillis() / 1000;
        String nowDateStr = String.valueOf(nowDate);
        md5Para.append(nowDateStr).append("123456");
        
        String sign = MD5.encode(md5Para.toString());
        paraStr.append(nowDateStr).append("&nettype=").append(netType).append("&sign_type=MD5").append("&sign=")
        	   .append(sign);
        urlStr.append("?").append(paraStr.toString()).toString();
        
        ServerBean serverBean = new ServerBean("false");
        try {
        	logger.info("WDGameAgent->getServers->urlStr=" + urlStr.toString());
        	String result = requestGet(urlStr.toString(), "UTF-8");
        	
            logger.info("WDGameAgent->getServers->result=" + result);
            serverBean = JSON.parseObject(result, ServerBean.class);
        } catch (Exception e) {
            logger.error("IOException in getServers:" + e);
            throw e;
        }
        
        logger.info("WDGameAgent->getServers->done.serverBean=" + JSON.toJSONString(serverBean));
        
        return serverBean;
    }
    
    /** 
     * 叙述:获取server信息<br />
     * 信息格式：
     * @param game
     * @param serverCode
     * @return JSONObject
     */
     public ResultBean<JSONObject> getServerByCode(int game,int serverCode) {
     	ResultBean<JSONObject> res = new ResultBean<JSONObject>();
     	long timestamp = System.currentTimeMillis() / 1000;
         String sign_type = "MD5";
         String domain = "http://game.module.gyyx.cn";
         String needEncoded = "/game/"+game+"/server/"+serverCode+"/?timestamp=" + timestamp;
         String sign = MD5.encode(needEncoded + "123456");
         String url = domain + 
         		needEncoded + 
         		"&sign=" + sign + 
         		"&sign_type=" + sign_type;
         RestClient client = new RestClient();
         Resource resource = client.resource(url).accept("application/json");
         ClientResponse response = resource.get();
         // 获取状态码
         int data = response.getStatusCode();
         String result = response.getEntity(String.class);
         if(data == 200) {
         	JSONObject jsonObj = JSONObject.fromObject(result);
         	JSONObject value = jsonObj.getJSONObject("Value");
         	res.setData(value);
         	res.setIsSuccess(true);
         	res.setMessage("success");
         } else {
         	logger.info("getServers status error:" + data + ",result:" + result);
         	res.setIsSuccess(false);
         	res.setMessage(data + "," + result);
         }
         return res;
     }
    
     /**
 	 * 判断是否激活
 	 * @param para
 	 * @return 
 	 */
	public ResultBean<Boolean> accountIsActivated(int gameId,String account, int serverId) {
		logger.debug("start judge activated!");
		ResultBean<Boolean> result = new ResultBean<Boolean>();
		try {
			ServerInfoBean serverInfo = getServerStatusFromWebApi(serverId);
			if (null != serverInfo && 
					null != serverInfo.getValue() && 
					null != serverInfo.getValue().getServerIp()) {
				StringBuilder realUrl = new StringBuilder("http://interface.account.gyyx.cn");
				StringBuilder url = new StringBuilder("/game/activelog/");
				url.append(gameId).append("/").append(account).append("/").append(serverId).append("/")
						.append(serverInfo.getValue().getServerIp()).append("/")
						.append(serverInfo.getValue().getServerPort()).append("?timestamp=")
						.append(System.currentTimeMillis() / 1000);
				String sign = MD5.encode(url.toString() + "123456");
				realUrl.append(url.toString()).append("&sign=").append(sign).append("&sign_type=MD5");
				logger.debug("request judge activated interface:" + realUrl.toString());
				RestClient client = new RestClient();
				String requestResult = "";
					Resource resource = client.resource(realUrl.toString()).accept("application/json");
					ClientResponse response = resource.get();
					requestResult = response.getEntity(String.class);
					JSONObject json = JSONObject.fromObject(requestResult);
					if ("success".equals(json.getString("Status"))) {
						if (requestResult.contains("Code")) {
							String time = requestResult.split("ActiveTime")[1].substring(3, 22).replace("T", " ");
							result.setIsSuccess(true);
							logger.debug("activated return time :" + time);
						} else {
							result.setIsSuccess(false);
						}
					}
			}
		} catch (Exception e) {
			logger.warn("get request error! bad connect", e);
			result.setIsSuccess(false);
			result.setMessage("获取激活信息失败，请稍后再试");
		}
		logger.debug("judge activated message --isSuccess:" + result.getIsSuccess() + "--message:" + result.getMessage()
				+ "--data:" + result.getData());
		logger.debug("finished judge activated!");
		return result;
	}
 	
 	/**
	 * 取问道
	 * 日期：2014年12月12日 作者：王雷 方法名：getServerStatusFromWebApi 参 数：int serverId
	 * 返回值：ServerInfoBean 描述：返回服务器相关信息
	 */

	private ServerInfoBean getServerStatusFromWebApi(int serverId)
			throws Exception {
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
		try {
			org.apache.wink.client.Resource resource = client.resource(url);
			ClientResponse response = (resource).get();
			// 接收返回响应体
			String result = response.getEntity(String.class);
			ServerInfoBean serverInfo = DataFormat.jsonToObj(result,
					ServerInfoBean.class);
			// TODO 看下核心类库有JSON向对象转换的封装
			logger.debug("serverInfo", serverInfo);
			return serverInfo;

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			throw e;
		}
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
     * 
     * @Title: getRoles
     * @Description: 根据区服获取角色
     * @param @param gameId
     * @param @param serverIP
     * @param @param serverPort
     * @param @param account
     * @param @return
     * @param @throws Exception
     * @return Integer
     * @throws
     */
    public ResultBean<Object> getRoles(int gameId, String serverIP, String serverPort,
            String account) {
        ResultBean<Object> resultBean = new ResultBean<>();
        logger.info("gameId" + gameId);
        logger.info("serverIP" + serverIP);
        logger.info("serverPort" + serverPort);
        logger.info("account" + account);
        long timestamp = System.currentTimeMillis() / 1000;
        String sign_type = "MD5";
        String sign = getMD5ByAccountAndServer(gameId, String.valueOf(serverIP),
                String.valueOf(serverPort), String.valueOf(account), timestamp);
        logger.info("sign" + sign);
        String url = "http://action.module.gyyx.cn/v1/togame/role?gameId="
                + gameId + "&serverIP=" + serverIP + "&serverPort="
                + serverPort + "&account=" + account + "&timestamp="
                + timestamp + "&sign=" + sign + "&sign_type=" + sign_type;
        logger.info("url" + url);
        RestClient client = new RestClient();
        Resource resource = client.resource(url);
        ClientResponse response = resource.get();
        // 获取状态码
        Integer data = response.getStatusCode();
        logger.info("data", data);

        // 接收返回响应体
        String result = response.getEntity(String.class);
        logger.info("getRoles接受返回响应体result" + result);
        // TODO 看下核心类库有JSON向对象转换的封装
        JSONObject jsonObj = JSONObject.fromObject(result);
        String isSuccess = jsonObj.getString("status");
        if (data.equals(200)) {
            Object jsonMsg = jsonObj.getJSONArray("data");
            logger.info("jsonMsg" + jsonMsg);
            resultBean.setIsSuccess(true);
            resultBean.setMessage("获取角色列表成功");
            resultBean.setData(jsonMsg);
            logger.info("data为200成功的时候返回的resultBean：" + resultBean);
            return resultBean;
        } else {
            String message = jsonObj.getString("message");
            resultBean.setIsSuccess(false);
            resultBean.setMessage(message);
            resultBean.setData(isSuccess);
            return resultBean;
        }
    }
    
    
    /**
     * 
     * @Title: givePrize
     * @Description: 根据角色发奖
     * @param @param playerName
     * @param @param itemsInfos
     * @param @param reason
     * @param @param gameId
     * @param @param serverIP
     * @param @param serverPort
     * @param @return
     * @param @throws Exception
     * @return Integer
     * @throws
     */
    public ResultBean<Boolean> givePrize(int gameId, String playerName, String itemsInfos,
            String reason, String serverIP, String serverPort) {
        ResultBean<Boolean> resultBean = new ResultBean<>();
        logger.info("playerName" + playerName);
        logger.info("itemsInfos" + itemsInfos);
        logger.info("serverIP" + serverIP);
        logger.info("serverPort" + serverPort);
        long timestamp = System.currentTimeMillis() / 1000;
        String sign_type = "MD5";
        String sign = getMD5ByPlayerAndServer(String.valueOf(playerName),
                String.valueOf(itemsInfos), String.valueOf(reason), gameId,
                String.valueOf(serverIP), String.valueOf(serverPort), timestamp);
        String url = "http://action.module.gyyx.cn/V1/ToGame/PlayerName/GivePresents?playerName="
                + playerName
                + "&itemsInfos="
                + itemsInfos
                + "&reason="
                + reason
                + "&GameID="
                + gameId
                + "&ServerIP="
                + serverIP
                + "&ServerPort="
                + serverPort
                + "&timestamp="
                + timestamp
                + "&sign=" + sign + "&sign_type=" + sign_type;
        logger.info("十年缤纷活动根据角色发奖url" + url);
        RestClient client = new RestClient();
        Resource resource = client.resource(url);
        ClientResponse response = (resource).post(url);
        // 获取状态码
        Integer data = response.getStatusCode();
        logger.info("十年缤纷活动根据角色发奖状态码data：" + data);
        // 接收返回响应体
        String result = response.getEntity(String.class);
        logger.info("givePrize接受返回响应体result" + result);
        // 看下核心类库有JSON向对象转换的封装
        JSONObject jsonObj = JSONObject.fromObject(result);
        String message = jsonObj.getString("Message");
        if (data.equals(200)) {
        	 resultBean.setIsSuccess(true);
             resultBean.setMessage(message);
             resultBean.setData(true);
            logger.info("data为200成功的时候返回的resultBean：" + resultBean);
            return resultBean;
        } else {
        	resultBean.setIsSuccess(false);
            resultBean.setMessage(message);
            resultBean.setData(false);
            logger.info("data不为200失败的时候返回的resultBean：" + resultBean);
            return resultBean;
        }
    }
    
    private String requestGet(String urlStr, String encode) {
        RestClient client = new RestClient();
        Resource resource = client.resource(urlStr).accept("application/json");
        ClientResponse response = resource.get();
        String result = response.getEntity(String.class);
        return result;
    }
    
    /**
     * 
     * @Title: getMD5ByPlayerAndServer
     * @Description: TODO 根据角色发奖方法加密
     * @param @param playerName
     * @param @param itemsInfos
     * @param @param reason
     * @param @param gameId
     * @param @param serverIP
     * @param @param serverPort
     * @param @param timestamp
     * @param @return
     * @return String
     * @throws
     */
    private String getMD5ByPlayerAndServer(String playerName,
            String itemsInfos, String reason, int gameId, String serverIP,
            String serverPort, long timestamp) {
        String integtationURL = "/V1/ToGame/PlayerName/GivePresents?GameID="
                + gameId + "&itemsInfos=" + itemsInfos + "&playerName="
                + playerName + "&reason=" + reason + "&ServerIP=" + serverIP
                + "&ServerPort=" + serverPort + "&timestamp=" + timestamp
                + "123456";
        return MD5.encode(integtationURL);
    }
    /**
     * 
     * @Title: getMD5ByAccountAndServer
     * @Description: 获取角色方法MD5加密
     * @param @param gameId
     * @param @param serverIP
     * @param @param serverPort
     * @param @param account
     * @param @param timestamp
     * @param @return
     * @return String
     * @throws
     */
    private String getMD5ByAccountAndServer(int gameId, String serverIP, String serverPort,
            String account, long timestamp) {
        String integtationURL = "/v1/togame/role?account=" + account
                + "&gameId=" + gameId + "&serverIP=" + serverIP
                + "&serverPort=" + serverPort + "&timestamp=" + timestamp
                + "123456";
        return MD5.encode(integtationURL);
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
}
