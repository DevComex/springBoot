package cn.mahjong.service;

import cn.mahjong.beans.GameUserInfo;
import cn.mahjong.beans.common.ResultBean;

public interface GameService {

  ResultBean<GameUserInfo> getUserInfo(int gameUserId);

  ResultBean<Object> exchange(int userId, int amount);
}
