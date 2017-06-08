package cn.mahjong.service.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserInventory;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.PasswordUtils;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.bll.UserInventoryBll;
import cn.mahjong.redis.bll.SMSBll;
import cn.mahjong.service.HeadService;

@Service
public class HeadServiceImpl implements HeadService {

  public static final SecureRandom random = new SecureRandom();
  private UserBll userBll;
  private UserInventoryBll cardBll;
  private SMSBll smsBll;

  private Consumer<UserBean> fillCardInventory = user -> {
    UserInventory card = cardBll.get(user.getCode());
    if (card == null) {
      user.setCardInventory(0);
    } else {
      user.setCardInventory(card.getInventory());
    }
  };

  @Autowired
  public void setUserBll(UserBll userBll) {
    this.userBll = userBll;
  }

  @Autowired
  public void setUserInventoryBll(UserInventoryBll cardBll) {
    this.cardBll = cardBll;
  }

  @Autowired
  public void setSMSBll(SMSBll smsBll) {
    this.smsBll = smsBll;
  }

  @Override
  public List<UserBean> getHeads(int parentUserId, Integer pageIndex, Integer pageSize) {
    // 由于该方法只会被推广员调用，而推广员下级只会有局头，因此不筛选角色 = head
    List<UserBean> list = userBll.getSubList(parentUserId, "normal", pageIndex, pageSize);
    list.stream().forEach(fillCardInventory);
    return list;
  }

  @Override
  public UserBean getHead(int parentUserId, String account) {
    UserBean user = userBll.get(account);
    if (user == null) {
      return null;
    }
    if (user.getParentId() != parentUserId) {
      return null;
    }
    if ("blocked".equals(user.getStatus())) {
      return null;
    }
    fillCardInventory.accept(user);
    return user;
  }

  @Override
  public ResultBean<String> createHead(UserBean newHead, CookieUserInfo user, String verifyCode) {
    UserBean exist = userBll.get(newHead.getPhone());

    if (exist != null) {
      return new ResultBean<>(false, "局头已存在", null);
    }

    boolean v = smsBll.validate(newHead.getPhone(), verifyCode);

    if (!v) {
      return new ResultBean<>(false, "验证码错误", null);
    }

    newHead.setAccount(newHead.getPhone());
    newHead.setParentId(user.getUserId());
    newHead.setGameId(user.getGameId());

    String newPassword = String.format("%06d", random.nextInt(1000000));
    newHead.setSalt(RandomStringUtils.randomAscii(8));
    String encodePassword = PasswordUtils.encode(newPassword, newHead.getSalt());
    newHead.setPassword(encodePassword);

    newHead.setRole(UserRole.HEAD.getValue());

    userBll.insert(newHead);

    return new ResultBean<>(true, "添加局头成功", newPassword);
  }

  @Override
  public ResultBean<Void> update(int userId, Integer gameUserId, String wechatId, String
      name, String province, String city, String address) {
    UserBean userBean = new UserBean();
    userBean.setCode(userId);
    userBean.setName(name);
    userBean.setGameUserId(gameUserId);
    userBean.setWechatId(wechatId);
    userBean.setProvince(province);
    userBean.setCity(city);
    userBean.setAddress(address);
    userBll.updateInfo(userBean);
    userBll.updateHeadInfo(userBean);
    return new ResultBean<>(true, "修改成功", null);
  }
}
