package cn.mahjong.action.form;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import cn.mahjong.beans.validator.FirstGroup;
import cn.mahjong.beans.validator.SecondGroup;

/**
  * <p>
  *   RegForm描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
@GroupSequence({ FirstGroup.class, SecondGroup.class, GameLoginForm.class })
public class GameLoginForm {
    
    /**
     * 用户在游戏内的唯一标识
     */
    @Min(value = 1,message ="用户标识不能为空",groups = {FirstGroup.class})
    private int userId;
    
    /**
     * 用户注册时间
     */
    @Min(value = 1,message = "登录时间不能为空", groups = {FirstGroup.class})
    private long loginTime;
    
    /**
     * 用户注册时IP地址
     */
    @NotBlank(message = "ip不能为空", groups = {FirstGroup.class})
    private String ip;

    public synchronized int getUserId() {
        return userId;
    }

    public synchronized void setUserId(int userId) {
        this.userId = userId;
    }

    public synchronized long getLoginTime() {
        return loginTime;
    }

    public synchronized void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public synchronized String getIp() {
        return ip;
    }

    public synchronized void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "GameLoginForm [userId=" + userId + ", loginTime=" + loginTime
                + ", ip=" + ip + "]";
    }
    
    
}
