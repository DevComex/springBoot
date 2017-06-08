package cn.mahjong.beans;

public class RechargeSummary {

  private int count;
  private int inventory;
  private int sum;

  @Override
  public String toString() {
    return "RechargeSummary{" +
        "count=" + count +
        ", inventory=" + inventory +
        ", sum=" + sum +
        '}';
  }

  public int getSum() {
    return sum;
  }

  public void setSum(int sum) {
    this.sum = sum;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getInventory() {
    return inventory;
  }

  public void setInventory(int inventory) {
    this.inventory = inventory;
  }
}
