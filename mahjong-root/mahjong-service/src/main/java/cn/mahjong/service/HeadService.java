package cn.mahjong.service;

import java.util.List;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.common.ResultBean;

public interface HeadService {

  /**
   * 获取指定下级
   *
   * @param parentUserId 父级用户ID
   * @param account 指定下级帐号
   */
  UserBean getHead(int parentUserId, String account);

  /**
   * 获取下级局头
   */
  List<UserBean> getHeads(int parentUserId, Integer pageIndex, Integer pageSize);

  /**
   * 创建局头
 * @param parent 
   */
  ResultBean<String> createHead(UserBean newHead, CookieUserInfo parent, String verifyCode);

  /**
   * 修改局头用户信息
   */
  ResultBean<Void> update(int userId, Integer gameUserId, String wechatId, String name,
      String province, String city, String address);
}
