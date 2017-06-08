package cn.mahjong.dao;

import cn.mahjong.beans.UserInventory;
import org.apache.ibatis.annotations.Param;

public interface UserInventoryMapper {

  UserInventory selectByUserId(int userId);

  void insert(UserInventory inventory);

  void increase(@Param("userId") int userId, @Param("amount") int amount, @Param("giftInventory")
      int giftInventory);
}
