package cn.gyyx.wd.wanjia.cartoon.service.angent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.wink.common.internal.uri.UriEncoder;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.cartoon.beans.MessageBean;
import net.sf.json.JSONObject;

/**
 * 
 * <p>
 * InterfacePlaywdAnget描述 playwd.gyyx.cn 站点接口
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
public class InterfacePlaywdAnget {
    private static String URI = "http://playwd.module.gyyx.cn:8025/message";
    private static String key = "123456";
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(InterfacePlaywdAnget.class);

    private static String getMD5(MessageBean message, long timestamp) throws UnsupportedEncodingException {
        String interfaceURL = "/message?contentId=" + message.getContentId()
                + "&contentTitle=" + message.getContentTitle() + "&contentType="
                + message.getContentType() + "&message=" + message.getMessage()
                + "&messageType=" + message.getMessageType() + "&timestamp="
                + timestamp + "&userId=" + message.getUserId() + key;
        String result = MD5.encode(interfaceURL);

        return result;
    }
    public static boolean insterMessage(MessageBean message) {
        String uriAPI = URI;// Post方式没有参数在这里
        String signType = "MD5";
        HttpPost httpRequst = new HttpPost(uriAPI);// 创建HttpPost对象
        long timestamp =System.currentTimeMillis() / 1000;
        try {
        String sign = getMD5(message, timestamp);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(
                new BasicNameValuePair("timestamp", String.valueOf(timestamp)));
            params.add(new BasicNameValuePair("userId",
                    String.valueOf(message.getUserId())));
            params.add(new BasicNameValuePair("contentId",
                    String.valueOf(message.getContentId())));
            params.add(new BasicNameValuePair("contentTitle",
                    message.getContentTitle()));
            params.add(new BasicNameValuePair("contentType",
                    message.getContentType()));
            params.add(new BasicNameValuePair("message",
                    message.getMessage() ));
            params.add(new BasicNameValuePair("messageType",
                    message.getMessageType()));
            params.add(new BasicNameValuePair("sign", sign));
            params.add(new BasicNameValuePair("sign_type", signType));
            logger.info("玩家天地调用playwd.module.gyyx.cn 插入消息接口:", uriAPI);
            httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(httpRequst);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);// 取出应答字符串
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                boolean isSuccess = (boolean) JSONObject.fromObject(result)
                        .get("isSuccess");
                logger.info("玩家天地调用playwd.module.gyyx.cn 插入消息接口:", result);
                return isSuccess;
            }
        } catch (Exception e) {

            logger.error("玩家天地调用playwd.module.gyyx.cn 插入消息接口异常:",
                Throwables.getStackTraceAsString(e));
            return false;
        }
        return false;
    }
}
