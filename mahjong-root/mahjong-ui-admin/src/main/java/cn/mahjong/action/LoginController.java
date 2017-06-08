package cn.mahjong.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mahjong.action.form.LoginForm;
import cn.mahjong.beans.AccountLoginLog;
import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.common.Common;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.beans.common.WebUtils;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.CaptchaService;
import cn.mahjong.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController extends AbstractBaseController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserBll userBll;
    
    @Autowired
    private CaptchaService captchaService;

    @RequestMapping(value = {"", "/"})
    public String index() {
        return "/login/index";
    }
    
    @RequestMapping(value = "/isNeedCaptcha", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getLoginCapachaType(@RequestParam(required = false) String account,HttpServletResponse response,HttpServletRequest request){
        //从redis里获取是否需要验证码
        if(StringUtils.isEmpty(account)){
            return new ResponseEntity<>(new ResultBean<>(false,"参数错误",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResultBean<>(true,"",loginService.isNeedCaptcha(account)),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> login(@Valid LoginForm loginForm,BindingResult bindResult, 
            HttpServletRequest request,HttpServletResponse response) {
        LOGGER.info("login start,loginForm:{}",loginForm);
        try {
            String errorMsg = validateData(bindResult);
            if(!StringUtils.isEmpty(errorMsg)){
                LOGGER.info("params error");
                return new ResponseEntity<>(new ResultBean<>(false,errorMsg,null) , HttpStatus.OK);
            }

            //如果登录错误三次 验证验证码 
            if(loginService.isNeedCaptcha(loginForm.getAccount())){
                //验证验证码
                String captchaCode = loginForm.getCaptchaCode();
                if(StringUtils.isEmpty(captchaCode)){
                    LOGGER.info("captcha empty");
                    return new ResponseEntity<>(new ResultBean<>(false,"验证码不能为空",null) , HttpStatus.OK);
                }
                if(!captchaService.checkCaptcha("adminlg", captchaCode, request)){
                    LOGGER.info("captcha error");
                    return new ResponseEntity<>(new ResultBean<>(false,"验证码错误或已过期",null) , HttpStatus.OK);
                }
            }
            
            AccountLoginLog accountLoginLog = new AccountLoginLog();
            accountLoginLog.setLoginIp(WebUtils.getIntIp(request));

            return new ResponseEntity<>(loginService.login(loginForm.getAccount(),loginForm.getPassword(),
                WebUtils.getIp(request),accountLoginLog,response,false),HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("login exception :{}",e);
            return new ResponseEntity<>(new ResultBean<>(false,"登录异常",null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    private ResponseEntity<Object> getLoginCookie(HttpServletRequest request,HttpServletResponse response) {
        CookieUserInfo cookieUserInfo = userBll.checkLogin(request);
        if (cookieUserInfo == null) {
            return new ResponseEntity<>(new ResultBean<>(false, "", null),HttpStatus.OK);
        }
        cookieUserInfo.setAccount(Common.getAcccountMask(cookieUserInfo.getAccount()));
        return new ResponseEntity<>(new ResultBean<>(true, "", cookieUserInfo),
                HttpStatus.OK);
    }
}
