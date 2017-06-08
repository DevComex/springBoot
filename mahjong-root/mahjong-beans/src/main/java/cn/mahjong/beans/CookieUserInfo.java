package cn.mahjong.beans;

/**
 * <p>
 * CookieUserInfo描述
 * </p>
 *
 * @author chenwen
 * @since 0.0.1
 */
public class CookieUserInfo {

  private int userId;
  private String account;
  private String role;
  private String loginIp;
  private Integer gameId;

  public CookieUserInfo() {
  }

  public CookieUserInfo(int userId, String account, String role, String loginIp, Integer gameId) {
    this.userId = userId;
    this.account = account;
    this.loginIp = loginIp;
    this.role = role;
    this.gameId = gameId;
  }

  public Integer getGameId() {
    return gameId;
  }

  public void setGameId(Integer gameId) {
    this.gameId = gameId;
  }

  public synchronized int getUserId() {
    return userId;
  }

  public synchronized void setUserId(int userId) {
    this.userId = userId;
  }

  public synchronized String getAccount() {
    return account;
  }

  public synchronized void setAccount(String account) {
    this.account = account;
  }

  public synchronized String getRole() {
    return role;
  }

  public synchronized void setRole(String role) {
    this.role = role;
  }

  public synchronized String getLoginIp() {
    return loginIp;
  }

  public synchronized void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
  }

  @Override
  public String toString() {
    return "CookieUserInfo [userId=" + userId + ", account=" + account
        + ", role=" + role + ", loginIp=" + loginIp + "]";
  }
}
