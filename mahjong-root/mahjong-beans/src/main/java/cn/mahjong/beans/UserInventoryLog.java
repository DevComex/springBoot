package cn.mahjong.beans;

import java.util.Date;

public class UserInventoryLog {

  private int userId;
  private int amount;
  private int gift;
  private Date createTime;
  private int code;

  public UserInventoryLog() {
  }

  public UserInventoryLog(int userId, int amount, int gift, Date createTime) {
    this.userId = userId;
    this.amount = amount;
    this.createTime = createTime;
    this.gift = gift;
  }

  @Override
  public String toString() {
    return "UserInventoryLog{" +
        "userId=" + userId +
        ", amount=" + amount +
        ", gift=" + gift +
        ", createTime=" + createTime +
        ", code=" + code +
        '}';
  }

  public int getGift() {
    return gift;
  }

  public void setGift(int gift) {
    this.gift = gift;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

}
