package cn.gyyx.action.service.sdsg.advice.Impl;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.action.beans.sdsg.advice.SdsgAdviceBean;
import cn.gyyx.action.bll.sdsg.advice.ISdsgAdviceBll;
import cn.gyyx.action.bll.sdsg.advice.SdsgAdviceBllImpl;
import cn.gyyx.action.service.sdsg.advice.ISdsgAdviceService;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 神道三国-你提我改
 * @ClassName: SdsgAdviceServiceImpl
 * @description SdsgAdviceServiceImpl
 * @author luozhenyu
 * @date 2016年11月15日
 */
public class SdsgAdviceServiceImpl implements ISdsgAdviceService {
	private static final Logger logger = GYYXLoggerFactory.getLogger(SdsgAdviceServiceImpl.class);

	ISdsgAdviceBll sdsgAdviceBll = new SdsgAdviceBllImpl();

	@Override
	public boolean insertSdsgAdvice(SdsgAdviceBean sdsgAdviceBean) {
		
		return sdsgAdviceBll.insertSdsgAdvice(sdsgAdviceBean)>0;
	}

	@Override
	public String getRegion() {
		StringBuffer md5Para = new StringBuffer("/v2/game/28/server?timestamp=");
		StringBuffer urlStr = new StringBuffer("http://game.module.gyyx.cn/v2/game/28/server");
		StringBuffer paraStr = new StringBuffer("timestamp=");
		long nowDate = System.currentTimeMillis()/1000;
		String nowDateStr = String.valueOf(nowDate);
		md5Para.append(nowDateStr).append("123456");
		String sign = MD5.encode(md5Para.toString());
		paraStr.append(nowDateStr).append("&sign_type=MD5").append("&sign=").append(sign);
		urlStr.append("?").append(paraStr.toString()).toString();
		String result = requestGet(urlStr.toString(),"UTF-8");
		return result;
	}
	
	
//	public void haha(){
//		StringBuffer md5Para = new StringBuffer("/api/qr/validate?timestamp=");
//		StringBuffer urlStr = new StringBuffer("http://game.module.gyyx.cn/v2/game/28/server");
//		StringBuffer paraStr = new StringBuffer("timestamp=");
//		long nowDate = System.currentTimeMillis()/1000;
//		String nowDateStr = String.valueOf(nowDate);
////		String param=String.format("&qrId=%s&type=%s&userId%s", qrId,type,userId);
//		md5Para.append(nowDateStr).append(param).append("123456");
//		String sign = MD5.encode(md5Para.toString());
//		paraStr.append(nowDateStr).append("&sign_type=MD5").append("&sign=").append(sign);
//		urlStr.append("?").append(paraStr.toString()).toString();
//		String result = requestGet(urlStr.toString(),"UTF-8");
//		
//	}
	public String requestGet(String urlStr,String encode){
		RestClient client = new RestClient();
		String result = "";
		try {
			org.apache.wink.client.Resource resource = client.resource(urlStr)
					.accept("application/json");
			ClientResponse response = (resource).get();
			// 接收返回响应体
			result = response.getEntity(String.class);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("获取列表失败" + e.getMessage());
		}
		return result;
	}

}
