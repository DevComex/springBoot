/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月16日 下午2:17:09
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.ui.wechatroulette;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.beans.wdpkforecast.WdPkRoleBindBean;
import cn.gyyx.action.beans.wechatroulette.Constant;
import cn.gyyx.action.bll.wechatroulette.RouletteBindBll;
import cn.gyyx.action.cache.MemcacheUtil;
import cn.gyyx.action.service.BaseController;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceInsuranceAgent;
import cn.gyyx.action.service.wechatroulette.WechatRouletteService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import net.sf.json.JSONObject;

/**
 * <p>
 * 微信大转盘控制器
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
@Controller
@RequestMapping("/wechatRoulette")
public class WechatRouletteController extends BaseController {
    private static final Logger logger = GYYXLoggerFactory
            .getLogger(WechatRouletteController.class);
    private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
    private RouletteBindBll rouletteBindBll = new RouletteBindBll();
    private XMemcachedClientAdapter memcachedClientAdapter = MemcacheUtil
            .getActionMemcache();
    private WechatRouletteService wechatRouletteService = new WechatRouletteService();

    /**
     * 
     * <p>
     * 获取问道对应netType下的区服信息
     * </p>
     *
     * @action caishuai 2017年3月16日 下午2:25:13 描述
     *
     * @param typeCode
     *            线路
     * @return ResultBean<List<ServerBean>>
     */
    @RequestMapping(value = "/getServers", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultBean<List<ServerBean>> getServers(
            @RequestParam(value = "netType") String typeCode) {

        ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
                true, "查询服务器失败", null);
        List<ServerBean> serverList = new ArrayList<ServerBean>();
        String memkey = "actioncode" + Constant.ACTION_CODE
                + "_getserver_gameId" + Constant.Game_Id + "_netype"
                + typeCode;
        logger.info("wechat_roulette_getserver_typeCode:{},memkey:{}", typeCode,
            memkey.toLowerCase());
        List<ServerBean> list = memcachedClientAdapter.get(memkey.toLowerCase(),
            List.class);
        if (list == null) {
            List<Value> serversList = callWebApiAgent
                    .getAllServerByNetTypeCode(typeCode);

            for (Value value : serversList) {
                ServerBean server = new ServerBean(value.getCode(),
                        value.getServerName());
                serverList.add(server);
            }
            memcachedClientAdapter.set(memkey.toLowerCase(),
                Constant.Three_Mins, serverList);
        } else {
            serverList.addAll(list);
        }
        result.setProperties(true, "查询服务器成功", serverList);
        logger.info("wechat_roulette_getserver_result:{}", result);
        return result;
    }

    /**
     * 
     * <p>
     * 角色绑定
     * </p>
     *
     * @action caishuai 2017年3月22日 上午10:45:16 描述
     *
     * @param wdPkRoleBindBean
     *            用户绑定信息实体
     * @param request
     * @param br
     *            参数验证异常
     * @param OpenId
     *            openid
     * @return
     * @throws BindException
     *             ResultBean<WdPkRoleBindBean>
     */
    @RequestMapping("/roleBind")
    @ResponseBody
    public ResultBean<WdPkRoleBindBean> roleBind(
            @ModelAttribute @Validated WdPkRoleBindBean wdPkRoleBindBean,
            HttpServletRequest request, BindingResult br,
            @RequestParam("OpenId") String openId) throws BindException {
        logger.info("wdPkRoleBindBean:" + wdPkRoleBindBean);
        ResultBean<WdPkRoleBindBean> result = new ResultBean<WdPkRoleBindBean>(
                false, "", null);
        logger.info("wechat_roulette_addRole:{},openid:{}", wdPkRoleBindBean,
            openId);
        // 接收Cookie中的信息
        UserInfo userInfo = SignedUser.getUserInfo(request);
        if (userInfo == null) {
            result.setIsSuccess(false);
            result.setMessage("请您先登录");
            return result;
        }
        // 查询活动开始结束时间
        ResultBean<Object> activityStatus = getActivityStatus(
            Constant.ACTION_CODE);
        if (!activityStatus.getIsSuccess()) {
            result.setProperties(false, activityStatus.getMessage(), null);
            return result;
        }
        // 查询是否有对应角色
        String roleInfo = CallWebServiceInsuranceAgent.getRoleInfo(
            userInfo.getAccount(), wdPkRoleBindBean.getServerId());
        JSONObject jsonObj = JSONObject.fromObject(roleInfo);

        String count = jsonObj.getString("Count");
        if (StringUtils.equals("0", count)) {
            if (StringUtils.equalsIgnoreCase(jsonObj.getString("ErrorMessage"),
                "该玩家在此服务器访问次数过多，请稍后访问")) {
                result.setMessage("您在该服务器绑定角色尝试次数过多，请休息一会再试!");
            } else {
                result.setMessage("您尚未在该服务器激活角色!");
            }
            result.setIsSuccess(false);
            return result;
        } else {
            wdPkRoleBindBean.setAccount(openId);
            wdPkRoleBindBean.setActionCode(Constant.ACTION_CODE);
            wdPkRoleBindBean.setUserId(userInfo.getUserId());
            wdPkRoleBindBean.setCreateAt(new Date());
            wdPkRoleBindBean.setRoleName(userInfo.getAccount());
            result = wechatRouletteService.addrole(wdPkRoleBindBean);
            logger.info("wechat_roulette_openid:{},roleBind返回参数：{}", openId,
                result);
            return result;
        }

    }

    /**
     * 
     * <p>
     * 检测当前状态
     * </p>
     *
     * @action caishuai 2017年3月21日 下午6:18:59 描述
     *
     * @param request
     * @param openId
     * @return Map<String,Object>
     */
    @RequestMapping("/checkBind")
    @ResponseBody
    public Map<String, Object> checkStatus(HttpServletRequest request,
            @RequestParam("OpenId") String openId) {
        HashMap<String, Object> map = new HashMap<>();
        logger.info("wechat_roulette_检查状态获取绑定和抽奖次数传入参数OpenId:{}" + openId);

        map.put("isLogin", false);
        map.put("isBind", false);
        map.put("BindInfo", null);
        // 判断是否绑定过。isBind
        try {
            WdPkRoleBindBean role = wechatRouletteService
                    .getUserInfoByOpenId(openId, Constant.ACTION_CODE);
            if (role != null) {
                map.put("isLogin", true);
                map.put("isBind", true);
                map.put("BindInfo", role);
            }
        } catch (Exception e) {
            logger.error("wechat_roulette_获取绑定信息error,{}",
                Throwables.getStackTraceAsString(e));
        }
        logger.info("wechat_roulette_检查状态获取绑定和抽奖次数返回参数:{}" + map);
        return map;
    }

    /**
     * 
     * <p>
     * 获取每日抽奖次数
     * </p>
     *
     * @action caishuai 2017年3月22日 下午3:23:13 描述
     *
     * @param request
     * @param openId
     * @return ResultBean<Integer>
     */
    @RequestMapping("/getScore")
    @ResponseBody
    public ResultBean<Integer> getScore(HttpServletRequest request,
            @RequestParam("OpenId") String openId) {
        ResultBean<Integer> result = new ResultBean<>(false, "初始化抽奖机会错误，请重试",
                null);
        try {
            int score = wechatRouletteService.getScore(openId,
                Constant.ACTION_CODE);
            if (score >= 0) {
                result.setProperties(true, "初始化每日抽奖成功", score);
            } else if (score == -2) {
                result.setProperties(false, "未检测到绑定信息，请先绑定区服", score);
            }
        } catch (Exception e) {
            logger.error("wechat_roulette_获取每日抽奖次数异常：{}",
                Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 
     * <p>
     * 插入地址信息
     * </p>
     *
     * @action caishuai 2017年3月22日 下午1:44:33 描述
     *
     * @param address
     * @param response
     * @param request
     * @param OpenId
     * @return ResultBean<Integer>
     */
    @RequestMapping(value = "/setAddress")
    @ResponseBody
    public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address,
            @RequestParam("OpenId") String openId) {
        ResultBean<Integer> result = new ResultBean<>(false, "添加地址信息失败", 0);

        /**
         * 验证用户姓名
         */
        if (address.getUserName() == null
                || address.getUserName().length() > 10) {
            result.setIsSuccess(false);
            result.setMessage("姓名格式不对");
            return result;
        }
        /**
         * 验证电话格式
         */
        String phoneTemplate = ("^[1][0-9]{10}$");
        if (address.getUserPhone() == null
                || !address.getUserPhone().matches(phoneTemplate)) {
            result.setIsSuccess(false);
            result.setMessage("电话格式不正确");
            return result;
        }
        /**
         * 验证地址长度
         */
        if (address.getUserAddress() == null
                || address.getUserAddress().length() > 200) {
            result.setIsSuccess(false);
            result.setMessage("请正确填写地址");
            return result;
        }
        /**
         * 验证活动时间
         */
        ResultBean<Object> activityStatus = getActivityStatus(
            Constant.ACTION_CODE);
        if (!activityStatus.getIsSuccess()) {
            result.setIsSuccess(false);
            result.setMessage(activityStatus.getMessage());
            return result;
        }
        logger.info(
            "wechat_roulette_input_openId:{},setAddressResult=>AddressBean{}",
            openId, address);
        try {
            result = wechatRouletteService.setAddress(openId,
                Constant.ACTION_CODE, address);
        } catch (Exception e) {
            logger.error("wechat_roulette_openId:{},setAddress_error:{}",
                openId, Throwables.getStackTraceAsString(e));
        }
        logger.info(
            "wechat_roulette_output_openId:{},setAddressResult=>AddressBean{}",
            openId, result);
        return result;
    }

    /**
     * 
     * <p>
     * 获取某个用户中奖信息的控制器
     * </p>
     *
     * @action caishuai 2017年3月21日 下午6:18:45 描述
     *
     * @param request
     * @param openId
     * @return ResultBean<List<UserInfoBean>>
     */
    @RequestMapping(value = "/getLotteryInfoByUser")
    @ResponseBody
    public ResultBean<List<UserInfoBean>> getAllLotteryInfoByUser(
            HttpServletRequest request, @RequestParam("OpenId") String openId) {
        ResultBean<List<UserInfoBean>> resultBean = new ResultBean<>(false,
                "未知错误，查询失败", null);

        try {
            List<UserInfoBean> list = wechatRouletteService
                    .getLotteryLogByUser(openId, Constant.ACTION_CODE);
            resultBean.setProperties(true, "成功", list);
        } catch (Exception e) {
            logger.error("wechat_roulette_获取用户中奖信息error:{}",
                Throwables.getStackTraceAsString(e));
        }

        logger.info(
            "wechat_roulette_openId:{},getAllLotteryInfoByUser->resultBean:{}",
            openId, resultBean);
        return resultBean;
    }

    /**
     * 
     * <p>
     * 获取all用户中奖信息的控制器
     * </p>
     *
     * @action caishuai 2017年3月21日 下午6:18:33 描述
     *
     * @param request
     * @return ResultBean<List<UserInfoBean>>
     */
    @RequestMapping(value = "/getAllLotteryInfo")
    @ResponseBody
    public ResultBean<List<UserInfoBean>> getAllLotteryInfo(
            HttpServletRequest request) {
        ResultBean<List<UserInfoBean>> resultBean = new ResultBean<>(false,
                "未知错误，查询失败", null);
        // 查询TOP50 All available=1
        String memkey = "wechat_roulette_actioncode" + Constant.ACTION_CODE;
        try {
            List<UserInfoBean> prizes = memcachedClientAdapter
                    .get(memkey.toLowerCase(), List.class);
            if (prizes == null) {
                prizes = rouletteBindBll
                        .selectTop50AvaPrizes(Constant.ACTION_CODE, 1);
                memcachedClientAdapter.set(memkey.toLowerCase(),
                    Constant.One_Min, prizes);
            }

            resultBean.setProperties(true, "成功", prizes);
        } catch (Exception e) {
            logger.error("wechat_roulette_获取all用户中奖信息error:{}",
                Throwables.getStackTraceAsString(e));
        }
        logger.info("wechat_roulette_getAllLotteryInfo->resultBean:{}",
            resultBean);
        return resultBean;
    }

    /**
     * 
     * <p>
     * 抽奖
     * </p>
     *
     * @action caishuai 2017年3月21日 下午6:18:18 描述
     *
     * @param request
     * @param openId
     * @return ResultBean<UserLotteryBean>
     */
    @RequestMapping(value = "/lottery")
    @ResponseBody
    public ResultBean<UserLotteryBean> lottery(HttpServletRequest request,
            @RequestParam("OpenId") String openId) {
        ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>(
                false, "抽奖错误，请重试", null);
        /**
         * 验证活动时间
         */
        ResultBean<Object> activityStatus = getActivityStatus(
            Constant.ACTION_CODE);
        if (!activityStatus.getIsSuccess()) {
            result.setIsSuccess(false);
            result.setMessage(activityStatus.getMessage());
            return result;
        }
        logger.info("wechat_roulette_Lottery->openid:{}", openId);

        try {
            result = wechatRouletteService.getLottery(Constant.ACTION_CODE,
                getIp(request), openId);
        } catch (Exception e) {
            logger.error("wechat_roulette_抽奖error:{}",
                Throwables.getStackTraceAsString(e));
        }
        logger.info("wechat_roulette_Lottery->openid:{},return->result:{}",
            openId, result);
        return result;

    }

    /**
     * 
     * <p>
     * 参数验证异常捕获返回
     * </p>
     *
     * @action caishuai 2017年3月22日 上午10:53:55 描述
     *
     * @param e
     * @return ResultBean<Integer>
     */
    @ExceptionHandler({ BindException.class })
    @ResponseBody
    public ResultBean<Integer> exception(BindException e) {
        ResultBean<Integer> bandResultBean = new ResultBean<Integer>();
        bandResultBean.setSuccess(false);
        BindingResult bindingRes = e.getBindingResult();
        FieldError fr = bindingRes.getFieldError();
        bandResultBean.setMessage(fr.getDefaultMessage());
        logger.error("wechat_roulette_BindException:" + e);
        return bandResultBean;
    }
}
