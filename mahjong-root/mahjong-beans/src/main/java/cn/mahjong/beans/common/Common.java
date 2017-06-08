package cn.mahjong.beans.common;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.util.StringUtils;

public class Common {

  /// <summary>
  /// 计算账号Mask
  /// </summary>
  /// <param name="p"></param>
  /// <returns></returns>
  public static String getAcccountMask(String account) {
    if (StringUtils.isEmpty(account)) {
      return "";
    }
    StringBuilder accountmask = new StringBuilder();

    if (account.matches("^1[34578]\\d{9}$")) {
      accountmask.append(account.substring(0, 3));
      // 手机号的，前面都是*，最后两位明文
      accountmask.append("****");
      return accountmask.append(account.substring(account.length() - 4)).toString();
    } else {
      // 非手机号类的，都是前2，后2展示，中间有6个*
      return account.substring(0, 2) + "******" + account.substring(account.length() - 2);
    }

  }

  /// <summary>
  /// 计算账号Mask
  /// </summary>
  /// <param name="p"></param>
  /// <returns></returns>
  public static String getPhoneMask(String phone) {
    if (StringUtils.isEmpty(phone)) {
      return "";
    }
    StringBuilder accountmask = new StringBuilder();

    //accountmask.append(phone.substring(0, 3));
    // 手机号的，前面都是*，最后两位明文
    accountmask.append("*******");
    return accountmask.append(phone.substring(phone.length() - 4)).toString();
  }

  public static Interval getMonthInterval(int year, int month) {
    DateTime start = new DateTime(year, month, 1, 0, 0, 0);
    DateTime end = new DateTime(start).plusMonths(1);
    return new Interval(start, end);
  }
}
