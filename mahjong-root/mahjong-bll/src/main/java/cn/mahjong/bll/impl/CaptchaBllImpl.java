package cn.mahjong.bll.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mahjong.beans.common.CookieUtil;
import cn.mahjong.beans.common.SimpleCaptcha;
import cn.mahjong.bll.CaptchaBll;
import cn.mahjong.redis.bll.CaptchaOperatorBll;

/**
  * <p>
  *   CaptchaBllImpl描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@Service
public class CaptchaBllImpl implements CaptchaBll {
    
    private final static String CAPTCHA_COOKIE_NAME = "MAHJONG_CAPTCHA";
    
    @Autowired
    private CaptchaOperatorBll captchaOperatorBll;
  
    /* (non-Javadoc)
     * @see cn.mahjong.service.CaptchaService#Create()
     */
    @Override
    public void create(String bid, HttpServletResponse response) {
        
        String cookieValue = UUID.randomUUID().toString();
        
        CookieUtil.addCookie(response, CAPTCHA_COOKIE_NAME, cookieValue, (int) TimeUnit.MINUTES.toSeconds(5));
        String answer = new SimpleCaptcha(response).generate();
        String cacheKey = bid == null ? cookieValue : bid + "_" + cookieValue;
        // 存redis
        captchaOperatorBll.save(answer, cacheKey);
    }
}
