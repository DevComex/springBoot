package cn.mahjong.redis.bll;

/**
  * <p>
  *   CaptchaOperatorBll描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public interface CaptchaOperatorBll {
    void save(String captchaCode,String key);
    
    String get(String key);
}
