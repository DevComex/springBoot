/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wdrankinglist2017
  * @作者：niushuai
  * @联系方式：niushuai@gyyx.cn
  * @创建时间：2017年4月5日 下午2:30:02
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.ui.wdrankinglist2017;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.base.Throwables;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.ActivityStatus;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.lottery.po.ActionPrizesAddressPO;
import cn.gyyx.action.beans.wdrankinglist2017.Constants;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationBean;
import cn.gyyx.action.beans.wdrankinglist2017.DeclarationQueryBean;
import cn.gyyx.action.beans.wdrankinglist2017.LotteryPrizesBean;
import cn.gyyx.action.beans.wdrankinglist2017.RoleBindBean;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.service.BaseController;
import cn.gyyx.action.service.wdrankinglist2017.CommonService;
import cn.gyyx.action.service.wdrankinglist2017.DeclarationService;
import cn.gyyx.action.service.wdrankinglist2017.LotteryPrizesBeanService;
import cn.gyyx.action.service.wdrankinglist2017.RoleBindService;
import cn.gyyx.action.service.wdrankinglist2017.impl.CommonServiceImpl;
import cn.gyyx.action.service.wdrankinglist2017.impl.DeclarationServiceImpl;
import cn.gyyx.action.service.wdrankinglist2017.impl.LotteryPrizesBeanServiceImpl;
import cn.gyyx.action.service.wdrankinglist2017.impl.RoleBindServiceImpl;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 2017问道微信新服冲榜活动(wdrankinglist2017)前台Controller
 * </p>
 * 
 * @author niushuai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wdrankinglist2017")
public class Wdrankinglist2017Controller extends BaseController {

    private static final Logger LOG = GYYXLoggerFactory
            .getLogger(Wdrankinglist2017Controller.class);
    private RoleBindService roleBindService = new RoleBindServiceImpl();
    private DeclarationService declarationService = new DeclarationServiceImpl();
    private LotteryPrizesBeanService lotteryPrizesBeanService = new LotteryPrizesBeanServiceImpl();
    private CommonService commonService = new CommonServiceImpl();

    /**
     * 
     * <p>
     * 测试用action
     * </p>
     *
     * @action laixiancai 2017年4月11日 下午4:35:09 描述
     *
     * @param request
     * @param response
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Integer> test(HttpServletRequest request,
            HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<Integer> result = new ResultBean<Integer>();
        result.setIsSuccess(true);
        result.setMessage("当前的debug是：" + commonService.isDebug());

        return result;
    }

    /**
     * <p>
     * 查询绑定信息
     * </p>
     *
     * @action niushuai 2017年4月5日 下午6:41:06 描述
     *
     * @return ResultBean<RoleBindBean>
     */
    @RequestMapping(value = "/rolebind", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<RoleBindBean> rolebind(String openId,
            HttpServletRequest request, HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<RoleBindBean> result = new ResultBean<RoleBindBean>();
        result.setIsSuccess(false);
        result.setMessage("查询失败！");
        if (openId == null || "".equals(openId.trim())) {
            result.setIsSuccess(false);
            result.setMessage("缺少openId！");
            return result;
        }
        try {
            RoleBindBean roleBindBean = roleBindService
                    .getRoleBindBeanByOpenId(openId);
            if (roleBindBean != null) {
                result.setIsSuccess(true);
                result.setMessage("查询成功！");
                result.setData(roleBindBean);
            } else {
                result.setIsSuccess(false);
                result.setMessage("您还没有绑定账号！");
            }
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "查询账号绑定信息异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询失败！请重试！");
        }
        return result;
    }

    /**
     * <p>
     * 绑定账号
     * </p>
     *
     * @action niushuai 2017年4月5日 下午7:09:07 描述
     *
     * @param roleBindBean
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/rolebind", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> rolebind(@Valid RoleBindBean roleBind,
            BindingResult bindingResult, HttpServletRequest request,
            HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }
        ResultBean<String> result = new ResultBean<String>();
        // 是否在活动期内
        ActivityStatus status = new DefaultHdConfigBLL()
                .getStatus(Constants.HD_CODE);
        if (status != ActivityStatus.IS_NORMAL) {
            result.setProperties(false, "不在活动期内！", null);
            return result;
        }
        if (roleBind.getOpenId()== null || "".equals(roleBind.getOpenId().trim())) {
            result.setProperties(false, "openId不能为空！", null);
            return result;
        }
        result.setIsSuccess(false);
        result.setMessage("操作失败！");
        // 获取参数验证信息
        if (bindingResult.hasErrors()) {
            result.setIsSuccess(false);
            result.setMessage(
                bindingResult.getFieldError().getDefaultMessage());
            return result;
        }
        UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
        if (null == userInfo) {
            result.setIsSuccess(false);
            result.setMessage("您没有登录！");
            return result;
        }
        result = roleBindService.bindRoleInfo(roleBind, userInfo);
        return result;
    }

    /**
     * <p>
     * 添加宣言
     * </p>
     *
     * @action niushuai 2017年4月7日 下午3:10:57 描述
     *
     * @param declaration
     * @param bindingResult
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/declaration", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addDeclaration(@Valid DeclarationBean declaration,
            BindingResult bindingResult, HttpServletRequest request,
            HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }
        ResultBean<String> result = new ResultBean<String>();
        // 是否在活动期内
        ActivityStatus status = new DefaultHdConfigBLL()
                .getStatus(Constants.HD_CODE);
        if (status != ActivityStatus.IS_NORMAL) {
            result.setProperties(false, "不在活动期内！", null);
            return result;
        }
        result.setIsSuccess(false);
        result.setMessage("添加失败！");
        try {
            // 获取参数验证信息
            if (bindingResult.hasErrors()) {
                result.setIsSuccess(false);
                result.setMessage(
                    bindingResult.getFieldError().getDefaultMessage());
                return result;
            }
            result = declarationService.addDeclaration(declaration);
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "查询账号绑定信息异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("添加失败！请重试！");
        }
        return result;
    }

    /**
     * <p>
     * 查询宣言
     * </p>
     * 
     * @action niushuai 2017年4月7日 下午3:36:58 描述
     *
     * @param openId
     * @param request
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/declaration", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<DeclarationBean> getDeclaration(String openId,
            String aopenId, HttpServletRequest request,
            HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<DeclarationBean> result = new ResultBean<DeclarationBean>();
        result.setIsSuccess(false);
        result.setMessage("查询失败！");
        if (openId == null || "".equals(openId.trim())) {
            result.setProperties(false, "缺少openId！", null);
            return result;
        }
        try {
            DeclarationBean declarationBean = null;
            if (aopenId != null && !"".equals(aopenId.trim())) {
                declarationBean = declarationService
                        .getDeclarationByOpenId(aopenId);
            } else {
                declarationBean = declarationService
                        .getDeclarationByOpenId(openId);
            }
            if (declarationBean == null) {
                result.setProperties(false, "您还没有填写宣言！", null);
                result.setStateCode(Constants.DECLARATION_STATUS_UNWRITE);
                return result;
            }
            result.setStateCode(declarationBean.getStatus());
            result.setProperties(true, "查询成功！", declarationBean);
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "查询宣言异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询宣言失败！请重试！");
            result.setStateCode(500);
        }
        return result;
    }

    /**
     * <p>
     * 获取用户的邮寄地址
     * </p>
     *
     * @action niushuai 2017年4月8日 下午1:11:13 描述
     *
     * @param openId
     * @return ResultBean<DeclarationBean>
     */
    @RequestMapping(value = "/address", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<ActionPrizesAddressPO> address(String openId,
            HttpServletRequest request, HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<ActionPrizesAddressPO> result = new ResultBean<ActionPrizesAddressPO>();
        result.setIsSuccess(false);
        result.setMessage("查询失败！");
        if (openId == null || "".equals(openId.trim())) {
            result.setIsSuccess(false);
            result.setMessage("缺少openId！");
            return result;
        }
        try {
            result = roleBindService.getUserAddress(openId);
        } catch (Exception e) {
            LOG.error(Constants.HD_NAME + "查询地址异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询地址失败！请重试！");
        }
        return result;
    }

    /**
     * <p>
     * 更新邮寄地址
     * </p>
     *
     * @action niushuai 2017年4月8日 下午2:38:21 描述
     *
     * @param address
     * @param openId
     * @param bindingResult
     * @return ResultBean<String>
     */
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> address(ActionPrizesAddressPO address,
            String openId, HttpServletRequest request,
            HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }
        ResultBean<String> result = new ResultBean<String>();
        // 是否在活动期内
        ActivityStatus status = new DefaultHdConfigBLL()
                .getStatus(Constants.HD_CODE);
        if (status != ActivityStatus.IS_NORMAL) {
            result.setProperties(false, "不在活动期内！", null);
            return result;
        }
        if (!StringUtils.isNotEmpty(address.getUserAddress())) {
            result.setIsSuccess(false);
            result.setMessage("地址不能为空！");
            return result;
        }
        if (!StringUtils.isNotEmpty(address.getUserName())) {
            result.setIsSuccess(false);
            result.setMessage("名字不能为空！");
            return result;
        }
        if (!StringUtils.isNotEmpty(address.getUserPhone())) {
            result.setIsSuccess(false);
            result.setMessage("电话不能为空！");
            return result;
        }
        result = roleBindService.addAddress(address, openId);
        return result;
    }

    /**
     * <p>
     * 查询抽奖次数
     * </p>
     *
     * @action niushuai 2017年4月10日 下午2:02:12 描述
     *
     * @param openId
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Integer> lottery(String openId,
            HttpServletRequest request, HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<Integer> result = new ResultBean<Integer>();
        result.setIsSuccess(false);
        if (openId == null || "".equals(openId.trim())) {
            result.setProperties(false, "缺少openId", 0);
            return result;
        }
        result.setMessage("查询异常！");
        result = roleBindService.getLotteryTimes(openId);
        return result;
    }

    /**
     * 
     * <p>
     * 前台宣言排行页面查询宣言数据
     * </p>
     *
     * @action laixiancai 2017年4月9日 下午7:06:47 描述
     *
     * @param queryBean
     * @param request
     * @param response
     * @return ResultBean<Object>
     */
    @RequestMapping(value = "/declarations", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> queryDeclarations(DeclarationQueryBean queryBean,
            HttpServletRequest request, HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<Object> result = new ResultBean<>();
        if (queryBean.getType() == null) {
            result.setProperties(false, "排序类型不能为空", null);
            return result;
        } else if (queryBean.getPageIndex() == null) {
            result.setProperties(false, "pageIndex参数不能为空", null);
            return result;
        } else if (queryBean.getPageSize() == null) {
            result.setProperties(false, "pageSize参数不能为空", null);
            return result;
        } else if (queryBean.getPageIndex() < 1) {
            result.setProperties(false, "页索引由1开始", null);
            return result;
        } else if (queryBean.getPageSize() < 1
                || queryBean.getPageSize() > 50) {
            result.setProperties(false, "分页大小必须在1-50之间！", 0);
            return result;
        }

        declarationService.queryDeclarations(queryBean, result);
        return result;
    }

    /**
     * <p>
     * 用户抽奖
     * </p>
     *
     * @action niushuai 2017年4月10日 下午9:00:43 描述
     *
     * @param openId
     * @param request
     * @param response
     * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping(value = "/lottery", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<UserLotteryBean> doLottery(
            @RequestParam("openId") String openId, HttpServletRequest request,
            HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();
        if (openId == null || openId.equals("")) {
            result.setIsSuccess(false);
            result.setMessage("openId不能为空！");
            return result;
        }

        // 是否在活动期内
        ActivityStatus status = new DefaultHdConfigBLL()
                .getStatus(Constants.HD_CODE);
        if (status != ActivityStatus.IS_NORMAL) {
            result.setIsSuccess(false);
            result.setMessage("不在活动期内！");
            return result;
        }

        return lotteryPrizesBeanService.doLottery(openId, getIp(request));
    }

    /**
     * 
     * <p>
     * 获取抽奖页面滚动中奖列表数据
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午11:16:23 描述
     *
     * @param size
     * @return ResultBean<LotteryPrizesBean>
     */
    @RequestMapping(value = "/prizes/show", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<LotteryPrizesBean> prizesShow(HttpServletRequest request,
            HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<LotteryPrizesBean> result = new ResultBean<LotteryPrizesBean>();
        result.setIsSuccess(false);
        result.setMessage("查询失败！");
        try {
            List<LotteryPrizesBean> list = lotteryPrizesBeanService
                    .getLotteryList();
            result.setRows(list);
            result.setIsSuccess(true);
            result.setMessage("查询成功！");
        } catch (Exception e) {
            LOG.error("查询流动中奖信息异常！{}", Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询失败！");
        }
        return result;
    }

    /**
     * 
     * <p>
     * 根据openId获取当前玩家的中奖记录
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午11:15:57 描述
     *
     * @param request
     * @return ResultBean<LotteryPrizesBean>
     */
    @RequestMapping(value = "/prizes", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<LotteryPrizesBean> getPrizes(
            @RequestParam("openId") String openId, HttpServletRequest request,
            HttpServletResponse response) {
        // 是否是微信浏览器
        if (!wxCheck(request, response)) {
            return null;
        }

        ResultBean<LotteryPrizesBean> result = new ResultBean<LotteryPrizesBean>();
        if (openId == null || openId.equals("")) {
            result.setIsSuccess(false);
            result.setMessage("openId不能为空！");
            return result;
        }
        try {
            // 检查玩家是否已完成报名
            ResultBean<Boolean> resultCheckApply = roleBindService
                    .checkApply(openId);
            if (!resultCheckApply.getIsSuccess()) {
                result.setIsSuccess(false);
                result.setMessage(resultCheckApply.getMessage());
                return result;
            }

            List<LotteryPrizesBean> list = lotteryPrizesBeanService
                    .getLotteryListByOpenId(openId);
            result.setRows(list);
            result.setIsSuccess(true);
            result.setMessage("查询成功！");
        } catch (Exception e) {
            LOG.error("查询 当前用户所有的中奖记录异常！{}",
                Throwables.getStackTraceAsString(e));
            result.setIsSuccess(false);
            result.setMessage("查询失败！请重试！");
        }
        return result;
    }

    /**
     * 
     * <p>
     * 检查是否是微信浏览器
     * </p>
     *
     * @action laixiancai 2017年4月10日 下午3:02:50 描述
     *
     * @param request
     * @param response
     * @return boolean
     */
    private boolean wxCheck(HttpServletRequest request,
            HttpServletResponse response) {
        boolean isPassCheck = true;
        if (commonService.isDebug()) {
            return isPassCheck;
        }
        
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") == -1 || ua.indexOf("windows") > -1) {// 不是微信浏览器
            try {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter()
                        .write(String.valueOf("<h1>此功能只能在微信浏览器中使用！</h1>"));
                response.getWriter().close();
                return !isPassCheck;
            } catch (IOException e) {
                LOG.error(Constants.HD_NAME + "wxCheck异常！{}",
                    Throwables.getStackTraceAsString(e));
            }
        }
        return isPassCheck;
    }

}
