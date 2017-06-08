package cn.mahjong.dao;

import cn.mahjong.beans.SMS;
import org.apache.ibatis.annotations.Param;

public interface SMSMapper {

  void insert(SMS sms);

  void update(@Param("code") int code, @Param("status") String status);
}
