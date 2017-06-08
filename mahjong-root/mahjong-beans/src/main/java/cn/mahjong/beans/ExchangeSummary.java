package cn.mahjong.beans;

public class ExchangeSummary {

  private int count;
  private int sum;

  @Override
  public String toString() {
    return "ExchangeSummary{" +
        "count=" + count +
        ", sum=" + sum +
        '}';
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getSum() {
    return sum;
  }

  public void setSum(int sum) {
    this.sum = sum;
  }
}
