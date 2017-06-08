package cn.mahjong.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.mahjong.beans.SMS;
import cn.mahjong.beans.SMSLog;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.redis.bll.SMSBll;
import cn.mahjong.service.impl.SMSServiceImpl;
import cn.mahjong.service.impl.sms.GuoDuProvider;
import cn.mahjong.service.sms.SPProvider;
import cn.mahjong.service.sms.SPProviderFactory;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SMSServiceImplTest {

  private SMSServiceImpl service;
  private SMSBll smsBll;
  private SPProviderFactory spProviderFactory;

  @BeforeClass
  public void setUp() {
    service = new SMSServiceImpl();
    smsBll = mock(SMSBll.class);
    spProviderFactory = mock(SPProviderFactory.class);
    service.setSmsBll(smsBll);
    service.setSpProviderFactory(spProviderFactory);
  }

  @Test(description = "当短信发送次数超出今日限制时返回False")
  public void send_whenIsOverLimit_thenReturnFalse() {
    when(smsBll.isOverLimit(anyString())).thenReturn(true);
    ResultBean<Integer> resultBean = service.send("18813057196", "Hi");
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当短信包含验证码且未到达下次发送时间返回False")
  public void send_whenIsVerifyCodeSMSAndNotArriveNextSendTime_thenReturnFalse() {
    when(smsBll.isOverLimit(anyString())).thenReturn(false);
    when(smsBll.secondsToNextSendTime(anyString())).thenReturn(1);
    ResultBean<Integer> resultBean = service.send("18813057196", "{verifyCode}");
    Assert.assertFalse(resultBean.getIsSuccess());
  }

  @Test(description = "当短信包含验证码且已到达下次发送时间返回True")
  public void send_whenIsVerifyCodeSMSAndArriveNextSendTime_thenReturnTrue() {
    when(smsBll.isOverLimit(anyString())).thenReturn(false);
    when(smsBll.secondsToNextSendTime(anyString())).thenReturn(0);
    doNothing().when(smsBll).saveSendTime(anyString());
    when(smsBll.getVerifyCode(anyString())).thenReturn(null);
    doNothing().when(smsBll).saveVerifyCode(anyString(), anyString());
    doNothing().when(smsBll).add(any(SMS.class));
    doNothing().when(smsBll).add(any(SMSLog.class));
    ArrayList<SPProvider> providers = new ArrayList<>();
    GuoDuProvider mock = mock(GuoDuProvider.class);
    when(mock.send(anyString(), anyString())).thenReturn(new ResultBean<>(true, "发送成功", null));
    providers.add(mock);
    when(spProviderFactory.createAll()).thenReturn(providers);
    ResultBean<Integer> resultBean = service.send("18813057196", "{verifyCode}");
    Assert.assertTrue(resultBean.getIsSuccess());
  }

  @Test(description = "当短信包含验证码且已到达下次发送时间返回True2")
  public void send_whenIsVerifyCodeSMSAndArriveNextSendTime_thenReturnTrue2() {
    when(smsBll.isOverLimit(anyString())).thenReturn(false);
    when(smsBll.secondsToNextSendTime(anyString())).thenReturn(0);
    when(smsBll.getVerifyCode(anyString())).thenReturn(null);
    doNothing().when(smsBll).saveSendTime(anyString());
    doNothing().when(smsBll).saveVerifyCode(anyString(), anyString());
    doNothing().when(smsBll).add(any(SMS.class));
    doNothing().when(smsBll).add(any(SMSLog.class));
    ArrayList<SPProvider> providers = new ArrayList<>();
    GuoDuProvider mock = mock(GuoDuProvider.class);
    when(mock.send(anyString(), anyString())).thenReturn(new ResultBean<>(false, "发送成功", null));
    providers.add(mock);
    when(spProviderFactory.createAll()).thenReturn(providers);
    ResultBean<Integer> resultBean = service.send("18813057196", "{verifyCode}");
    Assert.assertTrue(resultBean.getIsSuccess());
  }

  @Test(description = "当验证成功时返回True")
  public void validate_whenValidateSuccess_thenReturnTrue() {
    when(smsBll.validate(anyString(), anyString())).thenReturn(true);
    ResultBean<Object> resultBean = service.validate("18813057196", "111111");
    Assert.assertTrue(resultBean.getIsSuccess());
  }

  @Test(description = "当验证失败时返回False")
  public void validate_whenValidateFailed_thenReturnFalse() {
    when(smsBll.validate(anyString(), anyString())).thenReturn(false);
    ResultBean<Object> resultBean = service.validate("18813057196", "111111");
    Assert.assertFalse(resultBean.getIsSuccess());
  }
}
