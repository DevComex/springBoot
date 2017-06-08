 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月18日下午2:21:05
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.utils;

import java.security.MessageDigest;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

import com.google.common.base.Throwables;

/**
 * 签名工具
 * @author lidudi
 *
 */
public class SignTools {

	private static final Logger logger = GYYXLoggerFactory.getLogger(SignTools.class);
	
	public static final String SIGN_TYPE = "MD5";
	
	/**
	  * @Title: signIsLegal
	  * @Description: 签名是否合法
	  * @param partUrl  请求的URL
	  * @param requestParams 请求的参数，不包含签名参数
	  * @param signParams  签名参数，key,sign
	  * @return 
	  * boolean 
	  * @throws
	  */
	public static boolean signIsLegal(String partUrl, Map<String, String> requestParams,  Map<String, String> signParams) {
		String key = signParams.get("key");
		String sign = signParams.get("sign");
		//参数为空时 不合法
		if (CollectionUtils.isEmpty(requestParams) 
				|| stringIsEmpty(partUrl) // key和签名Url为空 
				|| stringIsEmpty(key) ) {
			return false;
		}
		String timeStampStr = requestParams.get("timestamp");
		if(stringIsEmpty(sign)//不包含 sign 和时间戳时，不合法
				|| stringIsEmpty(timeStampStr)
				|| !charsIs0To9(timeStampStr)){// 时间戳全是数字 否则不合法
			return false;
		}
		//获取当前时间戳
		long currentTime = System.currentTimeMillis() / 1000;
		long timeStamp = Long.parseLong(timeStampStr);
		// 相差五分钟以上 不合法
		if (Math.abs(currentTime - timeStamp) > 300)
			return false;
		
		//签名相同则合法
		if (sign.equals(generateSignFromMap(partUrl, requestParams, key))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Title:getSign
	 * @param partyUrl
	 *            url的部分
	 *            如：http://api.mobile.gyyx.cn/api/ChargeUser/?key1=value1中/api/
	 *            ChargeUser/?
	 * @param params
	 *            Hashmap键值对封装参数
	 * @param key
	 *            key
	 * @param input_charset
	 *            编码
	 * @return String
	 * @throws @Description:
	 *             请求的sign生成方法
	 */
	public static String generateSignFromMap(String partUrl, Map<String, String> params, String key) {
		// 必须有参数
		if (CollectionUtils.isEmpty(params) || stringIsEmpty(partUrl) || stringIsEmpty(key)) {
			return "";
		}
		//url改為 以?結尾的
		if(!partUrl.endsWith("?")){
			partUrl += "?";
		}
		//url+?+params拼接成的string
		String signString = partUrl+ QueryParamsTools.signStringDelEmptyParam(params,false,"&") + key;
		logger.info(String.format("验证签名开始：signString:%s", signString));
		byte[] hash;
		try {
			hash = MessageDigest.getInstance(SIGN_TYPE).digest(signString.getBytes("UTF-8"));
			StringBuilder hex = new StringBuilder(hash.length * 2);
			for (byte b : hash) {
				if ((b & 0xFF) < 0x10)
					hex.append("0");
				hex.append(Integer.toHexString(b & 0xFF));
			}
			logger.info(String.format("验证签名开始：hex:%s", hex.toString()));
			return hex.toString();
		} catch (Exception e) {
			logger.error(String.format("验证签名异常:%s", Throwables.getStackTraceAsString(e)));
		}
		return "";
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
