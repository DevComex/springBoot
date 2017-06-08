package cn.gyyx.action.service.actionSurvey;

import org.slf4j.Logger;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.actionsurvey.ActionSurveyInfoBean;
import cn.gyyx.action.bll.actionSurvey.ActionSurveyBll;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ActionSurveyService {
	private static final Logger logger = GYYXLoggerFactory.getLogger(ActionSurveyService.class);
	private ActionSurveyBll actionSurveyBll = new ActionSurveyBll();

	/**
	 * 
	 * @Title: addSurveyInfoBean @Description: TODO 添加调查问卷信息 @param @param
	 *         surveyInfoBean @param @return @return ResultBean<Boolean> @throws
	 */
	public ResultBean<Boolean> addSurveyInfoBean(ActionSurveyInfoBean actionSurveyInfoBean) {
		logger.info("actionSurveyInfoBean", actionSurveyInfoBean);
		ResultBean<Boolean> result = new ResultBean<Boolean>(false, "记录调查问卷信息失败", false);
		try (DistributedLock lock = new DistributedLock("sdsgsurvey"+actionSurveyInfoBean.getPhoneNum())) {
			lock.weakLock(10, 11);
			// 判断是否提交过调查问卷
			if (actionSurveyBll.getInfoByPhoneNum(actionSurveyInfoBean) != null) {
				result.setMessage("您已提交过调查问卷");
				return result;
			}
			actionSurveyBll.addSurveyInfoBean(actionSurveyInfoBean);
			result.setProperties(true, "恭喜您,调查问卷信息添加成功!", true);
			logger.info("actionSurveyInfoBean", actionSurveyInfoBean);
			logger.info("result", result);
		} catch (DLockException e1) {
			logger.warn("ActionSurveyService->addSurveyInfoBean,Type:{}" + e1.getType() + ",Message:{}" + e1.getMessage());
		}
		return result;
	}

}
