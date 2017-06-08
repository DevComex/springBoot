package cn.mahjong.service.impl;

import cn.mahjong.beans.BlockLog;
import cn.mahjong.bll.BlockLogBll;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.mahjong.beans.AccountBindChangePhoneLog;
import cn.mahjong.beans.AccountFindChangePwdLog;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.PasswordUtils;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.SMSService;
import cn.mahjong.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


  @Autowired
  private UserBll userBll;
  @Autowired
  private BlockLogBll blockLogBll;
  private SMSService smsService;

  @Value("${send.find.password.sms.content}")
  private String findPwdSmsContent;

  @Autowired
  public void setUserBll(UserBll userBll) {
    this.userBll = userBll;
  }

  @Autowired
  public void setSMSService(SMSService smsService) {
    this.smsService = smsService;
  }

  public synchronized void setFindPwdSmsContent(String findPwdSmsContent) {
    this.findPwdSmsContent = findPwdSmsContent;
  }

  /* (non-Javadoc)
   * @see cn.mahjong.service.UserService#changePwd(java.lang.Integer, java.lang.String)
   */
  @Override
  @Transactional
  public ResultBean<Object> changePwd(Integer userId, String newPwd, Integer ip) {
    UserBean userBean = userBll.get(userId);
    if (userBean == null) {
      LOGGER.info("userId:{} not exit", userId);
      return new ResultBean<>(false, "用户不存在", null);
    }

    if (updatePwd(userBean, "change_pwd", newPwd, ip)) {
      return new ResultBean<>(true, "修改密码成功", null);
    }

    return new ResultBean<>(false, "修改密码失败", null);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.service.UserService#findPwd(java.lang.String, java.lang.Integer, java.lang.String)
   */
  @Override
  public ResultBean<Object> findPwd(String account, Integer ip, String pwd, String smsCode,
      boolean isHead) {
    UserBean userBean = userBll.get(account);
    if (userBean == null) {
      LOGGER.info("account:{} not exit", account);
      return new ResultBean<>(false, "用户不存在", null);
    }

    if (isHead) {
      if (!UserRole.HEAD.getValue().equals(userBean.getRole())) {
        LOGGER.info("head login,account:{} is not head", account);
        return new ResultBean<>(false, "您输入的账号暂无权限", null);
      }
    } else {
      if (UserRole.HEAD.getValue().equals(userBean.getRole())) {
        LOGGER.info("admin login,account:{} is  head", account);
        return new ResultBean<>(false, "您输入的账号暂无权限", null);
      }
    }

    if (StringUtils.isEmpty(userBean.getPhone())) {
      LOGGER.info("account:{} not exit", account);
      return new ResultBean<>(false, "暂未绑定手机", null);
    }

    //验证短信验证码
    ResultBean<Object> validate = smsService.validate(userBean.getPhone(), smsCode);
    if (!validate.getIsSuccess()) {
      LOGGER.info("smsCode:{} validate fail", smsCode);
      return new ResultBean<>(false, "短信验证码错误或者已失效", null);
    }

    if (updatePwd(userBean, "find_pwd", pwd, ip)) {
      return new ResultBean<>(true, "找回密码成功", null);
    }

    return new ResultBean<>(false, "找回密码失败", null);

  }

  /**
   * <p>
   * 修改密码
   * </p>
   *
   * @return Boolean
   * @action 2017年5月25日 上午10:43:53 描述
   */
  private Boolean updatePwd(UserBean userBean, String logType, String password, Integer ip) {
    Integer userId = userBean.getCode();
    String salt = userBean.getSalt();
    String encodePassword = PasswordUtils.encode(password, salt);
    userBean.setPassword(encodePassword);

    AccountFindChangePwdLog findChangePwdLog = new AccountFindChangePwdLog();
    findChangePwdLog.setCreateTime(new Date());
    findChangePwdLog.setType(logType);
    findChangePwdLog.setUserId(userId);
    findChangePwdLog.setIp(ip);

    if (userBll.updatePwd(userId, encodePassword)) {
      userBll.add(findChangePwdLog);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see cn.mahjong.service.UserService#sendFindPwdSms(java.lang.String)
   */
  @Override
  public ResultBean<Integer> sendFindPwdSms(String account) {
    UserBean userBean = userBll.get(account);
    if (userBean == null) {
      LOGGER.info("account:{} not exit", account);
      return new ResultBean<>(false, "发送失败", null);
    }
    String phone = userBean.getPhone();
    if (StringUtils.isEmpty(phone)) {
      LOGGER.info("account:{} not exit", account);
      return new ResultBean<>(false, "发送失败", null);
    }

    return smsService.send(phone, findPwdSmsContent);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.service.UserService#sendChangePhoneOldSms(java.lang.Integer)
   */
  @Override
  public ResultBean<Integer> sendChangePhoneSms(Integer userId, String smsContent) {
    UserBean userBean = userBll.get(userId);
    if (userBean == null) {
      LOGGER.info("userId:{} not exit", userId);
      return new ResultBean<>(false, "发送失败", null);
    }
    String phone = userBean.getPhone();
    if (StringUtils.isEmpty(phone)) {
      LOGGER.info("userId:{},phone is empty", userId);
      return new ResultBean<>(false, "发送失败", null);
    }

    return smsService.send(phone, smsContent);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.service.UserService#sendChangePhoneSms(java.lang.Integer, java.lang.String, java.lang.String)
   */
  @Override
  public ResultBean<Integer> sendChangePhoneSms(Integer userId, String phone, String smsContent) {
    UserBean userBean = userBll.get(userId);
    if (userBean == null) {
      LOGGER.info("userId:{} not exit", userId);
      return new ResultBean<>(false, "发送失败", null);
    }

    return smsService.send(phone, smsContent);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.service.UserService#changePhoneFirst(java.lang.String, java.lang.Integer)
   */
  @Override
  public ResultBean<Object> changePhoneFirst(String smsCode, Integer userId) {
    UserBean userBean = userBll.get(userId);
    if (userBean == null) {
      LOGGER.info("userId:{} not exit", userId);
      return new ResultBean<>(false, "用户不存在", null);
    }

    if (StringUtils.isEmpty(userBean.getPhone())) {
      LOGGER.info("userId:{} phone is empty", userId);
      return new ResultBean<>(false, "暂未绑定手机", null);
    }

    //验证短信验证码
    ResultBean<Object> validate = smsService.validate(userBean.getPhone(), smsCode);
    if (!validate.getIsSuccess()) {
      LOGGER.info("smsCode:{} validate fail", smsCode);
      return new ResultBean<>(false, "短信验证码错误或者已失效", null);
    }

    return new ResultBean<Object>(true, "更换手机第一步验证成功", null);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.service.UserService#changePhoneSecond(java.lang.String, java.lang.String, int, int)
   */
  @Override
  public ResultBean<Object> changePhoneSecond(String newPhone, String smsCode, int userId, int ip) {
    //验证短信验证码
    ResultBean<Object> validate = smsService.validate(newPhone, smsCode);
    if (!validate.getIsSuccess()) {
      LOGGER.info("smsCode:{} validate fail", smsCode);
      return new ResultBean<>(false, "短信验证码错误或者已失效", null);
    }

    UserBean userBean = userBll.get(userId);
    if (userBean == null) {
      LOGGER.info("userId:{} not exit", userId);
      return new ResultBean<>(false, "用户不存在", null);
    }

    String oldPhone = userBean.getPhone();
    if (StringUtils.isEmpty(oldPhone)) {
      LOGGER.info("userId:{} phone is empty", userId);
      return new ResultBean<>(false, "暂未绑定手机", null);
    }

    if (oldPhone.equals(newPhone)) {
      LOGGER.info("输入的手机号与原绑定的手机号一致");
      return new ResultBean<Object>(false, "输入的手机号与原绑定的手机号一致", null);
    }

    updatePhone(userBean, ip, newPhone);
    return new ResultBean<Object>(true, "更换手机成功", null);
  }

  @Override
  public ResultBean<Object> block(int userId, int operator, String reason) {
    userBll.block(userId);
    BlockLog log = new BlockLog("block", userId, operator, reason, new Date());
    blockLogBll.insert(log);
    return new ResultBean<>(true, "操作成功", null);
  }

  @Override
  public ResultBean<Object> unblock(int userId, int operator) {
    userBll.unblock(userId);
    BlockLog log = new BlockLog("unblock", userId, operator, null, new Date());
    blockLogBll.insert(log);
    return new ResultBean<>(true, "操作成功", null);
  }

  // 修改手机号并记录操作日志
  private void updatePhone(UserBean userBean, Integer ip, String newPhone) {
    AccountBindChangePhoneLog log = new AccountBindChangePhoneLog();
    log.setUserId(userBean.getCode());
    log.setCreateTime(new Date());
    log.setIp(ip);
    log.setNewPhone(newPhone);
    log.setType("change_phone");
    log.setOldPhone(userBean.getPhone());

    UserBean bean = new UserBean();
    bean.setCode(userBean.getCode());
    bean.setPhone(newPhone);
    userBll.updatePhone(bean, log);
  }
}
