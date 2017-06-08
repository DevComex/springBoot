/*************************************************	
   Copyright ©, 2016, GY Game
   Author: 王雷
   Created: 2016年-4月-17日
   Note:问道十周年 —— 一生换十年活动 后台作品审核控制器
************************************************/
package cn.gyyx.action.oa.wdtenonechangeten;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.wd10yearonechangten.ResultBean;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping("review")
public class WorksReviewController {

	/**
	 * 审核初始页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		return "wdtenonechangeten/work_review";
	}
	/**
	 * 获取作品信息
	 * @param selectParamBean
	 * @return
	 */
	@RequestMapping(value = "/getWorkInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getWorkInfo(){
		ResultBean result = new ResultBean(false,"谢谢参与，活动已结束",0,0,null);
		
		return DataFormat.objToJson(result);
	}
	
	/**
	 * 发布作品
	 * @param isShow
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String show(Integer isShow, String code){
		ResultBean result = new ResultBean(false,"谢谢参与，活动已结束",0,0,null);
		
		return DataFormat.objToJson(result);
	}
	/**
	 * 删除作品
	 * @param isDelete
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete(Integer isDelete, String code){
		ResultBean result = new ResultBean(false,"谢谢参与，活动已结束",0,0,null);
		
		return DataFormat.objToJson(result);
	}
	/**
	 * 修改点赞数
	 * @param likeCount
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/updateLikeCount", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateLikeCount(Integer likeCount, String code){
		ResultBean result = new ResultBean(false,"谢谢参与，活动已结束",0,0,null);
		
		return DataFormat.objToJson(result);
	}
}
