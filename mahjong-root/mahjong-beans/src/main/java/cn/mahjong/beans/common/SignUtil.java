package cn.mahjong.beans.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


/**
 * @ClassName: SignUtil.java
 * @author 
 * @date 2016年8月29日 下午4:08:59
 * @description 签名工具
 */
public class SignUtil {

	public static final String SIGN_TYPE = "MD5";
	/**
	 * 打印日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);

	/**
	 * @Title: signUrl @Description: 根据URL进行签名 @param signUrl @return
	 * String @throws
	 */
	public static String signUrl(String signUrl, String key) {
		try {
			URL url = new URL(signUrl);
			long currentTime = System.currentTimeMillis() / 1000;
			String query = url.getQuery() + "&timestamp=" + currentTime;
			String path = url.getPath();
			String sign = getSigns(path, query, key);
			return signUrl + "&timestamp=" + currentTime + "&sign=" + sign + "&sign_type=MD5";
		} catch (MalformedURLException e) {
			LOGGER.error("签名URL失败：{}", e);
		}
		return null;
	}

	/**
	 * @Title: getSign @Description: 字符串URL进行签名 @param partUrl @param paramsStr
	 * query path @param key @return String @throws
	 */
	public static String getSigns(String partUrl, String paramsStr, String key) {
		Map<String, String> params = parseQueryParams(paramsStr);
		return getSign(partUrl, params, key);
	}

	/**
	 * @Title: parseQueryParams @Description: 根据url获取参数为map @param
	 * paramsStr @return Map<String,String> @throws
	 */
	public static Map<String, String> parseQueryParams(String paramsStr) {
		if (StringUtils.isBlank(paramsStr)) {
			return null;
		}
		Map<String, String> params = new HashMap<>();
		String newParamsStr = paramsStr;
		if (newParamsStr.contains("?") && newParamsStr.split("\\?").length >= 2) {
		    newParamsStr = newParamsStr.split("\\?")[1];
		}
		String[] splits = newParamsStr.split("&");
		Arrays.asList(splits).stream().forEach((info) -> {
			String[] split = info.split("=");
			if (split.length == 2) {
				params.put(split[0], split[1]);
			} else if (split.length == 1) {
				params.put(split[0], "");
			}
		});
		return params;
	}

	/**
	 * @Title:getSign
	 * @param partyUrl
	 *            url的部分
	 *            如：http://www.wyx.cn/api/ChargeUser/?key1=value1中/api/
	 *            ChargeUser/?
	 * @param params
	 *            Hashmap键值对封装参数
	 * @param key
	 *            key
	 * @param input_charset
	 *            编码
	 * @return
	 * @return String
	 * @throws @Description:
	 *             请求的sign生成方法
	 */
	public static String getSign(String partUrl, Map<String, String> params, String key) {
		// 必须有参数
		if (CollectionUtils.isEmpty(params) || stringIsEmpty(partUrl) || stringIsEmpty(key)) {
			return "";
		}
		// url改為 以?結尾的
		String url = partUrl;
		if (!url.endsWith("?")) {
		    url += "?";
		}
		// url+?+params拼接成的string
		String signString = url + signStringDelEmptyParam(params, false, "&") + key;
		LOGGER.info("-------------signString:" + signString);
		byte[] hash;
		try {
			hash = MessageDigest.getInstance(SIGN_TYPE).digest(signString.getBytes("UTF-8"));
			StringBuilder hex = new StringBuilder(hash.length * 2);
			for (byte b : hash) {
				if ((b & 0xFF) < 0x10)
					hex.append("0");
				hex.append(Integer.toHexString(b & 0xFF));
			}
			return hex.toString();
		} catch (Exception e) {
			LOGGER.error("MD%算法签名异常：{}", e);
		}
		return "";
	}

	/**
	 * @Title: signString @Description: 抛出指定不需要签名、为空的参数
	 * 此方法能够将map组装成&链接的url字符串，第二个参数去掉不需要拼装的参数。 @param params @param
	 * exceptKeys @return String @throws
	 */
	public static String combineIntoUrlParams(Map<String, String> params, boolean excludeZero, String spliter,
			String... exceptKeys) {
		Map<String, String> signMap = new HashMap<>();

		params.keySet().stream().filter(
				e -> (exceptKeys == null || "".equals(exceptKeys[0])) ? true : !Arrays.asList(exceptKeys).contains(e))
				.forEach(e -> {
					signMap.put(e, params.get(e) != null ? String.valueOf(params.get(e)) : "");
				});

		return signStringDelEmptyParam(signMap, excludeZero, spliter);
	}

	/**
	 * @Title: signString @Description: 抛出指定不需要签名的参数
	 * 此方法能够将map组装成&链接的url字符串，第二个参数去掉指定不需要拼装的参数。 @param params @param
	 * exceptKeys @return String @throws
	 */
	public static String combineIntoUrlParams(Map<String, String> params, String spliter, String... exceptKeys) {
		Map<String, String> signMap = new HashMap<>();

		params.keySet().stream().filter(
				e -> (exceptKeys == null || "".equals(exceptKeys[0])) ? true : !Arrays.asList(exceptKeys).contains(e))
				.forEach(e -> {
					signMap.put(e, params.get(e) != null ? String.valueOf(params.get(e)) : "");
				});

		return signStringAllParams(signMap, spliter);
	}

	/**
	 * @Title:signString
	 * @param params
	 *            HashMap
	 * @return
	 * @return String
	 * @throws @Description:
	 *             对非空 参数进行拼接
	 */
	public static String signStringDelEmptyParam(Map<String, String> params, boolean excludeZero, String spliter) {
		List<String> keys = new ArrayList<>(params.keySet());
		Collections.sort(keys);

		StringBuilder prestr = new StringBuilder("");

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (stringIsEmpty(value) || (excludeZero && "0".equals(value))) {// value为空时不参与签名
				continue;
			}
			prestr.append(key).append("=").append(value);
			if (!StringUtils.isEmpty(spliter) && i != keys.size() - 1) {
				prestr.append(spliter);
			}
		}

		if (!StringUtils.isEmpty(spliter) && prestr.length() > 0 && prestr.toString().endsWith(spliter)) {
			return prestr.substring(0, prestr.length() - 1);
		}
		return prestr.toString();
	}

	/**
	 * @Title:signString
	 * @param params
	 *            HashMap
	 * @return
	 * @return String
	 * @throws @Description:
	 *             对所有参数进行拼接
	 */
	public static String signStringAllParams(Map<String, String> params, String spliter) {
		List<String> keys = new ArrayList<>(params.keySet());
		Collections.sort(keys);

		StringBuilder prestr = new StringBuilder("");

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			prestr.append(key).append("=").append(value);
			if (!StringUtils.isEmpty(spliter) && i != keys.size() - 1) {
				prestr.append(spliter);
			}
		}

		if (!StringUtils.isEmpty(spliter) && prestr.length() > 0 && prestr.toString().endsWith(spliter)) {
			return prestr.substring(0, prestr.length() - 1);
		}
		return prestr.toString();
	}

	/**
	 * @Title: signIsLegal @Description: 签名是否合法 @param partUrl @param
	 * params @param key @return boolean @throws
	 */
	public static boolean signIsLegal(String partUrl, Map<String, String> requestParams,
			Map<String, String> signParams) {
		String key = signParams.get("key");
		String sign = signParams.get("sign");
		// 参数为空时 不合法
		if (CollectionUtils.isEmpty(requestParams) || stringIsEmpty(partUrl) // key和签名Url为空
				|| stringIsEmpty(key)) {
			return false;
		}
		String timeStampStr = requestParams.get("timestamp");
		if (stringIsEmpty(sign)// 不包含 sign 和时间戳时，不合法
				|| stringIsEmpty(timeStampStr) || !charsIs0To9(timeStampStr)) {// 时间戳全是数字
																				// 否则不合法
			return false;
		}
		// 获取当前时间戳
		long currentTime = System.currentTimeMillis() / 1000;
		long timeStamp = Long.parseLong(timeStampStr);
		// 相差五分钟以上 不合法
		if (Math.abs(currentTime - timeStamp) > 300)
			return false;
		LOGGER.info("-------------sign:" + sign);
		// 签名相同则合法
		if (sign.equals(getSign(partUrl, requestParams, key))) {
			return true;
		}
		return false;
	}

	/**
	 * @Title:stringIsEmpty
	 * @param query
	 * @return
	 * @return boolean
	 * @throws @Description:
	 *             验证String是否长度为空，或者为null
	 */
	private static boolean stringIsEmpty(String query) {
		boolean ret = false;
		if (query == null || "".equals(query.trim())) {
			ret = true;
		}
		return ret;
	}

	/**
	 * @Title:charsIs0To9
	 * @param chars
	 * @return
	 * @return boolean
	 * @throws @Description:
	 *             string的char是否都是0-9之间的
	 */
	private static boolean charsIs0To9(String chars) {
		return chars.matches("[0-9]+");
	}
}
