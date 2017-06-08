/**------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
-------------------------------------------------------------------------*/

package cn.gyyx.wd.wanjia.cartoon.beans.tools;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * 作 者：成龙 
 * 联系方式：chenglong@gyyx.cn 
 * 创建时间： 2016年6月6日 下午3:27:16 
 * 描 述： 字符串函数
 */
public class StringTools {
	public static String formatNumber(Number number, String fillFormat) {
		if (fillFormat == null) {
			return number + "";
		}
		DecimalFormat df = new DecimalFormat(fillFormat);
		return df.format(number);
	}

	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static double coverDouble(double value) {
		BigDecimal b = new BigDecimal(value);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * c 要填充的字符 
	 * length 填充后字符串的总长度 c
	 * ontent 要格式化的字符串 格式化字符串，左对齐
	 */
	public static String flushLeft(char c, int length, Object content) {
		String contentStr = String.valueOf(content);
		String str = "";
		String cs = "";
		if (contentStr.length() > length) {
			str = contentStr;
		} else {
			for (int i = 0; i < length - contentStr.length(); i++) {
				cs = cs + c;
			}
		}
		str = contentStr + cs;
		return str;
	}
	
	private static final String utf_8 = "UTF-8";//StandardCharsets.UTF_8.name()
	
	/**
	 * 转utf-8
	 */
	public static String getUTF8String(String value) throws UnsupportedEncodingException{
		if(value == null){
			return "";
		}
		return URLEncoder.encode(value,utf_8);
	}
	
	/**
	 * encode指定字符串
	 */
	public static String getEncodeString(String value,String encode) throws UnsupportedEncodingException{
		if(value == null){
			return "";
		}
		if(encode == null || encode.trim().equals("")){
			encode = utf_8;
		}
		return URLEncoder.encode(value,encode);
	}
	
	/**
	 * 为空返回默认值
	 */
	public static Object convertNullToDefault(Object value,Object defaultValue){
		if(value == null){
			return defaultValue;
		}
		return value;
	}

}
