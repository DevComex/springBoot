/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午4:50:50
 * 版本号：v1.0
 * 本类主要用途叙述：有关抽奖的模块
 */

package cn.gyyx.action.service.wd10yearcoser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.exchangescore.PrizeExchangeLogBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.exchangescore.PrizeExchangeLogBll;
import cn.gyyx.action.bll.lottery.AddressBLL;
import cn.gyyx.action.bll.lottery.LuckyDrawLogBll;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.lottery.QualificationBLL;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class LotteryService {
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	private UserLotteryBll userLotteryBll = new UserLotteryBll();
	private NewlotteryService lottery = new NewlotteryService();
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LotteryService.class);
	private LuckyDrawLogBll luckyDrawBll = new LuckyDrawLogBll();
	private AddressBLL addressBLL = new AddressBLL();
	
	private QualificationBLL qualificationBLL = new QualificationBLL();
	private NewUserLotteryBll newuserLotteryBll = new NewUserLotteryBll();
	private PrizeExchangeLogBll prizeExchageLogBll = new PrizeExchangeLogBll();

	/****
	 * 设定抽奖次数 在程序内部开启sqlsession
	 */
	public static ResultBean<String> setLotteryTimes(LuckyDrawLogBean logBean) {
		QualificationBLL qualificationBLL = new QualificationBLL();
		LuckyDrawLogBll luckyDrawLogBll = new LuckyDrawLogBll();
		try (SqlSession sqlSession = getSession()) {
			try {
				luckyDrawLogBll.addLuckyDrawLog(logBean, sqlSession);
				qualificationBLL.setTimes(logBean.getUserId(),
						logBean.getActionCode(), logBean.getDrawCount(),
						sqlSession);
				sqlSession.commit(true);
				return new ResultBean<>(true, "抽奖次数修改成功", "");
			} catch (Exception e) {
				logger.error("setLotteryTimes error" + e);
				sqlSession.rollback();
				return new ResultBean<>(false, "抽奖次数修改失败", "");
			}
		}

	}

	/****
	 * 设定抽奖次数 在程序内部开启sqlsession
	 */
	public static void setLotteryTimesSqlSession(LuckyDrawLogBean logBean,
			SqlSession sqlSession) {
		QualificationBLL qualificationBLL = new QualificationBLL();
		LuckyDrawLogBll luckyDrawLogBll = new LuckyDrawLogBll();

		luckyDrawLogBll.addLuckyDrawLog(logBean, sqlSession);
		qualificationBLL.setTimes(logBean.getUserId(), logBean.getActionCode(),
				logBean.getDrawCount(), sqlSession);

	}

	/***
	 * 分享的实现
	 * 
	 * contingent 队伍分享 challenge 挑战分享 index 首页分享
	 * 
	 * @return
	 */
	public ResultBean<String> shareSomeThing(String type, int actionCode,
			HttpServletRequest request) {

		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(true, "没有登录不增加抽奖次数", "没有登录不增加抽奖次数");
		}
		if (!"contingent".equals(type) && !"challenge".equals(type)
				&& !"index".equals(type)) {
			return new ResultBean<>(true, "没有" + type + "此来源", "没有此来源");
		}
		String key = actionCode + "." + userInfo.getAccount() + "." + type;
		try (DistributedLock lock = new DistributedLock("shareSomeThing"
				+ userInfo.getUserId() + "." + actionCode)) {
			// 判断他今天有没有分享过
			if (!isTodayShare(userInfo.getAccount(), type, key, actionCode)) {
				return new ResultBean<>(true, "今日已经分享过不增加抽奖次数",
						"今日已经分享过不增加抽奖次数");
			}
			String source = "share" + type;
			lock.weakLock(30, 2);
			if (!isTodayHaveAddTimes(userInfo.getUserId(), source)) {
				return new ResultBean<>(true, "今日已经分享过不增加抽奖次数",
						"今日已经分享过不增加抽奖次数");
			}
			LuckyDrawLogBean logBean = new LuckyDrawLogBean();
			logBean.setUserId(userInfo.getUserId());
			logBean.setSource(source);
			logBean.setDrawCount(1);
			logBean.setActionCode(actionCode);
			logBean.setAccount(userInfo.getAccount());
			setLotteryTimes(logBean);
			return new ResultBean<>(true, "分享增加次数", "");
		} catch (Exception e) {
			logger.error("shareSomeThing" + e);
			memcachedClientAdapter.set(key, getUntilDayEndSeconds(), "");
			return new ResultBean<>(false, "系统小差", "");

		}

	}

	/***
	 * 判断今天有没有相同类型的增加记录
	 * 
	 * @return
	 */
	private boolean isTodayHaveAddTimes(int userCode, String source) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String begin = sdf.format(now);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, 1);
		String end = sdf.format(calendar.getTime());
		LuckyDrawLogBean logBean = luckyDrawBll.getOneSourceluckyDrawLogInTime(
				userCode, begin, end, source);
		if (logBean == null) {
			return true;
		}
		return false;
	}

	/***
	 * 判断今日是否分享过
	 * 
	 * @param account
	 * @return
	 */
	private boolean isTodayShare(String account, String type, String key,
			int actionCode) {

		String times = memcachedClientAdapter.get(key, String.class);
		if (times == null || "".equals(times)) {
			memcachedClientAdapter.set(key, getUntilDayEndSeconds(), "yep");
			return true;
		}
		return false;
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
	 * 就抽个奖
	 * 
	 * @param actionCode
	 * @param request
	 * @param sqlSession
	 * @return
	 */
	public ResultBean<UserLotteryBean> letUsLottery(int actionCode,
			HttpServletRequest request) {
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			return new ResultBean<>(false, "请您先登录", null);
		}
		// 活动时间
		ResultBean<UserLotteryBean> resultBean = isActionOver(actionCode);
		if (!resultBean.getIsSuccess()) {
			return resultBean;
		}
		try (DistributedLock lock = new DistributedLock("letUsLottery"
				+ userInfo.getUserId() + "." + actionCode)) {
			lock.weakLock(30, 2);
			// 资格获取
			
			QualificationBean qualificationBean = qualificationBLL
					.selectByUserAndAction(
							String.valueOf(userInfo.getUserId()),
							String.valueOf(actionCode));
			// 资格判定
			if (qualificationBean == null) {
				return new ResultBean<>(false, "很遗憾，您的抽奖机会已用完，赶紧去增加抽奖次数吧~", null);
			}
			if (qualificationBean.getLotteryTime() <= 0) {
				return new ResultBean<>(false, "很遗憾，您的抽奖机会已用完，赶紧去增加抽奖次数吧~", null);
			}
			// 你去抽奖
			try (SqlSession sqlSession = getSession()) {
				ResultBean<UserLotteryBean> result = lotteryStory(actionCode, userInfo, sqlSession);
				// 获取剩余抽奖次数
				result.getData().setLotterytime(qualificationBean.getLotteryTime() - 1);
				return result;
			}
			
		} catch (Exception e) {
			logger.error("wishAndLottery" + e);
			return new ResultBean<>(false, "网络超时", null);
		}

	}

	/***
	 * 抽奖的主体
	 * 
	 * @param actionCode
	 * @param userInfo
	 * @param sqlSession
	 * @return
	 * @throws LotteryException
	 */
	private ResultBean<UserLotteryBean> lotteryStory(int actionCode,
			UserInfo userInfo, SqlSession sqlSession) {
		try {
			// 事务内部需要再做一个资格判定
			QualificationBean qualificationBean = qualificationBLL
					.selectLotteryInfoByUserAndActionSqlsession(
							userInfo.getUserId(), actionCode, sqlSession);
			// 资格判定
			if (qualificationBean == null) {
				return new ResultBean<>(false, "您并没有抽奖机会，请积极参加活动", null);
			}
			if (qualificationBean.getLotteryTime() <= 0) {
				return new ResultBean<>(false, "抽奖次数已然用尽", null);
			}
			// 去抽了一个奖
			ResultBean<UserLotteryBean> resultBean = lottery.lotteryByDataBase(
					actionCode, userInfo.getUserId(), "byChance",
					userInfo.getAccount(), "二〇一六", 1111, userInfo.getLoginIP(),
					getPrizeBean(actionCode), sqlSession);
			// 抽奖机会日志
			LuckyDrawLogBean logBean = new LuckyDrawLogBean();
			logBean.setAccount(userInfo.getAccount());
			logBean.setActionCode(actionCode);
			logBean.setDrawCount(-1);
			logBean.setSource("lottery");
			logBean.setUserId(userInfo.getUserId());
			
//			return resultBean;
			if (!resultBean.getIsSuccess()) {
				return resultBean;
			}
			//抽奖次数-1
			LotteryService.setLotteryTimesSqlSession(logBean, sqlSession);
			sqlSession.commit(true);
			return resultBean;
			// 再去处理又没有相同的奖
//			return isSameGift(userInfo.getAccount(), actionCode, resultBean
//					.getData().getPrizeChinese(), resultBean, logBean,
//					sqlSession);
		} catch (Exception e) {
			logger.error("lotteryStory" + e);
			sqlSession.rollback();
			return new ResultBean<>(false, "系统挂了", null);
		}
	}

	/***
	 * 判断中没中过相同的奖
	 * 
	 * @param userAccount
	 * @param actionCode
	 * @param gift
	 * @param sqlSession
	 * @return
	 */
	private ResultBean<UserLotteryBean> isSameGift(String userAccount,
			int actionCode, String gift,
			ResultBean<UserLotteryBean> resultBean, LuckyDrawLogBean logBean,
			SqlSession sqlSession) {
		List<UserInfoBean> userLotteryBean = newuserLotteryBll
				.getOneInActionOnePrizeInfoSqlsession(actionCode, userAccount,
						gift, sqlSession);
		// 这就得减少抽奖次数了
		if (userLotteryBean == null || userLotteryBean.size() <= 1) {
			LotteryService.setLotteryTimesSqlSession(logBean, sqlSession);
			sqlSession.commit(true);
			return resultBean;
		} else {
			sqlSession.rollback();
			LotteryService.setLotteryTimesSqlSession(logBean, sqlSession);
			sqlSession.commit(true);
			return new ResultBean<>(true, "谢谢参与", getUserLotteryBean());
		}
	}

	/***
	 * 获取纪念奖
	 * 
	 * @param level
	 * @return
	 */
	private PrizeBean getPrizeBean(int actionCode) {
		PrizeBean prizeBean = new PrizeBean();
		prizeBean.setActionCode(actionCode);
		prizeBean.setChinese("谢谢参与");
		prizeBean.setCode(0);
		prizeBean.setIsReal("thanks");
		prizeBean.setEnglish("thank");
		prizeBean.setNum(2);
		return prizeBean;
	}

	/***
	 * 获取纪念奖
	 * 
	 * @param level
	 * @return
	 */
	private UserLotteryBean getUserLotteryBean() {
		UserLotteryBean userLotteryBean = new UserLotteryBean();
		userLotteryBean.setUserCode(0);
		userLotteryBean.setPrizeChinese("谢谢参与");
		userLotteryBean.setConfigCode(2);
		userLotteryBean.setPrizeEnglish("thank");
		userLotteryBean.setIsReal("thanks");
		return userLotteryBean;
	}

	/***
	 * 判断活动是否结束
	 * 
	 * @param actionCode
	 * @return
	 */
	public ResultBean<UserLotteryBean> isActionOver(int actionCode) {
		ContrParmBean actionConfig = getActionConfig(actionCode);
		Date nowDate = new Date();
		if (nowDate.getTime() < actionConfig.getActivityStart().getTime()) {
			return new ResultBean<>(false, "该活动尚未开始", null);
		}

		if (nowDate.getTime() >= actionConfig.getActivityEnd().getTime()) {
			return new ResultBean<>(false, "该活动已结束", null);
		}
		return new ResultBean<>(true, "活动依旧", null);
	}

	/***
	 * 将活动信息放入缓存
	 * 
	 * @param account
	 * @param actionCode
	 * @return
	 */
	private ContrParmBean getActionConfig(int actionCode) {
		String key = actionCode + "ContrParmBean";
		ContrParmBean contrParmBean = memcachedClientAdapter.get(key,
				ContrParmBean.class);
		if (contrParmBean == null) {
			contrParmBean = userLotteryBll.getContrParm(actionCode);

			memcachedClientAdapter.set(key, 3600 * 5, contrParmBean);
		}
		return contrParmBean;
	}

	/**
	 * 获取抽奖资格
	 * 
	 * @param request
	 * @param actionCode
	 * @return
	 */
	public ResultBean<Integer> getLotteryTime(HttpServletRequest request,
			int actionCode) {
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			logger.info("UserInfo", userInfo);
			if (userInfo == null) {
				return new ResultBean<>(false, "请您先登录", 0);
			}
			QualificationBean qualificationBean = qualificationBLL
					.selectByUserAndAction(
							String.valueOf(userInfo.getUserId()),
							String.valueOf(actionCode));
			if (qualificationBean == null) {
				return new ResultBean<>(false, "没有抽奖资格", 0);
			}
			return new ResultBean<>(true, "",
					qualificationBean.getLotteryTime());

		} catch (Exception e) {
			logger.error("getLotteryTime" + e);
			return new ResultBean<>(false, "获取失败", 0);
		}
	}

	/***
	 * 获取用户的中奖信息
	 * 
	 * @param actionCode
	 * @param request
	 * @return
	 */
	public ResultBean<List<UserInfoBean>> getAllLotteryInfoByUser(
			int actionCode, HttpServletRequest request) {
		ResultBean<List<UserInfoBean>> resultBean = new ResultBean<>();
		// 获得用户信息
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			/*userInfo = new UserInfo();
			userInfo.setAccount("d61255840");
			userInfo.setUserId(294776);*/
			logger.info("UserInfo", userInfo);
			// 判断登录信息
			if (userInfo == null) {
				return new ResultBean<>(false, "请重新登陆", null);
			}

			List<UserInfoBean> list = userLotteryBll
					.wishGetUserLotteryByUserCode(actionCode,
							userInfo.getAccount());
			resultBean.setProperties(true, "成功", list);
			logger.info(" getAllLotteryInfoByUser resultBean", resultBean);
			return resultBean;

		} catch (Exception e) {
			logger.error("getAllLotteryInfoByUser" + e);
			return new ResultBean<>(false, "系统找不到了", null);
		}

	}

	/**
	 * 增加地址
	 * 
	 * @param prizeExchangeLogBean
	 * @return
	 */
	public ResultBean<String> addAddress(HttpServletRequest request,
			AddressBean address, int actionCode) {
		UserInfo userInfo = SignedUser.getUserInfo(request);
	/*	userInfo = new UserInfo();
		userInfo.setAccount("d61255840");
		userInfo.setUserId(294776);*/
		// 判断登录信息
		if (userInfo == null) {
			return new ResultBean<>(false, "请重新登陆", null);
		}
		try {
			address.setUserAccount(userInfo.getAccount());
			address.setUserCode(userInfo.getUserId());
			addressBLL.addAddress(address);
			return new ResultBean<>(true, "添加地址成功", null);
		} catch (Exception e) {
			logger.error("addAddress" + e);
			return new ResultBean<>(false, "添加失败", null);
		}

	}

}
