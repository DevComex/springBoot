package cn.gyyx.action.ui.wdnationalday;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.beans.lottery.po.ActionQualificationPO;
import cn.gyyx.action.beans.wdnationalday.WdNationaldayEnrollBean;
import cn.gyyx.action.beans.wdnationalday.WdPrizesLogBean;
import cn.gyyx.action.beans.wdnationalday.WdSignLogBean;
import cn.gyyx.action.bll.lottery.AddressBLL;
import cn.gyyx.action.bll.lottery.ILotteryValidateBLL;
import cn.gyyx.action.bll.lottery.impl.LotteryValidateBLLImplByTime;
import cn.gyyx.action.bll.wdnationalday.WdNationaldayEnrollBll;
import cn.gyyx.action.ui.WeChatBaseController;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

@Controller
@RequestMapping("/wdnationalscore")
public class WdNationalDayScorecheckController extends WeChatBaseController {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdNationalDayScorecheckController.class);
	private static int actionCode = 395;
	private AddressBLL addressBll = new AddressBLL();
	private String developType = "release";
	private WdNationaldayEnrollBll WdNationaldayEnrollBll = new WdNationaldayEnrollBll();
	private ILotteryValidateBLL validateByTime = new LotteryValidateBLLImplByTime();

	protected void setDevelopType(String type) {
		this.developType = StringUtils.isEmpty(type) ? "release" : type;
	}

	@RequestMapping("/scorepage")
	public String index(Model model) {

		return "nationalDay/sign_two";
	}


	/*
	 * 返回该用户当前积分节点状态
	 **/
	@RequestMapping("/checkscore")
	@ResponseBody
	public ResultBean<Map<Integer, Integer>> checkScoreStatus(HttpServletRequest request, HttpServletResponse response,
			@Param("OpenId") String OpenId) {
		ResultBean<Map<Integer, Integer>> result = new ResultBean<Map<Integer, Integer>>();
		result.setIsSuccess(false);
		result.setMessage("活动已结束！");
		return result;
	}

	@RequestMapping("/getchance")
	@ResponseBody
	public ResultBean<Map<String, String>> getChance(HttpServletRequest request, HttpServletResponse response,
			WdSignLogBean wdSignLogBean, @Param("OpenId") String OpenId) {
		ActionQualificationPO po = new ActionQualificationPO();
		WdPrizesLogBean wdPrizesLogBean = new WdPrizesLogBean();
		ResultBean<Map<String, String>> mapback = new ResultBean<Map<String, String>>();
		mapback.setIsSuccess(false);
		mapback.setMessage("活动已结束！");
		return mapback;
	}

	/**
	 * 
	 * @日期：2016年8月31日 @Title: resetAddress
	 * @Description: 插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/setAddress", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address, HttpServletResponse response,
			HttpServletRequest request, @Param("OpenId") String OpenId) {
		ResultBean<Integer> result = new ResultBean<>(false, "添加地址信息失败", 0);

		try {
			// 是否微信浏览器
			if (!this.isWechatBrowser(request, response)) {
				// 不是微信浏览器，则返回 return result; }
			}
			// 是否关注微信订阅号
						if (!this.isAttention(actionCode, OpenId)) {
							// 没有关注微信订阅号，则返回
							result.setMessage("请先关注微信订阅号！");
							return result;
						}
			// 是否登录
			if (!this.validateLogin(actionCode, OpenId)) { // 没有登录
				result.setMessage("请先登录！");
				return result;
			}

			if (!StringUtils.isEmpty(OpenId)) {

				/**
				 * 验证用户姓名
				 */
				String userNameTemplate = ("^[\u4E00-\u9FA5]{2,6}$");
				if (!address.getUserName().matches(userNameTemplate)) {
					result.setIsSuccess(false);
					result.setMessage("姓名只能为2-6个汉字");
					return result;
				}
				/**
				 * 验证电话格式
				 */
				String phoneTemplate = ("^[0-9]{10,20}$");
				if (!address.getUserPhone().matches(phoneTemplate)) {
					result.setIsSuccess(false);
					result.setMessage("电话格式不正确");
					return result;
				}

				/**
				 * 验证地址长度
				 */
				if (address.getUserAddress().length() >200) {
					result.setIsSuccess(false);
					result.setMessage("请正确填写地址");
					return result;

				}
				logger.info("WdNationalDayScorecheckController=>AddressBean", address);

				WdNationaldayEnrollBean bean = WdNationaldayEnrollBll.getUserInfoByOpenId(OpenId);
				address.setActionCode(actionCode);
				address.setUserAccount(bean.getAccount());
				address.setUserCode(bean.getUserId());
				address.setActionCode(actionCode);
				ResultBean<AddressBean> resultAddre = addressBll.getAddress(bean.getUserId(), actionCode);
				if (resultAddre.getData() == null) {
					result = addressBll.addAddress(address);
					if (result.getIsSuccess()) {
						result.setMessage("地址添加成功");
						return result;
					} else {
						result.setIsSuccess(false);
						result.setMessage("插入失败");
						return result;
					}

				} else {
					result = addressBll.setAddress(address);
					if (result.getIsSuccess()) {
						result.setMessage("地址修改成功");
						return result;
					} else {
						result.setIsSuccess(false);
						result.setMessage("地址修改失败");
						return result;
					}
				}

			}

			else {
				result.setMessage("请先登录！");
			}
		} catch (Exception e) {
			logger.error("LotteryBaseController.setAddress => OpenId=" + OpenId, e);
		}

		return result;

	}
}
