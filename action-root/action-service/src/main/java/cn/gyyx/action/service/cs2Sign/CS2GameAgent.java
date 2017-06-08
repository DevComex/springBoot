/*************************************************
       Copyright ©, 2016, GY Game
       Author: chenpeilan
       Created: 2016年5月11日
       Note：创世2活动agent
 ************************************************/
package cn.gyyx.action.service.cs2Sign;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.cs2sign.ServerBean;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: CS2GameAgent
 * @Description: TODO 创世2活动agent
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年5月11日 上午10:39:06
 *
 */
public class CS2GameAgent {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(CS2GameAgent.class);
    private static final int GAME_ID = 27;// 创世2 ID

    /**
     * 
     * @Title: getServers
     * @Description: TODO 获取游戏区服
     * @param @return
     * @return ServerBean
     * @throws
     */
    public ServerBean getServers() {
        StringBuffer md5Para = new StringBuffer("/v2/game/27/server?timestamp=");
        StringBuffer urlStr = new StringBuffer(
                "http://game.module.gyyx.cn/v2/game/27/server");
        StringBuffer paraStr = new StringBuffer("timestamp=");
        long nowDate = System.currentTimeMillis() / 1000;
        String nowDateStr = String.valueOf(nowDate);
        md5Para.append(nowDateStr).append("123456");
        String sign = MD5.encode(md5Para.toString());
        paraStr.append(nowDateStr).append("&sign_type=MD5").append("&sign=")
                .append(sign);
        urlStr.append("?").append(paraStr.toString()).toString();
        String result = requestGet(urlStr.toString(), "UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        ServerBean serverBean = new ServerBean("false");
        try {
            serverBean = mapper.readValue(result, ServerBean.class);
        } catch (IOException e) {
            logger.error("IOException in getServers:" + e);
        }
        return serverBean;
    }

    /** 
    * 叙述:<br />
    * @param game
    * @param serverCode
    * @return JSONObject
    */
    public ResultBean<JSONObject> getServerByCode(String game,int serverCode) {
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
    public String requestGet(String urlStr, String encode) {
        RestClient client = new RestClient();
        Resource resource = client.resource(urlStr).accept("application/json");
        ClientResponse response = resource.get();
        String result = response.getEntity(String.class);
        return result;
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
    private String getMD5ByAccountAndServer(String serverIP, String serverPort,
            String account, long timestamp) {
        String integtationURL = "/v1/togame/role?account=" + account
                + "&gameId=" + GAME_ID + "&serverIP=" + serverIP
                + "&serverPort=" + serverPort + "&timestamp=" + timestamp
                + "123456";
        return MD5.encode(integtationURL);
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
    public ResultBean<Object> getRoles(String serverIP, String serverPort,
            String account) {
        ResultBean<Object> resultBean = new ResultBean<>();
        logger.info("gameId" + GAME_ID);
        logger.info("serverIP" + serverIP);
        logger.info("serverPort" + serverPort);
        logger.info("account" + account);
        long timestamp = System.currentTimeMillis() / 1000;
        String sign_type = "MD5";
        String sign = getMD5ByAccountAndServer(String.valueOf(serverIP),
                String.valueOf(serverPort), String.valueOf(account), timestamp);
        logger.info("sign" + sign);
        String url = "http://action.module.gyyx.cn/v1/togame/role?gameId="
                + GAME_ID + "&serverIP=" + serverIP + "&serverPort="
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
            resultBean.setProperties(true, "获取角色列表成功", jsonMsg);
            logger.info("data为200成功的时候返回的resultBean：" + resultBean);
            return resultBean;
        } else {
            String message = jsonObj.getString("message");
            resultBean.setProperties(false, message, isSuccess);
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
    public ResultBean<Boolean> givePrize(String playerName, String itemsInfos,
            String reason, String serverIP, String serverPort) {
        ResultBean<Boolean> resultBean = new ResultBean<>();
        logger.info("playerName" + playerName);
        logger.info("itemsInfos" + itemsInfos);
        logger.info("serverIP" + serverIP);
        logger.info("serverPort" + serverPort);
        long timestamp = System.currentTimeMillis() / 1000;
        String sign_type = "MD5";
        String sign = getMD5ByPlayerAndServer(String.valueOf(playerName),
                String.valueOf(itemsInfos), String.valueOf(reason), GAME_ID,
                String.valueOf(serverIP), String.valueOf(serverPort), timestamp);
        String url = "http://action.module.gyyx.cn/V1/ToGame/PlayerName/GivePresents?playerName="
                + playerName
                + "&itemsInfos="
                + itemsInfos
                + "&reason="
                + reason
                + "&GameID="
                + GAME_ID
                + "&ServerIP="
                + serverIP
                + "&ServerPort="
                + serverPort
                + "&timestamp="
                + timestamp
                + "&sign=" + sign + "&sign_type=" + sign_type;
        logger.info("创世2每日签到活动根据角色发奖url" + url);
        RestClient client = new RestClient();
        Resource resource = client.resource(url);
        ClientResponse response = (resource).post(url);
        // 获取状态码
        Integer data = response.getStatusCode();
        logger.info("创世2每日签到活动根据角色发奖状态码data：" + data);
        // 接收返回响应体
        String result = response.getEntity(String.class);
        logger.info("givePrize接受返回响应体result" + result);
        // 看下核心类库有JSON向对象转换的封装
        JSONObject jsonObj = JSONObject.fromObject(result);
        String message = jsonObj.getString("Message");
        if (data.equals(200)) {
            resultBean.setProperties(true, message, true);
            logger.info("data为200成功的时候返回的resultBean：" + resultBean);
            return resultBean;
        } else {
            resultBean.setProperties(false, message, false);
            logger.info("data不为200失败的时候返回的resultBean：" + resultBean);
            return resultBean;
        }
    }

}
