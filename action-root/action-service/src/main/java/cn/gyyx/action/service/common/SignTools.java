package cn.gyyx.action.service.common;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.action.service.agent.CallWebAPIUserInfoAgent;
import cn.gyyx.core.HttpUtil;
import cn.gyyx.core.security.MD5;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 签名工具类  （针对签名工具2.0）
 */
public class SignTools {
	private static final Logger logger = GYYXLoggerFactory.getLogger(SignTools.class);
	
	private static final String SIGN_KEY = "123456";
	
	public static String getSignUrl(String url,Long timestamp) throws Exception {
		if (url == null) {
			return null;
		}
		URL u = new URL(url);
		StringBuilder newUrl = new StringBuilder();
		String urlPath = u.getPath() == null ? "" : u.getPath().replaceAll("/$", "");

		//自定义排序 .net的默认字典排序 不完全是按照ascll码 因此自定义
		Map<String, String> mapSorted = new TreeMap<String, String>(new SignTools().new MapComparator());
		String query = u.getQuery();
		if (query != null) {
			newUrl.append(url.substring(0, url.indexOf("?")).replaceAll("/$", ""));

			if (query != null) {
				for (String q : query.split("&")) {
					if (q != null && q.contains("=")) {
						String[] keyValue = q.split("=");
						if(keyValue.length < 2){
							continue;
						}
						mapSorted.put(keyValue[0], keyValue[1]);
					}
				}
			}
		}else{
			newUrl.append(url.replaceAll("/$", ""));
		}
		
		mapSorted.put("timestamp", timestamp + "");
		//sign生成规则  : ( uri(不含末尾的/) + "?" + 含有timestamp的排序好的参数str + SIGN_KEY) 的md5值
		String path = urlPath + "?";

		Set<String> keySet = mapSorted.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			path += key + "=" + mapSorted.get(key) + "&";
		}
		path = path.replaceAll("&$", "");
		
		//拼接结果串  排行序的参数str + sign_type + sign ,其中str要对value encode u8
		keySet = mapSorted.keySet();
		iter = keySet.iterator();
		String resultPath = "";
		while (iter.hasNext()) {
			String key = iter.next();
			resultPath += key + "=" + URLEncoder.encode(mapSorted.get(key), "utf-8") + "&";
		}
		resultPath += "sign=" + MD5.encode(path + SIGN_KEY) 
				    + "&sign_type=MD5";
		newUrl.append("?").append(resultPath);
		return newUrl.toString();
	}
	
	public static String getSignUrl(String url) throws Exception {
		Long timestamp = System.currentTimeMillis()/1000;
		return getSignUrl(url,timestamp);
	}
	
	class MapComparator implements Comparator<String>{
		@Override
		public int compare(String str1, String str2) {
			int len1 = str1.length();
	        int len2 = str2.length();
	        int lim = Math.min(len1, len2);
	        char v1[] = str1.toCharArray();
	        char v2[] = str2.toCharArray();

	        int k = 0;
	        while (k < lim) {
	        	char c1 = v1[k];
	            char c2 = v2[k];
	            k++;
	            if (c1 != c2) {
	            	if(Character.isLetter(c1) && Character.isLetter(c2) ){
	            		if(Character.toLowerCase(c1) == Character.toLowerCase(c2)){
	            			if(len1 == len2){
	            				return -(c1 - c2);//不区分大小写,出现的时候,相同字母大写后,小写前
	            			}else{
	            				continue;
	            			}
	            		}else{
	            			return Character.toLowerCase(c1) - Character.toLowerCase(c2);
	            		}
	            	}
	                return c1 - c2;
	            }
	        }
	        return len1 - len2;
		}
		
	}

}
