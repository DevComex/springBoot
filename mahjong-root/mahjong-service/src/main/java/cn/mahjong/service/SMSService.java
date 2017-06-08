package cn.mahjong.service;

import cn.mahjong.beans.common.ResultBean;

public interface SMSService {

  ResultBean<Integer> send(String phoneNumber, String unsignedContent);

  ResultBean<Object> validate(String phoneNumber, String verifyCode);
}
