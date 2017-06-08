package cn.gyyx.action.ui.wdbackroom;

import java.io.UnsupportedEncodingException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;
import cn.gyyx.action.service.newLottery.LotteryException;

@Controller
@RequestMapping(value = "/wdbackroom")
public class WdBackRoomController {

	/***
	 * 主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {

		return "wdbackroom/index";
	}

	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> userlogin(HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "谢谢参与，活动已结束", null);
	
		return result;

	}

	/***
	 * 抽奖
	 * 
	 * @param request
	 * @param serverName
	 * @param serverCode
	 * @return
	 * @throws LotteryException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/Lottery", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean getPredicableLottery(HttpServletRequest request)
			throws LotteryException, UnsupportedEncodingException {

		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
		
		return result;
	}

	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean getAllLotteryInfoByUser(HttpServletRequest request) {
		ResultBean resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
	
		return resultBean;
	}

	// 查询地址
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean getAddress(HttpServletRequest request) {
		ResultBean resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
		
		return resultBean;
	}

	/**
	 * 
	 * @日期：2016年10月09日 @Title: resetAddress
	 * @Description: 插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/setAddress", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address, HttpServletResponse response,
			HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
		return result;
	}

}
