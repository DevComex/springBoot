package cn.mahjong.model;

import java.util.Date;

public class Profile {

  private Integer gameUserId;
  private String wechatId;
  private String name;
  private String phoneNumber;
  private String province;
  private String city;
  private String address;
  private Date registerTime;

  public Profile(Integer gameUserId, String wechatId, String name, String phoneNumber,
      String province, String city, String address, Date registerTime) {

    this.gameUserId = gameUserId;
    this.wechatId = wechatId;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.province = province;
    this.city = city;
    this.address = address;
    this.registerTime = registerTime;
  }

  @Override
  public String toString() {
    return "Profile{" +
        "gameUserId=" + gameUserId +
        ", wechatId='" + wechatId + '\'' +
        ", name='" + name + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", province=" + province +
        ", city=" + city +
        ", address='" + address + '\'' +
        ", registerTime=" + registerTime +
        '}';
  }

  public Integer getGameUserId() {
    return gameUserId;
  }

  public void setGameUserId(Integer gameUserId) {
    this.gameUserId = gameUserId;
  }

  public String getWechatId() {
    return wechatId;
  }

  public void setWechatId(String wechatId) {
    this.wechatId = wechatId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getRegisterTime() {
    return registerTime;
  }

  public void setRegisterTime(Date registerTime) {
    this.registerTime = registerTime;
  }
}
