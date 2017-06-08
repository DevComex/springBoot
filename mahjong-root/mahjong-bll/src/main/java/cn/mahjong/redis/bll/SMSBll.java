package cn.mahjong.redis.bll;

import cn.mahjong.beans.SMS;
import cn.mahjong.beans.SMSLog;

public interface SMSBll {

  boolean validate(String phoneNumber, String verifyCode);

  boolean isOverLimit(String phoneNumber);

  int secondsToNextSendTime(String phoneNumber);

  void saveSendTime(String phoneNumber);

  void saveVerifyCode(String phoneNumber, String verifyCode);

  String getVerifyCode(String phoneNumber);

  void add(SMS sms);

  void update(int code, String status);

  void add(SMSLog log);
}
