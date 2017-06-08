package cn.mahjong.service;

import javax.servlet.http.HttpServletResponse;

import cn.mahjong.beans.AccountLoginLog;
import cn.mahjong.beans.common.ResultBean;

/**
  * <p>
  *   LoginService描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public interface LoginService {
    //登录
    ResultBean<Object> login(String account,String pwd,String loginIp,AccountLoginLog accountLoginLog,
            HttpServletResponse response,Boolean isHead);
    
    //获取账号是否需要验证码
    Boolean isNeedCaptcha(String account);
}
