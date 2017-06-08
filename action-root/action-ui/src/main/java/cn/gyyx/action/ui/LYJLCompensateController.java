package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年8月12日 下午12:49:24
 * 描        述：龙与精灵补偿活动-controller
 */
@Controller
@RequestMapping("/lyjlcompensate")  
public class LYJLCompensateController {
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(LYJLCompensateController.class);
	

	/**
	 * index
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request){
		String[] agent = { "android", "iphone", "ipod","ipad", "windows phone", "mqqbrowser" };
		String userAgent = request.getHeader("user-agent");
		if(userAgent == null){
			return "不支持该浏览器";
		}
		boolean isWap = false;
        for(int i=0;i<agent.length;i++){
            if(userAgent.toLowerCase().indexOf(agent[i])>0){
            	isWap = true;
            }
        }
        if(isWap){
        	return "lyjlcompensate/wapIndex";
        }else{
        	return "lyjlcompensate/pcIndex";
        }
		
	}
	
	/**
	 * 获取服务器信息
	  */
	@RequestMapping(value="/getServer")
	@ResponseBody
	public ResultBean<List<String>> getServer(HttpServletRequest request,@RequestParam(value = "appVersion") String appVersion){

		ResultBean<List<String>> resultBean = new ResultBean<List<String>>(false, "谢谢参与，活动已结束", null);

		resultBean.setIsSuccess(true);
		resultBean.setMessage("活动已下线");
		return resultBean;
	}
	
	/**
	 * 兑换礼包码
	  */
	@RequestMapping(value="/exchange",method=RequestMethod.POST)
	@ResponseBody
	public ResultBean<String> exchange(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "source") String source,@RequestParam(value = "distName") String distName,
			@RequestParam(value = "roleName") String roleName,@RequestParam(value = "validateCode") String validateCode){

		ResultBean<String> resultBean = new ResultBean<>(false,"谢谢参与，活动已结束",null);

	
		resultBean.setIsSuccess(false);
		resultBean.setMessage("活动已下线");

		return resultBean;
	}
}
