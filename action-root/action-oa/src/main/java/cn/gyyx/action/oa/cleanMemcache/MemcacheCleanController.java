/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：光宇9周年祝福活动
 * @作者：liuyongzhi
 * @联系方式：liuyongzhi@gyyx.cn
 * @创建时间： 2015年3月12日 下午5:01:40
 * @版本号：
 * @本类主要用途描述：光宇9周年祝福图片及祝福语审核控制器
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.cleanMemcache;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.bll.lottery.LotteryMemcacheBll;
import cn.gyyx.action.service.lottery.CleanMemcacheService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 光宇9周年祝福图片及祝福语审核控制器
 */
@Controller
@RequestMapping("/cleanMemcache")
public class MemcacheCleanController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(MemcacheCleanController.class);
	//private CleanMemcacheService   cleanMemcacheService  =new CleanMemcacheService();
	//private LotteryMemcacheBll   lotteryMemcacheBll  =new LotteryMemcacheBll();
	
	
	
	/**
	 * 跳转到清除memcache页面
	 * @param request
	 * @return
	 */
	    @RequestMapping("/cleanMemcache")
	
	    public String  toCleanView(HttpServletRequest request){
	    	return  "cleanMemcache/cleanMemcache";
	    }
        /**
         * 执行清除操作
         * @param request
         * @return
         */
	    @RequestMapping(value="/setcleanMemcache", method = RequestMethod.POST)
	    @ResponseBody
	    public ResultBean<String> setClean(HttpServletRequest request,int actionCode,Model model){
	    	logger.info("actionCode:"+actionCode);
	    	ResultBean<String> resultBean=new ResultBean<String>();
	    	if(actionCode==0){
	    		resultBean.setProperties(false, "活动编号不能为空", "活动编号不能为空");
	    	}
	    	/*
	    	else if(lotteryMemcacheBll.getControParaMem(actionCode)==null){
	    		resultBean.setProperties(false, "不存在这个活动", "不存在这个活动");
	    	}*/
	    	else{
	    	
	    	//cleanMemcacheService.cleanMemcache(actionCode);
	    	resultBean.setProperties(true, "清除成功", "清除成功");
	    	}
	    	logger.info("setClean",resultBean);
	    	return  resultBean;
	    }
	

}
