/**
 * ---------------------------------------------------
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞
 * 联系方式：wangyufei@gyyx.cn
 * 创建时间：2016年1月21日下午2:06:21
 * 版本号：v1.0
 * 本类主要用途叙述：老兵回归的
 */

package cn.gyyx.action.ui;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;

import cn.gyyx.action.service.newLottery.LotteryException;

/**
 * @modify bozch 2016-07-13
 *
 */
@Controller
@RequestMapping(value = "/reback")
public class WDOldManComeController {



	/***
	 * 主页
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "oldman/index";
	}

	/**
	 *
	 * @日期：2015年3月20日 @Title: getServers
	 * @Description: 获取服务器信息
	 * @param typeCode
	 * @return ResultBean<List<ServerBean>>
	 */
	@RequestMapping(value = "/getServers")
	@ResponseBody
	public ResultBean<List<String>> getServers(@RequestParam(value = "netType") String typeCode) {
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}

	/**
	 *
	 * @日期：2015年3月20日 @Title: getAddress
	 * @Description: 传递用户地址信息
	 * @param request
	 * @return ResultBean<AddressBean>
	 */
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean<String> getAddress(HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}

	/**
	 *
	 * @日期：2015年3月20日 @Title: resetAddress
	 * @Description: 重置或者插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/resetAddress", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> resetAddress(@ModelAttribute AddressBean address, HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}

	/**
	 * 得到当前活动所有中奖的信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfo")
	@ResponseBody
	public ResultBean<List<String>> getAllLotteryInfo() {
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
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
	@RequestMapping(value = "/getLottery", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> getPredicableLottery(HttpServletRequest request,
			@RequestParam(value = "serverName") String serverName, @RequestParam(value = "serverCode") int serverCode)
			throws LotteryException, UnsupportedEncodingException {
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}

	/**
	 * 获取用户中奖信息的控制器
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<String>> getRealLotteryInfoByUser(HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}

	/**
	 * 获取用户中奖信息的控制器
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<String>> getAllLotteryInfoByUser(HttpServletRequest request) {
		return new ResultBean<>(false, "谢谢参与，活动已结束", null);
	}
}
