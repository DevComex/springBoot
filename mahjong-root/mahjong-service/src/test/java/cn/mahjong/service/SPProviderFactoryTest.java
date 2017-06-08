package cn.mahjong.service;

import cn.mahjong.service.sms.SPProviderFactory;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SPProviderFactoryTest {
  private SPProviderFactory spProviderFactory;

  @BeforeClass
  public void setUp() {
    spProviderFactory = new SPProviderFactory();
  }

  @Test(description = "当任意场景时返回所有短信渠道")
  public void createAll_whenAnyTime_thenReturnAllProviders() {
    spProviderFactory.setProviders(new ArrayList<>());
    Assert.assertEquals(0, spProviderFactory.createAll().size());
  }
}
