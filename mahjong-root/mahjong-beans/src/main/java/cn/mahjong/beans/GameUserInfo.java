package cn.mahjong.beans;

public class GameUserInfo {

  private int balance;
  private String nickName;

  public GameUserInfo() {
  }

  public GameUserInfo(int balance, String nickName) {
    this.balance = balance;
    this.nickName = nickName;
  }

  @Override
  public String toString() {
    return "GameUserInfo{" +
        "balance=" + balance +
        ", nickName='" + nickName + '\'' +
        '}';
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
}
