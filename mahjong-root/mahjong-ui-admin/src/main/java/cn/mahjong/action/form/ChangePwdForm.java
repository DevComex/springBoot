package cn.mahjong.action.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import cn.mahjong.beans.validator.FirstGroup;
import cn.mahjong.beans.validator.SecondGroup;

/**
  * <p>
  *   ChangePwd描述
  * </p>
  *  
  * @author 
  * @since 0.0.1
  */
public class ChangePwdForm {
    /**
     * 密码
     */
    @NotBlank(message = "新密码不能为空", groups = {FirstGroup.class})
    @Pattern(regexp = "[^\u4e00-\u9fa5]{6,16}", message = "新密码格式不正确", groups = {SecondGroup.class})
    private String password;
    
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空", groups = {FirstGroup.class})
    private String confirmPassword;

    public synchronized String getPassword() {
        return password;
    }

    public synchronized void setPassword(String password) {
        this.password = password;
    }

    public synchronized String getConfirmPassword() {
        return confirmPassword;
    }

    public synchronized void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "ChangePwdForm [password=" + password + ", confirmPassword="
                + confirmPassword + "]";
    }

}
