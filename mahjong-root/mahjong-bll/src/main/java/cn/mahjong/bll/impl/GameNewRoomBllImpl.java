package cn.mahjong.bll.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mahjong.beans.GameNewRoomBean;
import cn.mahjong.bll.GameNewRoomBll;
import cn.mahjong.dao.GameNewRoomBeanMapper;

/**
  * <p>
  *   GameNewRoomBllImpl描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@Service
public class GameNewRoomBllImpl implements GameNewRoomBll {
    
    @Autowired
    private GameNewRoomBeanMapper gameNewRoomBeanMapper;

    /* (non-Javadoc)
     * @see cn.mahjong.bll.GameNewRoomBll#add(cn.mahjong.beans.GameNewRoomBean)
     */
    @Override
    public Boolean add(GameNewRoomBean gameNewRoomBean) {
        return gameNewRoomBeanMapper.insert(gameNewRoomBean) > 0;
    }

}
