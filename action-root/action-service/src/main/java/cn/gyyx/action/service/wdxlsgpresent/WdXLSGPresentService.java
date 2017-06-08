package cn.gyyx.action.service.wdxlsgpresent;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.common.base.Throwables;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ActionConfigBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.wdninestory.QualificationBLL;
import cn.gyyx.action.bll.wechat.WeiXinRechargeBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdXLSGPresentService {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdXLSGPresentService.class);
	private QualificationBLL qualificationBLL = new QualificationBLL();
	private WeiXinRechargeBll weiXinRechargeBll = new WeiXinRechargeBll();
	private WdXLSGlotteryService wdXLSGlotteryService = new WdXLSGlotteryService();

	/**
	 * 
	 * 
	 * @Title: getSession
	 * @Description: 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public ResultBean<UserLotteryBean> getLottery(String openId, int actionCode,String os) {
		ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>();
		logger.debug("WdXLSGPresentService: 检查活动时间");
		Date nowDate = new Date();
		ActionConfigBean actionConfig = qualificationBLL.selectActionConfigByCode(actionCode);

		Date beginDate = actionConfig.getHdStartD();
		if (nowDate.getTime() < beginDate.getTime()) {
			return new ResultBean<>(false, os.equals("ios") ? "IOS服尚未开放，敬请期待" : "活动尚未开始，请耐心等待", null);
		}

		Date endDate = actionConfig.getHdEndD();
		if (nowDate.getTime() >= endDate.getTime()) {
			return new ResultBean<>(false, "" + "活动已经结束，谢谢参与", null);
		}
		SqlSession sqlSession = getSession();
		//获取安卓和IOS平台的礼包信息
                String androidCard = weiXinRechargeBll.getCardCodeByUser(404, openId);
                String iosCard = weiXinRechargeBll.getCardCodeByUser(415, openId);
		//领取ios礼包
		if(os=="android"){
		try (DistributedLock lock = new DistributedLock("getLottery<" + openId + ">" + 404)) {
			lock.weakLock(65, 25);
			// 判断是否中过奖;
			logger.info("驯龙三国-领取礼包：openId=" + openId + ";actionCode=" + actionCode + ";os=" + os);
			logger.debug("WdXLSGPresentService: 检查该玩家是否领取过礼包");
			if (androidCard != null||iosCard != null) {
				logger.debug("WdXLSGPresentService: 该玩家{}已领取过礼包", openId);
				result.setIsSuccess(false);
				if(iosCard != null){
				    result.setMessage("亲爱的玩家~<br />您已经领取过IOS独家礼包<br /><br />礼包兑换码：<br />".concat(iosCard));
				}else{
                                    result.setMessage("亲爱的玩家~<br />您已经领取过Android独家礼包<br /><br />礼包兑换码：<br />".concat(androidCard));
				}
				return result;
			} else {
				// 开始抽奖
				logger.debug("WdXLSGPresentService: 该玩家{}未领取礼包,开始领取礼包", openId);
				result = wdXLSGlotteryService.lotteryByDataBase(actionCode, "byChance", openId,
						getNoPrizeBean(actionCode), sqlSession);
			}
			sqlSession.commit(true);
		} catch (Exception e) {
			logger.error("WdXLSGPresentService->getLottery:抽奖出现异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));
			if (sqlSession != null)sqlSession.rollback();
			return new ResultBean<>(false, "抽奖错误，请重试", null);
		} finally {
			if (sqlSession != null)sqlSession.close();
		}
		}
		//领取android礼包
		if(os=="ios"){
			try (DistributedLock lock = new DistributedLock("getLottery<" + openId + ">" + 415)) {

				lock.weakLock(65, 25);
				// 判断是否中过奖;
				logger.info("驯龙三国-领取礼包：openId=" + openId + ";actionCode=415"  + ";os=" + os);
				logger.debug("WdXLSGPresentService: 检查该玩家是否领取过礼包");
				if (androidCard != null||iosCard != null) {
					logger.debug("WdXLSGPresentService: 该玩家{}已领取过礼包", openId);
					result.setIsSuccess(false);
	                                if(iosCard != null){
	                                    result.setMessage("亲爱的玩家~<br />您已经领取过IOS独家礼包<br /><br />礼包兑换码：<br />".concat(iosCard));
	                                }else{
	                                    result.setMessage("亲爱的玩家~<br />您已经领取过Android独家礼包<br /><br />礼包兑换码：<br />".concat(androidCard));
	                                }
					return result;
				} else {
					// 开始抽奖
					logger.debug("WdXLSGPresentService: 该玩家{}未领取礼包,开始领取礼包", openId);
					result = wdXLSGlotteryService.lotteryByDataBase(415, "byChance", openId,
							getNoPrizeBean(actionCode), sqlSession);
				}
				sqlSession.commit(true);
			} catch (Exception e) {
				logger.error("WdXLSGPresentService->getLottery:抽奖出现异常,错误堆栈:{}", Throwables.getStackTraceAsString(e));
				if (sqlSession != null)sqlSession.rollback();
				return new ResultBean<>(false, "抽奖错误，请重试", null);
			} finally {
				if (sqlSession != null)sqlSession.close();
			}
			}

		return result;
	}

	/***
	 * 纪念奖
	 * 
	 * @param actionCode
	 * @return
	 */
	private PrizeBean getNoPrizeBean(int actionCode) {
		PrizeBean prizeBean = new PrizeBean();
		prizeBean.setActionCode(actionCode);
		prizeBean.setChinese("谢谢参与,礼包已领完");
		prizeBean.setEnglish("thankyou");
		prizeBean.setIsReal("noRealPrize");
		prizeBean.setNum(0);
		return prizeBean;

	}
}
