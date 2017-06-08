package cn.mahjong.action;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.RechargeService;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/recharge")
public class RechargeController extends AbstractBaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseController.class);
  @Autowired
  private UserBll userBll;
  @Autowired
  private RechargeService service;

  @RequestMapping(value = {"/", "/index"})
  public String index() {
    return "/recharge/index";
  }

  /**
   * 充值房卡
   *
   * @param account 被充值用户账号
   * @param amount 数量
   * @param gift 赠送数量
   */
  @RequestMapping(value = "/recharge", method = RequestMethod.POST)
  public ResponseEntity<Object> recharge(String account, int amount, int gift,
      HttpServletRequest request) {
    LOGGER.info("开始为用户充值, account: " + account + ", amount: " + amount + ", gift: " + gift);
    if (amount <= 0 || gift < 0 || account == null || account.trim().isEmpty()) {
      LOGGER.info("充值结束，参数错误");
      return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
    }

    CookieUserInfo operator = userBll.checkLogin(request);
    if (operator == null) {
      LOGGER.info("充值结束，用户未登录");
      return new ResponseEntity<>(new ResultBean<>(false, "用户未登录", null), HttpStatus.OK);
    }

    UserBean user = userBll.get(account);
    if (user == null) {
      return new ResponseEntity<>(new ResultBean<>(false, "用户不存在", null), HttpStatus.OK);
    }

    ResultBean<Void> result = service.recharge(amount, gift, user.getCode(), operator.getUserId());
    LOGGER.info("充值结束，result: " + result);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
