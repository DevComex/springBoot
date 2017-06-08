package cn.mahjong.beans;

import java.util.Date;

public class ExchangeLog {

  private String operatorAccount;
  private String gameNickName;
  private int code;
  private int gameUserId;
  private int amount;
  private int operator;
  private Date createTime;
  private String status;

  public String getOperatorAccount() {
    return operatorAccount;
  }

  public void setOperatorAccount(String operatorAccount) {
    this.operatorAccount = operatorAccount;
  }

  public String getGameNickName() {
    return gameNickName;
  }

  public void setGameNickName(String gameNickName) {
    this.gameNickName = gameNickName;
  }

  @Override
  public String toString() {
    return "ExchangeLog{" +
        "operatorAccount='" + operatorAccount + '\'' +
        ", gameNickName='" + gameNickName + '\'' +
        ", code=" + code +
        ", gameUserId=" + gameUserId +
        ", amount=" + amount +
        ", operator=" + operator +
        ", createTime=" + createTime +
        ", status='" + status + '\'' +
        '}';
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public int getGameUserId() {
    return gameUserId;
  }

  public void setGameUserId(int gameUserId) {
    this.gameUserId = gameUserId;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
