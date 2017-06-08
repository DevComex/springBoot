package cn.mahjong.service.sms;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SPProviderFactory {

  @Autowired
  private List<SPProvider> providers;

  public void setProviders(List<SPProvider> providers) {
    this.providers = providers;
  }

  @Autowired
  public List<SPProvider> createAll() {
    return providers;
  }
}
