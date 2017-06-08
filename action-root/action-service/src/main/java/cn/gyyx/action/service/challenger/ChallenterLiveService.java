/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月16日下午3:17:37
 * 版本号：v1.0
 * 本类主要用途叙述：直播的service
 */

package cn.gyyx.action.service.challenger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.ChallenterDeathLifeRankBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveRankBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveTimeRankBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.beans.challenger.OperationLogBean;
import cn.gyyx.action.beans.challenger.SameDataBean;
import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.challenger.ChallenterLiveBll;
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChallenterLiveService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChallenterLiveService.class);
	private ChallenterLiveBll challenterLiveBll = new ChallenterLiveBll();
	private ChallenterUserInfoBll challterUserInfoBll = new ChallenterUserInfoBll();
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

	/**
	 * 增加直播信息
	 * 
	 * @param challenterLiveBean
	 */
	public ResultBean<String> addChallenterLive(int actionCode,
			ChallenterLiveBean challenterLiveBean, HttpServletRequest request) {
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "您并没有登录", "您并没有登录");
		}
		String key = actionCode + "." + userInfo.getUserId()
				+ "addChallenterLive";

		try (DistributedLock lock = new DistributedLock("addChallenterLive"
				+ userInfo.getUserId() + "." + actionCode)) {
			lock.weakLock(30, 2);
			// 先限制一下
			if (!isFiveTimeMem(key)) {
				return new ResultBean<>(false, "一天只能发布五次直播，您明日再来",
						"您一天只能发布五次直播，您明日再来");
			}
			if (!isLiveQuickClick(actionCode, userInfo.getUserId())) {
				return new ResultBean<>(false, "不要点击太快", "不要点击太快");
			}
			ResultBean<ChallenterUserInfoBean> resultBean = findUserInfoStatus(userInfo
					.getUserId());
			if (!resultBean.getIsSuccess()) {
				return new ResultBean<>(false, resultBean.getMessage(), null);
			}
			ChallenterUserInfoBean challenterUserInfoBean = resultBean
					.getData();
			return addChallenterLive(key, challenterLiveBean,
					challenterUserInfoBean, userInfo);
		} catch (Exception e) {
			logger.error("addChallenterLive" + e);
			return new ResultBean<>(false, "参数格式错误，添加失败", "");
		}

	}

	/*
	 * private ChallenterLiveBean getDate(ChallenterLiveBean dBean) throws
	 * ParseException { ChallenterLiveBean newChallenterLiveBean = dBean;
	 * newChallenterLiveBean.setLiveTime(getLiveTimeDate(dBean
	 * .getLiveTimeString())); return newChallenterLiveBean; }
	 */

	private boolean isLiveQuickClick(int actionCode, int userId) {
		String key = actionCode + "." + userId + ".isLiveQuickClick";
		String temp = memcachedClientAdapter.get(key, String.class);
		if (temp == null) {
			memcachedClientAdapter.set(key, 2, "have");
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 转换时间合适
	 * 
	 * @param liveTimeString
	 * @return
	 * @throws ParseException
	 */
	public Date getLiveTimeDate(String liveTimeString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(liveTimeString);
	}

	/**
	 * 增加直播内部
	 * 
	 * @param actionCode
	 * @param challenterLiveBean
	 * @param challenterUserInfoBean
	 * @param userInfo
	 * @return
	 * @throws ParseException
	 */
	private ResultBean<String> addChallenterLive(String key,
			ChallenterLiveBean challenterLiveBean,
			ChallenterUserInfoBean challenterUserInfoBean, UserInfo userInfo)
			throws ParseException {

		// 数据库取值得到信息
		if (!isFiveTime(userInfo.getUserId())) {
			return new ResultBean<>(false, "一天只能发布五次直播，您明日再来",
					"您一天只能发布五次直播，您明日再来");
		}
		// 增加用户直播信息
		ChallenterLiveBean newChallenterLiveBean = challenterLiveBean;
		newChallenterLiveBean.setUserId(challenterUserInfoBean.getUserId());
		newChallenterLiveBean.setAccount(challenterUserInfoBean.getAccount());
		Date temp = getLiveTimeDate(newChallenterLiveBean.getLiveTimeString());
		newChallenterLiveBean.setLiveTime(temp);
		challenterLiveBll.addChallenterLive(newChallenterLiveBean);

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

		return new ResultBean<>(true, "添加成功", "");
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

	/***
	 * 查询用户的状态
	 * 
	 * @param userCode
	 * @return
	 */
	private ResultBean<ChallenterUserInfoBean> findUserInfoStatus(int userCode) {
		// 正在审核的报名信息
		ChallenterUserInfoBean challenterUserInfoBean = challterUserInfoBll
				.getLastOneChallenterUserInfo(userCode);
		if (challenterUserInfoBean == null
				|| "unpass".equals(challenterUserInfoBean.getState())) {
			return new ResultBean<>(false, "请您先报名，暂时不能操作", null);
		}
		if ("oncheck".equals(challenterUserInfoBean.getState())) {
			return new ResultBean<>(false, "报名信息正在审核，暂时不能操作", null);
		}
		return new ResultBean<>(true, "", challenterUserInfoBean);

	}

	/***
	 * 判断今日的直播是否发布了五次
	 * 
	 * @param key
	 * @return
	 */
	private boolean isFiveTimeMem(String key) {
		String times = memcachedClientAdapter.get(key, String.class);
		if (times == null || Integer.parseInt(times) < 5) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 判断今日的直播是否发布了五次
	 * 
	 * @param key
	 * @return
	 */
	private boolean isFiveTime(int userCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String begin = sdf.format(now);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, 1);
		String end = sdf.format(calendar.getTime());
		int count = challenterLiveBll
				.getOneCountLiveInday(userCode, begin, end);
		if (count < 5) {
			return true;
		}
		return false;

	}

	/***
	 * 获取前几条直播信息
	 * 
	 * @param size
	 * @return
	 */
	public ResultBean<List<ChallenterLiveBean>> getTopNumChallenterLiveBean(
			int size) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("M.d Ha");
			List<ChallenterLiveBean> list = challenterLiveBll
					.getTopNumChallenterLiveBean(size, "pass");
			if (list != null) {
				for (ChallenterLiveBean s : list) {
					if (s.getLiveTime() != null) {
						s.setLiveTimeString(sdf.format(s.getLiveTime()));
					}
				}
			}

			return new ResultBean<>(true, "获取成功", list);
		} catch (Exception e) {
			logger.error("ChallenterLiveBean" + e);
			return new ResultBean<>(false, "获取失败", null);
		}
	}

	/***
	 * 审核通过直播信息
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @return
	 */
	public ResultBean<String> passLiveInfo(int opCode, String opName, int code,
			int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			try (DistributedLock lock = new DistributedLock("passLiveInfo"
					+ opCode + "." + actionCode)) {
				lock.weakLock(30, 2);
				return passLiveInfoStory(opCode, opName, actionCode, code,
						sqlSession);
			} catch (Exception e) {
				logger.error("passLiveInfo" + e);
				sqlSession.rollback();
				return new ResultBean<>(false, "审核失败", "");
			}
		}
	}

	/***
	 * 审核通过直播的
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @param sqlSession
	 * @return
	 */
	public ResultBean<String> passLiveInfoStory(int opCode, String opName,
			int actionCode, int code, SqlSession sqlSession) {
		ChallenterLiveBean challenterLiveBean = challenterLiveBll
				.getOneCodeChallenterLiveBean(code, sqlSession);
		if (challenterLiveBean == null) {
			return new ResultBean<>(false, "没有这条记录", "");
		}
		if (!"oncheck".equals(challenterLiveBean.getState())) {
			return new ResultBean<>(false, "信息已经审核过了", "");
		}
		boolean is = isTodayHaveAddTimes(challenterLiveBean.getUserId(),
				challenterLiveBean.getCreateTime(), sqlSession);
		int count = challenterLiveBll.setStateAndTime(code, "pass", sqlSession);
		if (count == 1 && is) {
			LuckyDrawLogBean logBean = new LuckyDrawLogBean();
			logBean.setAccount(challenterLiveBean.getAccount());
			logBean.setActionCode(actionCode);
			logBean.setDrawCount(1);
			logBean.setSource("challengeInfo");
			logBean.setUserId(challenterLiveBean.getUserId());
			// 这就得加抽奖次数了
			LotteryService.setLotteryTimesSqlSession(logBean, sqlSession);
		}
		if (count == 1) {

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(opName);
			operateBean.setUserid(opCode);
			operateBean.setType("liveInfoCheck");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("直播审核通过");
			operationLogBll.addOperationLog(operateBean, sqlSession);
			sqlSession.commit(true);
		}
		return new ResultBean<>(true, "审核通过", "");

	}

	/***
	 * 审核通过直播信息
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @return
	 */
	public ResultBean<String> refuseLiveInfo(int opCode, String opName,
			int code, int actionCode) {
		try (SqlSession sqlSession = getSession()) {
			try (DistributedLock lock = new DistributedLock("refuseLiveInfo"
					+ opCode + "." + actionCode)) {
				lock.weakLock(30, 2);
				return refuseLiveInfoStory(opCode, opName, actionCode, code,
						sqlSession);
			} catch (Exception e) {
				logger.error("refuseLiveInfo" + e);
				sqlSession.rollback();
				return new ResultBean<>(false, "审核失败", "");
			}
		}
	}

	/***
	 * 审核通过直播的
	 * 
	 * @param opCode
	 * @param opName
	 * @param code
	 * @param sqlSession
	 * @return
	 */
	public ResultBean<String> refuseLiveInfoStory(int opCode, String opName,
			int actionCode, int code, SqlSession sqlSession) {
		ChallenterLiveBean challenterLiveBean = challenterLiveBll
				.getOneCodeChallenterLiveBean(code, sqlSession);
		if (challenterLiveBean == null) {
			return new ResultBean<>(false, "没有这条记录", "");
		}
		if (!"oncheck".equals(challenterLiveBean.getState())) {
			return new ResultBean<>(false, "信息已经审核过了", "");
		}
		int count = challenterLiveBll.setStateAndTime(code, "unpass",
				sqlSession);
		if (count == 1) {
			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(opName);
			operateBean.setUserid(opCode);
			operateBean.setType("liveInfoCheck");
			operateBean.setTid(code);
			operateBean.setActionCode(actionCode);
			operateBean.setDescription("直播被拒绝");
			operationLogBll.addOperationLog(operateBean, sqlSession);
			sqlSession.commit(true);
		}
		return new ResultBean<>(true, "直播被拒绝", "");

	}

	/**
	 * 根据页码获取未审核直播信息
	 * 
	 * @param size
	 * @param pageNo
	 * @return
	 */
	public ResultBeanWithPage<List<ChallenterLiveBean>> getChallenterLiveBeansPage(
			int size, int pageNo) {
		try {
			int count = challenterLiveBll
					.getAllCountOncheckChallenterLiveBean();
			List<ChallenterLiveBean> list = challenterLiveBll
					.getPageOncheckChallenterLiveBean(size, pageNo, "oncheck");
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("getChallenterLiveBeansPage" + e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}

	}

	@SuppressWarnings("unchecked")
	public List<ChallenterLiveRankBean> getTopFiveChallenterLiveRank() {
		List<ChallenterLiveRankBean> list = new ArrayList<ChallenterLiveRankBean>();
		String value = null;
		String key = ChallengerConstant.MEM_KEY_PREFIX
				+ ChallengerConstant.simpleDataType.LIVE_RANK.toString();
		try {
			String t = memcachedClientAdapter.get(key, String.class);
			if (t != null && !t.trim().equals("")) {
				value = t;
			} else {
				SameDataBean bean = sameDataBll.getSameDataBean(
						ChallengerConstant.simpleDataType.LIVE_RANK.toString(),
						ChallengerConstant.ACTIVITY_CODE);
				if (bean != null) {
					value = bean.getContent();
					memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
							value);
				}
			}
			if (value != null) {
				ObjectMapper mapper = new ObjectMapper();
				list = mapper.readValue(value, List.class);
			}
		} catch (Exception e) {
			logger.error("getTopFiveChallenterLiveRank", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ChallenterDeathLifeRankBean> getTopFiveChallenterDeathLifeRank() {
		List<ChallenterDeathLifeRankBean> list = new ArrayList<ChallenterDeathLifeRankBean>();
		String value = null;
		String key = ChallengerConstant.MEM_KEY_PREFIX
				+ ChallengerConstant.simpleDataType.DEATH_LIFE_RANK.toString();
		try {
			String t = memcachedClientAdapter.get(key, String.class);
			if (t != null && !t.trim().equals("")) {
				value = t;
			} else {
				SameDataBean bean = sameDataBll.getSameDataBean(
						ChallengerConstant.simpleDataType.DEATH_LIFE_RANK
								.toString(), ChallengerConstant.ACTIVITY_CODE);
				if (bean != null) {
					value = bean.getContent();
					memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
							value);
				}
			}
			if (value != null) {
				ObjectMapper mapper = new ObjectMapper();
				list = mapper.readValue(value, List.class);
			}
		} catch (Exception e) {
			logger.error("getTopFiveChallenterDeathLifeRank", e);
		}
		return list;
	}

	public ResultBean<String> saveDeathLifeRank(Integer staffCode,
			String realName, ChallenterDeathLifeRankBean bean) {
		List<ChallenterDeathLifeRankBean> list = new ArrayList<ChallenterDeathLifeRankBean>();
		String key = ChallengerConstant.MEM_KEY_PREFIX
				+ ChallengerConstant.simpleDataType.DEATH_LIFE_RANK.toString();
		try {
			if (bean.getPlayerString() != null) {
				for (int i = 0; i < bean.getPlayerString().length; i++) {
					ChallenterDeathLifeRankBean b = new ChallenterDeathLifeRankBean();
					b.setPlayer((bean.getPlayerString())[i]);
					b.setAttendCount((bean.getAttendCountString())[i]);
					b.setWinCount((bean.getWinCountString())[i]);

					list.add(b);
				}
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
				String value = mapper.writeValueAsString(list);
				memcachedClientAdapter.set(key, getUntilDayEndSeconds(), value);
				sameDataBll.resetSameDate(
						ChallengerConstant.simpleDataType.DEATH_LIFE_RANK
								.toString(), value,
						ChallengerConstant.ACTIVITY_CODE);
			} else {
				return new ResultBean<String>(false, "没有数据", null);
			}

			return new ResultBean<String>(true, "保存成功", null);
		} catch (Exception e) {
			logger.error("getTeamListData", e);
			return new ResultBean<String>(false, "保存失败", null);
		}
	}

	public ResultBean<String> saveLiveRank(Integer staffCode, String realName,
			ChallenterLiveRankBean bean) {
		List<ChallenterLiveRankBean> list = new ArrayList<ChallenterLiveRankBean>();
		String key = ChallengerConstant.MEM_KEY_PREFIX
				+ ChallengerConstant.simpleDataType.LIVE_RANK.toString();
		try {
			if (bean.getPlayerString() != null) {
				for (int i = 0; i < bean.getPlayerString().length; i++) {
					ChallenterLiveRankBean b = new ChallenterLiveRankBean();
					b.setPlayer((bean.getPlayerString())[i]);
					b.setLiveCount((bean.getLiveCountString())[i]);
					b.setChannel((bean.getChannelString())[i]);
					b.setWinCount((bean.getWinCountString())[i]);

					list.add(b);
				}
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
				String value = mapper.writeValueAsString(list);
				memcachedClientAdapter.set(key, getUntilDayEndSeconds(), value);
				sameDataBll.resetSameDate(
						ChallengerConstant.simpleDataType.LIVE_RANK.toString(),
						value, ChallengerConstant.ACTIVITY_CODE);
			} else {
				return new ResultBean<String>(false, "没有数据", null);
			}

			return new ResultBean<String>(true, "保存成功", null);
		} catch (Exception e) {
			logger.error("getTeamListData", e);
			return new ResultBean<String>(false, "保存失败", null);
		}
	}

	/***
	 * 判断今天有没有加入过直播
	 * 
	 * @return
	 */
	private boolean isTodayHaveAddTimes(int userCode, Date day, SqlSession sql) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = day;
		String begin = sdf.format(now);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, 1);
		String end = sdf.format(calendar.getTime());
		int count = challenterLiveBll.getOneCountLiveInday(userCode, begin,
				end, sql);
		if (count <= 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<ChallenterLiveTimeRankBean> getTopFiveChallenterLiveTimeRank() {
		List<ChallenterLiveTimeRankBean> list = new ArrayList<ChallenterLiveTimeRankBean>();
		String value = null;
		String key = ChallengerConstant.MEM_KEY_PREFIX
				+ ChallengerConstant.simpleDataType.LIVE_TIME_RANK.toString();
		try {
			String t = memcachedClientAdapter.get(key, String.class);
			if (t != null && !t.trim().equals("")) {
				value = t;
			} else {
				SameDataBean bean = sameDataBll.getSameDataBean(
						ChallengerConstant.simpleDataType.LIVE_TIME_RANK
								.toString(), ChallengerConstant.ACTIVITY_CODE);
				if (bean != null) {
					value = bean.getContent();
					memcachedClientAdapter.set(key, getUntilDayEndSeconds(),
							value);
				}
			}
			if (value != null) {
				ObjectMapper mapper = new ObjectMapper();
				list = mapper.readValue(value, List.class);
			}
		} catch (Exception e) {
			logger.error("getTopFiveChallenterLiveTimeRank", e);
		}
		return list;
	}

	public ResultBean<String> saveliveTimeRank(Integer staffCode,
			String realName, ChallenterLiveTimeRankBean bean) {
		List<ChallenterLiveTimeRankBean> list = new ArrayList<ChallenterLiveTimeRankBean>();
		String key = ChallengerConstant.MEM_KEY_PREFIX
				+ ChallengerConstant.simpleDataType.LIVE_TIME_RANK.toString();
		try {
			if (bean.getLiveTimeString() != null) {
				for (int i = 0; i < bean.getLiveTimeString().length; i++) {
					ChallenterLiveTimeRankBean b = new ChallenterLiveTimeRankBean();
					b.setPlayerA((bean.getPlayerAString())[i]);
					b.setPlayerB((bean.getPlayerBString())[i]);
					b.setLiveRadio((bean.getLiveRadioString())[i]);
					b.setLiveTime((bean.getLiveTimeString())[i]);

					list.add(b);
				}
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
				String value = mapper.writeValueAsString(list);
				memcachedClientAdapter.set(key, getUntilDayEndSeconds(), value);
				sameDataBll.resetSameDate(
						ChallengerConstant.simpleDataType.LIVE_TIME_RANK
								.toString(), value,
						ChallengerConstant.ACTIVITY_CODE);
			} else {
				return new ResultBean<String>(false, "没有数据", null);
			}

			return new ResultBean<String>(true, "保存成功", null);
		} catch (Exception e) {
			logger.error("getTeamListData", e);
			return new ResultBean<String>(false, "保存失败", null);
		}
	}

}
