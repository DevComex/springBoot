/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年8月11日 
 * @版本号：v1.211
 * @本类主要用途描述：问道康师傅抽奖活动项目 调用interface.account.gyyx.cn接口
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.service.agent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import net.sf.json.JSONObject;


import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wdmaster.AgentResultBean;
import cn.gyyx.action.beans.wdmaster.WDMasterRegisterInfoBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.security.MD5;

public class CallWebApiInterfaceAccountAgent {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(CallWebApiAgent.class);
	private static String key = "123456";
	/**
	 *  调用interface.account.gyyx.cn接口获取注册结果
	 * @param wdMasterRegisterInfoBean
	 * @return
	 */
	public AgentResultBean getRegisterResultFromWebApi(WDMasterRegisterInfoBean wdMasterRegisterInfoBean) {
		long timestamp = System.currentTimeMillis() / 1000;
		logger.info("问道康师傅暑期推广活动V1.211___调取interface.account.gyyx.cn域名的接口开始，参数WDMasterRegisterInfoBean：", wdMasterRegisterInfoBean);
		String sign_type = "MD5";
		String sign = getMD5ByAccountAndGameAndTimestamp(wdMasterRegisterInfoBean,timestamp);
		String url = "http://interface.account.gyyx.cn/register/QuickRegist?email=" + wdMasterRegisterInfoBean.getEmail()+
				"&extender=" + wdMasterRegisterInfoBean.getExtender()+
				"&gameId=" + wdMasterRegisterInfoBean.getGameId()+
				"&idcard="+wdMasterRegisterInfoBean.getIdcard()+ 
		"&password=" + wdMasterRegisterInfoBean.getPassword() + 
		"&passwordCompare=" + wdMasterRegisterInfoBean.getPasswordCompare()+
		"&sex=" + wdMasterRegisterInfoBean.getSex() + 
		"&sourceType=" + wdMasterRegisterInfoBean.getSourceType()+
		"&truename=" + wdMasterRegisterInfoBean.getTruename() + 
		"&userName=" + wdMasterRegisterInfoBean.getUserName()+
		
		
		"&timestamp="+timestamp+"&sign_type=" + sign_type + "&sign=" + sign;
		logger.info("问道康师傅暑期推广活动V1.211___调取interface.account.gyyx.cn域名的接口，URL为："+ url);
		RestClient client = new RestClient();
		org.apache.wink.client.Resource resource = client.resource(url);
		//向resource中增加CookieStr
		resource.cookie(getCookieStr());
		ClientResponse response = (resource).post(url);
		logger.info("问道康师傅暑期推广活动V1.211___调取interface.account.gyyx.cn域名的接口，response为："+ response);
		// 接收返回响应体
		String result = response.getEntity(String.class);
		logger.info("问道康师傅暑期推广活动V1.211___调取interface.account.gyyx.cn域名的接口，result:"+result);
		AgentResultBean agentResultBean = new AgentResultBean();
		JSONObject jsonobject = JSONObject.fromObject(result);
		agentResultBean.setMsg(jsonobject.getString("Message"));
		agentResultBean.setSuccess(new Boolean(jsonobject.getString("Success")));
		logger.info("问道康师傅暑期推广活动V1.211___调取interface.account.gyyx.cn域名的接口，agentResultBean为："+agentResultBean);
		return agentResultBean;
	}
	/**
	 * 生成Cookie字符串 
	 * @return
	 */
	private String getCookieStr(){
		//生成uuid
		UUID uuid = UUID.randomUUID();   
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time = sdf.format(new Date());
		String cookieStr = "$Version=1;ADVisitTemp=6131$3054$4291$11424$2$1$"+time+
				"$0$"+uuid+"$http://wd.gyyx.cn/register/regsc.html$$"+";$Domain=gyyx.cn;$Path=/";
		logger.info("问道康师傅暑期推广活动V1.211___生成Cookie字符串参数uuid："+uuid+"  time:"+time);
		logger.info("问道康师傅暑期推广活动V1.211___生成Cookie字符串："+cookieStr);
		return cookieStr;
	}

	/**
	 * 根据参数计算sign的值
	 * @param gameaccount
	 * @param timestamp
	 * @return sign
	 */
	private String getMD5ByAccountAndGameAndTimestamp(WDMasterRegisterInfoBean wdMasterRegisterInfoBean,
			long timestamp) {
		logger.info("问道康师傅暑期推广活动V1.211___计算sign的值。参数wdMasterRegisterInfoBean:"+wdMasterRegisterInfoBean+"  &timestamp:"+timestamp);
		String integtationURL = "/register/QuickRegist?email=" + wdMasterRegisterInfoBean.getEmail()+
				"&extender=" + wdMasterRegisterInfoBean.getExtender()+
				"&gameId=" + wdMasterRegisterInfoBean.getGameId()+
				"&idcard="+wdMasterRegisterInfoBean.getIdcard()+ 
				"&password=" + wdMasterRegisterInfoBean.getPassword() + 
				"&passwordCompare=" + wdMasterRegisterInfoBean.getPasswordCompare()+
				"&sex=" + wdMasterRegisterInfoBean.getSex() + 
				"&sourceType=" + wdMasterRegisterInfoBean.getSourceType()+
				"&timestamp="+timestamp+
				"&truename=" + wdMasterRegisterInfoBean.getTruename() + 
				"&userName=" + wdMasterRegisterInfoBean.getUserName()+
				
					
				 key;
		String result = MD5.encode(integtationURL);
		logger.info("问道康师傅暑期推广活动V1.211___计算sign的值。计算结果为："+result);
		return result;
	}
}
