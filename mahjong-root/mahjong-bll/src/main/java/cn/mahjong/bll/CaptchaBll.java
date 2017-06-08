package cn.mahjong.bll;

import javax.servlet.http.HttpServletResponse;

/**
  * <p>
  *   CaptchaBll描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public interface CaptchaBll {
    //创建验证码
    void create(String bid,HttpServletResponse response);
    
}
