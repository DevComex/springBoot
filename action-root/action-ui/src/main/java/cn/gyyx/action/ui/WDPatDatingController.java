package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ActionCommonCommentBean;
import cn.gyyx.action.beans.common.ActionCommonImageBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.wdninestory.AddressBean;
import cn.gyyx.action.beans.wdninestory.ServerBean;
import cn.gyyx.action.beans.wdpetdating.PageResult;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
@Controller
@RequestMapping(value = "wdpetdating")
public class WDPatDatingController {
	private int actionCode = 264;
	private PrizeBean noprizeBean = new PrizeBean(1013, "谢谢参与", "nameplate",
			actionCode, "noRealPrize", 5);
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDPatDatingController.class);
//	@RequestMapping(value = "/showSignTest")
//	public String showSignTest() {
//		return "wdpetdating/signTest";
//	}
	@RequestMapping(value = "/index")
	public String index() {
		return "wdpetdating/index";
	}
	@RequestMapping(value = "/guize")
	public String guize() {
		return "wdpetdating/guize";
	}
	@RequestMapping(value = "/cansai")
	public String cansai() {
		return "wdpetdating/cansai";
	}
	@RequestMapping(value = "/choujiang")
	public String choujiang() {
		return "wdpetdating/choujiang";
	}
	@RequestMapping("/ajaxGetServerByAreaCode")
	public @ResponseBody ResultBean<List<Value>> ajaxGetServerByAreaCode(HttpServletRequest request,@RequestParam("areaCode")int areaCode){
		ResultBean<List<Value>> result = new ResultBean<List<Value>>();
		result.setIsSuccess(false);
		result.setMessage("活动已结束！");
		return result;
	}
	/** 
	* 叙述:异步上传作品<br />
	* @param petBean 表单对应实体
	* @param image 图片对应实体
	* @return ResultBean<String>
	*/
	@RequestMapping("/ajaxUpload")
	public @ResponseBody ResultBean<String> ajaxUpload(HttpServletRequest request,ActionCommonImageBean image){
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		result.setMessage("活动已结束！");
		return result;
	}
	/** 
	* 叙述:异步检索作品<br />
	* @param queryBean 检索作品查询实体
	* @return ResultBean<PageResult> 
	*/
	@RequestMapping("/ajaxGetUserUploadList")
	public @ResponseBody ResultBean<PageResult> ajaxGetUserUploadList() {
		//TODO 检测实体信息完整性
		ResultBean<PageResult> result = new ResultBean<PageResult>();
		result.setIsSuccess(false);
		result.setMessage("活动已结束！");
		return result;
	}
	
	/** 
	* 叙述:根据作品主键，返回作品详细信息及上前、后间隔作品状态及主键<br />
	* @param queryBean
	* @return ResultBean<SingleResult>
	*/
	@RequestMapping("/ajaxGetUserUploadByCode")
	public @ResponseBody ResultBean<Object> ajaxGetUserUploadByCode() {
		//TODO 检测实体信息完整性
		ResultBean<Object> result = new ResultBean<Object>();
		result.setIsSuccess(false);
		result.setMessage("活动已结束！");
		return result;
	}
	
	/** 
	* 叙述:点赞操作<br />
	* @param request
	* @param code
	* @return ResultBean<String>
	*/
	@RequestMapping("/ajaxAdmire")
	public @ResponseBody ResultBean<String> ajaxAdmire(HttpServletRequest request,@RequestParam("code")int code) {
		ResultBean<String> result = new ResultBean<String>();
		result.setIsSuccess(false);
		result.setMessage("活动已经结束");
		return result;
	}
	
	@RequestMapping("/showUserUpload")
	public String showUserUpload(Model model,@RequestParam("code")int code) {
		return "";
	}
	//TODO
	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: index
	 * @Description: TODO 首页
	 * @param model
	 * @return String
	 */
	@RequestMapping("/lottery")
	public String index(Model model) {
		return "";
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getServers
	 * @Description: TODO 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(
			@RequestParam(value = "netType") String typeCode) {
		logger.info("typeCode", typeCode);
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(
				true, "活动已结束！", null);
		logger.info("result", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: getAddress
	 * @Description: TODO 传递用户地址信息
	 * @param request
	 * @return ResultBean<AddressBean>
	 */
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean<AddressBean> getAddress(HttpServletRequest request) {
		ResultBean<AddressBean> result = new ResultBean<AddressBean>(false,
				"活动已结束！", null);
		logger.info("getAddress result ", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: resetAddress
	 * @Description: TODO 重置或者插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/resetAddress")
	@ResponseBody
	public ResultBean<Integer> resetAddress(
			@ModelAttribute AddressBean address, HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "活动已结束！",
				0);
		logger.info("AddressBean", address);
		logger.info("resetAddress result", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月25日
	 * @Title: setAddress
	 * @Description: TODO 修改地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/setAddress")
	@ResponseBody
	public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address,
			HttpServletRequest request) {
		logger.info("AddressBean", address);

		ResultBean<Integer> result = new ResultBean<Integer>(false,
				"活动已结束！", 0);
		logger.info("result", result);
		logger.info("setAddress result ", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月20日
	 * @Title: resetAddress
	 * @Description: TODO 查询用户是否中奖
	 * @param address
	 * @param request
	 * @return ResultBean<UserLotteryBean>
	 */
	@RequestMapping(value = "/getPrize")
	@ResponseBody
	public ResultBean<UserLotteryBean> getPrize(
			@ModelAttribute ServerBean address, HttpServletRequest request) {

		logger.info("ServerBean", address);
		ResultBean<UserLotteryBean> result = new ResultBean<UserLotteryBean>(
				false, "活动已结束！", null);
		logger.info("getPrize result", result);
		return result;
	}

	/**
	 * 
	 * @日期：2015年3月24日
	 * @Title: getLotteryTime
	 * @Description: TODO 获取用户抽奖次数
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/getLotteryTime")
	@ResponseBody
	public ResultBean<Integer> getLotteryTime(HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<Integer>(false,
				"活动已结束！", 0);
		logger.info("result getLotteryTime", result);
		return result;
	}

	/**
	 * 得到当前活动所有中奖的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfo")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfo() {
		ResultBean<List<UserInfoBean>> resultBean = new ResultBean<List<UserInfoBean>>();
		resultBean.setProperties(true, "活动已结束！", null);
		logger.info("getAllLotteryInfo", resultBean);
		return resultBean;
	}

	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfoByUser(
			HttpServletRequest request) {
		ResultBean<List<UserInfoBean>> resultBean = new ResultBean<List<UserInfoBean>>();
		resultBean.setProperties(false, "活动已结束！", null);
		return resultBean;
	}
	@RequestMapping(value = "/addTalk")
	@ResponseBody
	public ResultBean<String> addTalk(HttpServletRequest request,ActionCommonCommentBean actionCommonCommentBean) {
		ResultBean<String> res = new ResultBean<String>();
		res.setIsSuccess(false);
		res.setMessage("活动已结束！");
		return res;
	}
	
}