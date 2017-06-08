/*************************************************	
   Copyright ©, 2016, GY Game
   Author: 王雷
   Created: 2016年-4月-15日
   Note:问道十周年 —— 一生换十年活动 上传作品控制器
************************************************/
package cn.gyyx.action.ui.wdtenonechangeten;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.wdninestory.ServerBean;


@Controller
@RequestMapping("/WDOneChangeTen")
public class UploadedWorkController {

	/**
	 * 获取服务器地址
	 * @param typeCode
	 * @return
	 */
	@RequestMapping(value = "/getServers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<List<ServerBean>> getServers(@RequestParam(value = "netType") String typeCode) {
		ResultBean<List<ServerBean>> result = new ResultBean<List<ServerBean>>(false, "谢谢参与，活动已结束", null);

		return result;
	}
	/**
	 * 上传作品
	 * @return
	 */
	@RequestMapping(value = "/uploadeWork", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultBean<Boolean> uploadeWork(String identifyingCode, HttpServletRequest request,
			 HttpServletResponse response){
		ResultBean<Boolean> result = new ResultBean<Boolean>(false,"谢谢参与，活动已结束",false);

		return result;
	}
	
}
