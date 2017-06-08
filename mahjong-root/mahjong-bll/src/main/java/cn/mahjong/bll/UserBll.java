package cn.mahjong.bll;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mahjong.beans.AccountBindChangePhoneLog;
import cn.mahjong.beans.AccountFindChangePwdLog;
import cn.mahjong.beans.AccountLoginLog;
import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;

public interface UserBll {

  /**
   * 通过用户ID（code）查找用户
   */
  UserBean get(Integer userId);

  /**
   * 通过用户帐号查找用户
   */
  UserBean get(String account);

  /**
   * 获取用户下层用户
   */
  List<UserBean> getSubList(int userId, String status, Integer pageIndex, Integer pageSize);

  /**
   * 新建用户
   */
  Boolean insert(UserBean user);

  /**
   * 更新密码
   */
  Boolean updatePwd(Integer userId, String newPwd);

  void updateInfo(UserBean bean);

  void updateHeadInfo(UserBean bean);

  void updatePhone(UserBean bean, AccountBindChangePhoneLog log);

  /**
   * <p>
   * 添加登录日志
   * </p>
   *
   * @return Boolean
   */
  Boolean addLoginLog(AccountLoginLog loginLog);

  /**
   * 添加找回密码日志
   */
  Boolean add(AccountFindChangePwdLog findChangePwdLog);

  /**
   * <p>
   * 写登录缓存
   * </p>
   *
   * @param loginIp void
   */
  void addLoginCookie(HttpServletResponse response, CookieUserInfo cookieUserInfo);

  /**
   * <p>
   * 验证登录
   * </p>
   *
   * @return CookieUserInfo
   * @action
   */
  CookieUserInfo checkLogin(HttpServletRequest request);

  /**
   * 登出
   */
  void logout(HttpServletResponse response);

  void block(int userId);

  void unblock(int userId);

  default List<UserBean> getParentUsers(int userId) {
    UserBean user = get(userId);
    ArrayList<UserBean> parentUsers = new ArrayList<>();
    if (user.getParentId() <= 0) {
      return parentUsers;
    }

    UserBean parentUser = get(user.getParentId());
    while (parentUser != null) {
      parentUsers.add(parentUser);
      parentUser = parentUser.getParentId() > 0 ? get(parentUser.getParentId()) : null;
    }
    return parentUsers;
  }
}
