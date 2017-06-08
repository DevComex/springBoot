package cn.mahjong.service;

import javax.servlet.http.HttpServletRequest;

/**
  * <p>
  *   CaptchaService描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public interface CaptchaService {
    //验证验证码
    Boolean checkCaptcha(String bid,String captchaCode,HttpServletRequest request);
}
