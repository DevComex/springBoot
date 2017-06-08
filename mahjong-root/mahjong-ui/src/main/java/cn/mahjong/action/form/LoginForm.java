package cn.mahjong.action.form;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import cn.mahjong.beans.validator.FirstGroup;
import cn.mahjong.beans.validator.SecondGroup;

@GroupSequence({FirstGroup.class, SecondGroup.class, LoginForm.class})
public class LoginForm {

  /**
   * 账户
   */
  @NotBlank(message = "账号不能为空", groups = {FirstGroup.class})
  private String account;

  /**
   * 密码
   */
  @NotBlank(message = "密码不能为空", groups = {FirstGroup.class})
  @Pattern(regexp = "[^\u4e00-\u9fa5]{6,16}", message = "密码格式不正确", groups = {SecondGroup.class})
  private String password;

  /**
   * 验证码
   */
  private String captchaCode;

  public synchronized String getAccount() {
    return account;
  }

  public synchronized void setAccount(String account) {
    this.account = account;
  }

  public synchronized String getPassword() {
    return password;
  }

  public synchronized void setPassword(String password) {
    this.password = password;
  }

  public synchronized String getCaptchaCode() {
    return captchaCode;
  }

  public synchronized void setCaptchaCode(String captchaCode) {
    this.captchaCode = captchaCode;
  }

  @Override
  public String toString() {
    return "LoginForm [account=" + account + ", password=" + password
        + ", captchaCode=" + captchaCode + "]";
  }


}
