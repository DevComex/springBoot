/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：wanglei
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月10日 
 * @版本号：v1.217
 * @本类主要用途描述：问道地铁抽奖控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping(value = "/wdsubway")
public class WDSubwayLotteryController {
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "subwaylottery/index";
	}
	/**
	 * 更新用户收货地址
	 * @param lotteryUserInfoBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resetAddress", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String resetAddress(@ModelAttribute HttpServletRequest request){
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(true, "活动已经结束", null);
		 return DataFormat.objToJson(resultBean);
	}

	/**
	 * 
	 * @Title: resetAddress
	 * @Description: TODO 插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/addAddress", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addAddress(
			@ModelAttribute HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(true, "活动已经结束", null);
		 return DataFormat.objToJson(resultBean);
	}



	/**
	 * 得到当前活动所有中奖的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAllLotteryInfo() {
		String result  = "{\"isSuccess\":true,\"message\":\"成功\",\"data\":[{\"account\":\"ff******\",\"presentName\":\"200京东卡\"},{\"account\":\"ai******\",\"presentName\":\"1京东卡\"},{\"account\":\"wl******\",\"presentName\":\"iPad mini3\"},{\"account\":\"I9******\",\"presentName\":\"100京东卡\"},{\"account\":\"Op******\",\"presentName\":\"1京东卡\"},{\"account\":\"Fd******\",\"presentName\":\"1京东卡\"},{\"account\":\"wv******\",\"presentName\":\"礼包\"},{\"account\":\"j6******\",\"presentName\":\"5京东卡\"},{\"account\":\"0A******\",\"presentName\":\"1京东卡\"},{\"account\":\"x3******\",\"presentName\":\"iPhone 6S Plus\"},{\"account\":\"n1******\",\"presentName\":\"1京东卡\"},{\"account\":\"hC******\",\"presentName\":\"1京东卡\"},{\"account\":\"dB******\",\"presentName\":\"5京东卡\"},{\"account\":\"bV******\",\"presentName\":\"10京东卡\"},{\"account\":\"v5******\",\"presentName\":\"5京东卡\"},{\"account\":\"NP******\",\"presentName\":\"50京东卡\"},{\"account\":\"oM******\",\"presentName\":\"200京东卡\"},{\"account\":\"82******\",\"presentName\":\"礼包\"},{\"account\":\"a5******\",\"presentName\":\"1京东卡\"},{\"account\":\"gv******\",\"presentName\":\"50京东卡\"},{\"account\":\"33******\",\"presentName\":\"1京东卡\"},{\"account\":\"l8******\",\"presentName\":\"100京东卡\"},{\"account\":\"M4******\",\"presentName\":\"礼包\"},{\"account\":\"oo******\",\"presentName\":\"Apple Watch\"},{\"account\":\"1I******\",\"presentName\":\"1京东卡\"},{\"account\":\"cT******\",\"presentName\":\"5京东卡\"},{\"account\":\"ss******\",\"presentName\":\"1京东卡\"},{\"account\":\"aw******\",\"presentName\":\"5京东卡\"},{\"account\":\"we******\",\"presentName\":\"10京东卡\"},{\"account\":\"dr******\",\"presentName\":\"礼包\"},{\"account\":\"gf******\",\"presentName\":\"1京东卡\"},{\"account\":\"cg******\",\"presentName\":\"10京东卡\"},{\"account\":\"we******\",\"presentName\":\"iPad mini3\"},{\"account\":\"hh******\",\"presentName\":\"1京东卡\"},{\"account\":\"qw******\",\"presentName\":\"100京东卡\"},{\"account\":\"zx******\",\"presentName\":\"1京东卡\"},{\"account\":\"fg******\",\"presentName\":\"礼包\"},{\"account\":\"d5******\",\"presentName\":\"1京东卡\"},{\"account\":\"c8******\",\"presentName\":\"10京东卡\"},{\"account\":\"vu******\",\"presentName\":\"1京东卡\"},{\"account\":\"d8******\",\"presentName\":\"礼包\"},{\"account\":\"W4******\",\"presentName\":\"1京东卡\"},{\"account\":\"cp******\",\"presentName\":\"1京东卡\"},{\"account\":\"23******\",\"presentName\":\"10京东卡\"},{\"account\":\"78******\",\"presentName\":\"礼包\"},{\"account\":\"f8******\",\"presentName\":\"1京东卡\"},{\"account\":\"v9******\",\"presentName\":\"Apple Watch \"},{\"account\":\"JK******\",\"presentName\":\"5京东卡\"},{\"account\":\"Y7******\",\"presentName\":\"1京东卡\"},{\"account\":\"2h******\",\"presentName\":\"礼包\"},{\"account\":\"c0******\",\"presentName\":\"1京东卡\"},{\"account\":\"KL******\",\"presentName\":\"iPad mini3\"}]}";

		return result;
		
	}

	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfoByUser", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAllLotteryInfoByUser(HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(true, "活动已经结束", null);
		 return DataFormat.objToJson(resultBean);
	}
	/**
	 * 抽奖
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lottery", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String lottery(HttpServletRequest request){
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(true, "活动已经结束", null);
		 return DataFormat.objToJson(resultBean);
		
	}
	
	/**
	 * @Description 根据存储过程查询各条地铁线投票总数
	 * @author fanyongliang
	 * @return
	 */
	@RequestMapping(value = "/getAllLineVoteInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAllLineVoteInfo() {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(true, "活动已经结束", null);
		 return DataFormat.objToJson(resultBean);
	}
	/**
	 * @Description  根据用户账号查询用户投票的地铁线
	 * @author fanyongliang
	 * @return
	 */
	@RequestMapping(value = "/getSubwayVoteByAccount" , method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getSubwayVoteByAccount(HttpServletRequest request) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(true, "活动已经结束", null);
		 return DataFormat.objToJson(resultBean);
	}
	/**
	 * @Description  投票，插入数据
	 * @author fanyongliang
	 * @return
	 */
	@RequestMapping(value = "/addSubwayVoteInfo" , method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addSubwayVoteInfo(HttpServletRequest request,String line) {
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(true, "活动已经结束", null);
		 return DataFormat.objToJson(resultBean);
	}
}
