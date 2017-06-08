package cn.mahjong.redis.bll;

import java.util.Map;

/**
  * <p>
  *   LoginErrorOperatorDao描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public interface LoginErrorOperatorBll {
    //增加登录错误次数
    void incrementErrorTimes(String account);
    //清空登录错误次数
    void cleanUpErrorTimes(String account);
    //获取登录错误次数
    Map<String, Object> get(String account);
}
