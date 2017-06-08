package cn.gyyx.action.ui.sdsgsurvey;

import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.actionsurvey.ActionSurveyInfoBean;
import cn.gyyx.action.beans.common.ActionConfigBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.bll.actionSurvey.ActionSurveyBll;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.wdninestory.QualificationBLL;
import cn.gyyx.action.service.actionSurvey.ActionSurveyService;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping(value = "/sdsg/survey")
public class SdsgSurveyController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(SdsgSurveyController.class);
	private ActionSurveyService actionSurveyService = new ActionSurveyService();
	private QualificationBLL qualificationBLL = new QualificationBLL();
	private ActionSurveyBll actionSurveyBll = new ActionSurveyBll();

	/**
	 * 
	 * @Title: toIndex @Description: TODO 活动首页 @param @param model @param @param
	 *         request @param @return @return String @throws
	 */
	@RequestMapping("/index")
	public String toIndex(Model model, HttpServletRequest request) {
		return "sdsgsurvey/survey";
	}

	/**
	 * 
	 * @Title: addSurveyInfoBean @Description: TODO 提交调查问卷信息 @param @param
	 *         surveyInfoBean @param @return @return ResultBean<Boolean> @throws
	 */
	@RequestMapping(value = "/addSurvey", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> addSurveyInfoBean(@ModelAttribute ActionSurveyInfoBean actionSurveyInfoBean) {
		ResultBean<Boolean> result = new ResultBean<Boolean>(false, "", false);
		logger.info("神道三国调查问卷活动传入参数actionSurveyInfoBean" + actionSurveyInfoBean);
		logger.debug("神道三国调查问卷活动: 检查活动时间");

		Date nowDate = new Date();
		ActionConfigBean actionConfig = qualificationBLL.selectActionConfigByCode(416);

		Date beginDate = actionConfig.getHdStartD();
		if (nowDate.getTime() < beginDate.getTime()) {
			return new ResultBean<>(false, "" + "活动尚未开始，请耐心等待", null);
		}

		Date endDate = actionConfig.getHdEndD();
		if (nowDate.getTime() >= endDate.getTime()) {
			return new ResultBean<>(false, "" + "活动已经结束，谢谢参与", null);
		}
		// 验证是否选择正确的性别
		if (actionSurveyInfoBean.getSex() == null) {
			result.setMessage("请选择性别");
			return result;

		}
		// 验证是否填写出生年月
		if (actionSurveyInfoBean.getBirthday() == null) {
			result.setMessage("请填写出生年月");
			return result;
		}
		// 验证是否填写所在城市
		if (actionSurveyInfoBean.getCity() == null) {
			result.setMessage("请填写所在城市");
			return result;
		}
		// 验证从哪得知该游戏
		if (actionSurveyInfoBean.getSource() == null) {
			result.setMessage("请填写从哪得知该游戏");
			return result;
		}
		// 验证是否填写最看重游戏哪方面的品质
		if (actionSurveyInfoBean.getQuality() == null) {
			result.setMessage("请填写最看重游戏哪方面的品质");
			return result;
		}
		// 验证是否填写每日游戏时间
		if (actionSurveyInfoBean.getTime() == null) {
			result.setMessage("请填写最每日游戏时间");
			return result;
		}
		// 判断手机号填写是否完整
		if (actionSurveyInfoBean.getPhoneNum() == null || "".equals(actionSurveyInfoBean.getPhoneNum())) {
			result.setMessage("尚未填写手机号");
			return result;
		}

		// 判断手机号格式
		String REGEX_MOBILE = "^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";
		if (!Pattern.matches(REGEX_MOBILE, actionSurveyInfoBean.getPhoneNum())) {
			result.setMessage("手机号格式不正确");
			return result;
		}

		// 判断最近3月所玩游戏长度
		if (!("".equals(actionSurveyInfoBean.getPhoneNum())) && actionSurveyInfoBean.getRecentlyGame() != null
				&& actionSurveyInfoBean.getRecentlyGame().length() > 50) {
			result.setMessage("填写信息长度有误,请重新填写~");
			return result;
		}		
			actionSurveyInfoBean.setCreateTime(new Date());
			actionSurveyInfoBean.setActionCode(416);
			result = actionSurveyService.addSurveyInfoBean(actionSurveyInfoBean);
		logger.info("神道三国调查问卷活动最终result" + result);
		return result;

	}

}
