package cn.mahjong.bll;

import cn.mahjong.beans.UserInventory;

public interface UserInventoryBll {
  UserInventory get(int userId);

  /**
   * 操作用户库存并记录流水
   * @param userId 用户ID
   * @param amount 数量
   */
  void increase(int userId, int amount, int gift);

  /**
   * 优先减赠品库存
   * @param userId 用户ID
   * @param amount 数量
   */
  void decrease(int userId, int amount);
}
