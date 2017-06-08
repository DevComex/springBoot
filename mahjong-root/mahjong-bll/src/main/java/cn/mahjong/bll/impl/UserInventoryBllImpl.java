package cn.mahjong.bll.impl;

import cn.mahjong.beans.UserInventory;
import cn.mahjong.beans.UserInventoryLog;
import cn.mahjong.bll.UserInventoryBll;
import cn.mahjong.dao.UserInventoryLogMapper;
import cn.mahjong.dao.UserInventoryMapper;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInventoryBllImpl implements UserInventoryBll {

  @Autowired
  private UserInventoryMapper mapper;
  @Autowired
  private UserInventoryLogMapper userInventoryLogMapper;

  @Override
  public UserInventory get(int userId) {
    return mapper.selectByUserId(userId);
  }

  @Override
  public void increase(int userId, int amount, int gift) {
    // 记录流水
    UserInventoryLog log = new UserInventoryLog(userId, amount, gift, new Date());
    userInventoryLogMapper.insert(log);
    // 操作inventory字段
    mapper.increase(userId, amount, gift);
  }

  @Override
  public void decrease(int userId, int amount) {
    UserInventory inventory = mapper.selectByUserId(userId);
    if (inventory.getGiftInventory() >= amount) {
      mapper.increase(userId, 0, -amount);
    } else {
      mapper.increase(userId, -amount + inventory.getGiftInventory(),
          -inventory.getGiftInventory());
    }
  }
}
