package cn.mahjong.service.sms;

import cn.mahjong.beans.common.ResultBean;

public interface SPProvider {

  String getName();

  ResultBean<String> send(String phoneNumber, String content);
}
