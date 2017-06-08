/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2016年1月5日下午14:39:17
 * 版本号：
 * 本类主要用途叙述：柳下挥喊你来玩终极跑酷活动控制器
 */


package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.lottery.AddressBean;


@Controller
@RequestMapping(value = "/zjkpLottery")
public class ZJKPLotteryController {
	private int actionCode = 338;
	
	/**
	 * 
	 * @Title: index
	 * @Description: TODO PC端活动首页
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "zjkpLottery/index";
	}
	
	/**
	 * 
	 * @Title: phone
	 * @Description: TODO 移动端活动首页
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/phone")
	public String phone(Model model) {
		return "zjkpLottery/phone";
	}
	
	/**
	 * 
	 * @Title: getAddress
	 * @Description: TODO 传递用户地址信息
	 * @param request
	 * @return ResultBean<AddressBean>
	 */
	@RequestMapping(value = "/getAddress")
	@ResponseBody
	public ResultBean<String> getAddress(HttpServletRequest request) {
	    return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 
	 * @Title: resetAddress
	 * @Description: TODO 重置或者插入地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/resetAddress",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> resetAddress(
			@ModelAttribute AddressBean address, HttpServletRequest request) {
	    return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 
	 * @Title: setAddress
	 * @Description: TODO 修改地址信息
	 * @param address
	 * @param request
	 * @return ResultBean<Integer>
	 */
	@RequestMapping(value = "/setAddress",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address,
			HttpServletRequest request) {
	    return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 
	 * @Title: resetAddress
	 * @Description: TODO 查询用户是否中奖
	 * @param address
	 * @param request
	 * @return ResultBean<UserLotteryBean>
	 */
	@RequestMapping(value = "/getPrize")
	@ResponseBody
	public ResultBean<String> getPrize(
			HttpServletRequest request) {

	    return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}

	/**
	 * 得到当前活动所有中奖的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfo")
	@ResponseBody
	public ResultBean<List<String>> getAllLotteryInfo() {
	    return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}
	
	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<String>> getAllLotteryInfoByUser(
			HttpServletRequest request) {
	    return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}
	
	@RequestMapping(value = "/isActivityTime")
	@ResponseBody
	public ResultBean<String> isActivityTime(){
	    return new ResultBean<>(false, "谢谢参与，活动已结束！", null);
	}
}
