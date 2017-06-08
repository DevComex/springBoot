package cn.mahjong.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.mahjong.beans.AccountLoginLog;
import cn.mahjong.beans.AccountStatus;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.redis.bll.LoginErrorOperatorBll;
import cn.mahjong.service.impl.LoginServiceImpl;

/**
  * <p>
  *   LoginServiceImplTest描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public class LoginServiceImplTest {
    private UserBll userBll;
    private LoginErrorOperatorBll loginErrorOperatorBll;
    private Integer loginErrorTimes;
    private LoginServiceImpl loginServiceImpl;
    
    @BeforeClass
    public void setUp(){
        userBll = mock(UserBll.class);
        loginErrorOperatorBll = mock(LoginErrorOperatorBll.class);
        loginErrorTimes = 3;
        
        loginServiceImpl = new LoginServiceImpl();
        loginServiceImpl.setLoginErrorOperatorBll(loginErrorOperatorBll);
        loginServiceImpl.setLoginErrorTimes(loginErrorTimes);
        loginServiceImpl.setUserBll(userBll);
    }
    private UserBean initUserBean(){
        UserBean userBean = new UserBean();
        userBean.setAccount("aaaaaTest");
        userBean.setCode(1);
        userBean.setStatus(AccountStatus.NORMAL.getValue());
        userBean.setRole(UserRole.HEAD.getValue());
        userBean.setSalt("ccccc");
        userBean.setPassword("339f199446aa6ca3a39d2ab9352024cb");
        return userBean;
    }
    
    
    @Test(description = "当用户不存在时，返回false")
    public void login_whenAccountNotExit_thenReturnFalse(){
        when(userBll.get(anyString())).thenReturn(null);
        ResultBean<Object> resultBean = loginServiceImpl.login("aaa", "aaa", "192", null, null, false);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("用户不存在", resultBean.getMessage());
    }
    
    @Test(description = "当用户已封停时，返回false")
    public void login_whenAccountIsBanned_thenReturnFalse(){
        UserBean userBean = initUserBean();
        userBean.setStatus(AccountStatus.BANNED.getValue());
        
        when(userBll.get(anyString())).thenReturn(userBean);
        ResultBean<Object> resultBean = loginServiceImpl.login("aaa", "aaa", "192", null, null, false);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("用户已封停", resultBean.getMessage());
    }
    
    @Test(description = "当局头登录时，用户是非局头账号，返回false")
    public void login_whenAccountIsNotHead_thenReturnFalse(){
        UserBean userBean = initUserBean();
        userBean.setRole(UserRole.HQ.getValue());
        
        when(userBll.get(anyString())).thenReturn(userBean);
        ResultBean<Object> resultBean = loginServiceImpl.login("aaa", "aaa", "192", null, null, true);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("您输入的账号暂无权限", resultBean.getMessage());
    }
    
    @Test(description = "当管理员界面登录时，用户是局头账号，返回false")
    public void login_whenAccountIsHead_thenReturnFalse(){
        UserBean userBean = initUserBean();
        
        when(userBll.get(anyString())).thenReturn(userBean);
        ResultBean<Object> resultBean = loginServiceImpl.login("aaa", "aaa", "192", null, null, false);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("您输入的账号暂无权限", resultBean.getMessage());
    }
    
    @Test(description = "当密码错误时，返回false")
    public void login_whenAccountPwdError_thenReturnFalse(){
        UserBean userBean = initUserBean();
        userBean.setPassword("dfgfgfgf");
        when(userBll.get(anyString())).thenReturn(userBean);
        ResultBean<Object> resultBean = loginServiceImpl.login("aaa", "aaa", "192", null, null, true);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("用户名或密码错误", resultBean.getMessage());
    }
    
    @Test(description = "当记录登录日志失败时，返回false")
    public void login_whenInsertLogFail_thenReturnFalse(){
        UserBean userBean = initUserBean();

        when(userBll.get(anyString())).thenReturn(userBean);
        when(userBll.addLoginLog(anyObject())).thenReturn(false);
        
        ResultBean<Object> resultBean = loginServiceImpl.login("aaa", "aaa", "192", new AccountLoginLog(), null, true);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("记录登录日志失败", resultBean.getMessage());
    }
    
    @Test(description = "当登录成功时，返回true")
    public void login_whenLoginSuccess_thenReturnTrue(){
        UserBean userBean = initUserBean();

        when(userBll.get(anyString())).thenReturn(userBean);
        when(userBll.addLoginLog(anyObject())).thenReturn(true);
        doNothing().when(userBll).addLoginCookie(anyObject(), anyObject());
        doNothing().when(loginErrorOperatorBll).cleanUpErrorTimes(anyString());
        
        ResultBean<Object> resultBean = loginServiceImpl.login("aaa", "aaa", "192", new AccountLoginLog(), null, true);
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("登录成功", resultBean.getMessage());
    }
    
    @Test(description = "当Redis没有登录错误次数时，返回false")
    public void isNeedCaptcha_whenLoginNotError_thenReturn(){
        when(loginErrorOperatorBll.get(anyString())).thenReturn(null);
        boolean result = loginServiceImpl.isNeedCaptcha("sss");
        Assert.assertEquals(false, result);
    }
    
    @Test(description = "当Redis没有登录错误次数时，返回false")
    public void isNeedCaptcha_whenLoginNotErrorTime_thenReturn(){
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", 1);
        when(loginErrorOperatorBll.get(anyString())).thenReturn(map);
        boolean result = loginServiceImpl.isNeedCaptcha("sss");
        Assert.assertEquals(false, result);
    }
    
    @Test(description = "当Redis登录错误次数小于3，返回false")
    public void isNeedCaptcha_whenLoginErrorTimeLess3_thenReturn(){
        Map<String, Object> map = new HashMap<>();
        map.put("errorTimes", 1);
        when(loginErrorOperatorBll.get(anyString())).thenReturn(map);
        boolean result = loginServiceImpl.isNeedCaptcha("sss");
        Assert.assertEquals(false, result);
    }
    
    @Test(description = "当Redis登录错误次数等于3，返回false")
    public void isNeedCaptcha_whenLoginErrorTimeEqual3_thenReturn(){
        Map<String, Object> map = new HashMap<>();
        map.put("errorTimes", 3);
        when(loginErrorOperatorBll.get(anyString())).thenReturn(map);
        boolean result = loginServiceImpl.isNeedCaptcha("sss");
        Assert.assertEquals(false, result);
    }
    
    @Test(description = "当Redis登录错误次数大于3，返回true")
    public void isNeedCaptcha_whenLoginErrorTimeThan3_thenReturn(){
        Map<String, Object> map = new HashMap<>();
        map.put("errorTimes", 4);
        when(loginErrorOperatorBll.get(anyString())).thenReturn(map);
        boolean result = loginServiceImpl.isNeedCaptcha("sss");
        Assert.assertEquals(true, result);
    }
}
