package cn.mahjong.beans.common;

public class PageResultBean<T> extends ResultBean<T> {

  private int count;

  public PageResultBean() {
  }

  public PageResultBean(boolean isSuccess, String message, T data) {
    super(isSuccess, message, data);
  }

  public PageResultBean(boolean isSuccess, String messsage, T data, int count) {
    super(isSuccess, messsage, data);
    setCount(count);
  }

  public synchronized int getCount() {
    return count;
  }

  public synchronized void setCount(int count) {
    this.count = count;
  }

}
