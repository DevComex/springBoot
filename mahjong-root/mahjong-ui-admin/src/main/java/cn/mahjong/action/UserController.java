package cn.mahjong.action;

import cn.mahjong.beans.UserInventory;
import cn.mahjong.bll.UserInventoryBll;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.mahjong.action.form.ChangePwdForm;
import cn.mahjong.action.form.FindPwdForm;
import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.Common;
import cn.mahjong.beans.common.ModelMappers;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.beans.common.WebUtils;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.CaptchaService;
import cn.mahjong.service.UserService;
import cn.mahjong.viewmodels.UserViewModel;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractBaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserBll userBll;

  @Autowired
  private UserInventoryBll userInventoryBll;

  @Autowired
  private UserService userService;

  @Autowired
  private CaptchaService captchaService;

  /**
   * <p>
   * 注销登录
   * </p>
   *
   * @return ResponseEntity<Object>
   * @action 2017年4月10日 下午12:58:56 描述
   */
  @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
  public ResponseEntity<Object> logOut(HttpServletRequest request, HttpServletResponse response) {
    // 清楚缓存
    userBll.logout(response);
    return new ResponseEntity<>(new ResultBean<>(true, "注销成功", null), HttpStatus.OK);
  }

  /**
   * 获取用户信息（仅限下级）
   *
   * @param account 下级账号
   */
  @RequestMapping(value = "/detail", method = {RequestMethod.GET})
  public ResponseEntity<ResultBean<UserViewModel>> getDetail(String account,
      HttpServletRequest request) {

    if (account == null) {
      return new ResponseEntity<>(new ResultBean<>(false, "用户帐号为必填项", null), HttpStatus.OK);
    }

    CookieUserInfo userInfo = userBll.checkLogin(request);
    if (userInfo == null) {
      return new ResponseEntity<>(new ResultBean<>(false, "用户未登录", null), HttpStatus.OK);
    }

    UserBean user = userBll.get(account);

    if (user == null) {
      return new ResponseEntity<>(new ResultBean<>(false, "用户不存在", null), HttpStatus.OK);
    }

    if (userBll.getParentUsers(user.getCode()).stream()
        .allMatch(u -> u.getCode() != userInfo.getUserId())) {
      return new ResponseEntity<>(new ResultBean<>(false, "无权限", null), HttpStatus.OK);
    }

    UserViewModel vm = ModelMappers.to(user, UserViewModel.class);

    return new ResponseEntity<>(new ResultBean<>(true, "", vm), HttpStatus.OK);
  }

  @GetMapping("/changepwd")
  public String changePwd(Model model) {
    return "/user/changepwd";// 返回视图
  }

  /**
   * <p>
   * 提交修改密码
   * </p>
   *
   * @return ResponseEntity<Object>
   * @action 2017年3月23日 上午11:43:10 描述
   */
  @RequestMapping(value = "/submitChangePassword", method = RequestMethod.POST)
  public ResponseEntity<Object> changePassword(@Valid ChangePwdForm changePwdForm,
      BindingResult bindResult
      , HttpServletRequest request, HttpServletResponse response) {
    LOGGER.debug("changePwdForm:{}", changePwdForm.toString());
    try {
      String errorMsg = validateData(bindResult);
      if (!StringUtils.isEmpty(errorMsg)) {
        LOGGER.info("params error");
        return new ResponseEntity<>(new ResultBean<>(false, errorMsg, null), HttpStatus.OK);
      }

      String regex = "[^\u4e00-\u9fa5]{6,16}";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(changePwdForm.getPassword());
      if (!matcher.matches()) {
        return new ResponseEntity<>(new ResultBean<>(false, "新密码格式不正确", null), HttpStatus.OK);
      }

      //验证确认密码
      if (!changePwdForm.getPassword().equals(changePwdForm.getConfirmPassword())) {
        return new ResponseEntity<>(new ResultBean<>(false, "新密码与确认密码不一致", null), HttpStatus.OK);
      }

      CookieUserInfo cookieUserInfo = userBll.checkLogin(request);
      LOGGER.debug("cookieUserInfo:{}", cookieUserInfo.toString());

      Integer ip = WebUtils.getIntIp(request);

      return new ResponseEntity<>(
          userService.changePwd(cookieUserInfo.getUserId(), changePwdForm.getPassword(), ip),
          HttpStatus.OK);

    } catch (Exception e) {
      LOGGER.error("changePassword 修改密码异常：{}", e);
      return new ResponseEntity<>(new ResultBean<>(false, "修改密码异常", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/findpwdcheckacc", method = RequestMethod.POST)
  public ResponseEntity<Object> checkAccount(@RequestParam(required = false) String captchaCode,
      @RequestParam(required = false) String account, HttpServletRequest request,
      HttpServletResponse response) {
    LOGGER.info("checkAccount start,captchaCode:{},account:{}", captchaCode, account);
    if (StringUtils.isEmpty(captchaCode) || StringUtils.isEmpty(account)) {
      LOGGER.info("params error");
      return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
    }
    //验证验证码
    if (!captchaService.checkCaptcha("check", captchaCode, request)) {
      LOGGER.info("captcha error");
      return new ResponseEntity<>(new ResultBean<>(false, "验证码错误或已过期", null), HttpStatus.OK);
    }

    UserBean userBean = userBll.get(account);
    if (userBean == null) {
      LOGGER.info("account:{} not exit", account);
      return new ResponseEntity<>(new ResultBean<>(false, "用户不存在", null), HttpStatus.OK);
    }

    if (UserRole.HEAD.getValue().equals(userBean.getRole())) {
      LOGGER.info("admin login,account:{} is  head", account);
      return new ResponseEntity<>(new ResultBean<>(false, "您输入的账号暂无权限", null), HttpStatus.OK);
    }

    String phone = userBean.getPhone();
    if (StringUtils.isEmpty(phone)) {
      LOGGER.info("account:{} not exit", account);
      return new ResponseEntity<>(new ResultBean<>(false, "用户暂无绑定手机号", null), HttpStatus.OK);
    }

    return new ResponseEntity<>(new ResultBean<>(true, "", Common.getPhoneMask(phone)),
        HttpStatus.OK);
  }

  @GetMapping("/findpwd")
  public String findPwd(Model model) {
    return "/user/findpwd";
  }

  @RequestMapping(value = "/findpwdsendsms", method = RequestMethod.POST)
  public ResponseEntity<Object> sendFindPwdSms(@RequestParam(required = false) String account,
      HttpServletRequest request, HttpServletResponse response) {
    LOGGER.info("sendFindPwdSms start,account:{}", account);
    try {
      if (StringUtils.isEmpty(account)) {
        LOGGER.info("params error");
        return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
      }

      return new ResponseEntity<>(userService.sendFindPwdSms(account), HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("sendFindPwdSms Exception：{}", e);
      return new ResponseEntity<>(new ResultBean<>(false, "发短信异常", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/findpwdsubmit", method = RequestMethod.POST)
  public ResponseEntity<Object> findPwd(@Valid FindPwdForm findPwdForm, BindingResult bindResult
      , HttpServletRequest request, HttpServletResponse response) {
    LOGGER.info("findPwd strart,findPwdForm:{}", findPwdForm.toString());
    try {
      String errorMsg = validateData(bindResult);
      if (!StringUtils.isEmpty(errorMsg)) {
        LOGGER.info("params error");
        return new ResponseEntity<>(new ResultBean<>(false, errorMsg, null), HttpStatus.OK);
      }

      String regex = "[^\u4e00-\u9fa5]{6,16}";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(findPwdForm.getPassword());
      if (!matcher.matches()) {
        return new ResponseEntity<>(new ResultBean<>(false, "新密码格式不正确", null), HttpStatus.OK);
      }

      //验证确认密码
      if (!findPwdForm.getPassword().equals(findPwdForm.getConfirmPassword())) {
        return new ResponseEntity<>(new ResultBean<>(false, "新密码与确认密码不一致", null), HttpStatus.OK);
      }

      Integer ip = WebUtils.getIntIp(request);
      return new ResponseEntity<>(userService
          .findPwd(findPwdForm.getAccount(), ip, findPwdForm.getPassword(),
              findPwdForm.getSmsCode(), false), HttpStatus.OK);

    } catch (Exception e) {
      LOGGER.error("findPwd Exception：{}", e);
      return new ResponseEntity<>(new ResultBean<>(false, "找回密码异常", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping("/inventory")
  public ResponseEntity<Object> inventory(HttpServletRequest request) {
    CookieUserInfo info = userBll.checkLogin(request);
    UserInventory inventory = userInventoryBll.get(info.getUserId());
    return new ResponseEntity<>(new ResultBean<>(true, "获取成功", inventory), HttpStatus.OK);
  }
}
