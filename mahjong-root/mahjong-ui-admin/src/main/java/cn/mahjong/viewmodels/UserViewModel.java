package cn.mahjong.viewmodels;

import java.util.Date;

public class UserViewModel {

  private Integer code;
  private String account;
  private String phone;
  private String name;
  private String role;
  private String status;
  private Date createTime;
  private int cardInventory;
  private int cardGiftInventory;
  private String province;
  private String city;
  private String address;
  private Integer gameUserId;
  private String wechatId;

  public int getCardGiftInventory() {
    return cardGiftInventory;
  }

  public void setCardGiftInventory(int cardGiftInventory) {
    this.cardGiftInventory = cardGiftInventory;
  }

  @Override
  public String toString() {
    return "UserViewModel{" +
        "code=" + code +
        ", account='" + account + '\'' +
        ", phone='" + phone + '\'' +
        ", name='" + name + '\'' +
        ", role='" + role + '\'' +
        ", status='" + status + '\'' +
        ", createTime=" + createTime +
        ", cardInventory=" + cardInventory +
        ", cardGiftInventory=" + cardGiftInventory +
        ", province='" + province + '\'' +
        ", city='" + city + '\'' +
        ", address='" + address + '\'' +
        ", gameUserId=" + gameUserId +
        ", wechatId='" + wechatId + '\'' +
        '}';
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public int getCardInventory() {
    return cardInventory;
  }

  public void setCardInventory(int cardInventory) {
    this.cardInventory = cardInventory;
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
}
