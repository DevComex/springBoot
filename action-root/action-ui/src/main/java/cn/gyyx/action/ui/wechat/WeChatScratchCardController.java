/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：wd weChat scratchCard
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2015年12月24日
 * @版本号：
 * @本类主要用途描述：问道微信刮刮卡活动控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.ui.wechat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.core.DataFormat;

/**
 * @ClassName: WeChatScratchCardController
 * @Description: TODO 问道微信刮刮卡活动
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2015年12月24日 下午4:56:49
 *
 */

@Controller
@RequestMapping(value="/wechatWDScratchCard")
public class WeChatScratchCardController {
	
	/**
	 * 
	 * @Title: scratchCardLottery
	 * @Description: TODO 链接分享
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/scratchCardShare")
	public String scratchCardShare() {
		return "wdscratch/share";
	}
	
	/**
	 * 
	 * @Title: scratchCardLottery
	 * @Description: TODO 刮刮卡抽奖
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/scratchCardLottery")
	public String scratchCardLottery(HttpServletRequest request) {
		return "wdscratch/index";
	}
	
	/**
	 * 
	 * @Title: resetAddress
	 * @Description: TODO 重置或者插入地址信息
	 * @param @param address
	 * @param @param OpenId
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = {"/resetAddress", "/updateFlag"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String resetAddress() {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "谢谢参与，活动已结束", 0);
		
		return DataFormat.objToJson(result);
	}
	/**
	 * 
	 * @Title: getPrize
	 * @Description: TODO 查询用户是否中奖及抽奖功能
	 * @param @param OpenId
	 * @param @param request
	 * @param @return
	 * @return ResultBean<UserLotteryBean>
	 * @throws
	 */
	@RequestMapping(value = "/getPrize", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean getPrize() {
		ResultBean result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
		return result;
	}
	
	/**
	 * 
	* @Title: getAllLotteryInfo
	* @Description: TODO 得到当前活动所有中奖的信息
	* @param @return    
	* @return ResultBean<List<UserInfoBean>>    
	* @throws
	 */
	@RequestMapping(value = "/getLotteryInfo")
	@ResponseBody
	public ResultBean getAllLotteryInfo() {
		ResultBean resultBean = new ResultBean<>(false, "谢谢参与，活动已结束", null);
		return resultBean;
	}
	
	/**
	 * 
	* @Title: getUserCount
	* @Description: TODO 获取用户参与量（100%中奖）
	* @param @return    
	* @return ResultBean<Integer>    
	* @throws
	 */
	@RequestMapping(value="/getUserCount")
	@ResponseBody
	public ResultBean<Integer> getUserCount() {
		ResultBean<Integer> resultBean = new ResultBean<Integer>(false, "谢谢参与，活动已结束", 0);
		return resultBean;
	}

}