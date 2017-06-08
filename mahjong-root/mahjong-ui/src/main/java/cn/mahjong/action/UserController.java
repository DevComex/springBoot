package cn.mahjong.action;

import cn.mahjong.action.form.EditProfileForm;
import cn.mahjong.model.Profile;
import cn.mahjong.service.HeadService;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.mahjong.action.form.ChangePwdForm;
import cn.mahjong.action.form.FindPwdForm;
import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserInventory;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.AES;
import cn.mahjong.beans.common.Common;
import cn.mahjong.beans.common.CookieUtil;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.beans.common.WebUtils;
import cn.mahjong.bll.UserBll;
import cn.mahjong.bll.UserInventoryBll;
import cn.mahjong.service.CaptchaService;
import cn.mahjong.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractBaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  
  private static final String CHANGE_PHONE_COOKIE_NAME = "MAHJONG_CHANGE_OPERTING_STEPS";
  
  /**
   * 用户操作步骤第一步
   */
  public static final String USER_OPERATING_STEPS_FIRST = "first_step";
  
  @Value("${send.change.phone.sms.content}")
  private String changePhoneSmsContent;
  
  @Value("${account.cookie.secret.key}")
  private String cookieKey;

  @Autowired
  private UserBll userBll;

  @Autowired
  private UserInventoryBll userInventoryBll;

  @Autowired
  private UserService userService;

  @Autowired
  private CaptchaService captchaService;

  @Autowired
  private HeadService headService;

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
    // 清除缓存
    userBll.logout(response);
    return new ResponseEntity<>(new ResultBean<>(true, "注销成功", null), HttpStatus.OK);
  }

  @RequestMapping("/inventory")
  public ResponseEntity<Object> inventory(HttpServletRequest request) {
    CookieUserInfo info = userBll.checkLogin(request);
    UserInventory inventory = userInventoryBll.get(info.getUserId());
    return new ResponseEntity<>(new ResultBean<>(true, "获取成功", inventory), HttpStatus.OK);
  }

  @RequestMapping(value = {"/", "/index"})
  public String indexView() {
    return "/user/index";
  }

  @RequestMapping("/profile")
  public ResponseEntity<Object> profile(HttpServletRequest request) {
    CookieUserInfo info = userBll.checkLogin(request);
    UserBean bean = userBll.get(info.getUserId());
    if (bean == null) {
      LOGGER.info("UserId: {} not exist", info.getUserId());
      return new ResponseEntity<>(new ResultBean<>(false, "用户不存在", null), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResultBean<>(true, "获取成功", new Profile(
        bean.getGameUserId(),
        bean.getWechatId(),
        bean.getName(),
        Common.getPhoneMask(bean.getPhone()),
        bean.getProvince(),
        bean.getCity(),
        bean.getAddress(),
        bean.getCreateTime()
    )), HttpStatus.OK);
  }

  @RequestMapping(value = "/profile", method = RequestMethod.POST)
  public ResponseEntity<Object> editProfile(EditProfileForm form, HttpServletRequest request) {
    LOGGER.info("开始修改局头用户信息: " + form);
    if (form.getGameUserId() == null || form.getGameUserId() <= 0
        || form.getWechatId() == null || form.getWechatId().trim().isEmpty()
        || form.getName() == null || form.getName().trim().isEmpty()
        || form.getProvince() == null || form.getProvince().trim().isEmpty()
        || form.getCity() == null || form.getCity().trim().isEmpty()
        || form.getAddress() == null || form.getAddress().trim().isEmpty()) {
      LOGGER.info("修改局头信息结束，参数错误");
      return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
    }

    CookieUserInfo info = userBll.checkLogin(request);
    if (info == null) {
      LOGGER.info("修改局头信息结束，用户未登录");
      return new ResponseEntity<>(new ResultBean<>(false, "用户未登录", null), HttpStatus.OK);
    }
    ResultBean<Void> resultBean = headService.update(info.getUserId(), form.getGameUserId(),
        form.getWechatId(), form.getName(), form.getProvince(), form.getCity(),
        form.getAddress());
    LOGGER.info("修改局头用户信息结束: " + resultBean);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
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
        LOGGER.info("params error:{}", errorMsg);
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
      LOGGER.error("changePassword Exception：{}", e);
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

    if (!UserRole.HEAD.getValue().equals(userBean.getRole())) {
      LOGGER.info("head login,account:{} is not head", account);
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
              findPwdForm.getSmsCode(), true)
          , HttpStatus.OK);

    } catch (Exception e) {
      LOGGER.error("findPwd Exception：{}", e);
      return new ResponseEntity<>(new ResultBean<>(false, "找回密码异常", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/changephone")
  public String changePhoneView(Model model, HttpServletRequest request) {
    LOGGER.info("进入更换手机");
    //验证登录
    CookieUserInfo cookieUserInfo = userBll.checkLogin(request);
    LOGGER.debug("cookieUserInfo:{}", cookieUserInfo.toString());

    UserBean userBean = userBll.get(cookieUserInfo.getUserId());
    if (userBean != null) {
      String phone = userBean.getPhone();
      model.addAttribute("phone", Common.getPhoneMask(phone));
    }
    return "/user/changephone";// 返回视图
  }

  /**
   *
   * <p>
   *    给旧手机号发送短信
   * </p>
   *
   * @action
   *     2017年5月31日 上午11:03:09 描述
   *
   * @param request
   * @param response
   * @return ResponseEntity<Object>
   */
  @RequestMapping(value = "/cpsendfirst", method = RequestMethod.POST)
  public ResponseEntity<Object> sendChangePhoneOldSms(HttpServletRequest request, HttpServletResponse response) {
    LOGGER.info("sendChangePhoneOldSms start");
    try {
      //验证登录
      CookieUserInfo cookieUserInfo = userBll.checkLogin(request);
      LOGGER.debug("cookieUserInfo:{}", cookieUserInfo.toString());

      return new ResponseEntity<>(userService.sendChangePhoneSms(cookieUserInfo.getUserId(),changePhoneSmsContent), HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("sendFindPwdSms Exception：{}", e);
      return new ResponseEntity<>(new ResultBean<>(false, "发短信异常", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   *
   * <p>
   *    给新手机号发送短信
   * </p>
   *
   * @action
   *     2017年5月31日 上午11:03:37 描述
   *
   * @param phone
   * @param request
   * @param response
   * @return ResponseEntity<Object>
   */
  @RequestMapping(value = "/cpsendsecond", method = RequestMethod.POST)
  public ResponseEntity<Object> sendChangePhoneSms(@RequestParam(required = false) String phone,
      @RequestParam(required = false) String captchaCode,
      HttpServletRequest request, HttpServletResponse response) {
    LOGGER.info("sendChangePhoneOldSms start,phone:{},captchaCode:{}",phone,captchaCode);
    try {
      if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(captchaCode)){
        LOGGER.info("params error");
        return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
      }

      String regex = "^1[34578]\\d{9}$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(phone);
      if (!matcher.matches()) {
        return new ResponseEntity<>(new ResultBean<>(false, "手机号格式不正确", null), HttpStatus.OK);
      }
      
      if(!captchaService.checkCaptcha("cpssms", captchaCode, request)){
          LOGGER.info("captcha error");
          return new ResponseEntity<>(new ResultBean<>(false,"验证码错误或已过期",null) , HttpStatus.OK);
      }

      //验证登录
      CookieUserInfo cookieUserInfo = userBll.checkLogin(request);
      LOGGER.debug("cookieUserInfo:{}", cookieUserInfo.toString());

      return new ResponseEntity<>(userService.sendChangePhoneSms(cookieUserInfo.getUserId(),phone,changePhoneSmsContent), HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("sendFindPwdSms Exception：{}", e);
      return new ResponseEntity<>(new ResultBean<>(false, "发短信异常", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /**
   * <p>
   * 更换手机第一步
   * </p>
   *
   * @param smsCode
   * @param request
   * @param response
   * @return ResponseEntity<Object>
   * @action 
   */
  @RequestMapping(value = "/submitFirstChangePhone", method = RequestMethod.POST)
  public ResponseEntity<Object> firstChangePhone(@RequestParam(required = false) String smsCode
          , HttpServletRequest request, HttpServletResponse response) {
      LOGGER.info("firstChangePhone,smsCode:{}", smsCode);
      try {
          if (StringUtils.isEmpty(smsCode)) {
              LOGGER.info("firstChangePhone end");
              return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
          }

          //验证登录
          CookieUserInfo cookieUserInfo = userBll.checkLogin(request);
          LOGGER.debug("cookieUserInfo:{}", cookieUserInfo.toString());

          ResultBean<Object> resultBean = userService.changePhoneFirst(smsCode, cookieUserInfo.getUserId());

          if (resultBean.getIsSuccess()) {
              //记录cookie 第一步通过
              Map<String, Object> cookieValueMap = new HashMap<>();
              cookieValueMap.put("userId", cookieUserInfo.getUserId());
              cookieValueMap.put("currentOperation", USER_OPERATING_STEPS_FIRST);
              
              LOGGER.debug("cookie name:{},value:{}", CHANGE_PHONE_COOKIE_NAME, cookieValueMap);
              String cookieValue = new ObjectMapper().writeValueAsString(cookieValueMap);
              String value = AES.encrypt(cookieValue, cookieKey);
              CookieUtil.addCookie(response, CHANGE_PHONE_COOKIE_NAME, value);
          }
          return new ResponseEntity<>(resultBean, HttpStatus.OK);

      } catch (Exception e) {
          LOGGER.error("firstChangePhone 更换手机第一步异常：{}", e);
          return new ResponseEntity<>(new ResultBean<>(false, "更换手机第一步异常", null), HttpStatus.BAD_REQUEST);
      }
  }
  
  /**
   * <p>
   * 修改手机第二步
   * </p>
   *
   * @param changePhoneForm
   * @param bindResult
   * @param request
   * @param response
   * @return ResponseEntity<Object>
   * @action 
   */
  @RequestMapping(value = "/submitSecondChangePhone", method = RequestMethod.POST)
  public ResponseEntity<Object> secondChangePhone(@RequestParam(required = false) String phone,
          @RequestParam(required = false) String smsCode,
          HttpServletRequest request, HttpServletResponse response) {
      LOGGER.info("secondChangePhone start,phone:{},smsCode:{}", phone,smsCode);

      if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsCode)){
          LOGGER.info("params error");
          return new ResponseEntity<>(new ResultBean<>(false, "参数错误", null), HttpStatus.OK);
      }
      
      String regex = "^1[34578]\\d{9}$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(phone);
      if (!matcher.matches()) {
        return new ResponseEntity<>(new ResultBean<>(false, "手机号格式不正确", null), HttpStatus.OK);
      }
      
      //验证登录
      CookieUserInfo cookieUserInfo = userBll.checkLogin(request);
      LOGGER.debug("cookieUserInfo:{}", cookieUserInfo.toString());
      
      if (!checkChangePhoneOperatingStep(cookieUserInfo.getUserId(), request, CHANGE_PHONE_COOKIE_NAME)) {
          return new ResponseEntity<>(new ResultBean<>(false, "第一步操作不正确，请重新验证", null), HttpStatus.OK);
      }

      int ip = WebUtils.getIntIp(request);
      ResultBean<Object> resultBean = userService.changePhoneSecond(phone, smsCode, cookieUserInfo.getUserId(), ip);
      if (resultBean.getIsSuccess()) {
          CookieUtil.removeCookie(response, CHANGE_PHONE_COOKIE_NAME);
      }
      return new ResponseEntity<>(resultBean, HttpStatus.OK);

  }
  
  /**
   * <p>
   * 验证用户上一步操作结果
   * </p>
   *
   * @param userId
   * @param request
   * @param cookieName
   * @return boolean
   * @action 
   */
  private boolean checkChangePhoneOperatingStep(int userId, HttpServletRequest request, String cookieName) {
      try {
          //验证cookie第一步的值
          Cookie cookie = CookieUtil.getCookie(request, cookieName);
          if (cookie == null) {
              LOGGER.info("没有获取到cookie值,name:{}", cookieName);
              return false;
          }
          String cookieValue = AES.decrypt(URLDecoder.decode(cookie.getValue()), cookieKey);
          if (StringUtils.isEmpty(cookieValue)) {
              LOGGER.info("操作cookievalue 不正确");
              return false;
          }
          LOGGER.debug("cookieValue:{}", cookieValue);

          ObjectMapper objectMapper = new ObjectMapper();
          Map<String, Object> operatingStep = objectMapper.readValue(cookieValue, Map.class);
          if (!operatingStep.containsKey("userId")
                  || userId != Integer.parseInt(operatingStep.get("userId").toString())
                  || !USER_OPERATING_STEPS_FIRST.equals(operatingStep.get("currentOperation"))) {
              LOGGER.info("非当前登录用户操作,或者非第一步操作结束");
              return false;
          }
          return true;
      } catch (Exception e) {
          LOGGER.error("checkOperatingStep Exception:{}", e);
          return false;
      }

  }
}
