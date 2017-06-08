/*-------------------------------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：tanjunkai
 * 联系方式：tanjunkai@gyyx.cn
 * 创建时间： 2017年2月27日
 * 版本号：v1.0
 * 本类主要用途描述：
 * 广告系统接口
-------------------------------------------------------------------------*/
package cn.gyyx.action.service.agent;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.noviceoa.NoviceChannelBean;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class CallWebApiAdAgent {

	private static final Logger logger = GYYXLoggerFactory.getLogger(CallWebServiceAgent.class);
	private static final String CHANNEL_URL = "http://as.module.gyyx.cn/ad/list";
	private static final String SIGNKEY = "123456";

	/*
	 * 获取活动渠道
	 */
	public static ResultBean<List<NoviceChannelBean>> getAdChannel() {
		ResultBean<List<NoviceChannelBean>> resultBean = new ResultBean<List<NoviceChannelBean>>();
		long timestamp = System.currentTimeMillis() / 1000;
		String sign_type = "MD5";
		String sign = MD5.encode("/ad/list?timestamp=" + timestamp + SIGNKEY);
		String url = CHANNEL_URL + "?timestamp=" + timestamp + "&sign=" + sign + "&sign_type=" + sign_type;
		logger.debug("sign", sign);
		logger.debug("url", url);
		try {
			Response response = Request.Get(url).execute();
			if (response != null) {
				HttpResponse httpResponse = response.returnResponse();
				com.alibaba.fastjson.JSONObject responseObject = com.alibaba.fastjson.JSONObject
						.parseObject(EntityUtils.toString(httpResponse.getEntity()));
				List<NoviceChannelBean> list = com.alibaba.fastjson.JSON.parseArray(responseObject.getString("Data"),
						NoviceChannelBean.class);
				if (list != null) {
					resultBean.setIsSuccess(true);
				} else {
					resultBean.setIsSuccess(false);
				}
				resultBean.setData(list);
				return resultBean;
			}
		} catch (ClientProtocolException e) {
			logger.error(Throwables.getStackTraceAsString(e));
		} catch (IOException e) {
			logger.error(Throwables.getStackTraceAsString(e));
		}
		return resultBean;
	}
}
