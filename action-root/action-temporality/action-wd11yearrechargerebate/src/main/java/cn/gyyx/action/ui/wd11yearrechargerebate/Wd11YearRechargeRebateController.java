/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wd11yearrechargerebate
  * @作者：chenglong
  * @联系方式：chenglong@gyyx.cn
  * @创建时间：2017年3月29日 上午10:59:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.ui.wd11yearrechargerebate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.beans.enums.ActivityStatus;
import cn.gyyx.action.beans.wd11yearrechargerebate.Constants;
import cn.gyyx.action.beans.wd11yearrechargerebate.RechargeRebateBean;
import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateAcessLogWithBLOBs;
import cn.gyyx.action.beans.wd11yearrechargerebate.RechargerebateHdConfigParamBean;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.wd11yearrechargerebate.RechargeRebateAcessLogBll;
import cn.gyyx.action.bll.wd11yearrechargerebate.RechargeRebateQueryLogBll;
import cn.gyyx.action.service.BaseController;
import cn.gyyx.action.service.CaptchaValidate;
import cn.gyyx.action.service.wd11yearrechargerebate.Wd11YearRechargeRebateService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * <p>
 * 问道11周年充值返现Controller 
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wd11yearrechargerebate")
public class Wd11YearRechargeRebateController extends BaseController{
    private static final Logger log = GYYXLoggerFactory
            .getLogger(Wd11YearRechargeRebateController.class);
    private IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();
    private RechargeRebateQueryLogBll rechargeRebateQueryLogBll = new RechargeRebateQueryLogBll();
    private RechargeRebateAcessLogBll rechargeRebateAcessLogBll = new RechargeRebateAcessLogBll();
    private CaptchaValidate captchaValidate = new CaptchaValidate();//验证码类
    /**
     * 主要业务service
     */
    private Wd11YearRechargeRebateService wd11YearRechargeRebateService = new Wd11YearRechargeRebateService();
    
    /**
     * 
      * <p>
      *    获取用户查询条件是否符合和累计返现记录
      * </p>
      *
      * @action
      *    chenglong 2017年3月29日 上午10:34:50 获取用户查询条件符合结果
      *
      * @param request
      * @return ResultBean<Map<String,String>>
     */
    @RequestMapping(value = "/getdata", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getDataResult(
            String callback,String validCode,HttpServletRequest request) {
        log.info("获取用户查询条件符合结果开始");
        RechargerebateAcessLogWithBLOBs accessLogBean = new RechargerebateAcessLogWithBLOBs();
        String result = null;
        int accessLogCode = -1;
        try {
            //判断用户是否登录
            UserInfo userInfo = cn.gyyx.core.auth.SignedUser
                    .getUserInfo(request);
            if (userInfo == null) {
                return callback + "( "+ DataFormat.objToJson(new ResultBean<>(false, "您没有登录", null)) +" )";
            }
            //判断当前活动的状态，是否在活动时间范围
            ResultBean<ActionConfigPO> hdStatus = this.hdStatus();
            if(!hdStatus.getIsSuccess()){
                return callback + "( "+ DataFormat.objToJson(new ResultBean<>(false, hdStatus.getMessage(), null)) +" )";
            }
            ActionConfigPO actionConfigPO = hdStatus.getData();
            RechargerebateHdConfigParamBean hdConfigParamBean = JSON.parseObject(actionConfigPO.getParas(), RechargerebateHdConfigParamBean.class);
            //插入访问日志 操作日志
            accessLogBean.setCreateTime(new Date());
            accessLogBean.setAccount(userInfo.getAccount());
            accessLogBean.setUserId(userInfo.getUserId()); 
            rechargeRebateAcessLogBll.instert(accessLogBean);
            accessLogCode = accessLogBean.getCode();
            
            //判断是否刷接口 限制次数
            ResultBean<String> isLimit = isLimit(validCode, request, accessLogCode, userInfo,
                hdConfigParamBean);
            if(!isLimit.getIsSuccess()){
                return callback + "( "+ DataFormat.objToJson(new ResultBean<>(false, isLimit.getMessage(), null)) +" )";
            }
            //查询数据
            Map<String, Object> data = getResultData(accessLogCode, userInfo,
                hdConfigParamBean);
            log.info("获取用户查询条件符合结果返回结果:{}", data);
            result = callback + "( "+ DataFormat.objToJson(new ResultBean<>(true, "获取数据成功", data)) +" )";
            accessLogBean = new RechargerebateAcessLogWithBLOBs();
            accessLogBean.setCode(accessLogCode);
            accessLogBean.setResult("获取用户查询条件符合结果返回结果:"+result);
            rechargeRebateAcessLogBll.update(accessLogBean);
            return result;
        } catch (Exception e) {
            log.error(
                "获取数据异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
            if(accessLogCode != -1){
                accessLogBean = new RechargerebateAcessLogWithBLOBs();
                accessLogBean.setCode(accessLogCode);
                accessLogBean.setResult("获取数据接口异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
                rechargeRebateAcessLogBll.update(accessLogBean);
            }
            return callback + "( "+ DataFormat.objToJson(new ResultBean<>(false, "fail", null)) +" )";
        }

    }

    /**
     * 
      * <p>
      *    获取数据
      * </p>
      *
      * @action
      *    chenglong 2017年4月6日 下午4:23:58 描述
      *
      * @param accessLogCode
      * @param userInfo
      * @param hdConfigParamBean
      * @return Map<String,Object>
     */
    public Map<String, Object> getResultData(int accessLogCode,
            UserInfo userInfo,
            RechargerebateHdConfigParamBean hdConfigParamBean) {
        boolean isBindApp;
        boolean rechargeIsGt10;
        boolean levelIsGt60;
        //新服ID
        int serverCode = hdConfigParamBean.getServerCode();
        isBindApp = wd11YearRechargeRebateService.isBindApp(userInfo.getUserId(),accessLogCode);
        rechargeIsGt10 = wd11YearRechargeRebateService.rechargeIsGt10(hdConfigParamBean.isRebateOpen(),hdConfigParamBean.getStartTime(),userInfo.getUserId(),userInfo.getAccount(),serverCode,accessLogCode);
        levelIsGt60 = wd11YearRechargeRebateService.levelIsGt60(hdConfigParamBean.isRebateOpen(),hdConfigParamBean.getJdbcDriverClassName(),
            hdConfigParamBean.getJdbcUrl(),hdConfigParamBean.getJdbcUserName(),hdConfigParamBean.getJdbcPassword(),userInfo.getAccount(),accessLogCode);
        Map<String,Object> data = new HashMap<>();
        data.put("account", this.accountEncrypt(userInfo.getAccount()));//账号
        data.put("isBindApp", isBindApp);//是否绑定app
        data.put("rechargeIsGt10", rechargeIsGt10);//新服充值金额是否大于等于10
        data.put("levelIsGt60", levelIsGt60);//新服等级是否大于等于60
        
        if(isBindApp || rechargeIsGt10 || levelIsGt60){
            //查询返现明细
            RechargeRebateBean rechargeRebateBean = wd11YearRechargeRebateService.getRechargeRebateData(
                userInfo.getAccount(),accessLogCode);
            //插入查询日志
            rechargeRebateQueryLogBll.insert(userInfo.getAccount(), userInfo.getUserId(), rechargeRebateBean.getRechargeSum());
            //设置数据
            data.put("historyRechargeSum", rechargeRebateBean.getRechargeSum() >= 150000 ? -1 : rechargeRebateBean.getRechargeSum());//累计充值金额
            data.put("rechargeRebateBalance", rechargeRebateBean.getRechargeRebateBalance());//达到返利的充值差额
            data.put("rebateSum", rechargeRebateBean.getRebateSum());//返现金额
            data.put("maxRebate", rechargeRebateBean.getMaxRebate());//最大返利
            data.put("salaryDays", rechargeRebateBean.getSalaryDays());//领取工资天数
            data.put("dailyNumber", rechargeRebateBean.getDailyNumber());//每天可领取份数
            data.put("luckyNumber", rechargeRebateBean.getLuckyNumber());//可领取福袋数
            
            SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
            data.put("time", sdf.format(new Date()));//当前时间
        }
        return data;
    }

    /**
     * 
      * <p>
      *    检查是否限制
      * </p>
      *
      * @action
      *    chenglong 2017年4月6日 下午4:23:33 描述
      *
      * @param validCode
      * @param request
      * @param accessLogCode
      * @param userInfo
      * @param hdConfigParamBean
      * @return ResultBean<String>
     */
    public ResultBean<String> isLimit(String validCode,
            HttpServletRequest request, int accessLogCode, UserInfo userInfo,
            RechargerebateHdConfigParamBean hdConfigParamBean) {
        RechargerebateAcessLogWithBLOBs accessLogBean;
        if(hdConfigParamBean.isLimit() && StringUtils.isEmpty(validCode)){
            boolean numIsLimit = wd11YearRechargeRebateService.requestIsLimit(userInfo.getAccount(),this.getIp(request),accessLogCode);
            if(numIsLimit){
                accessLogBean = new RechargerebateAcessLogWithBLOBs();
                accessLogBean.setCode(accessLogCode);
                accessLogBean.setResult("获取数据接口超过限制次数");
                rechargeRebateAcessLogBll.update(accessLogBean);
                return new ResultBean<>(false,"limit",null);
            }
        }
        
        //判断验证码是否合理
        if(!StringUtils.isEmpty(validCode) && captchaValidate.checkCaptcha(validCode, "action", request) != 0){
            accessLogBean = new RechargerebateAcessLogWithBLOBs();
            accessLogBean.setCode(accessLogCode);
            accessLogBean.setResult("获取数据接口验证码错误");
            rechargeRebateAcessLogBll.update(accessLogBean);
            return new ResultBean<>(false,"error_valid",null);
        }
        return new ResultBean<>(true,"","");
    }
    
    /**
     * 
      * <p>
      *    检查活动状态
      * </p>
      *
      * @action
      *    chenglong 2017年4月6日 下午4:23:18 描述
      *
      * @return ResultBean<ActionConfigPO>
     */
    public ResultBean<ActionConfigPO> hdStatus(){
        //判断当前活动的状态，是否在活动时间范围
        ActionConfigPO  actionConfigPO = hdConfigBLL
                .getConfigEntity(Constants.HD_CODE);
        if (actionConfigPO == null || actionConfigPO.getIsDelete()) {
            return new ResultBean<>(false,"活动已下架",null);
        }
        ActivityStatus activityStatus = hdConfigBLL.getStatus(
            actionConfigPO.getHdStart(), actionConfigPO.getHdEnd());
        if(activityStatus.getCode() != 1){
            return new ResultBean<>(false,activityStatus.getMsg(),null);
        }
        return new ResultBean<>(true,"",actionConfigPO);
    }
    
    /**
     * 
      * <p>
      *    加密显示账号
      * </p>
      *
      * @action
      *    chenglong 2017年3月30日 上午10:42:26 加密显示账号
      *
      * @param account
      * @return String
     */
    public String accountEncrypt(String account) {
        if (account == null) {
            return "";
        }
        if (account.length() > 4) {
            return account.substring(0, 2)
                    + "****"
                    + account.substring(account.length() - 2, account.length());
        } 
        return account;
    }
}
