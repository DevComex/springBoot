package cn.mahjong.bll.impl;

import cn.mahjong.beans.BlockLog;
import cn.mahjong.bll.BlockLogBll;
import cn.mahjong.dao.BlockLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockLogBllImpl implements BlockLogBll {

  @Autowired
  private BlockLogMapper blockLogMapper;

  @Override
  public void insert(BlockLog log) {
    blockLogMapper.insert(log);
  }
}
