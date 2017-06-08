package cn.mahjong.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.mahjong.beans.common.CookieUtil;
import cn.mahjong.redis.bll.CaptchaOperatorBll;
import cn.mahjong.service.CaptchaService;

/**
  * <p>
  *   CaptchaServiceImpl描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@Service
public class CaptchaServiceImpl implements CaptchaService{
    
    private final static String CAPTCHA_COOKIE_NAME = "MAHJONG_CAPTCHA";

    @Autowired
    private CaptchaOperatorBll captchaOperatorBll;

    public  void setCaptchaOperatorBll(CaptchaOperatorBll captchaOperatorBll) {
        this.captchaOperatorBll = captchaOperatorBll;
    }

    /* (non-Javadoc)
     * @see cn.mahjong.service.CaptchaService#checkCaptcha(java.lang.String)
     */
    @Override
    public Boolean checkCaptcha(String bid,String captchaCode,HttpServletRequest request) {
        String cookieValue = CookieUtil.getCookieValue(request, CAPTCHA_COOKIE_NAME);
        String cacheKey = bid == null ? cookieValue : bid + "_" + cookieValue;
        String captcha = captchaOperatorBll.get(cacheKey);
        if(StringUtils.isEmpty(captcha) || !captcha.equals(captchaCode)){
            return false;
        }
        
        return true;
    }
}
