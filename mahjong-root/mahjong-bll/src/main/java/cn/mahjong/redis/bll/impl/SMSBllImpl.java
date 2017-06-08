package cn.mahjong.redis.bll.impl;

import cn.mahjong.beans.SMS;
import cn.mahjong.beans.SMSLog;
import cn.mahjong.dao.SMSLogMapper;
import cn.mahjong.dao.SMSMapper;
import java.io.Serializable;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import cn.mahjong.redis.bll.SMSBll;

@Component
public class SMSBllImpl implements SMSBll {

  private static final int MAX_SEND_COUNT = 50;
  private static final int VERIFY_CODE_SEND_INTERVAL = 60;
  private static final String PREFIX_SEND_COUNT = "SMSSendCount:";
  private static final String PREFIX_VERIFY_CODE = "SMSCaptcha:";
  private static final String PREFIX_LAST_SEND_TIME = "SMSLastSendTime:";
  @Autowired
  private RedisOperations<Serializable, Object> redisOperations;
  @Autowired
  private SMSMapper smsMapper;
  @Autowired
  private SMSLogMapper smsLogMapper;

  @Override
  public boolean validate(String phoneNumber, String verifyCode) {
    String cacheVerifyCode = getVerifyCode(phoneNumber);
    boolean areEqual = cacheVerifyCode != null && cacheVerifyCode.equals(verifyCode);
    if (!areEqual) {
      return false;
    }
    String key = PREFIX_VERIFY_CODE + phoneNumber;
    redisOperations.delete(key);
    return true;
  }

  @Override
  public boolean isOverLimit(String phoneNumber) {
    ValueOperations<Serializable, Object> valueOperations = redisOperations.opsForValue();
    Object value = valueOperations.get(PREFIX_SEND_COUNT + phoneNumber);
    int sendCount = value == null ? 0 : (int) value;
    return sendCount >= MAX_SEND_COUNT;
  }

  @Override
  public int secondsToNextSendTime(String phoneNumber) {
    ValueOperations<Serializable, Object> valueOperations = redisOperations.opsForValue();
    Object value = valueOperations.get(PREFIX_LAST_SEND_TIME + phoneNumber);
    if (value == null) {
      return 0;
    }

    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DateTime lastSendTime = DateTime.parse(value.toString(), format);
    DateTime nextSendTime = lastSendTime.plusSeconds(VERIFY_CODE_SEND_INTERVAL);
    return Seconds.secondsBetween(new DateTime(), nextSendTime).getSeconds();
  }

  @Override
  public void saveSendTime(String phoneNumber) {
    String key = PREFIX_LAST_SEND_TIME + phoneNumber;
    ValueOperations<Serializable, Object> valueOperations = redisOperations.opsForValue();
    DateTime now = new DateTime();
    valueOperations.set(key, now.toString("yyyy-MM-dd HH:mm:ss"));
    Date date = now.plusMinutes(1).toDate();
    redisOperations.expireAt(key, date);
  }

  @Override
  public void saveVerifyCode(String phoneNumber, String verifyCode) {
    String key = PREFIX_VERIFY_CODE + phoneNumber;
    ValueOperations<Serializable, Object> valueOperations = redisOperations.opsForValue();
    valueOperations.set(key, verifyCode);
    Date date = new DateTime().plusMinutes(5).toDate();
    redisOperations.expireAt(key, date);
  }

  @Override
  public String getVerifyCode(String phoneNumber) {
    ValueOperations<Serializable, Object> valueOperations = redisOperations.opsForValue();
    String key = PREFIX_VERIFY_CODE + phoneNumber;
    Object value = valueOperations.get(key);
    return value == null ? null : value.toString();
  }

  @Override
  public void add(SMS sms) {
    smsMapper.insert(sms);
  }

  @Override
  public void update(int code, String status) {
    smsMapper.update(code, status);
  }

  @Override
  public void add(SMSLog log) {
    smsLogMapper.insert(log);
  }
}
