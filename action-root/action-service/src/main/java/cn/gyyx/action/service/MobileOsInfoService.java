package cn.gyyx.action.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class MobileOsInfoService {

	private static class InstanceHolder {
		public static final MobileOsInfoService INSTANCE = new MobileOsInfoService();
	}
	
	public static MobileOsInfoService getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
	public String get(String userAgent) {
		String result = StringUtils.EMPTY;
		
		if (StringUtils.isEmpty(userAgent)) return result;
		
		// IOS 判断字符串  
        String iosString = " LIKE MAC OS X"; 
        if (userAgent.toUpperCase().indexOf(iosString) != -1) {
        	Matcher m = Pattern.compile("(iPhone|iPad|iPod|iOS)+", Pattern.CASE_INSENSITIVE).matcher(userAgent);
        	if (m.find()) return "ios";
        }
        
        // Android 判断  
        String androidString = "ANDROID";  
        if (userAgent.toUpperCase().indexOf(androidString) != -1) {
        	Matcher m = Pattern.compile("(Android)+", Pattern.CASE_INSENSITIVE).matcher(userAgent);
        	if (m.find()) return "android";
        }
        
		return "android";
	}
}
