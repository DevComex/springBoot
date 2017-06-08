package cn.gyyx.action.service.wdlight2017;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.ContrParmBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean;
import cn.gyyx.action.beans.wdlight2017.LightBean;
import cn.gyyx.action.beans.wdlight2017.UserinfoBean;
import cn.gyyx.action.beans.wdlight2017.WishBean;
import cn.gyyx.action.beans.wdlight2017.base.Constants;
import cn.gyyx.action.beans.wdlight2017.base.Constants.LightType;
import cn.gyyx.action.beans.wdlight2017.base.Constants.MapperActionCode;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.newLottery.NewUserLotteryBll;
import cn.gyyx.action.bll.wdlight2017.LightBll;
import cn.gyyx.action.bll.wdlight2017.UserinfoBll;
import cn.gyyx.action.bll.wdlight2017.WishBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.agent.CallWebServiceAgent;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.action.service.newLottery.NewlotteryService;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WishService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WishService.class);
	
	private WishBll wishBll = new WishBll();
	private LightBll lightBll = new LightBll();
	private UserinfoBll userinfoBll = new UserinfoBll();
	private NewUserLotteryBll userLotterynewBll = new NewUserLotteryBll();
	
	private NewlotteryService lottery = new NewlotteryService();
	
	private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
	
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	private static Cache<String,Object> wishServiceCache = CacheBuilder.newBuilder()
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.build(); 
	
	/***
	 * 根据层级获取愿望
	 * 
	 * @return
	 */
	public ResultBean<List<WishBean>> getWishsBylevel (int level) {
		ResultBean<List<WishBean>> resultBean = new ResultBean<>();
		try {
			// 从缓存中查找
			/*List<WishBean> list = new ArrayList<>();
			String cacheKey = "light2017_getWishsBylevel_" + level + Constants.actionCode;
			List<WishBean> cacheList = memcachedClientAdapter.get(cacheKey, List.class);
			if (cacheList != null && cacheList.size() > 0) {
				list = cacheList;
			} else {
				// 查询num条数据
				list = wishBll.getWishsBylevel(level);
				if (list != null && list.size() > 0) {
					memcachedClientAdapter.set(cacheKey, 300, list);
				}
			}*/
			List<WishBean> list = wishBll.getWishsBylevel(level);
			
			
			// 获取当前层数所有许愿通过的人数
			int num = wishBll.getWishUserCountByLevel(level);
			resultBean.setData(list);
			resultBean.setTotal(num);
			resultBean.setIsSuccess(true);
			resultBean.setMessage("获取第"+level+"层许愿数据成功");
		} catch (Exception e) {
			logger.warn("getWishsBylevel",e);
			resultBean.setIsSuccess(false);
			resultBean.setMessage("获取第"+level+"层许愿数据失败");
		}
		return resultBean;
	}
	
	
	/***
	 * 获取用户本次需要要消耗的积分
	 * 
	 * @return
	 */
	public ResultBean<Integer> getConsumeScoreByLevelAndLightType (int userId,int level, int lightType) {
		ResultBean<Integer> resultBean = new ResultBean<>();
		try {
			// 获取用户在该层第几次抽奖
			int wishNum = wishBll.getUserWishNumByLevel(userId, level) + 1;
			// 获取消耗积分
			int consumeScore = getScore(level, wishNum, lightType);
			resultBean.setData(consumeScore);
			resultBean.setIsSuccess(true);
			resultBean.setMessage("获取本次许愿消耗的积分成功");
		} catch (Exception e) {
			logger.warn("getConsumeScoreByLevelAndLightType",e);
			resultBean.setIsSuccess(false);
			resultBean.setMessage("获取本次许愿消耗的积分失败");
		}
		return resultBean;
	}
	
	/***
	 * 用户许愿
	 * 
	 * @return
	 * @throws  
	 * @throws LotteryException 
	 */
	public ResultBean<UserLotteryBean> userWish(int userId,int level,String ip,String content) {
		logger.debug(String.format("用户【%s】获取点亮2017活动时间:",userId));
		// 判断活动时间
		ResultBean<UserLotteryBean>[] result = new ResultBean[1];
		if(!checkAndCacheRightTimeResult(result)){
			return result[0];
		};
		logger.debug(String.format("用户【%s】获取用户信息:",userId));
		UserinfoBean[] userinfoBeanArr = new UserinfoBean[1]; 
		if(!checkAndCacheUserinfo(result, userId, userinfoBeanArr)){
			return result[0];
		};
		UserinfoBean userinfoBean = userinfoBeanArr[0];
		
		logger.debug(String.format("用户【%s】开始获取所有灯的信息:",userId));
		List<LightBean> lightlist = lightBll.getLightAll();
		LightBean currentLight = lightlist.get(level - 1);
		int lightType = currentLight.getLightType();
		
		ResultBean<UserLotteryBean> resultBean = null;
		logger.debug(String.format("用户【%s】许愿开始:",userId));
		try (SqlSession sqlSession = getSession()) {
			try (DistributedLock lock = new DistributedLock("wdlight2017_lottery"
					+ userId + "." + Constants.actionCode)) {
				lock.weakLock(60, 88);
				
				// 得到账号将要消耗的分数
				// 获取用户在该层第几次抽奖
				int wishNum = wishBll.getUserWishNumByLevel(userId, level) + 1;
				// 获取消耗积分
				int consumeScore = getScore(level, wishNum, lightType);
				int userConsumeScore = userinfoBean.getConsumeScore();
				int totalConsumeScore = consumeScore + userConsumeScore;
				// 获取用户剩余积分
				int score = userinfoBean.getScore();
				// 比较积分 充足可以抽奖
				if (score >= totalConsumeScore) {
					// 根据层级 获取对应的actioncode
					int lotteryMapperActionCode = MapperActionCode.getActionCode(level);
					// 开始抽奖
					resultBean = lottery.lotteryByDataBase(
							lotteryMapperActionCode, userinfoBean.getUserId(), "byChance",
							userinfoBean.getAccount(), userinfoBean.getServerName(),
							userinfoBean.getServerId(), ip,
							getPrizeBean(Constants.actionCode), sqlSession);
					logger.debug(String.format("9.用户【%s】抽奖完成；",userId));
					
					UserLotteryBean userLottery = resultBean.getData();
					
					// 更新用户积分消耗信息
					UserinfoBean modifyInfo = new UserinfoBean();
					modifyInfo.setCode(userinfoBean.getCode());
					modifyInfo.setConsumeScore(totalConsumeScore);
					userinfoBll.updateUserinfoBean(modifyInfo,sqlSession);
					logger.debug(String.format("12.更新用户【%s】积分消耗信息",userId));
					
					// 保存用户愿望
					WishBean wishBean = new WishBean();
					wishBean.setContent(content);
					wishBean.setCreateTime(new Date());
					wishBean.setLevel(level);
					wishBean.setPrizeCode(userLottery.getPrizeCode());
					wishBean.setPrizeName(userLottery.getPrizeChinese());
					wishBean.setPresenttype(userLottery.getIsReal());
					wishBean.setRoleName(userinfoBean.getRoleName());
					wishBean.setServerId(userinfoBean.getServerId());
					wishBean.setServerName(userinfoBean.getServerName());
					wishBean.setStatus(1); // 0-未通过 1-待审核 2-审核通过
					wishBean.setUserId(userId);
					wishBll.addWishBean(wishBean, sqlSession);
					
					// 提交事务
					sqlSession.commit();
					logger.debug(String.format("用户【%s】许愿结束。",userId));
					
				} else {
					return new ResultBean<>(false, "-1", null);
				}
			} catch (Exception e) {
			    // 线程被 interrupt 或到达超时时间会引发
				logger.warn("userWish warn:" , e);
				sqlSession.rollback();
				return new ResultBean<>(false, "-4", null);
			}
			logger.debug(String.format("--用户【%s】发奖开始",userId));
			// 游戏发奖
			resultBean = reciveGiftToGame(resultBean,
					userinfoBean.getServerId(), userinfoBean.getAccount(), sqlSession);
			logger.debug(String.format("用户【%s】发奖结束--",userId));
			// 根据许愿人数 设置每层灯的状态
			logger.debug(String.format("--用户【%s】设置灯的状态",userId));
			int num = wishBll.getWishUserCountByLevel(level);
			if (lightlist != null && lightlist.size() > 0) {
				updateLightState(level, lightlist, currentLight, lightType, num);
			}
			logger.debug(String.format("--用户【%s】灯的状态设置结束",userId));
			return resultBean;
		}
	}


	private boolean checkAndCacheUserinfo(ResultBean<UserLotteryBean>[] rightTimeResult, final int userId, UserinfoBean[] userinfoBean) {
		// 获取绑定用户信息
		try {
			userinfoBean[0] = (UserinfoBean) wishServiceCache.get("userinfoBean_" + userId + Constants.actionCode,
					new Callable<Object>() {
						@Override
						public Object call() throws Exception {
							return userinfoBll.getUserinfoBeanByUserId(userId);
					}
			});
		} catch (ExecutionException e) {
			rightTimeResult[0] = new ResultBean<>(false, "-6", null);
			return false;
		} 
		// 进行抽奖
		if (userinfoBean[0] == null) {
			rightTimeResult[0] = new ResultBean<>(false, "-6", null);
			return false;
		}
		return true;
	}


	private boolean checkAndCacheRightTimeResult(ResultBean<UserLotteryBean>[] rightTimeResult) {
		try {
			rightTimeResult[0] = (ResultBean<UserLotteryBean>) wishServiceCache.get("rightTimeResult_"+ Constants.actionCode,
					new Callable<Object>() {
						@Override
						public Object call() throws Exception {
							return isRightTime(Constants.actionCode, getSession());
						}
					});
		} catch (ExecutionException e) {
			logger.warn("rightTimeResult warn:" , e);
			return false;
		}
		return rightTimeResult[0].getIsSuccess();
	}

	/**
	 * @Title: updateLightState
	 * @description 
	 *    TODO
	 * @action
	 *    add: bozch 2017年1月6日 上午3:37:34
	 * 
	 * @param level
	 * @param sqlSession
	 * @param lightlist
	 * @param currentLight
	 * @param lightType
	 * @param num void
	 * @throws ExecutionException 
	 */
	private void updateLightState(int level, List<LightBean> lightlist, LightBean currentLight,
			int lightType, int num) {
		// 查找对应层数的人数限制
		int limitNum = currentLight.getLimitNum();
		// 计算出当前灯的状态
		int lightTypeForLevel = lightBll.getCurrentLightType(limitNum, num);
		logger.debug(String.format("15.获取【第%d层】许愿的人数为【%d】",level,num));
		// 更新数据
		if (lightType != lightTypeForLevel && lightTypeForLevel > lightType ) {
			LightBean updateCurrentLightBean = new LightBean();
			updateCurrentLightBean.setCode(currentLight.getCode());
			updateCurrentLightBean.setLightType(lightTypeForLevel);
			lightBll.updateLight(updateCurrentLightBean);
			logger.debug(String.format("16.【第%d层】灯的状态有变化,进行更新状态",level));
			// 要点亮下一层的数据(全亮要点亮下一层，如果是第四层级不用管了)
			if (lightTypeForLevel == 3 && level != 4) {
				LightBean nextLightBean = lightlist.get(level);
				LightBean updateNextLightBean = new LightBean();
				updateNextLightBean.setCode(nextLightBean.getCode());
				updateNextLightBean.setLightType(1);
				lightBll.updateLight(updateNextLightBean);
				logger.debug(String.format("17.如果【第%d层】灯的状态为全亮，点亮下一层灯",level));
			}
		}
	}
	
	/***
	 * 根据层级获取用户的愿望
	 * 
	 * @return
	 */
	public ResultBean<List<WishBean>> getMyWishAll (int userId) {
		ResultBean<List<WishBean>> resultBean = new ResultBean<>();
		try {
			List<WishBean> list = wishBll.getMyWishAll(userId);
			resultBean.setData(list);
			resultBean.setIsSuccess(true);
			resultBean.setMessage("获取用户所有许愿数据成功");
		} catch (Exception e) {
			logger.warn("getMyWishAll:" + e);
			resultBean.setIsSuccess(false);
			resultBean.setMessage("获取用户所有许愿数据失败");
		}
		return resultBean;
	}
	
	/***
	 * 获取所有许愿获奖信息
	 * 
	 * @return
	 */
	public ResultBean<List<WishBean>> findWishAll (int num) {
		ResultBean<List<WishBean>> resultBean = new ResultBean<>();
		try {
			/*// 查询所有的个数
			String numCacheKey = "light2017_alllottery_num_" + Constants.actionCode;
			int sum = 0;
			
			// 从缓存中查找
			String cacheKey = "light2017_alllottery_" + Constants.actionCode;
			List<WishBean> list = new ArrayList<>();
			List<WishBean> cacheList = memcachedClientAdapter.get(cacheKey, List.class);
			if (cacheList != null && cacheList.size() > 0) {
				list = cacheList;
				if (memcachedClientAdapter.get(numCacheKey, int.class) != null) {
					sum = memcachedClientAdapter.get(numCacheKey, int.class);
				} else {
					sum = wishBll.getWishCountByStatus(2);
					// 增加缓存
					memcachedClientAdapter.set(numCacheKey, 300, Integer.toString(sum));
				}
			} else {
				// 查询num条数据
				list = wishBll.findWishAll(num);
				if (list != null && list.size() > 0) {
					memcachedClientAdapter.set(cacheKey, 300, list);
				}
				sum = wishBll.getWishCountByStatus(2);
				memcachedClientAdapter.set(numCacheKey, 300, Integer.toString(sum));
			}*/
			List<WishBean> list = wishBll.findWishAll(num);
			int sum = wishBll.getWishCountByStatus(2);
			resultBean.setTotal(sum);
			resultBean.setData(list);
			resultBean.setIsSuccess(true);
			resultBean.setMessage("获取所有许愿数据成功");
		} catch (Exception e) {
			logger.warn("findWishAll:" , e);
			resultBean.setIsSuccess(false);
			resultBean.setMessage("获取所有许愿数据失败");
		}
		return resultBean;
	}
	
	
		
	/***
	 * 判断日期
	 * 
	 * @return
	 */
	private ResultBean<UserLotteryBean> isRightTime(int actionCode,
			SqlSession sqlSession) {
		Date nowDate = new Date();
		// 配置活动的基本信息
		ContrParmBean controParm = new ContrParmBean();
		try {
			controParm = userLotterynewBll.getContrParm(actionCode, sqlSession);
			logger.debug("controParm" + controParm.toString());

		} catch (Exception e) {
			// 抛出活动信息的错误
			logger.warn("isRightTime:" , e);
			return new ResultBean<>(false, "-5", null);
		}
		Date beginDate = controParm.getActivityStart();
		Date endDate = controParm.getActivityEnd();
		if (nowDate.getTime() < beginDate.getTime()) {
			return new ResultBean<>(false, "-7", null);
		} else if (nowDate.getTime() >= endDate.getTime()) {
			return new ResultBean<>(false, "-8", null);
		}
		return new ResultBean<>(true, "0", null);
	}	
	
	
	
	// 获取消耗积分规则
	private static int getScore (int level, int wishNum, int lightType) {
		int consumeScore = 0;
		if (lightType == 3) {
			consumeScore = 2 << (level + wishNum - 1);
		} else {
			consumeScore = 2 << (level + wishNum - 2);
		}
		return consumeScore;
	}
	
	/***
	 * 获取纪念奖
	 * 
	 * @param level
	 * @return
	 */
	private PrizeBean getPrizeBean(int actionCode) {
		logger.debug(String.format("--活动获取【%d】发出纪念奖--",actionCode));
		PrizeBean prizeBean = new PrizeBean();
		prizeBean.setActionCode(actionCode);
		prizeBean.setChinese("称号(限时)");
		prizeBean.setCode(0);
		prizeBean.setIsReal("noRealPrize");
		prizeBean.setEnglish("title");
		prizeBean.setNum(1);
		return prizeBean;
	}
	
	/***
	 * 发奖到游戏里
	 * 
	 * @param resultBean
	 * @param serverCode
	 * @param account
	 * @return
	 */
	private ResultBean<UserLotteryBean> reciveGiftToGame(
			ResultBean<UserLotteryBean> resultBean, int serverCode,
			String account, SqlSession sqlSession) {
		String prizeEnglish = resultBean.getData().getPrizeEnglish().trim();
		String nameString = "";
		if ("nameplate".equals(prizeEnglish)) {
			nameString = "特殊铭牌奖励活动奖励酒葫芦铭牌(140902)";
		} else if ("title".equals(prizeEnglish)) {
			nameString = "2017年官网活动奖励圆梦2017称谓(161128)";
		} else if ("3000Ag".equals(prizeEnglish)) {
			nameString = "2017年官网活动奖励3000银元宝礼包(161128)";
		} else if ("5000Ag".equals(prizeEnglish)) {
			nameString = "2017年官网活动奖励5000银元宝礼包(161128)";
		} else if ("8000Ag".equals(prizeEnglish)) {
			nameString = "2017年官网活动奖励8000银元宝礼包(161128)";
		} else if ("10000Ag".equals(prizeEnglish)) {
			nameString = "2017年官网活动奖励10000银元宝礼包(161128)";
		}
		if (StringUtils.isNotBlank(nameString)) {
			boolean is = reciveGift(account, nameString, serverCode);
			// 发送成功
			if (is) {
				logger.debug(String.format("--light2017活动用户【%s】获取获取虚拟奖品【%s】success--",account,nameString));
				return resultBean;
			} else {
				// 发送失败
				logger.debug(String.format("--light2017活动用户【%s】获取获取虚拟奖品【%s】 fail--",account,nameString));
				sqlSession.rollback();
				return new ResultBean<>(false, "发送到游戏失败！", null);
			}
		} else {
			return resultBean;

		}
	}
	
	/***
	 * 发奖
	 * 
	 * @throws RemoteException
	 */
	public boolean reciveGift(String account, String giftPackage, int serverCode) {
		boolean is = true;
		try {
			// 获取服务器信息
			ServerInfoBean serverInfo = callWebApiAgent
					.getServerStatusFromWebApi(serverCode);
			// 发放物品
			CallWebServiceAgent.givePresents(2, account, giftPackage,
					"20250902235959", "问道点亮2017许愿灯", serverInfo);
		} catch (Exception e) {
			logger.warn("reciveGift" , e);
			is = false;
		}
		return is;
	}
	
	
}
