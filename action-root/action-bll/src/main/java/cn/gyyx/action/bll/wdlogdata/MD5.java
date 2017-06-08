package cn.gyyx.action.bll.wdlogdata;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;

import cn.gyyx.action.beans.wdlogdata.WdLogDataBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 对url参数进行排序，并进行md5加密
 * 
 * @author liqiang
 */
public class MD5 {
	/**
	 * 加密串的组成元素
	 */
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final Logger logger = GYYXLoggerFactory.getLogger(MD5.class);

	/**
	 * 由指定url参数生成一个验证标签并返回 mapping 映射部分 param 参数,要加密的部分 key 加密密钥
	 * */
	public static String encode(String mapping, String param, String key,
			String sign_type) {
		/* url参数排序重构 */
		Map<String, String> treeMap = strToMap(param);// treeMap默认会以key值升序排序
		StringBuilder sb = new StringBuilder();
		sb.append(mapping).append("?").append(mapToStr(treeMap)).append(key);
		logger.info("待加密的源参数串为:" + sb.toString());
		try {
			MessageDigest md5 = MessageDigest.getInstance(sign_type);
			byte[] raw = sb.toString().getBytes("UTF-8");
			byte[] bytes = md5.digest(raw);
			int len = bytes.length;
			StringBuilder buf = new StringBuilder(len * 2);
			// 把密文转换成十六进制的字符串形式
			for (int j = 0; j < len; j++) {
				buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
				buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * 把参数字符串拆分然后放入TreeMap中
	 * ip=10.3.4.34&mac=7446a0c4b2a0&timestamp=1447991525&ver
	 * =1.57.0901&sign_type=MD5转为 ip 10.3.4.34 mac 7446a0c4b2a0 timestamp
	 * 1447991525 ver 1.57.0901 sign_type MD5
	 */
	private static Map<String, String> strToMap(String origin) {
		
		String[] params = origin.split("=|&");
		Map<String, String> result = new TreeMap<String, String>();
			for (int i = 0; i < params.length; i += 2) {
			result.put(params[i], params[i + 1]);
		}
			return result;
		
	}

	/*
	 * 把TreeMap里排序好的数据合并成一个字符串 ip 10.3.4.34 mac 7446a0c4b2a0 sign_type MD5
	 * timestamp 1447991525 ver 1.57.0901转为
	 * ip=10.3.4.34&mac=7446a0c4b2a0&timestamp
	 * =1447991525&ver=1.57.0901&sign_type=MD5
	 */
	private static StringBuilder mapToStr(Map<String, String> origin) {
		StringBuilder result = new StringBuilder();
		for (String key : origin.keySet()) {
			result.append(key).append("=").append(origin.get(key)).append("&");
		}
		/* 去掉多余的& */
		result.deleteCharAt(result.length() - 1);
		return result;
	}
}
