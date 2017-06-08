package cn.mahjong.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.mahjong.beans.common.Result;
import cn.mahjong.beans.common.SignUtil;

/**
  * <p>
  *   CheckSignInterceptor描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public class CheckSignInterceptor implements HandlerInterceptor  {
    
    /**
     * 打印日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckSignInterceptor.class);
    
    @Value("${mahjong.signkey}")
    private String signKey;

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        // 遍历请求携带的所有数据 sign_type略过
        Map<String, String> requestParams = new HashMap<>();
        Map<String, String> signParams = new HashMap<>();
        Map<String, String[]> params = request.getParameterMap();

        for (Map.Entry<String, String[]> item : params.entrySet()) {
                String key = item.getKey();
                String[] values = item.getValue();
                if ("sign_type".equals(item.getKey()) || "sign".equals(item.getKey())) {
                        signParams.put(key, values[0]);
                        continue;
                }
                requestParams.put(key, values[0]);
        }
        LOGGER.info("Sign Check request params: {}", requestParams);
        signParams.put("key", signKey);

        if (SignUtil.signIsLegal(request.getRequestURI(), requestParams, signParams)) {
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
            
        ObjectMapper jsonMapper = new ObjectMapper();
        
        String jsonData = null;
        try {
                Result<String> result = new Result<>();
                result.setStatus("failed");
                result.setMessage("签名错误");
                LOGGER.info("Sign Check request errorMsg: {}", result.toString());
                jsonData = jsonMapper.writeValueAsString(result);
                LOGGER.info("Sign Check request errorMsg: {}", jsonData);
        } catch (JsonProcessingException e) {
                LOGGER.error("序列化错误信息失败：{}", e);
                e.printStackTrace();
        }
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
                response.getWriter().write(jsonData);
        } catch (IOException e) {
                LOGGER.error("写入响应信息异常：{}", e);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        
    }

}
