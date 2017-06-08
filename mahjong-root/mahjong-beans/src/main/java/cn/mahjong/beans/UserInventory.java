package cn.mahjong.beans;

public class UserInventory {

  private int giftInventory;
  private int userId;
  private int inventory;

  public UserInventory(int userId, int inventory, int giftInventory) {
    this.userId = userId;
    this.inventory = inventory;
    this.giftInventory = giftInventory;
  }
  public UserInventory() {
  }

  @Override
  public String toString() {
    return "UserInventory{" +
        "giftInventory=" + giftInventory +
        ", userId=" + userId +
        ", inventory=" + inventory +
        '}';
  }

  public int getGiftInventory() {
    return giftInventory;
  }

  public void setGiftInventory(int giftInventory) {
    this.giftInventory = giftInventory;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getInventory() {
    return inventory;
  }

  public void setInventory(int inventory) {
    this.inventory = inventory;
  }

}
