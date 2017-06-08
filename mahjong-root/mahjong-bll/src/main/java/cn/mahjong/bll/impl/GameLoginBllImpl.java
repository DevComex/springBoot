package cn.mahjong.bll.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mahjong.beans.GameLoginBean;
import cn.mahjong.bll.GameLoginBll;
import cn.mahjong.dao.GameLoginBeanMapper;

/**
  * <p>
  *   GameLoginBllImpl描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@Service
public class GameLoginBllImpl implements GameLoginBll{
    @Autowired
    private GameLoginBeanMapper gameLoginBeanMapper;

    /* (non-Javadoc)
     * @see cn.mahjong.bll.GameLoginBll#add(cn.mahjong.beans.GameLoginBean)
     */
    @Override
    public Boolean add(GameLoginBean gameLoginBean) {
        return gameLoginBeanMapper.insert(gameLoginBean) > 0;
    }

}
