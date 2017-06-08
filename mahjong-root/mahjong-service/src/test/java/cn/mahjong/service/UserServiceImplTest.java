package cn.mahjong.service;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.impl.UserServiceImpl;

/**
  * <p>
  *   UserServiceImplTest描述
  * </p>
  *  
  * @author chenwen
  * @since 0.0.1
  */
public class UserServiceImplTest {
    private UserBll userBll;
    private UserServiceImpl userServiceImpl;
    private SMSService smsService;
    
    @BeforeClass
    public void setUp(){
        userBll = mock(UserBll.class);
        smsService = mock(SMSService.class);
        
        userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserBll(userBll);
        userServiceImpl.setSMSService(smsService);
        userServiceImpl.setFindPwdSmsContent("");
    }
    
    @Test(description = "当用户不存在时，返回false")
    public void changePwd_whenUserIdNotExit_thenRerurnFalse(){
        when(userBll.get(anyInt())).thenReturn(null);
        
        ResultBean<Object> resultBean = userServiceImpl.changePwd(1, "aaa", 1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("用户不存在", resultBean.getMessage());
    }
    
    @Test(description = "当修改密码失败时，返回false")
    public void changePwd_whenUpdatePwdFail_thenRerurnFalse(){
        when(userBll.get(anyInt())).thenReturn(new UserBean());
        when(userBll.updatePwd(anyInt(), anyString())).thenReturn(false);        
        ResultBean<Object> resultBean = userServiceImpl.changePwd(1, "aaa", 1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("修改密码失败", resultBean.getMessage());
    }
    
    @Test(description = "当修改密码成功时，返回true")
    public void changePwd_whenUpdatePwdSuccess_thenRerurnFalse(){
        when(userBll.get(anyInt())).thenReturn(new UserBean());
        when(userBll.updatePwd(anyInt(), anyString())).thenReturn(true);  
        when(userBll.add(anyObject())).thenReturn(true);
        
        ResultBean<Object> resultBean = userServiceImpl.changePwd(1, "aaa", 1);
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("修改密码成功", resultBean.getMessage());
    }
    
    @Test(description = "当用户不存在时，返回false")
    public void findPwd_whenUserIdNotExit_thenRerurnFalse(){
        when(userBll.get(anyString())).thenReturn(null);
        
        ResultBean<Object> resultBean = userServiceImpl.findPwd("aaa", 1, "aaaa","",true);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("用户不存在", resultBean.getMessage());
    }
    
    private UserBean initUser(){
        UserBean userBean = new UserBean();
        userBean.setCode(1);
        userBean.setRole(UserRole.HEAD.getValue());
        userBean.setPhone("111111111");
        
        return userBean;
    }
    
    @Test(description = "当局头登录时，用户非局头用户，返回false")
    public void findPwd_whenUserIsNotHead_thenReturnFalse(){
        UserBean userBean = initUser();
        userBean.setRole("hq");
        
        when(userBll.get(anyString())).thenReturn(userBean);
        ResultBean<Object> resultBean = userServiceImpl.findPwd("aaa", 1, "aaaa","",true);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("您输入的账号暂无权限", resultBean.getMessage());
    }
    
    @Test(description = "当管理员登录时，用户是局头用户，返回false")
    public void findPwd_whenUserIsHead_thenReturnFalse(){
        UserBean userBean = initUser();
        
        when(userBll.get(anyString())).thenReturn(userBean);
        ResultBean<Object> resultBean = userServiceImpl.findPwd("aaa", 1, "aaaa","",false);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("您输入的账号暂无权限", resultBean.getMessage());
    }
    
    @Test(description = "当管理员登录时，用户是管理员用户，用户未绑定手机，返回false")
    public void findPwd_whenUserIsHq_thenReturnFalse(){
        UserBean userBean = initUser();
        userBean.setRole("hq");
        userBean.setPhone("");
        when(userBll.get(anyString())).thenReturn(userBean);
        ResultBean<Object> resultBean = userServiceImpl.findPwd("aaa", 1, "aaaa","",false);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("暂未绑定手机", resultBean.getMessage());
    }
    
    @Test(description = "当用户未绑定手机时，返回false")
    public void findPwd_whenUserIdNotBindPhone_thenRerurnFalse(){
        UserBean userBean = initUser();
        userBean.setPhone("");
        when(userBll.get(anyString())).thenReturn(userBean);
        
        ResultBean<Object> resultBean = userServiceImpl.findPwd("aaa", 1, "aaaa","",true);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("暂未绑定手机", resultBean.getMessage());
    }
    
    @Test(description = "当短信验证码错误时时，返回false")
    public void findPwd_whenSmsCodeError_thenRerurnFalse(){
        when(userBll.get(anyString())).thenReturn(initUser());
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(false,"验证错误",null));
        ResultBean<Object> resultBean = userServiceImpl.findPwd("aaa", 1, "aaaa","",true);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("短信验证码错误或者已失效", resultBean.getMessage());
    }
    
    @Test(description = "当找回密码失败时，返回false")
    public void findPwd_whenUpdatePwdFail_thenRerurnFalse(){
        when(userBll.get(anyString())).thenReturn(initUser());
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(true,"",null));
        when(userBll.updatePwd(anyInt(), anyString())).thenReturn(false); 
        
        ResultBean<Object> resultBean = userServiceImpl.findPwd("aaa", 1, "aaaa","",true);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("找回密码失败", resultBean.getMessage());
    }
    
    @Test(description = "当找回密码成功时，返回true")
    public void findPwd_whenUpdatePwdSuccess_thenRerurnFalse(){
        when(userBll.get(anyString())).thenReturn(initUser());
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(true,"",null));
        when(userBll.updatePwd(anyInt(), anyString())).thenReturn(true);  
        when(userBll.add(anyObject())).thenReturn(true);
        
        ResultBean<Object> resultBean = userServiceImpl.findPwd("aaa", 1, "aaaa","",true);
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("找回密码成功", resultBean.getMessage());
    }
    
    @Test(description = "当用户不存在时，返回false")
    public void sendFindPwdSms_whenUserIdNotExit_thenRerurnFalse(){
        when(userBll.get(anyString())).thenReturn(null);
        
        ResultBean<Integer> resultBean = userServiceImpl.sendFindPwdSms("");
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("发送失败", resultBean.getMessage());
    }
    
    @Test(description = "当用户未绑定手机时，返回false")
    public void sendFindPwdSms_whenUserIdNotBindPhone_thenRerurnFalse(){
        when(userBll.get(anyString())).thenReturn(new UserBean());
        
        ResultBean<Integer> resultBean = userServiceImpl.sendFindPwdSms("");
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("发送失败", resultBean.getMessage());
    }
    
    @Test(description = "当发送成功时，返回true")
    public void sendFindPwdSms_whenSendSuccess_thenRerurnFalse(){
        UserBean userBean = new UserBean();
        userBean.setPhone("111111111");
        when(userBll.get(anyString())).thenReturn(userBean);
        when(smsService.send(anyString(), anyString())).thenReturn(new ResultBean<>(true,"发送成功",null));
        
        ResultBean<Integer> resultBean = userServiceImpl.sendFindPwdSms("");
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("发送成功", resultBean.getMessage());
    }
    
    @Test(description = "当用户不存在时，返回false")
    public void sendChangePhoneSms_whenUserIdNotExit_thenRerurnFalse(){
        when(userBll.get(anyInt())).thenReturn(null);
        
        ResultBean<Integer> resultBean = userServiceImpl.sendChangePhoneSms(1,"");
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("发送失败", resultBean.getMessage());
    }
    
    @Test(description = "当用户未绑定手机时，返回false")
    public void sendChangePhoneSms_whenUserIdNotBindPhone_thenRerurnFalse(){
        when(userBll.get(anyInt())).thenReturn(new UserBean());
        
        ResultBean<Integer> resultBean = userServiceImpl.sendChangePhoneSms(1,"");
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("发送失败", resultBean.getMessage());
    }
    
    @Test(description = "当发送成功时，返回true")
    public void sendChangePhoneSms_whenSendSuccess_thenRerurnFalse(){
        UserBean userBean = initUser();
        userBean.setPhone("111111111");
        when(userBll.get(anyInt())).thenReturn(userBean);
        when(smsService.send(anyString(), anyString())).thenReturn(new ResultBean<>(true,"发送成功",null));
        
        ResultBean<Integer> resultBean = userServiceImpl.sendChangePhoneSms(1,"");
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("发送成功", resultBean.getMessage());
    }
    
    @Test(description = "当用户不存在时，返回false")
    public void sendSecondChangePhoneSms_whenUserIdNotExit_thenRerurnFalse(){
        when(userBll.get(anyInt())).thenReturn(null);
        
        ResultBean<Integer> resultBean = userServiceImpl.sendChangePhoneSms(1,"","");
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("发送失败", resultBean.getMessage());
    }
    
    @Test(description = "当用户存在时，返回True")
    public void sendSecondChangePhoneSms_whenUserIdExit_thenRerurnFalse(){
        when(userBll.get(anyInt())).thenReturn(initUser());
        when(smsService.send(anyString(), anyString())).thenReturn(new ResultBean<>(true,"发送成功",null));
        ResultBean<Integer> resultBean = userServiceImpl.sendChangePhoneSms(1,"","");
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("发送成功", resultBean.getMessage());
    }
    
    @Test(description = "当用户不存在时，返回false")
    public void changePhoneFirst_whenUserIdNotExit_thenRerurnFalse(){
        when(userBll.get(anyInt())).thenReturn(null);
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneFirst("",1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("用户不存在", resultBean.getMessage());
    }
    
    @Test(description = "当用户未绑定手机时，返回false")
    public void changePhoneFirst_whenUserIdNotBindPhone_thenRerurnFalse(){
        UserBean userBean = initUser();
        userBean.setPhone("");
        when(userBll.get(anyInt())).thenReturn(userBean);
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneFirst("",1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("暂未绑定手机", resultBean.getMessage());
    }
    
    @Test(description = "当短信验证码错误时，返回false")
    public void changePhoneFirst_whenSmsCodeError_thenRerurnFalse(){
        UserBean userBean = initUser();
        when(userBll.get(anyInt())).thenReturn(userBean);
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(false,"",null));
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneFirst("",1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("短信验证码错误或者已失效", resultBean.getMessage());
    }
    
    @Test(description = "当短信验证码正确时，返回false")
    public void changePhoneFirst_whenSmsCode_thenRerurnTrue(){
        UserBean userBean = initUser();
        when(userBll.get(anyInt())).thenReturn(userBean);
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(true,"",null));
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneFirst("",1);
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("更换手机第一步验证成功", resultBean.getMessage());
    }
    
    @Test(description = "当短信验证码错误时，返回false")
    public void changePhoneSecond_whenSmsCodeError_thenRerurnFalse(){
        
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(false,"",null));
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneSecond("","",1,1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("短信验证码错误或者已失效", resultBean.getMessage());
    }
    
    @Test(description = "当用户不存在时，返回false")
    public void changePhoneSecond_whenUserNotExit_thenRerurnFalse(){
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(true,"",null));
        when(userBll.get(anyInt())).thenReturn(null);
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneSecond("","",1,1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("用户不存在", resultBean.getMessage());
    }
    
    @Test(description = "当暂未绑定手机时，返回false")
    public void changePhoneSecond_whenUserNotPhone_thenRerurnFalse(){
        UserBean userBean = initUser();
        userBean.setPhone("");
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(true,"",null));
        when(userBll.get(anyInt())).thenReturn(userBean);
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneSecond("","",1,1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("暂未绑定手机", resultBean.getMessage());
    }
    
    @Test(description = "当输入的手机号与原绑定的手机号一致时，返回false")
    public void changePhoneSecond_whenUserPhoneNotEquals_thenRerurnFalse(){
        UserBean userBean = initUser();
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(true,"",null));
        when(userBll.get(anyInt())).thenReturn(userBean);
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneSecond("111111111","",1,1);
        Assert.assertEquals(false, resultBean.getIsSuccess());
        Assert.assertEquals("输入的手机号与原绑定的手机号一致", resultBean.getMessage());
    }
    
    @Test(description = "当修改手机号成功时，返回True")
    public void changePhoneSecond_whenChangePhoneSuccess_thenRerurnTrue(){
        UserBean userBean = initUser();
        when(smsService.validate(anyString(), anyString())).thenReturn(new ResultBean<>(true,"",null));
        when(userBll.get(anyInt())).thenReturn(userBean);
        doNothing().when(userBll).updatePhone(anyObject(), anyObject());
        
        ResultBean<Object> resultBean = userServiceImpl.changePhoneSecond("111","",1,1);
        Assert.assertEquals(true, resultBean.getIsSuccess());
        Assert.assertEquals("更换手机成功", resultBean.getMessage());
    }
}
