package cn.mahjong.action;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.RechargeLog;
import cn.mahjong.beans.RechargeSummary;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.RechargeService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recharge")
public class RechargeController extends AbstractBaseController {

  @Autowired
  private RechargeService rechargeService;

  @Autowired
  private UserBll userBll;

  @RequestMapping("/history")
  public String historyView() {
    return "/recharge/history";
  }

  @RequestMapping("/list")
  public ResponseEntity<Object> list(int year, int month, int pageIndex,
      HttpServletRequest request) {
    CookieUserInfo info = userBll.checkLogin(request);
    PageResultBean<List<RechargeLog>> logs = rechargeService
        .get(info.getUserId(), year, month, pageIndex);
    return new ResponseEntity<>(logs, HttpStatus.OK);
  }

  @RequestMapping("/summary")
  public ResponseEntity<Object> summary(int year, int month, HttpServletRequest request) {
    CookieUserInfo info = userBll.checkLogin(request);
    ResultBean<RechargeSummary> summary = rechargeService.getSummary(info.getUserId(), year, month);
    return new ResponseEntity<>(summary, HttpStatus.OK);
  }
}
