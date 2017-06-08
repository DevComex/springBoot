package cn.gyyx.action.oa.lhzs.activityCode;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.ActivityCodeChannelBean;
import cn.gyyx.action.beans.oa.lhzs.activityCode.UserMesBean;
import cn.gyyx.action.service.oa.lhzs.activityCode.LHZSOAActivityCodeService;

@Controller
@RequestMapping("/LHZSOAI")
public class LHZSActivityCodeOAController {
	private LHZSOAActivityCodeService lhzsService = new LHZSOAActivityCodeService();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@RequestMapping("/index")
	public String index(){
		return "lhzs/lhzs";
	}
	/**
	 * 已激活-产品
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/activationProduct")
	public ResultBean<String> getActivationProduct(String time){
		ResultBean<String> result = new ResultBean<String>();
		Date paraDate = new Date();
		try{
			paraDate = format.parse(time);
		}catch(Exception e){
			paraDate = new Date();
		}
		result = lhzsService.getActivationProduct(paraDate);
		return result;
	}
	/**
	 * 已激活-市场
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/activationMarket")
	public ResultBean<String> getActivationMarket(String time){
		ResultBean<String> result = new ResultBean<String>();
		Date paraDate = new Date();
		try{
			paraDate = format.parse(time);
		}catch(Exception e){
			paraDate = new Date();
		}
		result = lhzsService.getActivationMarket(paraDate);
		return result;
	}	
	/**
	 * 发放-产品
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendMarket")
	public ResultBean<String> getSendMarket(String time){
		ResultBean<String> result = new ResultBean<String>();
		Date paraDate = new Date();
		try{
			paraDate = format.parse(time);
		}catch(Exception e){
			paraDate = new Date();
		}
		result = lhzsService.getSendMarket(paraDate);
		return result;
	}
	@ResponseBody
	@RequestMapping("/insertActivation")
	public ResultBean<String> insertActivationCode(String num,String channel,String channelStr){
		int numInt = 0,channelInt = 0;
		ResultBean<String> result = new ResultBean<String>();
		try{
			numInt = Integer.parseInt(num);
			channelInt = Integer.parseInt(channel);
		}catch(Exception e){
			result.setProperties(false, "error", "请填写正确的个数或渠道号！");
			return result;
		}
		try {
			result = lhzsService.insertActivationCode(numInt, channelInt, new String(channelStr.getBytes("ISO-8859-1"),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			result.setProperties(false, "error", "渠道描述出错！");
			return result;
		}
		return result;
	}
	/**
	 * 查询所有渠道
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/searchChannel")
	public ResultBean<List<ActivityCodeChannelBean>> searchActivityChannel(){
		return lhzsService.searchActivityChannel();
	}
	/**
	 * 查询详细信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/getActivityCodeMes")
	public String getActivityCodeMes(Model model,String nowDate){
		Date paraDate = new Date();
		try{
			paraDate = format.parse(nowDate);
		}catch(Exception e){
			paraDate = new Date();
		}
		ResultBean<List<UserMesBean>> result = lhzsService.getActivityMarketDetailed(paraDate);
		model.addAttribute("activityMes", result);
		return "lhzs/showMes";
	}
	@ResponseBody
	@RequestMapping("/getActivityCodeMesCode")
	public ResultBean<List<UserMesBean>> getActivityMarketDetailedToCode(String nowDate, String startCode, String num) {
		int startCodeInt = 0,numInt = 0;
		ResultBean<List<UserMesBean>>  result= new ResultBean<List<UserMesBean>>();
		Date paraDate = new Date();
		try{
			startCodeInt = Integer.parseInt(startCode);
			numInt = Integer.parseInt(num);
		}catch(Exception e){
			result.setProperties(false, "error", null);
			return result;
		}
		try{
			paraDate = format.parse(nowDate);
		}catch(Exception e){
			paraDate = new Date();
		}
		result = lhzsService.getActivityMarketDetailedToCode(paraDate, startCodeInt, startCodeInt+numInt+1);
		return result;
	}
}
