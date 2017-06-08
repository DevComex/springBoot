package cn.mahjong.service.impl;

import cn.mahjong.beans.SMS;
import cn.mahjong.beans.SMSLog;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.redis.bll.SMSBll;
import cn.mahjong.service.SMSService;
import cn.mahjong.service.sms.SPProvider;
import cn.mahjong.service.sms.SPProviderFactory;
import java.util.Date;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {

  private static final String SIGNATURE = "【飞猪游戏】";
  private static final String PLACEHOLDER_VERIFY_CODE = "{verifyCode}";
  @Autowired
  private SMSBll smsBll;
  @Autowired
  private SPProviderFactory spProviderFactory;

  public void setSmsBll(SMSBll smsBll) {
    this.smsBll = smsBll;
  }

  public void setSpProviderFactory(SPProviderFactory spProviderFactory) {
    this.spProviderFactory = spProviderFactory;
  }

  @Override
  public ResultBean<Integer> send(String phoneNumber, String unsignedContent) {
    if (smsBll.isOverLimit(phoneNumber)) {
      return new ResultBean<>(false, "短信发送次数超出今日限制", null);
    }
    String content = unsignedContent;
    if (!content.contains(SIGNATURE)) {
      content += SIGNATURE;
    }

    boolean isVerifyCodeSMS = content.contains(PLACEHOLDER_VERIFY_CODE);
    if (isVerifyCodeSMS) {
      int secondsToNextSendTime = smsBll.secondsToNextSendTime(phoneNumber);
      if (secondsToNextSendTime > 0) {
        return new ResultBean<>(false, "未达到下次发送时间", secondsToNextSendTime);
      }

      String cacheVerifyCode = smsBll.getVerifyCode(phoneNumber);
      if (cacheVerifyCode != null) {
        content = content.replace(PLACEHOLDER_VERIFY_CODE, cacheVerifyCode);
      } else {
        String verifyCode = RandomStringUtils.random(6, false, true);
        content = content.replace(PLACEHOLDER_VERIFY_CODE, verifyCode);
        smsBll.saveVerifyCode(phoneNumber, verifyCode);
      }
      smsBll.saveSendTime(phoneNumber);
    }

    SMS sms = new SMS(phoneNumber, content, "success", new Date());
    smsBll.add(sms);

    for (SPProvider provider : spProviderFactory.createAll()) {
      ResultBean<String> result = provider.send(phoneNumber, content);
      SMSLog log = new SMSLog(sms.getCode(), provider.getName(),
          String.valueOf(result.getIsSuccess()), result.getData(), new Date());
      smsBll.add(log);
      if (result.getIsSuccess()) {
        break;
      }
    }
    return new ResultBean<>(true, "发送成功", 0);
  }

  @Override
  public ResultBean<Object> validate(String phoneNumber, String verifyCode) {
    if (smsBll.validate(phoneNumber, verifyCode)) {
      return new ResultBean<>(true, "验证成功", null);
    }
    return new ResultBean<>(false, "验证失败", null);
  }
}
