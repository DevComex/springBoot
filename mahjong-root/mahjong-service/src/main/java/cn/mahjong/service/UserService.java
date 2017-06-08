package cn.mahjong.service;

import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;

public interface UserService {

  void setUserBll(UserBll userBll);

  ResultBean<Object> changePwd(Integer userId, String newPwd, Integer ip);

  ResultBean<Object> findPwd(String account, Integer ip, String pwd, String smsCode,
      boolean isHead);

  ResultBean<Integer> sendFindPwdSms(String account);

  ResultBean<Integer> sendChangePhoneSms(Integer userId, String smsContent);

  ResultBean<Integer> sendChangePhoneSms(Integer userId, String phone, String smsContent);

  ResultBean<Object> changePhoneFirst(String smsCode, Integer userId);

  ResultBean<Object> changePhoneSecond(String newPhone, String smsCode, int userId, int ip);

  ResultBean<Object> block(int userId, int operator, String reason);

  ResultBean<Object> unblock(int userId, int operator);
}
