package cn.mahjong.service.impl.sms;

import java.net.URI;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.service.sms.SPProvider;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GuoDuProvider implements SPProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuoDuProvider.class);

  @Value("${sms.url.guodu}")
  private String baseUrl;
  @Value("${sms.account.guodu}")
  private String account;
  @Value("${sms.password.guodu}")
  private String password;
  @Autowired
  private RestTemplate restTemplate;

  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public String getName() {
    return "GuoDu";
  }

  @SuppressWarnings("SpellCheckingInspection")
  @Override
  public ResultBean<String> send(String phoneNumber, String content) {
    try {
      String url = String.format("%s?OperID=%s&OperPass=%s&DesMobile=%s&Content=%s&ContentType=%d",
          baseUrl, account, password, phoneNumber, content, 8);
      URI uri = UriComponentsBuilder.fromUriString(url).build().encode("GBK").toUri();
      int statusCode = getStatusCode(restTemplate.getForEntity(uri, String.class).getBody());
      if (statusCode == 0 || statusCode == 1 || statusCode == 3) {
        return new ResultBean<>(true, "发送成功", String.valueOf(statusCode));
      } else {
        LOGGER.error("SMS Send failed, status Code: " + statusCode);
        return new ResultBean<>(false, "发送失败", String.valueOf(statusCode));
      }
    } catch (Exception e) {
      LOGGER.error("SMS Send failed.", e);
      return new ResultBean<>(false, "发送失败", "-1");
    }
  }

  private int getStatusCode(String responseBody) {
    try {
      LOGGER.debug("Response body: " + responseBody);
      Document document = DocumentHelper.parseText(responseBody);
      Element root = document.getRootElement();
      Object data = root.element("code").getData();
      return Integer.parseInt(data.toString());
    } catch (Exception e) {
      LOGGER.error("Read status code from xml failed.", e);
      return -1;
    }
  }
}
