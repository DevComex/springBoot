package cn.mahjong.redis.bll.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import cn.mahjong.redis.bll.LoginErrorOperatorBll;

/**
  * <p>
  *   LoginErrorOperatorDaoImpl描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@Service
public class LoginErrorOperatorBllImpl implements LoginErrorOperatorBll {

    private static final String ERRORTIME_PREFIX="MahjongAccountLoginErrorTimes:";
    
    @Autowired
    private RedisOperations<Serializable, Object> redisTemplate;

    /* (non-Javadoc)
     * @see cn.mahjong.redisdao.LoginErrorOperatorDao#incrementErrorTimes(java.lang.String)
     */
    @Override
    public void incrementErrorTimes(String account) {
        //这个时候该账号登陆次数累加一
        Calendar expireDate = Calendar.getInstance();
        expireDate.setTime(new Date());
        expireDate.add(Calendar.DAY_OF_MONTH,1);
        String key= ERRORTIME_PREFIX + account;
        
        Map<String, Object> loginErrorTimes=get(account);
        Map<String, Object> loginErrorTimesMap=new HashMap<>();
        loginErrorTimesMap.put("errorTimes", loginErrorTimes==null?1:Integer.parseInt(loginErrorTimes.get("errorTimes").toString())+1);
        loginErrorTimesMap.put("expireTime", expireDate);
        HashOperations<Serializable, Object, Object> opsForHash = redisTemplate.opsForHash();
        for (Map.Entry<String, Object> entry : loginErrorTimesMap.entrySet()) {
                opsForHash.put(key, entry.getKey(), entry.getValue());
        }
        // 设置失效时间
        redisTemplate.expireAt(key, ((Calendar) loginErrorTimesMap.get("expireTime")).getTime());
    }

    /* (non-Javadoc)
     * @see cn.mahjong.redisdao.LoginErrorOperatorDao#cleanUpErrorTimes(java.lang.String)
     */
    @Override
    public void cleanUpErrorTimes(String account) {
        String errorTimesKey=ERRORTIME_PREFIX + account;
        redisTemplate.delete(errorTimesKey);
    }

    /* (non-Javadoc)
     * @see cn.mahjong.redisdao.LoginErrorOperatorDao#get(java.lang.String)
     */
    @Override
    public Map<String, Object> get(String account) {
        // 缓存数据
        String savekey = ERRORTIME_PREFIX + account;
        HashOperations<Serializable, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(savekey);
        Map<String, Object> result = new HashMap<String, Object>();
        if (entries.isEmpty()) {
            return null;
        }
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            result.put(entry.getKey().toString(), entry.getValue());
        }
        return result;
    }

}
