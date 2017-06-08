/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午12:14:53
 * 版本号：v1.0
 * 本类主要用途叙述：挑战的服务层
 */

package cn.gyyx.action.service.challenger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.ChallengeRelationshipBean;
import cn.gyyx.action.beans.challenger.ChallenterInfoBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.beans.challenger.OperationLogBean;
import cn.gyyx.action.beans.challenger.SameDataBean;
import cn.gyyx.action.beans.challenger.SeeMyChallengeRecordBean;
import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.challenger.ChallengeRelationBll;
import cn.gyyx.action.bll.challenger.ChallenterInfoBll;
import cn.gyyx.action.bll.challenger.ChallenterUserInfoBll;
import cn.gyyx.action.bll.challenger.OperationLogBll;
import cn.gyyx.action.bll.challenger.SameDataBll;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ChallengeService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChallengeService.class);
	private ChallenterUserInfoBll challterUserInfoBll = new ChallenterUserInfoBll();
	private ChallenterInfoBll challenterInfoBll = new ChallenterInfoBll();
	private ChallengeRelationBll challengeRelationBll = new ChallengeRelationBll();
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();
	private OperationLogBll operationLogBll = new OperationLogBll();
	private SameDataBll sameDataBll = new SameDataBll();

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 看是否能宣战
	 * 
	 * @return
	 */
	public ResultBean<String> isCanDeclareWar(HttpServletRequest request) {
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "您并没有登录", "您并没有登录");
		}
		try (DistributedLock lock = new DistributedLock("isCanDeclareWar"
				+ userInfo.getUserId())) {
			lock.weakLock(30, 2);
			// 判断是否报名
			ResultBean<String> resultBean = findUserInfoStatus(userInfo
					.getUserId());
			if (!resultBean.getIsSuccess()) {
				return resultBean;
			}
			ChallenterInfoBean challenterInfoBefore = challenterInfoBll
					.getOneChallenterInfoWithoutUnPass(userInfo.getUserId());
			if (challenterInfoBefore != null) {
				return new ResultBean<>(false, "您已发起过挑战", "您已发起过挑战");
			}
			return new ResultBean<>(true, "可以挑战", resultBean.getData());
		} catch (Exception e) {
			logger.error("isCanDeclareWar" + e);
			return new ResultBean<>(false, "网络超时", "网络超时");
		}
	}

	/***
	 * 发起挑战
	 * 
	 * @return
	 */
	public ResultBean<String> provocationEveryOne(
			ChallenterInfoBean challenterInfoBean, HttpServletRequest request) {

		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "您并没有登录", "您并没有登录");
		}
		try (DistributedLock lock = new DistributedLock("provocationEveryOne"
				+ userInfo.getUserId())) {
			lock.weakLock(30, 2);
			// 判断是否报名
			ResultBean<String> resultBean = findUserInfoStatus(userInfo
					.getUserId());
			if (!resultBean.getIsSuccess()) {
				return resultBean;
			}
			// 已经审核过的报名信息
			ChallenterUserInfoBean passchallenterUserInfoBean = challterUserInfoBll
					.getOneChallenterUserInfo(userInfo.getUserId(), "pass");
			ChallenterInfoBean challenterInfoBeanNew = challenterInfoBean;
			challenterInfoBeanNew.setRoleName(passchallenterUserInfoBean
					.getRoleName());
			challenterInfoBeanNew.setAccount(userInfo.getAccount());
			challenterInfoBeanNew.setUserId(userInfo.getUserId());
			ChallenterInfoBean challenterInfoBefore = challenterInfoBll
					.getOneChallenterInfoWithoutUnPass(userInfo.getUserId());
			if (challenterInfoBefore != null) {
				return new ResultBean<>(false, "您已发起过挑战", "您已发起过挑战");
			}
			challenterInfoBll.addChallenterInfo(challenterInfoBean);
			return new ResultBean<>(true, "发起挑战成功", "发起挑战成功");
		} catch (Exception e) {
			logger.error("provocationEveryOne" + e);
			return new ResultBean<>(false, "系统丢失了", "系统丢失了");
		}
	}

	/***
	 * 查询用户的状态
	 * 
	 * @param userCode
	 * @return
	 */
	private ResultBean<String> findUserInfoStatus(int userCode) {
		// 正在审核的报名信息
		ChallenterUserInfoBean challenterUserInfoBean = challterUserInfoBll
				.getLastOneChallenterUserInfo(userCode);
		if (challenterUserInfoBean == null
				|| "unpass".equals(challenterUserInfoBean.getState())) {
			return new ResultBean<>(false, "请您先报名，暂时不能操作", "请您先报名，暂时不能操作");
		}
		if ("oncheck".equals(challenterUserInfoBean.getState())) {
			return new ResultBean<>(false, "报名信息正在审核，暂时不能操作", "报名信息正在审核，暂时不能操作");
		}
		return new ResultBean<>(true, "", challenterUserInfoBean.getRoleName());

	}

	/***
	 * 分页获取挑战信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public ResultBeanWithPage<List<ChallenterInfoBean>> getAllChallenterUserInfoBean(
			int pageSize, int pageNo) {
		try {
			int count = challenterInfoBll.getCountStateChallenterInfo("pass");
			List<ChallenterInfoBean> list = challenterInfoBll
					.getStateChallenterInfo(pageNo, pageSize, "pass");
			if (list != null && !list.isEmpty()) {
				list = trunOldRoleIfNotExit(list);
			}
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("getAllChallenterUserInfoBean" + e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}

	/***
	 * 对某人挑战
	 * 
	 * @param actionCode
	 * @param userId
	 * @param request
	 * @return
	 */
	public ResultBean<String> challengeSomeOne(int actionCode, int userId,
			HttpServletRequest request) {
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "您并没有登录,不能挑战", "您并没有登录,不能挑战");
		}
		String key = actionCode + "." + userInfo.getUserId()
				+ "challengeSomeOne";
		// 用户能参与活动不
		ResultBean<String> resultBean = isUserCanChallengeSomeOne(key,
				userInfo, userId);
		if (!resultBean.getIsSuccess()) {
			return resultBean;
		}
		if (userInfo.getUserId() == userId) {
			return new ResultBean<>(false, "不能挑战自己", "不能挑战自己");
		}

		try (SqlSession sqlSession = getSession()) {
			// 增加挑战
			return addChallengeSomeoneRecored(userId, actionCode, key,
					sqlSession, userInfo);
		}

	}

	/***
	 * 增加挑战纪录
	 * 
	 * @param count
	 * @param sqlSession
	 * @param userId
	 * @param dareId
	 * @return
	 */
	private ResultBean<String> addChallengeSomeoneRecored(int userId,
			int actionCode, String key, SqlSession sqlSession, UserInfo userInfo) {
		try (DistributedLock lock = new DistributedLock(
				"addChallengeSomeoneRecored" + userInfo.getUserId() + "."
						+ actionCode)) {
			lock.weakLock(30, 2);
			// 用户能参与活动不
			ResultBean<String> resultBean = isUserCanChallenge( userInfo,
					userId, sqlSession);
			if (!resultBean.getIsSuccess()) {
				return resultBean;
			}
			// 挑战的次数
			int count = Integer.parseInt(resultBean.getData());
			ChallengeRelationshipBean challengeRelationshipBean = new ChallengeRelationshipBean();
			challengeRelationshipBean.setDareId(userInfo.getUserId());
			challengeRelationshipBean.setUserId(userId);
			// 增加记录
			challengeRelationBll.addChallengeRelationshipBean(
					challengeRelationshipBean, sqlSession);
			// 增加次数
			challenterInfoBll.addChallengeTimes(userId, sqlSession);
			// 今日没有挑战过 就加个抽奖机会
			if (count == 0) {
				LuckyDrawLogBean logBean = new LuckyDrawLogBean();
				logBean.setAccount(userInfo.getAccount());
				logBean.setActionCode(actionCode);
				logBean.setDrawCount(1);
				logBean.setSource("challengesomeone");
				logBean.setUserId(userInfo.getUserId());
				LotteryService.setLotteryTimesSqlSession(logBean, sqlSession);
			}
			// 增加缓存限制的次数
			String times = memcachedClientAdapter.get(key, String.class);
			if (times == null) {
				int newTimes = 1;
				memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
						String.valueOf(newTimes));
			} else {
				int newTimes = Integer.parseInt(times) + 1;
				memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
						String.valueOf(newTimes));
			}
			sqlSession.commit(true);
			return new ResultBean<>(true, "发起挑战成功", String.valueOf(count));
		} catch (Exception e) {
			logger.error("addChallengeSomeoneRecored" + e);
			sqlSession.rollback();
			return new ResultBean<>(false, "并没有挑战成功", "");

		}

	}

	/***
	 * 判断用户能否挑战
	 * 
	 * @param key
	 * @param userInfo
	 * @param userId
	 * @return
	 */
	private ResultBean<String> isUserCanChallengeSomeOne(String key,
			UserInfo userInfo, int userId) {
		// 先用缓存限制一下
		if (!isClickTwentyTimes(key)) {
			return new ResultBean<>(false, "您今日已经挑战20次了,不能挑战",
					"您今日已经挑战20次了,不能挑战");
		}
		// 判断是否报名
		ResultBean<String> resultBean = findUserInfoStatus(userInfo.getUserId());
		if (!resultBean.getIsSuccess()) {
			return resultBean;
		}

		return new ResultBean<>(true, "", "");
	}

	/***
	 * 判断用户能否挑战
	 * 
	 * @param key
	 * @param userInfo
	 * @param userId
	 * @return
	 */
	private ResultBean<String> isUserCanChallenge(
			UserInfo userInfo, int userId, SqlSession sqlSession) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String begin = sdf.format(now);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, 1);
		String end = sdf.format(calendar.getTime());
		// 获取今日挑战次数
		int countToday = challengeRelationBll
				.getCountDareIdChallengeRelationshipBeanInTimeo(begin, end,
						userInfo.getUserId(), sqlSession);
		if (countToday >= 20) {
			return new ResultBean<>(false, "您今日已经挑战20次了,不能挑战",
					"您今日已经挑战20次了,不能挑战");
		}
		// 看看今天有没有这个人挑战某个人的挑战纪录
		ChallengeRelationshipBean challengeRelationshipBean = challengeRelationBll
				.getOneTodayChallengeSomeOne(begin, end, userInfo.getUserId(),
						userId, sqlSession);
		if (challengeRelationshipBean != null) {
			return new ResultBean<>(false, "您今天已经挑战过此人", "您今天已经挑战过此人");
		}

		return new ResultBean<>(true, "", "" + countToday);
	}

	/***
	 * 判断是否20次挑战
	 * 
	 * @param account
	 * @return
	 */
	private boolean isClickTwentyTimes(String key) {
		String times = memcachedClientAdapter.get(key, String.class);
		if (times == null || Integer.parseInt(times) < 20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到距离一天结束的好友多少秒
	 * 
	 * @return
	 * @throws ParseException
	 */
	private Integer getUntilDayEndSeconds() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date nowDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.add(Calendar.DATE, 1);
			Date tempDate;
			tempDate = sdf.parse(sdf.format(calendar.getTime()));
			return (int) ((tempDate.getTime() - nowDate.getTime()) / 1000);
		} catch (ParseException e) {
			logger.warn("getUntilDayEndSeconds" + e);
			return 60 * 60 * 24;
		}
	}

	/****
	 * 我挑战了谁
	 * 
	 * @param userInfo
	 * @return
	 */
	public ResultBean<SeeMyChallengeRecordBean> getMyChallengeInfo(
			HttpServletRequest request) {
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "请您先登录", null);
		}
		try {
			// 判断是否报名
			ResultBean<String> resultBean = findUserInfoStatus(userInfo
					.getUserId());
			if (!resultBean.getIsSuccess()) {
				return new ResultBean<>(false, "您还没有报名", null);
			}
			SeeMyChallengeRecordBean seeMyChallengeRecordBean = new SeeMyChallengeRecordBean();
			// 获取角色信息
			List<String> listRoles = challengeRelationBll
					.getRoleNameIChallenge(userInfo.getUserId());
			int count = challengeRelationBll
					.getCountDareIdChallengeRelation(userInfo.getUserId());
			seeMyChallengeRecordBean.setCount(count);
			seeMyChallengeRecordBean.setRoleNames(listRoles);
			return new ResultBean<>(true, "我挑战别人的记录在此",
					seeMyChallengeRecordBean);
		} catch (Exception e) {
			logger.error("getMyChallengeInfo" + e);
			return new ResultBean<>(false, "记录获得失败", null);
		}
	}

	/***
	 * 谁挑战了我的信息
	 * 
	 * @param request
	 * @return
	 */
	public ResultBean<SeeMyChallengeRecordBean> getWhoChallengeMe(
			HttpServletRequest request) {
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "请您先登录", null);
		}
		try {
			// 判断是否报名
			ResultBean<String> resultBean = findUserInfoStatus(userInfo
					.getUserId());
			if (!resultBean.getIsSuccess()) {
				return new ResultBean<>(false, "您还没有报名", null);
			}
			ChallenterInfoBean challenterInfoBean = challenterInfoBll
					.getOneStatusChallenterInfo(userInfo.getUserId(), "pass");
			if (challenterInfoBean == null) {
				return new ResultBean<>(false, "您没有发起过挑战", null);
			}
			List<String> listRoles = challengeRelationBll
					.getRoleNameChallengeMe(userInfo.getUserId());
			SeeMyChallengeRecordBean seeMyChallengeRecordBean = new SeeMyChallengeRecordBean();
			seeMyChallengeRecordBean
					.setCount(challenterInfoBean.getDareCount());
			seeMyChallengeRecordBean.setRoleNames(listRoles);
			return new ResultBean<>(true, "别人挑战我的记录在此",
					seeMyChallengeRecordBean);
		} catch (Exception e) {
			logger.error("getWhoChallengeMe" + e);
			return new ResultBean<>(false, "记录获得失败", null);
		}

	}

	/***
	 * 审核通过挑战信息
	 * 
	 * @param code
	 * @return
	 */
	public ResultBean<String> passChallengeInfo(int code, int actionCode,
			int opcode, String userName) {
		ChallenterInfoBean challenterInfoBean = challenterInfoBll
				.getOneChallenterInfo(code);
		if (challenterInfoBean == null) {
			return new ResultBean<>(false, "记录消失，不能审核", "");
		}
		// 获取用户通过的挑战
		ChallenterInfoBean challenterInfoUser = challenterInfoBll
				.getOneStatusChallenterInfo(challenterInfoBean.getUserId(),
						"pass");
		if (challenterInfoUser != null) {
			return new ResultBean<>(false, "用户有已经通过审核的挑战，不能再审核通过了", "");
		}
		try (SqlSession sqlSession = getSession()) {
			try (DistributedLock lock = new DistributedLock("passChallengeInfo"
					+ code + "." + actionCode)) {
				lock.weakLock(30, 2);

				// 审核通过的内部实现方法
				return passChallengeInfoInStory(code,
						challenterInfoBean.getAccount(),
						challenterInfoBean.getUserId(), actionCode, opcode,
						userName, sqlSession);
			} catch (Exception e) {
				logger.error("passChallengeInfo error" + e);
				sqlSession.rollback();
				return new ResultBean<>(false, "审核失败", "");

			}
		}
	}

	/***
	 * 审核通过的内部方法
	 * 
	 * @param code
	 * @param account
	 * @param userCode
	 * @param actionCode
	 * @param opcode
	 * @param userName
	 * @param sqlSession
	 * @return
	 */
	private ResultBean<String> passChallengeInfoInStory(int code,
			String account, int userCode, int actionCode, int opcode,
			String userName, SqlSession sqlSession) {
		int status = challenterInfoBll.setOneStatusAndTime(code, "pass",
				sqlSession);
		if (status == 1) {
			LuckyDrawLogBean logBean = new LuckyDrawLogBean();
			logBean.setAccount(account);
			logBean.setActionCode(actionCode);
			logBean.setDrawCount(1);
			logBean.setSource("challengeInfo");
			logBean.setUserId(userCode);
			// 这就得加抽奖次数了
			LotteryService.setLotteryTimesSqlSession(logBean, sqlSession);
			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(userName);
			operateBean.setUserid(opcode);
			operateBean.setType("challengeInfoCheck");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("挑战信息审核通过");
			operationLogBll.addOperationLog(operateBean, sqlSession);
			sqlSession.commit(true);
			return new ResultBean<>(true, "审核通过", "");
		} else {
			sqlSession.rollback();
			return new ResultBean<>(false, "审核不正确", "");
		}
	}

	/***
	 * 拒绝挑战信息
	 * 
	 * @param code
	 * @return
	 */
	public ResultBean<String> refuseChallengeInfo(int code, int actionCode,
			int opcode, String userName) {
		ChallenterInfoBean challenterInfoBean = challenterInfoBll
				.getOneChallenterInfo(code);
		if (challenterInfoBean == null) {
			return new ResultBean<>(false, "记录消失，不能审核", "");
		}
		try (SqlSession sqlSession = getSession()) {
			try (DistributedLock lock = new DistributedLock(
					"refuseChallengeInfo" + code + "." + actionCode)) {
				lock.weakLock(30, 2);

				// 审核通过的内部实现方法
				return refuseChallengeInfoInStory(code,
						challenterInfoBean.getAccount(),
						challenterInfoBean.getUserId(), actionCode, opcode,
						userName, sqlSession);
			} catch (Exception e) {
				logger.error("refuseChallengeInfo error" + e);
				sqlSession.rollback();
				return new ResultBean<>(false, "审核失败", "");

			}
		}
	}

	/***
	 * 审核通过的内部方法
	 * 
	 * @param code
	 * @param account
	 * @param userCode
	 * @param actionCode
	 * @param opcode
	 * @param userName
	 * @param sqlSession
	 * @return
	 */
	private ResultBean<String> refuseChallengeInfoInStory(int code,
			String account, int userCode, int actionCode, int opcode,
			String userName, SqlSession sqlSession) {
		int status = challenterInfoBll.setOneStatusAndTime(code, "unpass",
				sqlSession);
		if (status == 1) {
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(userName);
			operateBean.setUserid(opcode);
			operateBean.setType("challengeInfoCheck");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("挑战信息审核不通过");
			operationLogBll.addOperationLog(operateBean, sqlSession);
			sqlSession.commit(true);
			return new ResultBean<>(true, "拒绝成功", "");
		} else {
			sqlSession.rollback();
			return new ResultBean<>(false, "审核不正确", "");
		}
	}

	/***
	 * 分页获取未审核的挑战信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public ResultBeanWithPage<List<ChallenterInfoBean>> getAllChallenterBean(
			int pageSize, int pageNo) {
		try {
			int count = challenterInfoBll
					.getCountStateChallenterInfo("oncheck");
			List<ChallenterInfoBean> list = challenterInfoBll
					.getStateChallenterInfo(pageNo, pageSize, "oncheck");
			if (list != null && !list.isEmpty()) {
				list = trunOldRoleIfNotExit(list);
			}
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("getAllChallenterUserInfoBean" + e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}

	/***
	 * 如果不存在曾经角色，显示无
	 * 
	 * @return
	 */
	private List<ChallenterInfoBean> trunOldRoleIfNotExit(
			List<ChallenterInfoBean> list) {
		List<ChallenterInfoBean> newList = list;
		for (int i = 0; i < newList.size(); i++) {
			if (newList.get(i).getOldRole() == null
					|| "".equals(newList.get(i).getOldRole().trim())) {
				newList.get(i).setOldRole("无");
			}
		}
		return newList;
	}

	/**
	 * 获取五条直播预告信息
	 * 
	 * @param actionCode
	 * @return
	 */
	public String getFiveLive(int actionCode) {
		SameDataBean sameDataBean = sameDataBll.getSameDataBean("fivelive",
				actionCode);
		if (sameDataBean != null) {
			return sameDataBean.getContent();
		} else {
			return "";
		}

	}

	public ResultBean<String> setFiveLive(int actionCode, String[] time,
			String[] playerOne, String[] playerTwo, String[] radio) {
		try {

			String content = "";
			if (time == null || playerOne == null || playerTwo == null
					|| radio == null) {
				sameDataBll.resetSameDate("fivelive", content, actionCode);
				return new ResultBean<>(true, "信息变空", "");
			}
			List<ChallenterLiveBean> list = new ArrayList<>();
			for (int i = 0; i < playerTwo.length; i++) {
				ChallenterLiveBean challenterLiveBean = new ChallenterLiveBean();
				challenterLiveBean.setLiveTimeString(time[i]);
				challenterLiveBean.setLiveRadio(radio[i]);
				challenterLiveBean.setPlayer(playerOne[i]);
				challenterLiveBean.setRoleName(playerTwo[i]);
				list.add(challenterLiveBean);
			}
			JSONArray jsonArray = JSONArray.fromObject(list);
			sameDataBll.resetSameDate("fivelive", jsonArray.toString(),
					actionCode);
			return new ResultBean<>(true, "修改成功", "");
		} catch (Exception e) {
			logger.error("setFiveLive" + e);
			return new ResultBean<>(false, "修改失败", "");
		}

	}

}
