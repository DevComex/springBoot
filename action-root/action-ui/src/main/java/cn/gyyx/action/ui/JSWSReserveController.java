/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：范佳琪 
 * 联系方式：fanjiaqi@gyyx.cn 
 * 创建时间：2016年1月19日下午14:56:23
 * 版本号：
 * 本类主要用途叙述：绝世武神预约分享的控制器
 */
package cn.gyyx.action.ui;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.bll.jswsReserve.ReserveBLL;
import cn.gyyx.action.service.jswsReserve.ReserveService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.distribute.lock.DLockException;
import cn.gyyx.distribute.lock.DistributedLock;

@Controller
@RequestMapping("/jswsReserve")
public class JSWSReserveController {

	private ReserveBLL reserveBLL = new ReserveBLL();
	private ReserveService reserveService = new ReserveService();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(JSWSReserveController.class);
	
	/**
	 * 
	 * @Title: index
	 * @Description: TODO 活动首页
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/index")
	public String index(){
		return "jswsReserve/index";
	}
	
	/**
	 * 
	 * @Title: index
	 * @Description: TODO 分享页
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 */
	@RequestMapping("/share")
	public String share(){
		return "jswsReserve/share";
	}
	
	/**
	 * 
	 * @Title: sendUserPhoneReserveSuccessMessage
	 * @Description: TODO 获取手机号码，发送预约成功短信
	 * @param @param openId
	 * @param @param phoneNum
	 * @param @return    
	 * @return ResultBean<String>    
	 * @throws
	 */
	@RequestMapping(value="/sendUserPhoneReserveSuccessMessage",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> sendUserPhoneReserveSuccessMessage(
			@RequestParam("phoneNum") String phoneNum){
		ResultBean<String> resultBean =  new ResultBean<String>(false, "请输入正确手机号码", null);
		logger.debug("phoneNum",phoneNum);
		try (DistributedLock lock = new DistributedLock("jswsReserve")){	
			lock.weakLock(30, 2);
			if(phoneNum.matches("[1]\\d{10}$")){
				if(null == reserveBLL.getReserveByPhoneNum(phoneNum)){
					String result = reserveService.sendUserPhoneReserveSuccessMessage(phoneNum);
					JSONObject jsonObject = JSONObject.fromObject(result);
					String message = (String) jsonObject.get("Message");
					if("SendSuccess".equals(message)){
						reserveService.insertReserveInfo(phoneNum);
						resultBean.setProperties(true, "预约成功，请查收短信。", result);
					}else {
						resultBean.setProperties(false, "短信发送失败", result);
					}
				}else{
					resultBean.setMessage("该手机号码已经预约过");
				}
			}
		} catch (DLockException e) {
			logger.error("DLockException type:" + e.getType() + ",msg:" + e.getMessage());
			return new ResultBean<String>(false, "网络超时", null);	
		}
		return resultBean;
	}
}