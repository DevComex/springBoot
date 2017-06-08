package cn.mahjong.action;

import cn.mahjong.service.SMSService;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;

import cn.mahjong.action.form.NewHeadForm;
import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserBean;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.ModelMappers;
import cn.mahjong.beans.common.PageResultBean;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.HeadService;
import cn.mahjong.viewmodels.UserViewModel;

@Controller
//@Secured({UserRole.Flags.SALESMAN})
@RequestMapping(value = "/head")
public class HeadController extends AbstractBaseController{

  private HeadService headService;
  private UserBll userBll;
  private SMSService smsService;

  @Autowired
  public HeadController(HeadService headService, UserBll userBll, SMSService smsService) {
    this.headService = headService;
    this.userBll = userBll;
    this.smsService = smsService;
  }

  @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
  public String index(HttpServletRequest request) {

    CookieUserInfo user = userBll.checkLogin(request);

    if (!UserRole.SALESMAN.getValue().equals(user.getRole())) {
      return "redirect:/";
    }

    return "head/index";
  }

  @RequestMapping(value = {"/list", "/list/"}, method = RequestMethod.GET)
  public ResponseEntity<PageResultBean<List<UserViewModel>>> getList(String account,
      @RequestParam(defaultValue = "1") Integer pageIndex,
      @RequestParam(defaultValue = "20") Integer pageSize,
      HttpServletRequest request) {

    CookieUserInfo user = userBll.checkLogin(request);

    if (!UserRole.SALESMAN.getValue().equals(user.getRole())) {
      PageResultBean<List<UserViewModel>> notSalesman = new PageResultBean<>(false, "您不是推广员", null);
      return new ResponseEntity<>(notSalesman, HttpStatus.OK);
    }

    if (pageIndex.intValue() < 1 || pageSize.intValue() < 1) {
      return new ResponseEntity<>(new PageResultBean<>(false, "pageIndex 和 pageSize 应为正整数", null),
          HttpStatus.OK);
    }

    List<UserViewModel> list;
    if (StringUtils.isNullOrEmpty(account)) {
      List<UserBean> users = headService.getHeads(user.getUserId(), pageIndex, pageSize);
      list = ModelMappers.toList(users, UserViewModel.class);
    } else if(pageIndex.intValue() == 1){ // 指定用户名查询只能有1个，一定在第一页
      list = Lists.newArrayList();
      UserBean child = headService.getHead(user.getUserId(), account);
      if (child != null) {
        list.add(ModelMappers.to(child, UserViewModel.class));
      }
    } else {
        list = Lists.newArrayList();
    }

    PageResultBean<List<UserViewModel>> succeed = new PageResultBean<>(true, "", list);
    return new ResponseEntity<>(succeed, HttpStatus.OK);
  }

  @RequestMapping(value = {"/new", "/new/"}, method = RequestMethod.GET)
  public String newView(HttpServletRequest request) {

    CookieUserInfo user = userBll.checkLogin(request);

    if (!UserRole.SALESMAN.getValue().equals(user.getRole())) {
      return "redirect:/";
    }

    return "head/new";
  }

  @RequestMapping(value = {"/new", "/new/"}, method = RequestMethod.POST)
  public ResponseEntity<ResultBean<String>> createHead(@Valid NewHeadForm newHead, BindingResult bindResult,
      HttpServletRequest request) {

    CookieUserInfo user = userBll.checkLogin(request);

    if (!UserRole.SALESMAN.getValue().equals(user.getRole())) {
      ResultBean<String> notSalesman = new ResultBean<>(false, "您不是推广员", null);
      return new ResponseEntity<>(notSalesman, HttpStatus.OK);
    }
      
    String valid = validateData(bindResult);
    
    if(!StringUtils.isNullOrEmpty(valid)){
        return new ResponseEntity<>(new ResultBean<>(false, valid, null), HttpStatus.OK);
    }

    UserBean head = ModelMappers.from(newHead, UserBean.class);
    ResultBean<String> result = headService.createHead(head, user, newHead.getVerifyCode());

    if (result.getIsSuccess()) {
      smsService.send(head.getPhone(), "您的密码是" + result.getData()); // 给局头发送密码
      result.setData(null);
    }

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
  
  @RequestMapping(value = {"/newSms/send","/newSms/send/","/newsms/send","/newsms/send/"}, method = RequestMethod.POST)
  public ResponseEntity<ResultBean<Integer>> sendFindPwdSms(String phone,
          HttpServletRequest request, HttpServletResponse response){
      if(StringUtils.isNullOrEmpty(phone)){
          return new ResponseEntity<>(new ResultBean<>(false,"请输入手机号",null) , HttpStatus.OK);
      }
      
      ResultBean<Integer> sendResult = smsService.send(phone, "注册验证码是{verifyCode}");
      
      return new ResponseEntity<>(sendResult, HttpStatus.OK); 
  }
}
