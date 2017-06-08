package cn.gyyx.action.service.weixin;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class RequestResource {
	private static final Logger LOG = GYYXLoggerFactory.getLogger(RequestResource.class);
	/**
	 * get 请求
	 * @param uri
	 * @return
	 */
	public static String get(String uri){
		LOG.debug("start get ! uri："+uri);
		RestClient client = new RestClient();
		String result = "";
		try{
			Resource resource = client.resource(uri).accept("application/json");
			ClientResponse response = resource.get();
			LOG.debug("get return status:"+response.getStatusCode());
			result = response.getEntity(String.class);
		}catch(Exception e){
			LOG.warn("get request error! bad connect",e);
		}
		LOG.debug("finish get ! ");
		return result;
	}
}
