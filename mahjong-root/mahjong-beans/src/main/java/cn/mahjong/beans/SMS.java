package cn.mahjong.beans;

import java.util.Date;

public class SMS {

  private int code;
  private Date createTime;
  private String phoneNumber;
  private String content;
  private String status;

  public SMS(String phoneNumber, String content, String status, Date createTime) {
    this.createTime = createTime;
    this.phoneNumber = phoneNumber;
    this.content = content;
    this.status = status;
  }

  public SMS() {

  }

  @Override
  public String toString() {
    return "SMS{" +
        "code=" + code +
        ", createTime=" + createTime +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", content='" + content + '\'' +
        ", status='" + status + '\'' +
        '}';
  }

  public Date getCreateTime() {

    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getCode() {

    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

}
