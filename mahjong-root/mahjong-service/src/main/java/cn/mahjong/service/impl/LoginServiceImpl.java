package cn.mahjong.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mahjong.beans.AccountLoginLog;
import cn.mahjong.beans.AccountStatus;
import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.PasswordUtils;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.redis.bll.LoginErrorOperatorBll;
import cn.mahjong.service.LoginService;

/**
  * <p>
  *   LoginServiceImpl描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
    
    @Autowired
    private UserBll userBll;
    
    @Autowired
    private LoginErrorOperatorBll loginErrorOperatorBll;
    
    @Value("${account.login.error.times.captcha}")
    private Integer loginErrorTimes;

    public synchronized void setUserBll(UserBll userBll) {
        this.userBll = userBll;
    }

    public synchronized void setLoginErrorOperatorBll(
            LoginErrorOperatorBll loginErrorOperatorBll) {
        this.loginErrorOperatorBll = loginErrorOperatorBll;
    }

    public synchronized void setLoginErrorTimes(Integer loginErrorTimes) {
        this.loginErrorTimes = loginErrorTimes;
    }

    /* (non-Javadoc)
     * @see cn.mahjong.service.LoginService#login(cn.mahjong.beans.UserBean, cn.mahjong.beans.AccountLoginLog)
     */
    @Override
    public ResultBean<Object> login(String account,String pwd,String loginIp,AccountLoginLog accountLoginLog
            ,HttpServletResponse response,Boolean isHead) {
        UserBean userBean = userBll.get(account);
        if(userBean == null){
            LOGGER.info("account:{} not exit",account);
            return new ResultBean<>(false,"用户不存在",null);
        }
        
        if(AccountStatus.BANNED.getValue().equals(userBean.getStatus())){
            LOGGER.info("account:{} is banned",account);
            return new ResultBean<>(false,"用户已封停",null);
        }
        
        if(isHead){
            if(!UserRole.HEAD.getValue().equals(userBean.getRole())){
                LOGGER.info("head login,account:{} is not head",account);
                return new ResultBean<>(false,"您输入的账号暂无权限",null);
            }
        }else {
            if(UserRole.HEAD.getValue().equals(userBean.getRole())){
                LOGGER.info("admin login,account:{} is  head",account);
                return new ResultBean<>(false,"您输入的账号暂无权限",null);
            }
        }
        
        //验证密码
        String salt = userBean.getSalt();   
        String encodePassword = PasswordUtils.encode(pwd, salt);
        if(!encodePassword.equals(userBean.getPassword())){
            loginErrorOperatorBll.incrementErrorTimes(account);
            LOGGER.info("account:{} , password:{}, pwd is error",account,pwd);
            return new ResultBean<>(false,"用户名或密码错误",null);
        }
        
        accountLoginLog.setUserId(userBean.getCode());
        accountLoginLog.setLoginTime(new Date());
        if(!userBll.addLoginLog(accountLoginLog)){
            LOGGER.info("account:{} insert log fail",account,pwd);
            return new ResultBean<>(false,"记录登录日志失败",null);
        }
        
        //写cookie
        CookieUserInfo cookieUserInfo = new CookieUserInfo(userBean.getCode(), account,
            userBean.getRole(), loginIp, userBean.getGameId());
        userBll.addLoginCookie(response, cookieUserInfo);
        //清空错误次数
        loginErrorOperatorBll.cleanUpErrorTimes(account);
        LOGGER.info("login success");
        return new ResultBean<>(true,"登录成功",null);
    }
    
    /* (non-Javadoc)
     * @see cn.mahjong.service.CaptchaService#getCaptcha(java.lang.String)
     */
    @Override
    public Boolean isNeedCaptcha(String account) {
        Map<String, Object>  map= loginErrorOperatorBll.get(account);
        if(map != null && map.containsKey("errorTimes") && (int)map.get("errorTimes") > loginErrorTimes){
            LOGGER.debug("account:{},loginErrorTimes:{}",account,map.get("errorTimes"));
            return true;
        }
        return false;
    }

}
