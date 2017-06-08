package cn.mahjong.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;

/**
  * <p>
  *   CheckLoginInterceptor描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public class CheckLoginInterceptor implements HandlerInterceptor {
    /**
     * 打印日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLoginInterceptor.class);
    
    @Autowired
    private UserBll userBll;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
        
        CookieUserInfo cookieUserInfo = userBll.checkLogin(request);
        
        if (cookieUserInfo == null) {
            
            boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
            if(ajax){
                setSignErrorResponse(response);
            }else{
                response.sendRedirect("/login");
            }
            return false;
        }
        
        return true;
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
                    ResultBean<String> resultBean = new ResultBean<>();
                    resultBean.setIsSuccess(false);
                    resultBean.setMessage("账号未登录");
                    LOGGER.info("Sign Check request errorMsg: {}", resultBean.toString());
                    jsonData = jsonMapper.writeValueAsString(resultBean);
                    LOGGER.info("Sign Check request errorMsg: {}", jsonData);
            } catch (JsonProcessingException e) {
                    LOGGER.error("序列化错误信息失败：{}", e);
                    e.printStackTrace();
            }
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            try {
                    response.getWriter().write(jsonData);
            } catch (IOException e) {
                    LOGGER.error("写入响应信息异常：{}", e);
            }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    ModelAndView modelAndView) throws Exception {
            

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                    throws Exception {
            
    }
}
