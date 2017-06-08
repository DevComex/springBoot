 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月18日下午2:18:06
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.module.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.utils.SignTools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

public class CheckSignInterceptor implements HandlerInterceptor {

	/**
	 * 打印日志
	 */
	private static final Logger logger = GYYXLoggerFactory.getLogger(CheckSignInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 遍历请求携带的所有数据 sign_type略过
		Map<String, String> requestParams = new HashMap<>();
		Map<String, String> signParams = new HashMap<>();
		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();

		for (Map.Entry<String, String[]> item : params.entrySet()) {
			String key = item.getKey();
			String[] values = item.getValue();
			if ("sign_type".equals(item.getKey()) || "sign".equals(item.getKey())) {
				signParams.put(key, new String(values[0].getBytes(), "UTF-8"));
				continue;
			}
			requestParams.put(item.getKey(), new String(values[0].getBytes(), "UTF-8"));
		}
		logger.info(String.format("验证签名开始：requestParams:%s", requestParams));
		
		signParams.put("key", "123456");
		if (SignTools.signIsLegal(request.getRequestURI(), requestParams, signParams)) {
			return true;
		}
		
		setSignErrorResponse(response);
		return false;
	}

	/**
	 * @Title:setResponse
	 * @param response
	 * @return void
	 * @throws @Description:
	 *             设置签名失败的返回数据
	 */
	private void setSignErrorResponse(HttpServletResponse response) {
		ResultBean<String> result=new ResultBean<>("invalid-signature", "签名错误", "failed");
		ObjectMapper jsonMapper = new ObjectMapper();
		String jsonData = null;
		try {
			jsonData = jsonMapper.writeValueAsString(result);
		} catch (Exception e) {
			logger.error(String.format("验证签名异常:%s", Throwables.getStackTraceAsString(e)));
		}
		response.setStatus(401);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().print(jsonData);
		} catch (Exception e) {
			logger.error(String.format("验证签名异常:%s", Throwables.getStackTraceAsString(e)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}


}
