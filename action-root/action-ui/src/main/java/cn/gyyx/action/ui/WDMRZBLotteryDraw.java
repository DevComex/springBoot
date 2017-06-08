/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：lottery
 * @作者：zhouzhongkai
 * @联系方式：zhouzhongkai@gyyx.cn
 * @创建时间： 2015年4月13日 上午10:26:35
 * @版本号：
 * @本类主要用途描述：问道名人争霸抽奖活动控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;

@Controller
@RequestMapping(value = "/wdmrzbLottery")
public class WDMRZBLotteryDraw {
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
		return "wdmrzblottery/index";
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
	public ResultBean getServers(@RequestParam(value = "netType") String typeCode) {
		
		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
				
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
	public ResultBean getAddress(HttpServletRequest request) {
		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
		
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
	public ResultBean<Integer> resetAddress(HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "谢谢参与，活动已结束", null);
		
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
	public ResultBean<Integer> setAddress(HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "谢谢参与，活动已结束", null);
		
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
	public ResultBean getPrize(HttpServletRequest request) {

		ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
		
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
	@RequestMapping(value = {"/getLotteryTime", "/getLotteryInfo", "/getLotteryInfoByUser"})
	@ResponseBody
	public ResultBean<Integer> getLotteryTime(HttpServletRequest request) {
		
		ResultBean<Integer> result = new ResultBean<Integer>(false, "谢谢参与，活动已结束", null);
		
		return result;	
	}


}
