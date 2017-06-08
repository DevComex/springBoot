package cn.mahjong.bll.impl;

import cn.mahjong.beans.UserInventory;
import cn.mahjong.dao.BlockLogMapper;
import cn.mahjong.dao.UserInventoryMapper;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.mahjong.beans.AccountBindChangePhoneLog;
import cn.mahjong.beans.AccountFindChangePwdLog;
import cn.mahjong.beans.AccountLoginLog;
import cn.mahjong.beans.AccountStatus;
import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.AES;
import cn.mahjong.beans.common.CookieUtil;
import cn.mahjong.bll.UserBll;
import cn.mahjong.dao.AccountBindChangePhoneLogMapper;
import cn.mahjong.dao.AccountFindChangePwdLogMapper;
import cn.mahjong.dao.AccountLoginLogMapper;
import cn.mahjong.dao.UserBeanMapper;

@Service
public class UserBllImpl implements UserBll {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserBllImpl.class);

  private static final String LOGIN_COOKIE_NAME = "MAHJONG_SESSION";

  @Value("${account.login.cookie.secret.key}")
  private String loginCookieKey;

  @Autowired
  private UserBeanMapper userBeanMapper;

  @Autowired
  private UserInventoryMapper userInventoryMapper;

  @Autowired
  private AccountLoginLogMapper accountLoginLogMapper;

  @Autowired
  private AccountFindChangePwdLogMapper findChangePwdLogMapper;

  @Autowired
  private AccountBindChangePhoneLogMapper bindChangePhoneLogMapper;

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#get(java.lang.Integer)
   */
  @Override
  public UserBean get(Integer userId) {
    return userBeanMapper.selectByPrimaryKey(userId);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#get(java.lang.String)
   */
  @Override
  public UserBean get(String account) {
    return userBeanMapper.selectByAccount(account);
  }

  @Override
  public List<UserBean> getSubList(int userId, String status, Integer pageIndex, Integer pageSize) {
    int offset = (pageIndex - 1) * pageSize;
    return userBeanMapper.selectSubByUserId(userId, status, offset, pageSize);
  }

  @Override
  @Transactional
  public Boolean insert(UserBean user) {
    user.setStatus(AccountStatus.NORMAL.getValue());
    user.setCreateTime(new Date());
    userBeanMapper.insert(user);
    if (!UserRole.HQ.getValue().equals(user.getRole())) {
      userBeanMapper.insertInfo(user);
    }
    if (UserRole.HEAD.getValue().equals(user.getRole())) {
      userBeanMapper.insertHeadInfo(user);
    }
    UserInventory inventory = new UserInventory(user.getCode(), 0, 0);
    userInventoryMapper.insert(inventory);
    return true;
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#updatePwd(java.lang.Integer)
   */
  @Override
  public Boolean updatePwd(Integer userId, String newPwd) {
    return userBeanMapper.updatePwdByPrimaryKey(userId, newPwd) > 0;
  }

  @Override
  public void updateInfo(UserBean bean) {
    userBeanMapper.updateInfoByPrimaryKeySelective(bean);
  }

  @Override
  public void updateHeadInfo(UserBean bean) {
    userBeanMapper.updateHeadInfoByPrimaryKeySelective(bean);
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#insert(cn.mahjong.beans.AccountLoginLog)
   */
  @Override
  public Boolean addLoginLog(AccountLoginLog loginLog) {
    return accountLoginLogMapper.insert(loginLog) > 0;
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#add(cn.mahjong.beans.AccountFindChangePwdLog)
   */
  @Override
  public Boolean add(AccountFindChangePwdLog findChangePwdLog) {
    return findChangePwdLogMapper.insert(findChangePwdLog) > 0;
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#addLoginCookie(javax.servlet.http.HttpServletResponse, cn.mahjong.beans.CookieUserInfo)
   */
  @Override
  public void addLoginCookie(HttpServletResponse response, CookieUserInfo cookieUserInfo) {
    try {
      if (cookieUserInfo != null) {
        String cookieValue = new ObjectMapper().writeValueAsString(cookieUserInfo);
        LOGGER.debug("cookie name:{},value:{}", LOGIN_COOKIE_NAME, cookieValue);
        String value = AES.encrypt(cookieValue, loginCookieKey);
        CookieUtil.addCookie(response, LOGIN_COOKIE_NAME, value);
      }
    } catch (Exception e) {
      LOGGER.warn("" + e);
    }
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#checkLogin(javax.servlet.http.HttpServletRequest)
   */
  @Override
  public CookieUserInfo checkLogin(HttpServletRequest request) {
    try {
      String cookieValue = getLoginCookie(request);
      if (StringUtils.isEmpty(cookieValue)) {
        LOGGER.info("账号未登陆");
        return null;
      }
      LOGGER.debug("cookieValue:{}", cookieValue);
      return new ObjectMapper().readValue(cookieValue, CookieUserInfo.class);

    } catch (Exception e) {
      LOGGER.error("checkLogin Exception:{}", e);
      return null;
    }
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#logout(javax.servlet.http.HttpServletResponse)
   */
  @Override
  public void logout(HttpServletResponse response) {
    CookieUtil.removeCookie(response, LOGIN_COOKIE_NAME);
  }

  @Override
  public void block(int userId) {
    userBeanMapper.block(userId);
  }

  @Override
  public void unblock(int userId) {
    userBeanMapper.unblock(userId);
  }

  private String getLoginCookie(HttpServletRequest request) {
    try {
      Cookie cookie = CookieUtil.getCookie(request, LOGIN_COOKIE_NAME);
      if (cookie == null) {
        LOGGER.info("没有获取到cookie值,name:{}", LOGIN_COOKIE_NAME);
        return "";
      }
      return AES.decrypt(URLDecoder.decode(cookie.getValue()), loginCookieKey);
    } catch (Exception e) {
      LOGGER.error("getLoginCookie exception:{}", e);
      return "";
    }
  }

  /* (non-Javadoc)
   * @see cn.mahjong.bll.UserBll#updatePhone(cn.mahjong.beans.UserBean)
   */
  @Override
  public void updatePhone(UserBean bean, AccountBindChangePhoneLog log) {
    if (userBeanMapper.updateInfoByPrimaryKeySelective(bean) > 0) {
      bindChangePhoneLogMapper.insert(log);
    }
    return;
  }
}
