package cn.mahjong.service.impl;

import org.springframework.stereotype.Service;
import cn.mahjong.beans.GameUserInfo;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.service.GameService;

@Service
public class GameServiceImpl implements GameService {

  @Override
  public ResultBean<GameUserInfo> getUserInfo(int gameUserId) {
    if (gameUserId % 2 == 0) {
      GameUserInfo userInfo = new GameUserInfo(37, "zsl");
      return new ResultBean<>(true, "获取成功", userInfo);
    } else {
      return new ResultBean<>(false, "用户不存在", null);
    }
  }

  @Override
  public ResultBean<Object> exchange(int userId, int amount) {
    return new ResultBean<>(true, "充值成功", null);
  }
}
