package cn.mahjong.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.service.impl.sms.GuoDuProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GuoDuProviderTest {

  private GuoDuProvider provider;
  private RestTemplate restTemplate;

  @BeforeClass
  public void setUp() {
    provider = new GuoDuProvider();
    restTemplate = mock(RestTemplate.class);
    provider.setRestTemplate(restTemplate);
  }

  @Test(description = "当程序抛出异常时返回False")
  public void send_whenExceptionThrows_thenReturnFalse() {
    ResultBean<String> resultBean = provider.send("18813057196", "asd");
    Assert.assertFalse(resultBean.getIsSuccess());
    Assert.assertEquals("发送失败", resultBean.getMessage());
    Assert.assertEquals("-1", resultBean.getData());
  }

  @Test(description = "当状态码不等于-1时返回False")
  public void send_whenStatusCodeNotEqualsMinus1_thenReturnFalse() {
    when(restTemplate.getForEntity(any(java.net.URI.class), eq(String.class)))
        .thenReturn(new ResponseEntity<>("", HttpStatus.OK));
    ResultBean<String> resultBean = provider.send("18813057196", "asd");
    Assert.assertFalse(resultBean.getIsSuccess());
    Assert.assertEquals("发送失败", resultBean.getMessage());
    Assert.assertEquals("-1", resultBean.getData());
  }

  @Test(description = "当状态码等于0时返回True")
  public void send_whenStatusCodeEquals0_thenReturnTrue() {
    when(restTemplate.getForEntity(any(java.net.URI.class), eq(String.class)))
        .thenReturn(new ResponseEntity<>("<xml><code>0</code></xml>", HttpStatus.OK));
    ResultBean<String> resultBean = provider.send("18813057196", "asd");
    Assert.assertTrue(resultBean.getIsSuccess());
    Assert.assertEquals("发送成功", resultBean.getMessage());
    Assert.assertEquals("0", resultBean.getData());
  }

  @Test(description = "当任意情况时返回GuoDu")
  public void getName_whenAnyTimes_thenReturnGuoDu() {
    String name = provider.getName();
    Assert.assertEquals("GuoDu", name);
  }
}
