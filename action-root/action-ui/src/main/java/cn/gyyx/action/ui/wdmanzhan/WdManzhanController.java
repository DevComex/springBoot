package cn.gyyx.action.ui.wdmanzhan;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.lottery.UserLotteryBean;
import cn.gyyx.action.service.newLottery.LotteryException;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping(value = "/wd_manzhan")
public class WdManzhanController {
	public static final int ACTIONCODE = 387;
	/***
	 * 主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {

		return "wdmanzhan/index";
	}

	/***
	 * 用户登录
	 * 
	 * return
	 * 
	 * 
	 */
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> userlogin(HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("isSuccess", "false");
		map.put("message ", "谢谢参与，活动已结束");
		return map;

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
	public ResultBean<UserLotteryBean> getPredicableLottery(HttpServletRequest request)
			throws LotteryException, UnsupportedEncodingException {
			return new ResultBean<>(false, "谢谢参与，活动已结束", null);
		
	}

	/**
	 * 点赞作品
	 * 
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/clicklike", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Integer> likeWork(Integer code, HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Integer> result = new ResultBean<>(false, "谢谢参与活动已结束", -1);
		return result;
	}

	/***
	 * 获取用户抽奖次数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLotteryTime", method = RequestMethod.GET)
	@ResponseBody
	public int getUserLottertTime(HttpServletRequest request) {
		return 0;
	}

	/***
	 * 获取图片点赞次数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getlikeTimes", method = RequestMethod.GET)
	@ResponseBody
	public int getWorkLikeTimes(HttpServletRequest request, String workId) {
		return 999;

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
	public ResultBean<Integer> setAddress(@ModelAttribute AddressBean address, HttpServletRequest request) {
		ResultBean<Integer> result = new ResultBean<>(false, "谢谢参与，活动已结束", 0);
		return result;
	}

	/**
	 * 得到当前作品点赞次数
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getliketimes")
	@ResponseBody
	public HashMap<String, String> getLikesByWorkId(String workId, HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("isSuccess", "false");
		map.put("message ", "谢谢参与，活动已结束");
		return map;
	}

	/**
	 * 得到当前活动所有中奖的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getLotteryInfo")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfo() {
		ResultBean<List<UserInfoBean>> resultBean = new ResultBean<>(false,"谢谢参与，活动已结束",null);
		return resultBean;
	}

	/**
	 * 获取漫画地址的控制器
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/getImageUrl")
	@ResponseBody
	public ResultBean<String> getImageUrl() {
		ResultBean<String> resultBean = new ResultBean<>();
			resultBean.setIsSuccess(false);
			resultBean.setMessage("谢谢参与，活动已结束");
			return resultBean;

	}

	/**
	 * 获取用户中奖信息的控制器
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllLotteryInfoByUser")
	@ResponseBody
	public ResultBean<List<UserInfoBean>> getAllLotteryInfoByUser(HttpServletRequest request) {
		ResultBean<List<UserInfoBean>> resultBean = new ResultBean<>();
			resultBean.setProperties(false, "谢谢参与活动已结束", null);
		return resultBean;
	}

	/**
	 * 微博发布获取中奖
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/sendweibo", method = RequestMethod.GET)
	@ResponseBody
	public String sendweibo(String workId, HttpServletRequest request, HttpServletResponse response) {
		ResultBean<Boolean> result = new ResultBean<Boolean>(false, "谢谢参与活动已结束", false);
		return DataFormat.objToJson(result);
	}

	/**
	 * 更新图片点击次数
	 */
	@RequestMapping(value = "/clickimage")
	@ResponseBody
	public void ClickImageTime() {
	}

	/**
	 * 查询所有图片次数
	 */
	@RequestMapping(value = "/imageclicktimes")
	@ResponseBody
	public List<String> selectAllImageClick() {
		List<String> list = new ArrayList<String>();
		list = null;
		return list;
	}
}
