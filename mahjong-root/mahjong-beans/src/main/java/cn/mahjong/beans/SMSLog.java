package cn.mahjong.beans;

import java.util.Date;

public class SMSLog {

  private int code;
  private int smsCode;
  private String channel;
  private String status;
  private String response;
  private Date createTime;

  public SMSLog() {

  }

  public SMSLog(int smsCode, String channel, String status, String response,
      Date createTime) {

    this.smsCode = smsCode;
    this.channel = channel;
    this.status = status;
    this.response = response;
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "SMSLog{" +
        "code=" + code +
        ", smsCode=" + smsCode +
        ", channel='" + channel + '\'' +
        ", status='" + status + '\'' +
        ", response='" + response + '\'' +
        ", createTime=" + createTime +
        '}';
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public int getSmsCode() {
    return smsCode;
  }

  public void setSmsCode(int smsCode) {
    this.smsCode = smsCode;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
