package cn.gyyx.action.service.wd10yearcoser;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.beans.wd10yearcoser.CoserOfficialVideo;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.beans.wd10yearcoser.UserfavoriteBean;
import cn.gyyx.action.bll.lottery.MemcacheUtil;
import cn.gyyx.action.bll.wd10yearcoser.CoserVideoBll;
import cn.gyyx.action.bll.wd10yearcoser.ResourceBll;
import cn.gyyx.action.bll.wd10yearcoser.UserfavoriteBll;
import cn.gyyx.action.bll.wdninestory.QualificationBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.challenger.LotteryService;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.distribute.lock.DistributedLock;

public class UserfavoriteService {
	private int CountByFavoriteNum = 100;
	private ResourceBll resourceBll = new ResourceBll();
	private UserfavoriteBll userfavoriteBll = new UserfavoriteBll();
	private CoserVideoBll coserVideoBll = new CoserVideoBll();
	private QualificationBLL qualificationBLL = new QualificationBLL();
	
	private MemcacheUtil memcacheUtil = new MemcacheUtil();
	private XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();
	
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	//用户资源点赞
	public ResultBean<String> saveUserfavorite(UserfavoriteBean userfavorite,int actionCode) throws Exception{
		//返还结果
		ResultBean<String> result = null;
		//是否点赞过  //判断用户是否点过赞需要传用户ID和资源id chenglong
		List<UserfavoriteBean> favoriteList = userfavoriteBll.findFavoriteByResource(userfavorite.getResourceCode(),userfavorite.getUserCode(),false);
		if (favoriteList != null && favoriteList.size() > 0) {
			result = new ResultBean<String>(false,"已点过赞", null);
			return result;
		}
		//点赞加锁  //对资源加锁chenglong
		try (DistributedLock lock = new DistributedLock("adduserfavoriteNum_" + userfavorite.getResourceCode());
				SqlSession session = getSession();) {
			lock.weakLock(30, 2);
			/*// 获取当前用户的点赞数
			int favoriteNumR = userfavoriteBll.countFavoriteNumByResourceCode(userfavorite.getResourceCode(), session);
			// 当前用户点赞10次增加一次抽奖机会
			if (favoriteNumR % 10 == 0) {
				// 资格信息
				//增加抽奖次数和抽奖次数添加日志
				LuckyDrawLogBean logBean = new LuckyDrawLogBean();
				logBean.setAccount(userfavorite.getUsername());
				logBean.setActionCode(actionCode);
				logBean.setDrawCount(1);
				logBean.setSource("worksFavorite:" + userfavorite.getResourceCode());
				logBean.setUserId(userfavorite.getUserCode());
				// 这就得加抽奖次数
				LotteryService.setLotteryTimesSqlSession(logBean, session);
			}*/
			//保存用户点赞记录
			userfavorite.setType(false);//用户的
			userfavoriteBll.addUserfavorite(userfavorite,session);
			//获取被点赞的资源
			ResourceBean resource = resourceBll.findResourceByCode(userfavorite.getResourceCode(),session);
			//点赞次数+1
			resource.setFavoriteNum(resource.getFavoriteNum() + 1);
			//更新资源
			resourceBll.updateResourceByCode(resource,session);
			//获取被点赞资源的所属用户的点赞数
			int favoriteNum = userfavoriteBll.countFavoriteNumBySourceCode(userfavorite.getSourceCode(),session);
			//判断点赞次数 每获得100个赞增加一次抽奖机会
			if (favoriteNum % CountByFavoriteNum == 0) {
				// 资格信息
				//增加抽奖次数和抽奖次数添加日志
				LuckyDrawLogBean logBean = new LuckyDrawLogBean();
				logBean.setAccount(resource.getUsername());
				logBean.setActionCode(actionCode);
				logBean.setDrawCount(1);
				logBean.setSource("worksFavorite:" + userfavorite.getResourceCode());
				logBean.setUserId(resource.getUserCode());
				// 这就得加抽奖次数
				LotteryService.setLotteryTimesSqlSession(logBean, session);
				
			}
			result = new ResultBean<String>(true,"点赞成功", null);
			//事物提交
			session.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResultBean<String>(false,"点赞失败", null);
			return result;
		} 
		
	}
	
	//官方视频点赞
	public ResultBean<String> saveOfficialVideofavorite(UserfavoriteBean userfavorite) throws Exception{
		//返还结果
		ResultBean<String> result = null;
		//是否点赞过  //判断用户是否点过赞需要传用户ID和资源id chenglong
		List<UserfavoriteBean> favoriteList = userfavoriteBll.findFavoriteByResource(userfavorite.getResourceCode(),userfavorite.getUserCode(),true);
		if (favoriteList != null && favoriteList.size() > 0) {
			result = new ResultBean<String>(false,"已点过赞", null);
			return result;
		}
		//点赞加锁  //对资源加锁chenglong
		try (DistributedLock lock = new DistributedLock("adduserfavoriteNum_" + userfavorite.getResourceCode());
				SqlSession session = getSession();) {
			lock.weakLock(30, 2);
			
			//保存用户点赞记录
			userfavorite.setType(true);
			userfavoriteBll.addUserfavorite(userfavorite,session);
			//获取被点赞的资源
			CoserOfficialVideo resource = coserVideoBll.getCoserVideo(userfavorite.getResourceCode(),session);
			//点赞次数+1
			resource.setFavoriteNum(resource.getFavoriteNum() + 1);
			//更新资源
			coserVideoBll.update(resource,session);
			result = new ResultBean<String>(true,"点赞成功", null);
			//事物提交
			session.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResultBean<String>(false,"点赞失败", null);
			return result;
		} 
		
	}
	
	public ResultBean<String> saveResourceClick(int code) throws Exception{
		//返还结果
		ResultBean<String> result = null;
		if (code < 0) {
			result = new ResultBean<String>(false,"点击失败", null);
		}
		try (DistributedLock lock = new DistributedLock("addResourceClickNum");
				SqlSession session = getSession();) {
			lock.weakLock(30, 2);
			//获取被点赞的资源
			ResourceBean resource = resourceBll.findResourceByCode(code,session);
			//点赞次数+1
			resource.setClickNum(resource.getClickNum() + 1);
			//更新资源
			resourceBll.updateResourceByCode(resource,session);
			result = new ResultBean<String>(true,"点击成功", null);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResultBean<String>(false,"点击失败", null);
			throw new Exception(String.format("className:%s,methord:%s", "UserfavoriteService.class","saveResourceClick"));
		} 
		return result;
	}
	
	
	/*try (DistributedLock lock = new DistributedLock("getExchangePrize310")) {

		lock.weakLock(30, 1);

		SqlSession sqlSession = getSession();
		try {

			ScoreBean scoreBean = WdTenYearService.getEScoreBean(
					userInfo.getUserId(), sqlSession);

			ResultBean<PrizeConfigBean> prizeConfigResult = getPrizeConfigInMem(
					prizeCode, scoreBean, sqlSession);
			if (!prizeConfigResult.getIsSuccess()) {
				return new ResultBean<>(false, "您的积分不足", "");
			}
			PrizeConfigBean prizeConfig = prizeConfigResult.getData();
			logger.info("getExchangePrize prizeConfig"+prizeConfig.getPrizeCode()+" "+prizeConfig.getDayBum());
			resultBean = isPrizeLeft(actionCode, userInfo, prizeConfig);
			if (!resultBean.getIsSuccess()) {
				return resultBean;
			}
			if (!canExchage(userInfo, actionCode, prizeConfig, sqlSession)) {
				return new ResultBean<>(false, "冷冻时间未到，不能领取", "");
			}
			// 奖品信息
			PrizeBean prizeBean = lotteryBll.getPrizeByCode(prizeCode,
					sqlSession);
			// 兑换奖品
			resultBean = exchangePrize(sqlSession, userInfo, prizeBean,
					address, prizeConfig);
			// 获取账号绑定信息
			RoleAccountBean roleAccount = getRoleAccountBeanBtMem(
					userInfo.getAccount(), actionCode);
			sqlSession.commit(true);
			resultBean = reciveGiftToGame(resultBean,
					roleAccount.getServerCode(), userInfo.getAccount(),
					prizeConfig);
			return resultBean;
		} catch (Exception e) {
			logger.warn("getExchangePrize" + e);
			sqlSession.rollback();
			return new ResultBean<>(false, "系统错误！", "");
		} finally {
			sqlSession.close();
		}
	} catch (Exception e) {
		logger.warn("getExchangePrize" + e);
		return new ResultBean<>(false, "网络超时，请重新登录！", "");
	}*/
	
}
