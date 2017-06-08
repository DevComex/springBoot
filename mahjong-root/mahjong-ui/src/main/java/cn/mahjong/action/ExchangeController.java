package cn.mahjong.action;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.ExchangeLog;
import cn.mahjong.beans.ExchangeSummary;
import cn.mahjong.beans.common.Common;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.ExchangeService;
import cn.mahjong.service.SMSService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/exchange")
public class ExchangeController extends AbstractBaseController {

  @Autowired
  private ExchangeService exchangeService;

  @Autowired
  private UserBll userBll;

  @Autowired
  private SMSService smsService;

  @RequestMapping("/")
  public String indexView() {
    return "/exchange/index";
  }

  @RequestMapping(value = "/exchange", method = RequestMethod.POST)
  public ResponseEntity<Object> exchange(int gameUserId, int amount, HttpServletRequest request) {
    CookieUserInfo info = userBll.checkLogin(request);
    ResultBean<Object> resultBean = exchangeService.exchange(info.getUserId(), gameUserId, amount);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @RequestMapping("/history")
  public String historyView(String role) {
    return "/exchange/history/" + role;
  }

  @RequestMapping("/list")
  public ResponseEntity<Object> list(Integer gameUserId, boolean filterByOperator, int year,
      int month, int pageIndex, HttpServletRequest request) {
    if (!filterByOperator && gameUserId == null) {
      return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
    }

    CookieUserInfo info = userBll.checkLogin(request);
    PageResultBean<List<ExchangeLog>> resultBean = exchangeService
        .get(filterByOperator ? info.getUserId() : null, gameUserId, year, month, pageIndex);
    if (!filterByOperator) {
      resultBean.getData().forEach(
          item -> item.setOperatorAccount(Common.getAcccountMask(item.getOperatorAccount())));
    }
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @RequestMapping("/summary")
  public ResponseEntity<Object> summary(Integer gameUserId, boolean filterByOperator, int year,
      int month,
      HttpServletRequest request) {
    if (!filterByOperator && gameUserId == null) {
      return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
    }

    CookieUserInfo info = userBll.checkLogin(request);
    ResultBean<ExchangeSummary> summary = exchangeService.getSummary(filterByOperator ? info
        .getUserId() : null, gameUserId, year, month);
    return new ResponseEntity<>(summary, HttpStatus.OK);
  }
}
