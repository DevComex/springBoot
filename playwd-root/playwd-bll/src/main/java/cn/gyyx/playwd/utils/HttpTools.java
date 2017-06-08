package cn.gyyx.playwd.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求工具类
 * @author chenglong
 *
 */
public class HttpTools {
	private static String CHARSET_UTF8 = "UTF-8";
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(HttpTools.class);
	
	public static String get(String url) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String content = "";
		try {
			HttpGet httpGet = new HttpGet(url);
			
			CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                	content = getResponse(resEntity,CHARSET_UTF8);
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
		} finally {
			httpclient.close();
		}

		return content;
	}
	
	public static String post(String url, Map<String, String> params) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String content = null;
		try {
			HttpPost httppost = new HttpPost(url);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
	        if(params != null && params.size() > 0){
	        	Set<String> keySet = params.keySet();
				for (String key : keySet) {
					String value = params.get(key);
					formparams.add(new BasicNameValuePair(key, value)); 
				}
				httppost.setEntity(new UrlEncodedFormEntity(formparams, CHARSET_UTF8));  
	        }
			
			CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                	content = getResponse(resEntity,CHARSET_UTF8);
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
		} finally {
			httpclient.close();
		}

		return content;
	}
	
	private static String getResponse(HttpEntity entity, String encode) throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStream in = entity.getContent();
        Reader reader = new InputStreamReader(in, encode);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            line = new String(line.getBytes());
            buffer.append(line);
        }
        bufferedReader.close();
        reader.close();
        return buffer.toString();
    }
	
	public static boolean isAjaxRequest(HttpServletRequest request) throws IOException {
		if(request.getHeader("accept") != null && request.getHeader("accept").contains("application/json")){
			return true;
		}
		if(request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest")){
			return true;
		}
        return false;
    }
	
	public static void main(String[] args) throws Exception {
		for(int i=0;i<100000;i++){
			HttpTools.get("http://www.zhaohaowang.com/zifei/zfxx.aspx?Mid=3&Pid=1&Cid=101");
		}
		
	}
}
