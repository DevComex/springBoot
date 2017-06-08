package cn.mahjong.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.mahjong.redis.bll.CaptchaOperatorBll;
import cn.mahjong.service.impl.CaptchaServiceImpl;

/**
  * <p>
  *   CaptchaServiceImplTest描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public class CaptchaServiceImplTest {
    private CaptchaOperatorBll captchaOperatorBll;
    private CaptchaServiceImpl captchaServiceImpl;
    private HttpServletRequest request;
    
    @BeforeClass
    public void setUp() {
        captchaServiceImpl = new CaptchaServiceImpl();
        captchaOperatorBll =mock(CaptchaOperatorBll.class);
        request = mock(HttpServletRequest.class);
        
        captchaServiceImpl.setCaptchaOperatorBll(captchaOperatorBll);
    }

    @Test(description = "当缓存里验证码不存在时，返回false")
    public void checkCaptcha_whenRedisCaptchaEmpty_thenReturnFalse(){
        when(captchaOperatorBll.get(anyString())).thenReturn("");
        when(request.getCookies()).thenReturn(null);
        
        boolean result = captchaServiceImpl.checkCaptcha("aaa", "aaaa", request);
        Assert.assertEquals(false, result);
    }
    
    @Test(description = "当验证码不相等时，返回false")
    public void checkCaptcha_whenRedisCaptchaNotEqual_thenReturnFalse(){
        when(captchaOperatorBll.get(anyString())).thenReturn("a");
        when(request.getCookies()).thenReturn(null);
        
        boolean result = captchaServiceImpl.checkCaptcha("aaa", "aaaa", request);
        Assert.assertEquals(false, result);
    }
    
    @Test(description = "当验证码相等时，返回true")
    public void checkCaptcha_whenRedisCaptchaEqual_thenReturnFalse(){
        Cookie[] cookies = {new Cookie("sss", "ssss")};
        when(captchaOperatorBll.get(anyString())).thenReturn("aaaa");
        when(request.getCookies()).thenReturn(cookies);
        
        boolean result = captchaServiceImpl.checkCaptcha("aaa", "aaaa", request);
        Assert.assertEquals(true, result);
    }
    
    @Test(description = "当bid等于null，验证码相等时，返回true")
    public void checkCaptcha_whenBidNullRedisCaptchaEqual_thenReturnFalse(){
        Cookie[] cookies = {new Cookie("sss", "ssss")};
        when(captchaOperatorBll.get(anyString())).thenReturn("aaaa");
        when(request.getCookies()).thenReturn(cookies);
        
        boolean result = captchaServiceImpl.checkCaptcha(null, "aaaa", request);
        Assert.assertEquals(true, result);
    }
}
