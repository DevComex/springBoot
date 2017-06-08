package cn.gyyx.action.service.lottery.impl;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.enums.OperateScoreEnums;
import cn.gyyx.action.bll.config.IHdConfigBLL;
import cn.gyyx.action.bll.config.impl.DefaultHdConfigBLL;
import cn.gyyx.action.bll.lottery.IActionQualificationBLL;
import cn.gyyx.action.bll.lottery.impl.ActionQualificationBLLImpl;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.service.lottery.IActionQualificationService;
import cn.gyyx.distribute.lock.DistributedLock;

public class AddScoreOnceEverydayByBlogQualificationService implements IActionQualificationService {

	private IHdConfigBLL hdConfigBLL = new DefaultHdConfigBLL();
	private IActionQualificationBLL actionQualificationBLL = new ActionQualificationBLLImpl();
	
	public ResultBean<Boolean> add(int activityId, String userId, String type, String ip) {
		ResultBean<Boolean> result = new ResultBean<Boolean>();
		
		// 防止用户重复提交
		try(DistributedLock lock = new DistributedLock(activityId + "-" + userId + "-" + AddScoreOnceEverydayByBlogQualificationService.class.getName())) {
			lock.weakLock(30, 0);
			
			// 是否在活动期间内
			int hdState = hdConfigBLL.getState(activityId);
			if (1 != hdState) {
				logger.info("ActionExchangeServiceImpl->exchange->activation is not in progress.");
				result.setStateCode(hdState);
				result.setMessage(hdConfigBLL.getMessage());
				return result;
			}
			
			// 今天有没有通过分享微博加分
			if (!actionQualificationBLL.getState(activityId, userId, OperateScoreEnums.SinaBlog.toString())) {
				// 没有分享过微博
				// 获得分享微博增加的分数
				Object scoreResult = hdConfigBLL.getConfigByKey(activityId, OperateScoreEnums.SinaBlog.toString());
				if (null != scoreResult) {
					int score = Integer.parseInt(scoreResult.toString());
					SqlSession session = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory().openSession();

					// 操作积分时，加并发锁
					try(DistributedLock scoreLock = new DistributedLock(activityId + "-" + userId)) {
						scoreLock.weakLock(30, 0);
						
						// 增加积分
						if (actionQualificationBLL.addLotteryScore(activityId, userId, score, OperateScoreEnums.SinaBlog.toString(), ip, session) > 0) {
							session.commit();
							result.setIsSuccess(true);
							return result;
						}
						else {
							session.rollback();
						}
					}
					catch(Exception sqle) {
						if (null != session) session.rollback();
						logger.error("AddScoreOnceEverydayByBlogQualificationService->add->sql->errorCause:" + sqle.getCause());
						logger.error("AddScoreOnceEverydayByBlogQualificationService->add->sql->errorMessage:" + sqle.getMessage());
						logger.error("AddScoreOnceEverydayByBlogQualificationService->add->sql->StackTrace:" + sqle.getStackTrace());
					}
					finally {
						if (null != session) session.close();
					}
				}
			}
		}
		catch(Exception e) {
			logger.error("AddScoreOnceEverydayByBlogQualificationService->add->errorCause:" + e.getCause());
			logger.error("AddScoreOnceEverydayByBlogQualificationService->add->errorMessage:" + e.getMessage());
			logger.error("AddScoreOnceEverydayByBlogQualificationService->add->StackTrace:" + e.getStackTrace());

		}
		
		return result;
	}
}
