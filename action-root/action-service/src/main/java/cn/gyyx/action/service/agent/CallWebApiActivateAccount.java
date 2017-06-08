/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：matao
 * @联系方式：matao@gyyx.cn
 * @创建时间： 2016年3月23日 
 * @本类主要用途描述：激活账号，只使用不需要激活码情况（原因：接口激活码规则）
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;

import cn.gyyx.action.beans.callwebservice.CallWebServiceResultMsg;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;


public class CallWebApiActivateAccount {
	private static final Logger LOG = GYYXLoggerFactory.getLogger(CallWebApiActivateAccount.class);
	private static final String ACTIVATE_URL = "http://interface.account.gyyx.cn/game/client/register";
	private static final String KEY = "123456";
	private TreeMap<String,String> para = new TreeMap<>();

	/**
	 * 无激活码
	 * @param gameId  游戏编号
	 * @param serverId  区服编号
	 * @param account  账号
	 * @param gamePwd  游戏密码
	 * @param ip  登录IP
	 */
	public CallWebApiActivateAccount(String gameId, String serverId,String account, String gamePwd, String ip) {
		para.put("GameId", gameId);
		para.put("ServerId", serverId);
		para.put("Account", account);
		para.put("GamePwd", gamePwd);
		para.put("Ip", ip);
		para.put("IsNeedActiveCode", "0");
		para.put("ActiveCode", "default");
		LOG.debug("new ActivateAccount instance (no activeCode)!"+gameId+";"+serverId+";"+account+";"+gamePwd+";"+ip);
	}


	/**
	 * 有激活码
	 * @param gameId  游戏编号
	 * @param serverId  区服编号
	 * @param account  账号
	 * @param gamePwd  游戏密码
	 * @param ip  登录IP
	 * @param activeCode  激活码
	 */
	public CallWebApiActivateAccount(String gameId, String serverId,String account, String gamePwd, String ip, String activeCode) {
		para.put("GameId", gameId);
		para.put("ServerId", serverId);
		para.put("Account", account);
		para.put("GamePwd", gamePwd);
		para.put("ActiveCode", activeCode);
		para.put("Ip", ip);
		para.put("IsNeedActiveCode", "1");
		LOG.debug("new ActivateAccount instance (have activeCode)!"+gameId+";"+serverId+";"+account+";"+gamePwd+";"+ip+";"+activeCode);
	}
	/**
	 * 执行激活
	 * @return
	 * @throws IOException 数据流关闭失败！
	 */
	public ResultBean<String> executeActivate () {
		LOG.debug("start to execute activate !");
		para.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
		ResultBean<String> result = new ResultBean<>(false,"激活失败!",CallWebServiceResultMsg.DEFAULT);
		String resultStr = post(CallWebApiActivateAccount.ACTIVATE_URL, this.para);
		String isSuccess="",jsonMsg = "";
		try{
			JSONObject jsonObj = JSONObject.fromObject(resultStr); 
			isSuccess = jsonObj.getString("IsSuccess");
			jsonMsg = jsonObj.getString("Message");
			LOG.info("response api message("+para.get("Account")+":"+para.get("Ip")+"):"+isSuccess+"--"+jsonMsg);
		}catch(JSONException e){
			LOG.error("activate 转json 失败!待转换字符串："+resultStr,e);
			result.setProperties(false, "激活异常!",CallWebServiceResultMsg.BAD_REQUEST);
			return result;
		}
		if("".equals(isSuccess)){
			result.setProperties(false, "激活异常!", CallWebServiceResultMsg.ERROR);
		}else if("true".equals(isSuccess)){
			result.setProperties(true, jsonMsg, CallWebServiceResultMsg.TRUE);
		}else{
			result.setProperties(false, "激活失败!", CallWebServiceResultMsg.FALSE);
		}
		LOG.debug("finished activate !");
		return result;
	}
	
	private String post(String url,Map<String,String> paraMap) {
		StringBuilder response = new StringBuilder("");
		String paraUrl = url+"?"+prepareParam(paraMap, CallWebApiActivateAccount.KEY);
		LOG.debug("get url("+para.get("Account")+":"+para.get("Ip")+"):"+paraUrl);
		HttpClient client = new HttpClient();
		PostMethod method = setRequestPara(paraUrl, paraMap);
    	try{
	    	client.executeMethod(method);
	    	appendResult(method,response);
	    }catch(IOException e){
	    	LOG.error("request activate error!",e);
	    }finally{
	    	if(method!=null){
	    		method.releaseConnection();
	    	}
	    }
    	LOG.debug("finished request activate interface!,response:"+response.toString());
		return response.toString();
	}
	private void appendResult(PostMethod method,StringBuilder strBuilder) {
		if (method.getStatusCode() == HttpStatus.SC_OK||method.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),"utf-8"))){
				String line;
				while ((line = reader.readLine()) != null) {
					strBuilder.append(line);
				}
			} catch (IOException e) {
				LOG.warn("request activate error!");
		    	LOG.info(e.toString());
			}
		}
	}
	private PostMethod setRequestPara(String url,Map<String,String> paraMap){
		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
		NameValuePair[] param = { 
				new NameValuePair("Account", paraMap.get("Account")),
        		new NameValuePair("Ip", paraMap.get("Ip")),
        		new NameValuePair("GameId", paraMap.get("GameId")),
        		new NameValuePair("ServerId", paraMap.get("ServerId")),
        		new NameValuePair("GamePwd", paraMap.get("GamePwd")), 
        		new NameValuePair("IsNeedActiveCode", "1"),    
        		new NameValuePair("ActiveCode", paraMap.get("ActiveCode")),
        		new NameValuePair("timestamp", paraMap.get("timestamp"))
		};
		
		method.setRequestBody(param);
	    return method;
	}
	private String prepareParam(Map<String,String> paraMap,String key){
		StringBuilder paraBuffer = new StringBuilder();
		StringBuilder urlBuffer = new StringBuilder("/game/client/register");
		int i = 0;
		for(Entry<String,String> entry:paraMap.entrySet()){
			if(i == 0){
				paraBuffer.append(entry.getKey()).append("=").append(entry.getValue());
				urlBuffer.append("?").append(entry.getKey()).append("=").append(entry.getValue());
			}else{
				paraBuffer.append("&").append(entry.getKey()).append("=").append(entry.getValue());
				urlBuffer.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
			i++;
		}
		String sign = MD5.encode(urlBuffer.toString() + key);
		paraBuffer.append("&sign_type=MD5&sign=").append(sign);
		return paraBuffer.toString();
	}
	
	
	
	
	
}
