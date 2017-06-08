package cn.mahjong.action.form;

public class EditProfileForm {

  private Integer gameUserId;
  private String wechatId;
  private String name;
  private String province;
  private String city;
  private String address;

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

  @Override
  public String toString() {
    return "EditProfileForm{" +
        "gameUserId=" + gameUserId +
        ", wechatId='" + wechatId + '\'' +
        ", name='" + name + '\'' +
        ", province='" + province + '\'' +
        ", city='" + city + '\'' +
        ", address='" + address + '\'' +
        '}';
  }
}
