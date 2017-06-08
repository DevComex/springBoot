/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-wdshenluowangxiang
 * @作者：lihu
 * @联系方式：lihu@gyyx.cn
 * @创建时间：2017年4月8日 下午4:23:05
 * @版本号：0.0.1
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wdshenluowangxiang;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ServerBean;
import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.beans.enums.ActivityStatus;
import cn.gyyx.action.beans.wdshenluowangxiang.Constants;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangAddressBean;
import cn.gyyx.action.beans.wdshenluowangxiang.ShenLuoWangXiangReturnBean;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.service.BaseController;
import cn.gyyx.action.service.wdshenluowangxiang.ShenLuoWangXiangService;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLogger;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.service.WDGameAgent;

/**
 * <p>
 * 森罗万象活动页面的接口
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
@Controller
@RequestMapping(value = "/wdshenluowangxiang")
public class ShenLuoWangXiangController extends BaseController {

	private static final GYYXLogger LOG = GYYXLoggerFactory.getLogger(ShenLuoWangXiangController.class);
	private IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();

	private WDGameAgent gameAgent = new WDGameAgent();
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil.getMemcache();
	private ShenLuoWangXiangService shenLuoWangXiangService = new ShenLuoWangXiangService();

	/**
	 * 检测用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkUser")
	@ResponseBody
	public ResultBean<ShenLuoWangXiangReturnBean> checkUser(HttpServletRequest request) {
		try {
		        // 判断用户是否登录
	                UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
			if (userInfo == null) {
				return new ResultBean<>(false, Constants.NOLOGIN_PROMPT, null);
			}
			// 判断活动是否结束
			ResultBean<ActionConfigPO> hdStatus = this.hdStatus();
			if (!hdStatus.getIsSuccess()) {
				return new ResultBean<>(false, hdStatus.getMessage(), null);
			}
			return new ResultBean<>(true, "检测用户信息成功", shenLuoWangXiangService.checkUser(userInfo));
		} catch (Exception e) {
			LOG.error("检测用户信息，错误堆栈：" + Throwables.getStackTraceAsString(e));
			return new ResultBean<>(false, "检测用户信息异常", null);
		}
	}

	/**
	 * <p>
	 * 获取服务器列表
	 * </p>
	 *
	 * @action 李鹄
	 *
	 * @param netId
	 * @return ResultBean<ServerBean>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<ServerBean> getServers(HttpServletRequest request, int netId) {
		try {
		        // 判断用户是否登录
	                UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
	                if (userInfo == null) {
	                        return new ResultBean<>(false, Constants.NOLOGIN_PROMPT, null);
	                }
	                LOG.info("获取服务器列表 netId:{} userId: {}", netId, userInfo.getUserId());
	                // 判断活动是否结束
	                ResultBean<ActionConfigPO> hdStatus = this.hdStatus();
	                if (!hdStatus.getIsSuccess()) {
	                        return new ResultBean<>(false, hdStatus.getMessage(), null);
	                }
			// 从缓存中获取
			ServerBean bean = memcachedClientAdapter.get(Constants.SERVER_LIST + netId, ServerBean.class);
			if (bean == null) {// 没有去调取接口
				bean = gameAgent.getServers(Constants.GAME_ID, netId);
				memcachedClientAdapter.set(Constants.SERVER_LIST + netId, 3600 * 24, bean);
			}
			return new ResultBean<>(true, "获取服务器成功", bean);
		} catch (IOException e) {
			LOG.error("获取服务器列表异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
			return new ResultBean<>(false, "获取服务器列表异常", null);
		}

	}

	/**
	 * <p>
	 * 报名
	 * </p>
	 *
	 * @action 李鹄
	 * @param request
	 * @param serverId
	 * @param serverName
	 * @param vcode
	 * @return
	 */
	@RequestMapping(value = "/bindServer", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> bindServer(HttpServletRequest request, HttpServletResponse response,int serverId, String serverName,String vcode,String source) {
		// 判断用户是否登录
		UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, Constants.NOLOGIN_PROMPT, null);
		}
		if (!new Captcha(request, response).equals(vcode)) {
	                return new ResultBean<>(false, "验证码填写错误", null);
	        }
		// 判断活动是否结束
		ResultBean<ActionConfigPO> hdStatus = this.hdStatus();
		if (!hdStatus.getIsSuccess()) {
			return new ResultBean<>(false, hdStatus.getMessage(), null);
		}
		if(!"mobile".equals(source)){
		    source = "pc";//mobile代表手机
		}
		LOG.info("报名 serverId:{} serverName: {} account: {}", serverId, serverName, userInfo.getAccount());
		
		try(DistributedLock lock = new DistributedLock(Constants.LOTTERY_PRIFIX + userInfo.getAccount());) {
			// 加同步锁
			lock.weakLock(76, 5);
			// 用户报名
			return shenLuoWangXiangService.bindServer(serverId, serverName, userInfo,source);
		} catch (Exception e) {
			LOG.error("获取数据异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
			return new ResultBean<>(false, "报名接口异常", null);
		} 

	}

	

	/**
	 * <p>
	 * 编辑邀请函地址
	 * </p>
	 *
	 * @action 李鹄
	 * @param request
	 * @param serverId
	 * @param serverName
	 * @return
	 */
	@RequestMapping(value = "/editInviteAddress")
	@ResponseBody
	public ResultBean<String> editInviteAddress(HttpServletRequest request,@Valid ShenLuoWangXiangAddressBean addressBean,
			BindingResult bindingResult ) {
		try {
		        // 判断用户是否登录
	                UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
	                if (userInfo == null) {
	                        return new ResultBean<>(false, Constants.NOLOGIN_PROMPT, null);
	                }
	                if (bindingResult.hasErrors()) {
	                        String messageString = validationData(bindingResult);
	                        return new ResultBean<>(false, messageString, null);
	                }
	                // 判断活动是否结束
	                ResultBean<ActionConfigPO> hdStatus = this.hdStatus();
	                if (!hdStatus.getIsSuccess()) {
	                        return new ResultBean<>(false, hdStatus.getMessage(), null);
	                }
	                LOG.info("编辑邀请函地址 addressBean:{} account{} ", userInfo.getAccount(), addressBean);
			// 编辑邀请函地址
			return shenLuoWangXiangService.editInviteAddress(addressBean, userInfo);
		} catch (Exception e) {
			LOG.error("获取数据异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
			return new ResultBean<>(false, "编辑邀请函地址接口异常", null);
		}  
	}

	/**
	 * <p>
	 * 获取邀请函地址
	 * </p>
	 *
	 * @action 李鹄
	 * @param request
	 * @param serverId
	 * @param serverName
	 * @return
	 */
	@RequestMapping(value = "/getInviteAddress")
	@ResponseBody
	public ResultBean<ShenLuoWangXiangAddressBean> getInviteAddress(HttpServletRequest request) {
		try {
		        // 判断用户是否登录
	                UserInfo userInfo = cn.gyyx.core.auth.SignedUser.getUserInfo(request);
	                if (userInfo == null) {
	                        return new ResultBean<>(false, Constants.NOLOGIN_PROMPT, null);
	                }
	                // 判断活动是否结束
	                ResultBean<ActionConfigPO> hdStatus = this.hdStatus();
	                if (!hdStatus.getIsSuccess()) {
	                        return new ResultBean<>(false, hdStatus.getMessage(), null);
	                }
	                LOG.info("获取邀请函地址  account{} ", userInfo.getAccount() );
			// 编辑邀请函地址
			return shenLuoWangXiangService.getInviteAddress( userInfo.getUserId());
		} catch (Exception e) {
			LOG.error("获取邀请函地址，错误堆栈：" + Throwables.getStackTraceAsString(e));
			return new ResultBean<>(false, "获取邀请函地址接口异常", null);
		}  
	}
	/**
	 * <p>
	 * 获取活动状态
	 * </p>
	 *
	 * @action 李鹄
	 */
	private ResultBean<ActionConfigPO> hdStatus() {
		// 判断当前活动的状态，是否在活动时间范围
		ActionConfigPO actionConfigPO = hdConfigBLL.getConfigEntity(Constants.HD_CODE);
		if (actionConfigPO == null || actionConfigPO.getIsDelete()) {
			return new ResultBean<>(false, "活动已下架", null);
		}
		ActivityStatus activityStatus = this.getStatus(actionConfigPO.getHdStart(), actionConfigPO.getHdEnd());
		if (activityStatus.getCode() != 1) {
			return new ResultBean<>(false, activityStatus.getMsg(), null);
		}
		return new ResultBean<>(true, "", actionConfigPO);
	}

	private ActivityStatus getStatus(Date hdStart, Date hdEnd) {
		Date now = Calendar.getInstance().getTime();
		if (hdStart != null && hdStart.getTime() > now.getTime()) {
			return ActivityStatus.HAS_NOT_START;
		}

		if (hdEnd != null && hdEnd.getTime() < now.getTime()) {
			return ActivityStatus.IS_OVER;
		}
		return ActivityStatus.IS_NORMAL;
	}
	
	/**
	 * 获取错误信息
	 * @param vresult
	 * @return
	 */
	private String validationData(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		FieldError fieldError = fieldErrors.get(0);
		return fieldError.getDefaultMessage();
	}	
	
	/**
         * <p>
         * 简单注册
         * </p>
         *
         * @action 李鹄
         * @param request
         * @param account
         * @param pwd
         * @param vcode
         * @return
         */
        @RequestMapping(value = "/regist", method = RequestMethod.POST)
        @ResponseBody
        public ResultBean<String> regist(HttpServletRequest request, HttpServletResponse response,
            String account,String pwd,String vcode) {
                if(StringUtils.isEmpty(account)
                        || StringUtils.isEmpty(pwd)
                        || StringUtils.isEmpty(vcode)){
                    return new ResultBean<>(false, "账号、密码及验证码均不能为空!","");
                }
                try {
                    if (!new Captcha(request, response).equals(vcode)) {
                        return new ResultBean<>(false, "验证码填写错误!", null);
                    }
                    return shenLuoWangXiangService.regist(account, pwd, request);
                } catch (Exception e) {
                        LOG.error("注册异常，错误堆栈：" + Throwables.getStackTraceAsString(e));
                        return new ResultBean<>(false, "服务器繁忙,请稍后重试！", null);
                } 

        }
}
