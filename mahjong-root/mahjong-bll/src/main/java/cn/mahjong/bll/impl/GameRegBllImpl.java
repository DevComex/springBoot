package cn.mahjong.bll.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mahjong.beans.GameRegBean;
import cn.mahjong.bll.GameRegBll;
import cn.mahjong.dao.GameRegBeanMapper;

/**
  * <p>
  *   GameRegBllImpl描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@Service
public class GameRegBllImpl implements GameRegBll {
    
    @Autowired
    private GameRegBeanMapper gameRegBeanMapper;

    /* (non-Javadoc)
     * @see cn.mahjong.bll.GameRegBll#add(cn.mahjong.beans.GameRegBean)
     */
    @Override
    public boolean add(GameRegBean gameRegBean) {
        return gameRegBeanMapper.insert(gameRegBean) > 0;
    }

}
