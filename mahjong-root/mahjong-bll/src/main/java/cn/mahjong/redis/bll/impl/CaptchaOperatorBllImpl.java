package cn.mahjong.redis.bll.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.mahjong.redis.bll.CaptchaOperatorBll;

/**
  * <p>
  *   CaptchaOperatorBllImpl描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@Service
public class CaptchaOperatorBllImpl implements CaptchaOperatorBll{
    
    private static final String CAPTCHA_PREFIX="MahjongCaptchaCode:";
    
    @Autowired
    private RedisOperations<Serializable, Object> redisTemplate;

    /* (non-Javadoc)
     * @see cn.mahjong.redis.bll.CaptchaOperatorBll#save(java.lang.String)
     */
    @Override
    public void save(String captchaCode,String key) {
        Calendar expireDate = Calendar.getInstance();
        expireDate.setTime(new Date());
        expireDate.add(Calendar.MINUTE,5);
        String newKey= CAPTCHA_PREFIX + key;
        
        ValueOperations<Serializable, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(newKey,  captchaCode);
        
        // 设置失效时间
        redisTemplate.expireAt(newKey, expireDate.getTime());
    }

    /* (non-Javadoc)
     * @see cn.mahjong.redis.bll.CaptchaOperatorBll#get(java.lang.String)
     */
    @Override
    public String get(String key) {
        ValueOperations<Serializable, Object> opsForValue = redisTemplate.opsForValue();
        String newKey = CAPTCHA_PREFIX + key;
        Object value = opsForValue.get(newKey);
        if(value == null){
            return "";
        }
        return value.toString();
    }
    
}
