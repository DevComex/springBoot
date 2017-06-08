package cn.mahjong.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mahjong.beans.CookieUserInfo;
import cn.mahjong.beans.UserRole;
import cn.mahjong.beans.common.ResultBean;
import cn.mahjong.bll.UserBll;
import cn.mahjong.service.StatService;

@Controller
@RequestMapping("/stat")
public class StatController extends AbstractBaseController {
  private static final Logger LOGGER = LoggerFactory.getLogger(StatController.class);

    @Autowired
    private StatService statService;
    
    @Autowired
    private UserBll userBll;
    
    @RequestMapping("/salepage")
    public String index() {
        return "/stat/sale";
    }
    
    @RequestMapping("/purchasepage")
    public String index2() {
    	return "/stat/purchase";
    }
    
    /**
     * 
      * <p>
      *    个人销售记录
      * </p>
     * @return 
      *
      * @action
      *
      * @return ResultBean<List<RechargeLog>>
     */
  @RequestMapping(value = "/sale", method = RequestMethod.GET)
  @ResponseBody
  public  ResultBean<Map<String, Object>> personSaleSum(String start, String end, Integer pageIndex,
      Integer pageSize, HttpServletRequest request) {
    CookieUserInfo info = userBll.checkLogin(request);
    if (info == null) {
      return new ResultBean<>(false, "用户未登录", null);
    }
    if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end) || pageIndex == null
        || pageSize == null) {
      return new ResultBean<>(false, "参数错误", null);
    }
    LOGGER.info("userId :{} personSaleSum",info.getUserId());
    try {

      return statService.personSaleSum(start,end,pageIndex,pageSize,info.getUserId());
    } catch (Exception e) {
      LOGGER.error("personSaleSum Exception {}",e);
      return new ResultBean<>(false, "接口异常", null);
    }
  }
    /**
     * 
      * <p>
      *    个人进货记录
      * </p>
      *
      * @action
      *
      * @param start
      * @param end
      * @param pageIndex
      * @param pageSize
      * @param request
      * @return ResultBean<Map<String,Object>>
     */
  @RequestMapping(value = "/purchase", method = RequestMethod.GET)
  @ResponseBody
  public  ResultBean<Map<String, Object>> personPurchaseSum(String start, String end, Integer pageIndex,
      Integer pageSize, HttpServletRequest request) {
    CookieUserInfo info = userBll.checkLogin(request);
    if (info == null) {
      return new ResultBean<>(false, "用户未登录", null);
    }
    if(!info.getRole().equals(UserRole.L1_MANAGER.getValue())&&!info.getRole().equals(UserRole.SALESMAN.getValue())){
      return new ResultBean<>(false, "用户无权限查询进货记录", null);
    }
    if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end) || pageIndex == null
        || pageSize == null) {
      return new ResultBean<>(false, "参数错误", null);
    }
    LOGGER.info("userId :{} personPurchaseSum",info.getUserId());
    try {
      return statService.personPurchaseSum(start,end,pageIndex,pageSize,info.getUserId());
    } catch (Exception e) {
      LOGGER.error("personPurchaseSum Exception {}",e);
      return new ResultBean<>(false, "接口异常", null);
    }
  }
  
}
