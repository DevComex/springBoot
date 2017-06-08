package cn.gyyx.action.service.wdqingchengshan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ServerBean;
import cn.gyyx.action.beans.enums.OperateScoreEnums;
import cn.gyyx.action.beans.wdqingchengshan.SignLogBean;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.lottery.IActionQualificationBLL;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.lottery.impl.ActionQualificationBLLImpl;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.wdqingchengshan.EnrollAndSignBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.weixin.WeChatAttention;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.service.WDGameAgent;

public class WDQcsEnrollAndSignService {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WDQcsEnrollAndSignService.class);
	
	private WDGameAgent gameAgent = new WDGameAgent();
	private EnrollAndSignBLL enrollAndSignBLL = new EnrollAndSignBLL();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private WeChatAttention att = new WeChatAttention();
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();
	private CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public ResultBean<ServerBean> serverlist(int netType) {
		logger.info("netType ---- {}", netType);
		ResultBean<ServerBean> result = new ResultBean<>(false, "获取服务器列表异常",
				null);
		try {
			ServerBean sb = gameAgent.getServers(2,netType);
			result.setProperties(true, "查询服务器成功", sb);
			logger.info("result", result);
		} catch (Exception e) {
			logger.error("问道青城山活动[获取服务器列表异常]", e);
		}
		return result;
	}
	/*public ResultBean<ServerBean> serverlist(int netType) {
		logger.info("netType ---- {}", netType);
		ResultBean<ServerBean> result = new ResultBean<>(false, "获取服务器列表异常",
				null);
		try {
			ServerBean sb = gameAgent.getServers(2,netType);
			result.setProperties(true, "查询服务器成功", sb);
			logger.info("result", result);
		} catch (Exception e) {
			logger.error("问道十年国庆微信活动[获取服务器列表异常]", e);
		}
		return result;
	}

	public ResultBean<String> rolelist(String account, int serverId) {
		ResultBean<String> result = new ResultBean<>(false, "获取角色列表异常",
				null);
		try {
			String roleInfo = CallWebServiceInsuranceAgent.getRoleInfo(account, serverId);
		/*	JSONObject jsonObj = JSONObject.fromObject(roleInfo);
			if (jsonObj == null || jsonObj.get("IsSuccess") == null) {
				result.setProperties(false, "调用接口获取角色列表失败", null);
				return result;
			}
			if (!(boolean)jsonObj.get("IsSuccess")) {
				result.setProperties(false, jsonObj.getString("ErrorMessage"), null);
				return result;
			}*  /
			result.setProperties(true, "查询角色列表成功", roleInfo);
		} catch (Exception e) {
			logger.error("问道十年国庆微信活动[获取角色列表异常]", e);
		}
		return result;
	}

	public ResultBean<WdNationaldayEnrollBean> enroll(int userId,String account, int serverId,
			String serverName, String openId, String roleId, String roleName,
			HttpServletRequest request) {
		ResultBean<WdNationaldayEnrollBean> result = new ResultBean<>(false, "报名失败",null);
		
		//判断活动是否开始或结束
		cn.gyyx.action.beans.novicecard.ResultBean<ActivityConfigBean> resultParameter = activityConfigBll.getConfigMessage(WdNationalConstant.HD_NAME);
		if (!resultParameter.getIsSuccess()) {
			result.setProperties(false, resultParameter.getMessage(),null);
			return result;
		}
		
		//检测是否关注过微信号
		
		JSONObject jsonWXUserInfo = att.getWeXinUserInfo("Wd", openId);
        if (!jsonWXUserInfo.containsKey(AttentionDictionary.ATTENTION_SUBSCRIBE)) {
            result.setIsSuccess(false);
            result.setMessage("无效的openId:" + openId );
            return result;
        } else if (!"1".equals(jsonWXUserInfo.getString(AttentionDictionary.ATTENTION_SUBSCRIBE))) {
            result.setIsSuccess(false);
            result.setMessage("请先关注问道微信");
            return result;
        } else if (!jsonWXUserInfo.containsKey(AttentionDictionary.ATTENTION_NICK_NAME)) {
            result.setIsSuccess(false);
            result.setMessage("获取昵称失败");
            return result;
        }
		
		try (DistributedLock lock = new DistributedLock(
				WdNationalConstant.MEM_KEY_PREFIX + "enroll_" + account)) {
			lock.weakLock(30, 11);
			//1.判断用户是否已经报名-根据用户account
			WdNationaldayEnrollBean bean = wdNationaldayEnrollBll.getEnrollInfoByAccount(account);
			if(bean != null){
				result.setProperties(false, "您的账号或微信号已参与活动", null);
				return result;
			}
			//2.报名
			bean = new WdNationaldayEnrollBean();
			bean.setAccount(account);
			bean.setServerId(serverId);
			bean.setServerName(serverName);
			bean.setOpenId(openId);
			bean.setRoleId(roleId);
			bean.setRoleName(roleName);
			bean.setCreateTime(new Date());
			bean.setIp(WdNationalConstant.getIpAddress(request));
			bean.setTotalScore(0);
			bean.setUserId(userId);
			bean.setNickName(jsonWXUserInfo.getString(AttentionDictionary.ATTENTION_NICK_NAME));
			int c = wdNationaldayEnrollBll.insert(bean);
			
			//3.缓存中存放openId 对应 用户信息
			if(c > 0){
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
				String value = mapper.writeValueAsString(bean);
				memcachedClientAdapter.set(WdNationalConstant.MEM_KEY_PREFIX + openId, WdNationalConstant.getUntilNDayEndSeconds(15),value);
			}
			
			result.setProperties(true, "报名成功", bean);
		} catch (Exception e) {
			logger.error("问道十年国庆微信活动[报名失败]", e);
		}
		return result;
	}

	public ResultBean<WdNationaldayEnrollBean> getUserInfo(String openId) {
		ResultBean<WdNationaldayEnrollBean> result = new ResultBean<>(false, "获取用户报名信息失败",null);
		
		try {
			WdNationaldayEnrollBean userInfo = wdNationaldayEnrollBll.getUserInfoByOpenId(openId);
			if(userInfo == null){
				result.setProperties(false, "请先登录！", null);
				return result;
			}
			String account = userInfo.getAccount();
			int userId = userInfo.getUserId();
			
			WdNationaldayEnrollBean bean = wdNationaldayEnrollBll.getEnrollInfoByAccount(account);
			if(bean != null){
				//***用户名***
				bean.setAccount(WdNationalConstant.userNameEncrypt(account));
				//获取签到次数
				int signCount =  wdNationaldayEnrollBll.getSignCountByAccount(account);
				bean.setSignCount(signCount);
				//获取上传日志次数
				int diaryCount = iShowProductionBLL.getVerifyProductionCountUserId(userId,"1");
				bean.setDiaryCount(diaryCount);
				//获取上传照片次数
				int picCount =   iShowProductionBLL.getVerifyProductionCountUserId(userId,"0");
				bean.setPicCount(picCount);
				
				result.setProperties(true, "获取用户信息成功", bean);
				return result;
			}else{
				result.setProperties(false, "用户未报名", null);
				return result;
			}
		} catch (Exception e) {
			logger.error("问道十年国庆微信活动[获取用户报名信息失败]", e);
		}
		return result;
	}*/
	
	/*
	 * 签到
	 */
	public ResultBean<String> sign(int activityId, String account, String ip) {
		ResultBean<String> result = new ResultBean<>(false, "签到失败",null);
		
		IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();
		// 是否在活动期间内
		int hdState = hdConfigBLL.getState(activityId);
		if (1 != hdState) {
			logger.info("ActionExchangeServiceImpl->exchange->activation is not in progress.");
			result.setStateCode(hdState);
			result.setMessage(hdConfigBLL.getMessage());
			return result;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//1.判断用户是否当天签过到
		SignLogBean log = enrollAndSignBLL.getTodaySignLog(account, sdf.format(new Date()));
		if(log != null){
			result.setProperties(false, "您今天已签到", "");
			return result;
		}
		
		String key = activityId + account + this.getClass().getName();
		try (DistributedLock lock = new DistributedLock(key)) {
			lock.weakLock(30, 0);
			
			//2.插入签到记录
			log = new SignLogBean();
			log.setAccount(account);
			log.setSignTime(new Date());
			
			SqlSession session = getSession();
			
			try {
				enrollAndSignBLL.insert(log,session);
				
				// 增加积分
				IActionQualificationBLL qualificationBLL = new ActionQualificationBLLImpl();
				if (qualificationBLL.addLotteryScore(activityId, account, 5, OperateScoreEnums.SignUp.toString(), ip, session) < 1) {
					session.rollback();
					return result;
				}
				
				session.commit();
				
				result.setMessage("签到成功！");
				result.setIsSuccess(true);
			}
			catch(Exception sqle) {
				if (session != null) {
					session.rollback();
				}
				logger.error("问道青城山活动[签到失败]", sqle);
			}
			finally {
				if (session != null) {
					session.close();
				}
			}
			
		} catch (Exception e) {
			logger.error("问道青城山活动[签到失败]", e);
		}
		
		return result;
	}

	// 获得签到
	public ResultBean<SignLogBean> getSignInfo(String userId) {
		ResultBean<SignLogBean> result = new ResultBean<SignLogBean>();
		result.setIsSuccess(false);
		
		SignLogBean params = new SignLogBean();
		params.setAccount(userId);
		
		List<SignLogBean> logList = enrollAndSignBLL.select(params);
		if (null != logList) {
			result.setRows(logList);
			result.setIsSuccess(true);
		}
		
		return result;
	}
}

