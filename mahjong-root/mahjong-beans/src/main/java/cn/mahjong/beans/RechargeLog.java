package cn.mahjong.beans;

import java.util.Date;

public class RechargeLog {

  private int code;
  private int userId;
  private int amount;
  private int operator;
  private int gift;
  private String operatorAccount;
  private String userAccount;
  private Date createTime;

  public RechargeLog() {
    
  }

  public RechargeLog(int amount, int gift, int operator, String operatorAccount,
      int userId, String userAccount, Date createTime) {
    this.userId = userId;
    this.amount = amount;
    this.operator = operator;
    this.gift = gift;
    this.operatorAccount = operatorAccount;
    this.userAccount = userAccount;
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "RechargeLog{" +
        "code=" + code +
        ", userId=" + userId +
        ", amount=" + amount +
        ", operator=" + operator +
        ", gift=" + gift +
        ", operatorAccount='" + operatorAccount + '\'' +
        ", userAccount='" + userAccount + '\'' +
        ", createTime=" + createTime +
        '}';
  }

  public int getGift() {
    return gift;
  }

  public void setGift(int gift) {
    this.gift = gift;
  }

  public String getUserAccount() {
    return userAccount;
  }

  public void setUserAccount(String userAccount) {
    this.userAccount = userAccount;
  }

  public String getOperatorAccount() {
    return operatorAccount;
  }

  public void setOperatorAccount(String operatorAccount) {
    this.operatorAccount = operatorAccount;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
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

  public int getOperator() {
    return operator;
  }

  public void setOperator(int operator) {
    this.operator = operator;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
