 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月18日下午2:24:09
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * url参数操作类
 * @author lidudi
 *
 */
public class QueryParamsTools {

	/**
	 * @Title:signString
	 * @param params
	 *            HashMap
	 * @return
	 * @return String
	 * @throws @Description:
	 *             对非空 参数进行拼接
	 */
	public static String signStringDelEmptyParam(Map<String, String> params,boolean excludeZero,String spliter) {
		List<String> keys = new ArrayList<>(params.keySet());
		Collections.sort(keys);
		
		StringBuilder prestr = new StringBuilder("");

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (StringUtils.isEmpty(value) || 
					(excludeZero && "0".equals(value))) {// value为空时不参与签名
				continue;
			}
			prestr.append(key).append("=").append(value);
			if ( !StringUtils.isEmpty(spliter) && i != keys.size() - 1) {
				prestr.append(spliter);
			}
		}
		
		if ( !StringUtils.isEmpty(spliter) && prestr.length() > 0 && prestr.toString().endsWith(spliter)) {
			return prestr.substring(0, prestr.length() - 1);
		}
		return prestr.toString();
	}
}
